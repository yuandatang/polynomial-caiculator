// Name: Yuanda Tang
// USC loginid:yuandata
// CS 455 PA2
// Fall 2016


import java.util.ArrayList;

/**
   A polynomial. Polynomials can be added together, evaluated, and
   converted to a string form for printing.

   Representation invariant:
   For a non-zero poly all the terms must be in decreasing order by exponent.
   No two terms have the same exponent.
   Internal representation is a simplified polynomial
   with no terms of zero coeff.
*/
public class Polynomial {
	ArrayList<Term> poly;     

    /**
       Creates the 0 polynomial
    */
    public Polynomial() {
    	poly=new ArrayList<Term>();
    	assert isValidPolynomial();
    }


    /**
       Creates polynomial with single term given
     */
    public Polynomial(Term term) {
    	poly=new ArrayList<Term>();
    	if(term.getCoeff()!=0){
    		poly.add(term);
    	}
    	assert isValidPolynomial();
    }


    /**
       Returns the Polynomial that is the sum of this polynomial and b
       (neither poly is modified)
     */
    public Polynomial add(Polynomial b) {
    	Polynomial sum=new Polynomial(); // sum of the two polynomials
    	int i=0;
    	int j=0;
    	while(i<poly.size() && j<b.poly.size()){
    		if(poly.get(i).getExpon()==b.poly.get(j).getExpon()){
    		    Term term=new Term(poly.get(i).getCoeff()+b.poly.get(j).getCoeff(),poly.get(i).getExpon());
    			if(poly.get(i).getCoeff()+b.poly.get(j).getCoeff()!=0){
    				sum.poly.add(term);
    			}
    			i++;
    			j++;
    		}
    		else if(poly.get(i).getExpon()>b.poly.get(j).getExpon()){
    	        sum.poly.add(poly.get(i));
    			i++;
    		}
    		else{
    			sum.poly.add(b.poly.get(j));
    			j++;
    		}
    	}
    	while(i<poly.size()){
			sum.poly.add(poly.get(i));
    		i++;
    	}
    	while(j<b.poly.size()){
			sum.poly.add(b.poly.get(j));
    		j++;
    	}
    	assert this.isValidPolynomial();
        assert b.isValidPolynomial();  
    	assert sum.isValidPolynomial();    
    	return sum;
    }


    /**
       Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
    	int i=0;
    	double sum=0;
    	while(i<poly.size()){
    		sum+=poly.get(i).getCoeff()*Math.pow(x,poly.get(i).getExpon());
    		i++;
    	}
    	assert isValidPolynomial();
    	return sum;
    }


    /**
       Return a String version of the polynomial with the 
       following format, shown by example:
       zero poly:   "0.0"
       1-term poly: "3.2x^2"
       4-term poly: "3.0x^5 + -x^2 + x + -7.9"
       
       Representation invariants:
       Polynomial is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
    	int i=0;
    	String simPoly=new String();
    	if(poly.size()==0){
    		simPoly="0.0";
    	}
    	else{
    		while(i<poly.size()){
    		    String xExpon="x^"+poly.get(i).getExpon();
    			String xCoeff=Double.toString(poly.get(i).getCoeff());
    			String plus="+";
    			if(poly.get(i).getExpon()==0){           //+3
    				xExpon="";
    			}
    			if(i==poly.size()-1){                    //if it is the last term, do not include "+"
    				plus="";
    			}
    			if(poly.get(i).getCoeff()==1 && poly.get(i).getExpon()!=0){           //+x^7
    				xCoeff="";
    			}
    			if(poly.get(i).getCoeff()==-1 && poly.get(i).getExpon()!=0){          //-x^7
    				xCoeff="-";
    			}
    			if(poly.get(i).getExpon()==1){           //+3x
    				xExpon="x";
    			}
    		    simPoly+=xCoeff+xExpon+plus;
    		    i++;    			
    		}
    	}
    	assert isValidPolynomial();
	    return simPoly;
    }


    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
    */
    private boolean isValidPolynomial() {
    	if(poly.size()==0){
    		return true;
    	}
    	if(poly.get(poly.size()-1).getCoeff()==0){
    		return false;
    	}
    	for(int i=0;i+1<poly.size();i++){
    		if(poly.get(i+1).getExpon()-poly.get(i).getExpon()>=0){
    			return false;
    		}
    		if(poly.get(i).getCoeff()==0){    //no terms where the coefficient is zero
    			return false;
    		}
    	}
    	return true;
    }


    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    
    
}