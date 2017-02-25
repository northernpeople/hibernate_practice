package practice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	private Long id;
	private String name;
	private String phone;
	
	@ElementCollection(fetch= FetchType.EAGER)
	private Set<String> tags = new HashSet<>();
	
	public Contact() {}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", phone=" + phone + ", tags=" + tags + "]";
	}
	
	
	
	
}
