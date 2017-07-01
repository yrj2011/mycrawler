package com.mycrawler.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mycrawler.common.domain.User;

/**
 * User 表数据库控制层接口
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("selectUserList")
    List<User> selectUserList(Pagination page,String state);
}
