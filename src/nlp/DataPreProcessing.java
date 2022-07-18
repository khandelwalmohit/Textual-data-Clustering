package nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class DataPreProcessing {
	List<String> docData;
	Set<String> stop_words;
	DataReader reader;
	Properties props;
	StanfordCoreNLP scn;
	
	public DataPreProcessing(List<String> data) {
		docData = data;
		reader = new DataReader();
		stop_words = reader.getStopWords();
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
		scn  = new StanfordCoreNLP(props, false);
	}
	
	public List<String> PreProcess() {
		List<String> processed = new ArrayList<String>();
		for(String doc: docData) {
			processed.add(processString(doc));
		}
		
		return processed;
		//After this do NGram for each object.
	}
	
	private String processString(String text){
		List<String> filter = new ArrayList<String>();
		List<String> split_array = new ArrayList<String>(Arrays.asList(text.toLowerCase().split("[-!~,.():\\[\\]\"\\s]+")));
		
		for(String s: split_array) {
			String word = s.trim();
			if(!stop_words.contains(word)) {
				filter.add(word);
			}
		}
		
		String filtered_String = String.join(" ", filter);
		List<String> temp= new ArrayList<String>();
		Annotation doc = scn.process(filtered_String);
		
		for(CoreMap sent: doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			for(CoreLabel l: sent.get(CoreAnnotations.TokensAnnotation.class)) {
				String lem = l.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
				temp.add(lem);
			}
		}
		
		//System.out.println(temp.get(0).toString() + " " +  temp.size());
		return String.join(" ", temp);
	}
}
