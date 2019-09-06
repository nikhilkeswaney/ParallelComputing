public class LemoinesVbl{

    // Shared value of Lemoines equation
    public int p, q, n;



    public LemoinesVbl(int p, int q, int n) {
        this.p = p;
        this.q = q;
        this.n = n;
    }


    public LemoinesVbl() {
    }


    private int intPValue() {
        return this.p;
    }


    private int intQValue() {
        return this.q;
    }


    private int intNValue() {
        return this.n;
    }


    public void checkAndUpdate(int p, int q, int n) {
        if (p >= this.p) {
            this.p = p;
            this.q = q;
            this.n = n;
        }
    }


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

        this.checkAndUpdate(p, q, numberToCheck);
    }

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

    public void reduce(LemoinesVbl vbl){
        this.reduce(vbl.intPValue(), vbl.intQValue(), vbl.intNValue());
    }
}
