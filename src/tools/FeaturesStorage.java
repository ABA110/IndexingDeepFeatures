package tools;

import deep.ImgDescriptor;
import deep.Parameters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FeaturesStorage {
	
	public static void storeBuckets(List<String> ids, String StoragePath) throws IOException {
		if (ids != null){
			File storageFile = new File(StoragePath);
			storageFile.getParentFile().mkdir();
	        FileOutputStream fos =  new FileOutputStream(storageFile);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        try {
	        	oos.writeObject(ids);
	        } finally {
	        	oos.close();
	        	fos.close();
	        }
		}
	}
	
	public static List<String> loadBuckets(File storageFile) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(storageFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<String> ids = null;
        try {
        	ids = (List<String>) ois.readObject();
        } finally {
        	ois.close();
        	fis.close();
        }
        return ids;
	}
	
	public static void storeDB(List<ImgDescriptor> descriptors, File storageFile) throws IOException{
		
		FileOutputStream fos = null;
	    DataOutputStream dos = null;
	    
	    // create file output stream
        fos = new FileOutputStream(storageFile);
          
        // create data output stream
        dos = new DataOutputStream(fos);
        
        
		for(ImgDescriptor tmp : descriptors){
			for(long j:tmp.getFeatures())
	         {
	            // write long to data output stream
	            dos.writeLong(j);
	         }
		}
		
		// force data to the underlying file output stream
        dos.flush();
        
        if(fos!=null)
            fos.close();
         if(dos!=null)
            dos.close();

	}
	
public static long[] loadDB(File storageFile) throws IOException{
		
		FileInputStream is = null;
	    DataInputStream dis = null;
	    
	    /*result has number of longs equal to the size of the file in bytes divided by the size of a long in bytes*/
	    long[]result = new long[(int) (storageFile.length()/Long.BYTES)];
	    
	    // create file output stream
	    is = new FileInputStream(storageFile);
        
        // create new data input stream
        dis = new DataInputStream(is);
        
        // read till end of the stream
        int i = 0;
        while(dis.available()>0)
        {
           // read character
          long c = dis.readLong();
          result[i] = c;
          i++;
        
        }
        if(is!=null)
            is.close();
        
        if(dis!=null)
            dis.close();
        
        return result;
	}
	
public static void storeGfunctions(int[] ids, File storageFile) throws IOException {
	if (ids != null){
		//File storageFile = new File(StoragePath);
		storageFile.getParentFile().mkdir();
        FileOutputStream fos =  new FileOutputStream(storageFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
        	oos.writeObject(ids);
        } finally {
        	oos.close();
        	fos.close();
        }
	}
}

public static int[] loadGFunctions(File storageFile) throws IOException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(storageFile);
    ObjectInputStream ois = new ObjectInputStream(fis);
    int[] ids = null;
    try {
    	ids = (int[]) ois.readObject();
    } finally {
    	ois.close();
    	fis.close();
    }
    return ids;
}
	/*public static void storeBuckets(List<String> ids, String StoragePath) throws IOException {
		
		PrintWriter out = new PrintWriter(StoragePath);
		
		for(String tmp : ids)
			out.println(tmp);
		
		out.flush();
		
		out.close();
		
	}
	
/*public static void storeDB(List<ImgDescriptor> descriptors, File storageFile) throws IOException{
		
	if (descriptors != null){
		storageFile.getParentFile().mkdir();
        FileOutputStream fos =  new FileOutputStream(storageFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
        	oos.writeObject(descriptors);
        } finally {
        	oos.close();
        	fos.close();
        }
	}

	}*/
}
