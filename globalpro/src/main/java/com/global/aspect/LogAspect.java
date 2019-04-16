package com.global.aspect;

import com.alibaba.fastjson.JSONObject;
import com.global.annotation.Log;
import com.global.api.entity.system.SysLog;
import com.global.api.entity.system.SysUser;
import com.global.api.service.system.ISysLogService;
import com.global.api.vo.CurrentUser;
import com.global.utils.ToolUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @Author yanghanjin
 * @Description:
 * @Date 2019/2/28
 */
@Aspect    //表示该类为一个切面类
@Component //@component组件扫描,让其 logService能注入进来
public class LogAspect {

    @Autowired
    private ISysLogService logService;

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //Controller层切点
    @Pointcut("execution(* com.global..*.*(..)) && @annotation(com.global.annotation.Log)")
    public void controllerAspect() {
    }

    /**
     * <p>Discription:[后置通知，扫描com.zhent包及此包下的所有带有@Log注解的方法]</p>
     * Created on 2017年11月24日 上午10:28:34
     *
     * @param joinPoint 前置参数
     * @author:[yhj]
     */
    @After(("execution(* com.global..*.*(..)) && @annotation(com.global.annotation.Log)"))
    public void doAfterAdvice(JoinPoint joinPoint) {
        doBefore(joinPoint);
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        SysUser user = CurrentUser.CurrentUser();
        //获取用户名
        String username = user == null ? "null" : user.getLoginName();
        //请求的IP
        try {
            SysLog log = new SysLog();
            log.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.setHttpMethod(request.getMethod());
            log.setUserName(username);
            //获取传入目标方法的参数
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];
                if (o instanceof ServletRequest || (o instanceof ServletResponse) || o instanceof MultipartFile) {
                    args[i] = o.toString();
                }
            }
            try {
                String str = JSONObject.toJSONString(args);
                log.setParams(str.length() > 5000 ? JSONObject.toJSONString("请求参数数据过长不与显示") : str);
            }catch (Exception e){
                //用户退出后清除sessionid,与shiro冲突,会出现sesstonid参数报错情况
            }
            String ip = ToolUtil.getClientIp(request);
            if ("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)) {
                ip = "127.0.0.1";
            }
            log.setIp(ip);
            log.setRequstUrl(request.getRequestURL().toString());
            if (session != null) {
                log.setSessionId(session.getId());
            }
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log mylog = method.getAnnotation(Log.class);
            if (mylog != null) {
                //注解上的描述
                log.setLogTitle(mylog.value());
            }
            if (user != null) {
                log.setCreatedBy(user.getLoginName());

            }
            log.setCreatedTime(new Date());
            logService.saveSysLog(log);
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("===================请求日志保存异常===================");
            e.printStackTrace();
        }
    }

}
