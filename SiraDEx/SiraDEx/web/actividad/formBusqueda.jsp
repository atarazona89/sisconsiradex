<%-- 
    Document   : formBusqueda
    Created on : May 2, 2013, 1:27:11 PM
    Author     : SisCon
--%>

<%@page import="Clases.Actividad"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<head>
    <script type="text/javascript" src="../Scripts/hilitor.js"></script>
    <script type="text/javascript">
        $(document).ready(resaltar);
        var myHilitor;
        function resaltar() {
            myHilitor = new Hilitor("contenido");
            myHilitor.apply(document.getElementsByName("palabras")[0].value);
        }

        $(document).ready(function() {

            $(".textolargo").hide();
            $(".mostrar").text("Más detalles");
            $(".mostrar").click(function() {
                $(this).siblings('.textolargo').toggle();
                var $this = $(this);
                $this.text($this.text() === "Menos detalles" ? "Más detalles" : "Menos detalles");
            });
            $(".grafica").show();
            $(".ver").click(function() {
                $('.grafica').toggle();
                var $ver1 = $(document.getElementsByClassName('ver')[0]);
                $ver1.text($ver1.text() === "[Mostrar Gráfica]" ? "[Ocultar Gráfica]" : "[Mostrar Gráfica]");
                var $ver2 = $(document.getElementsByClassName('ver')[1]);
                $ver2.text($ver2.text() === "[Mostrar Gráfica]" ? "[Ocultar Gráfica]" : "[Mostrar Gráfica]");
            });
        });

    </script>

    <style>
        .ui-button {
            padding: 2px 6px;
            margin: 0;
            cursor: pointer;
            color: #333 !important;
            margin-right: -0.5em !important;       
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Búsquedas</title>
    </head>

    <body>
        <logic:present name="permiso">
            <h1 class="title">Búsquedas Avanzadas</h1>
        </logic:present>
        <logic:notPresent name="permiso">
            <h1 class="title">Búsquedas Públicas</h1>
        </logic:notPresent>


        <logic:notPresent name="actividades">
            <div align="center"><br>
                Para realizar una consulta, seleccione o rellene los campos mostrados a 
                la izquierda de la página. Luego, presione el botón <b>Buscar</b>.
            </div>
        </logic:notPresent>

        <logic:present name="actividades">
            <logic:empty name="actividades">
                <br><br>
                <div align="center">
                    No hay actividad que mostrar que coincida con los
                    parámetros de la búsqueda
                </div>
            </logic:empty>
            <logic:notEmpty name="actividades">
                <h1>Actividades de Extensión</h1>
                <% String chd = (String) request.getSession().getAttribute("graficaCantidad");
                    String chdl = (String) request.getSession().getAttribute("graficaNombres");
                    String s = "http://chart.apis.google.com/chart?&cht=p3&chs=800x200&chd=t:";
                    String chco = "chco=3399CC,00CC00,00FF00,FF00FF,FF0066,FFCC00&";
                    s += chd + "&" + chco + "chdl=" + chdl;
                %>     


                <html:form method="POST">
                    <a class="ver">
                        [Ocultar Gráfica]
                    </a>
                    <html:link style="margin-left:20px;" action="/ExportarActividades" 
                               title="Exportar a JSON">
                        [Exportar Actividades]
                    </html:link> 
                </html:form>
                <div class="grafica">

                    <html:img src="<%=s%>"/>

                    <table class="cebra">
                        <tbody>
                            <tr>
                            <th><b>Tipo de Actividad</b></th>
                            <th><b>Actividades</b></th>
                            </tr>

                            <logic:iterate  name="busquedaActividadForm" property="datosGrafica" 
                                            id="dato"> 
                                <tr>
                                <td style="padding-left:1em">
                                        <bean:write name="dato" property="nombre"/>
                                    </td>
                                    <td align="center">
                                        <bean:write name="dato" property="cantidad"/>
                                    </td>
                                    </tr>
                                </logic:iterate>
                                <tr>
                                <td style="padding-left:1em;background-color:#D3D6FF">
                                    <b>Total</b>
                                </td>
                                <td align="center">
                                    <b>${(busquedaActividadForm.totalActividades)}</b>
                                </td>
                                </tr>
                            </tbody>
                        </table>


                        <html:form method="POST">
                            <a class="ver">
                                [Ocultar Gráfica]
                            </a>
                            <html:link style="margin-left:20px;" action="/ExportarActividades" 
                                       title="Exportar a JSON">
                                [Exportar Actividades]
                            </html:link> 
                        </html:form>


                    </div>                  
                    <br>

                    <div class="ui-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix"
                         style="font-weight: normal;padding: 5px;">
                    Encontradas <b>${(busquedaActividadForm.totalActividades)} actividades</b>
                </div>
                <div id="contenido">
                    <table class="cebra" style="margin:0">
                            <tbody>
                                <logic:iterate name="actividades" id="act" indexId="index">
                                    <tr>
                                    <td>
                                        ${(busquedaActividadForm.pagina - 1) * busquedaActividadForm.mostrarPorPagina + index + 1}.
                                    </td>
                                    <td>
                                        <b><% Actividad a = (Actividad) pageContext.findAttribute("act");
                                                out.print(a.participantesToString());%></b>
                                        "<bean:write name="act" property="nombreTipoActividad"/>",


                                        <% out.print(a.camposValoresToString(a.getIdActividad()));%>

                                        <logic:equal name="act" property="validacion" value="En espera">
                                            <b>Actividad por validar.</b>
                                        </logic:equal>
                                        <br>
                                        <div>
                                            <span class="textolargo">

                                                <b>Descripción:</b> 
                                                <bean:write name="act" property="descripcion"/>

                                                <logic:iterate name="act" property="camposValores" 
                                                               id="campoValor" indexId="index">

                                                    <logic:equal name="campoValor" property="campo.tipo" 
                                                                 value="textol">
                                                        <br>
                                                        <bean:write name="campoValor" property="campo.nombre"/>: 
                                                        <bean:write name="campoValor" property="valor"/>
                                                    </logic:equal>

                                                </logic:iterate>
                                                <logic:iterate name="act" property="archivos" 
                                                               id="archivo" indexId="index">

                                                    <html:form method="POST">
                                                        <html:hidden name="act" property="idActividad"/>
                                                        <html:hidden name="act" property="idArchivo" value="${index}"/>
                                                        <html:link action="/MostrarPDF" paramName="act" paramProperty="idActividad" 
                                                                   paramId="idActividad" title="Descargar">
                                                            ${archivo.nombre}
                                                        </html:link> 
                                                    </html:form>

                                                </logic:iterate>
                                            </span> 

                                            <a class="mostrar" id="detalles" style="text-decoration:underline">
                                                Más detalles</a>
                                        </div>
                                    </td>
                                    </tr>
                                </logic:iterate>
                            </tbody>
                        </table>
                    </div>
                    <% String permiso = (String) request.getSession().getAttribute("permiso");
                        String accion;
                        if (permiso == null) {
                            accion = "/BusquedaPublica?method=aPagina";
                        } else {
                            accion = "/BusquedaAvanzada?method=aPagina";
                        }
                    %>
                    <div  class="ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix" style="padding: 5px;">
                    <div style="font-weight: normal;width: 50%;float: left">
                            &nbsp;Página ${busquedaActividadForm.pagina} de ${busquedaActividadForm.totalPaginas}
                        </div>
                        <div style="float: right;text-align: right;width: 350px !important;">


                        <logic:equal name="busquedaActividadForm" property="pagina" value="1">
                            <a class="ui-corner-tl ui-corner-bl ui-button ui-state-default ui-state-disabled">
                                Primera
                            </a>
                            <a class="ui-button ui-state-default ui-state-disabled">
                                Anterior
                            </a> 
                        </logic:equal>

                        <logic:notEqual name="busquedaActividadForm" property="pagina" value="1">
                            <html:link action="<%=accion%>"
                                       styleClass="ui-corner-tl ui-corner-bl ui-button ui-state-default"
                                       title="Ir a la primera página"
                                       onclick="var acc = this.href.split('&')[0];
                                       this.href=acc.concat('&pagina=1');">
                                Primera
                            </html:link> 
                            <html:link action="<%=accion%>" 
                                       styleClass="ui-button ui-state-default"
                                       title="Ir a la página anterior"
                                       onclick="if (${busquedaActividadForm.pagina} > 1){
                                       var acc = this.href.split('&')[0];
                                       this.href=acc.concat('&pagina=${busquedaActividadForm.pagina - 1}');
                                       }">
                                Anterior
                            </html:link>
                        </logic:notEqual>

                        <logic:iterate name="busquedaActividadForm" property="botonesPaginas" id="pag">
                            <logic:equal name="pag" value="${busquedaActividadForm.pagina}">
                                <a class="ui-button ui-state-default ui-state-disabled">
                                    ${pag}
                                </a>
                            </logic:equal>
                            <logic:notEqual name="pag" value="${busquedaActividadForm.pagina}">
                                <html:link action="<%=accion%>" 
                                           styleClass="ui-button ui-state-default"
                                           title="Ir a la página ${pag}"
                                           onclick="var acc = this.href.split('&')[0];
                                           this.href=acc.concat('&pagina=${pag}');">
                                    ${pag}
                                </html:link>
                            </logic:notEqual>
                        </logic:iterate>
                        <logic:equal name="busquedaActividadForm" property="pagina" 
                                     value="${busquedaActividadForm.totalPaginas}">
                            <a class="ui-button ui-state-default ui-state-disabled">
                                Siguiente
                            </a>
                            <a class="ui-corner-tr ui-corner-br ui-button ui-state-default ui-state-disabled">
                                Última
                            </a>&nbsp; 
                        </logic:equal>
                        <logic:notEqual name="busquedaActividadForm" property="pagina" 
                                        value="${busquedaActividadForm.totalPaginas}">

                            <html:link action="<%=accion%>" 
                                       styleClass="ui-button ui-state-default"
                                       title="Ir a la página siguiente"
                                       onclick="if (${busquedaActividadForm.pagina} < ${busquedaActividadForm.totalPaginas}){
                                       var acc = this.href.split('&')[0];
                                       this.href=acc.concat('&pagina=${busquedaActividadForm.pagina + 1}');
                                       }">
                                Siguiente
                            </html:link> 
                            <html:link action="<%=accion%>" 
                                       styleClass="ui-corner-tr ui-corner-br ui-button ui-state-default"
                                       title="Ir a la última página"
                                       onclick="var acc = this.href.split('&')[0];
                                       this.href=acc.concat('&pagina=${busquedaActividadForm.totalPaginas}');">
                                Última
                            </html:link>&nbsp; 
                        </logic:notEqual>
                    </div>
                </div>
                <div align="center">

                </div>
            </logic:notEmpty>
        </logic:present>
    </body>





