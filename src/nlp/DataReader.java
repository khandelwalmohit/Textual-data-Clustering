package nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
					fileContent+= line+ " ";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println("Reading document "+ p);
			fileContents.add(fileContent);
		}
	}
	
	List<String> getData(){
		return fileContents;
	}
	
	void printContent() {
		for(String s: fileContents) {
			for(int i=0; i<10; i++) {
				System.out.print(s.charAt(i));
			}
			System.out.println();
		}
	}
	
	void stopWordCreator() {
		SortedSet<String> stopWord = new TreeSet<String>();
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			writer = new FileWriter(rootPath+ "/src/external_resources/stopwords.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<String> paths = new ArrayList<String>();
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_1_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_2_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_3_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_4_google_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_5_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stop-words-collection-2014-02-24/stop-words/stop-words_english_6_en.txt");
		paths.add("/Users/mohitkhandelwal/Downloads/stopwords_en.txt");
		paths.add(rootPath + "/src/lib/stanfordCoreNLP/patterns/stopwords.txt");

		
		for(String p: paths) {
			try {
				 reader = new BufferedReader(new FileReader(p));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			String line = "";
			try {
				while((line = reader.readLine())!= null) {
					stopWord.add(line.trim());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		for(String w: stopWord) {
			try {
				writer.write(w +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println("Wrote: "+ w);
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Set<String> getStopWords() {
		Set<String> stop_words = new HashSet<String>();
		BufferedReader reader = null;
		String path = System.getProperty("user.dir") + "/src/external_resources/stopwords.txt";
		
		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String word = "";
		try {
			while((word = reader.readLine())!= null) {
				stop_words.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stop_words;
	}

}
