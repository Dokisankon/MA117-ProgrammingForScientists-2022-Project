import java.util.ArrayList;

/*
 * PROJECT II: Secant.java
 *
 * This file contains a template for the class Secant. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * In this class, you will create a basic Java object responsible for
 * performing the Secant root finding method on a given polynomial
 * f(z) with complex co-efficients. The formulation outlines the method, as
 * well as the basic class structure, details of the instance variables and
 * how the class should operate.
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

public class Secant {
    /**
     * The maximum number of iterations that should be used when applying
     * Secant. Ensure this is *small* (e.g. at most 50) otherwise your
     * program may appear to freeze.
     */
    public static final int MAXITER = 20;

    /**
     * The tolerance that should be used throughout this project. Note that
     * you should reference this variable and _not_ explicity write out
     * 1.0e-10 in your solution code. Other classes can access this tolerance
     * by using Secant.TOL.
     */
    public static final double TOL = 1.0e-10;

    /**
     * The polynomial we wish to apply the Secant method to.
     */
    private Polynomial f;


    /**
     * A root of the polynomial f corresponding to the root found by the
     * iterate() function below.
     */
    private Complex root;
    
    /**
     * The number of iterations required to reach within TOL of the root.
     */
    private int numIterations;

    /**
     * An enumeration that signifies errors that may occur in the root finding
     * process.
     *
     * Possible values are:
     *   OK: Nothing went wrong.
     *   ZERO: Difference went to zero during the algorithm.
     *   DNF: Reached MAXITER iterations (did not finish)
     */
    enum Error { OK, ZERO, DNF };
    private Error err = Error.OK;
    
    
    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * Basic constructor.
     *
     * @param p  The polynomial used for Secant.
     */
    public Secant(Polynomial p) {
        // You need to fill in this method.
        this.f = p;
    }

    // ========================================================
    // Accessor methods.
    // ========================================================
    
    /**
     * Returns the current value of the err instance variable.
     */
    public Error getError() {
        // You need to fill in this method with the correct code.
        return err;
    }

    /**
     * Returns the current value of the numIterations instance variable.
     */
    public int getNumIterations() { 
        // You need to fill in this method with the correct code.
        return numIterations;
    }
    
    /**
     * Returns the current value of the root instance variable.
     */
    public Complex getRoot() {
        // You need to fill in this method with the correct code.
        // Complex point = new Complex(0,0);
        // Polynomial dPolynomial = new Polynomial();
        // for (int i = 0; i < MAXITER; i++){
        //     for (int j = 0;j < f.coeff.length - 1;j++){
        //         dPolynomial.coeff[j] = f.coeff[j + 1].multiply(j + 1);
        //     }
            
        // }
        return root;
    }

    /**
     * Returns the polynomial associated with this object.
     */
    public Polynomial getF() {
        // You need to fill in this method with the correct code.
        return f;
    }

    // ========================================================
    // Secant method (check the comment)
    // ========================================================
    
    /**
     * Given two complex numbers z0 and z1, apply Secant to the polynomial f in
     * order to find a root within tolerance TOL.
     *
     * One of three things may occur:
     *
     *   - The root is found, in which case, set root to the end result of the
     *     algorithm, numIterations to the number of iterations required to
     *     reach it and err to 0.
     *   - At some point the absolute difference between f(zn) and f(zn-1) becomes zero. 
     *     In this case, set err to -1 and return.
     *   - After MAXITER iterations the algorithm has not converged. In this 
     *     case set err to -2 and return.
     *
     * @param z0,z1  The initial starting points for the algorithm.
     */
    public void iterate(Complex z0, Complex z1) {
        // You need to fill in this method.
        Complex x0 = z0;
        Complex x1 = z1;
        Complex x2;
        Complex lef = new Complex();
        Complex ans;
        err = Error.DNF;
        for (int i = 2; i <= MAXITER; i++){
            lef = f.evaluate(x1).multiply(x1.add(x0.negate()));
            if (f.evaluate(x1).abs() == 0){
                if (f.evaluate(x0).abs() == 0){
                    this.err = Error.ZERO;
                    return;       
               }
            }
            lef = lef.divide(f.evaluate(x1).add(f.evaluate(x0).negate()));       
            x2 = x1.add(lef.negate()); 
            ans = f.evaluate(x2);
            // System.out.println(ans.abs());
            if (ans.abs() < TOL){
                // System.out.println(x2.toString());
                this.numIterations = i;
                this.err = Error.OK;
                root = x2;
                break;
            }
            x0 = x1;
            x1 = x2;
        } 
        // System.out.println(x0.toString()); 
        // System.out.println(x1.toString());
        // System.out.println(numIterations);
}
      
    // ========================================================
    // Tester function.
    // ========================================================
    
    public static void main(String[] args) {
        // Basic tester: find a root of f(z) = z^3-1.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(), new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        Secant     s    = new Secant(p);
                
        s.iterate(new Complex(0,0), new Complex(1.0,1.0));
        System.out.println(s.getNumIterations());   // 12
        System.out.println(s.getError());           // OK
        // System.out.println(s.getRoot());

        // Complex[] roots = new Complex[3];
        // int i = 0;
        // for (int x = -5; x <= 5; x++){
        //     for (int y = -5; y <= 5; x ++){
            ArrayList<Complex> roots;
            roots = new ArrayList<Complex>();
            for (int i = -1; i <= 1; i++){
                for (int j = -1; j <= 1; j++){
                    s.iterate(new Complex(), new Complex(i, j));
                    // System.out.println(roots.contains(new Complex(1, 0)));
                    if (roots.contains(s.getRoot()) == false){
                        roots.add(s.getRoot());
                    }
                }
            }    
            System.out.println(roots);
            // s.iterate(new Complex(), new Complex(-2, 2));
            //     System.out.println(s.getRoot());
            //     roots.add(s.getRoot());
            //     s.iterate(new Complex(), new Complex(1, 0));
            //     System.out.println(s.getRoot());                
            //     s.iterate(new Complex(), new Complex(1, -1));
            //     System.out.println(s.getRoot());
            //     System.out.println(roots.get(1));
        //     }
        // }
    }
}
