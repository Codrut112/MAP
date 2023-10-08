package exceptie;

public class Exception extends Throwable{
    private String problema;
    public Exception(String problema){
        this.problema=problema;
    }

    public String getProblema() {
        return problema;
    }
}
