package packagedirectory.test;

import packagedirectory.test.Message;

public class Tasks {

    protected Message msg;
    protected String status;
    public static int count;
    protected String logo;

    public Tasks(Message msg) {
        this.msg = msg;
        this.status = "[x]";
        this.logo = "";
        count++;
    }

    public Message getMsg() {
        return msg;
    }

    public void added() {
        String output = Message.lines + "added: " + msg.getMsg() + "\n" + Message.lines;
        System.out.println(output);
    }

    public void done() {
        status = "[o]";
        Message output = new Message("Nice! I've finish this task:\n" + status + " " + msg.getMsg());
        System.out.println(output);
    }

    public void removed() {
        Message output = new Message("Noted. I've removed this task:\n" + status + " " + msg.getMsg());
        System.out.println(output);
    }

    @Override
    public String toString() {
        return status + " " + msg.getMsg() + "\n";
    }

}
