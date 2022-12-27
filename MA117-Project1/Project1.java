// import java.util.*;
// import java.io.*;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 */



public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names or types of the instance variables below! This 
    // is where you will store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int      circleCounter; // Number of non-singular circles in the file.
    public double[] aabb;          // The bounding rectangle for the first and last circles
    public double   Smax;          // Area of the largest circle (by area).
    public double   Smin;          // Area of the smallest circle (by area).
    public double   areaAverage;   // Average area of the circles.
    public double   areaSD;        // Standard deviation of area of the circles.
    public double   areaMedian;    // Median of the area.
    public int      stamp = 220209;
    // -----------------------------------------------------------------------
    // You should implement - but *not* change the types, names or parameters of
    // the variables and functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
        }

    /**
     * Results function. It should open the file called fileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */

     /**
      * 使用scanner函数读取目标文件，并记录要使用的值。
      */
    public void results(String fileName){
        // You need to fill in this method.
        circleCounter = 0;
        double[] arr_x;
        double[] arr_y;
        double[] arr_rad;
        try(
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        ){
        while(scanner.hasNext()) {
            double x_axis = scanner.nextDouble();
            double y_asis = scanner.nextDouble();
            double rad = scanner.nextDouble();
            circleCounter ++;
            }
        }
        catch(Exception e) {
            System.err.println("An error has occured. See below for details");
            e.printStackTrace();
        }

        arr_x   = new double[circleCounter];
        arr_y   = new double[circleCounter];
        arr_rad = new double[circleCounter];
        
        try(
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));
        ){
        while(scanner.hasNext()) {
            int i = 0; 
            arr_x[i] = scanner.nextDouble();
            arr_y[i] = scanner.nextDouble();
            arr_rad[i] = scanner.nextDouble();
            i++;
            }
        }
        catch(Exception e) {
            System.err.println("An error has occured. See below for details");
            e.printStackTrace();
        }

        //Get the average area value
        int length = circleCounter;
        double sum_rad_2 = 0;
        for (int i = 0; i < length; i ++){
            sum_rad_2 = sum_rad_2 + Math.pow(arr_rad[i], 2);
        }
        areaAverage = (Math.PI * sum_rad_2) / length;
        
        //Get the top right point and bottom left point of circles
        double x = 0;
        double y = 0;
        double r = 0;
        double x_max = arr_x[0];
        double y_max = arr_x[0];        
        double x_min = arr_y[0];
        double y_min = arr_y[0];
        for(int i = 0; i < circleCounter; i++){
            x = arr_x[i];
            y = arr_y[i];
            r = arr_rad[i];
            if (x + r > x_max){
                x_max = x + r;
            }
            if (y + r > y_max){
                y_max = y + r;
            }
            if (x - r < x_min){
                x_min = x - r;
            }
            if (y - r < y_min){
                y_min = y - r;
            }
        }
        aabb = new double[] {x_min, y_min, x_max, y_max};

        //Find out the largest value and the smallest value of r.
        double r_max = 0;
        double r_min = 0;
        for(int k = 0; k < circleCounter; k++){
            if (r_max < arr_rad[k]){
                r_max = arr_rad[k];
            }
            if (r_min > arr_rad[k]){
                r_min = arr_rad[k];
            }
        }
        Smax = Math.PI * Math.pow(r_max, 2);
        Smin = Math.PI * Math.pow(r_min, 2);

        //Find out the standard deviation of circles
        areaSD = 0;
        for(int i = 0; i < circleCounter; i++){
            areaSD = areaSD + Math.pow(Math.PI * Math.pow(arr_rad[i], 2) - areaAverage, 2);
        }
        areaSD = areaSD / circleCounter;
        areaSD = Math.pow(areaSD, 0.5);
       
        //find out the median of circles' area.
        double m_rad = 0;
        Arrays.sort(arr_rad);
        if (arr_rad.length % 2 == 0){
            m_rad = ((arr_rad[arr_rad.length / 2 - 1] + arr_rad[arr_rad.length / 2])) / 2;
        }
        else{
            m_rad = arr_rad[arr_rad.length / 2];
        }
        areaMedian = Math.PI * Math.pow(m_rad, 2);
    }

    /**
     * A function to calculate the avarage area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public static double averageCircleArea(Circle[] circles) {
        // You need to fill in this method
        int lengh = circles.length;
        double ans = 0;
        for(int i = 0; i < lengh; i++){
            ans = ans + Math.PI * Math.pow(circles[i].getRadius(), 2);
        }
        ans = ans / lengh;
        return ans;
    }
    
    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided. 
     * This array may contain 0 or more circles.
     * 
     * @param circles  An array of Circles
     */
    public static double areaStandardDeviation(Circle[] circles) {
      //You need to complete this method.
        int lengh = circles.length;
        double ans = 0.0;
        double av_area = averageCircleArea(circles);
        for(int i = 0; i < lengh; i++){
            ans = ans + Math.pow(Math.PI * Math.pow(circles[i].getRadius(), 2) - av_area, 2);
        }
        ans = ans / lengh;
        ans = Math.pow(ans, 0.5);
      return ans;
    }
    
    /**
     * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle
     * that surrounds the array of circles given.
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with
     *         the origin (bottom left) at [X1,Y1] and opposite corner (top right)
     *         at [X2,Y2]
     */
    public static double[] calculateAABB(Circle[] circles){ 
        // You need to fill in this method.
        int lengh = circles.length;
        double x;
        double y;
        double r;
        double x_max = circles[0].getCentre().getX();
        double y_max = circles[0].getCentre().getY();        
        double x_min = circles[0].getCentre().getX();
        double y_min = circles[0].getCentre().getY();
        if (lengh != 0){
            for(int i = 0; i < lengh; i++){
                x = circles[i].getCentre().getX();
                y = circles[i].getCentre().getY();
                r = circles[i].getRadius();
                if (x + r > x_max){
                    x_max = x + r;
                }
                if (y + r > y_max){
                    y_max = y + r;
                }
                if (x - r < x_min){
                    x_min = x - r;
                }
                if (y - r < y_min){
                    y_min = y - r;
                }
            }
        }
        return new double[] {x_min, y_min, x_max, y_max};
    }


  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        // You can use this method for testing.
        Circle[] mine;
        mine = new Circle[5];
        mine[0] = new Circle(1, 5, 5);
        mine[1] = new Circle(2, 6, 4);
        mine[2] = new Circle(3, 7, 3);
        mine[3] = new Circle(4, 8, 2);
        mine[4] = new Circle(5, 9, 1);
        // System.out.println(mine[2]);
        double[] x = calculateAABB(mine);
        System.out.println(x[0]);
        System.out.println(x[1]);
        System.out.println(x[2]);
        System.out.println(x[3]);
    }
}