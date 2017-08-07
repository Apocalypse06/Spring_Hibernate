<%@ page contentType="text/html; charset=UTF-8" 
    import='org.springframework.web.context.support.*' 
    import='org.springframework.context.*, javax.sql.*, java.sql.*'
%> 
<html>
<body>
<%--
   
   ApplicationContext ctx = 
   		WebApplicationContextUtils.getWebApplicationContext(application);

   DataSource ds= (DataSource)ctx.getBean("dataSource");
   Connection con = ds.getConnection();
   System.out.println(con);
--%>
<h2>Hello World!</h2>
<hr>
<a href='_01_ShowMember/showMember.do'>顯示會員</a><br>
<a href='_01_ShowMember/showMemberJson.jsp'>顯示會員(JSON)</a><br>
</body>
</html>
