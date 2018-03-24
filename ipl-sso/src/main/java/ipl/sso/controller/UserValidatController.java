package ipl.sso.controller;

import ipl.common.utils.LabIplResultNorm;
import ipl.sso.service.UserValidatService;
import ipl.sso.service.enums.ValidateEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/3/20 20:02
 * @since api1.0
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserValidatController {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserValidatController.class);
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserValidatService userValidatService;

    @RequestMapping("/v1/checkinfo")
    @ResponseBody
    public Object checkData(@RequestParam(value = "checkvalue") String param, @RequestParam(value = "type") Integer type) {
// @RequestParam 是从request里面拿取值，而 @PathVariable 是从一个URI模板里面来填充
        LabIplResultNorm result = null;

        //参数有效性校验
        if (StringUtils.isBlank(param)) {
            result = LabIplResultNorm.build("400", "校验内容不能为空");
        }
        if (type == null) {
            result = LabIplResultNorm.build("400", "校验内容类型不能为空");
        }
        // 去掉： type != ValidateEnum.USER_PHONE.getType() &&
        if (type != ValidateEnum.USER_NAME.getType() && type != ValidateEnum.USER_EMAIL.getType()) {
            result = LabIplResultNorm.build("400", "校验内容类型错误");
        }
        // 如果此时result中就已经有值，那么校验出错,不再调用服务。
        if (null != result) {
            return result;
        }
        // 调用服务
        try {
            result = userValidatService.validateUserInfo(param, type);

        } catch (Exception e) {
            e.printStackTrace();
            result = LabIplResultNorm.build("500", ExceptionUtil.unwrapThrowable(e).toString());
        }
        return result;
    }

}
