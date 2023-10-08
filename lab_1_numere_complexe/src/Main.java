import exceptie.Exception;
import model.ComplexExpression;
import model.NumarComplex;
import parser.ExpressionParser;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        for (String arg : args){
            System.out.println(arg);

        }
        ExpressionParser parser=new ExpressionParser(args);
        try{
            //parcurg expresia si obtin numerele
            ComplexExpression expression =parser.parse();
            //execut operatia pe numere si obtin rezultatul
            NumarComplex result = expression.execute();
            System.out.println("rezultatul este "+result.toString());
        }catch (Exception e){
            System.out.println(e.getProblema());
        }


    }
}