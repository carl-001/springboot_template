package com.global.api.service.system;

import com.global.api.entity.system.SysLog;
import com.baomidou.mybatisplus.service.IService;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.plugins.Page;
import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
public interface ISysLogService extends IService<SysLog> {

    boolean saveSysLog(SysLog item);

    SysLog get(@Param("id") String id);

    boolean delete(@Param("id") String id);

    List<SysLog> selectList();

    List<SysLog> findByPage(Map<String,String> map, Page<SysLog> page);

}
