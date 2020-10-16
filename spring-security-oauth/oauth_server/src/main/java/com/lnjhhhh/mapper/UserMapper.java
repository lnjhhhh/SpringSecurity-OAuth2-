package com.lnjhhhh.mapper;

import com.lnjhhhh.pojo.SysUser;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 20:56
 * @Version 1.0
 */

public interface UserMapper {

    @Select("select * from sys_user where username = #{username}")
    @Results(id = "userBaseMap",value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "status", column = "status"),
            @Result(property = "roleList",column = "id",
                many = @Many(select = "com.lnjhhhh.mapper.RoleMapper.findById",fetchType = FetchType.LAZY))
    })
    SysUser findByUsername(String username);

}
