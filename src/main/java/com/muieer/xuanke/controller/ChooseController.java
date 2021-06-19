package com.muieer.xuanke.controller;

import com.muieer.xuanke.dto.ExposerDTO;
import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.dto.ResultDTO;
import com.muieer.xuanke.entiy.PlanEntity;
import com.muieer.xuanke.service.ChooseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getPlanEntityList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                                    @RequestParam(value = "size", defaultValue = "6") Integer size) {

        ListDTO<PlanEntity> listDTO = chooseService.getPlanEntityListPage(pageNum, size);
        model.addAttribute("planDto", listDTO);
        return "elective";
    }

    @PostMapping("/{pno}/exposer")
    @ResponseBody
    private ResultDTO<String> exposer(@PathVariable(value = "pno")Integer pno){

        try{
            ExposerDTO exposer = chooseService.exposer(pno);
            return new ResultDTO<>(exposer.getMd5());
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return new ResultDTO<>(-1, e.getMessage());
        }
    }

    //选课
    @PostMapping("/{pno}/{md5}/confirm")
    @ResponseBody
    public ResultDTO<String> doChoose(@PathVariable(value = "pno")Integer pno, @PathVariable(value = "md5")String md5) {

        try{
            return chooseService.doChoose(pno, md5);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return new ResultDTO<>(-1, e.getMessage());
        }

    }


}
