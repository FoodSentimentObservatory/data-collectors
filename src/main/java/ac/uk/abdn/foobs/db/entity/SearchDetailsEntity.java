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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Search")
public class SearchDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long Id;

	@Column(name = "StartOfSearch")
	private Date startOfSearch;
	@Column(name = "EndOfSearch")
	private Date endOfSearch;
	@Column(name = "Keywords")
	private String keywords;
	@Column(name = "Note")
	private String note;
	@Column(name = "Radius")

	private double radius;


	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "locationId")
	private LocationEntity locationId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "searchDetailsId", cascade = CascadeType.ALL)
	private Set<PostEntity> posts = new HashSet<PostEntity>();

	public Long getId() {
		return Id;
	}

	public Date getStartOfSearch() {
		return startOfSearch;
	}

	public void setStartOfSearch(Date startOfSearch) {
		this.startOfSearch = startOfSearch;
	}

	public Date getEndOfSearch() {
		return endOfSearch;
	}

	public void setEndOfSearch(Date endOfSearch) {
		this.endOfSearch = endOfSearch;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocationEntity getLocationId() {
		return locationId;
	}

	public void setLocationId(LocationEntity locationId) {
		this.locationId = locationId;
	}


	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius2) {
		this.radius = radius2;

	}

	public Set<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(Set<PostEntity> posts) {
		this.posts = posts;
	}
}