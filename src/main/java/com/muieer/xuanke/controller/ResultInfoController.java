package com.muieer.xuanke.controller;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.dto.ResultDTO;
import com.muieer.xuanke.entiy.ResultEntity;
import com.muieer.xuanke.service.ResultService;
import com.muieer.xuanke.utils.StudentIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{pno}/noChoose")
    @ResponseBody
    public ResultDTO<String> noChoose(@PathVariable(name = "pno") Integer pno) {

        try{
            return resultService.noChoose(pno);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return new ResultDTO<>(-1, e.getMessage());
        }

    }

}
