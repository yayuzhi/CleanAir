package com.ca.vo;

import com.alibaba.fastjson.JSON;
import com.ca.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

//@Data
//@Accessors(chain = true)
//@NoArgsConstructor
//@AllArgsConstructor
public class LayUITbale {
    public String LayUIResponseByitem(Integer count, List<Item> items) {
        String json= JSON.toJSONString(items);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByrole(Integer count, List<Role> roles) {
        String json= JSON.toJSONString(roles);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByMenu(Integer count, List<Menu> menus) {
        String json= JSON.toJSONString(menus);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByAdmin(Integer count, List<Admin> admins) {
        String json= JSON.toJSONString(admins);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByLog(Integer count, List<Log> logs) {
        String json= JSON.toJSONString(logs);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByOrder(Integer count, List<Order> orders) {
        String json= JSON.toJSONString(orders);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
    public String LayUIResponseByLeaveApply(Integer count, List<LeaveApply> leaveApplies) {
        String json= JSON.toJSONString(leaveApplies);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        return jso;
    }
}
