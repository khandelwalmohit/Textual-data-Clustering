package nlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.stanford.nlp.simple.*;

public class DataPreProcessing {
	List<String> docData;
	Set<String> stop_words;
	public DataPreProcessing(List<String> data) {
		docData = data;
		stop_words = getStopWords();
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
