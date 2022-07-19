package nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
		
		new NGram(processed, 3);
		
		TFIDF tf = new TFIDF(processed);
		double[][] tfidf = tf.buildTFIDF();
		tf.writeTopics();
		
//		ArrayList<ArrayList<Double>> idf = new ArrayList<>();
//		for(int i=0; i<tfidf.length; i++) {
//			ArrayList<Double> tmp = new ArrayList<>();
//			for(int j=0; j<tfidf[0].length; j++) {
//				tmp.add(tfidf[i][j]);
//			}
//			idf.add(tmp);
//		}
		
		System.out.println("Strating K-Means");
		 System.out.println("Similarity measure as Euclidean");
		 KMeans kmean1 = new KMeans(tfidf);
		    kmean1.euclidean_Kmeans();
		 EvaluationMatrix em1 = new EvaluationMatrix(kmean1);
		 em1.builConfusionMatrix();
		 
		 
		System.out.print("\n");
	    System.out.println("Similarity measure as Cosine");
	    KMeans kmean2 = new KMeans(tfidf);
	    kmean2.cosine_Kmeans();
	    EvaluationMatrix em2 = new EvaluationMatrix(kmean2);
		 em1.builConfusionMatrix();
//		    EvaluationMatrix em = new EvaluationMatrix();
//		    em.buildConfusionMatrix(kmean1);

	}

}
