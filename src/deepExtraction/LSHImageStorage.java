package deepExtraction;

import deep.DNNExtractor;
import deep.LSHIndexer;
import deep.ImgDescriptor;
import deep.Parameters;
import tools.FeaturesStorage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LSHImageStorage {
	
	private static int l = 0;
	
	public static void main(String[] args) throws Exception {

		long startTime = System.currentTimeMillis();

		LSHImageStorage indexing = new LSHImageStorage();

		/*
		 * descriptors: a List containing the ImgDescriptors of all dataset
		 * images
		 */
		
		List<ImgDescriptor> descriptors;
		descriptors = indexing.extractFeatures(Parameters.SRC_FOLDER);
		
		for (l = 0; l < Parameters.L; l++) {
			// A list of list, to put each list belonging to a bucket, into a
			// single
			// list
			List<ArrayList<String>> bucketsList;

			bucketsList = indexing.indexFeatures(descriptors);

			// FeaturesStorage.store(descriptors, Parameters.STORAGE_FILE);
			FeaturesStorage.storeDB(descriptors, Parameters.DB_STORAGE_FILE);

			for (int i = 0; i < bucketsList.size(); i++) {
				FeaturesStorage.storeBuckets(bucketsList.get(i),
						Parameters.BUCKETS_FOLDER + "/" + l + "/" + Integer.toString(i) + ".dat");

			}
		}
		long endTime = System.currentTimeMillis();

		System.out.println("Storage completed, That took " + (endTime - startTime) + " milliseconds");
	}

	private List<ImgDescriptor> extractFeatures(File imgFolder) {

		List<ImgDescriptor> descriptors = new ArrayList<ImgDescriptor>();

		// Load the dataset folder
		File[] listOfFiles = imgFolder.listFiles();
		Arrays.sort(listOfFiles);
		File currentFile;

		/*
		 * EXTRACTING FEATURES FROM ALL IMAGES AND CREATING A LIST OF
		 * IMAGEDESCRIPTORS WITH THEM
		 */
		// LOOP:
		// Extract the deep features for each file
		DNNExtractor extractor = new DNNExtractor();
		for (int i = 0; i < listOfFiles.length && listOfFiles[i].isFile(); i++) {
			ImgDescriptor ids;
			currentFile = new File(listOfFiles[i].getAbsolutePath());
			ids = new ImgDescriptor(extractor.extract(currentFile, Parameters.DEEP_LAYER), Integer.toString(i),
					listOfFiles[i].getName());
			descriptors.add(ids);

			// checks all images, every feature vector has the right size (4096)
			// if(i == listOfFiles.length-1)
			// for(int j=0; j<ids.getFeatures().length; j++ )
			// System.out.println(ids.getFeatures()[j] + ", ");

		}

		return descriptors;

	}

	private List<ArrayList<String>> indexFeatures(List<ImgDescriptor> descriptors) throws IOException {

		List<ArrayList<String>> bucketsList = new ArrayList<ArrayList<String>>();
		int num_buckets = (int) Math.pow(2, Parameters.LSH_BITS);

		for (int i = 0; i < num_buckets; i++) {
			ArrayList<String> bucket = new ArrayList<String>();
			bucketsList.add(bucket);
		}

		LSHIndexer indexer = new LSHIndexer();
		int[] positions;// = new int[Parameters.LSH_BITS];
		positions = indexer.bitPositions();

		//for (int i = 0; i < positions.length; i++) {
			// positions[i] = bitPositions[i];
			//System.out.print(positions[i] + ", ");
		//}

		FeaturesStorage.storeGfunctions(positions, new File(Parameters.G_STORAGE_FILE + "/" + l + ".dat"));

		//////////// Scan all the descriptors, compute the bucket they belong to
		//////////// and put their ID in it///////////////////
		for (ImgDescriptor tmp : descriptors) {
			int bucketNumber;
			bucketNumber = indexer.hash(tmp, positions);
			// System.out.println(bucketNumber);
			bucketsList.get(bucketNumber).add(tmp.id); // corresponding id of image, that fit the hash id
														// according to it bit positions,, but we store id, 
														// and not the feature
		}

		return bucketsList;
	}

}

/////// THIS IS THE OLD EXTRACTFEATURE METHOD, THAT HAS BEEN SPLIT IN THE NEW
/////// ECTRACTFEATURES + INDEXFEATURES METHODS//////

/*
 * private List<ArrayList<String>> extractFeatures(File imgFolder) {
 * 
 * List<ArrayList<String>> bucketsList = new ArrayList<ArrayList<String>>(); int
 * num_buckets = (int) Math.pow(2, Parameters.LSH_BITS);
 * 
 * List<ImgDescriptor> descriptors = new ArrayList<ImgDescriptor>(); /*
 * Initializing the list with NUM_BUCKETS Lists, creating as many list as number
 * of bucketsList , add it to the list of list
 * 
 * for (int i = 0; i < num_buckets; i++) { ArrayList<String> bucket = new
 * ArrayList<String>(); bucketsList.add(bucket); }
 * 
 * // Load the dataset folder File[] listOfFiles = imgFolder.listFiles(); File
 * currentFile;
 * 
 * /* EXTRACTING FEATURES FROM ALL IMAGES AND CREATING A LIST OF
 * IMAGEDESCRIPTORS WITH THEM
 * 
 * // LOOP: // Extract the deep features for each file DNNExtractor extractor =
 * new DNNExtractor(); for (int i = 0; i < listOfFiles.length &&
 * listOfFiles[i].isFile(); i++) { ImgDescriptor ids; currentFile = new
 * File(listOfFiles[i].getAbsolutePath()); ids = new
 * ImgDescriptor(extractor.extract(currentFile, Parameters.DEEP_LAYER),
 * Integer.toString(i), listOfFiles[i].getName()); descriptors.add(ids); }
 * 
 * 
 * LSHIndexer indexer = new LSHIndexer(); int[] positions =
 * indexer.bitPositions();
 * 
 * ////////////Scan all the descriptors, compute the bucket they belong to and
 * put their ID in it/////////////////// for (ImgDescriptor tmp : descriptors) {
 * int bucketNumber; bucketNumber = indexer.hash(tmp, positions);
 * bucketsList.get(bucketNumber).add(tmp.id); }
 * 
 * return bucketsList; }
 */