package vote.util;

import vote.pojo.User;

public class UserHolder {
	public static ThreadLocal<User> user=new ThreadLocal<User>();
	public static  User getUser(){
		return user.get();
	}
	public static  void setUser(User users){
		user.set(users);
	}

}
