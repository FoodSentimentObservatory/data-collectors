package ac.uk.abdn.foobs.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

   @OneToOne(fetch=FetchType.EAGER,mappedBy="locationId",cascade=CascadeType.ALL)
   private AddressEntity address;

   @OneToOne(fetch=FetchType.EAGER,mappedBy="locationId",cascade=CascadeType.ALL)
   private SearchDetailsEntity search;


   @OneToOne(fetch=FetchType.LAZY,mappedBy="locationId",cascade=CascadeType.ALL)
   private GeoPointEntity geoPoint;

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

   /**
   * @return the address
   */
   public AddressEntity getAddress() {
      return address;
   }

   public void setAddressAndDispayString(AddressEntity address) {
      String addressString = "";
      addressString = address.getLine1() + ", " +
                      address.getCity() + ", " +
                      address.getPostcode() + ", " +
                      address.getCountry();
      this.displayString = addressString;
      this.address = address;
   }

   /**
    * @param address the address to set
    */
   public void setAddress(AddressEntity address) {
      this.address = address;
   }

   /**
    * @return the geoPoint
    */
   public GeoPointEntity getGeoPoint() {
      return geoPoint;
   }

   /**
    * @param geoPoint the geoPoint to set
    */
   public void setGeoPoint(GeoPointEntity geoPoint) {
      this.geoPoint = geoPoint;
   }

}
