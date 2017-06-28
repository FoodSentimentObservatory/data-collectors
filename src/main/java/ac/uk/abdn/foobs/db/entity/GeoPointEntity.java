package ac.uk.abdn.foobs.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import twitter4j.GeoLocation;

@Entity
@Table(name="GeoPoint")
public class GeoPointEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="longitude")
   private Double longitude;

   @Column(name="latitude")
   private Double latitude;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="locationId")
   private LocationEntity locationId;

   public GeoPointEntity() {}

   public GeoPointEntity(GeoLocation geoLocation) {
      this.longitude = geoLocation.getLongitude();
      this.latitude = geoLocation.getLatitude();
   }
   /**
   * @return the id
   */
   public Long getId() {
      return Id;
   }

   /**
   * @return the longitude
   */
   public Double getLongitude() {
      return longitude;
   }

   /**
    * @param longitude the longitude to set
    */
   public void setLongitude(Double longitude) {
      this.longitude = longitude;
   }

   /**
   * @return the latitude
   */
   public Double getLatitude() {
      return latitude;
   }

   /**
    * @param latitude the latitude to set
    */
   public void setLatitude(Double latitude) {
      this.latitude = latitude;
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
