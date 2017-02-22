package practice.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String address;

	public Address(String address) {
		this.address = address;
	}
	
	public Address() {}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
