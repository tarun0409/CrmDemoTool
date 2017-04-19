<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="utilities.RunCRMClient" %>
<%@ page import="utilities.AuthUtil" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</style>
<title>Insert title here</title>
</head>
<body>
<p class="text2">Thank you for using CRM Demo Tool. The data has been successfully imported into CRM.</p>
<p class="text2">Please press the button to go back to home page</p>
<a href="CRMTool.jsp" class="button">Go Back</a>
<%
	System.out.println(request.getParameter("code"));
	if(!RunCRMClient.isReserved())
	{
		RunCRMClient client = new RunCRMClient();
		String authToken=(String)session.getAttribute("authToken");
		client.startProcess(authToken);	
	}
	else
	{
		System.out.println("\n\n\nInstance already running!!!!!!!\n\n\n");
	}
%>
</body>
</html>