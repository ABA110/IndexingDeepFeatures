package deepExtraction;

import deep.DNNExtractor;

import deep.ImgDescriptor;
import deep.LSHIndexer;
import deep.Parameters;
import deep.QueryResult;
import tools.FeaturesStorage;
import tools.Output;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class LSHImageSearch {

	private static List<QueryResult> result = new ArrayList<QueryResult>();

	public static void main(String[] args) throws Exception {

		/*
		 * for(int i = 0; i < Parameters.positions.length; i++) //print
		 * positions array to see if it is correctly read
		 * System.out.print(Parameters.positions[i] + ", ");//from
		 * LSHImageStorage
		 */

		LSHImageSearch searcher = new LSHImageSearch();

		// searcher.open(Parameters.STORAGE_FILE);

		// Image Query File
		File img = new File(Parameters.SRC_FOLDER, "kaklacatrtoy.jpg");

		DNNExtractor extractor = new DNNExtractor();

		float[] features = extractor.extract(img, Parameters.DEEP_LAYER);
		ImgDescriptor query = new ImgDescriptor(features, "Query", img.getName());

		searcher.search(query);

		Output.toHTML(result, Parameters.BASE_URI, Parameters.RESULTS_HTML);

	}

	// TODO
	// public void open(File storageFile) throws ClassNotFoundException,
	// IOException {
	// Load the dataset and assign it to the descriptors object
	// descriptors = FeaturesStorage.load(storageFile);

	// }

	// TODO
	public void search(ImgDescriptor queryF) throws IOException, ClassNotFoundException {

		// long[] dataBase = FeaturesStorage.loadDB(Parameters.DB_STORAGE_FILE);

		RandomAccessFile dataBase = new RandomAccessFile(Parameters.DB_STORAGE_FILE, "r");

		//ArrayList<String> result = new ArrayList<String>();

		List<String> bucketContent = new ArrayList<String>();

		LSHIndexer indexer = new LSHIndexer();

		for (int l = 0; l < Parameters.L; l++) {
			int[] positions = FeaturesStorage.loadGFunctions(new File(Parameters.G_STORAGE_FILE + "/" + l + ".dat"));

			/*for (int i = 0; i < positions.length; i++)// print positions array
														// to see if it is
														// correctly read
				System.out.print(positions[i] + ", "); // from LSHImageStorage
			System.out.println("");*/

			int bucket = indexer.hash(queryF, positions);
			//System.out.println("BucketNumber: " + bucket);

			bucketContent = FeaturesStorage
					.loadBuckets(new File(Parameters.BUCKETS_FOLDER + "/" + l + "/" + Integer.toString(bucket) + ".dat"));			
			
			//System.out.println("Number of images inside the bucket: " + bucketContent.size());

			long[] dickHeadBaldracca = new long[Parameters.FEATURE_LENGTH / Long.SIZE]; // 4096bit feature length->64Longs

			for (String tmp : bucketContent) {

				dataBase.seek(512 * Long.valueOf(tmp).longValue());
				for (int i = 0; i < Parameters.FEATURE_LENGTH / Long.SIZE; i++)
					dickHeadBaldracca[i] = dataBase.readLong(); // bit haie feature 1 image. 64 bit * 64 = 4096

				int distance = queryF.hammingDistance(dickHeadBaldracca);
								
				if (distance <= Parameters.MAX_DISTANCE) {
					QueryResult partialResult = new QueryResult(distance, tmp);
					if(!result.contains(partialResult))
					result.add(partialResult);
					//result.add(tmp);
					//distances.add(distance);
				}

				// System.out.println(tmp);

			}
		}
		
		Collections.sort(result, new Comparator<QueryResult>() {
		    @Override
		    public int compare(QueryResult o1, QueryResult o2) {
		        return new Integer(o1.getDistance()).compareTo(new Integer(o2.getDistance()));
		    }
		});
		//return result;
	}

	/*searching through l different hash functions could generate duplicate in the result
	static ArrayList<QueryResult> removeDuplicates(List<QueryResult> list) {

		// Store unique items in result.
		ArrayList<QueryResult> result = new ArrayList<>();

		// Record encountered Strings in HashSet.
		HashSet<QueryResult> set = new HashSet<>();

		// Loop over argument list.
		for (QueryResult item : list) {

			// If result is not in set, add it to the list and the set.
			if (!set.contains(item)) {
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}*/
}
