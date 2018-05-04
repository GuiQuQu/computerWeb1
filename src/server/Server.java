/*
TODO: main driven for server
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server = null;

    public Server(int port) throws IOException{
        this.server = new ServerSocket(port);
        while(true){
            Socket socket = null;
            try{
                socket = server.accept();
                new SockeThread(socket).start();
            }catch (Exception e){
                System.out.println("Error in Server!");
                System.out.println("Error."+e);
            }
        }
    }
    public void close() throws IOException{
        if(server!=null) server.close();
    }
}
