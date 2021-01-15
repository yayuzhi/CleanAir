package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select * from qk_admin where name = #{name}")
    Admin selectByName(@Param("name") String name);


    @Select("select * from qk_admin limit #{page},#{limit}")
    List<Admin> findAdminByPage(@Param("page") int page, @Param("limit") int limit);

    @Select("select count(*) from qk_admin")
    int count();

    @Select("select * from qk_admin where role_id = #{roleId}")
    List<Admin> selectByRoleId(@Param("roleId") Integer roleId);


    @Select("select * from qk_admin where name like concat('%',#{name},'%') or name = null limit #{page},#{limit} ")
    List<Admin> findAdminByname(@Param("name") String name,@Param("page") int page, @Param("limit") int  limit);
}
