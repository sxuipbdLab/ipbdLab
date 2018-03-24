package ipl.sso.service.impl;

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.LabIplResultNorm;
import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.UserValidatService;
import ipl.sso.service.enums.ValidateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service
 * @date 2018/3/19 22:26
 * @since api1.0
 */
@Service
public class UserValidatServiceImpl implements UserValidatService, Serializable {
    //    HashMap<String, Object> hashMap = new HashMap<>(4, 1);
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserValidatServiceImpl.class);

    @Override
    public String validateUserInfo(String validateValue, int type) {
        //创建查询条件
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        //对数据进行校验：1、2、3分别代表user_name、phone、email

        String msg = null;
        //用户名校验
        if (type == ValidateEnum.USER_NAME.getType()) {
            criteria.andUsernameEqualTo(validateValue);
            LOGGER.info("验证用户名===== {}", validateValue);
            msg = ValidateEnum.USER_NAME.getDesc();
        }
        /*else if (type == ValidateEnum.USER_PHONE.getType()){
            criteria.andPhoneEqualTo(validateValue);
            LOGGER.info("验证手机号=====" + validateValue);
            msg = ValidateEnum.USER_PHONE.getDesc();
        }*/
        else if (type == ValidateEnum.USER_EMAIL.getType()) {
            criteria.andEmailEqualTo(validateValue);
            LOGGER.info("验证邮箱=====" + validateValue);
            msg = ValidateEnum.USER_EMAIL.getDesc();
        }

        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            // 数据可用，返回"true"
            /*hashMap.put("验证数据：",validateValue);
            hashMap.put("tureOr"false"","true");*/
            return JacksonUtil.bean2Json(LabIplResultNorm.build("200",msg +validateValue + "可用",true,null));
        } else {
            LOGGER.info("出现405请求");
            // 数据不可用【405 (SC_METHOD_NOT_ALLOWED)指出请求方法(GET, POST, HEAD, PUT, DELETE, 等)对某些特定的资源不允许使用。该状态码是新加入 HTTP 1.1中的。】
            LabIplResultNorm labIplResultNorm = LabIplResultNorm.build("405", msg + validateValue + "已经存在", false, null);
            return JacksonUtil.bean2Json(labIplResultNorm);
        }
    }
}
