package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LinkController
 * @Description 友链控制接口
 * @Author Protip
 * @Date 2023/1/1 18:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/all")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
