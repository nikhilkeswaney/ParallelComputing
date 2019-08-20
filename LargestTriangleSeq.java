import edu.rit.pj2.Task;
import edu.rit.util.Instance;

import java.util.ArrayList;

class LargestTriangleSeq extends Task {
    public LargestTriangleVBL globalxy;

    public void main(final String[] args) throws Exception {
        if (args.length < 1)
            usage();
        String rondomPointObject = args[0];
        RandomPoints pointGenerator = (RandomPoints) Instance.newInstance(rondomPointObject);
        ArrayList<Double> xList = new ArrayList<Double>();
        ArrayList<Double> yList = new ArrayList<Double>();

        while(pointGenerator.hasNext()){
            Point temp = pointGenerator.next();
            xList.add(temp.x);
            yList.add(temp.y);
        }
        globalxy = new LargestTriangleVBL(0 ,0 ,0, 0.0);
        for(int i = 0; i < xList.size(); i++) {

            for(int j =0; j < xList.size(); j++){
                Double point1x = xList.get(i);
                Double point1y = yList.get(i);
                Double point2x = xList.get(j);
                Double point2y = yList.get(j);
                double side1 = Math.sqrt((point1x-point2x)*(point1x-point2x) + (point1y-point2y)*(point1y-point2y));
                globalxy.checkLastPoint(xList, yList, i, j, side1);
            }
        }
        System.out.printf ("%d %.5g %.5g%n", globalxy.p1, xList.get(globalxy.p1), yList.get(globalxy.p1));
        System.out.printf ("%d %.5g %.5g%n", globalxy.p2, xList.get(globalxy.p2), yList.get(globalxy.p2));
        System.out.printf ("%d %.5g %.5g%n", globalxy.p3, xList.get(globalxy.p3), yList.get(globalxy.p3));
        System.out.printf ("%.5g%n", globalxy.area);
    }


    private static void usage() {
        System.err.println("Usage: java pj2 LemoineSeq <lb> <ub>");
        System.err.println("       a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).\n" +
                "       b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).\n");
        terminate(1);
    }
}