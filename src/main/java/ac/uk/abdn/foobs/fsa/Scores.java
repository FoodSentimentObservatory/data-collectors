package ac.uk.abdn.foobs.fsa;

public class Scores {
   private int hygiene;
   private int structural;
   private int confidenceInManagement;

   public Scores(int rHygiene, int rStructural, int rConfidenceInManagement) {
      this.hygiene = rHygiene;
      this.structural = rStructural;
      this.confidenceInManagement = rConfidenceInManagement;
   }

   public String toString() {
      String value = "\n\tHygiene: " + hygiene + 
                     "\n\tStructural: " + structural +
                     "\n\tConfidenceInManagement: " + confidenceInManagement;
      return value;
   }

   /**
    * @return the hygiene
    */
   public int getHygiene() {
      return hygiene;
   }

   /**
    * @return the structural
    */
   public int getStructural() {
      return structural;
   }

   /**
    * @return the confidenceInManagement
    */
   public int getConfidenceInManagement() {
      return confidenceInManagement;
   }
}
