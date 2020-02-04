package packagedirectory.test;

import packagedirectory.test.Folder;
import packagedirectory.test.ToDos;
import packagedirectory.test.Message;
import packagedirectory.test.Deadlines;
import packagedirectory.test.Events;
import packagedirectory.test.DukeException;
import packagedirectory.test.Tasks;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ChatBox {
    private Folder folder;
    private boolean hasClosed;
    private String location;

    public ChatBox(String location) {
        this.folder = new Folder();
        this.hasClosed = true;
        this.location = location;
    }

    public void load() throws FileNotFoundException {
        File file = new File(location);
        Scanner data = new Scanner(file);
        while (data.hasNextLine()) {
            String[] ms = data.nextLine().split("=");
            String key = ms[0];
            String status = ms[1];
            Message message = new Message(ms[2]);
            if(key.equals("[T]")) {
                folder.add(new ToDos(message, status));
            } else if (key.equals("[E]")) {
                folder.add(new Events(message, status));
            } else if (key.equals("[D]")) {
                folder.add(new Deadlines(message, status));
            }
        }
    }

    public void save() throws IOException {
        FileWriter fw = new FileWriter(location);
        fw.write(folder.getText());
        fw.close();
    }

    public String reply(Message input) {
        String replyMsg = "";
        String[] msg = input.getMsg().split(" ");
        String key = msg[0];
        try {
            switch (key) {
            case "bye":
                replyMsg = Message.end();
                hasClosed = false;
                break;
            case "list":
                replyMsg = folder.show();
                break;
            case "done":
                int i = Integer.parseInt(msg[1]);
                replyMsg = folder.finishTasks(i);
                save();
                break;
            case "delete":
                int b = Integer.parseInt(msg[1]);
                replyMsg = folder.deleteTasks(b);
                save();
                break;
            case "find":
                replyMsg = folder.find(msg[1]);
                break;
            default:
                Tasks tasks;
                if (key.equals("todo")) {
                    String data = input.getMsg().split("todo ")[1];
                    tasks = new ToDos(new Message(data));
                } else if (key.equals("deadline")) {
                    String s1 = input.getMsg().split("deadline ")[1];
                    String s2 = s1.split("/by")[0];
                    String s3 = s1.split("/by")[1];
                    tasks = new Deadlines(new Message(s2 + "(by: " + s3 + ")"));
                } else if (key.equals("event")) {
                    String s1 = input.getMsg().split("event ")[1];
                    String s2 = s1.split("/at")[0];
                    String s3 = s1.split("/at")[1];
                    tasks = new Events(new Message(s2 + "(at: " + s3 + ")"));
                } else {
                    throw new IllegalArgumentException("wrong liao");
                }
                folder.add(tasks);
                replyMsg = tasks.added();
                save();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            String er = "OOPS!! The description of a " + key + " cannot be empty";
            System.out.println(new DukeException(er));
        } catch (IllegalArgumentException e) {
            String er = "OOPS!!! Dont understand what you are saying...";
            System.out.println(new DukeException(er));
        } catch (IOException e) {
            String er = "OOPS!!! No such directory to save the file...";
            System.out.println(new DukeException(er));
        }
        return replyMsg;
    }

    public void initialise() {
        try {
            Message.welcome();
            load();
            Scanner scan = new Scanner(System.in);
            while (hasClosed && scan.hasNextLine()) {
                Message input = new Message();
                String msg = scan.nextLine();
                input.add(msg);
                reply(input);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            String er = "OOPS!! History is not loaded correctly, check the file location...";
            System.out.println(new DukeException(er));
        }
    }
}