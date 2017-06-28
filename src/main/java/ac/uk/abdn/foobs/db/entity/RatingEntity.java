package ac.uk.abdn.foobs.db.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Rating")
public class RatingEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="schemeType")
   private String schemeType;

   @Column(name="ratingKey")
   private String ratingKey;

   @Column(name="ratingValue")
   private String ratingValue;

   @Column(name="ratingDate")
   private Date ratingDate;

   @Column(name="newRatingPending")
   private String newRatingPending;

   @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="premisesId")
   private PremisesEntity premisesId;

   /**
    * @return the id
    */
   public Long getId() {
      return Id;
   }

   /**
    * @return the schemeType
    */
   public String getSchemeType() {
      return schemeType;
   }

   /**
    * @param schemeType the schemeType to set
    */
   public void setSchemeType(String schemeType) {
      this.schemeType = schemeType;
   }

   /**
    * @return the ratingKey
    */
   public String getRatingKey() {
      return ratingKey;
   }

   /**
    * @param ratingKey the ratingKey to set
    */
   public void setRatingKey(String ratingKey) {
      this.ratingKey = ratingKey;
   }

   /**
    * @return the ratingValue
    */
   public String getRatingValue() {
      return ratingValue;
   }

   /**
    * @param ratingValue the ratingValue to set
    */
   public void setRatingValue(String ratingValue) {
      this.ratingValue = ratingValue;
   }

   /**
    * @return the ratingDate
    */
   public Date getRatingDate() {
      return ratingDate;
   }

   /**
    * @param ratingDate the ratingDate to set
    */
   public void setRatingDate(Date ratingDate) {
      this.ratingDate = ratingDate;
   }

   /**
   * @return the newRatingPending
   */
   public String getNewRatingPending() {
      return newRatingPending;
   }

   /**
    * @param newRatingPending the newRatingPending to set
    */
   public void setNewRatingPending(String newRatingPending) {
      this.newRatingPending = newRatingPending;
   }

   /**
    * @return the premisesId
    */
   public PremisesEntity getPremisesId() {
      return premisesId;
   }

   /**
    * @param premisesId the premisesId to set
    */
   public void setPremisesId(PremisesEntity premisesId) {
      this.premisesId = premisesId;
   }

}
