package com.global.api.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.global.api.dao.system.SysUserDao;
import com.global.api.entity.system.SysUser;
import com.global.api.enums.ResponseStatus;
import com.global.api.service.system.ISysUserService;
import com.global.api.vo.ResponseModel;
import com.global.utils.CheckPasswordUtils;
import com.global.utils.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements ISysUserService {

    @Override
    public List<SysUser> selectList() {
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<SysUser> findByPage(Map<String, String> map, Page<SysUser> page) {
        return baseMapper.findByPage(map, page);
    }

    @Override
    public SysUser get(String id) {
        return baseMapper.get(id);
    }

    @Override
    public ResponseModel saveSysUser(SysUser item) {
        ResponseModel model = new ResponseModel();
        if (StringUtils.isEmpty(item.getUserId())) {
            //验证密码规则
           /* ResponseModel responseModel = CheckPasswordUtils.verifyPassword(item.getLoginName(), item.getPassword());
            if (responseModel.getCode() != 200) {
                model.setCode(responseModel.getCode());
                model.setMessage(responseModel.getMessage());
                return model;
            }*/
            String salt = PasswordUtils.generateSalt(20);
            item.setStatus(1);
            item.setPassword(PasswordUtils.encryptPassword("SHA-256", item.getPassword(), salt));
            item.setSalt(salt);
        }
        insertOrUpdateAllColumn(item);
        item.setPassword(null);
        model.setCode(ResponseStatus.RESPONNSE_SUCCESS.getCode());
        model.setMessage(ResponseStatus.RESPONNSE_SUCCESS.getMessage());
        model.setData(item);
        return model;
    }

    @Override
    public boolean delete(String id) {
        baseMapper.deleteById(id);
        return true;
    }

}
