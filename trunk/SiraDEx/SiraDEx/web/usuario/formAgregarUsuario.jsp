<%-- 
    Document   : formAgregarUsuario
    Created on : 24/10/2012, 11:38:28 PM
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
        <script>
            $(document).ready(function(){

                $(".selector").change(function(){
                    var val = $('.selector')[0].value;
                    if(val=="dex"){
                        $('.ocultable').css("visibility", "visible");
                    }else {
                        $('.ocultable').css("visibility", "hidden");
                    }     
                })
            });              
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Registrar Usuario</title>
    </head>
    <body>
        <h1 class="title">Registrar Usuario</h1>

        <logic:present name="usuarioForm" property="mensaje"><br>
            <div class ="status"><bean:write name="usuarioForm" property="mensaje" /></div>
        </logic:present> 
        <logic:present name="usuarioForm" property="mensajeError"><br>
            <div class ="error"><bean:write name="usuarioForm" property="mensajeError" /></div>
        </logic:present>

        <html:form action="/RegistrarUsuario?method=save">

            <table>
                <tbody>
                    <tr>
                    <td width="15%" style="font-weight: bold">USB-ID</td>
                    <td><html:text name="user" property="username" value=""/></td>
                    </tr>
                    <tr>
                    <td style="font-weight: bold">Contraseña</td>
                    <td><html:text name="user" property="password" value=""/></td>
                    </tr>
                    <tr>
                    <td style="font-weight: bold">Nombres</td>
                    <td><html:text name="user" property="nombres" value=""/></td>
                    </tr>
                    <tr>
                    <td style="font-weight: bold">Apellidos</td>
                    <td><html:text name="user" property="apellidos" value=""/></td>
                    </tr>
                    <tr>
                    <td style="font-weight: bold">Teléfono</td>
                    <td><html:text name="user" property="telefono" value=""/></td>
                    </tr>
                    <tr>
                    <td style="font-weight: bold">Correo Electrónico</td>
                    <td><html:text name="user" property="email" value=""/></td>
                    </tr>
                    <tr>
                    <td><b>Rol</b></td>
                    <td>
                        <html:select styleClass="selector"
                                     name="usuarioForm" property="rol" value="">
                            <html:option value="">-- Seleccione --</html:option>
                            <html:option value="empleado">Empleado Administrativo</html:option>
                            <html:option value="estudiante">Estudiante</html:option>
                            <html:option value="obrero">Obrero</html:option>
                            <html:option value="profesor">Profesor</html:option>
                            <html:option value="dex">Personal del DEx</html:option>
                            <html:option value="WM">Webmaster</html:option>
                        </html:select>
                    </td>
                    </tr>
                    <tr>
                    <td>
                    <span align="left" class="ocultable" style="visibility: hidden">
                        <b>Dependencia o Unidad</b>
                    </span></td>
                    <td>
                    <span align="left" class="ocultable" style="visibility: hidden">

                        <html:select name="usuarioForm" property="rolDex">
                            <html:option value="">-- Seleccione --</html:option>
                            <html:optionsCollection name="dependencias" label="contenido" value="contenido"/>

                        </html:select>
                    </span></td>
                    </tr>
                </tbody> 
            </table>  

            <html:submit>Registrar</html:submit>
        </html:form>
    </body>
</html>
