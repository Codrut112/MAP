package model.operatii;

import model.ComplexExpression;
import model.NumarComplex;
import model.Operation;

public class ADDITION extends ComplexExpression {
    /**
     * constructor
     * @param numere the vector of complex numbers
     */
    public ADDITION(NumarComplex[] numere) {
        super(Operation.ADDITION, numere);
    }

    /**
     * sum of two numbers
     * @param a NumarComplex
     * @param b NumarComplex
     * @return NumarComplex
     */
    @Override
    public NumarComplex executeOneOperation(NumarComplex a,NumarComplex  b){
        return a.adunare(b);
    }
}
