package com.yhh.xuanke.service.impl;

import com.yhh.xuanke.common.CodeMsg;
import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.exception.GlobalException;
import com.yhh.xuanke.repository.PlanRepository;
import com.yhh.xuanke.repository.ResultRepository;
import com.yhh.xuanke.redis.RedisService;
import com.yhh.xuanke.service.ResultService;
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

        ListDTO<ResultEntity> listDTO = (ListDTO<ResultEntity>) redisService.getFromHash("forResultList::" + sno, sno + "-" + pageNum);

        if (listDTO != null) {
//            LOGGER.info("从redis中加载选课结果");
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
        redisService.setToHash("forResultList::" + sno, sno + "-" + pageNum, listDTO, 30, TimeUnit.MINUTES);
        LOGGER.info("从数据中加载选课结果，并将选课结果写入redis中");
        return listDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO<String> noChoose(Integer pno) {

        Integer sno = StudentIDUtils.getStudentIDFromMap();

        //为啥突然拿不到授课编号了，头疼，头大，难顶
//        LOGGER.info("得到授课编号{}", pno);
        //原因：前端参数名称写错了，要仔细啊

        ResultEntity resultEntity = findResultEntityByPnoAndSno(pno, sno);
        if(resultEntity == null){
            throw new GlobalException(CodeMsg.RESULT_NOT_EXIST);
        }

        //选课结果表删除掉该条选课记录
        resultRepository.delete(resultEntity);

        //授课计划余量加一
        Integer a = planRepository.increaseNumByPno(resultEntity.getPno());
        if(a == 0){
            throw new GlobalException(CodeMsg.PLAN_NUM_ERROR);
        }

        //redis中对应的课程余量加一
        redisService.hdecr("forPlanCount", String.valueOf(pno), -1);

        //删除选课记录中此条选课结果
        redisService.delFromHash("forResult" + "-" + sno, String.valueOf(pno));

        //选课内容发生变化，删除redis中旧有数据
        redisService.del("forResultList::" + sno);

        return new ResultDTO<>(CodeMsg.RESULT_NO_CHOOSE_SUCCESS.getMsg());
    }

    @Override
    public ResultEntity findResultEntityByPnoAndSno(Integer pno, Integer sno) {

        ResultEntity entity ;

        //从redis中得到是否有对应选课记录
        entity = (ResultEntity) redisService.getFromHash("forResult" + "-" + sno, String.valueOf(pno));
        if( entity!=null ) return entity;

        entity = resultRepository.findResultEntityByPnoAndSno(pno, sno);
        if(entity != null){
            redisService.setToHash("forResult" + "-" + sno, String.valueOf(pno), entity, 1, TimeUnit.DAYS);
        }
        return entity;
    }
}
