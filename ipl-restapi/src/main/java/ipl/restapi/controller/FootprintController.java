package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.manager.pojo.Footprint;
import ipl.manager.pojo.FootprintKey;
import ipl.restapi.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
 * @date 2018/3/24 15:41
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*",methods = {GET,POST})
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @RequestMapping(value = "/Footprint/{userId}", method = GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object getAllFootprint(@PathVariable Long userId){

        List<Footprint> footprint = footprintService.getAllFootprint(userId);
        String roleJson = JacksonUtil.bean2Json(footprint);
        return roleJson;
    }

    @RequestMapping(value = "/footprint/insertBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object insertFootprintByuserId(@PathVariable Long userId,@RequestParam(value = "searchContent") String searchContent){
        Date searchTime = new Date();

        Footprint footprint = new Footprint();
        footprint.setSearchContent(searchContent);
        footprint.setUserId(userId);
        footprint.setSearchTime(searchTime);

        if(10 > footprintService.getFootprintcount(userId)){
            try {
                footprintService.insertFootprintByUserId(footprint);
            } catch (Exception e) {
                footprintService.updateByPK(footprint);
            }
        }else{
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

        List<Footprint> footprints = footprintService.getAllFootprint(userId);
        String roleJson = JacksonUtil.bean2Json(footprints);
        return roleJson;
    }

    @RequestMapping(value = "/footprint/delAllBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delAllFootprintByuserId(@PathVariable Long userId){

        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);

        footprintService.delAllByUserId(footprintKey);

        List<Footprint> footprints = footprintService.getAllFootprint(userId);
        String roleJson = JacksonUtil.bean2Json(footprints);
        return roleJson;
    }

    @RequestMapping(value = "/footprint/delBy{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delFootprintByUserId(@PathVariable Long userId,@RequestParam(value = "searchContent") String searchContent){

        FootprintKey footprintKey = new FootprintKey();
        footprintKey.setUserId(userId);
        footprintKey.setSearchContent(searchContent);

        footprintService.delByUserIdAndContent(footprintKey);

        List<Footprint> footprints = footprintService.getAllFootprint(userId);
        String roleJson = JacksonUtil.bean2Json(footprints);
        return roleJson;
    }
}
