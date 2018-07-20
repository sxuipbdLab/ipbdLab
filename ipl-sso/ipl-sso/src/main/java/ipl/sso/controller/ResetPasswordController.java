package ipl.sso.controller;

import ipl.manager.pojo.UserInfoExample;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class ResetPasswordController{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String resetPassword(String email, String newpassword) throws  Exception{
        //通过邮箱查找用户
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andEmailEqualTo(email);

        List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
        //查找失败,无此用户
        if (list.size() != 1){
            return JacksonUtil.bean2Json(ResultFormat.build("0", "重置密码失败，无此邮箱", 1, "resetPassword", null));
        }
        //查找成功，获得用户对象
        UserInfo user = list.get(0);
        //重置密码
        user.setPassword(newpassword);
        //更新用户
        userInfoMapper.updateByPrimaryKeySelective(user);
        return JacksonUtil.bean2Json(ResultFormat.build("1", "重置密码成功", 0, "resetPassword", null));

    }
}
