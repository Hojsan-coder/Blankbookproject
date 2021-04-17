<%@page import="beans.UserBean"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome page</title>
</head>
<body>  
<p>You are successfully logged in!</p>  


<%
  
  // Check to see if the session has a user bean.
  if(session.getAttribute("user") == null){
	
	// if there is not one, goto the logout servlet
	RequestDispatcher rd = request.getRequestDispatcher("Logout");
	rd.forward(request, response);
	
	
  } else {
	  
	  
	// if there is a session , then all is well  
	
	// get the bean to unpack the user data
	UserBean bean = (UserBean) request.getAttribute("user");
	
	  
	  
	  out.print("<h1>Welcome, " + bean.getName() + "</h1>");
	
	
  }
%>



<!-- Goto the logout servlet to logout..  -->
<p>press this button to log out: </p>
<form action="<%= request.getContextPath() %>/Logout" method="post"> 
   	<input type="submit" value="logout"/>  
</form>

</body>
</html>