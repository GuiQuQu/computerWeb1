import server.Server;

import java.io.IOException;

public class main {

    public static void main(String[] args){
        Server server = null;
        try{
            server = new Server(8080);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
