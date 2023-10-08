package model;



public class NumarComplex {
    private double re;
    private double im;

    /**
     * create a ComplexNumber instance
     * @param re real part
     * @param im imaginary part
     */
    public NumarComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * default constructor
     */
    public NumarComplex(){}

    /**
     * add a number to the ComplexNumber
     * @param a ComplexNumber
     * @return ComplexNumber
     */
    public NumarComplex adunare(NumarComplex a){

        return new NumarComplex(re+a.re,im+a.im);

    }

    /**
     * substract a number from the instance number
     * @param a ComplexNumber
     * @return ComplexNumber
     */
    public NumarComplex scadere(NumarComplex a){

        return new NumarComplex(re-a.re,im-a.im);

    }

    /**
     * return the multiplication of the instance with a ComplexNumber
     * @param b NumarComplex
     * @return ComplexNumber
     */
    public NumarComplex inmultire(NumarComplex b){
        NumarComplex a=this;
        System.out.println(a.re);
        System.out.println(a.im);
        System.out.println(b.re);
        System.out.println(b.im);
        System.out.println("gata");
        NumarComplex rez=new NumarComplex();
        rez.re=a.re*b.re-a.im*b.im;
        rez.im=a.re*b.im+b.re*a.im;

        return rez;
    }

    /**
     * divide the number
     * @param y NumarComplex
     * @return ComplexNumber
     */
    public NumarComplex impartire(NumarComplex y){
        NumarComplex x=this;
        NumarComplex rez=new NumarComplex();
        double a,b,c,d;
        a=x.re;
        b=x.im;
        c=y.re;
        d=y.im;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        rez.re=(a*c+b*d)/(c*c+d*d);
        rez.im=(b*c-a*d)/(c*c+d*d);
        return rez;
    }

    /**
     * calculate the conjugation of the instance number
     * @return Complexnumber
     */
    public NumarComplex conjugation(){
        NumarComplex rez=new NumarComplex();
        rez.re=this.re;
        rez.im=-this.im;
        return rez;
    }

    /**
     * suprascrie metoda toString
     * @return String
     */
    @Override
    public String toString() {
        if(re==0 && im==0)return "0";
        if(re==0) return Double.toString(im)+"*i";
        if(im==0) return Double.toString(re);
        if(im>0)return Double.toString(re)+"+"+Double.toString(im)+"*i";
        else return Double.toString(re)+Double.toString(im)+"*i";
    }
}
