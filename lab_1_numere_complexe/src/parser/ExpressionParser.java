package parser;

import exceptie.Exception;
import factory.ExpressionFactory;
import model.NumarComplex;
import model.Operation;
import model.ComplexExpression;
import model.operatii.ADDITION;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {

    /**
     * sirul de elemente citite din linia de comanda
     */
    private final String[] elements;

    /**
     * constructor
     * @param elements Expresia de numere complexe
     */
    public ExpressionParser(String[] elements) {
        this.elements = elements;
    }

    /**
     * verifica daca expresia are operatie
     * @return operatia(ADDITION,MULTIPLICATION,DIVISION,SUBSTRACTION)
     * @throws Exception arunca exceptie daca expresia nu are o operatie principala
     */
    private Operation ParseOperation() throws Exception {
        String op=elements[1];//prima operatie intre numerele complexe

        if(op.length()!=1 )throw new Exception("operatia invalida");//operatie invalida
        if(elements.length%2==0)throw new Exception("operatie in plus la final");// avem o operatie in plus la final
        //verfifica ca intre toate numerele exist aceeai operatie
        for(int i=1;i<elements.length;i+=2){
            if(!elements[i].contentEquals(op)) throw new Exception("nu avem o operatie principala");
        }

        switch (op.charAt(0)){
            case '+': return Operation.ADDITION;
            case '-': return Operation.SUBSTRACTION;
            case ':': return Operation.DIVISION;
            case '*': return Operation.MULTIPLICATION;
            default: throw new Exception("operatie invalida");
        }
    }
    private boolean ithasminus(String numar){
        return numar.contains("-");

    }
    private boolean isImaginary(String numar){
        return numar.contains("i");

    }

    /**
     * se foloseste de o expresi regulara pentru a verifiica ca stringul numarului este conform cerintei
     * @param numar String
     * @return  true daca este valid
     *           fals in caz contrar
     */
    private boolean validateNumar(String numar){
        String pattern= "^[-]?\\d*(\\.\\d+)?[+-]?(\\d*(\\.\\d+)?\\*)?(i)?$";
        String areCifre="^.*[0-9i]+.*$";
        Pattern r=Pattern.compile(pattern);
        Matcher matcher1=r.matcher(numar);
        r=Pattern.compile(areCifre);
        Matcher matcher2=r.matcher(numar);
        return matcher1.matches() && matcher2.matches();
    }
    //private int countSign(String numar,String semnn){
    //  int count=numar.split(semn)-1;
    //return count;

    //}

    /**
     * primeste stringul unui numar imaginar (ex: -2*i-> 2)
     * @param parteaImaginara String
     * @return double
     */
    private double prelucreParteaImaginare(String parteaImaginara){
        System.out.println(parteaImaginara+ " hopa");
        if(parteaImaginara.contentEquals("i") || parteaImaginara.contentEquals("-i"))parteaImaginara="1";
        parteaImaginara=parteaImaginara.replace("i","");//stergem i-ul
        parteaImaginara=parteaImaginara.replace("*","");//stergem *
        parteaImaginara=parteaImaginara.replace("-","");//stergem -
        return Double.parseDouble(parteaImaginara);//transformam in double
    }

    /**
     * prelucreaza stringul partii intregi (scapa de minus)
     * @param parteaReala String
     * @return double
     */
    private double prelucreazaParteaReala(String parteaReala){
        parteaReala=parteaReala.replace("-","");//stergem -
        return Double.parseDouble(parteaReala);//transformam in double
    }

    /**
     * primeste stringul unui numar verifica daca e valid si in acest caz creea un NumarComplex
     * @param numar String
     * @return NumarComplex
     * @throws Exception arunca exceptie daca stringul este invalid
     */
    private NumarComplex parseNumar(String numar) throws Exception {

        if (validateNumar(numar) == false) throw new Exception(numar+" invalid da");//verifica daca e valid
        boolean re_neg = false;
        boolean im_neg = false;
        double re=0;
        double im=0;
        String[] numere = numar.split("\\+");
        // + poate doar intre ele deci exista si parte imaginara si parte reala, iar partea imaginara e pozitiva
        if (numere.length == 2) {
            String parteaImaginara=numere[1];
            im=prelucreParteaImaginare(parteaImaginara);
            //prelucram partea reala
            String parteReala=numere[0];
            //verificam daca partea reala este negativa
            if(parteReala.charAt(0)=='-'){
                re_neg=true;
                parteReala=parteReala.substring(1);
            }
            re=Double.parseDouble(parteReala);
        }

        else {
            numere = numar.split("\\-");

            switch (numere.length){
                //nu exista + sau - in string deci contine doar parte reala sau imaginara
                //avem partea reala sau imaginara pozitiva (doar una exista)
                case 1:

                    if(numar.contains("i")){
                        im=prelucreParteaImaginare(numar);

                    }
                    else{

                        re=prelucreazaParteaReala(numar);

                    }
                    break;
                case 2:

                    //avem partea reala sau imaginara negativa (doar una exista)
                    if(numere[0].contentEquals("")){//daca in string avem minus in fata pe prima pozitie dupa split va fi un sting null


                        if(numar.contains("i")){
                            im=prelucreParteaImaginare(numar);
                            im_neg=true;
                        }
                        else{

                            re=prelucreazaParteaReala(numar);
                            re_neg=true;
                        }


                    }
                    else {
                        //partea reala pozitiva,partea imaginara negativa
                        String parteaReala = numere[0];
                        String parteaImaginara = numere[1];
                        im = prelucreParteaImaginare(parteaImaginara);
                        im_neg = true;
                        re = prelucreazaParteaReala(parteaReala);
                    }
                    break;
                //split imparte in 3 parit pentu ca sunt 2 minusuri, iar prima parte este un string vid
                default://ambele negative
                    String parteaReala = numere[1];
                    String parteaImaginara = numere[2];
                    im = prelucreParteaImaginare(parteaImaginara);
                    im_neg = true;
                    re = prelucreazaParteaReala(parteaReala);
                    re_neg=true;
                    break;
            }
        }

        if(re_neg)re=-re;
        if(im_neg)im=-im;


        return new NumarComplex(re,im);
    }

    /**
     * pparcurge expresia initiala
     * @return ComplexExpression
     * @throws Exception arunca exceptie daca expresia nu are o operatie principal sau daca un string nu este conform
     */
    public ComplexExpression parse() throws Exception {


        if(elements.length==0){
            NumarComplex[] dummy=new NumarComplex[1];
            dummy[0]=new NumarComplex(0,0);
            return new ADDITION(dummy);}

        if(elements.length==1) {

            NumarComplex[] dummy = new NumarComplex[1];

            dummy[0] = parseNumar(elements[0]);

            return new ADDITION(dummy);

        }
        //obtin operatia principala a expresie (exceptie altfel)
        Operation op=ParseOperation();
        //en expresi am n elemente (n+1)/2 numere si (n-1)/2 repetari ale operatie principale
        NumarComplex[] list=new NumarComplex[elements.length/2+1];
        for (int i=0;i<elements.length;i+=2)
        {
            list[i/2]=parseNumar(elements[i]);

        }
        return ExpressionFactory.instance.createExpression(op,list);
    }
}
