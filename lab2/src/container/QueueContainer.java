package container;

import taskuri.Task;

import java.util.ArrayList;
import java.util.List;

public class QueueContainer extends Stack_Queue_Container implements Container{//stergerea difera fata de stack

    public QueueContainer() {
        super();
    }

    public Task remove(){return list.removeFirst();}
}
