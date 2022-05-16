package Specific_Questions;

import java.io.*;
import java.util.*;

// to run this anywhere after developing on windows dev machine:
//  copy contents to new Empty.java file in different directory
//  remove top "package *" line
//  Use Git Bash to run:
//  	javac Empty.java
//		cat contents.txt | java Empty
public class StdinReader {

	public static void main(String[] args) throws IOException {
		// ubuntu subsystem shell:
		// cd /mnt/d/Programming/Github/Interview_Practice/src/Specific_Questions

		// example read from file name given in stdin
//		File f = new File(args[0]);
//		System.out.println(f.getAbsolutePath());
//		try (BufferedReader br = new BufferedReader(new FileReader("src/Specific_Questions/"+args[0]))) {
//			String line;
//			while ((line = br.readLine()) != null) {
//				System.out.println("File line: " + line);
//			}
//		}

		// modify intellij run configuration - "Redirect input from"
		// example stdin with scanner
//		Scanner in = new Scanner(new FileInputStream(filename)); // read from file
//		Scanner in = new Scanner(socket.getInputStream()); / network socket
		Scanner in = new Scanner(System.in);
		while (in.hasNext()){
//			String input = in.next();
			String input = in.nextLine();
			System.out.println("Input line: " + input);
		}

		// example user input:
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.print("Enter a number: ");
//		int firstNum = Integer.parseInt(br.readLine());
//		System.out.println("you entered: " + firstNum);
	}

}
