package com.yhh.xuanke.controller;

import com.yhh.xuanke.entiy.GradeEntity;
import com.yhh.xuanke.service.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/grade")
public class GradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @GetMapping("/list")
    public String getGradeList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                               @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Page<GradeEntity> page = gradeService.getGradeEntityListPage(pageNum, size);
        model.addAttribute("Grade", page);

        return "grade";
    }

}
