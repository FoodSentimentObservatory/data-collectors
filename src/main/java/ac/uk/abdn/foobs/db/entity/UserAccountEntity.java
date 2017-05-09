package ac.uk.abdn.foobs.db.entity;

import java.util.Date;

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
import org.hibernate.annotations.Type;

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
   private PlatformEntity platform;

   @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name="agentId")
   private AgentEntity agent;

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
   * @return the platform
   */
   public PlatformEntity getPlatform() {
      return platform;
   }

   /**
    * @param platform the platform to set
    */
   public void setPlatform(PlatformEntity platform) {
      this.platform = platform;
   }

   /**
    * @return the agent
    */
   public AgentEntity getAgent() {
      return agent;
   }

   /**
    * @param agent the agent to set
    */
   public void setAgent(AgentEntity agent) {
      this.agent = agent;
   }
}
