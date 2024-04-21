package fileCSV;

import tasks.Task;

public class CSVformat {

    CSVformat(){

    }
    String head = "id,type,name,status,description,epic";
    String comma = ",";


    public String toString(Task task) {
        String strTask = "";
        System.out.println(task);
        return strTask;
    }
}
