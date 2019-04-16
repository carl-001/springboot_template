package com.global.api.service.system;

import com.global.api.entity.system.SysUser;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;

import com.global.api.vo.ResponseModel;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.plugins.Page;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
public interface ISysUserService extends IService<SysUser> {

    ResponseModel saveSysUser(SysUser item);

    SysUser get(@Param("id") String id);

    boolean delete(@Param("id") String id);

    List<SysUser> selectList();

    List<SysUser> findByPage(Map<String,String> map, Page<SysUser> page);

}
