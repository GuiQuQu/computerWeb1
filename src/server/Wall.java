package server;


import java.io.IOException;
import java.io.OutputStream;

public class Wall {
    static String[] w_url_list = {
            "qq.com",
    };
    static String[] w_ip_list = {

    };
    static String[] w_redirect_list = {
            "qq.com"
    };
    static String redirect = "HTTP/1.1 200 OK\n Server: nginx\n Content-Type: text/html\n\n hello world";

    public static boolean forbid_url(Model requestInfo){
        for(String url:w_url_list){
            if(requestInfo.url.contains(url)){
                return false;
            }
        }
        return true;
    }

    public static boolean forbid_ip(Model requestInfo){
        for(String ip:w_ip_list){
            if(requestInfo.ip.contains(ip)){
                return false;
            }
        }
        return true;
    }

    public static boolean redirect(Model requestInfo, OutputStream osIn) throws IOException{
        for(String url:w_redirect_list){
            if(requestInfo.url.contains(url)){
                byte[]bytes = redirect.getBytes();
                osIn.write(bytes,0,bytes.length);
                osIn.flush();
                return false;
            }
        }
        return true;
    }
}
