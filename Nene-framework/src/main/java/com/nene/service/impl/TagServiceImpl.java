package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Tag;
import com.nene.service.TagService;
import com.nene.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Ayachi Nene
* @description 针对表【nene_tag】的数据库操作Service实现
* @createDate 2023-02-12 15:13:33
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult getTagList() {
        List<Tag> tags = this.lambdaQuery()
                .select(Tag::getId,
                        Tag::getName,
                        Tag::getRemark)
                .list();
        return ResponseResult.okResult(tags);
    }
}




