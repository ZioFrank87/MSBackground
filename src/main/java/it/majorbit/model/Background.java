package it.majorbit.model;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;


@Table
@Entity
public class Background {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String code;
	@Lob
	@Column
	private String image;
	@Column
	private Integer cost = 0;
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate enabledUntil;
	
	
	
	public Background() {
		super();
	}
	
	public Background(String code, String image, LocalDate date) {
		super();
		this.code = code;
		this.image = image;
		enabledUntil=date;
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

	
	public LocalDate getEnabledUntil() {
		return enabledUntil;
	}

	public void setEnabledUntil(LocalDate enabledUntil) {
		this.enabledUntil = enabledUntil;
	}

	@Override
	public String toString() {
		return "Background [code=" + code + ", image=" + image + ", cost=" + cost + ", enabledUntil=" + enabledUntil
				+ "]";
	}

	
}
