package com.global.api.dao.system;

import com.global.api.entity.system.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Repository
public interface SysUserDao extends BaseMapper<SysUser> {

    SysUser get(@Param("id") String id);

    List<SysUser> findByPage(@Param("map") Map<String,String> map,Page<SysUser> page);

    String getRole(@Param("loginName") String loginName);

}