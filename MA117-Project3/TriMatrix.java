/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
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

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;
    
    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension  The dimension of the array.
     */
    public TriMatrix(int dimension) {
        // You need to fill in this method.
        super(dimension, dimension);
        if(dimension < 1){
            throw new MatrixException("Your dimension cannot smaller than 1");
        }
        this.diagonal = new double[dimension];
        this.upperDiagonal = new double[dimension - 1];
        this.lowerDiagonal = new double[dimension - 1];
        
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
            throw new MatrixException("The value of j or i are not avalible.");
        }
        if(i == j){
            return this.diagonal[i];
        }else if(i - j == 1){
            return this.lowerDiagonal[j];
        }else if(j - i == 1){
            return this.upperDiagonal[i];
        }else{
             return 0.0;  
        }  
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
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
        if (Math.abs(i-j) > 1){   
            throw new MatrixException("You cannot set element as it's not in matrix.");
        }else if(i - j == 0){
            this.diagonal[i] = value;
        }else if(i - j == 1){
            this.lowerDiagonal[j] = value;
        }else if(j - i == 1){
            this.upperDiagonal[i] = value;
        }
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // You need to fill in this method.

        double deta = 1;
        TriMatrix LU = this.LUdecomp();

        for(int i = 0; i <  this.iDim; i++){
            deta = deta*LU.getIJ(i, i);
        }
        return deta;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix LUdecomp() {
        // You need to fill in this method.

        TriMatrix ans = new TriMatrix(this.iDim);
        ans.setIJ(0, 0, this.getIJ(0,0));
        for(int i = 0; i < ans.iDim -1 ; i++){
            ans.setIJ(i, i+1, this.getIJ(i, i+1));
        }  

        for(int j = 0; j < ans.iDim-1; j++){
            ans.setIJ(j+1, j, this.getIJ(j+1, j)/ans.getIJ(j, j));
            ans.setIJ(j+1, j+1, this.getIJ(j+1, j+1) - (ans.getIJ(j+1, j) * ans.getIJ(j, j+1)));
        } 
        
        return ans;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return        The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second){
        // You need to fill in this method.
        GeneralMatrix ans;
        if (this.iDim == second.iDim && this.jDim == second.jDim){
            ans = new GeneralMatrix(second.iDim, second.jDim);
            for (int i = 0; i < second.iDim; i++){
                for (int j = 0; j < second.jDim; j++){
                    ans.setIJ(i, j, this.getIJ(i, j) + second.getIJ(i, j));
                }
            }
            return ans;
        }
        throw new MatrixException("You cannot add to this matrix.");
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
        int n = this.iDim;
        for (int i = 0; i < n; i++){
            this.setIJ(i, i, Math.random());
        }
        for (int i = 0; i < n-1; i++){
            this.setIJ(i, i+1, Math.random());
            this.setIJ(i+1, i, Math.random());
        }
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        // Test your class implementation using this method.
        TriMatrix A = new TriMatrix(5);
        A.random();
        GeneralMatrix B = new GeneralMatrix(3,3);
        B.setIJ(0, 0, 1);
        B.setIJ(1, 1, 1);
        B.setIJ(2, 2, 1);
        // B.setIJ(2, 0, 1);
        System.out.println(A);
        System.out.println();
        // System.out.println(B);
        // System.out.println();
        // System.out.println(A.multiply(B));
        System.out.println(A.determinant());
        // System.out.println(A.getIJ(1, 0)*A.getIJ(0, 1) + A.getIJ(1, 1)*A.getIJ(1, 1));
        System.out.println(A.LUdecomp().getIJ(1, 1));
    }
}