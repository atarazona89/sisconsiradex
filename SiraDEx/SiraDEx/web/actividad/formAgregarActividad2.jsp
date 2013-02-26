<%-- 
    Document   : formAgregarActividad2
    Created on : 02/11/2012, 05:14:54 PM
    Author     : SisCon
--%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/Interfaz/Stylesheets/jquery-ui-1.9.2.custom.css"/>"/>


<script type="text/javascript">
    $(function() {		
        $("#fecha_input input").datepicker({
            changeMonth: true,
            changeYear: true
        });

        $( "#fecha_input input" ).datepicker(
        "option", "dateFormat", "dd/mm/yy" 
    );
	
        $( "#fecha_input input" ).datepicker({
            dayNamesMin: [ "Dom", "Lun", "Mar", "Mie", "Juev", "Vier", "Sab" ] 
        });		
	
        var dayNamesMin = $( "#fecha_input input" ).datepicker( "option", "dayNames" );
        $( "#fecha_input input" ).datepicker( 
        "option", "dayNamesMin", [ "Dom", "Lun", "Mar", "Mie", "Juev", "Vier", "Sab" ] 
    );

        $( "#fecha_input input" ).datepicker( "option", "yearRange", "1970:2013" );
    })	
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Registrar <bean:write name="actividadForm"
                    property="nombreTipoActividad"/></title>
    </head>
    <body>
        <h1 class='title' id='page-title'>Registro de <bean:write 
                name="actividadForm" property="nombreTipoActividad"/> </h1>

        <logic:present name="actividadForm" property="mensaje">
            <br/><b><div align="center">
                    <bean:write name="actividadForm" property="mensaje" /></div></b><br/>
                </logic:present>
        <p>Los campos con el asterisco  <span style="color:red">*</span> son obligatorios.</p></br>
<table>           
    <html:form method="POST"
               enctype="multipart/form-data"
               action="/RegistrarActividad?method=save2">

        <logic:iterate name="actividadForm" property="camposValores" id="campoValor" indexId="index">
            <tr>
            <td><bean:write name="campoValor" property="campo.nombre"></bean:write>
                <logic:equal name="campoValor" property="campo.obligatorio" value="true">
                <span style="color:red">*</span>  
            </logic:equal>
        </td>
        <td><logic:equal name="campoValor" property="campo.tipo" value="texto">
                <html:text name="campoValor" property="valor" indexed="true"/>  
            </logic:equal>

            <logic:equal name="campoValor" property="campo.tipo" value="numero">
                <html:text name="campoValor" property="valor" indexed="true"/> 
            </logic:equal>

            <logic:equal name="campoValor" property="campo.tipo" value="fecha">
            <span id="fecha_input"> <html:text name="campoValor" property="valor" indexed="true" /></span>  
        </logic:equal>

        <logic:equal name="campoValor" property="campo.tipo" value="checkbox">
            <html:checkbox name="campoValor" property="valor" indexed="true"/>
        </logic:equal>

        <logic:equal name="campoValor" property="campo.tipo" value="textol">
            <html:textarea name="campoValor"  cols="campo.longitud" rows="4"
                           property="valor" indexed="true"></html:textarea>
        </logic:equal>
        <logic:equal name="campoValor" property="campo.tipo" value="archivo">
            <html:file name="campoValor" property="file" indexed="true"/>
        </logic:equal>
        <logic:equal name="campoValor" property="campo.tipo" value="catalogo">
            <html:select name="campoValor" property="valor" indexed="true">
                <html:option value="">
                    -- Seleccione --
                </html:option>
                <html:optionsCollection name="cat" label="contenido" value="contenido"/>
            </html:select>
        </logic:equal>
    </td>  
</tr>
</logic:iterate>
<tr>
<td><bean:write name="tipoAct" property="producto"/><span style="color:red">*</span> </td>    
<td><html:text name="campoValor" property="valor"/> </td><%--falta por hacer--%>  
</tr>
</table>
<br>
<div align="center"><html:submit>Registrar</html:submit></div>

</html:form>
</body>
</html>