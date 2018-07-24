package ipl.restapi.controller;

import ipl.common.utils.Get_Session;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintKey;
import ipl.restapi.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> 用户足迹信息管理 </P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/24 15:41
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    /**
     * 获取用户所有足迹
     * @param request
     * @return
     */
    @RequestMapping(value = "/footprint/userId", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object getAllFootprint(HttpServletRequest request){
        Get_Session getSession = new Get_Session();
        Long userId = getSession.getID(request);
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","返回数据失败,未登录",1,"collect",null));
        }
        List<Footprint> footprint;
        try{
            footprint = footprintService.getAllFootprint(userId);
        }catch(Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","返回数据失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","返回数据成功",0,"footprint",footprint));
    }

    /**
     * 添加用户足迹
     * @param request
     * @param searchContent
     * @return
     */
    @RequestMapping(value = "/footprint/insertByuserId", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object insertFootprintByuserId(HttpServletRequest request,@RequestParam(value = "searchContent") String searchContent) {
        Get_Session getSession = new Get_Session();
        Long userId = getSession.getID(request);
        if (userId == null) {
            return JacksonUtil.bean2Json(ResultFormat.build("0", "返回收藏失败,未登录", 1, "collect", null));
        }
        Date searchTime = new Date();

        Footprint footprint = new Footprint();
        footprint.setSearchContent(searchContent);
        footprint.setUserId(userId);
        footprint.setSearchTime(searchTime);

        try {
            if (10 > footprintService.getFootprintcount(userId)) {
                try {
                    footprintService.insertFootprintByUserId(footprint);
                } catch (Exception e) {
                    footprintService.updateByPK(footprint);
                }
            } else {
                Footprint footprint2 = new Footprint();
                footprint2.setSearchTime(footprintService.getMinTime(userId));
                footprint2.setUserId(userId);
                footprintService.delFPByIDAndTime(footprint2);
                try {
                    footprintService.insertFootprintByUserId(footprint);
                } catch (Exception e) {
                    footprintService.updateByPK(footprint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0", "添加足迹失败", 1, "footprint", null));
        }


        List<Footprint> footprints;
        try {
            footprints = footprintService.getAllFootprint(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0", "添加足迹成功返回数据失败", 1, "footprint", null));
        }
        String data = JacksonUtil.bean2Json(footprints);
        return JacksonUtil.bean2Json(ResultFormat.build("1", "添加足迹成功返回数据成功", 0, "footprint", data));
    }

    /**
     * 删除所有用户足迹
     * @param request
     * @return
     */
    @RequestMapping(value = "/footprint/delAllByuserId", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delAllFootprintByuserId(HttpServletRequest request){
        Get_Session getSession = new Get_Session();
        Long userId = getSession.getID(request);
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","清除足迹失败,未登录",1,"collect",null));
        }
        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);

        try{
            footprintService.delAllByUserId(footprintKey);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","清除足迹失败",1,"footprint",null));
        }

        List<Footprint> footprints;
        try{
            footprints = footprintService.getAllFootprint(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","清除足迹成功返回信息失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","清除足迹成功返回信息成功",0,"footprint",footprints));
    }

    /**
     * 删除用户单挑足迹
     * @param request
     * @param searchContent
     * @return
     */
    @RequestMapping(value = "/footprint/delByuserId", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delFootprintByContent(HttpServletRequest request,@RequestParam(value = "searchContent") String searchContent){
        Get_Session getSession = new Get_Session();
        Long userId = getSession.getID(request);
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除单项足迹失败,未登录",1,"collect",null));
        }
        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);
        footprintKey.setSearchContent(searchContent);

        try{
            footprintService.delByUserIdAndContent(footprintKey);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除单项足迹失败",1,"footprint",null));
        }
        List<Footprint> footprints;
        try{
            footprints = footprintService.getAllFootprint(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0","删除单项足迹成功返回数据失败",1,"footprint",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1","删除单项足迹成功返回数据成功",0,"footprint",footprints));
    }
}
