/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class, as 
 * well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
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

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix      The matrix object that will be filled with random
     *                    samples.
     * @param nSamp       The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {
        // You need to fill in this method.
        double deta;
        double ex2 = 0, e2x = 0;
        for(int index = 0; index < nSamp; index++){
            matrix.random();
            deta = matrix.determinant();
            e2x += Math.pow(deta, 2);
            ex2 += deta;
        }
        return (e2x/nSamp - Math.pow(ex2/nSamp, 2));
    }
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50 and print the results to the output. See the 
     * formulation for more detail.
     */
    public static void main(String[] args) {
        // You need to fill in this method.
        Matrix A, B;
        // double n = System.currentTimeMillis();  // Record the start time ... 
        for(int index = 2; index <= 50; index++){
            A = new GeneralMatrix(index, index);
            B = new TriMatrix(index);
            System.out.println(String.format("%2d %30.15f",index, matVariance(A, 20000)) + String.format("%25.15f", matVariance(B, 200000))); 
        }
        // System.out.println("Time Elapsed: " + (System.currentTimeMillis() - n)/1000 + 's');
    }
}