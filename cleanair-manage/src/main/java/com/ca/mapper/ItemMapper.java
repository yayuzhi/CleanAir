package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ItemMapper extends BaseMapper<Item> {
    /** 后台展现商品
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_item limit #{page},#{limit} ")
    List<Item> findAllPage(@Param("page") int page, @Param("limit") int  limit);

    /**后台展现商品
     * @return
     */
    @Select("select count(*) from qk_item")
    int count();

    /**后台展现
     * @param title
     * @return
     */
    @Select("SELECT COUNT(title) FROM qk_item WHERE title LIKE concat('%',#{title},'%') " )
    int Countbytitle(@Param("title") String title);

    /**后台展现
     * @param title
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_item where title like concat('%',#{title},'%') or title = null limit #{page},#{limit} ")
    List<Item> findAllpage(@Param("title") String title,@Param("page") int page, @Param("limit") int  limit);


    /**前台展现
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_item  where status = 1 limit #{page},#{limit}")
    List<Item> findAllPageweb(@Param("page") int page, @Param("limit") int  limit);

    /**
     * 前台展现
     * @param title
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_item where (title like concat('%',#{title},'%') or title = null ) and status = 1 limit #{page},#{limit}")
    List<Item> findAllpageweb(@Param("title") String title,@Param("page") int page, @Param("limit") int  limit);

    /**
     * 按照名称 item数量
     * @param title
     * @return
     */
    @Select("SELECT COUNT(title) FROM qk_item WHERE title LIKE concat('%',#{title},'%') and status = 1" )
    int findBytitleCount(@Param("title") String title);

    /**
     * 所有item的数量
     * @return
     */
    @Select("select count(*) from qk_item where status = 1")
    int countweb();

}
