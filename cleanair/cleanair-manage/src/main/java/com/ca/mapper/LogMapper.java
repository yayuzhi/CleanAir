package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.Item;
import com.ca.pojo.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LogMapper extends BaseMapper<Log> {


    @Select("select * from qk_logs limit #{page},#{limit} ")
    List<Log> findAllPage(@Param("page") int page, @Param("limit") int  limit);;


    @Select("select count(*) from qk_logs")
    int count();

    @Select("select count(*) from qk_logs WHERE username like concat('%',#{username},'%') ")
    int countbyname(@Param("username") String username);


    @Select("select * from qk_logs where username like concat('%',#{username},'%') or username = null limit #{page},#{limit} ")
    List<Log> findLogByName(@Param("username") String username, @Param("page") int page, @Param("limit") int  limit);

}
