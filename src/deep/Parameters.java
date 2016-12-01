package deep;

import java.io.File;

public class Parameters {
	
	//DEEP parameters
	public static final String DEEP_PROTO = "data/caffe/train_val.prototxt";
	public static final String DEEP_MODEL = "data/caffe/bvlc_reference_caffenet.caffemodel";
	public static final String DEEP_MEAN_IMG = "data/caffe/meanImage.png";
	
	public static final String DEEP_LAYER = "fc7";
	public static final int IMG_WIDTH = 227;
	public static final int IMG_HEIGHT = 227;
	
	//Image Source Folder
	public static final File SRC_FOLDER = new File("data/img");
	
	//Features Storage File
	public static final File STORAGE_FILE = new File("out/deep.seq.dat");
	public static final File DB_STORAGE_FILE = new File("main_descriptors_DB/DB_text.dat");
	
	//Bucket storage folder
	public static final String BUCKETS_FOLDER = "buckets/";
	
	// g-functions Storage File
	public static final String G_STORAGE_FILE = "buckets/g_functions";
	
	//L = number of g() functions
	public static final int L = 70; //lowest number to get a 100% research success with 8-bits hash function
	
	//Number of bucket to insert descriptors in
	
	public static final int LSH_BITS = 8;// = 256 buckets, approximately 100 images per bucket
	
	public static final int FEATURE_LENGTH = 4096;
	
	//HTML Output Parameters
	public static final  String BASE_URI = "file:///" + Parameters.SRC_FOLDER.getAbsolutePath() + "/";
	public static final File RESULTS_HTML = new File("out/deep.seq.html");
	
	//Hamming Distance used for comparison
	public static final int MAX_DISTANCE = 1000;
	

}
