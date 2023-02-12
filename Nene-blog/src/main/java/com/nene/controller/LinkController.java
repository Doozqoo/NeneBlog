package com.nene.controller;

import com.nene.annotation.ApiLog;
import com.nene.domain.ResponseResult;
import com.nene.service.LinkService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    @ApiLog(name = "友链列表")
    @GetMapping("/all")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
