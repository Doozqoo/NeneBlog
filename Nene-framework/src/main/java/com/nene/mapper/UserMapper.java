package com.nene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nene.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author zhish
 * @description 针对表【sys_user】的数据库操作Mapper
 * @createDate 2023-01-02 13:45:40
 * @Entity com.nene.domain.entity.User
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据账号查询验证数据
     *
     * @param account 账号名
     * @return 用户登录验证所需数据
     */
    public User selectOneByUserName(String account);

}




