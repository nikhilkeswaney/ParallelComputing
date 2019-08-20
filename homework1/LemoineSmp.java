//************************************************************************
// File:    LemoineSmp.java
//
// The Bellow code is a Parallel version of the program that verifies the
// lemoines conjecture.
//************************************************************************

import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

/**
 * The class LemoineSmp verifies the Lemoine's Conjecture from the given lower
 * limit to the given upper limit using the formulae
 *
 * n = p + 2q for some primes p and q. The primes p and q might or might not be the
 * same. Note that p must be odd; q might be odd or even. There might be more than one
 * solution to the formula and it finds the solution with the minimum p.
 *
 * The class prints the equation of the number which has the MAX value of the p of all odd
 * numbers from the lower limit to the upper limit.
 *
 * Usage: java pj2 LemoineSmp <lb> <ub>
 *        a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).
 *        b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).
 *
 * Optional ussage: java pj2 cores=<K> LemoineSmp <lb> <ub>
 *                  a. <K> is the number of CPU cores to use; by default, this is also the number of threads in the parallel thread team.
 *                  b. <lb> is the lower bound integer to be examined (an odd integer greater than 5).
 *                  c. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>).
 *
 * @author  Nikhil Haresh Keswaney
 * @version 25-Sept-2018
 *
 */
public class LemoineSmp extends Task {
    private LemoinesVbl globalpqn;

    /**
     * Main program.
     */
    public void main(final String[] args) throws Exception {
        if (args.length < 2)
            usage();

        int lowerLimit = 0;
        int upperLimit = 0;

        try {
            lowerLimit = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Please enter the lowerLimit as Integer");
            terminate(1);
        }
        if (lowerLimit % 2 == 0 || lowerLimit <= 5) {
            System.err.println("Please enter the lower limits as odd number > 5");
            terminate(1);
        }
        try {
            upperLimit = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println("Please enter the upperlimit as Integer");
            terminate(1);
        }
        if (upperLimit% 2 == 0) {
            System.err.println("Please enter the upper limits as odd number");
            terminate(1);
        }
        if (upperLimit < lowerLimit) {
            System.err.println("Please enter the upper limit such that upperlimit > lowerlimit");
            terminate(1);
        }

        final int lowerLimitFinal = lowerLimit;
        final int upperLimitFinal = upperLimit;
        int lowerLimitRange = 0;
        int upperLimitRange = (upperLimit - lowerLimit) / 2;

        // Maintaining a global maximum for all the threads.
        globalpqn = new LemoinesVbl.Max(0, 0, 0);

        parallelFor(lowerLimitRange, upperLimitRange).exec(new Loop() {
            LemoinesVbl threadLocalpqn;

            //Prime Iterator for each thread.
            Prime.Iterator iterateRange;

            public void start() {
                iterateRange = new Prime.Iterator();

                //Local variable for each thread.
                threadLocalpqn = threadLocal(globalpqn);
                iterateRange.restart();
            }

            @Override
            public void run(int numberToCheckCounter) throws Exception {
                int numberToCheck = lowerLimitFinal + numberToCheckCounter * 2;
                if (numberToCheck <= upperLimitFinal) {
                    iterateRange.restart();
                    threadLocalpqn.checkNextAndUpdate(iterateRange, numberToCheck);
                }
            }
        });
        System.out.println(globalpqn.n + " = " + globalpqn.p + " + 2*" + globalpqn.q);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("Usage: java pj2 LemoineSmp <lb> <ub>");
        System.err.println("       a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).\n" +
                           "       b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).\n");
        terminate(1);
    }

}
