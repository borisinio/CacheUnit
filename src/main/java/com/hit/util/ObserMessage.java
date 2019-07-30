package main.java.com.hit.util;

public class ObserMessage {

    private String sentIdentifier;
    private String messege;
    private String extra;

    public ObserMessage(String sentId, String msg) {
        this(sentId, msg, null);
    }

    public ObserMessage(String sentId, String msg, String extra)
    {
        this.sentIdentifier = sentId;
        this.messege = msg;
        this.extra = extra;
    }

    public String getSentIdentifier()
    {
        return sentIdentifier;
    }

    public String getMessege()
    {
        return messege;
    }

    public String getExtra() {
        return extra;
    }
}
