package ipl.sso.controller;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.UserLoginRegistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @Autowired
    private UserInfoMapper userInfoMapper;

    // 用户登录[不允许GET方法]
    @RequestMapping(value = "/login", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
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
    @RequestMapping(value = "/register", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
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

    @RequestMapping(value = "/logout", method = {GET, POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String userLogout(HttpServletRequest request){
        try{
            HttpSession session = request.getSession();
            Long ID = (Long) session.getAttribute("sessionid");
            if (ID == null) {
                return JacksonUtil.bean2Json(ResultFormat.build("0", "注销登录失败，未登录", 1, "logout", null));
            }else {
                //注销用户，使session失效
                request.getSession().removeAttribute("sessionid");
                return JacksonUtil.bean2Json(ResultFormat.build("1", "注销登录成功", 1, "logout", null));
            }
        }catch(Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0", "注销登录失败", 0, "logout", null));
        }
    }

    @RequestMapping(value = "/resetPassword", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String resetPassword(@RequestParam("email") String email, @RequestParam("newpassword") String newpassword, @RequestParam("number") String number, HttpServletRequest request) throws Exception {
        //通过邮箱查找用户
        String sessionCacheData = (String) request.getSession().getAttribute("sessionCacheData");

        if (number.equals(sessionCacheData)) {
            request.getSession().removeAttribute("sessionCacheData");
            UserInfoExample userInfoExample = new UserInfoExample();
            UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
            criteria.andEmailEqualTo(email);

            List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
            //查找失败,无此用户
            if (list.size() == 0) {
                return JacksonUtil.bean2Json(ResultFormat.build("0", "重置密码失败，此邮箱未注册", 1, "resetPassword", null));
            }
            //查找成功，获得用户对象
            UserInfo user = list.get(0);
            //重置密码
            user.setRegistTime(new Date());
            //        设置本次登录时间
            user.setLoginTime(new Date());
            //        设置上次登录时间
            user.setLastLoginTime(new Date());
            //        设置登录次数
            user.setLoginCount(1);
            user.setIdentity((short) 1);
            // md5加密
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            try {
                userInfoMapper.updateByPrimaryKeySelective(user);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info(StackTraceToString.getStackTraceString(e));
                return JacksonUtil.bean2Json(ResultFormat.build("0", "重置密码失败，请联系站长", 1, "register", null));
            }
            return JacksonUtil.bean2Json(ResultFormat.build("1", "重置密码成功", 0, "register", null));
        }else{
            return  JacksonUtil.bean2Json(ResultFormat.build("0", "验证码输入错误", 0, "register", null));
        }

    }
}
