import edu.rit.pj2.Job;
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;
import edu.rit.util.Instance;

import java.util.ArrayList;

class LargestTriangleClu extends Job {

    public void main(String[] args) throws Exception {
        String randomPointObject = args[0];
        System.out.println(workers());
        RandomPoints pointGenerator = (RandomPoints) Instance.newInstance(randomPointObject);
        masterFor(0, pointGenerator.size() - 1, LargestTriangleTask.class).args(args);
        rule().atFinish().task(reduce.class).args(args);
    }


    private static void usage() {
        System.err.println("Usage: java pj2 LemoineSeq <lb> <ub>");
        System.err.println("       a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).\n" +
                "       b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).\n");
        terminate(1);
    }
}

class LargestTriangleTask extends Task{
    public LargestTriangleVBL globalxy;
    public void main(final String[] args) throws Exception {
        String randomPointObject = args[0];
        RandomPoints pointGenerator = (RandomPoints) Instance.newInstance(randomPointObject);
        final ArrayList<Double> xList = new ArrayList<Double>();
        final ArrayList<Double> yList = new ArrayList<Double>();

        while(pointGenerator.hasNext()){
            Point temp = pointGenerator.next();
            xList.add(temp.x);
            yList.add(temp.y);
        }

        globalxy = new LargestTriangleVBL.Max(0 ,0 ,0, 0.0);
        workerFor().exec(new Loop() {
            public void run(int i) throws Exception {
                final int finalI = i;
                parallelFor(0, xList.size() - 1).exec(new Loop() {
                    LargestTriangleVBL localxy;
                    public void start() {
                        localxy = threadLocal(globalxy);
                    }
                    @Override
                    public void run(int j) throws Exception {
                        Double point1x = xList.get(finalI);
                        Double point1y = yList.get(finalI);
                        Double point2x = xList.get(j);
                        Double point2y = yList.get(j);
                        double side1 = localxy.eucledianDistance(point1x, point2x, point1y, point2y);
                        localxy.checkLastPoint(xList, yList, finalI, j, side1);
                    }
                });
            }
        });
        putTuple(globalxy);
    }
}

class reduce extends Task{

    @Override
    public void main(String[] args) throws Exception {
        String randomPointObject = args[0];
        RandomPoints pointGenerator = (RandomPoints) Instance.newInstance(randomPointObject);
        final ArrayList<Double> xList = new ArrayList<Double>();
        final ArrayList<Double> yList = new ArrayList<Double>();
        while(pointGenerator.hasNext()){
            Point temp = pointGenerator.next();
            xList.add(temp.x);
            yList.add(temp.y);
        }
        LargestTriangleVBL globalxy = new LargestTriangleVBL.Max(0 ,0 ,0, 0.0);
        LargestTriangleVBL template = new LargestTriangleVBL.Max();
        LargestTriangleVBL temp;
        while ((temp = tryToTakeTuple(template)) != null){
            globalxy.reduce(temp);
        }
        System.out.printf ("%d %.5g %.5g%n", globalxy.p1, xList.get(globalxy.p1), yList.get(globalxy.p1));
        System.out.printf ("%d %.5g %.5g%n", globalxy.p2, xList.get(globalxy.p2), yList.get(globalxy.p2));
        System.out.printf ("%d %.5g %.5g%n", globalxy.p3, xList.get(globalxy.p3), yList.get(globalxy.p3));
        System.out.printf ("%.5g%n", globalxy.area);
    }
}