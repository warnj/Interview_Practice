import java.io.*;
import java.util.*;

public class NaiveBayes {
	private static Map<String, Double> spamWords = new HashMap<>(); // initially map spam words to their counts, then to their probabilities P(w | Spam)
	private static Map<String, Double> hamWords = new HashMap<>(); // initially map non-spam words to their counts, then to their probabilities P(w | Ham)
	
	public static void main(String[] args) throws IOException {
		// CHANGE TO RELATIVE PATH
		train("D:/OneDrive/Documents/College/Current Quarter/CSE312/hw/NaiveBayes/data/train/");
		test();
	}
	
	private static void train(String parentPath) throws IOException {
		File s = new File(parentPath + "spamTest");
		File h = new File(parentPath + "hamTest");
		File[] spam = s.listFiles();
		File[] ham = h.listFiles();
		
		count(spamWords, spam);
		count(hamWords, ham);
		
		calcProbs(spamWords, spam.length);
		calcProbs(hamWords, ham.length);
		
//		System.out.println(spamWords);
//		System.out.println(hamWords);
		
		double pHam = (double) ham.length / (ham.length + spam.length);
		double pSpam = (double) spam.length / (ham.length + spam.length);
		
		System.out.println(pHam);
		System.out.println(pSpam);
	}
	
	private static void calcProbs(Map<String, Double> map, int total) {
		for(Map.Entry<String, Double> entry : map.entrySet()) {
			double probability = (entry.getValue() + 1) / (total + 2);
			map.put(entry.getKey(), probability);
		}
	}
	
	// counts the word occurrences in the given files and increments their count in the given map
	private static void count(Map<String, Double> map, File[] files) throws IOException {
		for(File file : files) {
			Set<String> words = tokenSet(file);
			for(String word : words) {
				addWord(map, word);
			}
		}
	}

	// adds the given string to the given map if not present and increments its count if it is present
	private static void addWord(Map<String, Double> map, String toAdd) {
		Double val = map.get(toAdd);
		if (val == null) {
			map.put(toAdd, 1.0);
		} else {
			map.put(toAdd, ++val);
		}
	}

	private static void test() {
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
//	This function reads in a file and returns a 
	//	set of all the tokens. It ignores the subject line
	//
	//	If the email had the following content:
	//
	//	Subject: Get rid of your student loans
	//	Hi there ,
	//	If you work for us, we will give you money
	//	to repay your student loans . You will be 
	//	debt free !
	//	FakePerson_22393
	//
	//	This function would return to you
	//	[hi, be, student, for, your, rid, we, get, of, free, if, you, us, give, !, repay, will, loans, work, fakeperson_22393, ,, ., money, there, to, debt]
	public static HashSet<String> tokenSet(File filename) throws IOException {
		HashSet<String> tokens = new HashSet<String>();
		Scanner filescan = new Scanner(filename);
		filescan.next(); //Ignoring "Subject"
		while(filescan.hasNextLine() && filescan.hasNext()) {
			tokens.add(filescan.next());
		}
		filescan.close();
		return tokens;
	}
}
