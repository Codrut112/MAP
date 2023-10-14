package container;

import taskuri.Task;

import java.util.ArrayList;
import java.util.List;

public class StackContainer extends Stack_Queue_Container implements Container {


    public StackContainer() {
        super();
    }


    public Task remove(){return list.removeLast();}
}
