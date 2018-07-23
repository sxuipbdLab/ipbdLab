package ipl.sso.controller;


import ipl.common.utils.StackTraceToString;
import ipl.manager.pojo.UserInfoExample;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import ipl.sso.service.impl.UserLoginRegistServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.*;
/**
 * <p> Desciption：重置登录密码的实现</P>
 *
 * @author 乔晓斌
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/7/18 19:12
 * @since api1.0
 */

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResetPasswordController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRegistServiceImpl.class);
    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
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