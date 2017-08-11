package ac.uk.abdn.foobs.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FileUtils {
	
	
	public static void saveCacheReport (ArrayList previousSearchesXML) {
	Path path = Paths.get("./SearchResults/" +LocalDateTime.now() + ".txt");
	
	
	
	File directory = new File("./SearchResults/");
    if (! directory.exists()){
        directory.mkdir();
        // If you require it to make the entire directory path including parents,
        // use directory.mkdirs(); here instead.
    }
    String stringToWrite="";
    for (int l =0; l<previousSearchesXML.size();l++ ) {
		stringToWrite = stringToWrite + previousSearchesXML.get(l)+"\n";
	}
	
	//Use try-with-resource to get auto-closeable writer instance
	try (BufferedWriter writer = Files.newBufferedWriter(path)) 
	{
	    writer.write(stringToWrite);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
