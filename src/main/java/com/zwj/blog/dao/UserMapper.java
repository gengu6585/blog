package com.zwj.blog.dao;

import com.zwj.blog.model.Vo.User;
import com.zwj.blog.model.Vo.UserVoExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    long countByExample(UserVoExample example);

    int deleteByExample(UserVoExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserVoExample example);

    User selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserVoExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserVoExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}