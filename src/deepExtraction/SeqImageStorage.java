package deepExtraction;

import deep.DNNExtractor;
import deep.ImgDescriptor;
import deep.Parameters;
import tools.FeaturesStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SeqImageStorage {

	public static void main(String[] args) throws Exception {
		
		long startTime = System.currentTimeMillis();
				
		SeqImageStorage indexing = new SeqImageStorage();
				
		List<ImgDescriptor> descriptors = indexing.extractFeatures(Parameters.SRC_FOLDER);
		
		// FeaturesStorage.store(descriptors, Parameters.STORAGE_FILE);
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Storage completed, That took " + (endTime - startTime) + " milliseconds");
	}
	
	//TODO
	private List<ImgDescriptor> extractFeatures(File imgFolder){
		
		List<ImgDescriptor>  descs = new ArrayList<ImgDescriptor>();
		DNNExtractor extractor = new DNNExtractor();

		//Scan the folder to insert all dataset files
		File[] listOfFiles = imgFolder.listFiles();
		File currentFile;
			
		//LOOP:
			//Extract the deep features for each file
			//put the features in an ImgDescriptor object and add it in the descs List 
		for (int i = 0; i < listOfFiles.length && listOfFiles[i].isFile(); i++) {
			ImgDescriptor ids;
			currentFile = new File(listOfFiles[i].getAbsolutePath());
			ids = new ImgDescriptor(extractor.extract(currentFile, Parameters.DEEP_LAYER), listOfFiles[i].getName());
			descs.add(ids);
		}	
		return descs;	
	}		
}
