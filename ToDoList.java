import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class ToDoList {

    /*
     * OPERATION_MODE 
     * 0 : Store in file 
     * 1 : Store in redis 
     * Caution -> Each operation
     * mode won't sync data
     */
    private static final int OPERATION_MODE = 1;

    /*
     *  This To Do List Program was designed following MVC Architecture
     *  This is a main function
     *  @param String[] argument(s)
     *  @return -
     *  @throws -
     */
    public static void main(String[] args) {

        String inputTask;
        int indexToDelete;

        ToDoModel toDoModel = new ToDoModel(OPERATION_MODE);
        ToDoView toDoView = new ToDoView();
        ToDoController toDoController = new ToDoController(toDoModel, toDoView);

        System.out.println("Start program");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter new task:");
            inputTask = scanner.nextLine();
            if (inputTask.equals("")) {
                break;
            }
            System.out.println("Task : " + inputTask + " added");
            toDoController.addToDoList(inputTask);
        }

        while (true) {

            if (toDoController.printToDoLists() != 0) {
                break;
            }

            System.out.print("Please enter task number to delete:");
            String indexStr = scanner.nextLine();

            if (indexStr.equals("")) {
                break;
            } else if (indexStr.equals("0")) {
                System.out.println("Index to delete must be greater than 0");
                continue;
            }

            try {
                indexToDelete = Integer.parseInt(indexStr);
                int returnStatus = toDoController.deleteToDoList(indexToDelete);
                if (returnStatus == -1) {
                    continue;
                } else if (returnStatus == 1) {
                    System.out.println("No task left");
                    break;
                }
            } catch (NumberFormatException numException) {
                System.out.println("Your input is not a number");
                continue;
            } catch (ArrayIndexOutOfBoundsException arrayException) {
                System.out.println("Index out of bound");
            }
        }

    }

}