


import container.Strategy;
import container.TaskRunner;
import taskuri.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MessageTask messageTask = new MessageTask("1", "Seminar", "Done", "Noi", "Voi", LocalDateTime.now());
        messageTask.execute();

        MessageTask m1 = new MessageTask("1", "Seminar1", "Done1", "Noi", "Voi", LocalDateTime.now());
        MessageTask m2 = new MessageTask("2", "Seminar2", "Done2", "Noi", "Voi", LocalDateTime.now());
        MessageTask m3 = new MessageTask("3", "Seminar3", "Done3", "Noi", "Voi", LocalDateTime.now());
        MessageTask m4 = new MessageTask("4", "Seminar4", "Done4", "Noi", "Voi", LocalDateTime.now());
        MessageTask m5 = new MessageTask("5", "Seminar5", "Done5", "Noi", "Voi", LocalDateTime.now());

        List<Task> list = Arrays.asList(m1, m2, m3, m4, m5);
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.valueOf(args[0]));
        for (Task t:list){
            strategyTaskRunner.addTask(t);
        }
        strategyTaskRunner.executeAll();

        DelayTaskRunner delayTaskRunner=new DelayTaskRunner(strategyTaskRunner);
        for(Task t: list){
            delayTaskRunner.addTask(t);

        }
        delayTaskRunner.executeAll();
        TaskRunner printerTaskRunner=new PrinterTaskRunner(delayTaskRunner);
        for(Task t: list){
            printerTaskRunner.addTask(t);

        }
        printerTaskRunner.executeAll();



    }

}