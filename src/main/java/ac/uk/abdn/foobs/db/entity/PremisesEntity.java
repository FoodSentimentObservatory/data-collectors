package ac.uk.abdn.foobs.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Premises")
public class PremisesEntity {
   @Id
   @Column(name="Id")
   private Integer Id;

   @Column(name="businessName")
   private String businessName;

   @Column(name="businessType")
   private String businessType;

   @OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
   @JoinColumn(name="locationId")
   private LocationEntity location;

   @ManyToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
   @JoinColumn(name="belongToAgent")
   private AgentEntity belongToAgent;

   @OneToMany(fetch=FetchType.LAZY,mappedBy="premisesId",cascade=CascadeType.ALL)
   private Set<RatingEntity> ratings = new HashSet<RatingEntity>();

   /**
   * @return the id
   */
   public Integer getId() {
      return Id;
   }

   /**
    * @param id the id to set
    */
   public void setId(Integer id) {
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
   public Set<RatingEntity> getRatings() {
      return ratings;
   }

   /**
   * @param ratings the ratings to set
   */
   public void setRatings(Set<RatingEntity> ratings) {
      this.ratings = ratings;
   }

}
