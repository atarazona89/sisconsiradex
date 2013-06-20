
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SiraDEx | Entradas Directas</title>
    <html:base/>
</head>
<body>
    <h1 class="title">Entrar como otro Tipo de Usuario</h1>

    <logic:present name="mensaje"><br>
        <div class ="error"><bean:write name="mensaje"/></div>
        <br>
    </logic:present>

    <h1>Entrar como:</h1>
    <ul>
        <li><html:link action="/Entrar?method=signinEA">Empleado Administrativo</html:link></li>
        <li><html:link action="/Entrar?method=signinPO">Personal Obrero</html:link></li>
        <li><html:link action="/Entrar?method=signinES">Estudiante</html:link></li>
        <li><html:link action="/Entrar?method=signinProf">Profesor</html:link></li>
        <li><html:link action="/Entrar?method=signinDEx">Jefe de Dependencia del DEx</html:link></li>
    </ul>
</body>