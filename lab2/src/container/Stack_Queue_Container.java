package container;

import taskuri.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class Stack_Queue_Container{
    List<Task> list;

    public Stack_Queue_Container() {
        this.list = new ArrayList<>();
    }

    public void add(Task t) {
        list.add(t);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
    public abstract Task remove();
}
