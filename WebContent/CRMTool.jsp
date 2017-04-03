<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>Press below button to start process!!</h3>
<h4>Please make sure you have the required properties set up in app_properties.txt</h4>
<a href="https://accounts.zoho.com/oauth/v2/auth?scope=ZohoCRM.modules.ALL,ZohoCRM.settings.ALL&client_id=1000.Y6HRBGVXDSZ2753563NHSIAKRDHS3E&client_secret=a4e24519e2c2f25c8c536cda8957653ff701428d89&response_type=code&access_type=offline&redirect_uri=http://localhost:7000/CRMClientTool/StartProcess.jsp" class="button">Start Process</a>

</body>
</html>