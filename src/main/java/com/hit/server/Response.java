package main.java.com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Response<T> implements Serializable{

    private Map<String,String> header;
    private T body;

    public Response(Map<String,String> header, T body){
        this.header = header;
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
