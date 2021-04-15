package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import com.ca.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yayuzhi
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_role limit #{page},#{limit} ")
    List<Role> findAllPage(@Param("page") int page, @Param("limit") int  limit);

    /**
     * 分页查询
     * @return
     */
    @Select("select count(*) from qk_role")
    int count();

    /**
     * 搜索的分页查询
     * @param name
     * @return
     */
    @Select("select count(*) from qk_role where name like concat('%',#{name},'%')")
    int countbyname(@Param("name") String name);

    /**
     * 搜索的分页查询
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_role where name like concat('%',#{name},'%') or name = null limit #{page},#{limit} ")
    List<Role>  findPageByName(@Param("name") String name,@Param("page") int page, @Param("limit") int  limit);

    /**
     * 返回所有的role
     * @return
     */
    @Select("select * from qk_role")
    List<Role>  findAllRole();
}
