package com.yhh.xuanke.controller;

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

    //默认从第0页开始，一页10条数据
    @GetMapping("/list")
//    @ResponseBody
    public String getPlanEntityList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {

//        List<PlanEntity> planEntityList = chooseService.getPlanEntityList();
        Page<PlanEntity> page = chooseService.getPlanEntityListPage(pageNum, size);
        model.addAttribute("Plan", page);
        return "choose";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public String doChoose(Integer pno) {
        chooseService.doChoose(pno);
        return "1";
    }


}
