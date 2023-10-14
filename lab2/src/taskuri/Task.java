package taskuri;



import java.util.Objects;

public abstract  class Task {
    private String taskId;
    public abstract void execute();

    private String descriere;

    public Task() {
        this.taskId = "";
        this.descriere = "";
    }

    public Task(String taskId, String descriere) {
        this.taskId = taskId;
        this.descriere = descriere;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", descriere='" + descriere + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(taskId, task.taskId) && Objects.equals(descriere, task.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, descriere);
    }


}
