package ru.springtest.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;

@Entity(name="_user")
public class User {
	
    @Id
    @GeneratedValue(generator = "TableGenerator")
    @GenericGenerator(name = "TableGenerator", strategy = "org.hibernate.id.enhanced.TableGenerator",
                      parameters = {
        @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "_lawsuit_costs")
    })
    @Column(name = "_id")		
	private Integer id;
	
    @Column(name = "_name")
	private String name;
	
    @Column(name = "_pwd")
	private String pwd;

	public User() {
	}

	public User(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
