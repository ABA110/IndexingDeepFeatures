package deepExtraction;

import deep.DNNExtractor;

import deep.ImgDescriptor;
import deep.Parameters;
import tools.FeaturesStorage;
import tools.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeqImageSearch {

	private List<ImgDescriptor> descriptors;
		
	public static void main(String[] args) throws Exception {

		SeqImageSearch searcher = new SeqImageSearch();
		
		searcher.open(Parameters.STORAGE_FILE);
		
		//Image Query File
		File img = new File(Parameters.SRC_FOLDER, "1ca3ba487028e9945aa487728c907c09.jpg");
		
		DNNExtractor extractor = new DNNExtractor();
		
		float[] features = extractor.extract(img, Parameters.DEEP_LAYER);
		ImgDescriptor query = new ImgDescriptor(features, img.getName());
				
		List<ImgDescriptor> res = searcher.search(query, Parameters.K);
		
		Output.toHTML(res, Parameters.BASE_URI, Parameters.RESULTS_HTML);

	}
		
	//TODO
	public void open(File storageFile) throws ClassNotFoundException, IOException {
		//Load the dataset and assign it to the descriptors object
		descriptors = FeaturesStorage.load(storageFile);
		
	}
	
	//TODO
	public List<ImgDescriptor> search(ImgDescriptor queryF, int k) {
		
		List<ImgDescriptor> res = new ArrayList<ImgDescriptor>();
		//LOOP descriptors to perform a sequential scan search
		for(ImgDescriptor temp : descriptors){
			temp.dist = temp.distance(queryF);
		}
		
		/*for(ImgDescriptor temp : descriptors){
			System.out.println(temp.dist);
		}*/
		
		//sort the results
		Collections.sort(descriptors);
		
		//return the sorted k best results
		for(int i = 0; i< k; i++)
			//System.out.println(descriptors.get(i).dist);
			res.add(descriptors.get(i));
		
		return res;
	}

}
