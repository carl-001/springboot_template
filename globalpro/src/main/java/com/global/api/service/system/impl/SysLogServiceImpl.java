package com.global.api.service.system.impl;

import com.global.api.entity.system.SysLog;
import com.global.api.dao.system.SysLogDao;
import com.global.api.service.system.ISysLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements ISysLogService {

     @Override
     public List<SysLog> selectList(){
        EntityWrapper<SysLog> wrapper = new EntityWrapper<>();
        return baseMapper.selectList(wrapper);
     }

     @Override
     public List<SysLog> findByPage(Map<String,String> map, Page<SysLog> page) {
        return baseMapper.findByPage(map,page);
     }

     @Override
     public SysLog get(String id){
        return baseMapper.get(id);
     }

     @Override
     public boolean saveSysLog(SysLog item){
        insertOrUpdateAllColumn(item);
        return true;
     }

     @Override
     public boolean delete(String id){
        baseMapper.deleteById(id);
        return true;
     }

}
