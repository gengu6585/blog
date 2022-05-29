package com.zwj.blog.service.impl;

import com.zwj.blog.dao.UserMapper;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.UserService;
import com.zwj.blog.utils.Utils;
import com.zwj.blog.model.Vo.UserVoExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userDao;

    @Override
    @Transactional
    public Integer insertUser(User user) {
        Integer uid = null;
        if (StringUtils.isNotBlank(user.getUsername()) && StringUtils.isNotBlank(user.getEmail())) {
//            用户密码加密
            String encodePwd = Utils.MD5encode(user.getUsername() + user.getPassword());
            user.setPassword(encodePwd);
            userDao.insertSelective(user);
        }
        return user.getUid();
    }

    @Override
    public User queryUserById(Integer uid) {
        User user = null;
        if (uid != null) {
            user = userDao.selectByPrimaryKey(uid);
        }
        return user;
    }

    @Override
    public User login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new TipException("用户名和密码不能为空");
        }
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userDao.countByExample(example);
        if (count < 1) {
            throw new TipException("不存在该用户");
        }
        String pwd = Utils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<User> users = userDao.selectByExample(example);
        if (users.size() != 1) {
            throw new TipException("用户名或密码错误");
        }
        return users.get(0);
    }

    @Override
    @Transactional
    public void updateByUid(User user) {
        if (null == user || null == user.getUid()) {
            throw new TipException("userVo is null");
        }
        int i = userDao.updateByPrimaryKeySelective(user);
        if (i != 1) {
            throw new TipException("update user by uid and retrun is not one");
        }
    }
}
