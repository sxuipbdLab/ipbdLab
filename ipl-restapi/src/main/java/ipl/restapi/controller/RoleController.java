package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.manager.pojo.Role;
import ipl.manager.pojo.RoleSearchKey;
import ipl.restapi.service.RoleSearchService;
import ipl.restapi.service.RoleService;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/23 9:27
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST})
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleSearchService roleSearchService;

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/all", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object getALLRoleJson() {
        //返回所有角色信息
        List<Role> role = roleService.getAllRole();

        //将角色信息变为JSON格式赋值给roleJson
        String roleJson = JacksonUtil.bean2Json(role);
        return roleJson;
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/{roleId}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object getRoleByIdJson(@PathVariable Short roleId,String callback) {
        //得到角色信息
        Role role = roleService.getRoleById(roleId);

        String roleJson = JacksonUtil.bean2Json(role);
        return roleJson;
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/delete/{roleId}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object deleteRoleJson(@PathVariable Short roleId,String callback) {

        /**
         * if待删除的角色id等于最小的角色id则所有角色升一级
         * 否则所有角色降一级
         */
        if(roleId == roleService.getMinRoleId()){
            //所有用户升一级
            usersService.updateIndentityByIndentityUP(roleId);
        }else{
            //所有用户降一级
            usersService.updateIndentityByIndentityDOWN(roleId);
        }
        //删除role_search表中的对应
        roleSearchService.delByRoleId(roleId);

        //删除角色
        roleService.deleteRoleById(roleId);

        //显示删除后的所有角色列表
        List<Role> role = roleService.getAllRole();
        String roleJson = JacksonUtil.bean2Json(role);
        return roleJson;
    }

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/roles/add",
            method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    @ResponseBody
    public Object addRole(@RequestParam(value = "roleId") Short roleId, @RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleSearchCount") int roleSearchCount, @RequestParam(value = "index1") Short index1, @RequestParam(value = "index2") Short index2, @RequestParam(value = "index3") Short index3,@RequestParam(required = false) String callback){
        Short i1=1,i2=2,i3=3;
        Role role = new Role();
        RoleSearchKey roleSearchKey = new RoleSearchKey();
        role.setId(roleId);
        role.setName(roleName);
        role.setSearchCount(roleSearchCount);
        roleService.insertRole(role);
        if(index1 == i1){
            roleSearchKey.setSearchId(index1);
            roleSearchKey.setRoleId(roleId);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }
        if(index2 == i1){
            roleSearchKey.setSearchId(i2);
            roleSearchKey.setRoleId(roleId);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }
        if(index3 == i1){
            roleSearchKey.setSearchId(i3);
            roleSearchKey.setRoleId(roleId);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }

        //返回所有角色信息
        List<Role> roleArr = roleService.getAllRole();

        //将角色信息变为JSON格式赋值给roleJson
        String roleJson = JacksonUtil.bean2Json(roleArr);
        return roleJson;
    }

    @RequestMapping(value = "/roles/update",
            method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object updateRole(@RequestParam(value = "roleId") Short roleId, @RequestParam(value = "roleName") String roleName, @RequestParam(value = "roleSearchCount") int roleSearchCount, @RequestParam(value = "index1") Short index1, @RequestParam(value = "index2") Short index2, @RequestParam(value = "index3") Short index3,@RequestParam(required = false) String callback){

        Short i1 = 1,i2 = 2,i3 = 3;
        Role role = new Role();
        RoleSearchKey roleSearchKey = new RoleSearchKey();
        role.setId(roleId);
        role.setName(roleName);
        role.setSearchCount(roleSearchCount);
        roleService.updateRole(role);

        roleSearchKey.setRoleId(roleId);
        roleSearchService.delByRoleId(roleId);
        if(index1 == i1){
            roleSearchKey.setSearchId(i1);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }
        if(index2 == i1){
            roleSearchKey.setSearchId(i2);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }
        if(index3 == i1){
            roleSearchKey.setSearchId(i3);
            roleSearchService.insertRoleSearchKey(roleSearchKey);
        }
        //返回所有角色信息
        List<Role> roleArr = roleService.getAllRole();

        //将角色信息变为JSON格式赋值给roleJson
        String roleJson = JacksonUtil.bean2Json(roleArr);
        return roleJson;
    }
}
