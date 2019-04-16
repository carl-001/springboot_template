package com.global.api.dao.system;

import com.global.api.entity.system.SysLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Repository
public interface SysLogDao extends BaseMapper<SysLog> {

    SysLog get(@Param("id") String id);

    List<SysLog> findByPage(@Param("map") Map<String,String> map,Page<SysLog> page);

}