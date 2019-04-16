package com.global.api.vo;

import com.global.api.entity.system.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * @Author yanghanjin
 * @Description:
 * @Date 2019/2/26
 */
public class CurrentUser {

    /**
     * 取出Shiro中的当前用户UserId.
     */
    public static String id() {
        return CurrentUser().getUserId();
    }

    public static String loginName() {
        return CurrentUser().getLoginName();
    }

    public static String nickName() {
        return CurrentUser().getUserName();
    }

    public static SysUser CurrentUser() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

}
