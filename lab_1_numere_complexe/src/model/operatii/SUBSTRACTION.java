package model.operatii;

import model.ComplexExpression;
import model.NumarComplex;
import model.Operation;

public class SUBSTRACTION extends ComplexExpression {
    public SUBSTRACTION( NumarComplex[] numere) {
        super(Operation.SUBSTRACTION, numere);
    }

    /**
     * executa impartirea a doua numere complexe
     * @param a NumarComplex
     * @param b NumarComplex
     * @return NumarComplex
     */
    @Override
    protected NumarComplex executeOneOperation(NumarComplex a, NumarComplex b) {
        return a.scadere(b);
    }
}
