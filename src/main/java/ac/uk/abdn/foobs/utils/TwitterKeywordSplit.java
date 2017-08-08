package ac.uk.abdn.foobs.utils;

import java.util.ArrayList;

public class TwitterKeywordSplit {

public  ArrayList <String> createKeywordStrings (ArrayList <String> list) {
		
		ArrayList <String> keywordslist =  new ArrayList <String> ();
		int THRESHOLD = 30;
		StringBuilder temp = new StringBuilder();
		char quotes='"';
		
		
		for (int i=0 ; i <list.size();i++ ) {
			
		String word =  list.get(i).trim();
		
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
