package deep;

import java.io.Serializable;
import java.util.BitSet;

public class ImgDescriptor implements Serializable, Comparable<ImgDescriptor> {

	private static final long serialVersionUID = 1L;
	
	private long[] binarizedVector; // image feature
	
	public String id; // unique id of the image (usually file name)
	
	public String sourceFile;
	
	public long dist; // used for sorting purposes
	
	public ImgDescriptor(float[] features, String id, String sourcefile) {
		this.id = id;
		float norm2 = evaluateNorm2(features);
		this.binarizedVector = getBinarizedVector(features, norm2);
		this.sourceFile = sourcefile;
	}
	
	public long[] getFeatures() {
		return binarizedVector;
	}
	
    // compare with other friends using distances
	@Override
	public int compareTo(ImgDescriptor arg0) {
		return new Long(dist).compareTo(arg0.dist);
	}
	
		
	public int hammingDistance(long[] features){
		int dist = 0;
		
		for(int i=0; i < this.binarizedVector.length; i++)
			dist += Long.bitCount(this.binarizedVector[i] ^ features[i]);
	
		
		return dist;
	}
	
	//TODO
	private long[] getBinarizedVector(float[] vector, float norm2) {
		long[] binarizedVector = new long[vector.length/Long.SIZE];
		//System.out.println("Long.SIZE= " + Long.SIZE);
		
		BitSet bitSetVector = new BitSet(vector.length); //4096 bitset, since vector.length=4096
		
		
		//Normalize the vector values by means of its norm 2
		for (int i = 0; i < vector.length; i++){
			//System.out.println(vector[i]/ norm2);
			if(vector[i] > 0) bitSetVector.set(i);
							
		}
		
		binarizedVector = bitSetVector.toLongArray();
		return binarizedVector;
	}
	
	//TODO
	private float evaluateNorm2(float[] vector) {
		float norm2 = 0;
		
		//Evaluate the norm 2 of the vector
		for (int i = 0; i < vector.length; i++){
			norm2 += Math.pow(vector[i], 2);
		}
		norm2 = (float) Math.sqrt(norm2);
		//System.out.println("norm2= " + norm2);
		//System.out.println("norm2/vectlenght= " + norm2/vector.length);
		//cacarella = norm2/vector.length
		return norm2;
	}
    
}
