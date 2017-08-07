package tx03.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Advertise")
public class Advertise {
	private Integer adid;
	private Integer status;
	private String description;

	public Advertise(Integer adid, Integer status, String description) {
		this.adid = adid;
		this.status = status;
		this.description = description;
	}

	public Advertise() {
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getAdid() {
		return adid;
	}

	public void setAdid(Integer adid) {
		this.adid = adid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
