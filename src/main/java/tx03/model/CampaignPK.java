package tx03.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CampaignPK implements Serializable {
	protected Integer adid;
	protected Integer pdid;

	public CampaignPK(Integer adid, Integer pdid) {
		this.adid = adid;
		this.pdid = pdid;
	}

	public CampaignPK() {
	}

	public Integer getAdid() {
		return adid;
	}

	public void setAdid(Integer adid) {
		this.adid = adid;
	}

	public Integer getPdid() {
		return pdid;
	}

	public void setPdid(Integer pdid) {
		this.pdid = pdid;
	}

}
