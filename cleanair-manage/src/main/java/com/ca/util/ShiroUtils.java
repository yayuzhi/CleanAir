package com.ca.util;

import com.ca.pojo.Admin;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

    public static String getAdminname() {

        return getAdmin().getName();

    }

    public static Admin getAdmin() {

        return  (Admin) SecurityUtils.getSubject().getPrincipal();

    }
}
