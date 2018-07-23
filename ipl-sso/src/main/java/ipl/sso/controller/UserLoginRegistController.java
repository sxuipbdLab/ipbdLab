package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.UserLoginRegistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/3/23 16:02
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserLoginRegistController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRegistController.class);

    @Autowired
    private UserLoginRegistService userService;

    // 用户登录[不允许GET方法]
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String userLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            String result = userService.userLogin(email, password,request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(StackTraceToString.getStackTraceString(e));
            return JacksonUtil.bean2Json(ResultFormat.build("0", "服务器维护，请联系站长", 1, "login", null));
        }
    }

    // 用户注册[不允许GET方法]
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String createUser(UserInfo user, @RequestParam(value = "number")String number,HttpServletRequest request) {
//        System.out.println("接收到：\n" + JacksonUtil.bean2Json(user));
        String sessionCacheData = (String) request.getSession().getAttribute("sessionCacheData");

        if (number.equals(sessionCacheData)){
            //return JacksonUtil.bean2Json(ResultFormat.build("1", "验证码匹配成功", 1, "testnumber",null));
            try {
                request.getSession().removeAttribute("sessionCacheData");
                String result = userService.createUser(user);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return JacksonUtil.bean2Json(ResultFormat.build("0", "服务器维护，请联系站长", 1, "register", null));
            }
        }else{
            return JacksonUtil.bean2Json(ResultFormat.build("0","验证码匹配失败", 0, "testnumber",null));
        }

    }
}
