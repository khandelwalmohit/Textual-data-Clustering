package nlp;

public class Main {

	public static void main(String[] args) {
		DataReader reader= new DataReader();
		reader.read();
		//reader.stopWordCreator();
		DataPreProcessing pp = new DataPreProcessing(reader);
	}

}
