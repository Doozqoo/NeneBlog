package com.nene.service;

import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhish
* @description 针对表【nene_link】的数据库操作Service
* @createDate 2023-01-01 18:10:15
*/
public interface LinkService extends IService<Link> {

    /**
     * 获取所有友链
     * @return 返回友链数据对象集合
     */
    ResponseResult getAllLink();
}
