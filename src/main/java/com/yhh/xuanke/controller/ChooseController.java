package com.yhh.xuanke.controller;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.dto.ResultDTO;
import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.service.ChooseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/choose")
public class ChooseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseController.class);

    @Autowired
    private ChooseService chooseService;

    //得到选修课数据
    //默认从第0页开始，一页10条数据
    @GetMapping("/elective/list")
//    @ResponseBody
    public String getPlanEntityList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                                    @RequestParam(value = "size", defaultValue = "6") Integer size) {

//        List<PlanEntity> planEntityList = chooseService.getPlanEntityList();
        ListDTO<PlanEntity> listDTO = chooseService.getPlanEntityListPage(pageNum, size);
        model.addAttribute("planDto", listDTO);
        return "elective";
    }

    //选课
    @PostMapping("/confirm")
    @ResponseBody
    public ResultDTO<String> doChoose(Integer pno) {

        try{
            return chooseService.doChoose(pno);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return new ResultDTO<>(-1,e.getMessage());
        }

    }


}
