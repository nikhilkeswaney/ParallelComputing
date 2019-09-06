//************************************************************************
// File:    LemoineSmp.java
//
// The Bellow code is a Parallel version of the program that verifies the
// lemoines conjecture.
//************************************************************************

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
 * Usage: java LemoineSmp <lb> <ub>
 *        a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).
 *        b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).
 *
 */
public class LemoineSmp {

    /**
     * Main program.
     */
    public static void main(String[] args) throws Exception {

        if (args.length < 2)
            usage();

        int lowerLimit = 0;
        int upperLimit = 0;

        try {
            lowerLimit = Integer.parseInt(args[0]);

        } catch (Exception e) {
            System.err.println("Please enter the lowerLimit as Integer");
            return;
        }

        if (lowerLimit % 2 == 0 || lowerLimit < 5) {
            System.err.println("Please enter the lower limits as odd number > 5");
            return;
        }

        try {
            upperLimit = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println("Please enter the upperlimit as Integer");
            return;
        }

        if (upperLimit % 2 == 0) {
            System.err.println("Please enter the upper limits as odd number");
            return;
        }

        if (upperLimit < lowerLimit) {
            System.err.println("Please enter the upper limit such that upperlimit > lowerlimit");
            return;
        }
        LemoinesVbl[] globalpqn = new LemoinesVbl[Runtime.getRuntime().availableProcessors()];

        // omp parallel
        {

            //Prime Iterator for each thread.
            Prime.Iterator iterateRange = new Prime.Iterator();

            globalpqn[OMP4J_THREAD_NUM] = new LemoinesVbl(0, 0, 0);
            LemoinesVbl localpqn = globalpqn[OMP4J_THREAD_NUM];

            for (int numberToCheck = lowerLimit + (OMP4J_THREAD_NUM * 2);
                     numberToCheck <= upperLimit;
                     numberToCheck += (OMP4J_NUM_THREADS * 2)) {

                iterateRange.restart();
                localpqn.checkNextAndUpdate(iterateRange, numberToCheck);
            }

            // omp barrier
            {}

            for (int i = OMP4J_NUM_THREADS / 2; i > 0; i >>= 1) {
                if (OMP4J_THREAD_NUM < i)
                    globalpqn[OMP4J_THREAD_NUM].reduce(globalpqn[OMP4J_THREAD_NUM + i]);

                // omp barrier
                {}
            }
        }
        System.out.println(globalpqn[0].n + " = " + globalpqn[0].p + " + 2*" + globalpqn[0].q);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("Usage: java LemoineSeq <lb> <ub>");
        System.err.println("       a. <lb> is the lower bound integer to be examined (an odd integer greater than 5, type int).\n" +
                           "       b. <ub> is the upper bound integer to be examined (an odd integer greater than or equal to <lb>, type int).\n");
        System.exit(0);
    }
}