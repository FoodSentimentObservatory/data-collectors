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

import twitter4j.Status;

@Entity
@Table(name="Post")
public class PostEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="postType")
   private String postType;

   @Column(name="title")
   private String title;

   @Column(name="body")
   private String body;

   @Column(name="createdAt")
   private Date createdAt;

   @Column(name="importedAt")
   private Date importedAt;
   
   @Column (name = "platformPostID")
   private String platformPostID;
   
  
@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="hasCreator")
   private UserAccountEntity hasCreator;

   public PostEntity() {}

   public PostEntity(Status tweet) {
      this.postType = "tweet";
      this.body = tweet.getText();
      this.createdAt = tweet.getCreatedAt();
      this.importedAt = new Date();
      this.platformPostID = Long.toString(tweet.getId());
   }

   /**
    * @return the id
    */
   public Long getId() {
      return Id;
   }

   /**
    * @return the postType
    */
   public String getPostType() {
      return postType;
   }

   /**
    * @param postType the postType to set
    */
   public void setPostType(String postType) {
      this.postType = postType;
   }

   /**
    * @return the title
    */
   public String getTitle() {
      return title;
   }

   /**
    * @param title the title to set
    */
   public void setTitle(String title) {
      this.title = title;
   }

   /**
    * @return the body
    */
   public String getBody() {
      return body;
   }

   /**
    * @param body the body to set
    */
   public void setBody(String body) {
      this.body = body;
   }

   /**
    * @return the createdAt
    */
   public Date getCreatedAt() {
      return createdAt;
   }

   /**
    * @param createdAt the createdAt to set
    */
   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * @return the importedAt
    */
   public Date getImportedAt() {
      return importedAt;
   }

   /**
    * @param importedAt the importedAt to set
    */
   public void setImportedAt(Date importedAt) {
      this.importedAt = importedAt;
   }

   /**
    * @return the hasCreator
    */
   public UserAccountEntity getHasCreator() {
      return hasCreator;
   }

   /**
    * @param hasCreator the hasCreator to set
    */
   public void setHasCreator(UserAccountEntity hasCreator) {
      this.hasCreator = hasCreator;
   }
   
   public String getPlatformPostID() {
		return platformPostID;
	}

	public void setPlatformPostID(String platformPostID) {
		this.platformPostID = platformPostID;
	}

}
