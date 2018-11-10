public class ToDoController {

    private ToDoModel toDoModel;
    private ToDoView toDoView;

    /*
     *  This constructor uses for initialzing controller class
     *  with model class and view class
     *  @param ToDoModel    toDoModel
     *         ToDoView     toDoView
     *  @return -
     *  @throws -
     */
    public ToDoController(ToDoModel toDoModel, ToDoView toDoView) {
        this.toDoModel = toDoModel;
        this.toDoView = toDoView;
    }

    /*
     *  This function uses for adding new To Do List
     *  with model class and view class
     *  @param String       newToDo
     *  @return -
     *  @throws -
     */
    public void addToDoList(String newToDo) {
        toDoModel.addToList(newToDo);
    }

    /*
     *  This function uses for printing To Do List(s)
     *  by calling view class
     *  @param  -
     *  @return ERROR_STATUS   0 : Success
     *                         -1: Failure
     *  @throws -
     */
    public int printToDoLists() {
        if (toDoModel.getListSize() == 0) {
            System.out.println("No any task");
            return -1;
        }

        for (int index = 0; index < toDoModel.getListSize(); index++) {
            toDoView.printToDoList(index, toDoModel.getListElement(index));
        }
        return 0;
    }

    /*
     *  This function uses for deleting To Do List
     *  @param  int            index
     *  @return ERROR_STATUS   0 : Success
     *                         -1: Index out of bound
     *                         1 : No task left
     *  @throws -
     */
    public int deleteToDoList(int index) {
        if (index < 0 || index > toDoModel.getListSize()) {
            System.out.println("Index is out of bound");
            return -1;
        }

        System.out.println(toDoModel.getListElement(index - 1) + " has been deleted");
        toDoModel.deleteFromList(index - 1);

        if (toDoModel.getListSize() == 0) {
            return 1;
        }

        return 0;
    }

}
