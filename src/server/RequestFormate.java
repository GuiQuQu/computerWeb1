package server;

public class RequestFormate {
    public static Model formatter(byte[] buffer,int len){
        String str = new String(buffer,0,len);
//        if(str.charAt(0) == 'C') str = "GET"+str.substring(7);

        String[] source = str.split("\\r\\n");

        Model requestInfo = new Model();

        String[] firstLine = source[0].split(" ");
        requestInfo.method = firstLine[0];
        requestInfo.protocol = firstLine[2];
        requestInfo.url = firstLine[1];

        for(String line:source){
            String[] map = line.split(":");
            switch(map[0]){
                case "Host":
                    int i = 0;
                    if(map[i+1].startsWith("http")) i++;
                    requestInfo.host = map[i+1].substring(1).replace("/","");
                    if(map.length>i+2) requestInfo.port = Integer.parseInt(map[i+2]);
                    break;
                case "Cookie":
                    requestInfo.cookies = map[1].substring(1);
            }
        }
        requestInfo.bytes = str.getBytes();
        requestInfo.len = requestInfo.bytes.length;
        return requestInfo;
    }
}
