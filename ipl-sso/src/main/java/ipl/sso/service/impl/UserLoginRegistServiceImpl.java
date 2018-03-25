package ipl.sso.service.impl;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.UserLoginRegistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service.impl
 * @date 2018/3/22 19:51
 * @since api1.0
 */
@Service
public class UserLoginRegistServiceImpl implements UserLoginRegistService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserLoginRegistServiceImpl.class);

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;

    @Override
    public String userLogin(String email, String password, HttpServletRequest resuest, HttpServletResponse response) {
        // 此情况几乎不可能出现，所以没写入API接口文档
        if(email == null || password == null){
            LOGGER.info("缺少信息{}", email);
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，必须提供用户邮箱和密码", true, "login", null));
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andEmailEqualTo(email);

        List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
        if (list.size() != 1 ) {
            System.out.println("邮箱参数=======：" + email);
            LOGGER.info("没有邮箱为：{}的用户", email);
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，无此邮箱", true, "login", null));
        }
        UserInfo user = list.get(0);
        String requestPass = password.trim();
        // 接下来验证密码。md5算法。!DigestUtils.md5DigestAsHex(password.getBytes())
        if (!DigestUtils.md5DigestAsHex(requestPass.getBytes()).equals(user.getPassword())) {
            return JacksonUtil.bean2Json(ResultFormat.build("101", "登录失败，用户密码不正确", true, "login", null));
        }
        user.setLastLoginTime(user.getLoginTime());
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());*/
        user.setLoginTime(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        // 把用户对象中的密码清空。
        user.setPassword(null);
//        String token = SSOToken.createToken()
        return JacksonUtil.bean2Json(ResultFormat.build("100", "登录成功", false , "login", null));
    }

    @Override
    public String createUser(UserInfo user) {
        // 此情况几乎不可能出现，所以没写入API接口文档
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            return JacksonUtil.bean2Json(ResultFormat.build("400", "username,email,password没有完整提供", true, "create", null));
        }
//        设置注册时间
        user.setRegistTime(new Date());
//        设置本次登录时间
        user.setLoginTime(new Date());
//        设置上次登录时间
        user.setLastLoginTime(new Date());
//        设置登录次数
        user.setLoginCount(1);
        // md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            userInfoMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("107", "注册失败，请联系站长", true, "register", null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("106", "注册成功", false, "register", null));
    }
}
