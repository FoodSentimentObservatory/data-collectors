package ac.uk.abdn.foobs.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
	
	public static  ArrayList <String> createKeywordAndPhrasesStrings (List<String> keywords) {
	  
	  ArrayList <String> keywordslist =  new ArrayList <String> ();
		int THRESHOLD = 330;
		StringBuilder temp = new StringBuilder();
		char quotes='"';

		for (int i=0 ; i <keywords.size();i++ ) {
			String word =  keywords.get(i).trim();
			
			if (word.length()>0) {
				//word is phrase only
				if (word.contains(" ") && !word.contains("+")) {
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
				}//if word contains only words that do not need to be one after the other in text
				else if(word.contains("+") && !word.contains(" ")){
					//String newWord = word.replace("+", " ");
					int count = StringUtils.countMatches(word, "+");			
					String[] parts = word.split("\\+");
					StringBuilder fullLine = new StringBuilder();
					
					for (int n=0; n<=count; n++) {
						String stringSection = parts[n];
						
						String stringSectionReady = cleanString(stringSection);
						fullLine.append(stringSectionReady);
						if (n!=count) {
							fullLine.append(" ");
						}
					}
					String fullString = fullLine.toString();
					if (temp.length()==0) {
						//first word in the string
						temp.append("(");
						temp.append(fullString);
						temp.append(")");
						
						}else {
							if ((temp + word).length() +6 < THRESHOLD) {
								temp.append(" OR ");
								temp.append("(");
								temp.append(fullString);
								temp.append(")");
								
							}else  {
								//start new entry
								keywordslist.add(temp.toString());
								temp= new StringBuilder();
								temp.append("(");
								temp.append(fullString);
								temp.append(")");
							}
						}
		
				}//if the word contains a mixture of both
				else if (word.contains("+") && word.contains(" ")){
					int count = StringUtils.countMatches(word, "+");			
					String[] parts = word.split("\\+");
					StringBuilder fullLine = new StringBuilder();
					
					for (int n=0; n<=count; n++) {
						String stringSection = parts[n];
						
						String stringSectionReady = cleanString(stringSection);
						fullLine.append(stringSectionReady);
						if (n!=count) {
							fullLine.append(" ");
						}
					}
					String fullString = fullLine.toString();
					
					if (temp.length()==0) {
						//first word in the string	
						temp.append("(");
						temp.append(fullString);
						temp.append(")");
						
						}else {
							if ((temp + word).length()+2 < THRESHOLD) {
								temp.append(" OR ");	
								temp.append("(");
								temp.append(fullString);
								temp.append(")");
								
							}else  {
								//start new entry
								keywordslist.add(temp.toString());
								temp= new StringBuilder();
								temp.append("(");
								temp.append(fullString);	
								temp.append(")");
							}
						}
			
				}else {
					//single word			
					if (temp.length()==0) {
						//first word in the string				
						temp.append(word);
						
						}else {
							if ((temp + word).length() +4 < THRESHOLD) {
								temp.append(" OR ");	
								temp.append(word);
								
							}else  {
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

	//function to check if the sub strings are phrases or single words
	public static String cleanString(String word) {
		  String newString;
		  if (word.contains(" ")) {
			   newString = '"'+word+'"'; 
		  }else {
			  newString = word;
		  }
		  return newString;
	}
	
}
