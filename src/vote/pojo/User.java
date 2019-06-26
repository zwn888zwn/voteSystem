package vote.pojo;

import javax.validation.constraints.*;

public class User {
	@Size(min = 4, max = 14, message="³¤¶È´íÎó")
	String id;
	String password;
	String username;
	int sex;
	int age;
	int authority=0;// 0=nomall,1=manager
	String qq;
	public User(){}
	public User(String id){
		this.id=id;
	}
	
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	
	
}
