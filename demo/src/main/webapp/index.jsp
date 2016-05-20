<html>
<body>
<h2>Hello World!</h2>

Your IP address 1: <%= request.getRemoteAddr() %><br/>
Your IP address 2: <%= request.getHeader("x-forwarded-for") %><br/>

<h3>Some words</h3>

<ul>
<%
String[] words = new String[] {"some", "test", "words"};
for (String word : words) {
   out.println("<li>"+word+"</li>");
}
%>
</ul>

</body>
</htmlM
