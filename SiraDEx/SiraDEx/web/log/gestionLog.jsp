<%-- 
    Document   : gestionarLog
    Created on : May 30, 2013, 11:27:56 PM
    Author     : SisCon
--%>
<%@page import="Clases.Actividad"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>

        <script type="text/javascript" src="../Scripts/jquery.min.js"></script>
        <script type="text/javascript" language="javascript" src="../Scripts/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="../Scripts/ColReorderWithResize.js"></script>
        <style type="text/css" title="currentStyle">
            @import "../Stylesheets/demo_table_jui.css";
        </style>
        <script>
            $(document).ready(function() {

                $(".textolargo").hide();
                $(".mostrar").click(function() {
                    $(this).siblings('.textolargo').toggle();
                    var $this = $(this);
                    $this.text($this.text() == "Menos detalles" ? "Más detalles" : "Menos detalles");
                });

                $('#datatab').dataTable({
                    "aoColumns": [
                        /* Id */ null,
                        /* IP Remitente */ null,
                        /* Usuario */ {"bSortable": false},
                        /*Tipo de Accion*/{"bSortable": true},
                        /*Fecha*/ null,
                    ]});
            });
        </script>


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SiraDEx | Log del sistema </title>
    </head>
    <body>

        <h1 class="title">Consulta del Log de actividades del sistema</h1>
        <br>

        <logic:present name="logs">

            <table cellpadding="0" cellspacing="0" border="0" class="display" id="datatab">
                <thead>
                    <tr>
                    <th>Id</th>
                    <th>IP Remitente</th>
                    <th>Usuario</th>
                    <th>Tipo de Accion</th>
                    <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>

                    <logic:iterate name="logs" id="log">
                        <tr>
                        <td>
                            <bean:write name="log" property="idLog"/>

                        </td>
                        <td>
                            <bean:write name="log" property="ip"></bean:write>
                            </td>
                            <td>
                            <bean:write name="log" property="usbid"></bean:write>
                            </td>
                            <td>
                            <bean:write name="log" property="accion"></bean:write>
                            <span class="textolargo"><br>
                                Descripción: <br>
                            <bean:write name="log" property="query"/>
                            </span>  

                            <br>
                            <a class="mostrar" style=" cursor: pointer">
                                Más detalles
                            </a>
                            </td>
                            <td>
                            <bean:write name="log" property="fecha"></bean:write>
                            </td>
                            </tr>
                    </logic:iterate>   
                </tbody> 
            </table>
        </logic:present>

    </body>
</html>
