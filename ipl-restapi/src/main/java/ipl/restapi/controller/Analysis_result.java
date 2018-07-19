package ipl.restapi.controller;

import com.alibaba.fastjson.JSONObject;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author 原之安
 * @date 2018/7/5 15:14
 */

@Controller
@CrossOrigin(origins = "*",methods = {GET,POST},maxAge=3600)
public class Analysis_result{
    Analog_landing analog_landing = new Analog_landing();

    /**
     * 专利分析
     * @param searchStr 分析专利项目
     * @param dp = 1 页码
     * @param pn = 10 每页显示结果数
     * @param field 分析关键词
     * @return
     */
    //这个接口用来做条件过滤
    @RequestMapping(value = "/Analysis", method = {GET, POST},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getUrl(@RequestParam() String searchStr,
                         @RequestParam(defaultValue = "1") String dp,
                         @RequestParam(defaultValue = "10")String pn,
                         @RequestParam() String field) {

        try {
            searchStr = URLEncoder.encode(searchStr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dataUrl = "http://172.21.201.131/search/pub/ApiAnalyse?sdf0=" + field + "&dp=" + dp + "&pn=" + pn + "&q=" + searchStr;

        JSONObject json = JSONObject.parseObject(analog_landing.ConnectTheNet(dataUrl));
        json.put("status",100);
        return json;
    }
    //这个接口用来做分析统计，由用户触发，需要登录
    @RequestMapping(value = "/AnalysisByUser", method = {GET, POST},
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public Object getUrlByUser(@RequestParam() String searchStr,
                         @RequestParam(defaultValue = "1") String dp,
                         @RequestParam(defaultValue = "10")String pn,
                         @RequestParam() String field,HttpServletRequest request) {

        Long userId = (Long) request.getSession().getAttribute("sessionid");
        if (userId==null){
            return JacksonUtil.bean2Json(ResultFormat.build("0","返回分析结果失败,未登录不能进行此项业务",1,"collect",null));
        }
        try {
            searchStr = URLEncoder.encode(searchStr,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dataUrl = "http://172.21.201.131/search/pub/ApiAnalyse?sdf0=" + field + "&dp=" + dp + "&pn=" + pn + "&q=" + searchStr;

        JSONObject json = JSONObject.parseObject(analog_landing.ConnectTheNet(dataUrl));
        json.put("status",100);
        return json;
    }
}
