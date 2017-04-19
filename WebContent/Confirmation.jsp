<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ page import="utilities.AuthUtil" %>
<%@ page import="org.json.JSONObject" %>
<style>
.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}
.text2 {
	font-family: times, Times New Roman, times-roman, georgia, serif;
 	font-size: 16px;
 	line-height: 22px;
 	text-transform: uppercase;
}
.well {
	height: 100px;
  	width: 550px;
  	background: rgba(0, 0, 0, 0);
  	border-radius: 10px;
  	box-shadow: inset 0 0 10px black, 0 0 10px black;
  	padding: 10px;
  	display: inline-block;
  	margin: 15px;
  	vertical-align: top;
  	text-align: center;
}
.userInfo {
	font-weight: bold;
}
</style>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account Confirmation</title>
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
<div>
<p class="text2">WARNING: The tool is designed to make insert and delete operations on existing records!</p>
<p class="text2">Hence kindly make sure you have logged in into the demo account for using this tool. Account details are below</p>
</div>
<br>
<div class="well">
<p class="userInfo"><%= userEmail %></p>
<p class="userInfo"><%= userFullName %></p>
</div>
<br><br>
<form action="StartProcess.jsp" method="post">
    <%
    session.setAttribute("authToken", authToken);
        %>
        <input type="submit" value="Proceed" id="submit" class="button">
        <input type="submit" value="Go Back" id="back" class="button" formaction="CRMTool.jsp">      
</form>
</body>
</html>