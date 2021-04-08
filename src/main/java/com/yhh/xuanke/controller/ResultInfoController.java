package com.yhh.xuanke.controller;

import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.service.ResultService;
import com.yhh.xuanke.utils.StudentIDUtils;
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
@RequestMapping("/result")
public class ResultInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultInfoController.class);

    @Autowired
    private ResultService resultService;

    @GetMapping("/list")
    public String chooseResultList(Model model) {
        //todo 做分页的逻辑
        //在这要根据学号查询选课结果，不然直接查表会拿到其他学生的选课结果
        Integer sno = StudentIDUtils.getStudentIDFromMap();
        LOGGER.info("取得学生学号 {}", sno);
        List<ResultEntity> resultList = resultService.getResultListBySno(sno);
        model.addAttribute("rList", resultList);
        return "choose_detail";
    }

    @PostMapping("/noChoose")
    @ResponseBody
    public String noChoose(Integer rno) {

        resultService.noChoose(rno);
        return "1";
    }

}
