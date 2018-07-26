package ipl.restapi.controller;

import ipl.common.utils.Get_Session;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.common.utils.StackTraceToString;
import ipl.manager.pojo.UserInfo;
import ipl.restapi.service.MyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * <p> 个人信息管理 </p>
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

    @Autowired
    private MyInfoService myInfoService;

    public static final Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);

    /**
     * 获取用户自身信息[不允许POST方法]
     * @param request
     * @return
     */
    @RequestMapping(value = "/myinfo", method = {GET,POST},
            produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String getMyInfo(HttpServletRequest request) {
        Get_Session getSession = new Get_Session();
        Long userId = getSession.getID(request);
        if (userId == null){
            return JacksonUtil.bean2Json(ResultFormat.build("0", "用户未登录", 1, "myinfo", null));
        }
        if (userId <= 10) {
            LOGGER.warn("id：{}不存在,用户虚假cookie请求“我的信息”，已拦截。", userId);
            return JacksonUtil.bean2Json(ResultFormat.build("0", "用户未登录,数据库中没有对应id的用户", 1, "myinfo", null));
        }
        try {
            UserInfo user = myInfoService.getUserInfoById(userId);
            user.setPassword("不可见");
            String dataString = JacksonUtil.bean2Json(user);
            return JacksonUtil.bean2Json(ResultFormat.build("1", "获取用户信息成功", 0, "myinfo", dataString));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("用户,{}获取自己信息失败========{}", userId, StackTraceToString.getStackTraceString(e));
            return JacksonUtil.bean2Json(ResultFormat.build("0", "获取用户信息失败，请联系站长", 1, "myinfo", null));
        }
    }
}
