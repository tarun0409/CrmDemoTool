<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="utilities.AuthUtil" %>
<%@ page import="org.json.JSONObject" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Please confirm the account being used</title>
</head>
<body>
<%
	String code=request.getParameter("code");
	System.out.println(code);
	String authToken = AuthUtil.getAuthtoken(code);
	JSONObject user = AuthUtil.getCurrentUserDetails(authToken);
	String userEmail = "";
	String userFullName = "";
	if(user.has("email"))
	{
		userEmail+="ACCOUNT EMAIL ID: "+user.getString("email")+"\n\n";
	}
	if(user.has("full_name"))
	{
		userFullName+="USER FULL NAME: "+user.getString("full_name")+"\n\n";
	}
%>
<h3>WARNING: The tool is designed to make insert and delete operations on existing records!</h3>
<h3>Hence kindly make sure you have logged in into the demo account for using this tool. Account details are below</h3>
<br>
<h2>
<%= userEmail %>
</h2>
<br>
<h2>
<%= userFullName %>
</h2>
<form action="StartProcess.jsp" method="post">
    <%
    session.setAttribute("authToken", authToken);
        %>
        <input type="submit" value="Proceed">
    </form>
</body>
</html>