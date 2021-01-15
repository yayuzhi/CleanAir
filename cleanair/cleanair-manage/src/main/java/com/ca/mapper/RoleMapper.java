package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import com.ca.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from qk_role limit #{page},#{limit} ")
    List<Role> findAllPage(@Param("page") int page, @Param("limit") int  limit);



    @Select("select count(*) from qk_role")
    int count();


    @Select("select * from qk_role where name like concat('%',#{name},'%') or name = null limit #{page},#{limit} ")
    List<Role>  findPageByName(@Param("name") String name,@Param("page") int page, @Param("limit") int  limit);



    @Select("select * from qk_role")
    List<Role>  findAllRole();
}
