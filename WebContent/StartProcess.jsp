<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="utilities.RunCRMClient" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="CRMTool.jsp" class="button">Go Back</a>
<%
	if(!RunCRMClient.isReserved())
	{
		RunCRMClient client = new RunCRMClient();
		  client.startProcess();	
	}
	else
	{
		System.out.println("\n\n\nInstance already running!!!!!!!\n\n\n");
	}
%>
</body>
</html>