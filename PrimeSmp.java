import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;

/**
 * Class PrimeSmp is an SMP parallel program that tests numbers for primality.
 * It prints the numbers on the command line that are prime.
 * <P>
 * Usage: <TT>java pj2 edu.rit.pj2.example.PrimeSmp <I>number</I> ...</TT>
 *
 * @author  Alan Kaminsky
 * @version 14-Mar-2014
 */
public class PrimeSmp
   extends Task
   {

// Main program.

   /**
    * Main program.
    */
   public void main
      (final String[] args)
      throws Exception
      {
      // Validate command line arguments.
      if (args.length < 1) usage();

      // Test numbers for primality.
      parallelFor (0, args.length - 1) .exec (new Loop()
         {
         public void run (int i)
            {
            if (isPrime (Long.parseLong (args[i])))
               System.out.printf ("%s%n", args[i]);
            }
         });
      }

// Hidden operations.

   /**
    * Test the given number for primality.
    *
    * @param  x  Number &ge; 3.
    *
    * @return  True if x is prime, false otherwise.
    */
   private static boolean isPrime
      (long x)
      {
      if (x % 2 == 0) return false;
      long p = 3;
      long psqr = p*p;
      while (psqr <= x)
         {
         if (x % p == 0) return false;
         p += 2;
         psqr = p*p;
         }
      return true;
      }

   /**
    * Print a usage message and exit.
    */
   private static void usage()
      {
      System.err.println ("Usage: java pj2 edu.rit.pj2.example.PrimeSmp <number> ...");
      terminate (1);
      }

   }
