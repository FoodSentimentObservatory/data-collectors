package ac.uk.abdn.foobs;

import java.io.File;

import ac.uk.abdn.foobs.db.HibernateUtil;

public class Main {
   public static void main(String[] args) {
      
      if (args.length < 1) {
         System.out.println("Run program with arguments <config.xml>");
         return;
      }

      File file = new File(args[0]);
      Config config = new Config(file);

      TaskManager.manageTasks(config);

      HibernateUtil.getSessionFactory().close();

   }

}
