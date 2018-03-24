package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.LabIplResultNorm;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.UserLoginRegistService;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class UserLoginRegistController {
    @Autowired
    private UserLoginRegistService userService;

    // 用户登录[不允许GET方法]
    @RequestMapping(value = "/v1/login", method = {RequestMethod.POST, RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String userLogin(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=============> 接受到：" + email + ", " + password);

        try {
            String result = userService.userLogin(email, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString(), false, null));
        }
    }

    // 用户注册[不允许GET方法]
    @RequestMapping(value = "/v1/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String createUser(UserInfo user) {
        System.out.println("接收到：\n" + JacksonUtil.bean2Json(user));

        try {
            String result = userService.createUser(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString(), false, null));
        }
    }
}
