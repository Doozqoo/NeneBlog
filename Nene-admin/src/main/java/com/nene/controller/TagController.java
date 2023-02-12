package com.nene.controller;

import com.nene.domain.ResponseResult;
import com.nene.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ayachi Nene
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/content/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/list")
    public ResponseResult tagList() {
        return tagService.getTagList();
    }
}
