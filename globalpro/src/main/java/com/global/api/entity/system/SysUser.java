package com.global.api.entity.system;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>  用户
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String userId;
    @TableField("userName")
    private String userName;
    @TableField("loginName")
    private String loginName;
    @TableField("password")
    private String password;
    @TableField("salt")
    private String salt;
    @TableField("version")
    private Integer version;
    @TableField("created_by")
    private String createdBy;
    @TableField("created_time")
    private Date createdTime;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;
    @TableField("status")
    private Integer status;

    /**
     * 登录后传给前端
     */
    @TableField(exist = false)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public SysUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SysUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public SysUser setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public SysUser setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public SysUser setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SysUser setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public SysUser setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public SysUser setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SysUser setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SysUser setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                ", userId=" + userId +
                ", userName=" + userName +
                ", loginName=" + loginName +
                ", password=" + password +
                ", salt=" + salt +
                ", version=" + version +
                ", createdBy=" + createdBy +
                ", createdTime=" + createdTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", status=" + status +
                "}";
    }
}
