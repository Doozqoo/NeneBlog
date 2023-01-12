package com.nene.mapper;

import com.nene.domain.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author zhish
* @description 针对表【nene_comment(评论数据表)】的数据库操作Mapper
* @createDate 2023-01-10 18:43:10
* @Entity com.nene.domain.entity.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}




