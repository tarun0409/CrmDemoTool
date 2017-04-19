<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
#text1 {
	font-family: times, Times New Roman, times-roman, georgia, serif;
 	font-size: 48px;
    line-height: 40px;
    letter-spacing: -1px;
 	color: #444;
 	margin: 0 0 0 0;
 	padding: 0 0 0 0;
    font-weight: 100;
}
#text2 {
	font-family: times, Times New Roman, times-roman, georgia, serif;
 	font-size: 14px;
 	line-height: 20px;
 	text-transform: uppercase;
 	color: #444;
}
#text3 {
	font-family: times, Times New Roman, times-roman, georgia, serif;
 	font-size: 28px;
 	line-height: 40px;
 	letter-spacing: -1px;
 	color: #444;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CRM Demo Tool</title>
</head>
<body>
<p id="text1">Welcome to CRM Demo Tool</p>
<hr>
<p id="text2">This tool is used for demonstration purposes to delete and import data in a test CRM Account</p>
<p id="text2">Please make sure the properties for performing operations are already set in the back end before you start the process.</p>
<hr>
<p id="text3">Press below button to start process!!</p>
<a href="https://accounts.zoho.com/oauth/v2/auth?scope=ZohoCRM.modules.ALL,ZohoCRM.settings.ALL,ZohoCRM.users.ALL&client_id=1000.Y6HRBGVXDSZ2753563NHSIAKRDHS3E&client_secret=a4e24519e2c2f25c8c536cda8957653ff701428d89&response_type=code&access_type=offline&redirect_uri=http://zcrm-u12-opti.csez.zohocorpin.com:7000/CRMClientTool/Confirmation.jsp" class="button">Start Process</a>

</body>
</html>