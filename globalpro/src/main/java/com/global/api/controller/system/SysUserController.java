package com.global.api.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.global.api.entity.system.SysUser;
import com.global.api.service.system.ISysUserService;
import com.global.api.vo.RequestModel;
import com.global.api.vo.ResponseModel;
import com.global.utils.LayerData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yanghanjin123
 * @since 2019-04-16
 */
@Controller
@Api(tags = "SysUserController", description = "控制器")
@RequestMapping("/api/system/SysUser")
public class SysUserController {

    @Autowired
    private ISysUserService service;

    /***
     * 查询列表
     */
    @RequestMapping(value = "getlist", method = RequestMethod.POST)
    @ApiOperation(value = "查询列表", notes = "分页查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "limit", required = true, dataType = "String", paramType = "query"),
    })
    @ResponseBody
    public LayerData<SysUser> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                   HttpServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<SysUser> LayerData = new LayerData<>();
        EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
        Page<SysUser> logPage = service.selectPage(new Page<>(page, limit), wrapper);
        LayerData.setCount(logPage.getTotal());
        LayerData.setData(logPage.getRecords());
        return LayerData;
    }

    /***
     * 根据id查询
     */
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParam(name = "id", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public SysUser get(String id) {
        SysUser result = service.get(id);
        return result;
    }

    /***
     * 保存或修改
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "保存或修改", notes = "保存或修改")
    @ResponseBody
    public ResponseModel saveSysUser(@RequestBody @ApiParam(name = "SysUser", required = true) RequestModel<SysUser> obj) {
        ResponseModel responseModel = new ResponseModel();
        responseModel = service.saveSysUser(obj.getData());
        return responseModel;
    }

    /***
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParam(name = "id", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Boolean delete(String id) {
        service.delete(id);
        return true;
    }

}
