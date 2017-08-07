package tx03.model;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="Campaign")
public class Campaign {
	@EmbeddedId
	CampaignPK  campaignPK;
//	private Integer adid;
//	private Integer pdid;
	private Timestamp startDateTime;
	private Timestamp endDateTime;
	private Integer productPrice;

	public Campaign(CampaignPK  campaignPK, Timestamp startDateTime, Timestamp endDateTime, Integer productPrice) {
		this.campaignPK = campaignPK;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.productPrice = productPrice;
	}

	public Campaign() {
	}
	
	public Timestamp getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Timestamp getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public CampaignPK getCampaignPK() {
		return campaignPK;
	}

	public void setCampaignPK(CampaignPK campaignPK) {
		this.campaignPK = campaignPK;
	}

}
