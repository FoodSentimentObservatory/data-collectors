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

import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name="GeoPoint")
public class GeoPointEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="locationPoint",columnDefinition="geometry(Point,4326)")
   private Point locationPoint;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="locationId")
   private LocationEntity location;

   /**
   * @return the id
   */
   public Long getId() {
      return Id;
   }

   /**
    * @return the locationPoint
    */
   public Point getLocationPoint() {
      return locationPoint;
   }

   /**
    * @param locationPoint the locationPoint to set
    */
   public void setLocationPoint(Point locationPoint) {
      this.locationPoint = locationPoint;
   }

   /**
    * @return the location
    */
   public LocationEntity getLocation() {
      return location;
   }

   /**
    * @param location the location to set
    */
   public void setLocation(LocationEntity location) {
      this.location = location;
   }
}
