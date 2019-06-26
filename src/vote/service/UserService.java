package vote.service;

import javax.servlet.http.HttpSession;

import vote.pojo.User;

public interface UserService {
		void saveUser(User user);
		User getUserBySession(HttpSession session);
		User getUserById(String id);
}
