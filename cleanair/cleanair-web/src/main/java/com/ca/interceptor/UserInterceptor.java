package com.ca.interceptor;

import com.ca.pojo.User;
import com.ca.util.ObjectMapperUtil;
import com.ca.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 参数介绍:
     *
     * @return Boolean  true 请求放行    false 请求拦截  一般配合重定向使用
     * @throws Exception 如果用户不登录则重定向到登录页面
     *                   <p>
     *                   需求:   如何判断用户是否登录?
     *                   依据:   1.根据cookie    2.判断redis
     * @param1 request  用户请求对象
     * @param2 response 服务器响应对象
     * @param3 handler  当前处理器本身
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("1");
        String ticket = null;
        //1.判断cookie中是否有记录
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("QK_TICKET".equals(cookie.getName())) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("2");
        //2.判断cookie数据是否有效
        if (!StringUtils.isEmpty(ticket)) {
            if (jedisCluster.exists(ticket)) {
                System.out.println("55555");
                String userJSON = jedisCluster.get(ticket);
                User user = ObjectMapperUtil.toObject(userJSON, User.class);
                //3.利用request对象进行数据的传递    request对象是最为常用的传递参数的媒介.
                request.setAttribute("QK_USER", user);
                UserThreadLocal.set(user);
                return true;    //表示用户已经登录.
            }
        }
        System.out.println("3");
//            //重定向到用户登录页面
//            //因为ajax的关系，这里我们需要告诉ajax 喂！我这个是重定向哦
//            response.setHeader("Access-Control-Expose-Headers", "REDIRECT,CONTEXTPATH");
//            response.setHeader("REDIRECT", "REDIRECT");
//            //告诉ajax重定向的url
//            String url = "/login";
//            response.setHeader("CONTEXTPATH", url);
//            response.getWriter().write(1);
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////        response.sendRedirect("/login.html");
//            return false;
        // 获取到项目名，以便下面进行重定向
        String homeUrl = request.getContextPath();
        // 如果是 ajax 请求，则设置 session 状态 、CONTEXTPATH 的路径值
        // 如果是ajax请求响应头会有，x-requested-with
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            response.setHeader("SESSIONSTATUS", "TIMEOUT");
            response.setHeader("CONTEXTPATH", "http://www.qk.com/login");
            // FORBIDDEN，forbidden。也就是禁止、403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            // 如果不是 ajax 请求，则直接跳转即可
            response.sendRedirect("http://www.qk.com/login");
        }
        return false;

    }


    /**
     * 为了满足业务需要将数据删除
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        request.removeAttribute("QK_USER");
        UserThreadLocal.remove();
    }
}
