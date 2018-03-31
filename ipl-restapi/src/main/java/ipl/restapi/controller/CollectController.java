package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.Collect;
import ipl.restapi.service.CollectService;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("901","返回收藏信息失败",1,"collect",null));
        }
        //将角色信息变为JSON格式赋值给roleJson
        String data = JacksonUtil.bean2Json(collect);
        return JacksonUtil.bean2Json(ResultFormat.build("902","返回收藏信息成功",0,"collect",data));
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
        try{
            collectService.insertByuserId(coll);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("903","添加收藏失败",1,"collect",null));
        }
        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("904","添加收藏成功返回信息失败",1,"collect",null));
        }
        String data = JacksonUtil.bean2Json(collect);
        return JacksonUtil.bean2Json(ResultFormat.build("905","添加收藏成功返回信息成功",0,"collect",data));
    }

    @RequestMapping(value = "/collects/update/{userId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object updateByUserIdAndDocId(@PathVariable Long userId,@RequestParam(value = "docId") Long docId, @RequestParam(value = "description") String description) {
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
            return JacksonUtil.bean2Json(ResultFormat.build("906","更新收藏失败",1,"collect",null));
        }

        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("907","更新收藏成功返回信息失败",1,"collect",null));
        }
        String data = JacksonUtil.bean2Json(collect);
        return JacksonUtil.bean2Json(ResultFormat.build("908","更新收藏成功返回信息成功",0,"collect",data));
    }

    @RequestMapping(value = "/collects/delete/{userId}/{docId}", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object delByUserIdAndDocId(@PathVariable Long userId,@PathVariable Long docId){

        Collect coll = new Collect();
        coll.setUserId(userId);
        coll.setDocId(docId);

        try{
            collectService.delByPK(coll);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("909","删除收藏信息失败",1,"collect",null));
        }

        List<Collect> collect;
        try{
            collect = collectService.getAllCollect(userId);
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("910","删除收藏信息成功返回信息失败",1,"collect",null));
        }
        String data = JacksonUtil.bean2Json(collect);
        return JacksonUtil.bean2Json(ResultFormat.build("911","删除收藏信息成功返回信息成功",0,"collect",data));
    }

    @RequestMapping(value = "/getUrl", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public Object getUrl(@RequestParam(value = "Url")String Url){
        StringBuilder sb = null;
        String loginUrl = "http://172.21.201.131/search/user/login";
        String dataUrl = "http://172.21.201.131/search/pub/ApiSearch?dp=1&pn=10&fl=TI,PN&q=TI=" + Url;

        HttpClient httpClient = new HttpClient();

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod = new PostMethod(loginUrl);

        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = { new NameValuePair("name", "webmaster"), new NameValuePair("pwd", "dfld1234") };
        postMethod.setRequestBody(data);

        try {
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int statusCode = httpClient.executeMethod(postMethod);

            // 获得登陆后的 Cookie
            Cookie compCookie = new Cookie();
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();

            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = " + c.toString());
            }

            URL url = new URL(dataUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", tmpcookies.toString());
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }
}
