package tools;

import deep.ImgDescriptor;
import deep.QueryResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Output {

	public static final int COLUMNS = 5;

	public static void toHTML(List<QueryResult> result, String baseURI, File outputFile) {
		String html = "<html>\n<body>\n<table align='center'>\n";

		for (int i = 0; i < result.size(); i++) {
			System.out.println("Result " + i  + "\tID: " + result.get(i).getID() + "\t" + "distance: " + result.get(i).getDistance());

			if (i % COLUMNS == 0) {
				if (i != 0)
					html += "</tr>\n";
				html += "<tr>\n";
			}

			String srcFolderString = baseURI.split("file:///")[1].split("/")[0];
			File imgFolder = new File(srcFolderString);

			File[] listOfFiles = imgFolder.listFiles();
			Arrays.sort(listOfFiles);

			html += "<td><img align='center' border='0' height='160' title='" + result.get(i).getID() + ", dist: "
			        + result.get(i).getDistance() +  "' src='" + baseURI
					+ listOfFiles[Integer.parseInt(result.get(i).getID())].getName() + "'></td>\n";
			
			 System.out.println(listOfFiles[Integer.parseInt(result.get(i).getID())].getName());
		}
		if (result.size() != 0)
			html += "</tr>\n";

		html += "</table>\n</body>\n</html>";

		try {
			string2File(html, outputFile);
			System.out.print("html generated");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void string2File(String text, File file) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(text);
		} finally {
			if (fileWriter != null)
				fileWriter.close();
		}
	}
}
