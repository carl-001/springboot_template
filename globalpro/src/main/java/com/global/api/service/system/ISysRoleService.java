package com.global.api.service.system;

import com.global.api.entity.system.SysRole;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.plugins.Page;
import java.util.Map;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
public interface ISysRoleService extends IService<SysRole> {

    boolean saveSysRole(SysRole item);

    SysRole get(@Param("id") String id);

    boolean delete(@Param("id") String id);

    List<SysRole> selectList();

    List<SysRole> findByPage(Map<String,String> map, Page<SysRole> page);

}
