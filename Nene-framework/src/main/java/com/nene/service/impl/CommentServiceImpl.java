package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.dto.CommentDto;
import com.nene.domain.entity.Comment;
import com.nene.domain.entity.User;
import com.nene.domain.vo.CommentVo;
import com.nene.domain.vo.PageVo;
import com.nene.mapper.CommentMapper;
import com.nene.service.CommentService;
import com.nene.service.UserService;
import com.nene.utils.BeanCopyUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhish
 * @description 针对表【nene_comment(评论数据表)】的数据库操作Service实现
 * @createDate 2023-01-10 18:43:10
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    private static final int DEFAULT_PAGE_NUM = 1;

    private static final int DEFAULT_PAGE_SIZE = 3;

    private final UserService userService;

    @Override
    public ResponseResult getCommentPage(Long articleId, Integer pageNum, Integer pageSize) {

        // 分页查询当前文章的根评论
        Page<Comment> page = new Page<>(pageNum, pageSize);
        this.lambdaQuery()
                .select(Comment::getId,
                        Comment::getArticleId,
                        Comment::getRootId,
                        Comment::getContent,
                        Comment::getToCommentUserId,
                        Comment::getToCommentId,
                        Comment::getCreateBy,
                        Comment::getCreateTime)
                .eq(Comment::getRootId, SystemConstants.ROOT_COMMENT)
                .eq(Comment::getType, SystemConstants.COMMENT_TYPE_ARTICLE)
                .orderByAsc(Comment::getCreateTime)
                .page(page);

        // 将评论封装为vo
        List<CommentVo> commentVos = toCommentVo(page.getRecords());

        // 设置对应子评论
        setSubordinateComments(commentVos);

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult getSubordinateCommentPage(Long rootId, Integer pageNum, Integer pageSize) {

        // 查询子评论页
        Page<Comment> page = selectSubordinateCommentPage(rootId, pageNum, pageSize);

        // 将评论封装为vo
        List<CommentVo> commentVos = toCommentVo(page.getRecords());

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    @Override
    public ResponseResult publishComment(CommentDto commentDto) {
        Comment comment = BeanCopyUtil.beanCopy(commentDto, Comment.class);
        this.save(comment);
        return ResponseResult.okResult("评论发布成功！");
    }

    // ==================================================METHOD==================================================

    /**
     * 获取并设置根评论的子评论
     */
    private void setSubordinateComments(List<CommentVo> commentVos) {

        for (CommentVo commentVo : commentVos) {
            // 查询根评论id对应的子评论数据
            Page<Comment> page = selectSubordinateCommentPage(commentVo.getId(), DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
            // 将子评论封装为vo
            List<CommentVo> vos = toCommentVo(page.getRecords());
            // 将子评论数据设置到根评论属性中
            commentVo.setSubordinateComments(new PageVo(vos, page.getTotal()));
        }

    }

    /**
     * 查询子评论页
     */
    @NotNull
    private Page<Comment> selectSubordinateCommentPage(Long rootId, Integer pageNum, Integer pageSize) {
        // 根据根评论id分页查询当前子评论
        Page<Comment> page = new Page<>(pageNum, pageSize);
        this.lambdaQuery()
                .select(Comment::getId,
                        Comment::getArticleId,
                        Comment::getRootId,
                        Comment::getContent,
                        Comment::getToCommentUserId,
                        Comment::getToCommentId,
                        Comment::getCreateBy,
                        Comment::getCreateTime)
                .eq(Comment::getRootId, rootId)
                .orderByAsc(Comment::getCreateTime)
                .page(page);
        return page;
    }

    /**
     * 将comment封装到vo
     */
    private List<CommentVo> toCommentVo(List<Comment> comments) {

        if (CollectionUtils.isEmpty(comments)) {
            return null;
        }

        // 提取需要查询的用户id
        List<Long> userIds = Stream.concat(
                comments.stream().map(Comment::getToCommentUserId),
                comments.stream().map(Comment::getCreateBy)
        ).distinct().collect(Collectors.toList());

        // 调用接口查询所需用户信息
        List<User> users = userService.lambdaQuery()
                .select(User::getId,
                        User::getNickName,
                        User::getAvatar)
                .in(User::getId, userIds)
                .list();

        // 将查询到的用户数据根据用户id进行映射
        Map<Long, User> userIdToUserMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        // 根据用户id补全缺少的用户信息
        List<CommentVo> commentVos = BeanCopyUtil.beanListCopy(comments, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            if (userIdToUserMap.containsKey(commentVo.getCreateBy())) {
                User user = userIdToUserMap.get(commentVo.getCreateBy());
                commentVo.setUsername(user.getNickName());
                commentVo.setAvatar(user.getAvatar());
            }
            if (userIdToUserMap.containsKey(commentVo.getCreateBy())) {
                User user = userIdToUserMap.get(commentVo.getCreateBy());
                commentVo.setToCommentUsername(user.getNickName());
            }
        }

        return commentVos;
    }
}