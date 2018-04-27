/*
TODO: the model of the request
 */
package server;

public class Model {
    public String method;
    public String host;
    public int port = 80;
    public String protocol;
    public String cookies;
    public String url;
    public byte[] bytes;
    public int len;
    public String ip;
}
