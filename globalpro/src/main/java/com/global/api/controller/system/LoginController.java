package com.global.api.controller.system;

import com.github.pagehelper.StringUtil;
import com.global.api.entity.system.SysUser;
import com.global.api.enums.ResponseStatus;
import com.global.api.service.system.ISysUserService;
import com.global.api.vo.CurrentUser;
import com.global.api.vo.ResponseModel;
import com.global.utils.Base64Util;
import com.global.utils.RedisServiceUtils;
import com.global.utils.VerifyCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author yanghanjin
 * @Description: 登录控制器
 * @Date 2019/2/26
 */
@Controller
@Api(tags = "LoginController", description = "登录控制器")
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private RedisServiceUtils redisService;

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @ApiOperation(value = "用户登录", notes = "根据用户名和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", required = true, dataType = "String", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseModel login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) {
        ResponseModel responseModel = new ResponseModel();
        String loginToken = request.getParameter("login_token");
        if (StringUtils.isEmpty(loginToken)) {
            responseModel.setCode(400);
            responseModel.setMessage("请求异常，缺少token");
            return responseModel;
        }
        username = Base64Util.decode(username).replace(loginToken, "");
        password = Base64Util.decode(password).replace(loginToken, "");
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        HttpSession session = request.getSession();
        if (session == null) {
            responseModel.setCode(400);
            responseModel.setMessage("session超时");
            return responseModel;
        }
        /*String trueCode = (String) session.getAttribute(Constants.VALIDATE_CODE);*/
        String trueCode = (String) redisService.get("verifyCode_" + session.getId());
        if (StringUtils.isBlank(trueCode)) {
            responseModel.setCode(400);
            responseModel.setMessage("验证码超时");
            return responseModel;
        }
        if (StringUtil.isEmpty(code)) {
            responseModel.setCode(400);
            responseModel.setMessage("验证码不能为空");
            return responseModel;
        }
        if (!code.equals(trueCode)) {
            responseModel.setCode(400);
            responseModel.setMessage("验证码错误");
            return responseModel;
        }
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            responseModel.setCode(400);
            responseModel.setMessage("账号或密码错误");
            return responseModel;
        } catch (DisabledAccountException e) {
            responseModel.setCode(400);
            responseModel.setMessage("账号或密码错误");
            return responseModel;
        } catch (AuthenticationException e) {
            responseModel.setCode(400);
            responseModel.setMessage("账号或密码错误");
            return responseModel;
        } catch (Exception e) {
            responseModel.setCode(400);
            responseModel.setMessage("登陆异常");
            return responseModel;
        }
        String login_token = UUID.randomUUID().toString();
        SysUser user = CurrentUser.CurrentUser();
        user.setPassword(null);
        user.setToken(login_token);
        responseModel.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
        responseModel.setMessage(ResponseStatus.RESPONNSE_SUCCESS.getMessage());
        responseModel.setData(user);
        redisService.set("user-" + user.getUserId(), login_token, 10800L);
        return responseModel;
    }

    /**
     * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
     */
    @GetMapping("/genCaptcha")
    public void genCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
        //将验证码放到HttpSession里面
        HttpSession session = request.getSession();
        /*session.setAttribute(Constants.VALIDATE_CODE, verifyCode);*/
        redisService.set("verifyCode_" + session.getId(), verifyCode, 60l);
        //设置输出的内容的类型为JPEG图像
        response.setContentType("image/jpeg");
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 116, 36, 5, true, new Color(249, 205, 173), null, null);
        //写给浏览器
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
    }


    /**
     * 登出
     *
     * @param session
     * @return
     */
    @ApiOperation(value = "用户登出", notes = "用户登出")
    @ResponseBody
    @ApiImplicitParam(name = "requestModel", required = false, dataType = "String", paramType = "query")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseModel logout(String requestModel, HttpSession session) {
        ResponseModel responseModel = new ResponseModel();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        responseModel.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
        responseModel.setMessage(ResponseStatus.RESPONNSE_SUCCESS.getMessage());
        return responseModel;
    }

}
