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
     * username可以暴露
     */
    private String username;
    /**
     * 创建token的时间
     */
    private Date iat;

    public PlayLoadHelper() {
    }

    public PlayLoadHelper(String issuser, String subject, String username, Date createDate) {
        this.iss = issuser;
        this.sub = subject;
        this.username = username;
        this.iat = createDate;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
