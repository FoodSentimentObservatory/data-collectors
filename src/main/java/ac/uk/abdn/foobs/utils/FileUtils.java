package ac.uk.abdn.foobs.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileUtils {
	
	
	
	public static void saveCacheReport (ArrayList previousSearchesXML) {
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(HH-mm)");
		 LocalDateTime formatDateTime = LocalDateTime.parse(LocalDateTime.now().toString(), formatter);
		String pathString =  "SEARCH-CACHE-RESULT-"+formatDateTime + ".txt";	
		String directoryString = "/SearchResults/";
	  
	    

			//path = Paths.get(URLEncoder.encode(pathString, "UTF-8"));
		
			File output = new File (pathString);
			
		/*
			File directory = new File(directoryString);
	    if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		*/

	
	

    String stringToWrite="";
    for (int l =0; l<previousSearchesXML.size();l++ ) {
		stringToWrite = stringToWrite + previousSearchesXML.get(l)+"\n";
	}
	
	//Use try-with-resource to get auto-closeable writer instance
	try (BufferedWriter writer = Files.newBufferedWriter(output.toPath())) 
	{
	    writer.write(stringToWrite);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	

}
