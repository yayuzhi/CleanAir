package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ItemMapper extends BaseMapper<Item> {
    // 后台展现商品
    @Select("select * from qk_item limit #{page},#{limit} ")
    List<Item> findAllPage(@Param("page") int page, @Param("limit") int  limit);
    // 后台展现商品
    @Select("select count(*) from qk_item")
    int count();
    //后台展现
    @Select("SELECT COUNT(title) FROM qk_item WHERE title LIKE concat('%',#{title},'%') " )
    int Countbytitle(@Param("title") String title);
    //后台展现
    @Select("select * from qk_item where title like concat('%',#{title},'%') or title = null limit #{page},#{limit} ")
    List<Item> findAllpage(@Param("title") String title,@Param("page") int page, @Param("limit") int  limit);


    //前台展现
    @Select("select * from qk_item  where status = 1 limit #{page},#{limit}")
    List<Item> findAllPageweb(@Param("page") int page, @Param("limit") int  limit);
    @Select("select * from qk_item where (title like concat('%',#{title},'%') or title = null ) and status = 1 limit #{page},#{limit}")
    List<Item> findAllpageweb(@Param("title") String title,@Param("page") int page, @Param("limit") int  limit);

    @Select("SELECT COUNT(title) FROM qk_item WHERE title LIKE concat('%',#{title},'%') and status = 1" )
    int findBytitleCount(@Param("title") String title);

    @Select("select count(*) from qk_item where status = 1")
    int countweb();


//    @Delete("delete from qk_item where id = #{id}")
//    void deleteItemById(Integer id);
}
