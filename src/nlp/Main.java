package nlp;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		DataReader reader= new DataReader();
		reader.read();
		List<String> data= reader.getData();
		//reader.stopWordCreator();
		DataPreProcessing pp = new DataPreProcessing(data);
		List<String> processed = pp.PreProcess();
		
//		for(String s: processed) {
//			System.out.println(s);
//		}
		
		//new NGram(processed, 3);
		
		TFIDF tf = new TFIDF(processed);
		double[][] tfidf = tf.buildTFIDF();
		
	}

}
