package ipl.sso.controller;

import ipl.common.token.JWTManager;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.MyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/3/26 16:51
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class MyInfoController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserValidatController.class);
    @Autowired
    private MyInfoService myInfoService;

    // 获取用户自身信息[不允许POST方法]
    @RequestMapping(value = "/myinfo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String getMyInfo(HttpServletRequest request){
        String token = request.getCookies().toString();

        System.out.println("request中返回的header中的token======="+token);
        if (token == null){
            return JacksonUtil.bean2Json(ResultFormat.build("109","用户未登录", true, "myinfo",null));
        }
        // 解析token，获取用户email
        String str = JWTManager.validateJWT(token);
        if (str.equals("ok")){
            try {
                String email = JWTManager.parseJWT(token).getSubject();
                UserInfo user = myInfoService.getUserInfoByEmail(email);
                String dataString =  JacksonUtil.bean2Json(user);
                return JacksonUtil.bean2Json(ResultFormat.build("102","获取用户信息成功", false, "myinfo", dataString));
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("用户,{}获取自己信息失败========");
                return JacksonUtil.bean2Json(ResultFormat.build("103","获取用户信息失败失败，请联系站长", true, "myinfo", null));
            }
        }
        // 如果str不是“ok”，说明token违法或者过期，直接返回封装后的错误信息
        return str;
    }
}
