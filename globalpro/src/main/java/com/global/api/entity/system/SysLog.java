package com.global.api.entity.system;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@TableName("sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.UUID)
    private String logId;
    /**
     * 日志标题
     */
    @TableField("logTitle")
    private String logTitle;
    /**
     * 操作IP地址
     */
    @TableField("ip")
    private String ip;
    /**
     * 操作用户昵称
     */
    @TableField("userName")
    private String userName;
    /**
     * 请求URI
     */
    @TableField("requstUrl")
    private String requstUrl;
    /**
     * 操作方式
     */
    @TableField("httpMethod")
    private String httpMethod;
    /**
     * 请求类型方法
     */
    @TableField("classMethod")
    private String classMethod;
    /**
     * SessionId
     */
    @TableField("sessionId")
    private String sessionId;
    /**
     * 返回内容
     */
    @TableField("response")
    private String response;
    /**
     * 方法执行时间
     */
    @TableField("useTime")
    private Integer useTime;
    /**
     * 浏览器信息
     */
    @TableField("browser")
    private String browser;
    /**
     * 地区
     */
    @TableField("area")
    private String area;
    /**
     * 省
     */
    @TableField("province")
    private String province;
    /**
     * 市
     */
    @TableField("city")
    private String city;
    /**
     * 参数
     */
    @TableField("params")
    private String params;
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField("ppdated_time")
    private Date ppdatedTime;


    public String getLogId() {
        return logId;
    }

    public SysLog setLogId(String logId) {
        this.logId = logId;
        return this;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public SysLog setLogTitle(String logTitle) {
        this.logTitle = logTitle;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public SysLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SysLog setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getRequstUrl() {
        return requstUrl;
    }

    public SysLog setRequstUrl(String requstUrl) {
        this.requstUrl = requstUrl;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public SysLog setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public SysLog setClassMethod(String classMethod) {
        this.classMethod = classMethod;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public SysLog setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public SysLog setResponse(String response) {
        this.response = response;
        return this;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public SysLog setUseTime(Integer useTime) {
        this.useTime = useTime;
        return this;
    }

    public String getBrowser() {
        return browser;
    }

    public SysLog setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    public String getArea() {
        return area;
    }

    public SysLog setArea(String area) {
        this.area = area;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public SysLog setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SysLog setCity(String city) {
        this.city = city;
        return this;
    }

    public String getParams() {
        return params;
    }

    public SysLog setParams(String params) {
        this.params = params;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SysLog setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public SysLog setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SysLog setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Date getPpdatedTime() {
        return ppdatedTime;
    }

    public SysLog setPpdatedTime(Date ppdatedTime) {
        this.ppdatedTime = ppdatedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.logId;
    }

    @Override
    public String toString() {
        return "SysLog{" +
        ", logId=" + logId +
        ", logTitle=" + logTitle +
        ", ip=" + ip +
        ", userName=" + userName +
        ", requstUrl=" + requstUrl +
        ", httpMethod=" + httpMethod +
        ", classMethod=" + classMethod +
        ", sessionId=" + sessionId +
        ", response=" + response +
        ", useTime=" + useTime +
        ", browser=" + browser +
        ", area=" + area +
        ", province=" + province +
        ", city=" + city +
        ", params=" + params +
        ", createdBy=" + createdBy +
        ", createdTime=" + createdTime +
        ", updatedBy=" + updatedBy +
        ", ppdatedTime=" + ppdatedTime +
        "}";
    }
}
