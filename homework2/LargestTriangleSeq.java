//********************************************************************************
// File:    LargestTriangleSeq.java
//
// The bellow program gives you the triangle with the maximum area from the graph.
//********************************************************************************

import edu.rit.pj2.Task;
import edu.rit.util.Instance;

/**
 * The class LargestTriangleSeq finds the maximum area from a given graph
 *
 * It creates an instance of the class you pass in the comand line arguments and then
 * checks each and every possible combination of the of three points in the graph
 *
 * Usage: java pj2 jar=<jarfile> LargestTriangleSeq "<pointspec>"
 *        a. <jarfile> is the name of a Java Archive file containing the compiled class files for your project.
 *        b. <pointspec> is a constructor expression for a point specification object.
 *
 * @author  Nikhil Haresh Keswaney
 * @version 24-Oct-2018
 *
 */
public class LargestTriangleSeq extends Task {
    public LargestTriangleVBL globalxy;
    double xlist[] = null;
    double ylist[] = null;
    /**
     * Main program.
     */
    public void main(final String[] args) throws Exception {
        if (args.length < 1)
            usage();
        String randomPointObject = args[0];
        PointSpec pointGenerator = null;

        try {
            pointGenerator = (PointSpec) Instance.newInstance(randomPointObject);
        }
        catch (Exception e){
            usage();
        }
        globalxy = new LargestTriangleVBL.Max(0 ,0 ,0, -Double.MAX_VALUE, -1, -1, -1, -1, -1, -1);
        xlist = new double[pointGenerator.size()];
        ylist = new double[pointGenerator.size()];

        for(int i = 0 ; i< xlist.length; i++){
            Point temp = pointGenerator.next();
            xlist[i] = temp.x;
            ylist[i] = temp.y;
        }
        for(int i = 0; i < pointGenerator.size() - 2; i++) {
            globalxy.checkLastPoint(i, xlist, ylist);
        }
        System.out.printf ("%d %.5g %.5g%n", globalxy.p1, globalxy.x1, globalxy.y1);
        System.out.printf ("%d %.5g %.5g%n", globalxy.p2, globalxy.x2, globalxy.y2);
        System.out.printf ("%d %.5g %.5g%n", globalxy.p3, globalxy.x3, globalxy.y3);
        System.out.printf ("%.5g%n", globalxy.area);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("Usage: java pj2 jar=<jarfile> LargestTriangleSeq \"<pointspec>\"");
        System.err.println("       a. <rp> Is the random point interface.\n" +
                           "       b. <pointspec> is a constructor expression for a point specification object.");
        terminate(1);
    }

    /**
     * Specify that this task requires one core.
     */
    protected static int coresRequired()
    {
        return 1;
    }
}