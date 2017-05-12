package ac.uk.abdn.foobs.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import twitter4j.User;

@Entity
@Table(name="UserAccount")
public class UserAccountEntity {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
   @GenericGenerator(name="native",strategy="native")
   private Long Id;

   @Column(name="platformAccountId")
   private String platformAccountId;

   @Column(name="accountURL")
   private String accountURL;

   @Column(name="displayName")
   private String displayName;

   @Column(name="profileDescription")
   private String profileDescription;

   @Column(name="verified")
   @Type(type="numeric_boolean")
   private boolean verified;

   @Column(name="lastCheckedDate")
   private Date lastCheckedDate;

   @Column(name="accountCreatedAt")
   private Date accountCreatedAt;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="platformId")
   private PlatformEntity platformId;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="agentId")
   private AgentEntity agentId;

   @OneToMany(fetch=FetchType.LAZY,mappedBy="hasCreator",cascade=CascadeType.ALL)
   private Set<PostEntity> posts = new HashSet<PostEntity>();

   public UserAccountEntity() {}

   public UserAccountEntity(User user) {
      this.lastCheckedDate = new Date();
      this.accountCreatedAt = user.getCreatedAt();
      this.accountURL = user.getURL();
      this.displayName = user.getName();
      this.platformAccountId = user.getScreenName();
      this.profileDescription = user.getDescription();
      this.verified = user.isVerified();

   }

   /**
   * @return the id
   */
   public Long getId() {
      return Id;
   }

   /**
   * @return the platformAccountId
   */
   public String getPlatformAccountId() {
      return platformAccountId;
   }

   /**
    * @param platformAccountId the platformAccountId to set
    */
   public void setPlatformAccountId(String platformAccountId) {
      this.platformAccountId = platformAccountId;
   }

   /**
   * @return the accountURL
   */
   public String getAccountURL() {
      return accountURL;
   }

   /**
    * @param accountURL the accountURL to set
    */
   public void setAccountURL(String accountURL) {
      this.accountURL = accountURL;
   }

   /**
   * @return the displayName
   */
   public String getDisplayName() {
      return displayName;
   }

   /**
    * @param displayName the displayName to set
    */
   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   /**
   * @return the profileDescription
   */
   public String getProfileDescription() {
      return profileDescription;
   }

   /**
    * @param profileDescription the profileDescription to set
    */
   public void setProfileDescription(String profileDescription) {
      this.profileDescription = profileDescription;
   }

   /**
   * @return the verified
   */
   public boolean isVerified() {
      return verified;
   }

   /**
    * @param verified the verified to set
    */
   public void setVerified(boolean verified) {
      this.verified = verified;
   }

   /**
   * @return the lastCheckedDate
   */
   public Date getLastCheckedDate() {
      return lastCheckedDate;
   }

   /**
    * @param lastCheckedDate the lastCheckedDate to set
    */
   public void setLastCheckedDate(Date lastCheckedDate) {
      this.lastCheckedDate = lastCheckedDate;
   }

   /**
   * @return the accountCreatedAt
   */
   public Date getAccountCreatedAt() {
      return accountCreatedAt;
   }

   /**
    * @param accountCreatedAt the accountCreatedAt to set
    */
   public void setAccountCreatedAt(Date accountCreatedAt) {
      this.accountCreatedAt = accountCreatedAt;
   }

   /**
   * @return the platformId
   */
   public PlatformEntity getPlatformId() {
      return platformId;
   }

   /**
    * @param platformId the platformId to set
    */
   public void setPlatformId(PlatformEntity platformId) {
      this.platformId = platformId;
   }

   /**
   * @return the agentId
   */
   public AgentEntity getAgentId() {
      return agentId;
   }

   /**
    * @param agentId the agentId to set
    */
   public void setAgentId(AgentEntity agentId) {
      this.agentId = agentId;
   }

   /**
    * @return the posts
    */
   public Set<PostEntity> getPosts() {
      return posts;
   }

   /**
    * @param posts the posts to set
    */
   public void setPosts(Set<PostEntity> posts) {
      this.posts = posts;
   }

}
