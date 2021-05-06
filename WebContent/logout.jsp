<%
//session.invalidate();
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie ck : cookies){
	if(ck.getName().equals("email")) 
		ck.setMaxAge(0);
		ck.setValue(null);
	//ck.setPath("/");
	response.addCookie(ck);
}
response.sendRedirect("index.jsp");
}
%>
