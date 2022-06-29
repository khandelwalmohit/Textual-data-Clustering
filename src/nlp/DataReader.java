package nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {
	String rootPath, dataPath;
	File data; 
	List<String> allFiles;
	List<String> fileContents;
	
	public DataReader() {
		rootPath = System.getProperty("user.dir");
		dataPath = rootPath+ "/src/external_resources/data";
		data = new File(dataPath);
		allFiles = new ArrayList<String>();
		fileContents = new ArrayList<String>();
	}
	
	void explorer(File root, List<String> allFiles, String path){
		for(File entry: root.listFiles()) {
			if(entry.isDirectory()) {
				explorer(entry, allFiles, path+entry.getName()+"/");
			}else if(entry.getName().compareTo(".DS_Store") != 0){
				allFiles.add(path+entry.getName());
			}
		}
		
		return;
	}
	
	void read() {
		explorer(data, allFiles, "/");
		for(String p: allFiles) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(dataPath + p));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
			String line = "";
			String fileContent = "";
			try {
				while((line = reader.readLine())!= null) {
					fileContent+= line + " ";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Reading document "+ p);
			fileContents.add(fileContent);
		}
	}
	
	void printContent() {
		for(String s: fileContents) {
			for(int i=0; i<10; i++) {
				System.out.print(s.charAt(i));
			}
			System.out.println();
		}
	}

}
