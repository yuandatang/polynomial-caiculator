// Name: Yuanda Tang
// USC loginid:yuandata
// CS 455 PA2
// Fall 2016
import java.util.ArrayList;
import java.util.Scanner;

/**
   a Polynomial calculate class.
   customer can do command such as
   create, add, print, etc. in this class.
 */
public class PolynomialCalculator {
	private static final int POLYNOMIALS_NUMBERS=10;
	private static final int DUMMY_INDEX=100;
	private static final int TARGET_INDEX=0;
	private static final int FIRST_INDEX=1;
	private static final int SECOND_INDEX=2;
	private static final int INPUT_QUANTITY=3;
	
	public static void main(String[] args) {
		Polynomial[] arrayOfPoly=new Polynomial[POLYNOMIALS_NUMBERS];
		for(int i=0;i<POLYNOMIALS_NUMBERS;i++){
			arrayOfPoly[i]=new Polynomial();
		}
		System.out.print("cmd>");
	    readCommand(arrayOfPoly);
	}
	

    /**
       read the customer input 
     */
	public static void readCommand(Polynomial[] arrayOfPoly){
	    Scanner in=new Scanner(System.in);
	    while(in.hasNextLine()){ 	
	    	String line=in.nextLine().toLowerCase();    //convert the upper case input to lower case.
	    	Scanner lineScanner=new Scanner(line);
	    	if(lineScanner.hasNext("create")){
	    	    doCreate(lineScanner,arrayOfPoly);
	    	}
	    	else if(lineScanner.hasNext("eval")){
	            doEval(lineScanner,arrayOfPoly);
	    	}
	    	else if(lineScanner.hasNext("print")){
	    		doPrint(lineScanner,arrayOfPoly);
	    	}
	    	else if(lineScanner.hasNext("help")){
	    		displayHelp();
	    	}
	    	else if(lineScanner.hasNext("add")){
	    		doAdd(lineScanner,arrayOfPoly);
	    	}
	    	else if(lineScanner.hasNext("quit")){
	    		break;
	    	}
	    	else{
	    		System.out.println("ERROR: Illegal command. Type 'help' for command options.");
	    	}
	    	System.out.print("cmd>");
	    }
	}
	
	
	/**
	   create a polynomial 
	 */
    public static void doCreate(Scanner receivedLine,Polynomial[] targetArray){
    	receivedLine.skip("create");
    	if(receivedLine.hasNextInt()){
			int indexOfArray=receivedLine.nextInt();
			if(indexOfArray>=0 && indexOfArray<POLYNOMIALS_NUMBERS){
                System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");	
                Scanner in=new Scanner(System.in);
                String ints=in.nextLine();
                Scanner intsLine=new Scanner(ints);
                targetArray[indexOfArray]=new Polynomial(new Term());
                while(intsLine.hasNextDouble()){
            	    double coeff=intsLine.nextDouble();
            	    if(intsLine.hasNextInt()){
            	        int exponent=intsLine.nextInt();
            	        int positiveExponent=Math.abs(exponent);
            	        if(exponent<0){
            	        	System.out.println("WARNING: turning your negative exponent to positve value");
            	        }
                        targetArray[indexOfArray]=targetArray[indexOfArray].add(new Polynomial(new Term(coeff,positiveExponent)));
            	    }
            	    else{
            	    	System.out.println("WARNING: there is no exponent for your last term");
            	    }
                }
		    }
			else{
				System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
			}
				
		}
    	else{
    		System.out.println("ERROR: Illegal command. Type 'help' for command options.");
    	}
    }
    
    
    /**
       add two polynomials 
     */
    public static void doAdd(Scanner receivedLine,Polynomial[] targetArray){
    	int[] threeIndexs={DUMMY_INDEX,DUMMY_INDEX,DUMMY_INDEX};
    	receivedLine.skip("add");
    	for(int i=0;i<INPUT_QUANTITY;i++){
    		if(!receivedLine.hasNextInt()){
    			System.out.println("Please input the indexs when you use ADD command");
    			break;
    		}
    		else{
    			threeIndexs[i]=receivedLine.nextInt();;
    		}    		
    	}
    	
    	if(threeIndexs[TARGET_INDEX]>=0 && threeIndexs[TARGET_INDEX]<POLYNOMIALS_NUMBERS && 
    			threeIndexs[FIRST_INDEX]>=0 && threeIndexs[FIRST_INDEX]<POLYNOMIALS_NUMBERS && 
    			threeIndexs[SECOND_INDEX]>=0 && threeIndexs[SECOND_INDEX]<POLYNOMIALS_NUMBERS){
    			targetArray[threeIndexs[TARGET_INDEX]]=targetArray[threeIndexs[FIRST_INDEX]].add(targetArray[threeIndexs[SECOND_INDEX]]);
    	}
    	else{
    		System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
    	}
    }
    
    
    /**
       evaluate the result for the specific polynomial
     */
    public static void doEval(Scanner receivedLine,Polynomial[] targetArray){
    	receivedLine.skip("eval");
    	if(receivedLine.hasNextInt()){
    		int indexOfArray=receivedLine.nextInt();
    		if(indexOfArray>=0 && indexOfArray<POLYNOMIALS_NUMBERS){
    		    System.out.print("Enter a floating point value for x:");	
                Scanner in=new Scanner(System.in);
                if(in.hasNextInt()){
                    System.out.println(targetArray[indexOfArray].eval(in.nextInt()));    	
                }
    	    }
    		else{
    			System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
    		}
    	}
    	else{
    		System.out.println("ERROR: Illegal command. Type 'help' for command options.");
    	}
    }
    
    
    /**
       print the specific polynomial
     */
    public static void doPrint(Scanner receivedLine,Polynomial[] targetArray){
    	receivedLine.skip("print");
    	if(receivedLine.hasNextInt()){
    		int indexOfArray=receivedLine.nextInt();
    		if(indexOfArray<POLYNOMIALS_NUMBERS && indexOfArray>=0){
    			System.out.println(targetArray[indexOfArray].toFormattedString());
    		}
    		else{
    			System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
    		}
    	}
    	else{
    		System.out.println("ERROR: Illegal command. Type 'help' for command options.");
    	}
    }
    
    
    /**
       display the command of how to control the program
     */
    public static void displayHelp(){
    	System.out.println("create: create a polynomial");
    	System.out.println("eval: evaluate the value of one polynomial");
    	System.out.println("print: print one polynomial");
    	System.out.println("add x y z: polynomial x = polynomial y+polynomial z");
    	System.out.println("quit: quit the program");
    }
}
