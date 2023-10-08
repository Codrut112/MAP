package factory;

import model.ComplexExpression;
import model.NumarComplex;
import model.Operation;
import model.operatii.ADDITION;
import model.operatii.DIVISION;
import model.operatii.MULTIPLICATION;
import model.operatii.SUBSTRACTION;

public class ExpressionFactory {
    /**
     * constructor privat
     */
    private ExpressionFactory() {}

    /**
     * fabrica pe care o vom folosi pentru a creea numerele (Singleton)
     */
    public static ExpressionFactory instance =new ExpressionFactory();

    /**
     *
     * @return unica instanta creata a clasei
     */
    public ExpressionFactory getInstance(){return instance;}

    /**
     *
     * @param operation
     * @param args
     * @return
     */
    /**
     * creeaza expresia (operatia+element)(Factory)
     * @param operation operatia expresiei date
     * @param args
     * @return
     */
    public ComplexExpression createExpression(Operation operation, NumarComplex[] args){
        switch (operation) {
            case ADDITION:return new ADDITION(args);
            case DIVISION:return new DIVISION(args);
            case MULTIPLICATION:return new MULTIPLICATION(args);
            case SUBSTRACTION:return new SUBSTRACTION(args);
            default: return null;
        }


    }

}
