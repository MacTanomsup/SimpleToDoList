import java.io.*;
import java.net.ConnectException;
import java.util.*;
import redis.clients.jedis.Jedis;

public class ToDoModel {

    int OPERATION_MODE;
    private List<String> mTaskLists;
    private String mListName;
    private final String taskFileName = "Data.txt";

    Jedis mJedis;

    /*
     *  This constructor uses for intializing model class
     *  @param  int            operation_mode
     *  @return -
     *  @throws -
     */
    public ToDoModel(int operation_mode) {
        mTaskLists = new ArrayList<String>();
        OPERATION_MODE = operation_mode;
        if (OPERATION_MODE == 0) {
            readFromFile(taskFileName);
        } else if (OPERATION_MODE == 1) {
            mJedis = new Jedis("localhost");
            mTaskLists = mJedis.lrange("todolist", 0, mJedis.llen("todolist"));
        } else {
            System.out.println("Invalid operation mode");
            System.out.println("Reset operation mode to store in file");
            OPERATION_MODE = 0;
            readFromFile(taskFileName);
        }
    }

    /*
     *  This function uses for adding To Do List
     *  @param  String       task
     *  @return -
     *  @throws -
     */
    public void addToList(String task) {
        this.mTaskLists.add(task);
        if (OPERATION_MODE == 0) {
            saveToFile(taskFileName);
        } else {
            saveToDatabase();
        }
    }

    /*
     *  This function uses for getting To Do List element
     *  @param  int       index
     *  @return String    To Do List element
     *  @throws -
     */
    public String getListElement(int index) {
        return this.mTaskLists.get(index);
    }

    /*
     *  This function uses for getting whole To Do List
     *  @param  -       
     *  @return List<String>  To Do List
     *  @throws -
     */
    public List<String> getWholeList() {
        return this.mTaskLists;
    }

    /*
     *  This function uses for deleting element in To Do List
     *  @param  int           listIndex
     *  @return -
     *  @throws -
     */
    public void deleteFromList(int listIndex) {
        this.mTaskLists.remove(listIndex);
        if (OPERATION_MODE == 0) {
            saveToFile(taskFileName);
        } else {
            saveToDatabase();
        }
    }

    /*
     *  This function uses for getting size in element
     *  @param  -
     *  @return int           listSize
     *  @throws -
     */
    public int getListSize() {
        return this.mTaskLists.size();
    }

    /*
     *  This function uses for saving data to database
     *  @param  -
     *  @return -
     *  @throws -
     */
    public void saveToDatabase() {
        mJedis.del("todolist");
        this.mTaskLists.forEach(taskName -> {
            mJedis.rpush("todolist", taskName);
        });
    }

    /*
     *  This function uses for saving data to file
     *  @param  String        taskFileName
     *  @return ERROR_STATUS  0 :Success
     *                        1 : No task
     *  @throws -
     */
    public int saveToFile(String taskFileName) {
        if (this.mTaskLists != null) {
            try {
                FileWriter fw = new FileWriter(taskFileName, false);
                this.mTaskLists.forEach(task -> {
                    try {
                        fw.write(task + "\n");
                    } catch (IOException eio) {
                        System.out.println("Cannot write to disk" + eio.toString());
                    }
                });
                fw.close();
            } catch (IOException eio) {
                System.out.println("Cannot write to disk" + eio.toString());
            }
            return 0;
        } else {
            System.out.println("No task to write");
            return 1;
        }
    }

    /*
     *  This function uses for reading data from file
     *  @param  String     taskFileName
     *  @return -
     *  @throws -
     */
    public void readFromFile(String taskFileName) {
        String stringReadFromFile;
        File file = new File(taskFileName);
        if (!(file.exists() && !file.isDirectory())) {
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(taskFileName));
            while ((stringReadFromFile = br.readLine()) != null) {
                this.mTaskLists.add(stringReadFromFile);
            }
            br.close();
        } catch (IOException eio) {
            System.out.println("Cannot read from disk" + eio.toString());
        }
    }

}
