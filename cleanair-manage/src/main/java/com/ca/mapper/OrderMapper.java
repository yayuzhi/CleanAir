package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import com.ca.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author yayuzhi
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_order limit #{page},#{limit} ")
    List<Order> findAllPage(@Param("page") int page, @Param("limit") int  limit);


    /**
     * 所有的order数量
     * @return
     */
    @Select("select count(*) from qk_order")
    int count();


    @Select("select count(*) from qk_order WHERE id LIKE concat('%',#{id},'%')")
    int countbyid(@Param("id") int id);

    /**
     * 返回 id like order 的信息
     * @param id
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from qk_order where id LIKE concat('%',#{id},'%') or id = null limit #{page},#{limit} ")
    List<Order> findOrderById(@Param("id") int id,@Param("page") int page, @Param("limit") int  limit);
}
