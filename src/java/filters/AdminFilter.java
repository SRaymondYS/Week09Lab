package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.UserService;

/**
 *
 * @author 794471
 */
public class AdminFilter implements Filter 
{
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException 
    {       
        HttpServletRequest hsr = (HttpServletRequest)request;
        HttpSession session = hsr.getSession();
        
        String email = (String) session.getAttribute("email");
        
        UserService us = new UserService();
        
      
        try 
        {
            User u = us.get(email);
            Role r = u.getRole();
            
            String roleName = r.getRoleName();
            
            if(!roleName.contains("admin"))
            {
                HttpServletResponse hsre = (HttpServletResponse)response;
                hsre.sendRedirect("home");
                return;
            }
        } 
        catch (Exception ex) 
        {

        }
        
        chain.doFilter(request, response);
    }

    
    /**
     * Destroy method for this filter
     */
    public void destroy() 
    {  
        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) 
    {        

    }
}
