<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
   window.onload=function(){
	   var respArea = document.getElementById("result"); 
	   var xhr = new XMLHttpRequest();
	   xhr.open('GET', 'showMember.do?type=json', true);
	   xhr.send();
	   xhr.onreadystatechange = function(){
		   var result = "<table border='1'>";
		   if (xhr.status == 200 && xhr.readyState == 4) {
		       var mems = JSON.parse(xhr.responseText);
		       for(var i=0; i < mems.length; i++) {
		    	   result += "<tr>" +		    	   
		    	       "<td>" + mems[i].userId + "</td>" +
		    	       "<td>" + mems[i].password + "</td>" + 
		    	       "<td>" + mems[i].name + "</td>" +
		    	       "<td>" + mems[i].email + "</td>" +
		    	       "</tr>"
		       }
		       result += "</table>";
		       respArea.innerHTML = result;
		   }
	   }
   }
</script>
</head>
<body>
<div id='result'></div>
</body>
</html>