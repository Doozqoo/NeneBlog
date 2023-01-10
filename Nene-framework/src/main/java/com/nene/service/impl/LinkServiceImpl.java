package com.nene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nene.constants.SystemConstants;
import com.nene.domain.ResponseResult;
import com.nene.domain.entity.Link;
import com.nene.domain.vo.LinkVo;
import com.nene.mapper.LinkMapper;
import com.nene.service.LinkService;
import com.nene.utils.BeanCopyUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhish
 * @description 针对表【nene_link】的数据库操作Service实现
 * @createDate 2023-01-01 18:10:15
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
        implements LinkService {

    @Override
    public ResponseResult getAllLink() {

        List<Link> links = this.lambdaQuery()
                .select(Link::getId,
                        Link::getName,
                        Link::getLogo,
                        Link::getDescription,
                        Link::getUrl,
                        Link::getCreateBy)
                .eq(Link::getStatus, SystemConstants.LINK_STATUS_APPROVED)
                .list();

        List<LinkVo> linkVos = BeanCopyUtil.beanListCopy(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}




