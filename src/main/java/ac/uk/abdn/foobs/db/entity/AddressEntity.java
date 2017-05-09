package ac.uk.abdn.foobs.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Address")
public class AddressEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="line1")
   private String line1;

   @Column(name="line2")
   private String line2;

   @Column(name="postcode")
   private String postcode;

   @Column(name="county")
   private String county;

   @Column(name="country")
   private String country;

   @Column(name="city")
   private String city;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="locationId")
   private LocationEntity locationId;

   /**
    * @return the id
    */
   public Long getId() {
      return Id;
   }

   /**
    * @return the line1
    */
   public String getLine1() {
      return line1;
   }

   /**
    * @param line1 the line1 to set
    */
   public void setLine1(String line1) {
      this.line1 = line1;
   }

   /**
    * @return the line2
    */
   public String getLine2() {
      return line2;
   }

   /**
    * @param line2 the line2 to set
    */
   public void setLine2(String line2) {
      this.line2 = line2;
   }

   /**
    * @return the postcode
    */
   public String getPostcode() {
      return postcode;
   }

   /**
    * @param postcode the postcode to set
    */
   public void setPostcode(String postcode) {
      this.postcode = postcode;
   }

   /**
    * @return the county
    */
   public String getCounty() {
      return county;
   }

   /**
    * @param county the county to set
    */
   public void setCounty(String county) {
      this.county = county;
   }

   /**
    * @return the country
    */
   public String getCountry() {
      return country;
   }

   /**
    * @param country the country to set
    */
   public void setCountry(String country) {
      this.country = country;
   }

   /**
    * @return the city
    */
   public String getCity() {
      return city;
   }

   /**
    * @param city the city to set
    */
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * @return the locationId
    */
   public LocationEntity getLocationId() {
      return locationId;
   }

   /**
    * @param locationId the locationId to set
    */
   public void setLocationId(LocationEntity locationId) {
      this.locationId = locationId;
   }
}
