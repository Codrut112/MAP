package model.operatii;

import model.ComplexExpression;
import model.NumarComplex;
import model.Operation;

public class MULTIPLICATION extends ComplexExpression {
    /**
     * constructor
     * @param numere lista de numere complexe
     */
    public MULTIPLICATION(NumarComplex[] numere) {
        super(Operation.MULTIPLICATION, numere);
    }

    /**
     * executa inumltirea a doua numere complexe
     * @param a NumarComplex
     * @param b NumarComplex
     * @return rezultatul inmultirii un NumarComplex
     */
    @Override
    protected NumarComplex executeOneOperation(NumarComplex a, NumarComplex b) {
        return a.inmultire(b);
    }
}
