/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class.
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

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] values;

    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the data array.
     *
     * @param firstDim   The first dimension of the array.
     * @param secondDim  The second dimension of the array.
     */
    public GeneralMatrix(int firstDim, int secondDim) {
        // You need to fill in this method.
        super(firstDim, secondDim);
        if(firstDim < 1 || secondDim < 1){
            throw new MatrixException("Your dimension cannot smaller than 1");
        }
        this.iDim = firstDim;
        this.jDim = secondDim;
        this.values = new double[firstDim][secondDim];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the second matrix.
     *
     * @param second  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix second) {
        // You need to fill in this method.
        super(second.iDim, second.jDim);
        this.iDim = second.iDim;
        this.jDim = second.jDim;
        this.values = second.values;
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        // You need to fill in this method.
        if(i >= this.iDim || j >= this.jDim || i < 0 || j < 0){
            throw new MatrixException("The value of i or j are not avalible.");
        } 
        return this.values[i][j];
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the values array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        // You need to fill in this method.
        if(i >= this.iDim || j >= this.jDim || i < 0 || j < 0){
            throw new MatrixException("This point is out of range.");
        }
        this.values[i][j] = value;
        
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.
        double[] d = new double[1];
        double det = 1;
        GeneralMatrix LU = this.LUdecomp(d);
        for(int i = 0; i <  this.iDim; i++){
            det = det*LU.getIJ(i, i);
        }
        det = det*d[0];
        return det;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second) {
        // You need to fill in this method.
        if (second.iDim == this.iDim && second.jDim == this.jDim){
            GeneralMatrix ans = new GeneralMatrix(this.iDim, this.jDim);
            for (int i = 0; i < iDim; i++){
                for (int j = 0; j < jDim; j++){
                    ans.setIJ(i, j, this.values[i][j] + second.getIJ(i, j));
                }
            }
            return ans;
        }
        throw new MatrixException("This two matrix are not in same size.");
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        // You need to fill in this method.
        if(this.jDim == A.iDim){
            GeneralMatrix ans = new GeneralMatrix(this.iDim, A.jDim);
            for (int i = 0; i < this.iDim; i++){
                for (int j = 0; j < A.jDim; j++){
                    double value = 0;
                    for (int index = 0; index < A.iDim; index++){
                        value = value + this.getIJ(i, index) * A.getIJ(index, j);
                    }
                    ans.setIJ(i, j, value);
                }
            }
            return ans;
        }
        throw new MatrixException("This two matrix cannot multiply to each other in this order.");
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {
        // You need to fill in this method.
        GeneralMatrix ans = new GeneralMatrix(this.iDim, this.jDim);
        for (int i = 0; i < iDim; i++){
            for (int j = 0; j < jDim; j++){
                ans.setIJ(i, j, this.getIJ(i, j) * scalar);
            }
        }
        return ans;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        // You need to fill in this method.
        for (int i = 0; i < this.iDim; i++){
            for (int j = 0; j < this.jDim; j++){
                this.setIJ(i, j, Math.random());
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 u_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * sign[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     * In this case the string from the exception's getMessage() will contain
     * "singular"
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param sign  An array of length 1. On exit, the value contained in here
     *              will either be 1 or -1, which you can use to calculate the
     *              correct sign on the determinant.
     * @return      The LU decomposition of the matrix.
     */
    public GeneralMatrix LUdecomp(double[] sign) {
        // This method is complete. You should not even attempt to change it!!
        if (jDim != iDim)
            throw new MatrixException("Matrix is not square");
        if (sign.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[jDim];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        sign[0] = 1.0;
        
        for (i = 1; i <= jDim; i++) {
            big = 0.0;
            for (j = 1; j <= jDim; j++)
                if ((temp = Math.abs(a.values[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= jDim; j++) {
            for (i = 1; i < j; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= jDim; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= jDim; k++) {
                    dum = a.values[imax-1][k-1];
                    a.values[imax-1][k-1] = a.values[j-1][k-1];
                    a.values[j-1][k-1] = dum;
                }
                sign[0] = -sign[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.values[j-1][j-1] == 0.0)
                a.values[j-1][j-1] = 1.0e-20;
            if (j != jDim) {
                dum = 1.0/a.values[j-1][j-1];
                for (i = j+1; i <= jDim; i++)
                    a.values[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // Test your class implementation using this method.
        GeneralMatrix A = new GeneralMatrix(3, 3);
        GeneralMatrix B = new GeneralMatrix(3,1);
        A.random();
        B.random();
        // A.setIJ(0, 0, 0);
        // A.setIJ(1, 1, -1);
        // A.setIJ(2, 2, -1);
        // System.out.println(A);
        // System.out.println(A.LUdecomp(new double[] {0}));
        // B.random();
        // System.out.println(B);
        // System.out.println(B.determinant());;
        // A.setIJ(0, 2, 0);
        // A.setIJ(2, 0, 0);
        System.out.println(A);

        System.out.println("\n\r");
        System.out.println(A.multiply(2));
        System.out.println();
        System.out.println(A.determinant());

        // System.out.println("hello");
        // System.out.println("world");
        
    }
}