package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.Collect;
import ipl.restapi.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/24 14:13
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class CollectController {

    @Autowired
    private CollectService collectService;

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/collects/userId", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object getALLCollect(HttpServletRequest request, HttpServletResponse response) {
        //返回所有角色信息
        Long userId = (Long) request.getSession().getAttribute("sessionid");
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","返回收藏失败,未登录",1,"collect",null));
        }
        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","返回收藏信息失败",1,"collect",null));
        }
        //将角色信息变为JSON格式赋值给roleJson
        return JacksonUtil.bean2Json(ResultFormat.build("1","返回收藏信息成功",0,"collect",collect));
    }

    @RequestMapping(value = "/collects/add/userId", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object insertByUserId(@RequestParam(value = "docId") Long docId, @RequestParam(value = "description") String description,HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("sessionid");
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","添加收藏失败,未登录",1,"collect",null));
        }
        Date collTime = new Date();
        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);
        coll.setCollTime(collTime);
        coll.setDescription(description);
        try{
            collectService.insertByuserId(coll);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","添加收藏失败",1,"collect",null));
        }
        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","添加收藏成功返回信息失败",1,"collect",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","添加收藏成功返回信息成功",0,"collect",collect));
    }

    @RequestMapping(value = "/collects/update/userId", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object updateByUserIdAndDocId(HttpServletRequest request,@RequestParam(value = "docId") Long docId, @RequestParam(value = "description") String description) {
        Long userId = (Long) request.getSession().getAttribute("sessionid");
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","更新收藏失败,未登录",1,"collect",null));
        }
        Date collTime = new Date();
        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);
        coll.setCollTime(collTime);
        coll.setDescription(description);

        try{
            collectService.updateByUserIdAndDocId(coll);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","更新收藏失败",1,"collect",null));
        }

        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","更新收藏成功返回信息失败",1,"collect",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","更新收藏成功返回信息成功",0,"collect",collect));
    }

    @RequestMapping(value = "/collects/delete/userId/{docId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delByUserIdAndDocId(HttpServletRequest request,@PathVariable Long docId){
        Long userId = (Long) request.getSession().getAttribute("sessionid");
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除收藏信息失败,未登录",1,"collect",null));
        }
        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);

        try{
            collectService.delByPK(coll);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除收藏信息失败",1,"collect",null));
        }

        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除收藏信息成功返回信息失败",1,"collect",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","删除收藏信息成功返回信息成功",0,"collect",collect));
    }

}