package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> Desciption：注销登录的实现</P>
 *
 * @author 乔晓斌
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/7/20 15:37
 * @since api1.0
 */

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserLogoutController{

    @RequestMapping(value = "/logout", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String userLogout(HttpServletRequest request){
        try{
            request.getSession().removeAttribute("sessionid");
            //注销用户，使session失效
            //request.getSession().invalidate();
            return JacksonUtil.bean2Json(ResultFormat.build("1","注销登录成功",1,"logout",null));
        }catch(Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0", "注销登录失败", 0, "logout", null));
        }
    }
}