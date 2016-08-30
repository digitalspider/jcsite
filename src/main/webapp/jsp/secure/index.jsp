<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/jsp/include/taglibs.jsp"%>

<!DOCTYPE html>

<s:layout-render name="/jsp/layout/public.jsp" pageTitle="Login">
    <s:layout-component name="contents">
<%
String username = request.getRemoteUser();
%>
<span>Hello <%= username %>. This is a secure resource</span>

<ul>
	<li><a href="${ctx}/secure/index" class="nav-link">Account Settings</a></li>
	<li><a href="${ctx}/secure/server" class="nav-link">Servers</a></li>
	<li><a href="${ctx}/secure/invoice" class="nav-link">Invoices</a></li>
</ul>
	
    </s:layout-component>
</s:layout-render>