package nlp;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class KMeans {
	double[][] data, pmatrix, best;
	int[] lmatrix;
	int t1, n, p, props, itr;
	boolean l1, ppflag;
	double wcs, eps;
	
	public KMeans(double[][] data) {
		this.data = data;
//		for(int i=0; i<this.data[0].length; i++) {
//		System.out.print(this.data[0][i]);
//		}
		
	}
	
	private double cosFunc(double[] vec1, double[] vec2) {
        double dot_product = 0.0;
        double A, B = 0.0;
        A=B;

        for(int i=0; i<vec1.length; i++) {
            dot_product += vec1[i] * vec2[i];
            A += Math.pow(vec1[i], 2);
            B += Math.pow(vec2[i], 2);
        }
        if (A == 0) {
            return 1;
        }
        else if(B ==0) {
        	return 1;
        }
    
        return 1 - (dot_product / (Math.sqrt(A) * Math.sqrt(B)));
    }
	
	public void firstCentroids() {
		pmatrix = new double[t1][p];
		if(ppflag) {
			double[] closestCentroid = new double[n];
			double[] weights = new double[n];
			
			Random rands = new Random();
			int rand_val = rands.nextInt(n);
            System.arraycopy(this.data[rand_val], 0, pmatrix[0], 0, p);
			for(int i=1; i<t1; i++) {
				for (int p = 0; p < n; p++) {
                    double dist = cosFunc(this.data[p], this.data[i - 1]);
                    if (i == 1)
                    	closestCentroid[p] = dist;

                    else if(dist < closestCentroid[p]){
                        	closestCentroid[p] = dist;
                    }
                    
                    if (p != 0) {
                    	weights[p] = weights[p - 1] + closestCentroid[p];
                    }
                    else {
                    	weights[0] = closestCentroid[0];
                    }
                }
                double rand = rands.nextDouble();
                for (int j = n - 1; j > 0; j--) {
                    if (rand > (weights[j - 1] / weights[n - 1])) {
                        rand_val = j;
                        break;
                    } else
                        rand_val = 0;
                }
                
                System.arraycopy(this.data[rand_val], 0, pmatrix[i], 0, p);
			}
		}
		else {
			Random rands = new Random();
			for(int i=0; i<t1; i++) {
				int rand = rands.nextInt(8) + 8 * i;
				System.arraycopy(this.data[rand], 0, pmatrix[i], 0, p);
			}
		}
		
		
		wcs = Double.longBitsToDouble(0x7ff0000000000000L);
		double prev;
		do {
			lmatrix = new int[n];
	        double tDist, minVal;
	        int minLoc;
	        for (int i=0;  i < n; i++) {
	            minLoc = 0;
	            minVal = Double.longBitsToDouble(0x7ff0000000000000L);
	            for (int j=0; j < t1; j++) {
	            	//System.out.println(String.valueOf(i)+ " " + String.valueOf(j));
	                tDist = distance(data[i], pmatrix[j]);
	                if (tDist < minVal) {
	                    minVal = tDist;
	                    minLoc = j;
	                }
	            }
	            lmatrix[i] = minLoc;
	        }
	        
	        
	        updateCentroids();
	        
	        
	        prev = wcs;
	        
	        double wC = 0;
	        int assginC;

	        for (int i=0; i < n; i++) {
	            assginC = lmatrix[i];
	            wC += distance(this.data[i], pmatrix[assginC]);
	        }
	        this.wcs = wC;
	        
		}
		while(! (eps > 1 - (wcs / prev)) );
	}
	
	private double distance(double[] a, double[] b) {
		double dist =0;
		if(!l1) {
			dist = euclidean(a, b);
		}
		else {
			dist = cosFunc(a, b) ;
		}
		
        return dist;
    }

    private double euclidean(double[] a, double[] b) {
//    	System.out.println("New line");
//    	for(int i=0; i<b.length; i++) {
//    		System.out.print(b[i]);
//    		}
        double dist = 0;
        int k=0;
        while(k<a.length) {
        	 dist += Math.abs((a[k] - b[k]) * (a[k] - b[k]));
        	 k++;
        }           

        return Math.sqrt(dist);
    }

	
	private void updateCentroids() {
    	int j=0;
        
        for(int x=0; x<t1; x++) {
        	for(int y=0; y<p; y++) {
        		pmatrix[x][y] = 0;
        	}
        }

        int[] t1array = new int[t1];
        
       int i=0;
        while (i < n) {
            t1array[lmatrix[i]]++;
            j=0;
            while( j < p) {
                pmatrix[lmatrix[i]][j] += data[i][j];
                j++;
            }
            i++;
        }
        
        Set<Integer> empty = new LinkedHashSet<Integer>();
        
//        for(int x=0; x<t1; x++) {
//        	if (t1array[i] != 0)
//        	{
//            
//            for(int k=0; k<this.p; k++) {
//            	 pmatrix[x][k] /= t1array[x];
//            }
//        		
//        	}
//        	
//        	else {
//                empty.add(i);
//        	}
//        }
        
        i=0;
        while (i < t1) {
            if (t1array[i] == 0)
                empty.add(i);

            else {
	            	j=0;
	                while (j < p) {
	                    pmatrix[i][j] /= t1array[i];
	                	j++;
	                }
                }
            i++;
        }
     
        
        if (!empty.isEmpty()) {
            Set<double[]> have_centroids = new HashSet<>();
            int zi=0;
            while (zi < t1) {
                if (!empty.contains(zi))
                    have_centroids.add(pmatrix[zi]); 
                zi++;
            }

            Random random = new Random();
            for (int e : empty)
                while (true) {
                    int rand = random.nextInt(data.length);
                    if (!have_centroids.contains(data[rand])) {
                        have_centroids.add(data[rand]);
                        pmatrix[e] = data[rand];
                        break;
                    }
                }
        }
    }
	
	public void initCluster() {
		firstCentroids();
	}
	
	public void euclidean_Kmeans() {
		//System.out.println("Euclidean");
		t1 = 3;
		itr = 50;
		ppflag = false;
		l1 = false;
		eps = .001;
		n= data.length;
		p = data[0].length;
		double bwc = Double.POSITIVE_INFINITY;
        best = new double[0][0];
        int[] new_pos = new int[0];
        for (int z=0; z < itr; z++) {
            initCluster();
            if (wcs < bwc) {
                bwc = wcs;
                best = pmatrix;
                new_pos = lmatrix;
            }
        }
        lmatrix = new_pos;
        pmatrix = best;
        wcs = bwc;
	}
	
	public void cosine_Kmeans() {
		//System.out.println("Cosine");
		t1 = 3;
		itr = 50;
		ppflag = false;
		l1 = true;
		eps = .001;
		n= data.length;
		p = data[0].length;
		double bwc =Double.longBitsToDouble(0x7ff0000000000000L);
		best = new double[0][0];
        int[] new_pos = new int[0];
        for (int z=0; z < itr; z++) {
            initCluster();
            if (wcs < bwc) {
                bwc = wcs;
                best = pmatrix;
                new_pos = lmatrix;
            }
        }
        lmatrix = new_pos;
        pmatrix = best;
        wcs = bwc;

	}
	
	
	
	
}

