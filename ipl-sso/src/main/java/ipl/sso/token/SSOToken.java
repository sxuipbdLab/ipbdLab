package ipl.sso.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Descirption:登录时创建token</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.token
 * @date 2018/3/23 9:07
 * @since api1.0
 */
public class SSOToken {
    private static final String SECRET = "WH#$%(!)(#*!()!KL<55$6><MQLMNQNQJQK EiLCJuYW1lIjoif>?N<:{LWPW";

    /**
     * @param playLoadHelper 自定义的playload对象
     * @param    expirdate      token可用时间
     * @param <T>
     * @return
     */
    public static <T> String createToken(PlayLoadHelper playLoadHelper, long expirdate) {
        final Map<String, Object> helder = new HashMap<>(4, 1);
        helder.put("alg", "HS256");
        helder.put("typ", "JWT");
        try {
            Date date = new Date(expirdate);
            String token = JWT.create()
                    .withHeader(helder)
                    .withIssuer(playLoadHelper.getIss())
                    .withIssuedAt(playLoadHelper.getIat())
                    .withSubject(playLoadHelper.getSub())
                    .withClaim("username", playLoadHelper.getemail())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC256(SECRET));
            return token;
        } catch (Exception e) {
            return null;
        }
    }
}
