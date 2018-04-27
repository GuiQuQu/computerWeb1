/*
TODO: make threads to request and response as osIN,isIn,osOut,isOut
TODO: override run for every thread
 */


package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SockeThread extends Thread{
    private Socket socketIn,socketOut;
    private InputStream isIn,isOut;
    private OutputStream osIn,osOut;
    private byte[] bytes = new byte[2048];

    public SockeThread(Socket socket){ this.socketIn = socket; }

    public void run() {
        try{
            System.out.println("one client is connected!");
            isIn = socketIn.getInputStream();
            osIn = socketIn.getOutputStream();

            int len;
            Model requestInfo = null;
            if((len = isIn.read(bytes))!=-1&&len>0){
                requestInfo = RequestFormate.formatter(bytes,len);
                requestInfo.ip = socketIn.getLocalAddress().getHostAddress();
            }

            if(Wall.redirect(requestInfo,osIn)){
                socketOut = new Socket(requestInfo != null ? requestInfo.host : null, requestInfo != null ? requestInfo.port : 0);
                isOut = socketOut.getInputStream();
                osOut = socketOut.getOutputStream();

                System.out.println(new String(requestInfo.bytes,0,requestInfo.len));
                osOut.write(requestInfo.bytes,0,requestInfo.len);
                osOut.flush();

                if(Wall.forbid_ip(requestInfo)&&Wall.forbid_url(requestInfo)){

                    SocketThreadOutput out = new SocketThreadOutput(isIn,osOut);
                    out.start();
                    SocketThreadInput in = new SocketThreadInput(isOut,osIn);
                    in.start();

                    out.join();
                    in.join();

                }
            }
        }catch (Exception e){
            System.out.println("client error "+e.getMessage());
        }finally {
            try{
                if(socketIn!=null){
                    socketIn.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

class SocketThreadInput extends Thread{
    private InputStream isOut;
    private OutputStream osIn;
    private byte[]buffer = new byte[1024];

    public SocketThreadInput(InputStream isOut,OutputStream osIn){
        this.isOut = isOut;
        this.osIn = osIn;
    }
    public void run(){
        try{
            int len;
            while((len=isOut.read(buffer)) != -1){
                if(len>0){
                    System.out.println(new String(buffer,0,len));
                    osIn.write(buffer,0,len);
                    osIn.flush();
                }
            }
        }catch (Exception e){
            System.out.println("SocketThreadInput leave");
        }
    }
}
class SocketThreadOutput extends Thread{
    private InputStream isIn;
    private OutputStream osOut;
    private byte[]buffer = new byte[1024];
    public SocketThreadOutput(InputStream isIn,OutputStream osOut){
        this.isIn = isIn;
        this.osOut = osOut;
    }

    public void run() {
        try{
            int len;
            while((len = isIn.read(buffer))!=-1){
                if(len>0){
                    System.out.println(new String(buffer,0,len));
                    osOut.write(buffer,0,len);
                    osOut.flush();
                }
            }
            byte[] bytes = "if-modified-since: abcs".getBytes();
            osOut.write(bytes,0,bytes.length);
            osOut.flush();
            System.out.println(new String(bytes,0,len));
        }catch (Exception e){
            System.out.println("SocketThreadOutput leave");
        }


    }
}
