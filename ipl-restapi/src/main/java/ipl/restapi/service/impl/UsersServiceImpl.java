package ipl.restapi.service.impl;

import ipl.manager.mapper.UsersMapper;
import ipl.manager.pojo.Users;
import ipl.restapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.restapi.service.impl
 * @date 2018/3/16 21:13
 * @since api1.0
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UsersMapper usersMapper;

    @Override
    // 这里的itemId参数看起来是“死”的，实际在controller中，使用@RequestMapping以及@PathVariable“转活”
    public Users getUserById(long userId) {
//        UsersExample usersExample = new UsersExample();
        Users user = usersMapper.selectByPrimaryKey(userId);
        return user;
    }

}
