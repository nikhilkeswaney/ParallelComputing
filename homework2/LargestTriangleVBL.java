//********************************************************************************
// File:    LargestTriangleVBL.java
//
// The bellow program is used for reduction of the largest triangle
//********************************************************************************

import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;
import java.io.IOException;

/**
 * The class LargestTriangleVBL is used for reduction of the largest triangle
 * and to pass to the tuple space so that the reduce task can give the final
 * result.
 *
 * @author  Nikhil Haresh Keswaney
 * @version 24-Oct-2018
 *
 */
public class LargestTriangleVBL extends Tuple implements Vbl {

    int p1, p2, p3;
    double area;
    double x1, y1;
    double x2, y2;
    double x3, y3;

    /**
     * Construct a new LargestTriangleVBL object with initializing all values.
     */
    LargestTriangleVBL(int p1, int p2, int p3, double area,
                       double x1, double y1, double x2, double y2, double x3, double y3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.area = area;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    /**
     * Construct a new LargestTriangleVBL object.
     */
    LargestTriangleVBL() {
    }

    /**
     * This method clones the object.
     *
     * @return The new cloned object.
     */
    @Override
    public LargestTriangleVBL clone() {
        LargestTriangleVBL clonObject = (LargestTriangleVBL) super.clone();
        return clonObject;
    }

    /**
     * This method writes in the tuple space
     * @param outStream Stream to write
     * @throws IOException
     */
    @Override
    public void writeOut(OutStream outStream) throws IOException {
        outStream.writeInt(p1);
        outStream.writeInt(p2);
        outStream.writeInt(p3);
        outStream.writeDouble(area);
        outStream.writeDouble(x1);
        outStream.writeDouble(y1);
        outStream.writeDouble(x2);
        outStream.writeDouble(y2);
        outStream.writeDouble(x3);
        outStream.writeDouble(y3);
    }
    /**
     * This method reads from the tuple space
     * @param inStream Stream to read.
     * @throws IOException
     */
    @Override
    public void readIn(InStream inStream) throws IOException {
        this.p1 = inStream.readInt();
        this.p2 = inStream.readInt();
        this.p3 = inStream.readInt();
        this.area = inStream.readDouble();
        this.x1 = inStream.readDouble();
        this.y1 = inStream.readDouble();
        this.x2 = inStream.readDouble();
        this.y2 = inStream.readDouble();
        this.x3 = inStream.readDouble();
        this.y3 = inStream.readDouble();
    }

    /**
     * Set the p1, p2, p3 i.e. the points with the largest area
     * area the largest area
     */
    @Override
    public void set(Vbl vbl) {
        this.p1 = ((LargestTriangleVBL) vbl).point1Index();
        this.p2 = ((LargestTriangleVBL) vbl).point2Index();
        this.p3 = ((LargestTriangleVBL) vbl).point3Index();
        this.area = ((LargestTriangleVBL) vbl).areaValue();
        this.x1 = ((LargestTriangleVBL) vbl).x1;
        this.y1 = ((LargestTriangleVBL) vbl).y1;
        this.x2 = ((LargestTriangleVBL) vbl).x2;
        this.y2 = ((LargestTriangleVBL) vbl).y2;
        this.x3 = ((LargestTriangleVBL) vbl).x3;
        this.y3 = ((LargestTriangleVBL) vbl).y3;
    }

    /**
     * Unsupporteed
     * @param p1    Point1 with the largest area
     * @param p2    Point2 with the largest area
     * @param p3    Point3 with the largest area
     * @param area  Largest area
     */
    public void reduce(int p1, int p2, int p3, double area,
                       double x1, double y1, double x2, double y2, double x3, double y3) {
        throw new UnsupportedOperationException("reduce() not defined in base class LargestTriangleVbl; use a subclass");
    }

    /**
     * Used for reduction
     * @param var1  Vbl class object.
     */
    public void reduce(Vbl var1) {
        this.reduce(((LargestTriangleVBL) var1).point1Index(),
                ((LargestTriangleVBL) var1).point2Index(),
                ((LargestTriangleVBL) var1).point3Index(),
                ((LargestTriangleVBL) var1).areaValue(),
                ((LargestTriangleVBL) var1).x1,
                ((LargestTriangleVBL) var1).y1,
                ((LargestTriangleVBL) var1).x2,
                ((LargestTriangleVBL) var1).y2,
                ((LargestTriangleVBL) var1).x3,
                ((LargestTriangleVBL) var1).y3);
    }

    /**
     * gives the 1st point from the max area triangle
     * @return point1
     */
    private int point1Index() {
        return this.p1;
    }

    /**
     * gives the 2nd point from the max area triangle
     * @return point2
     */
    private int point2Index() {
        return this.p2;
    }

    /**
     * gives the 3rd point from the max area triangle
     * @return point3
     */
    private int point3Index() {
        return this.p3;
    }
    /**
     * gives the max area of the triangle
     * @return area
     */
    private double areaValue(){
        return this.area;
    }

    /**
     * checks all the points with the given point
     * @param i given point
     */
    public void checkLastPoint(int i, double xlist[], double ylist[]){

        for(int j = i + 1; j < xlist.length - 1; j++) {
            double point1x = xlist[i];
            double point1y = ylist[i];
            double point2x = xlist[j];
            double point2y = ylist[j];
            double side1 = eucledianDistance(point1x, point2x, point1y, point2y);
            for (int k = j + 1; k < xlist.length; k++) {
                double point3x = xlist[k];
                double point3y = ylist[k];
                double side2 = eucledianDistance(point3x, point2x, point3y, point2y);
                double side3 = eucledianDistance(point3x, point1x, point3y, point1y);
                double triangleArea = this.calculateArea(side1, side2, side3);
                this.reduce(i, j, k, triangleArea, xlist[i], ylist[i], xlist[j], ylist[j], xlist[k], ylist[k]);
            }
        }
    }

    /**
     * Calculate the area of the given triangle
     * @param side1 length of side 1
     * @param side2 length of side 2
     * @param side3 length of side 3
     * @return  Area of the triangle
     */
    private double calculateArea(double side1, double side2, double side3){
        double s = (side1 + side2 + side3)/2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    /**
     * Eucledian distance between two points
     * @param x1    x of point 1
     * @param x2    x of point 2
     * @param y1    y of point 1
     * @param y2    y of point 2
     * @return distance between both the points
     */
    public double eucledianDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt((x1 - x2)*(x1 - x2)  + (y1 - y2)*(y1 - y2));
    }

    public static class Max extends LargestTriangleVBL {
        /**
         * Default contructor
         */
        public Max() {
        }

        Max(int p1, int p2, int p3, double area,
            double x1, double y1, double x2, double y2, double x3, double y3) {

            super(p1, p2, p3, area, x1, y1, x2, y2, x3, y3);
        }

        /**
         * This method saves the largest area from both the objects
         * @param p1    Point1 with the largest area
         * @param p2    Point2 with the largest area
         * @param p3    Point3 with the largest area
         * @param area  Largest area
         */
        public void reduce(int p1, int p2, int p3, double area,
                           double x1, double y1, double x2, double y2, double x3, double y3) {
            if (this.area < area) {
                assign(p1, p2, p3, area, x1, y1, x2, y2, x3, y3);
            }
            else if(this.area == area){
                if(p1 < this.p1){
                    assign(p1, p2, p3, area, x1, y1, x2, y2, x3, y3);
                }
                else if(p1 == this.p1){
                    if(p2 < this.p2){
                        assign(p1, p2, p3, area, x1, y1, x2, y2, x3, y3);
                    }
                    else if(p2 == this.p2){
                        if(p3 < this.p3){
                            assign(p1, p2, p3, area, x1, y1, x2, y2, x3, y3);
                        }
                    }
                }
            }
        }
        public void assign(int p1, int p2, int p3, double area,
                           double x1, double y1, double x2, double y2, double x3, double y3){
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.area = area;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.x3 = x3;
            this.y3 = y3;
        }
    }
}
