package com.global.api.service.system.impl;

import com.global.api.entity.system.SysRole;
import com.global.api.dao.system.SysRoleDao;
import com.global.api.service.system.ISysRoleService;
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
 * 系统角色表 服务实现类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements ISysRoleService {

     @Override
     public List<SysRole> selectList(){
        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        return baseMapper.selectList(wrapper);
     }

     @Override
     public List<SysRole> findByPage(Map<String,String> map, Page<SysRole> page) {
        return baseMapper.findByPage(map,page);
     }

     @Override
     public SysRole get(String id){
        return baseMapper.get(id);
     }

     @Override
     public boolean saveSysRole(SysRole item){
        insertOrUpdateAllColumn(item);
        return true;
     }

     @Override
     public boolean delete(String id){
        baseMapper.deleteById(id);
        return true;
     }

}
