package nlp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TFIDF {
	List<String> data;
	List<String> matrix;
	double[][] tf, tfIDfMatix;
	int[] freq;
	int[][] curr;
	
	public TFIDF(List<String> data) {
		this.data = data;
		matrix = new ArrayList<String>();
		for(int i=0; i<this.data.size(); i++) {
			String tmp = data.get(i);
			tmp = tmp.replaceAll(",", " ");
			matrix.add(tmp);
			//System.out.println(tmp);
		}
		
	}
	
	public double[][] buildTFIDF(){
		List<List<String>> d = new ArrayList<List<String>>();
		for(String text: matrix) {
			String removePunc = text.replaceAll("\\p{Punct}", "");
			String[] split = removePunc.toLowerCase().split("\\s");
			
			List<String> words = new ArrayList<String>();
			for(String word: split) {
				String tmp = word.trim();
				if(tmp.length() > 0) {
					words.add(tmp);
				}
				
			}
			
			d.add(words);
		}
		
//		for(List<String> l: d) {
//			System.out.println(l.toString());
//		}
		
		matrix = unique(d);
		//System.out.println(matrix.toString() + matrix.size());
		
		freq = new int[matrix.size()];
		curr = new int[matrix.size()][d.size()];
		
//		System.out.println(Arrays.deepToString(curr));
//		System.out.println(Arrays.toString(freq));
//		
		for(int i=0; i<d.size(); i++) {
			List<String> d2 = d.get(i);
			Map<String, Integer> m = bf(d2);
//			System.out.println(m.toString());
			for(Entry<String, Integer> entry: m.entrySet()) {
				String w = entry.getKey();
				int freq2 = entry.getValue();
//				System.out.println(matrix.indexOf(w));
				curr[matrix.indexOf(w)][i] = freq2;
				//freq[matrix.indexOf(w)]++;
				int freq3 = freq[matrix.indexOf(w)];
				freq3++;
				freq[matrix.indexOf(w)] = freq3;
			}
		}
//		System.out.println(Arrays.deepToString(curr));
//		System.out.println(Arrays.toString(freq));
//		
//		 System.out.println(freq.toString() + "\n" + curr.toString());
		
		tf = new double[matrix.size()][d.size()];

		
		for(int i=0; i<matrix.size(); i++) {
			for(int k=0; k<d.size(); k++) {
				tf[i][k] = ((double)(Math.sqrt(curr[i][k])))*((double)(Math.log((double) (d.size()) / (1.0 + freq[i])) + 1.0));
			}
		}
		
		List<Map<String, Integer>> docmaps = new ArrayList<Map<String, Integer>>();
        tfIDfMatix = new double[24][matrix.size()];
        
        Map<String, Integer> tmp;
        for(int i=0; i<d.size(); i++)
         {
        	tmp = new HashMap<>();
            int counter = 0; 
            while (counter < matrix.size()) 
            {
                tmp.put(matrix.get(counter), curr[counter][i]);
                counter++;
            }
            
//            System.out.println(tmp.toString());
            tmp = sortValues(tmp);
            System.out.println(tmp.toString());
            
            updateMatrix(i);
            
            docmaps.add(tmp);
        }
//        System.out.println(docmaps.toString());
//        System.out.println("[");
//        FileWriter fw = null;
//		try {
//			fw = new FileWriter(System.getProperty("user.dir")+ "/src/external_resources/topics.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        for (Map<String, Integer> temp : docmaps) {
//            int setVal = 1;
//            System.out.print("{");
//            try {
//				fw.write("{");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            for (String key : temp.keySet()) {
//                System.out.print(key + " : " + temp.get(key) + " ");
//                try {
//					fw.write(key + " : " + temp.get(key) + " ");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                setVal++;
//                if (setVal > 100) {
//                    break;
//                }
//            }
//            System.out.println("},");
//            try {
//				fw.write("}\n");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        System.out.println("]");
//        try {
//			fw.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
        return tfIDfMatix;
		
	}
	
	private Map<String, Integer> sortValues(Map<String, Integer> map) {
//		return map.entrySet().stream()
//			       .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//			       .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, HashMap::new));
		 List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
	        list.sort(Entry.comparingByValue());
	        Collections.reverse(list);

	        Map<String, Integer> result = new LinkedHashMap<>();
	        for (Entry<String, Integer> entry : list) {
	            result.put(entry.getKey(), entry.getValue());
	        }
	        return result;
	}
	
	private void updateMatrix(int i) {
		for(int k=0; k<matrix.size(); k++) {
            tfIDfMatix[i][k] = tf[k][i];
        }
	}
	
	Map<String, Integer> bf(List<String> d2){
		Map<String, Integer> m = new HashMap<String, Integer>();
		for(String s: d2) {
			Integer j= m.get(s);
			m.put(s, (j == null) ? 1 : j + 1);
		}
		
		return m;
	}
	
	private List<String> unique(List<List<String>> d){
		Set<String> unique = new HashSet<String>();
		List<String> unique_words = new ArrayList<String>();
		for(List<String> text: d) {
			for(String s: text) {
				if(!unique.contains(s)) {
					unique.add(s);
					unique_words.add(s);
				}
			}
		}
		
		return unique_words;
	}
	
}
