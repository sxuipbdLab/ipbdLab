package ipl.restapi.controller;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 原之安
 * @date 2018/5/28 21:17
 */
public class Analog_landing {
    /**
     * 模拟登陆
     * @param dataUrl
     * @return
     */
    public static String ConnectTheNet(String dataUrl){
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
            }

            URL url = new URL(dataUrl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", tmpcookies.toString());
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setDoInput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
