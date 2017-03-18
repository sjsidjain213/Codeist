package service;

import javax.servlet.http.HttpServletRequest;

public class SessionService {

	public void sessionCreate(HttpServletRequest req,String username)
	{
	req.getSession().setAttribute("username",username);
    }
	
	public boolean sessionVerifier(HttpServletRequest req)
	{
    return (req.getSession().getAttribute("username")==null)?false:true;
	}
    
	public void sessionDestroy(HttpServletRequest req)
	{
		req.getSession().removeAttribute("username");
	}
}
