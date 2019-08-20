//**************************************************************************
//
// File:    LemoineSeq.java
//
// The Bellow code is a Sequential version of the program that verifies the
// Lemoines conjecture.
//**************************************************************************

import edu.rit.pj2.Task;

/**
 *
 * The class LemoineSeq verifies the Lemoine's Conjecture from the given lower
 * limit to the given upper limit using the formulae
 *
 * n = p + 2q for some primes p and q. The primes p and q might or might not be the
 * same. Note that p must be odd; q might be odd or even. There might be more than one
 * solution to the formula and it finds the solution with the minimum p.
 *
 * The class prints the equation of the number which has the MAX value of the p of all odd
 * numbers from the lower limit to the upper limit.
 *
 * Usage: java pj2 LemoineSeq <lb> <ub>
 *        a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).
 *        b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>.
 *
 * @author  Nikhil Haresh Keswaney.
 * @version 25-Sept-2018.
 *
 */
public class LemoineSeq extends Task {

    // Global reduction variable.
    public LemoinesVbl globalpqn;
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
        if (lowerLimit % 2 == 0 || lowerLimit < 5) {
            System.err.println("Please enter the lower limits as odd number > 5");
            terminate(1);
        }
        try {
            upperLimit = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println("Please enter the upperlimit as Integer");
            terminate(1);
        }
        if (upperLimit % 2 == 0) {
            System.err.println("Please enter the upper limits as odd number");
            terminate(1);
        }
        if (upperLimit < lowerLimit) {
            System.err.println("Please enter the upper limit such that upperlimit > lowerlimit");
            terminate(1);
        }
        globalpqn = new LemoinesVbl.Max(0, 0, 0);
        Prime.Iterator iterateRange = new Prime.Iterator();

        for (int numberToCheck = lowerLimit; numberToCheck <= upperLimit; numberToCheck += 2) {
            iterateRange.restart();
            globalpqn.checkNextAndUpdate(iterateRange,numberToCheck);
        }
        System.out.println(globalpqn.n + " = " + globalpqn.p + " + 2*" + globalpqn.q);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("Usage: java pj2 LemoineSeq <lb> <ub>");
        System.err.println("       a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).\n" +
                "       b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).\n");
        terminate(1);
    }
}