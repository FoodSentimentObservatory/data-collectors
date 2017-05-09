package ac.uk.abdn.foobs.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name="Premises")
public class PremisesEntity {
   @Id
   @Column(name="Id")
   private String Id;

   @Column(name="businessName")
   private String businessName;

   @Column(name="businessType")
   private String businessType;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="locationId")
   private LocationEntity location;

   @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="belongToAgent")
   private AgentEntity belongToAgent;

   @OneToMany(fetch=FetchType.LAZY,mappedBy="premisesId",cascade=CascadeType.ALL)
   private List<RatingEntity> ratings;

   /**
   * @param id the id to set
   */
   public void setId(String id) {
      Id = id;
   }

   /**
   * @return the businessName
   */
   public String getBusinessName() {
      return businessName;
   }

   /**
    * @param businessName the businessName to set
    */
   public void setBusinessName(String businessName) {
      this.businessName = businessName;
   }

   /**
   * @return the businessType
   */
   public String getBusinessType() {
      return businessType;
   }

   /**
    * @param businessType the businessType to set
    */
   public void setBusinessType(String businessType) {
      this.businessType = businessType;
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

   /**
    * @return the belongToAgent
    */
   public AgentEntity getBelongToAgent() {
      return belongToAgent;
   }

   /**
    * @param belongToAgent the belongToAgent to set
    */
   public void setBelongToAgent(AgentEntity belongToAgent) {
      this.belongToAgent = belongToAgent;
   }

   /**
    * @return the ratings
    */
   public List<RatingEntity> getRatings() {
      return ratings;
   }

   /**
    * @param ratings the ratings to set
    */
   public void setRatings(List<RatingEntity> ratings) {
      this.ratings = ratings;
   }
}
