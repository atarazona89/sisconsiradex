<%-- 
    Document   : formBusquedaAvanzada
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
    <script type="text/javascript">
        $(document).ready(function(){

            $(".textolargo").hide();
            $(".mostrar").click(function(){
                $(this).siblings('.textolargo').toggle();
                var $this = $(this);
                $this.text($this.text() == "Menos detalles" ? "Más detalles" : "Menos detalles");
            });
            $(".grafica").hide();
            $(".ver").click(function(){
                $('.grafica').toggle();
                var $this = $(this);
                $this.text($this.text() == "Ocultar Gráfica" ? "Mostrar Gráfica" : "Ocultar Gráfica");
            });
        });
    
    </script>


    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SiraDEx | Búsquedas Avanzadas</title>
</head>

<body>
    <h1 class="title">Búsquedas Avanzadas</h1> 

    <logic:present name="busquedaActividadForm" property="mensaje"><br>
        <div class ="status"><bean:write name="tipoActividadForm" 
                    property="mensaje" /></div>
        </logic:present> 
        <logic:present name="busquedaActividadForm" property="mensajeError"><br>
        <div class ="error"><bean:write name="tipoActividadForm" 
                    property="mensajeError" /></div>
        </logic:present>

    <logic:present name="actividades">
        <logic:notEmpty name="actividades">
            <% String chd = (String) request.getSession().getAttribute("estadisticaCantidad");
                String chl = (String) request.getSession().getAttribute("estadisticaNombres");
                String s = "http://chart.apis.google.com/chart?&cht=p3&chs=800x200&chd=t:";
                String chtt = "Actividades%20de%20extensión&";
                String chco = "chco=ff0000&";
                String chdl = chl;
                s += chd + "chl=" + chl + chtt + chco + "chdl=" + chl;
            %>
            <a class="ver" style=" cursor: pointer; text-decoration:underline">
                Mostrar Gráfica</a>
        <span class="grafica">
            <center>
                <html:img src="<%=s%>"/>
            </center>
        </span>
    </logic:notEmpty>
</logic:present>
<fieldset>
    <logic:notPresent name="actividades">
        <div align="center">
            Para realizar una consulta, seleccione o rellene los campos mostrados a 
            la izquierda de la página. Luego, presione el botón <b>Buscar</b>.
        </div>
    </logic:notPresent>

    <logic:present name="actividades">

        <logic:notEqual name="busquedaActividadForm" property="totalPaginas" value="0">
            <html:form action="/BusquedaAvanzada?method=aPagina">
                Página ${busqueda.pagina} de ${busqueda.totalPaginas}&nbsp;&nbsp;&nbsp;  
                <html:text name="busquedaActividadForm" property="pagina"
                           value="${busqueda.pagina}" size="1"/>&nbsp;&nbsp;<html:submit>Ir a página</html:submit>
            </html:form>
        </logic:notEqual>

        <logic:empty name="actividades">
            <br>
            <div align="center">No hay actividad que mostrar que coincida con los
                parámetros de la búsqueda</div>
            </logic:empty>

        <logic:iterate name="actividades" id="act">
            <br><br>
            <b>
                <% Actividad a = (Actividad) pageContext.findAttribute("act");
                    out.print(a.participantesToString());%>
            </b>
            "<bean:write name="act" property="nombreTipoActividad"/>",


            <% out.print(a.camposValoresToString());%>

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
                                ${archivo.tipo}
                            </html:link> 
                        </html:form>

                    </logic:iterate>
                </span> 

                <a class="mostrar" style=" cursor: pointer; text-decoration:underline">
                    Más detalles</a>
            </div>

        </logic:iterate>
        <br>

        <logic:notEqual name="busquedaActividadForm" property="totalPaginas" value="0">
            <html:form action="/BusquedaAvanzada?method=aPagina">
                Página ${busqueda.pagina} de ${busqueda.totalPaginas}&nbsp;&nbsp;&nbsp;  
                <html:text name="busquedaActividadForm" property="pagina"
                           value="${busqueda.pagina}" size="1"/>&nbsp;&nbsp;<html:submit>Ir a página</html:submit>
            </html:form>
        </logic:notEqual>
    </logic:present>
</fieldset>
</body>





