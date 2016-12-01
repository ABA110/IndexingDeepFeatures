package deep;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

public class LSHIndexer {

	/*Returns the bucket ID the images should go in*/
public int hash(ImgDescriptor desc, int[] positions){
		
		int res = 0;
				
		/*Initialize mask to 0*/
				
		BitSet features = BitSet.valueOf(desc.getFeatures());
		//System.out.println(features.cardinality());
		BitSet result = new BitSet(Parameters.LSH_BITS);
		
		for(int i = 0; i < Parameters.LSH_BITS; i++)
			if(features.get(positions[i])) //If the bit in the position indicated by positions[i] is set to 1
					result.set(i);		   //We also set the corresponding bit in the result to 1

		/*Conversion of result from BitSet tyoe to integer type, that corresponds to the number of the bucket 
		 * the image should go in*/
		for(int i = 0 ; i < Parameters.LSH_BITS; i++)
	        if(result.get(i))
	            res |= (1 << i);  //OR and left bit shift
	    				
			
		return res;
	}

	/*Returns an array of integers that indicate the bits we have to consider for LSH indexing*/
	public int[] bitPositions(){
		BitSet mask = new BitSet(Parameters.FEATURE_LENGTH);
		mask.clear();
		
		
		int[] positions = new int[Parameters.LSH_BITS]; //Array to store the randomly selected bits for hashing
		Random rand = new Random();
		
		// for 1 single G()
		for(int i = 0; i < Parameters.LSH_BITS;){ 
			int toSet = rand.nextInt(4096);
			if(!mask.get(toSet)){				// we are trying not to let the program to 
												//choose the same bits on next iterations
					mask.set(toSet);			// so we have different place of bits for different trials
					positions[i] = toSet;
					i++;
			}
			
		}
		
		Arrays.sort(positions);
		
		return positions;
	}
}


/*private int hash(){
		
		int res = 0;
				
		/*Initialize mask to 0
		BitSet mask = new BitSet(Parameters.FEATURE_LENGTH);
		mask.clear();
		
		
		int[] positions = new int[Parameters.LSH_BITS]; //Array to store the randomly selected bits for hashing
		Random rand = new Random();
		for(int i = 0; i < Parameters.LSH_BITS;){
			int toSet = rand.nextInt(4096);
			if(!mask.get(toSet)){				// we are trying not to let the program to 
												//choose the same bits on next iterations
					mask.set(toSet);			// so we have different place of bits for different trials
					positions[i] = toSet;
					i++;
			}
			
		}
		
		positions[0] = 0;
		positions[1] = 3;
		Arrays.sort(positions);
		for(int i = 0; i < Parameters.LSH_BITS; i++)
			System.out.println(positions[i]); //ARRAY OF RANDOM POSITIONS TEST
		
		//BitSet features = BitSet.valueOf(desc.getFeatures());
		long tryhard[] = new long[1];
		tryhard[0] = 0x0000000EL;
		
		BitSet features = BitSet.valueOf(tryhard);
		BitSet result = new BitSet(Parameters.LSH_BITS);
		
		
		for(int i = 0; i < Parameters.LSH_BITS; i++)
			if(features.get(positions[i]))
					result.set(i);
		
		/*BitStream to integer conversion
		for(int i = 0 ; i < Parameters.LSH_BITS; i++)
	        if(result.get(i))
	            res |= (1 << i);
	    				
		System.out.println("res = " + res);	
		return res;
	}
}*/

	
