<%@page import="beans.PostBean"%>
<%@page import="database.SQLcon"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.UserBean"%>




<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Logged in</title>

<!-- Bootstrap core CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
	crossorigin="anonymous">
<script defer
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
	crossorigin="anonymous"></script>

<link href="postpage.css" rel="stylesheet">

</head>
<body>

	<main class="d-flex justify-content-center h-100">

		<header class="row ">
			<div class="row">
				<%
				// Check to see if the session has a user bean.
				if (session.getAttribute("user") == null) {
					// if there is not one, goto the logout servlet
					RequestDispatcher rd = request.getRequestDispatcher("Logout");
					rd.forward(request, response);
				} else {
					// if there is a session , then all is well  
					// get the bean to unpack the user data
					UserBean userBean = (UserBean) request.getAttribute("user");
					out.print("<h5>Logged in as, " + userBean.getName() + "</h5>");
				}
				%>
			</div>

			<div class="col leftbtnAlign">
				<form action="<%=request.getContextPath()%>/Logout" method="post">
					<button class="btn btn-primary " type="submit">Log out</button>
				</form>
			</div>

			<form class="post-form"
				action="<%=request.getContextPath()%>/PostController" method="POST">
				<textarea rows="10" cols="40" name="content" required="required"
					placeholder="Type your message here." class="formMessage"
					maxlength="255"></textarea>
				<br> <input type="text" class="formHashTag"
					placeholder="#Hashtag*" name="tag" maxlength="25" /> <input
					type="submit" value="Send" class="btn float-right login_btn">
			</form>

			<div class="post">
				<%
				// creates postbean from requestattribute
				PostBean postBean = (PostBean) request.getAttribute("PostBean");

				// print out from arraylists
				if (postBean != null) {
					for (int i = 0; i < postBean.postListCreator.size() ; i++) {
						out.print("<p>");
						out.println(postBean.postListCreator.get(i) + "<br>" + postBean.postListContent.get(i) + "<br>"
						+ postBean.postListTag.get(i) + "<br>" + postBean.postListDate.get(i));
						out.println("</p>");
					}
				}
				%>
			</div>
	</main>

</body>
</html>