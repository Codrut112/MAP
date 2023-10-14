package taskuri;

import container.AbstractTaskRunner;
import container.TaskRunner;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner {
    public PrinterTaskRunner(TaskRunner tr) {
        super(tr);
    }

    @Override
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println("Task executat cu succes la data de " + LocalDateTime.now());
    }

}