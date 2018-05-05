package server;

/*
TODO: make a wall to the vpn
TODO: w_url_list forbid url,w_ip_list forbid ip
TODO: w_redirect_list return helloworld
 */

import java.io.IOException;
import java.io.OutputStream;

public class Wall {
    static String[] w_url_list = {
            "jwts.hit.edu.cn",
    };
    static String[] w_ip_list = {
//        "127.0.0.1"
    };
    static String[] w_redirect_list = {
            "ito.hit.edu.cn/",
    };
    static String redirectURL = "http://yumendy.com";
    static String redirectHost = "yumendy.com";

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

    public static boolean redirect(Model requestInfo) throws IOException{
        for(String url:w_redirect_list){
            if(requestInfo.url.contains(url)){
                return false;
            }
        }
        return true;
    }
}
