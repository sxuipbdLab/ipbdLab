package ipl.restapi.controller;

import ipl.common.utils.JacksonUtil;
import ipl.manager.pojo.Users;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.restapi.controller
 * @date 2018/3/16 22:18
 * @since api1.0
 */
@Controller
public class UserController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/users/{userId}")
    @ResponseBody
    public String getUserJson(@PathVariable long userId) {
        Users user = usersService.getUserById(userId);
        //  取出user的所有属性，解析为前端所需要的json格式，返回给前端
        String userJson = JacksonUtil.bean2Json(user);
        return userJson;
    }

}

