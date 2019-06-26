package vote.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import vote.pojo.User;
import vote.util.UserHolder;
public class AuthInterceptor implements HandlerInterceptor  {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object arg2) throws Exception {
		if(req.getRequestURI().contains("login.do")||req.getRequestURI().contains("registe")){
			return true;
		}
		Object user = req.getSession().getAttribute("userSession");
		if(user==null){
			resp.sendRedirect(req.getContextPath()+"/login.do");
			 return false;
		}
		UserHolder.setUser((User) user);
		return true;
	}

}
