package com.yhh.xuanke.service.impl;

import com.google.common.base.Preconditions;
import com.yhh.xuanke.common.CodeMsg;
import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.exception.GlobalException;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.service.RedisService;
import com.yhh.xuanke.service.ResultService;
import com.yhh.xuanke.utils.RedisUtil;
import com.yhh.xuanke.utils.StudentIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultServiceImpl.class);

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public ListDTO<ResultEntity> getResultListPageBySno(Integer pageNum, Integer size, Integer sno) {

        ListDTO<ResultEntity> listDTO = (ListDTO<ResultEntity>) redisService.getFromHash("forResult:" + sno, sno + "-" + pageNum);

        if (listDTO != null) {
            LOGGER.info("从redis中加载选课结果");
            return listDTO;
        }

        //分页查询,按选课时间降序排序，目的是看到最新选的课
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Direction.DESC, "createTime"));

        //jpa复杂查询
        Page<ResultEntity> page = resultRepository.findAll((Specification<ResultEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //根据学号查询
            if (sno != null) {
                predicates.add(builder.equal(root.get("sno"), sno));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        listDTO = new ListDTO<ResultEntity>(page.stream().collect(Collectors.toList()), pageNum, size, page.getTotalPages());

        //加载到redis中
        redisService.setToHash("forResult:" + sno, sno + "-" + pageNum, listDTO, 30, TimeUnit.MINUTES);
        LOGGER.info("从数据中加载选课结果，并将选课结果写入redis中");
        return listDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO<String> noChoose(Integer pno) {

        //为啥突然拿不到授课编号了，头疼，头大，难顶
        LOGGER.info("得到授课编号{}", pno);
        //原因：前端参数名称写错了，要仔细啊

        ResultEntity resultEntity = resultRepository.findResultEntityByPnoAndSno(pno, StudentIDUtils.getStudentIDFromMap());
        if(resultEntity == null){
            throw new GlobalException(CodeMsg.RESULT_NOT_EXIST);
        }
//        Preconditions.checkArgument(resultEntity != null, "没有该条选课记录");

        //选课结果表删除掉该条选课记录
        resultRepository.delete(resultEntity);

        //授课计划余量加一
        Integer a = planRepository.increaseNumByPno(resultEntity.getPno());
        if(a == 0){
            throw new GlobalException(CodeMsg.PLAN_NUM_ERROR);
        }
//        Preconditions.checkArgument(a != 0, "余量不能大于容量");

        //选课内容发生变化，删除redis中旧有数据
        redisService.del("forResult:" + StudentIDUtils.getStudentIDFromMap());

        return new ResultDTO<>(CodeMsg.RESULT_NO_CHOOSE_SUCCESS.getMsg());
    }
}
