package ac.uk.abdn.foobs;

import java.io.File;

public class Main {
   public static void main(String[] args) {
      
      if (args.length < 1) {
         System.out.println("Run program with arguments <config.xml>");
         return;
      }

      File file = new File(args[0]);
      Config config = new Config(file);

      TaskManager.manageTasks(config);

   }

}
