package taskuri;

import container.Container;
import container.*;
import factory.TaskContainerFactory;

public class StrategyTaskRunner implements TaskRunner {

    private Container container;

    public StrategyTaskRunner(Strategy strategy){
        TaskContainerFactory factory=TaskContainerFactory.getInstance();
        container=factory.createContainer(strategy);
    }
    @Override
    public void executeOneTask() {
        if (!container.isEmpty())
            container.remove().execute();
    }



    @Override
    public void executeAll() {

        while(!container.isEmpty()){

            container.remove().execute();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}

