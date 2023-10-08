package model.operatii;

import model.ComplexExpression;
import model.NumarComplex;
import model.Operation;

public class DIVISION extends ComplexExpression {
    /**
     * constructor
     * @param numere vectorul de numere
     */
    public DIVISION(NumarComplex[] numere) {
        super(Operation.DIVISION, numere);
    }

    /**
     * executa operatie de scadere
     * @param a NumarComplex
     * @param b NumarComplex
     * @return rezultatul operatiei NumarComplex
     */
    @Override
    protected NumarComplex executeOneOperation(NumarComplex a, NumarComplex b) {
        return a.impartire(b);
    }
}
