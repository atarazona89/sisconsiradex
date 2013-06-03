
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script type="text/javascript" src="../Scripts/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../Scripts/jquery-ui-1.9.2.custom.js"></script>
<link rel="stylesheet" type="text/css" 
      href="<html:rewrite page="/Interfaz/Stylesheets/jquery-ui-1.9.2.custom.css"/>"/>

<script type="text/javascript">
    $(function() {
        $(".fecha_input input").datepicker();
        $(".fecha_click click").datepicker();
    })		
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Agregar Elemento al Catálogo <bean:write name="elementoCatalogoForm"
                    property="nombreCatalogo"/></title>

    </head>
    <body>
        <h1 class='title' id='page-title'>Agregar Elemento al Catálogo <bean:write 
                name="elementoCatalogoForm" property="nombreCatalogo"/> </h1>

        <logic:present name="elementoCatalogoForm" property="mensaje"><br>
            <div class ="status"><bean:write name="elementoCatalogoForm" property="mensaje"/></div><br>
        </logic:present> 
        <logic:present name="elementoCatalogoForm" property="mensajeError"><br>
            <div class ="error"><bean:write name="elementoCatalogoForm" property="mensajeError"/></div><br>
        </logic:present>

        <font size=2>Debe llenar al menos un campo.</font><br><br>

        <html:form action="/RegistrarElemento?method=save">
            <table>    
                <tbody>
                    <logic:iterate name="elementoCatalogoForm" property="camposValores" 
                                   id="camposValores" indexId="index">
                        <tr>
                        <td><bean:write name="camposValores" property="campo.nombre"/> 
                        </td>
                        <td>
                            <logic:equal name="camposValores" property="campo.tipo" value="usbid">
                                <html:text name="camposValores" property="valor" indexed="true"/>  
                            </logic:equal>
                            
                            <logic:equal name="camposValores" property="campo.tipo" value="texto">
                                <html:text name="camposValores" property="valor" indexed="true"/>  
                            </logic:equal>

                            <logic:equal name="camposValores" property="campo.tipo" value="numero">
                                <html:text name="camposValores" property="valor" indexed="true"/> 
                            </logic:equal>

                            <logic:equal name="camposValores" property="campo.tipo" value="fecha">
                            <span class="fecha_input">
                                <html:text name="camposValores" property="valor" indexed="true" 
                                           readonly="true" />
                            </span>
                            <span class="fecha_click">
                                <html:hidden name="camposValores" property="valor" indexed="true"/>
                            </span>
                        </logic:equal>
                        </td>  
                        </tr>
                    </logic:iterate>
                </tbody>
            </table>
            <br>
            <div align="center"><html:submit>Registrar</html:submit></div>

        </html:form>
    </body>
</html>
