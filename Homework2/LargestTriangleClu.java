//********************************************************************************
// File:    LargestTriangleClu.java
//
// The bellow program gives you the triangle with the maximum area from the graph.
//********************************************************************************

import edu.rit.pj2.Job;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.util.Instance;

/**
 * The class LargestTriangleClu creates a Job for the workers made in the comandline
 *
 * It creates an instance of the task class and passes it to the workers for
 * Them to process
 *
 *
 * usage: java pj2 jar=<jarfile> workers=<K> LargestTriangleClu "<pointspec>"
 *        a. <jarfile> is the name of a Java Archive file containing the compiled class files for your project.
 *        b. <K> is the number of worker tasks.
 *        c. <pointspec> is a constructor expression for a point specification object.
 *
 * @author  Nikhil Haresh Keswaney
 * @version 24-Oct-2018
 *
 */
public class LargestTriangleClu extends Job {
    /**
     * Main program.
     */
    public void main(String[] args) throws Exception {
        if(args.length < 1){
            usage();
        }
        String randomPointObject = args[0];
        PointSpec pointGenerator = null;
        try {
            pointGenerator = (PointSpec) Instance.newInstance(randomPointObject);
        }
        catch (Exception e){
            usage();
        }
        masterSchedule (proportional);
        masterChunk (100);
        masterFor(0, (pointGenerator.size() - 1) - 2, LargestTriangleTask.class).args(args);
        rule().atFinish().task(Reduce.class).args(args).runInJobProcess();
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("usage: java pj2 jar=<jarfile> workers=<K> LargestTriangleClu \"<pointspec>\"");
        System.err.println("       a. <jarfile> is the name of a Java Archive file containing the compiled class files for your project.\n" +
                           "       b. <K> is the number of worker tasks.\n" +
                           "       c. <pointspec> is a constructor expression for a point specification object.");
        terminate(1);
    }
    /**
     * The class LargestTriangleTask creates Multiple threads in a worker and then start
     * to run it on multiple threads utilizing the whole cluster capacity
     *
     * usage: java pj2 jar=<jarfile> workers=<K> LargestTriangleClu "<pointspec>"
     *        a. <jarfile> is the name of a Java Archive file containing the compiled class files for your project.
     *        b. <K> is the number of worker tasks.
     *        c. <pointspec> is a constructor expression for a point specification object.
     *
     * @author  Nikhil Haresh Keswaney
     * @version 20-Oct-2018
     *
     */
    private static class LargestTriangleTask extends Task{
        public LargestTriangleVBL globalxy;
        double xlist[] = null;
        double ylist[] = null;

        /**
         * Main program of the task class which is called by the job class.
         */
        public void main(final String[] args) throws Exception {
            String randomPointObject = args[0];
            PointSpec pointGenerator = (PointSpec) Instance.newInstance(randomPointObject);
            globalxy = new LargestTriangleVBL.Max(0 ,0 ,0, -Double.MAX_VALUE, -1, -1, -1, -1, -1, -1);
            xlist = new double[pointGenerator.size()];
            ylist = new double[pointGenerator.size()];

            for(int i = 0 ; i< xlist.length; i++){
                Point temp = pointGenerator.next();
                xlist[i] = temp.x;
                ylist[i] = temp.y;
            }
            workerFor().exec(new Loop() {

                LargestTriangleVBL localxy;
                public void start() {
                    localxy = threadLocal(globalxy);
                }

                public void run(int point1) throws Exception {
                    localxy.checkLastPoint(point1, xlist, ylist);
                }
            });
            putTuple(globalxy);
        }
    }

    /**
     * The class reduces reduces the result of each worker and combines them to
     * find the triangle with the maximum answer this will run on the job side and
     * not on the worker side.
     *
     * @author  Nikhil Haresh Keswaney
     * @version 20-Oct-2018
     *
     */
    private static class Reduce extends Task{
        /**
         * Main program of the Reduce class this will be called after the
         * program completes.
         */
        public void main(String[] args) throws Exception {
            LargestTriangleVBL globalxy = new LargestTriangleVBL.Max(0 ,0 ,0, -Double.MAX_VALUE, -1, -1, -1, -1, -1, -1);
            LargestTriangleVBL template = new LargestTriangleVBL.Max();
            LargestTriangleVBL temp;
            while ((temp = tryToTakeTuple(template)) != null){
                globalxy.reduce(temp);
            }
            System.out.printf ("%d %.5g %.5g%n", globalxy.p1, globalxy.x1, globalxy.y1);
            System.out.printf ("%d %.5g %.5g%n", globalxy.p2, globalxy.x2, globalxy.y2);
            System.out.printf ("%d %.5g %.5g%n", globalxy.p3, globalxy.x3, globalxy.y3);
            System.out.printf ("%.5g%n", globalxy.area);
        }
    }
}
