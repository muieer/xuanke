package com.yhh.xuanke.controller;

import com.yhh.xuanke.entiy.PlanEntity;
import com.yhh.xuanke.service.ChooseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping("/choose")
public class ChooseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseController.class);

    @Autowired
    private ChooseService chooseService;

    @GetMapping("/list")
    public String getPlanEntityList(Model model) {

        //todo 做分页的逻辑
        List<PlanEntity> planEntityList = chooseService.getPlanEntityList();
        model.addAttribute("plist", planEntityList);
        return "choose";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public String doChoose(Integer pno) {
        chooseService.doChoose(pno);
        return "1";
    }


}
