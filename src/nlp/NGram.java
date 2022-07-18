package nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class NGram {
	int threshold;
	public NGram(List<String> data, int threshold) {
		this.threshold = threshold;
		System.out.println("NGrams created are:");
		for(String s: data) {
			NGramEachString(s);
		}
	}
	
	private void NGramEachString(String text) {
		List<String> split = Arrays.asList(text.split(" "));
		List<String> unique = new ArrayList<String>(new HashSet<String>(split));
		Map<String, List<Integer>> matrix = new HashMap<String, List<Integer>>();
		
		for(int i=0; i<unique.size(); i++) {
			List<Integer> temp = new ArrayList<Integer>();
			for(int j=0; j<unique.size(); j++) {
				temp.add(0);
			}
			matrix.put(unique.get(i), temp);
		}
		
		for(int i=0; i<split.size()-1; i++) {
			String curr = split.get(i);
			String next = split.get(i+1);
			int pos = IntStream.range(0, unique.size())
	                .filter(j -> Objects.equals(unique.get(j), curr))
	                .findFirst()
	                .orElse(-1);
//			System.out.println("curr: " + curr + " next: " + next + "pos: "+String.valueOf(pos));
//			int pos = unique.indexOf(curr);
//			for(String s: matrix.keySet()) {
//				System.out.println(s);
//			}
			int freq = matrix.get(next).get(pos);
			matrix.get(next).set(pos, freq+1);			
		}
		
		List<List<String>> n_grams = new ArrayList<List<String>>();

		for(int i=0; i<unique.size(); i++) {
			for(String entry : matrix.keySet()) {
				String curr = unique.get(i);
				int curr_freq = matrix.get(entry).get(i);
				if(threshold <= curr_freq) {
					n_grams.add(new ArrayList<String>(Arrays.asList(curr, entry, String.valueOf(curr_freq))));
					System.out.println(curr + " "+ entry + " " + String.valueOf(curr_freq));
				}
			}
		}
	}
	
}
