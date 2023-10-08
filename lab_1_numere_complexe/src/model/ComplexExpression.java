package model;



public abstract class ComplexExpression {
    public final Operation operatie;
    private final NumarComplex[] numere;

    /**
     * constuctor
     * @param operatie
     * @param numere
     */
    public ComplexExpression(Operation operatie, NumarComplex[] numere) {
        this.operatie = operatie;
        this.numere = numere;
    }

    /**
     * return the operation
     * @return
     */
    public Operation getOperatie() {
        return operatie;
    }

    /**
     *
     * @return the numbers of expression
     */
    public NumarComplex[] getNumere() {
        return numere;
    }

    /**
     *
     * @return the result of the expression
     */
    public final NumarComplex execute(){
        NumarComplex rez=null;
        for(NumarComplex numar :numere)
        {if(rez==null)rez=numar;
        else  rez=executeOneOperation(rez,numar);
        }
        return rez;

    };

    /**
     * make an operation between two numbers
     * @param a NumarComplex
     * @param b NumarComplex
     * @return NumarComplex
     */
    protected abstract NumarComplex executeOneOperation(NumarComplex a,NumarComplex b);

}
