package ca.pringle.library;


import java.util.Date;

/**
 * Random number generator, with different distributions.
 */


public final class Random {
    //based on results of Fisherman and Moore. they recommend
    //x = (69621*x) % 2147483647 and x = (48271*x) % 2147483647
    //where x = (a*x) % m;
    private static final int A = 48271;
    private static final int M = 2147483647;
    private static final int Q = M / A;
    private static final int R = M % A;
    public final int seed;
    public final Discrete discrete;
    private int x;


    /**
     * sets the seed to the current time. Recommend using Random(long) constructor
     * for reproducable results.
     */
    public Random() {
        Date si = new Date();
        seed = (int) Math.abs((int) si.getTime());
        x = seed;
        discrete = new Discrete();
    }


    /**
     * takes a seed to start the pseudo-ca.pringle.random sequence.
     */
    public Random(int seedParam) {
        seed = seedParam;
        x = seed;
        discrete = new Discrete();
    }

    private double nextRandomNumber() {
        //the following code prevents overflow
        int n = A * (x % Q) - R * (x / Q);

        if (n > 0) {
            x = n;
        } else {
            x = n + M;
        }

        return (double) x / (double) M;
    }

    public final class Discrete {

        /**
         * generates the next pseudo-ca.pringle.random number in the sequence. Will be in the range [0..high-1].
         */
        public int nextInt(int high) {
            return nextUniform(0, high - 1);
        }


        /**
         * generates the next discrete Geometric number. Exponential distribution. See a stats
         * text for a decent explanation of how this thing works.
         */
        public int nextGeometric(double probability) {
            double u = nextRandomNumber();

            return Math.floor(Math.ln(1 - u) / Math.ln(1 - probability));
        }


        /**
         * generates the next discrete Poisson number. Exponential distribution. See a stats
         * text for a decent explanation of how this thing works.
         */
        public int nextPoisson(int exponent) {
            double b = 1, u;
            int num = -1;

            do {
                u = nextRandomNumber();
                b = b * u;
                num = num + 1;
            }
            while (b >= Math.exp(Math.E, -exponent));


            return num;
        }


        /**
         * generates the next discrete Bernoulli number. has a 1-probability chance of being 0,
         * and a probablity chance of being 0. Restriction: 0 <= probability <= 1.
         */
        public int nextBernoulli(double probability) {
            double u = nextRandomNumber();

            if (u > 0 && u < (1 - probability)) return 0;
            return 1;
        }


        /**
         * generates the next discrete Binomial number. Produces t Bernoulli numbers with
         * probability 'probability' and sums them up.
         */
        public int nextBinomial(double probability, int number) {
            int n = 0;

            for (int i = 0; i < number; i++) {
                n = n + nextBernoulli(probability);
            }

            return n;
        }


        /**
         * generates the next discrete Uniform number. Will be in the range [low..high].
         */
        public int nextUniform(int low, int high) {
            double r = nextRandomNumber();
            int range = high - low + 1;

            return low + Math.floor(range * r);
        }

    }    //End of class Discrete

}    //End of class Random