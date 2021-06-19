package com.muieer.xuanke.controller;

import com.muieer.xuanke.dto.ListDTO;
import com.muieer.xuanke.entiy.KaoShiEntity;
import com.muieer.xuanke.service.KaoShiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kaoshi")
public class KaoShiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KaoShiController.class);

    @Autowired
    private KaoShiService kaoShiService;

    @GetMapping("/list")
    public String getKaoShiList(Model model, @RequestParam(value = "pageNum", defaultValue = "0")Integer pageNum,
                                @RequestParam(value = "size", defaultValue = "6") Integer size) {

        ListDTO<KaoShiEntity> page = kaoShiService.getKaoShiEntityListPage(pageNum, size);

        model.addAttribute("Kaoshi", page);

        return "kaoshi";
    }
}
