package ipl.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 王海
 * @package ipl.manager.controller
 * @description 页面跳转controller
 * @date 2018/3/14 22:10
 * @version V1.0
 */
// 标记为Spring MVC的控制器，处理HTTP请求。
@Controller
public class PageController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndexJsp(){
        // 前后缀字符串拼接（也可以使用ModelAndView对象）,返回test.jsp
        return "test";
    }

    /**
     * 展示其他页面
     * @param page 请求URL的page名
     * @return page名
     */
//    用于定义一个请求映射，value为请求的url，值为 / 说明，该请求首页请求，method用以指定该请求类型，一般为get和post
    @RequestMapping("/{page}")
//    @ResponseBody会报错，只会返回一个String到HTML的body而不会返回到url
    public String showPage(@PathVariable String page){
        return page;
    }

}

