package container;

import taskuri.Task;

public class AbstractTaskRunner implements TaskRunner{

    private TaskRunner taskRunner;
    public AbstractTaskRunner(TaskRunner tr) {
        taskRunner=tr;
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
    }

    @Override
    public void executeAll() {
        while (hasTask())
            executeOneTask();
    }

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return  taskRunner.hasTask();
    }
}
