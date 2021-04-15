package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yayuzhi
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 按照用户名找到admin返回
     * @param name
     * @return
     */
    @Select("select * from qk_admin where name = #{name}")
    Admin selectByName(@Param("name") String name);

    /**
     * 分页查询admin信息
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_admin limit #{page},#{limit}")
    List<Admin> findAdminByPage(@Param("page") int page, @Param("limit") int limit);

    /**
     * 查询admin的数量
     * @return
     */
    @Select("select count(*) from qk_admin")
    int count();

    /**
     * name一样的admin的数量
     * @param name
     * @return
     */
    @Select("select count(*) from qk_admin  WHERE name LIKE concat('%',#{name},'%') ")
    int countbyname(@Param("name") String name);

    /**
     * 通过角色id返回amdins
     * @param roleId
     * @return
     */
    @Select("select * from qk_admin where role_id = #{roleId}")
    List<Admin> selectByRoleId(@Param("roleId") Integer roleId);


    /**
     * 分页搜索 name一样的admin
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_admin where name like concat('%',#{name},'%') or name = null limit #{page},#{limit} ")
    List<Admin> findAdminByname(@Param("name") String name,@Param("page") int page, @Param("limit") int  limit);
}
