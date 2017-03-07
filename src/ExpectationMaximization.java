// JUSTIN WARNER
// warnej3	1272287
// 3/6/2017
// CSE 312 AA
// Hw 8

// Compile:	javac ExpectationMaximization.java
// Run:		java ExpectationMaximization


import java.util.*;

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
		Scanner console = new Scanner(test1);

		data = new ArrayList<Double>();
		while (console.hasNextDouble()) {
			data.add(console.nextDouble());
		}

		// initialize theta to be minimum, maximum and middle values
		Collections.sort(data);
		double[] curTheta = new double[numDistributions];
		curTheta[0] = 8.457;//data.get(0); // min
		curTheta[1] = 62.359;//data.get(data.size() / 2); // middle
		curTheta[2] = 74.277;//data.get(data.size()-1); // max

		// used to update theta
		double[] nextTheta = new double[numDistributions];

		// to store all expectations for given data set
		double[][] expectations = new double[data.size()][numDistributions];

		System.out.printf("%-6s %-10s %-10s %-10s %-10s\n", "i", "mu_1", "mu_2", "mu_3", "Loglikelyhood");
		System.out.printf("%-6s %-10.5f %-10.5f %-10.5f %-10.5f\n", "[1,]", curTheta[0], curTheta[1], curTheta[2]);

		// repeat E and M until converges
		double difference = Double.POSITIVE_INFINITY;
		int iteration = 2;
		while (difference > EPSILON) {
			eStep(expectations, curTheta);   // Expectation step
			mStep(expectations, nextTheta);  // Maximization step
			double log = getLogLikelihood(expectations, nextTheta);
			
			// maximum change of three thetas
			difference = nextTheta[0]-curTheta[0];
			for(int i = 1; i < numDistributions; i++) {
				difference = Math.max(difference, nextTheta[i]-curTheta[i]);
			}

			// display current means
			System.out.printf("%-6s ", "[" + iteration + ",]");
			for (int i = 0; i < numDistributions; i++) {
				curTheta[i] = nextTheta[i];
				System.out.printf("%-10.5f ", nextTheta[i]);
			}
			System.out.printf("%-10.5f\n", log);
			iteration++;
		}
		
		// print the final probabilities
		System.out.printf("\n%-6s %-10s %-17s %-17s %-17s\n", "i", "x_i", "P(cls 1 | x_i)", "P(cls 2 | x_i)", "P(cls 3 | x_i)");
		for (int i = 0; i < expectations.length; i++) {
			System.out.printf("%-6s %-10.2f ", "[" + (i+1) + ",]", data.get(i));
			for (int j = 0; j < numDistributions; j++) {
				System.out.printf("%-17f ", probXGivenTheta(data.get(i), curTheta[j]));
			}
			System.out.println();
		}
	}

	// expectation step - calculate all expectations
	private static void eStep(double[][] expectations, double[] curTheta) {
		for (int i = 0; i < expectations.length; i++) {
			double denom = 0.0;
			for (int j = 0; j < numDistributions; j++) {
				denom += probXGivenTheta(data.get(i), curTheta[j]);
			}
			for (int j = 0; j < numDistributions; j++) {
				double fj = probXGivenTheta(data.get(i), curTheta[j]);
				expectations[i][j] = fj / denom;
			}
		}
	}

	// Maximization step; return the log likelihood of given data set
	private static void mStep(double[][] expectations, double[] nextTheta) {
		for (int i = 0; i < numDistributions; i++) {
			double numerator = 0.0;
			double denominator = 0.0;
			for (int j = 0; j < data.size(); j++) {
				numerator += expectations[j][i] * data.get(j);
				denominator += expectations[j][i];
			}
			nextTheta[i] = numerator / denominator;
		}
	}

	// Calculate the conditional probability according to the definition
	private static double probXGivenTheta(double x, double theta) {
		return Math.exp(-Math.pow(x - theta, 2) / (2 * Math.pow(SIGMA, 2)));
	}

	// calculate the log likelihood
	private static double getLogLikelihood(double[][] expectations, double[] nextTheta) {
		double like = Math.log(T) * data.size() - Math.log(2 * Math.pow(SIGMA, 2) * Math.PI) * (data.size() / 2);
		for (int i = 0; i < numDistributions; i++) {
			double temp = 0.0;
			for (int j = 0; j < data.size(); j++) {
				temp += (Math.pow(data.get(j) - nextTheta[i], 2) * expectations[j][i] / (2 * Math.pow(SIGMA, 2)));
			}
			like -= temp;
		}
		return like;
	}
}
