package ipl.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
  * <p> Desciption </p>
  *
  * @author 原之安
  * @version v1.0
  * @date 2018/7/24 9:45
  * @package ipl.common.utils ipbdLab
  */

public class Get_Session {
    public Long getID(HttpServletRequest request){
        Long ID = (Long) request.getSession().getAttribute("sessionid");
        return ID;
    }
}