package ac.uk.abdn.foobs.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Platform")
public class PlatformEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="forumType")
   private String forumType;

   @Column(name="forumName")
   private String forumName;

   @Column(name="forumSiteUrl")
   private String forumSiteUrl;

   @OneToMany(fetch=FetchType.LAZY,mappedBy="platformId",cascade=CascadeType.ALL)
   private List<UserAccountEntity> users;

   public PlatformEntity() {}

   public PlatformEntity(String fType, String fName, String fSiteUrl) {
      this.forumType = fType;
      this.forumName = fName;
      this.forumSiteUrl = fSiteUrl;
   }

   /**
    * @return the id
    */
   public Long getId() {
      return Id;
   }

   /**
    * @return the forumType
    */
   public String getForumType() {
      return forumType;
   }

   /**
    * @param forumType the forumType to set
    */
   public void setForumType(String forumType) {
      this.forumType = forumType;
   }

   /**
    * @return the forumName
    */
   public String getForumName() {
      return forumName;
   }

   /**
    * @param forumName the forumName to set
    */
   public void setForumName(String forumName) {
      this.forumName = forumName;
   }

   /**
    * @return the forumSiteUrl
    */
   public String getForumSiteUrl() {
      return forumSiteUrl;
   }

   /**
    * @param forumSiteUrl the forumSiteUrl to set
    */
   public void setForumSiteUrl(String forumSiteUrl) {
      this.forumSiteUrl = forumSiteUrl;
   }

   /**
    * @return the users
    */
   public List<UserAccountEntity> getUsers() {
      return users;
   }

   /**
    * @param users the users to set
    */
   public void setUsers(List<UserAccountEntity> users) {
      this.users = users;
   }

}
