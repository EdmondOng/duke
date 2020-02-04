package packagedirectory.test;

import packagedirectory.test.Tasks;
import packagedirectory.test.Message;
import java.util.ArrayList;

public class Folder {
    private ArrayList<Tasks> listTasks;

    Folder() {
        listTasks = new ArrayList<>();
    }

    public void add(Tasks tasks) {
        listTasks.add(tasks);
    }

    public void finishTasks(int i) {
        listTasks.get(i-1).done();
    }

    public void deleteTasks(int i) {
        listTasks.remove(i-1).removed();
    }

    public String getText() {
        int j = 1;
        String output = "";
        for(Tasks x: listTasks) {
            output = output + x.logo + "=" + x.status + "=" + x.msg.getMsg() + "\n";
            j++;
        }
        return output;
    }

    public void find(String word) {
        int i = 1;
        String output = Message.lines + "Here are the tasks in your list:\n";
        for(Tasks x: listTasks) {
            if(x.getMsg().getMsg().contains(word)) {
                output = output + i + ": " + x;
                i++;
            }
        }
        output += Message.lines;
        System.out.println(output);
    }

    public void show() {
        int i = 1;
        String output = Message.lines + "Here are the tasks in your list:\n";
        for(Tasks x: listTasks) {
            output += i + ": " + x;
            i++;
        }
        output += Message.lines;
        System.out.println(output);
    }
}
