import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;
import java.io.IOException;
import java.util.ArrayList;

public class LargestTriangleVBL extends Tuple implements Vbl {

    int p1, p2, p3;
    double area;


    LargestTriangleVBL(int p1, int p2, int p3, Double area) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.area = area;
    }

    /**
     * Construct a new LemoinesVbl object.
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
        return new LargestTriangleVBL.Max(this.p1, this.p2, this.p3, this.area);
    }

    @Override
    public void writeOut(OutStream outStream) throws IOException {
        outStream.writeObject(this);
    }

    @Override
    public void readIn(InStream inStream) throws IOException {
        LargestTriangleVBL temp = (LargestTriangleVBL) inStream.readObject();
        this.p1 = temp.p1;
        this.p2 = temp.p2;
        this.p3 = temp.p3;
        this.area = temp.area;
    }

    /**
     * Set the p,q and n values of the Lemoines vbl.
     */
    @Override
    public void set(Vbl vbl) {
        this.p1 = ((LargestTriangleVBL) vbl).point1Index();
        this.p2 = ((LargestTriangleVBL) vbl).point2Index();
        this.p3 = ((LargestTriangleVBL) vbl).point3Index();
        this.area = ((LargestTriangleVBL) vbl).areaValue();

    }


    public void reduce(int p1, int p2, int p3, Double area) {
        throw new UnsupportedOperationException("reduce() not defined in base class LemoinesVbl; use a subclass");
    }

    public void reduce(Vbl var1) {
        this.reduce(((LargestTriangleVBL) var1).point1Index(),
                ((LargestTriangleVBL) var1).point2Index(),
                ((LargestTriangleVBL) var1).point3Index(),
                ((LargestTriangleVBL) var1).areaValue());
    }


    private int point1Index() {
        return this.p1;
    }


    private int point2Index() {
        return this.p2;
    }


    private int point3Index() {
        return this.p3;
    }

    private double areaValue(){
        return this.area;
    }
    public void checkLastPoint(ArrayList<Double> x, ArrayList<Double> y, int i,
                               int j, double side1){
        Double point1x = x.get(i);
        Double point1y = y.get(i);
        Double point2x = x.get(j);
        Double point2y = y.get(j);
        for(int k = 0; k < x.size(); k++){
            Double point3x = x.get(k);
            Double point3y = y.get(k);
            double side2 = eucledianDistance(point3x , point2x, point3y, point2y);
            double side3 = eucledianDistance(point3x , point1x, point3y, point1y);
            double triangleArea = this.calculateArea(side1, side2, side3);
            this.checkAndUpdate(i, j, k, triangleArea);
        }
    }

    private double calculateArea(double side1, double side2, double side3){
        double s = (side1 + side2 + side3)/2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    public double eucledianDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt((x1 - x2)*(x1 - x2)  + (y1 - y2)*(y1 - y2));
    }

    private void checkAndUpdate(int p1, int p2, int p3, double area) {
        if (area > this.area) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.area = area;
        }
    }

    public static class Max extends LargestTriangleVBL {
        public Max() {
        }

        Max(int p1, int p2, int p3, Double area) {
            super(p1, p2, p3, area);
        }


        public void reduce(int p1, int p2, int p3, Double area) {
            if (this.area < area) {
                this.p1 = p1;
                this.p2 = p2;
                this.p3 = p3;
                this.area = area;
            }
        }
    }
}
