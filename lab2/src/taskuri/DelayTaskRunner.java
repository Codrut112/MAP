package taskuri;

import container.AbstractTaskRunner;
import container.TaskRunner;

public class DelayTaskRunner extends AbstractTaskRunner {

    public DelayTaskRunner(TaskRunner tr) {
        super(tr);
    }

    @Override
    public void executeOneTask() {
        super.executeOneTask();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
