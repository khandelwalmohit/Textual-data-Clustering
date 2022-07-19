package nlp;


public class EvaluationMatrix {
	KMeans k;
	int[][] confArray=  {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
	int[] clusterArray;
	
	public EvaluationMatrix(KMeans k) {
	this.k = k;
	clusterArray = k.lmatrix;
	}
	
	public void builConfusionMatrix() {
        System.out.println("\nPrecision matrix of topics takenaka, cent, pilot\nConfusion Matrix");
        int foo = 0;
        for (int i=0;  i < 3; i++) {
            for (int j=foo; j < foo + 8; j++) {
                confArray[clusterArray[j]][i]++;
            }
            foo = (foo == 0) ? 8 : 16;
            i++;
        }
        
        for (int i=0;  i < 3; i++) {
            for (int j=0; j <3; j++) {
                System.out.print(confArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        float calci = (confArray[0][0] + confArray[0][1] + confArray[0][2]);
        float precision = 0,a,c;
        if (calci != 0)
            precision = (float) confArray[0][0] / (confArray[0][0] + confArray[0][1] + confArray[0][2]);

        System.out.println("Precision of topic takenaka - " + precision);
        
        calci = (confArray[0][0] + confArray[1][0] + confArray[2][0]);
        float recall = 0;
        if (calci != 0)
            recall = (float) confArray[0][0] / (confArray[0][0] + confArray[1][0] + confArray[2][0]);
        System.out.println("Recall of topic takenaka - " + recall);
        
        float f1 = 0;
        a=precision+recall;
        c=precision*recall;
        if (a != 0)
            f1 = 2 * (c / a);
        System.out.println("F1 score of topic takenaka - " + f1);

        System.out.println();
        
        calci = (confArray[1][0] + confArray[1][1] + confArray[1][2]);
        precision = 0;
        if (calci != 0)
            precision = (float) confArray[1][1] / (confArray[1][0] + confArray[1][1] + confArray[1][2]);
        System.out.println("Precision of topic cent - " + precision);
        
        calci = confArray[0][1] + confArray[1][1] + confArray[2][1];
        recall = 0;
        if (calci != 0)
            recall = (float) confArray[1][1] / (confArray[0][1] + confArray[1][1] + confArray[2][1]);
        System.out.println("Recall of topic cent - " + recall);
        
        f1 = 0;
        a=precision+recall;
        c=precision*recall;
        if (a != 0)
            f1 = 2 * (c / a);
        System.out.println("F1 score of topic cent - " + f1);

        System.out.println();
        
        precision = 0;
        calci = confArray[2][0] + confArray[2][1] + confArray[2][2];
        if (calci != 0)
            precision = (float) confArray[2][2] / (confArray[2][0] + confArray[2][1] + confArray[2][2]);
        System.out.println("Precision of topic pilot - " + precision);
        
        recall = 0;
        calci = confArray[0][2] + confArray[1][2] + confArray[2][2];
        if (calci != 0)
            recall = (float) confArray[2][2] / (confArray[0][2] + confArray[1][2] + confArray[2][2]);
        System.out.println("Recall of topic pilot - " + recall);
       
        f1 = 0;
        a=precision+recall;
        c=precision*recall;
        if (a != 0)
            f1 = 2 * (c/a);
        System.out.println("F1 score of topic pilot - " + f1);
    }

}
