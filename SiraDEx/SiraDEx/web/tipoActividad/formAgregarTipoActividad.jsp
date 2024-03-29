<%-- 
    Document   : formAgregarTipoActividad
    Created on : Oct 26, 2012, 8:37:29 AM
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
        <style>
            .selector {width: 126px;}
            .selectlargo {width: 570px;}
        </style>
        <script>
            $(document).ready(function() {
                function visibilidad(valor, mostrador, mostrador2, longitud) {
                    //var valor = $(this).val();

                    if (valor === "catalogo") {
                        $('#' + longitud).css("visibility", "hidden");
                        $('.' + mostrador).css("visibility", "visible");
                        $('.' + mostrador2).css("visibility", "hidden");
                    } else if (valor === "participante") {
                        $('#' + longitud).css("visibility", "visible");
                        $('.' + mostrador).css("visibility", "hidden");
                        $('.' + mostrador2).css("visibility", "visible");
                    } else if (valor === "texto" || valor === "textol"
                            || valor === "numero") {
                        $('#' + longitud).css("visibility", "visible");
                        $('.' + mostrador).css("visibility", "hidden");
                        $('.' + mostrador2).css("visibility", "hidden");
                    } else {
                        $('#' + longitud).css("visibility", "hidden");
                        $('.' + mostrador).css("visibility", "hidden");
                        $('.' + mostrador2).css("visibility", "hidden");
                    }
                }
                $(".selector").change(function(evento) {
                    var tg = evento.target.id;
                    var mos = "mostrador" + tg.slice("selector".length);
                    var mos2 = "mostrador2" + tg.slice("selector".length);
                    var lon = "longitud" + tg.slice("selector".length);
                    var val = $('.selector')[tg.slice("selector".length)].value;
                    visibilidad(val, mos, mos2, lon);
                });
            });
        </script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Agregar Tipo de Actividad</title>
    </head>

    <body>
        <logic:notPresent name="permiso">
            <div align="center" class ="warning">
                Usted no tiene permiso para acceder a esta página del SiraDEx.
            </div>
        </logic:notPresent>
        <logic:present name="permiso">
            <h1 class="title">Registro de Tipo de Actividad</h1>

            <logic:present name="tipoActividadForm" property="mensaje"><br>
                <div class ="error"><bean:write name="tipoActividadForm" property="mensaje"/></div>
                <br>
            </logic:present>
            <br>
            <font size=2>
                Los campos con el asterisco <span style="color:red">*</span> 
                son obligatorios.
            </font><br>
            <html:form action="/RegistrarTipoActividad?method=save">
                <table>
                    <tbody>
                        <tr>
                        <td width="16%">
                            <b>Nombre del Tipo de Actividad</b> <span style="color:red">*</span>
                        </td>
                        <td><html:text name="tipoActividadForm" property="nombreTipo" 
                                   size="78"/>
                        </td>
                        </tr>
                        <tr>
                        <td>
                            <b>Descripción</b> <span style="color:red">*</span>
                        </td>
                        <td>
                            <html:textarea name="tipoActividadForm"  cols="80" rows="2"
                                           property="descripcion"/>
                        </td>
                        </tr>
                        <tr>
                        <td>
                            <b>Tipo de Producto</b> <span style="color:red">*</span>
                        </td>
                        <td>
                            <html:radio property="tipoPR" value="P" 
                                        title="Evaluables por pares académicos">
                                P
                            </html:radio>
                            <html:radio property="tipoPR" value="R"
                                        title="No evaluables por pares académicos">
                                R
                            </html:radio>
                        </td>
                        </tr>
                        <tr>
                        <td>
                            <b>Programa</b> <span style="color:red">*</span>
                        </td>
                        <td>
                            <html:select property="programa" styleClass="selectlargo">
                                <html:option value="">-- Seleccione --</html:option>
                                <html:optionsCollection name="programas" label="contenido" value="contenido"/>

                            </html:select>
                        </td>
                        </tr>
                        <logic:equal name="user" property="rol" value="WM">
                            <tr>
                            <td>
                                <b>Dependencia a validar</b> <span style="color:red">*</span>
                            </td>
                            <td>
                                <html:select property="validador" styleClass="selectlargo">
                                    <html:option value="">-- Seleccione --</html:option>
                                    <html:optionsCollection name="dependencias" label="contenido" value="contenido"/>
                                </html:select>
                            </td>
                            </tr>
                        </logic:equal>
                        <logic:notEqual name="user" property="rol" value="WM">
                            <html:hidden property="validador" value="${user.rol}"/>
                        </logic:notEqual>    
                        <tr>
                        <td>
                            <b>Realizado por</b> <span style="color:red">*</span>
                        </td>
                        <td>
                            <html:multibox property="permisos">Empleado</html:multibox> Empleados Administrativos<br>
                            <html:multibox property="permisos">Estudiante</html:multibox> Estudiantes<br>
                            <html:multibox property="permisos">Profesor</html:multibox> Profesores<br>
                            <html:multibox property="permisos">Obrero</html:multibox> Obreros 
                            </td>       
                            </tr>
                        </tbody>
                    </table>
                    <font size=2>
                        Los campos siguientes son variables.
                    </font><br>
                    <table class="cebra">
                        <tbody>
                            <tr>
                            <th></th>
                            <th><b>Nombre</b> <span style="color:red">*</span></th>
                            <th><b>Tipo</b></th>
                            <th><b>Longitud/Límite</b> <span style="color:red">*</span></th>
                            <th><b>Obligatorio</b></th>
                            <th><b>Catálogo</b> <span style="color:red">*</span></th>
                            <th>Eliminar</th>
                            </tr>      

                        <logic:iterate name="tipoActividadForm" property="campos" id="campos"
                                       indexId="index">
                            <%
                                int i = (Integer) pageContext.getAttribute("index");
                                String s = "selector" + i;
                                String m = "mostrador" + i;
                                String m2 = "mostrador2" + i;
                                String l = "longitud" + i;
                            %>

                            <tr>
                            <td><span style="color: gray;font-size:10px">${index+1}</span></td>
                            <td align="center">
                                <html:text name="campos" property="nombre" indexed="true" size="30"/>

                            </td>

                            <td align="center">
                                <html:select name="campos"  property="tipo" indexed="true" 
                                styleId="<%=s%>" styleClass="selector">
                                    <html:optionsCollection name="campos" property="tipos" 
                                                            label="etiqueta" value="valor"/>
                                </html:select>   
                            </td>


                            <td align="center">
                                <logic:equal name="campos" property="tipo" value="fecha">
                                    <div id="<%=l%>" style="visibility: hidden">
                                        <html:text name="campos" property="longitud" indexed="true" 
                                                   title="Si el campo es tipo 'texto' o 'número' indica la cantidad máxima de caracteres o dígitos que podrá almacenar, si es tipo 'catálogo de usuarios' indica la cantidad máxima que se podrán agregar de estos campos" 
                                                   size="2" maxlength="4"/>
                                    </div>
                                </logic:equal>
                                <logic:equal name="campos" property="tipo" value="archivo">
                                    <div id="<%=l%>" style="visibility: hidden">
                                        <html:text name="campos" property="longitud" indexed="true" 
                                                   title="Longitud/Límite: si el campo es tipo 'texto' o 'número' indica la cantidad máxima de caracteres o dígitos que podrá almacenar, si es tipo 'catálogo de usuarios' indica la cantidad máxima que se podrán agregar de estos campos" 
                                                   size="2" maxlength="4"/>
                                    </div>
                                </logic:equal>
                                <logic:notEqual name="campos" property="tipo" value="fecha">
                                    <logic:notEqual name="campos" property="tipo" value="archivo">
                                        <div id="<%=l%>" style="visibility: visible">
                                            <html:text name="campos" property="longitud" indexed="true" 
                                                       title="Longitud/Límite: si el campo es tipo 'texto' o 'número' indica la cantidad máxima de caracteres o dígitos que podrá almacenar, si es tipo 'catálogo de usuarios' indica la cantidad máxima que se podrán agregar de estos campos" 
                                                       size="2" maxlength="4"/>     
                                        </div>
                                    </logic:notEqual>
                                </logic:notEqual>
                            </td>


                            <td align="center">
                                <html:checkbox name="campos" property="obligatorio" indexed="true"
                                               title="Obligatorio: indica que el campo será obligatorio"/>
                                <html:hidden name="campos" property="obligatorio" value="false" 
                                             indexed="true"/>
                            </td>

                            <td align="center">
                                <logic:equal name="campos" property="tipo" value="catalogo">
                                    <div class="<%=m%>" style="visibility: visible">
                                        <html:select name="campos" property="catalogo" indexed="true">                          
                                            <html:option value="">-- Seleccione --</html:option>

                                            <html:optionsCollection name="catalogos" label="nombre" 
                                                                    value="nombre"/>
                                        </html:select>
                                    </div>
                                </logic:equal>

                                <logic:notEqual name="campos" property="tipo" value="catalogo">
                                    <div class="<%=m%>" style="visibility: hidden">
                                        <html:select name="campos" property="catalogo" indexed="true">                          
                                            <html:option value="">-- Seleccione --</html:option>

                                            <html:optionsCollection name="catalogos" label="nombre" 
                                                                    value="nombre"/>
                                        </html:select>
                                    </div>
                                </logic:notEqual>

                                <logic:equal name="campos" property="tipo" value="participante">
                                    <div class="<%=m2%>" style="visibility: visible">
                                        <html:select name="campos" property="catalogoPart" indexed="true">                          
                                            <html:option value="">-- Seleccione --</html:option>

                                            <html:optionsCollection name="catalogosPart" label="nombre" 
                                                                    value="nombre"/>
                                        </html:select>
                                    </div>
                                </logic:equal>

                                <logic:notEqual name="campos" property="tipo" value="participante">
                                    <div class="<%=m2%>" style="visibility: hidden">
                                        <html:select name="campos" property="catalogoPart" indexed="true">                          
                                            <html:option value="">-- Seleccione --</html:option>

                                            <html:optionsCollection name="catalogosPart" label="nombre" 
                                                                    value="nombre"/>
                                        </html:select>
                                    </div>
                                </logic:notEqual>   
                            </td>

                            <td td align="center">
                                <html:checkbox name="campos" property="eliminado" indexed="true"
                                               onclick="if (this.checked) {
                                               anterior = document.getElementById('submit').value;
                                               document.getElementById('submit').value='Eliminar Campos'
                                               } else {
                                               document.getElementById('submit').value=anterior
                                               }"
                                               title="Eliminar"/>
                                <html:hidden name="campos" property="eliminado" value="false" 
                                             indexed="true"/>
                            </td>

                            </tr>
                        </logic:iterate>
                    </tbody>
                </table>
                <span style="color:#0ADF12;font-weight:bold;font-size:1.2em">Más campos</span>
                <html:text name="tipoActividadForm" styleId="mas"
                           property="nroCampos" value="0" size="1" maxlength="2"
                           onkeyup="if(this.value > 0 
                           && document.getElementById('submit').value!='Eliminar Campos') {
                           document.getElementById('submit').value='Agregar Campos'
                           } else if (this.value <= 0 
                           && document.getElementById('submit').value!='Eliminar Campos'){
                           document.getElementById('submit').value='Registrar'
                           }"/>

                <div align="center">
                    <html:submit value="Registrar" styleId="submit"/>
                </div>

            </html:form>
        </logic:present>
    </body>
</html>
