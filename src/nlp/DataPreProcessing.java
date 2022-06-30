package nlp;

import java.util.List;
import java.util.Set;

public class DataPreProcessing {
	List<String> docData;
	Set<String> stop_words;
	
	public DataPreProcessing(DataReader reader) {
		docData = reader.getData();
		stop_words = reader.getStopWords();
	}

}
