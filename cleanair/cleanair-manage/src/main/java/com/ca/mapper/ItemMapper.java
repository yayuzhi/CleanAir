package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ItemMapper extends BaseMapper<Item> {

    @Select("select * from qk_item limit #{page},#{limit} ")
    List<Item> findAllPage(@Param("page") int page, @Param("limit") int  limit);



    @Select("select count(*) from qk_item")
    int count();

    @Select("select * from qk_item where title like concat('%',#{title},'%') or title = null limit #{page},#{limit} ")
    List<Item> findAllpage(@Param("title") String title,@Param("page") int page, @Param("limit") int  limit);

//    @Delete("delete from qk_item where id = #{id}")
//    void deleteItemById(Integer id);
}
