package com.kang.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(exclude = { "id" })
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Customer.byNameQuery",query = "from Customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

	public Customer(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	

}
