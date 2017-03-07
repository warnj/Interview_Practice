// JUSTIN WARNER
// warnej3	1272287
// 3/6/2017
// CSE 312 AA
// Hw 8

// Compile:	javac ExpectationMaximization.java
// Run:		java ExpectationMaximization


import java.util.*;
// performs the EM algorithm on multiple gaussian distributions
public class ExpectationMaximization {
	private static final int numDistributions = 3;
	private static final double T = 1.0 / numDistributions;
	private static final double SIGMA = 1;
	private static final double EPSILON = 0.001;
	
	private static List<Double> data;

	public static void main(String[] args) {
		//Scanner console = new Scanner(System.in);
		String test1 = "9 10 11 20 21 22 45 50 55 57";
		String test2 = "35 42 9 28 27 31 11 40 32";
		String test3 = "-6 -5 -4 -5.5 4 5 6";
		String test4 = "150 151 150.1 99.2 98.15 97.48 100.5 148.77 121.3 147.5 122.2";
		Scanner console = new Scanner(test1);

		data = new ArrayList<Double>();
		while (console.hasNextDouble()) {
			data.add(console.nextDouble());
		}
		System.out.println(data);

		// initialize theta to be minimum, maximum and middle values
		Collections.sort(data);
		double[] curMeans = new double[numDistributions];
//		curMeans[0] = 8.457;//data.get(0); // min
//		curMeans[1] = 62.359;//data.get(data.size() / 2); // middle
//		curMeans[2] = 74.277;//data.get(data.size()-1); // max
		
		// random initialization of means
		Random r = new Random();
		for(int i = 0; i < curMeans.length; i++) {
			curMeans[i] = r.nextInt((int)(data.get(data.size()-1)-data.get(0))) + data.get(0);
		}
		Arrays.sort(curMeans);

		// used to update means
		double[] nextMeans = new double[numDistributions];
		// store expectations for given data set, the Zijs
		double[][] expectations = new double[data.size()][numDistributions];
		
		// print starting info
		System.out.printf("%-6s ", "itr");
		for (int i = 1; i <= numDistributions; i++) {
			System.out.printf("%-10.5s ", "mu_" + i);
		}
		System.out.printf("%-10s\n", "Loglikelyhood");
		
		System.out.printf("%-6s ", "[0,]");
		for (int i = 0; i < numDistributions; i++) {
			System.out.printf("%-10.5f ", curMeans[i]);
		}
		System.out.println();
		

		// repeat E and M until converges
		double difference = Double.POSITIVE_INFINITY;
		int iteration = 1;
		while (difference > EPSILON) {
			eStep(expectations, curMeans);
			mStep(expectations, nextMeans);
			double log = getLogLikelihood(expectations, nextMeans);
			
			// maximum change of three thetas
			difference = nextMeans[0]-curMeans[0];
			for(int i = 1; i < numDistributions; i++) {
				difference = Math.max(difference, nextMeans[i]-curMeans[i]);
			}

			// display current means
			System.out.printf("%-6s ", "[" + iteration + ",]");
			for (int i = 0; i < numDistributions; i++) {
				curMeans[i] = nextMeans[i];
				System.out.printf("%-10.5f ", nextMeans[i]);
			}
			System.out.printf("%-10.5f\n", log);
			iteration++;
		}
		
		// print the final probabilities
		System.out.printf("\n%-6s %-10s ", "i", "x_i");
		for (int i = 1; i <= numDistributions; i++) {
			System.out.printf("%-17s ", "P(cls " + i + " | x_i)");
		}
		System.out.println();
		
		for (int i = 0; i < expectations.length; i++) {
			System.out.printf("%-6s %-10.2f ", "[" + (i+1) + ",]", data.get(i));
			for (int j = 0; j < numDistributions; j++) {
				System.out.printf("%-17f ", expectations[i][j]);
			}
			System.out.println();
		}
	}

	// calculate all expectations using the current means
	private static void eStep(double[][] expectations, double[] curMeans) {
		for (int i = 0; i < expectations.length; i++) {
			double denom = 0.0;
			for (int j = 0; j < numDistributions; j++) {
				denom += probXGivenTheta(data.get(i), curMeans[j]);
			}
			for (int j = 0; j < numDistributions; j++) {
				double fj = probXGivenTheta(data.get(i), curMeans[j]);
				expectations[i][j] = fj / denom;
			}
		}
	}

	// calculate means from the current expectations
	private static void mStep(double[][] expectations, double[] nextMeans) {
		for (int i = 0; i < numDistributions; i++) {
			double numerator = 0.0;
			double denominator = 0.0;
			for (int j = 0; j < data.size(); j++) {
				numerator += expectations[j][i] * data.get(j);
				denominator += expectations[j][i];
			}
			nextMeans[i] = numerator / denominator;
		}
	}

	// returns conditional probability according to the normal random variable density function
	private static double probXGivenTheta(double x, double mean) {
		// don't need the term: 1.0 / (mean * Math.sqrt(2 * Math.PI)) since
		// it is just canceled out in the division of the e-step
		return Math.exp(-1 * Math.pow(x - mean, 2) / (2 * Math.pow(SIGMA, 2)));
	}

	// calculate the log likelihood
	private static double getLogLikelihood(double[][] expectations, double[] nextMeans) {
		double like = Math.log(T) * data.size() - Math.log(2 * Math.pow(SIGMA, 2) * Math.PI) * (data.size() / 2);
		for (int i = 0; i < numDistributions; i++) {
			double temp = 0.0;
			for (int j = 0; j < data.size(); j++) {
				temp += (Math.pow(data.get(j) - nextMeans[i], 2) * expectations[j][i] / (2 * Math.pow(SIGMA, 2)));
			}
			like -= temp;
		}
		return like;
	}
}
