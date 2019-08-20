//******************************************************************************
//
// File:    ModCubeRoot.java
//
// This file breaks the encryption of a 2048 bit integer gives arounnd 1-3 values of
// m
// c = m^3 (mod n)
// m = c^1/3 (mod n)
//
//******************************************************************************
import edu.rit.gpu.*;
import edu.rit.pj2.Task;
import java.util.Arrays;
import edu.rit.util.Sorting;


/**
 * This class breaks the RSA encryption and returns around 1-3 possible values
 * of m from the formulae
 * c = m^3 (mod n)
 *
 * Author: Nikhil Keswaney
 * version: 11-15-2018
 */
public class ModCubeRoot extends Task {
    /**
     * Kernel function interface.
     */
    private static interface ModCubeRootKernel
            extends Kernel {
        public void DoBruteForce
                (long N, long c, GpuLongArray possibleValues);
    }

    /**
     * Task main program.
     */
    public void main(String[] args) throws Exception {
        // Validate command line arguments.
        if (args.length != 2) usage();
        long c = 0, N = 0;
        try {
            c = Long.parseLong(args[0]);
            N = Long.parseLong(args[1]);
        } catch (Exception e){
            usage();
        }


        String printString = "<m>^3 = <c> (mod <p>)";
        String printStringUnc = "No cube roots of <c> (mod <p>)";
        // Initialize GPU.
        Gpu gpu = Gpu.gpu();
        gpu.ensureComputeCapability(2, 0);

        // Set up GPU counter variable.
        Module module = gpu.getModule("ModCubeRoot.ptx");
        GpuLongArray possibleValues = gpu.getLongArray(3);
        ModCubeRootKernel kernel = module.getKernel(ModCubeRootKernel.class);
        kernel.setBlockDim(1024);
        kernel.setGridDim(gpu.getMultiprocessorCount());
        kernel.setCacheConfig (CacheConfig.CU_FUNC_CACHE_PREFER_L1);        
        GpuIntVbl incrementNumber = module.getIntVbl("incrementNumber");
        incrementNumber.item = 0;
        incrementNumber.hostToDev();
        kernel.DoBruteForce(N, c, possibleValues);
        incrementNumber.devToHost();
        possibleValues.devToHost();
        Sorting.sort(possibleValues.item, 0, incrementNumber.item);
        if (incrementNumber.item == 0) {
            System.out.println(printStringUnc.replace("<c>",
                    "" + c).replace("<p>", "" + N));
        } else {
            for (int i = 0; i < incrementNumber.item; i++) {
                System.out.println(printString.replace("<m>",
                        "" + possibleValues.item[i]).replace("<c>",
                        "" + c).replace("<p>", "" + N));
            }
        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage() {
        System.err.println("java pj2 ModCubeRoot <c> <n>");
        System.err.println("a. <c> is the number whose modular cube root(s) are to be found; it " +
                "must be a decimal integer (type int) in the range 0 ≤ c ≤ n−1.");
        System.err.println("b. <n> is the modulus; it must be a decimal integer (type int) ≥ 2.");
        terminate(1);
    }

    protected static int coresRequired() {
        return 1;
    }

    /**
     * Specify that this task requires one GPU accelerator.
     */
    protected static int gpusRequired() {
        return 1;
    }

}
