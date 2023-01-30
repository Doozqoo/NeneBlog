package com.nene.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nene.domain.entity.User;
import com.nene.utils.ThreadLocalUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName CustomMetaObjectHandler
 * @Description 对象数据填充实处理器
 * @Author Protip
 * @Date 2023/1/12 22:34
 * @Version 1.0
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = ThreadLocalUtil.getUserId();
        userId = userId == null ? -1L : userId;
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateBy", Long.class, userId);
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = ThreadLocalUtil.getUserId();
        userId = userId == null ? -1L : userId;
        this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
