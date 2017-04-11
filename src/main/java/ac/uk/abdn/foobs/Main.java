package ac.uk.abdn.foobs;

import java.io.File;

public class Main {
   public static void main(String[] args) {
      System.out.println("Hello World");
      File file = new File(args[0]);
      Config config = new Config(file);

      System.out.println(config.getTwitterUserAccessTokenSecret());
   }
}
