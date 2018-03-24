package ipl.sso.service.impl;

import ipl.common.utils.LabIplResultNorm;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.UserLoginRegistService;
import org.apache.ibatis.reflection.ExceptionUtil;
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
    public LabIplResultNorm userLogin(String email, String password, HttpServletRequest resuest, HttpServletResponse response) {

        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andEmailEqualTo(email);

        List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
        if (list.size() != 1 || list == null) {
            LOGGER.info("没有用户，{}", email);
            return LabIplResultNorm.build("400", "查询数据库无此用户", "false");
        }
        UserInfo user = list.get(0);
        user.setLastLoginTime(user.getLoginTime());
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());*/
        user.setLoginTime(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        // 接下来验证密码。md5算法。
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            LOGGER.info("用户，{}，密码不正确", email);
            return LabIplResultNorm.build("400", "用户密码不正确", "false");
        }
        //保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);
        return LabIplResultNorm.ok("true");
    }

    @Override
    public LabIplResultNorm createUser(UserInfo user) {
//        设置注册时间
        user.setRegistTime(new Date());
//        设置本次登录时间
        user.setLoginTime(new Date());
//        设置上次登录时间
        user.setLastLoginTime(new Date());
//        设置登录次数
        user.setLoginCount(1);
        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try {
            userInfoMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString());
        }
        return LabIplResultNorm.ok("successful");
    }
}
