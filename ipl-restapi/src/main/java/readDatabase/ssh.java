package readDatabase;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package readDatabase
 * @date 2018/7/14 16:26
 * @since api1.0
 */
public class ssh {
    public static void main(String[] args) throws IOException, JSchException {
        // TODO Auto-generated method stub
        String host = "172.21.201.131";
        int port = 22;
        String user = "root";
        String password = "dfld2014";
        String dockey = "CN98126546@CN1115637C@20030723";
        String command = "/root/install/tool/dbclient -b meta -d " + dockey + " -p 8001 -H localhost -o VIEW -f PDF";
        System.out.println(command);
        String res = exeCommand(host,port,user,password,command);

        System.out.println(res);

    }


    public static String exeCommand(String host, int port, String user, String password, String command) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        //    java.util.Properties config = new java.util.Properties();
        //   config.put("StrictHostKeyChecking", "no");

        session.setPassword(password);
        session.connect();

        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        channelExec.setCommand(command);
        channelExec.setErrStream(System.err);
        channelExec.connect();
        String out = IOUtils.toString(in, "UTF-8");

        channelExec.disconnect();
        session.disconnect();

        return out;
    }
}
