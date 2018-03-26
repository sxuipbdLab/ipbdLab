package ipl.sso.service.impl;

import ipl.manager.mapper.UserInfoMapper;
import ipl.manager.pojo.UserInfo;
import ipl.manager.pojo.UserInfoExample;
import ipl.sso.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.service.impl
 * @date 2018/3/26 16:40
 * @since api1.0
 */
@Service
public class MyInfoImpl implements MyInfoService{
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByEmail(String uemail) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andEmailEqualTo(uemail);
        List<UserInfo> list = userInfoMapper.selectByExample(userInfoExample);
        /*if (list == null || list.size() == 0) {
            return JacksonUtil.bean2Json(ResultFormat.build("103", "可用", true, "myinfo", null));
        }*/
        return list.get(0);
    }
}
