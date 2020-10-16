package com.lnjhhhh.mapper;

import com.lnjhhhh.pojo.SysRole;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 21:00
 * @Version 1.0
 */

public interface RoleMapper {

    @Select("select * from sys_role where id in (select rid from sys_user_role where uid = #{uid})")
    @Results(id = "roleBaseMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleDesc", column = "role_desc")
    })
    List<SysRole> findById(Integer uid);

}
