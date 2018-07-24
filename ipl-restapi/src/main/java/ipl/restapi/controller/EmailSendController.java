package ipl.restapi.controller;

/**
 * <p> 关于邮件服务 </P>
 *
 * @author 黎佳能
 * @version V1.0
 * @package ipl.sso.controller
 * @date 2018/7/9 14:14
 * @since api1.0
 */

import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmailSendController {
    /**
     * 获得创建一封邮件的实例对象
     * @param
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String SendJavaMail(@RequestParam(value = "email") String email,
                               HttpServletRequest request){

        String number = null;
        try {
            for (int i = 0; i < 6; i++) {
                number = number + (int) (Math.random() * 10);
            }
            request.getSession().setAttribute("sessionCacheData", number);
            Properties props = new Properties();
            //指定邮件的发送服务器地址,改用qq
            props.put("mail.smtp.host", "smtp.qq.com");
            //服务器是否要验证用户的身份信息
            props.put("mail.smtp.auth", "true");

            //得到Session
            Session session = Session.getInstance(props);
            //代表启用debug模式，可以在控制台输出smtp协议应答的过程
            session.setDebug(true);

            //创建一个MimeMessage格式的邮件
            MimeMessage message = new MimeMessage(session);

            //设置发送者
            Address fromAddress = new InternetAddress("649568285@qq.com");
            //设置发送的邮件地址
            message.setFrom(fromAddress);
            //设置接收者  email为要接收邮件的邮箱
            Address toAddress = new InternetAddress(email);
            //设置接收者的地址
            message.setRecipient(RecipientType.TO, toAddress);

            //设置邮件的主题
            message.setSubject("您的验证码");
            //设置邮件的内容
            message.setText(number);
            //保存邮件
            message.saveChanges();

            //得到发送邮件的服务器(这里用的是smtp服务器)
            Transport transport = session.getTransport("smtp");

            //发送者的账号连接到smtp，用qq服务器
            transport.connect("smtp.qq.com", "649568285@qq.com", "nmecwkurhyzzbdag");
            //发送信息
            transport.sendMessage(message, message.getAllRecipients());
            //关闭服务器通道
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
            return JacksonUtil.bean2Json(ResultFormat.build("0", "邮箱发送验证码失败", 1, "sendEmail",null));
        }
        return JacksonUtil.bean2Json(ResultFormat.build("1", "已向邮箱发送验证码", 1, "sendEmail",null));
    }

    /**
     * 验证邮箱信息
     * @param number
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/TestMail", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"})
    @ResponseBody
    public String TestJavaMail(@RequestParam(value ="number") String number,HttpServletRequest request){

        String sessionCacheData = (String) request.getSession().getAttribute("sessionCacheData");
        request.getSession().removeAttribute("sessionCacheData");
        if (number.equals(sessionCacheData)){
            return JacksonUtil.bean2Json(ResultFormat.build("1", "验证码匹配成功", 1, "testnumber",null));
        }else{
            return JacksonUtil.bean2Json(ResultFormat.build("0","验证码匹配失败", 0, "testnumber",null));
        }
    }
}
