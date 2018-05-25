package ipl.restapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.Result;
import ipl.common.utils.ResultObjectFromApi;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/31 14:26
 * @since api1.0
 */

@Controller
@CrossOrigin(origins = "*",methods = {GET,POST})
public class SearchApiController {

    public Object ConnectTheNet(String dataUrl){
        System.out.println(dataUrl);
        StringBuilder sb = null;

        String loginUrl = "http://172.21.201.131/search/user/login";

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
                System.out.println("line = ****   " + line);
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//
//        ResultObjectFromApi resultObjectFromApi = new ResultObjectFromApi();
//        String jsonStr = sb.toString();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            JsonNode node = mapper.readTree(jsonStr);
//            String resultJson = node.get("mesg").get("RESULT").toString();
//
//            //JsonNode resultNode = mapper.readTree(resultJson);
//            Result result = JacksonUtil.json2Bean(resultJson,Result.class);
//            resultObjectFromApi = ResultObjectFromApi.build(node.get("status").intValue(),node.get("FOUNDNUM").toString(),result);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return JacksonUtil.bean2Json(resultObjectFromApi);
        return sb;
    }

    @RequestMapping(value = "/getSearch", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Object getUrl(@RequestParam(value = "Url")String Url, @RequestParam(value = "dp") int dp){

        StringBuilder sb = null;
        // 登陆 Url
        try {
            Url = URLEncoder.encode(Url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String dataUrl = "http://172.21.201.131/search/pub/ApiSearch?dp=" + dp + "&pn=10&fl=TI,PN,AN,PD,AU,AD,LS,AB,PA&q=TI=" +Url;

        return ConnectTheNet(dataUrl);
    }

    @RequestMapping(value = "/getFullText", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Object getFullText(@RequestParam(value = "docPIN")String docPIN,@RequestParam(value = "docAN")String docAN,@RequestParam(value = "docPD") String docPD,@RequestParam(value = "mid") String mid){


        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?un=103&sid=103&fk=FT,TI&dk=[{\"DCK\":\""+docAN+"@"+docPIN+"@"+docPD+"\",\"MID\":\""+mid+"\"}]";
        System.out.println(dataUrl);
        return ConnectTheNet(dataUrl);
    }
}
