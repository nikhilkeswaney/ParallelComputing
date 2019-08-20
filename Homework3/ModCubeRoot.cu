//******************************************************************************
//
// File:    ModCubeRoot.cu
//
// This CUDA C file is the kernel function for the GPU to try and break the cipher
// key
//
//******************************************************************************

// Number of threads per block.
#define NT 1024
// Overall counter variable in global memory.
__device__ unsigned int incrementNumber;

/**
 * This kernel is used to break the RSA
 * @param  N  is a large integer, e.g. a 2048-bit integer.
 * @param  C  cipher key.
 * @param  possibleValues  array of possible answers.
 *
 * @author  Nikhil Keswaney
 * @version 11-15-2018
 */
extern "C" __global__ void DoBruteForce
   (unsigned long long int N, unsigned long long int C, unsigned long long int* possibleValues)
   {
   unsigned long long int thr, size, rank;
   unsigned long long int m;
   // Determine number of threads and this thread's rank.
   thr = threadIdx.x;
   size = gridDim.x*NT;
   rank = blockIdx.x*NT + thr;

   for (unsigned long long int i = rank; i < N; i += size)
      {
        m = (((i * i) % N) * i) % N;
        if(m == C){
          possibleValues[atomicAdd(&incrementNumber, 1)] = i;        
        }

      }

   }
