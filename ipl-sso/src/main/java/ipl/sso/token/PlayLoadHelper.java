package ipl.sso.token;

import java.util.Date;

/**
 * <p>Descirption:Token加密的载荷工具类</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.token
 * @date 2018/3/23 10:19
 * @since api1.0
 */
public class PlayLoadHelper {
    /**
     * 发布者
     */
    private String iss;
    /**
     * 主题
     */
    private String sub;
    /**
     * email可以暴露
     */
    private String email;
    /**
     * 创建token的时间
     */
    private Date iat;

    public PlayLoadHelper() {
    }

    public PlayLoadHelper(String issuser, String subject, String email, Date createDate) {
        this.iss = issuser;
        this.sub = subject;
        this.email = email;
        this.iat = createDate;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
