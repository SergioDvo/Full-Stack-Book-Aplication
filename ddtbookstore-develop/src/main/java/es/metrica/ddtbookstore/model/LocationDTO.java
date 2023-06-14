package es.metrica.ddtbookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;




@Entity
@Table(name = "locations")
public class LocationDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;

	private String street;
	private Integer number;
	private String town;
	private String province;
	private String cp;
	private String telephone;

	public LocationDTO() {

	}


	public LocationDTO(String street, Integer number, String town, String province, String cp, String telephone) {
		this.street = street;
		this.number = number;
		this.town = town;
		this.province = province;
		this.cp = cp;
		this.telephone = telephone;
	}

	public Long getLocationID() {
		return locationId;
	}

	public void setLocationID(Long locationID) {
		this.locationId = locationID;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
