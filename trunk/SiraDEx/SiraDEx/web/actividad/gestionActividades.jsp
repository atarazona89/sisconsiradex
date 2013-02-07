<%-- 
    Document   : gestionActividad
    Created on : 31/10/2012, 08:41:09 AM
    Author     : SisCon
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Gestion de Actividades</title>
    </head>
    <body>
        <h1 class="title" id="page-title">Gestion de Actividades</h1>
        <logic:present name="actividadForm" property="mensaje">
            <bean:write name="actividadForm" property="mensaje" /><br/>
        </logic:present>

        <logic:equal name="user" property="rol" value="WM">

            <html:link action="/RegistrarActividad?method=page"> 
                Registrar actividad
            </html:link><br/>  
        </logic:equal>

        <logic:equal name="user" property="rol" value="CU"> 

            <html:link action="/RegistrarActividad?method=page"> 
                Registrar actividad
            </html:link><br/>        
        </logic:equal>

        <logic:equal name="user" property="rol" value="Prof">

            <html:link action="/RegistrarActividad?method=page"> 
                Registrar actividad
            </html:link><br/>     
        </logic:equal>

        <h1 class="title" id="page-title">Actividades registradas en el sistema</h1>
        <logic:notPresent name="acts">
            No hay actividad que mostrar
        </logic:notPresent>
        <logic:present name="acts">
            <logic:empty name="acts">
                No hay actividad que mostrar
            </logic:empty>
            <table>
                <logic:iterate name="acts" id="act">
                    <tr>
                    <td><b><bean:write name="user" property="apellido"></bean:write>, 
                        <bean:write name="user" property="nombre"></bean:write></b></td> 
                    <td>"<bean:write name="act" property="nombreTipoActividad"/>"</td>
                    <td><html:form method="POST" action="/EliminarActividad">
                        <html:hidden name="act" property="idActividad" />
                        <html:submit>Eliminar</html:submit>
                    </html:form></td>
                    </tr>
                    <tr></tr>
                </logic:iterate>
            </table>
        </logic:present>

    </body>
</html>