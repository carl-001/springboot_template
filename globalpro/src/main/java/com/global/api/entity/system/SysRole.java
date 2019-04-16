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
 * 系统角色表
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String roleId;
    /**
     * 角色名
     */
    @TableField("roleName")
    private String roleName;
    /**
     * 状态 0-已删除，1-可用
     */
    @TableField("status")
    private String status;
    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
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
    @TableField("updated_time")
    private Date updatedTime;


    public String getRoleId() {
        return roleId;
    }

    public SysRole setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public SysRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SysRole setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public SysRole setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SysRole setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public SysRole setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SysRole setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public SysRole setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public String toString() {
        return "SysRole{" +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", status=" + status +
        ", remarks=" + remarks +
        ", createdBy=" + createdBy +
        ", createdTime=" + createdTime +
        ", updatedBy=" + updatedBy +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
