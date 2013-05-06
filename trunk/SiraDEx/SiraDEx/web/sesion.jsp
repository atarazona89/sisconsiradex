<%-- 
    Document   : sesion
    Created on : 26/03/2013, 5:09:52 PM
    Author     : SisCon
--%>

<%@page import="Clases.Usuario"%>
<%@page import="org.apache.struts.tiles.taglib.GetAttributeTag"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario u = (Usuario) request.getSession().getAttribute("user");
    String r = u.getRol();
    if (r.equals("WM") || r.equals("profesor") || r.equals("obrero")
            || r.equals("estudiante") || r.equals("empleado")) {
        r = "";
    }
%>
<logic:equal name="user" property="rol" value="<%= r%>">
    <jsp:forward page="GestionActividades.do?method=listDex"/>
</logic:equal>

<logic:equal name="user" property="rol" value="WM">
    <jsp:forward page="GestionActividades.do?method=listAll"/>
</logic:equal>

<logic:equal name="user" property="rol" value="profesor">
    <jsp:forward page="GestionActividades.do?method=listUser"/>
</logic:equal> 

<logic:equal name="user" property="rol" value="obrero">
    <jsp:forward page="GestionActividades.do?method=listUser"/>
</logic:equal>

<logic:equal name="user" property="rol" value="estudiante">
    <jsp:forward page="GestionActividades.do?method=listUser"/>
</logic:equal>

<logic:equal name="user" property="rol" value="empleado">
    <jsp:forward page="GestionActividades.do?method=listUser"/>
</logic:equal>
