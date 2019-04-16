package com.global.api.controller.system;

import com.global.annotation.Log;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.global.utils.LayerData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import java.util.HashMap;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import com.global.api.vo.RequestModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.global.api.entity.system.SysRole;
import com.global.api.service.system.ISysRoleService;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Controller
@Api(tags = "SysRoleController", description = "系统角色表控制器")
@RequestMapping("/api/system/SysRole")
public class SysRoleController {

    @Autowired
    private ISysRoleService service;

    /***
     * 查询列表
     */
    @Log("查询系统角色表列表")
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    @ApiOperation(value = "查询列表", notes = "分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public LayerData<SysRole> list(@RequestBody @ApiParam(name = "model", required = true) RequestModel requestModel,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                     HttpServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<SysRole> LayerData = new LayerData<>();
        EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
        Page<SysRole> logPage = service.selectPage(new Page<>(page, limit), wrapper);
        LayerData.setCount(logPage.getTotal());
        LayerData.setData(logPage.getRecords());
        return  LayerData;
    }

    /***
     * 根据id查询
     */
    @Log("根据id查询系统角色表")
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParam(name = "id", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public SysRole get(@RequestBody @ApiParam(name = "model", required = true) RequestModel requestModel, String id){
       SysRole result = service.get(id);
       return result;
    }

    /***
     * 保存或修改
     */
    @Log("保存或修改系统角色表")
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改", notes = "保存或修改")
    @ResponseBody
    public Boolean saveSysRole(@RequestBody @ApiParam(name = "SysRole", required = true) RequestModel<SysRole> obj){
        service.saveSysRole(obj.getData());
        return true;
    }

    /***
     * 删除
     */
    @Log("删除系统角色表")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParam(name = "id", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Boolean delete(@RequestBody @ApiParam(name = "model", required = true) RequestModel requestModel, String id){
       service.delete(id);
       return true;
    }

}
