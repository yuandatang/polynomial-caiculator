
public class PolynomialTester {

	public static void main(String[] args) {
		    Polynomial p1 = new Polynomial();
		    Polynomial p2 = new Polynomial();
		    Polynomial p3 = p1.add(new Polynomial(new Term(0, 7)));
		    Polynomial p4 = new Polynomial(new Term(3, 2)).add(new Polynomial(new Term(-3, 2)));
		    Polynomial p5 = new Polynomial(new Term(3, 2)).add(new Polynomial(new Term(-5, 2)));
		    Polynomial p6 = p5.add(new Polynomial(new Term(2, 2)));
	   
		    
		    
		  
	    
	    System.out.println(p1.toFormattedString());
	    System.out.println(p2.toFormattedString());
	  System.out.println(p3.toFormattedString());
	    System.out.println(p4.toFormattedString());
	    System.out.println(p5.toFormattedString());
        System.out.println(p6.toFormattedString());
	}

}
