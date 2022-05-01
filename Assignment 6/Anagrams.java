// Vatsal Shah 10474245

import java.io.*;
import java.util.*;

public class Anagrams {
	//Data
	final Integer[] primes= {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};  
	Map<Character,Integer> letterTable;  
	Map<Long,ArrayList<String>> anagramTable;
		
	//Constructor
	public Anagrams(){
		letterTable= new HashMap<Character,Integer>();
		buildLetterTable();
		anagramTable= new HashMap<Long,ArrayList<String>>();
	}
	
	//Hash Code LetterTable
	private void buildLetterTable() {
		Character[] a= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int i = 0;
		while(i < 26) {
			//To assign respective prime number to a character 
			letterTable.put(a[i],primes[i]);
			i++;
		}
	}	

	//Adding Input String To Anagram Table
	private void addWord(String s) {
		//If String Not Present, Add The Word To Anagram Table And Compute Hash Code
		if (!anagramTable.containsKey(myHashCode(s))) {
			ArrayList<String> x=new ArrayList<String>();
			x.add(s);
			anagramTable.put(myHashCode(s), x);
		} else { //If String Is Present In Anagram Table Then Hash Code Is Fetched From The Anagram Word
			ArrayList<String> x=anagramTable.get(myHashCode(s));
			x.add(s);
			anagramTable.replace(myHashCode(s), x);
		}
	}
	
	//Compute Unique Hash Code
	private Long myHashCode(String s) {
		int i=0;
		long hashCode=1;
		while(i < s.length()) {
			Character x=s.charAt(i);
			hashCode *=letterTable.get(x);
			i++;
		}
		return hashCode;
	}
	
	//Processing The I/P File
	private void processFile(String s) throws IOException{
		FileInputStream  fstream  =  new  FileInputStream(s);  
		BufferedReader br = new BufferedReader (new InputStreamReader(fstream));  
		String  strLine;  
		while ((strLine = br.readLine()) != null ){ 
		  this.addWord ( strLine );
		}  
	  br.close ();  
	} 
		
	//Gets The Largest Number of Anagrams
	private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries(){
		ArrayList<Map.Entry<Long,ArrayList<String>>> x= new ArrayList<Map.Entry<Long,ArrayList<String>>>();
		int a=0;
		for (Map.Entry<Long,ArrayList<String>> z:anagramTable.entrySet()) {
			if(z.getValue().size() == a) {
				x.add(z);
			} else {
				if(z.getValue().size() > a) {
					x.clear();
					x.add(z);
					a=z.getValue().size();
				}
			}
		}
		return x;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Anagrams anagram = new  Anagrams();
		final long startTime  = System.nanoTime();  
		try  {
			anagram.processFile("words_alpha.txt");
		}catch  ( IOException e1 )  {
			e1.printStackTrace ();
		}
		ArrayList <Map.Entry<Long,ArrayList<String>>> maxEntries = anagram.getMaxEntries();  
		final long  estimatedTime = System.nanoTime() - startTime;
		final double  seconds  =  ((double) estimatedTime / 1000000000);
		System.out.println ("Elapsed Time :  "+  seconds );
		System.out.println("Key  of  max  anagrams : " + maxEntries.get(0).getKey());
		System.out.println ("List  of  max  anagrams :  "+ maxEntries.get(0).getValue() ); 
		System.out.println("Length  of  list  of  max  anagrams : "+maxEntries.get(0).getValue().size());
	}

}
