package ac.uk.abdn.foobs.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Location")
public class LocationEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="locationType")
   private String locationType;

   @Column(name="displayString")
   private String displayString;

   /**
    * @return the id
    */
   public Long getId() {
      return Id;
   }

   /**
    * @return the locationType
    */
   public String getLocationType() {
      return locationType;
   }

   /**
    * @param locationType the locationType to set
    */
   public void setLocationType(String locationType) {
      this.locationType = locationType;
   }

   /**
    * @return the displayString
    */
   public String getDisplayString() {
      return displayString;
   }

   /**
    * @param displayString the displayString to set
    */
   public void setDisplayString(String displayString) {
      this.displayString = displayString;
   }

}
