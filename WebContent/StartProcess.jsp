<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="utilities.RunCRMClient" %>
<%@ page import="utilities.AuthUtil" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="CRMTool.jsp" class="button">Go Back</a>
<%
	System.out.println(request.getParameter("code"));
	if(!RunCRMClient.isReserved())
	{
		RunCRMClient client = new RunCRMClient();
		String authToken=(String)session.getAttribute("authToken");
		//String authToken = AuthUtil.getAuthtoken(code);
		client.startProcess(authToken);	
	}
	else
	{
		System.out.println("\n\n\nInstance already running!!!!!!!\n\n\n");
	}
%>
</body>
</html>