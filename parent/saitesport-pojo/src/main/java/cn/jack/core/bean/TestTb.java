package cn.jack.core.bean;

import java.io.Serializable;
import java.util.Date;

public class TestTb implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TestTb() {
		super();
	}
	public TestTb(Integer id, String name, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
	}
	private Integer id;
	private String name;
	private Date birthday;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "TestTb [id=" + id + ", name=" + name + ", birthday=" + birthday + "]";
	} 
	
	
}
