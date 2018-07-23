package ipl.restapi.controller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
public class SessionGet {
    public Long getID(HttpServletRequest request){
        Long ID;
        HttpSession session1 =request.getSession();
        ServletContext Context = session1.getServletContext();
        ServletContext Context1= Context.getContext("/ipl-sso");
        HttpSession session2 =(HttpSession)Context1.getAttribute("session");
        try {
            ID = (Long) session2.getAttribute("sessionid");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return ID;


    }
}
