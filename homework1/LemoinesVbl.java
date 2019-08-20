//********************************************************************
// File:    LemoinesVbl.java
//
// The Bellow code is used for reduction of the Lemoine's conjecture.
//********************************************************************

import edu.rit.pj2.Vbl;

/**
 * Class LemoinesVbl provides a Lemoines Variable reduction variable shared by multiple
 * threads executing a parallel for statement.
 * <p>
 * For class LemoinesVbl each thread has a global copy and a thread local copy and then it reduces
 * the global copy to the equation with the MAXIMUM value of the p.
 * <p>
 * 1 subclass is provided in which the equation with the maximum p value is stored.
 *
 * @author Nikhil Haresh Keswaney
 * @version 25-Sept-2018
 */
public class LemoinesVbl implements Vbl {

    // Shared value of Lemoines equation
    public int p, q, n;

    /**
     * Construct a new LemoinesVbl object with the given values of p q and n.
     *
     * @param p value of first prime
     * @param q value of second prime which will be multiplied by 2
     * @param n value of the number to form
     */

    public LemoinesVbl(int p, int q, int n) {
        this.p = p;
        this.q = q;
        this.n = n;
    }

    /**
     * Construct a new LemoinesVbl object.
     */
    public LemoinesVbl() {
    }

    /**
     * This method clones the object.
     *
     * @return The new cloned object.
     */
    @Override
    public LemoinesVbl clone() {
        return new LemoinesVbl.Max(this.p, this.q, this.n);
    }

    /**
     * Set the p,q and n values of the Lemoines vbl.
     */
    @Override
    public void set(Vbl vbl) {
        this.p = ((LemoinesVbl) vbl).intPValue();
        this.q = ((LemoinesVbl) vbl).intQValue();
        this.n = ((LemoinesVbl) vbl).intNValue();
    }


    public void reduce(int p, int q, int n) {
        throw new UnsupportedOperationException("reduce() not defined in base class LemoinesVbl; use a subclass");
    }

    /**
     * Reduce the given shared variable into this shared variable. The two
     * variables are combined together using this shared variable's reduction
     * operation, and the result is stored in this shared variable.
     *
     * @param var1 Shared variable.
     * @throws ClassCastException (unchecked exception) Thrown if the class of is not
     *                            compatible with the class of this shared variable.
     */
    public void reduce(Vbl var1) {
        this.reduce(((LemoinesVbl) var1).intPValue(),
                ((LemoinesVbl) var1).intQValue(),
                ((LemoinesVbl) var1).intNValue());
    }

    /**
     * Setter for getting the p value.
     *
     * @return p for the equation object.
     */
    private int intPValue() {
        return this.p;
    }

    /**
     * Setter for getting the q value.
     *
     * @return q for the equation object.
     */
    private int intQValue() {
        return this.q;
    }

    /**
     * Setter for getting the n value.
     *
     * @return n for the equation object.
     */
    private int intNValue() {
        return this.n;
    }

    /**
     * This function updates the p q and n values according to the maximum p value.
     *
     * @param p new p value.
     * @param q new q value.
     * @param n new n value.
     */
    public void checkAndUpdate(int p, int q, int n) {
        if (p >= this.p) {
            this.p = p;
            this.q = q;
            this.n = n;
        }
    }

    /**
     * This method checks the next smallest q value and updates the local vbl
     * if the current copy is smaller
     * @param iterateRange Prime iterator
     * @param numberToCheck the number to verify the Lemoines variable
     */
    public void checkNextAndUpdate(Prime.Iterator iterateRange, int numberToCheck) {
        // Iterate through all the prime numbers until we find a
        // prime number p for which q is also a prime
        int tempP = 0;
        int p = 0;
        int q = 0;

        while (tempP < numberToCheck) {
            tempP = iterateRange.next();
            int tempQ = (numberToCheck - tempP) >> 1;
            if (Prime.isPrime(tempQ)) {
                p = tempP;
                q = tempQ;
                break;
            }
        }

        // Checking if the p we got for number n is the maximum or not.
        this.checkAndUpdate(p, q, numberToCheck);
    }

    /**
     * Class LemoinesVbl.Max provides an equation with
     * maximum p as the reduction operation.
     *
     * @author Nikhil Haresh Keswaney
     * @version 25-Sept-2018
     */
    public static class Max extends LemoinesVbl {
        public Max() {
        }

        /**
         * Construct a new shared long integer variable with the given initial
         * p,q,n value.
         *
         * @param p p value for the equation value.
         * @param q q value for the equation value.
         * @param n n value for the equation value.
         */
        public Max(int p, int q, int n) {
            super(p, q, n);
        }

        /**
         * Reduce the given value of p, q and n and reduces the
         * equation which has the maximum value of p
         *
         * @param p p value of the equation.
         * @param q q value of the equation.
         * @param n n value of the equation.
         */
        public void reduce(int p, int q, int n) {
            if (this.p < p) {
                this.p = p;
                this.q = q;
                this.n = n;
            }
            if (this.p == p) {
                if (n > this.n) {
                    this.p = p;
                    this.q = q;
                    this.n = n;
                }
            }
        }
    }
}
