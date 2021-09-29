package it.majorbit.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Table
@Entity
public class Background {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String code;
	@Column
	private String image;
	@Column
	private Integer cost = 0;
	
	
	
	public Background() {
		super();
	}
	
	public Background(String code, String image) {
		super();
		this.code = code;
		this.image = image;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Background [code=" + code + ", image=" + image + ", cost=" + cost + "]";
	}
	
	

}
