/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 */

public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        // You need to fill in this function.
        this.coeff = coeff;
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        // You need to fill in this function.
        this.coeff = new Complex[] {};
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return  The coefficients array.
     */
    public Complex[] getCoeff() {
        // You need to fill in this method with the correct code.
        int counter = 0;
        Complex[] ans;
        for (int i = 0; i < coeff.length; i++){
            if (coeff[i].abs2() != 0){
                counter = counter + 1;
            }
        }
        ans = new Complex[counter];
        counter = 0;
        for (int i = 0; i < coeff.length; i++){
            if (coeff[i].abs2() != 0){
                ans[counter] = coeff[i];
                counter++;
            }
        }
        return this.coeff;
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable.  Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */
    public String toString() {
        // You need to fill in this method with the correct code.
        String ans = "";
        if (coeff.length > 1){
            ans = '(' + coeff[0].toString() + ')' + " + " + '(' + coeff[1].toString() + ')' + "z";
            for (int i = 2; i < coeff.length; i++){
                ans = ans + " + " + '(' + coeff[i].toString() + ')' + "z^" + i;
            }
        }
        if (coeff.length == 1){
            ans = '(' + coeff[0].toString() + ')';
        }
        return ans;
    }


    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        // You need to fill in this method with the correct code.
        if (coeff.length != 0){
            return coeff.length - 1;
        }
        return 0;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        // You need to fill in this method with the correct code.
        Complex ans = new Complex();
        Complex deg_z = new Complex(1,0);
        for (int i = 0; i < coeff.length; i++){
            if (i != 0){
                deg_z = deg_z.multiply(z);            
            }
            ans = ans.add(coeff[i].multiply(deg_z));
        }
        return ans;
    }

    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // You can fill in this function with your own testing code.  
        Polynomial A = new Polynomial(new Complex[] {   
                                                        // new Complex(5,6),
                                                        // new Complex(3,3),
                                                        // new Complex(2,2)
                                                    });
        // System.out.println(A.degree());
        // System.out.println(A.getCoeff());
        // System.out.println("function's answer is " + A.evaluate(new Complex(5,4)));

        
        Complex[]  C;
        C = new Complex[A.degree()];
        for (int i = 0; i < A.coeff.length - 1; i++){
            C[i] = A.coeff[i + 1].multiply(i + 1);
        }
        Polynomial B = new Polynomial(C);
        System.out.println(A.degree());
        System.out.println(B.evaluate(new Complex(1,1)));
    }
}