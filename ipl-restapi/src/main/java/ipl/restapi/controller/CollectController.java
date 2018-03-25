package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.manager.pojo.Collect;
import ipl.restapi.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*",methods = {GET,POST})
public class CollectController {

    @Autowired
    private CollectService collectService;

    // 可以匹配多个value,produces属性避免乱码
    @RequestMapping(value = "/collects/{userId}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    // 用于将请求URL中的模板变量映射到功能处理方法的参数上，即取出uri模板中的变量作为参数
    public Object getALLCollect(@PathVariable Long userId) {
        //返回所有角色信息
        List<Collect> collect = collectService.getAllCollect(userId);

        //将角色信息变为JSON格式赋值给roleJson
        String roleJson = JacksonUtil.bean2Json(collect);
        return roleJson;
    }

    @RequestMapping(value = "/collects/add/{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object insertByUserId(@PathVariable Long userId, @RequestParam(value = "docId") Long docId, @RequestParam(value = "description") String description) {

        Date collTime = new Date();
        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);
        coll.setCollTime(collTime);
        coll.setDescription(description);

        collectService.insertByuserId(coll);

        List<Collect> collect = collectService.getAllCollect(userId);
        String roleJson = JacksonUtil.bean2Json(collect);
        return roleJson;
    }

    @RequestMapping(value = "/collects/update/{userId}/{docId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object updateByUserIdAndDocId(@PathVariable Long userId,@PathVariable Long docId, @RequestParam(value = "description") String description) {
        Date collTime = new Date();
        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);
        coll.setCollTime(collTime);
        coll.setDescription(description);

        collectService.updateByUserIdAndDocId(coll);

        List<Collect> collect = collectService.getAllCollect(userId);
        String roleJson = JacksonUtil.bean2Json(collect);
        return roleJson;
    }

    @RequestMapping(value = "/collects/delete/{userId}/{docId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delByUserIdAndDocId(@PathVariable Long userId,@PathVariable Long docId){

        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);

        collectService.delByPK(coll);

        List<Collect> collect = collectService.getAllCollect(userId);
        String roleJson = JacksonUtil.bean2Json(collect);
        return roleJson;
    }
}