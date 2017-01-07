# polynomial-caiculator

Introduction

In this assignment you will get practice using the Java ArrayList class, and more practice implementing your own classes. Like you did in assignment 1 and lab 4, you will be implementing a class whose specification we have given you, in this case a polynomial class called Polynomial; you will also be writing a command-based program to work with Polynomial objects. Writing the command-based program will give you more experience processing input line-by-line and error-checking user input.
Note: this program is due after your midterm exam, but it's a fair amount bigger than the first assignment. We recommend getting started on it before the midterm. It only uses topics from before the midterm, so working on it now will also help you prepare for the exam (there will be paper and pencil programming problems as part of the exam). One possible milestone would be to complete Polynomial and PolynomialTester by the midterm.

The assignment files

Getting the assignment files. Make a pa2 directory and cd into it. Copy all of the files in the pa2 directory of the course account to your pa2 directory. We can accomplish this with Unix wildcards (denoted with a *).
cp ~csci455/assgts/pa2/* .
The files in bold below are ones you create and/or modify and submit. The ones not in bold are ones that you will use, but not modify. The files are:

Polynomial.java The interface for the Polynomial class; it contains stub versions of the functions so it will compile. You will be completing the implementation of this class. You may not change the interface for this class (with one exception described later), but you may add private instance variables and/or private methods to it
Term.java Code for a class for a single term in a polynomial. We wrote this class for you. Don't change it.
PolynomialTester.java A non-interactive test program for your Polynomial class. You are not required to submit this file. This is described further in a later section.
PolynomialCalculator.java The command-based program that lets you manipulate polynomials. Its behavior is specified later in the assignment. You create this file.
turninpa2 A shell script with the command for turning in the assignment. See the section on submitting your program for more details.
README See section on Submitting your program for what to put in it. Before you start the assignment please read the following statement which you will be "signing" in the README:
"I certify that the work submitted for this assignment does not violate USC's student conduct code. In particular, the work is my own, not a collaboration, and does not involve code created by other people, with the exception of the resources explicitly mentioned in the CS 455 Course Syllabus. And I did not share my solution or parts of it with other students in the course."
Note on running your program

We will be using assert statements in this program. To be able to use them, you need to run the program with asserts enabled (-ea flag). (You do not need to compile any special way.) Here are two examples:
java -ea PolynomialTester
java -ea PolynomialCalculator
You should run with this flag set every time.
Assert statements, described in Special Topic 11.5 of the text, are another tool to help us write correct code. More about how we are using them here in the section on representation invariants.

NOTE: In Eclipse you use the Run Configurations settings (in the Run menu) to change what arguments are used when running a program.


The assignment

You will be implementing a polynomial class along with an interactive command-based program that uses your class. The program will allow the user to create and manipulate several polynomials. The internal representation we will use for a polynomial is an ArrayList object. Some of the requirements for the program relate to efficiency, testing, and style/design, as well as functionality. They are described in detail in the following sections of the document, and then summarized near the end of the document.
Polynomial: interface

Your polynomial class, Polynomial, will have floating point coefficients, and non-negative integer exponents. The class definition is in Polynomial.java. There is also a helper Term class defined in Term.java. A Term is used to hold the (coefficient,exponent) pair that makes up one term in a polynomial. The interface for Polynomial is described below using example calls and results:
Polynomial p = new Polynomial();
Create the zero polynomial. That is one all of whose coefficients have the value zero.
Polynomial p = new Polynomial(new Term(3,5));
Create a polynomial with a single term. For example, the above expression creates the polynomial 3x5.
Polynomial sum = poly1.add(poly2);
Add two polynomials, resulting in a new one that is the sum of the first two. The two original polynomials are unchanged.
poly1: 3x3 + 2x + 7
poly2: x5 -3x3 + 5
sum:   x5 + 2x + 12
double result = poly1.eval(x);
Evaluate a polynomial for a specific floating point value of x.
poly1: 3x3 + 2x + 7
x: 2
result: 35
System.out.println(poly1.toFormattedString());
Return a String version of the polynomial that's close to the usual format we use to denote polynomials. It's "close to", because it's not going to use a superscript to denote exponentiation. Note: toFormattedString does not print anything itself. Here are some examples showing the format to use:
0.0
12.7x^2
3.0x^2 + 2.0x + 7.0
-3.0x^5 + -2.0x + -7.0
-x^2 + x + -1.0
Note that to make the coding easier a double that has no decimal part is printed as, for example, "3.0" instead of "3"; that's the default format used in Java when converting such a double to a String.
For more information on toFormattedString, see the method comments in Polynomial.java.

Note: No Polynomial methods do any I/O.
Clearly there is additional functionality one might want to offer in a useful Polynomial class, such as multiplication, but we'll keep this one small to make it doable within the time constraints. (You may be enhancing this class in a later assignment.)

You may have noticed that there is one more Polynomial method, isValidPolynomial, that's private , i.e., not part of the public interface. We will describe that later after we discuss the representation.

You may not change the interface for this class, with the following exception: you may add a public Polynomial toString method that you may want to use for debugging purposes. It would probably mostly call the toString method(s) of its constituent part(s). That way you can see if you are building your Polynomial object correctly, even before you implement toFormattedString.

Polynomial: representation/implementation

You can think of a polynomial
Cn xn + Cn-1xn-1 + . . .+ C1 x + C0

as a bunch of pairs (Ci, i), where Ci is the coefficient of the term with power i. We could easily represent this with an array or ArrayList of n+1 values, where v[i] is the coefficient for xi. However, for any of the terms where the coefficient is zero, we don't normally show that as part of the polynomial, since a zero coefficient makes that whole term zero. In fact, many times we have a polynomial with a large degree (highest power of x), but very few non-zero terms. We call a polynomial with many zero terms a sparse polynomial.

An efficient representation for a sparse polynomial is one where we only store the non-zero terms. We're going to use such a sparse representation using an ArrayList for it here (a linked list would also be suitable, but we haven't covered that yet). Each element in the list will represent the (Ci, i) pair for a non-zero term in the polynomial. Furthermore, we're going to maintain this as an ordered list, ordered from highest power term to lowest. Here are a few examples:

polynomial: x100 + 3 
representation: { (1,100)   (3,0) }

polynomial: x2 + 2x + 1 
representation: { (1,2)   (2,1)   (1,0) }

We can think of the first example above as "1 times x to the 100th plus 3 times x to the 0", which leads to the two pairs given. The zero polynomial will be represented by a list of no pairs (i.e., an empty list).

The polynomial will be in a simplified form: besides no zero terms, we won't ever have two terms with the same exponent. If you look at the interface for Polynomial, you will see that there are no restrictions on the term argument used in creating one (although the Term class itself has a restriction of no negative exponents); this implies that the Polynomial code itself is responsible for making sure the internal representation is a simplified polynomial. For example, after executing the following code sequence all of the polynomials p1 through p4 and p6 are the zero polynomial (i.e., internally, an empty ArrayList), and p5 has one Term in its ArrayList.

    Polynomial p1 = new Polynomial();
    Polynomial p2 = new Polynomial(new Term(0, 3));
    Polynomial p3 = p2.add(new Polynomial(new Term(0, 7)));
    Polynomial p4 = new Polynomial(new Term(3, 2)).add(new Polynomial(new Term(-3, 2)));
    Polynomial p5 = new Polynomial(new Term(3, 2)).add(new Polynomial(new Term(-5, 2)));
    Polynomial p6 = p5.add(new Polynomial(new Term(2, 2)));
For another example of this simplification, look at the example input and output for the last call to create shown in the section on PolynomialCalculator.
Auxiliary functions. As usual with classes, you are free to add private auxiliary (a.k.a., helper) methods that help you do the work of the methods that are part of the public interface, especially in cases where you can use that function more than once in the other code. This will help you avoid writing redundant code, and will help you keep your methods shorter and more readable. See the summary section near the end of the document for more information about restrictions on method length.


More on adding two polynomials

You are required to add two polynomials in an efficient way. If the first polynomial has n non-zero terms, and the second one has m, you are required to do compute the sum in O(m + n ) steps. Since each of the two polynomials' terms are in order from largest to smallest exponent, we can do this by doing what's called a "merge" of the two ordered lists to create one longer* ordered list. Note: we will be talking about big-O notation in class soon: you can think of O(m+n) as meaning it takes at most C1*(m+n) + C2 steps, where C1 and C2 are constants (i.e., the constants don't vary with the size of the lists).
To merge two ordered lists, traverse them both simultaneously (i.e., using one loop; not nested loops). Compare the current element in each of the two lists to figure out what value to put at the end of the result-list next to build it in order (decreasing order, in the polynomial example). So, for example, if list1 had the element you put in the result list this time, you advance to the next element of list1, but you don't advance where you are in list2, since you don't necessarily know where it goes in the result list yet.

When you run out of elements in one of the lists, just copy all the rest of the elements from the other list into the result list.

A merge can be "destructive", which means it reuses elements from the original lists, or "non-destructive", which means it creates new elements for the result list. Since add is not a mutator (i.e., the two operands are unchanged by the operation), this is a non-destructive version.

Note, however, that Term objects can be shared between the operands and the result because the Term class is immutable (see section 8.2.3 of text for more about immutable classes). Put another way, you don't always have to do new Term(...) when you put a Term into the ArrayList inside a Polynomial. Recall that the String class is also immutable.

The merge algorithm is illustrated with an example at the beginning of section 14.4 of the textbook. A completed method to merge two arrays appears in that section as well. That section discusses its use in an algorithm called merge sort, which we'll discuss later in the semester (you do not have to understand merge sort to complete this assignment).

* In our case, the resulting list may actually be shorter, even much shorter, because we don't store sums that cancel (i.e., add to zero)↩

What data can add access? Another issue that comes up with the add method is the rules for visibility of private data when we are dealing with multiple objects of the same class. The rule is that a method such as add of class Polynomial may access the private data of any Polynomial objects that are in its scope. E.g.,

 
public class Polynomial {

   private Foobar myData;   // dummy instance variable

   public Polynomial add(Polynomial b) {  // contains dummy code to illustrate visibility rules

       Polynomial sum = new Polynomial();

       this.myData;   // legal to access pvt data of this, b, and sum here
       b.myData;
       sum.myData;  

       Term t = new Term(3,5);
       t.expon;      // does not compile -- expon is pvt in Term class
   }
}

Representation invariants

Many of the development techniques we discuss in this class, for example incremental development, the use of good variable names, and unit-testing, are to help enable us to write correct code (and make it easier to enhance that code later). Another way to ensure correct code within a class is to make explicit any restrictions on what values are allowed to be in a private instance variable, and any restrictions on relationships between values in different instances variables in our object. Or put another way, making sure we know what must be true about our object representation when it is in a valid state. These are called representation invariants.
Representation invariants are things that are true about the object as viewed by the implementor. Since for many classes, once a constructor has been called, the other methods can be called in any order, we need to ensure that none of the constructors or mutators can leave the object in an invalid state. It will be easier to do that if we know what those assumptions are.

Note: The Polynomial class doesn't actually have any mutators, the only methods that change a poly object's state are the ones that create them, namely, the two constructors and the add method.

There are two assignment requirements for your Polynomial class related to this issue (detailed explanations of each of these follow):

in a comment next to your private instance variable definitions for Polynomial, list the representation invariants for the object.
write the private boolean method isValidPolynomial() and call it from other places in your program as described below.
The representation invariant comment for Polynomial 
Write a list of all the conditions that the internals of a Polynomial object must satisfy. That is, conditions that are always true about the data in a valid Polynomial object. You can find this information in the previous section. For example, one invariant is that for a non-zero poly all the terms must be in decreasing order by exponent.
isValidPolynomial() method 
This private method will test the representation invariant for the internals of a poly. It will return true iff it is valid, i.e., the invariants are satisfied.

Call this function at the end of every Polynomial method, including the constructors, to make sure the method leaves the poly in the correct state. This is one kind of sanity check: one part of a program double-checking that another part is doing the right thing (similar to printing expected results and actual results).

Rather than putting this test in an if statement, we're going to put it in an assert statement. For example:

assert isValidPolynomial();      // calls isValidPolynomial on implicit parameter
Assert statements are described in Special topic 11.5 of the text.
In addition, for the add method, call it on all of the poly objects it deals with. Of course the one it creates, but also its two operands, e.g.:

public Polynomial add(Polynomial b) {

   . . .

   assert isValidPolynomial();   // call it on "this"-- the left operand of the add
   assert b.isValidPolynomial();  // call it on the right operand of the add
   assert result.isValidPolynomial();    // call it on the poly created in add
   return result;
}
The add operands are supposed to be unmodified by add: the checks above help make sure you didn't' modify them by mistake.
Please make sure you are running your program with assertions enabled for every run of this program, since it's in a development stage. See earlier section for how to do this. You won't really know if they are getting checked unless you force one to fail.

The point of these assert statements is to notify you in no uncertain terms (so to speak :-)) of possible bugs in your code. The program crashing will force you to fix those bugs. For example, if a Polynomial is not in decreasing order by exponent, then other methods, such as toFormattedString will not work as advertised.

PolynomialTester program

You are required to write a PolynomialTester program, similar to the other "Tester" programs we have written for other classes, that uses hard-coded values to put the methods through their paces on well-chosen test cases, and prints expected as well as actual results. You are not required to submit the PolynomialTester program.

The main advantage to writing such a program is that it will allow you to thoroughly test and debug the more difficult aspects of your Polynomial class (in particular, the add method) without without worrying about the details of doing I/O necessary for PolynomialCalculator. If you try to debug your PolynomialCalculator at the same time as your Polynomial class, it will be much harder to pinpoint which part of the program is causing problems.

Another advantage to writing a PolynomialTester program: if you do not get your PolynomialCalculator working, partial credit will be given to a working Polynomial class. You won't even know if you have a working Polynomial class unless you have some way to test it.


PolynomialCalculator program

This section describes the user interface for PolynomialCalculator, an interactive program that lets you build and do computations on several Polynomial objects.
Your program will initially create an array of 10 polynomials whose initial value is the zero polynomial. The user will be able to manipulate any of these polynomials by giving the commands shown by example below. The arguments to the commands are indices of this array.

Follow the example below to see what the interface should look like. You should also include an intro message that directs users to a help command option, that when given, should provide a command summary. You should do some rudimentary error handling as well. The example shows some, but not all, of the errors you'll need to handle. See the next section for details of error checking. User input shown in bold below. The explanatory text in italics and parentheses is not part of the actual user interaction.

cmd> create 0
Enter a space-separated sequence of coeff-power pairs terminated by <nl>
1 100 3 0
cmd> print 0
x^100 + 3.0
cmd> create 1
Enter a space-separated sequence of coeff-power pairs terminated by <nl>
1 2 2 1 1 0           (can use repeated calls to add)
cmd> print 1
x^2 + 2.0x + 1.0
cmd> add 2 0 1        (poly 2 = poly 0 + poly 1)
cmd> print 2
x^100 + x^2 + 2.0x + 4.0
cmd> create 3
Enter a space-separated sequence of coeff-power pairs terminated by <nl>
20 0 3 2              (ok to enter terms in any order)  
cmd> add 2 2 3        (poly 2 = poly 2 + poly 3)
cmd> PriNt 2          (commands can be upper or lower case)
x^100 + 4.0x^2 + 2.0x + 24.0
cmd> eval 3
Enter a floating point value for x: 2
32.0
cmd> create 4
Enter a space-separated sequence of coeff-power pairs terminated by <nl>
-1 100 -4 2 -24 0              (ok to enter terms in any order)
cmd> print 4
-x^100 + -4.0x^2 + -24.0
cmd> add 5 4 2        (poly 5 = poly 4 + poly 2)
cmd> print 5
2.0x
cmd> eval 10
ERROR: illegal index for a poly.  must be between 0 and 9, inclusive
cmd> print 9
0.0
cmd> eval 9
Enter a floating point value for x: 17
0.0
cmd> create 1
Enter a space-separated sequence of coeff-power pairs terminated by <nl>
-3 10   0 1  52.3 0   5 10   12 0   -2 10      (ok to enter zero terms; mult terms with same exponent)
cmd> print 1
64.3 
cmd> exit
ERROR: Illegal command.  Type 'help' for command options.
cmd> quit

Your output for a particular input should match what's shown above character-by-character (e.g., the messages displayed and the error handling should be the same), so we can automate our tests when we grade your program. There are a few places where we haven't specified the exact output (the help command, some of the error messages); obviously, those won't always be the same for each solution.
Hint on identifying commands. The String class has a method to do case-insensitive comparisons.

Error checking required

Here is a list of errors your poly program must check for:
invalid command
illegal polynomial index (for the array of polynomials)
for the polynomial data for the create command:
missing the last exponent (i.e., odd number of values). For this you should ignore the last value entered (i.e., the term that had a coefficient given, but no corresponding exponent).
negative exponent. For this you should use the absolute value.
For each one you must print an informative message which also states the corrective action being taken, if any. All error messages must start with the string "ERROR:". For the last two items listed above, where you are taking corrective action, you should label your message with the string "WARNING:" instead.
Reading polynomial data for create command

The polynomial data for the create command are free-form. Here is more about what will be allowed in that line of input:
Zero coefficients. Invoking create with one or more terms term with a zero coefficient is fine. The Polynomial class itself handles simplifying the polynomial. There was an example shown of this in the last create command given in the large example shown earlier.
Multiple coefficients that have the same exponent. Again, the polynomial takes care of simplification. The create example just mentioned also shows this.
Multiple spaces (' ') and/or tabs ('\t') are allowed before, between or after the numbers given. Recall that the first newline ('\n') encountered terminates the list of numbers.
Completing this last requirement of create is a little tricky since "in.nextInt()", for example, skips leading whitespace including newlines, but we are using newline as our sentinel, so we have to be able to test for it. Section 11.2.5 of the textbook (called Scanning a String) shows one way of solving the problem of processing an indeterminate number of values all on one line. It takes advantage of the fact that the Scanner class can also be used to read from a String instead of the keyboard. First you use nextLine() to read the whole line into a String. Then you use a second Scanner object initialized with this string to then break up the line into the parts you want, using the Scanner methods you are already familiar with.
Hint on how to test out this technique: write a little test program to read an indeterminate number of ints from each line. For each line break it up into an indeterminate number of ints, storing them in an ArrayList. Note: This simpler test is not exactly the code you will need for this program (for example, reading the create input involves reading a double alternated with an int).


Structure of PolynomialCalculator

The code for PolynomialCalculator is much too complicated to put it all into one main method. We could design and add another class, to encapsulate the collection of polynomials, but that still would leave all the code to do the I/O. The structure of such a command-based program lends itself more to a procedural programming design, which we'll review here.
A good design principle (for procedural as well as object-oriented programming) is to keep each of your functions small, for easier program readability. In object-oriented programming, the class design sometimes naturally results in small methods, but sometimes you still need auxiliary private methods as we discussed in a previous section. The same principles apply for a procedural design. Since we haven't given you a predefined method decomposition for the PolynomialCalculator, you will have to create this decomposition yourself (although we'll give you some hints, coming up).

A procedural design in Java is just implemented as static methods in Java that pass data around via explicit parameters. Static methods are discussed in Section 8.4 of the text, and this use of them was also discussed in a sidebar in Lab 4. We have seen several examples of this in other test programs we have written, for example NumsTester.java of lab4 and PartialNamesTester.java we developed in lecture. We have also seen some utility classes in Java that have static methods: Math and Arrays.

If you have learned about procedural design in previous programming classes, you know that global variables are a no-no. Thus, in designing such a "main program" class, we don't create any class-level variables, because they become effectively global variables (see also Style Guideline #9). The "main class" does not represent any overall object. Instead you will create variables local to main that will get passed to its helper methods, as necessary.

In particular here, for each PolynomialCalculator command you may want a function to read the data for it and process it once you identify which command it is. E.g., doEval, doCreate, etc. That way it will be easy to see the main control structure of main, because it won't be cluttered with a lot of code for reading command arguments, for example. Some of those "do" functions will need to be further decomposed into smaller steps (almost certainly doCreate will).

Note: the next section discusses a limit on method length as one of our style guidelines for this course.


Summary of requirements

As on the first assignment, there are several requirements for this assignment related to design, testing, and development process strewn throughout this document. We'll summarize those and the functional requirements here:
implement Polynomial class according its public interface (see Polynomial: interface)
use the representation for Polynomial described in the section about that.
the Polynomial add method can't run in more than O(m+n) time as described in the section on adding two polynomials.
write representation invariant comments for Polynomial class.
implement and use private Polynomial method isValidPolynomial as described here.
implement PolynomialCalculator with the user interface described in the section about the PolynomialCalculator program.
your code will also be evaluated on style and documentation. We will deduct points for programs that do not follow the published style guidelines for this course (they are also linked from the Assignments page). (Note: For pa1 we only deducted points for problems related to some of the style guidelines.) One guideline we want you to be especially aware of is the limit of 30 lines of code at most allowable in a method. This is exclusive of whitespace, comment lines, and lines that just have a curly bracket by itself (i.e., you should not sacrifice code-readability to make your code fit into this limit).
README file / Submitting your program

You will be submitting Polynomial.java, PolynomialCalculator.java, and README. Make sure your name and loginid appear at the start of each file.
Here's a review of what goes in the README: This is the place to document known bugs in your program. That means you should describe thoroughly any test cases that fail for the the program you are submitting. You can also use the README to give the grader any other special information, such as if there is some special way to compile or run your program. You will also be signing the certification shown near the top of this document.

Like last time, for your convenience we put the exact submit command to use in a shell script. There should be a copy of this script in your aludra directory that has all your source code files in it (it was one of the original files you copied from our assignment directory), and you can run it as follows:

source turninpa2
(It's just a text file, so you can look at its contents to see the submit command it will run.)
