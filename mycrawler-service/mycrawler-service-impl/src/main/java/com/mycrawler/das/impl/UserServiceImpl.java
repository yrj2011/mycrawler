package com.mycrawler.das.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mycrawler.common.dao.UserMapper;
import com.mycrawler.common.domain.User;
import com.mycrawler.das.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements IUserService {

}
