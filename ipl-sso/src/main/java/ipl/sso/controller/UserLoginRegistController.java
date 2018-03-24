package ipl.sso.controller;

import ipl.common.utils.LabIplResultNorm;
import ipl.manager.pojo.UserInfo;
import ipl.sso.service.UserLoginRegistService;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserLoginRegistService userService;

    //用户登录[不允许GET方法]
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LabIplResultNorm userLogin(String username, String password,
                                      HttpServletRequest request, HttpServletResponse response) {
        try {
            LabIplResultNorm result = userService.userLogin(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString());
        }
    }

    //用户注册[不允许GET方法]
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public LabIplResultNorm createUser(UserInfo user) {
        try {
            LabIplResultNorm result = userService.createUser(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString());
        }
    }
}
