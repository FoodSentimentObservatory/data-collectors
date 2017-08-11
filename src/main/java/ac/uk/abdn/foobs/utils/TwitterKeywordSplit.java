package ac.uk.abdn.foobs.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TwitterKeywordSplit {

public static  ArrayList <String> createKeywordStrings (List<String> keywords) {
		
		ArrayList <String> keywordslist =  new ArrayList <String> ();
		int THRESHOLD = 350;
		StringBuilder temp = new StringBuilder();
		char quotes='"';
		
		
		for (int i=0 ; i <keywords.size();i++ ) {
			
		String word =  keywords.get(i).trim();
		
		//System.out.println("Processing"+word);
		
		if (word.length()>0) {
			//word is phrase
		if (word.contains(" ")) {
			if (temp.length()==0) {
			//first phrase in the string
				
				temp.append(quotes);
				temp.append(word);
				temp.append(quotes);
			}
			else {
				
				if ((temp + word).length()+6 < THRESHOLD) {
					temp.append(" OR ");
					temp.append(quotes);
					temp.append(word);
					temp.append(quotes);
					
				}
				else  {
					//start new entry
					keywordslist.add(temp.toString());
					temp= new StringBuilder();
					temp.append(quotes);
					temp.append(word);
					temp.append(quotes);
				}
				
			}

		}
		
		else {
			//single word
			
			if (temp.length()==0) {
				//first word in the string
				
				temp.append(word);
				
					
				}
				else {
					
					
					if ((temp + word).length() +4 < THRESHOLD) {
						temp.append(" OR ");
						
						temp.append(word);
						
					}
					
					else  {
						//start new entry
						keywordslist.add(temp.toString());
						temp= new StringBuilder();
						
						temp.append(word);
						
					}
					
				}
			
		}
		
		}

		}
		
		keywordslist.add(temp.toString());
	
		return keywordslist;
	}
	
	
}
