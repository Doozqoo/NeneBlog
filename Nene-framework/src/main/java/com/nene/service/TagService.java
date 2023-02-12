package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ayachi Nene
* @description 针对表【nene_tag】的数据库操作Service
* @createDate 2023-02-12 15:13:33
*/
public interface TagService extends IService<Tag> {

    /**
     * 获取标签列表
     * @return
     */
    ResponseResult getTagList();
}
