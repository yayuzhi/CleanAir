package com.ca.service;

import com.ca.pojo.Log;

import java.util.List;

public interface LogService {
   List<Log> findLogByPage(int page, int limit);
   Integer count();

   Integer countbyname(String username);
   void savelog(Log log);

   void deleteLogById(Integer id);

   List<Log> findLogByName(String username,int page,int limit);
}
