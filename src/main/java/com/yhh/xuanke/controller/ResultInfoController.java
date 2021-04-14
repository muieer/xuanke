package com.yhh.xuanke.controller;

import com.yhh.xuanke.dto.ListDTO;
import com.yhh.xuanke.entiy.ResultEntity;
import com.yhh.xuanke.service.ResultService;
import com.yhh.xuanke.utils.StudentIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/result")
public class ResultInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultInfoController.class);

    @Autowired
    private ResultService resultService;

    @GetMapping("/list")
    public String chooseResultList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                                   @RequestParam(value = "size", defaultValue = "6") Integer size) {

        //在这要根据学号查询选课结果，不然直接查表会拿到其他学生的选课结果
        Integer sno = StudentIDUtils.getStudentIDFromMap();
        LOGGER.info("取得学生学号 {}", sno);
        ListDTO<ResultEntity> page = resultService.getResultListPageBySno(pageNum, size, sno);
        model.addAttribute("Result", page);
        return "result";
    }

    @PostMapping("/noChoose")
    @ResponseBody
    public String noChoose(Integer pno) {

        resultService.noChoose(pno);
        return "1";
    }

}
