/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Actividad;

import Clases.Actividad;
import Clases.BusquedaActividad;
import Clases.ElementoCatalogo;
import Clases.Root;
import Clases.TipoActividad;
import Clases.Usuario;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author Siscon
 */
public class Buscar extends DispatchAction {

    /* forward name="success" path="" */
    private final static String PAGE = "page";

    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction1, where "method" is the value
     * specified in <action> element : ( <action parameter="method" .../> )
     */
    public ActionForward page(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (!request.isRequestedSessionIdValid()) {
            return mapping.findForward(PAGE);
        }

        Root.deleteSessions(request, "");
        String[] atributo = {"activo"};
        Object[] valor = {true};
        ArrayList<TipoActividad> ta = Clases.TipoActividad.listarCondicion(atributo, valor);
        ArrayList<ElementoCatalogo> programas;
        programas = Clases.ElementoCatalogo.listarElementos("Programas", 1);
        ArrayList<ElementoCatalogo> dependencias;
        dependencias = Clases.ElementoCatalogo.listarElementos("Dependencias", 1);
        ArrayList<ElementoCatalogo> participantes = Clases.ElementoCatalogo.listarParticipantes();


        request.setAttribute("validadores", dependencias);
        request.setAttribute("programas", programas);
        request.setAttribute("tiposdeactividad", ta);
        request.setAttribute("participantes", participantes);

        return mapping.findForward(PAGE);
    }

    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction2, where "method" is the value
     * specified in <action> element : ( <action parameter="method" .../> )
     */
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (!request.isRequestedSessionIdValid()) {
            return mapping.findForward(PAGE);
        }

        BusquedaActividad ba = (BusquedaActividad) form;
        String permiso = (String) request.getSession().getAttribute("permiso");
        if (permiso == null) {
            ba.buscar(true);
        } else {
            ba.buscar(false);
        }

        String[] atributo = {"activo"};
        Object[] valor = {true};
        ArrayList<TipoActividad> ta = Clases.TipoActividad.listarCondicion(atributo, valor);
        ArrayList<ElementoCatalogo> programas;
        programas = Clases.ElementoCatalogo.listarElementos("Programas", 1);
        ArrayList<ElementoCatalogo> dependencias;
        dependencias = Clases.ElementoCatalogo.listarElementos("Dependencias", 1);
        ArrayList<ElementoCatalogo> participantes = Clases.ElementoCatalogo.listarParticipantes();

        String[] grafica = ba.getGrafica();

        ArrayList<Actividad> acts = ba.buscarPagina(ba, 0);

        request.getSession().setAttribute("actividades", acts);
        request.getSession().setAttribute("validadores", dependencias);
        request.getSession().setAttribute("programas", programas);
        request.getSession().setAttribute("tiposdeactividad", ta);
        request.getSession().setAttribute("participantes", participantes);
        request.getSession().setAttribute("graficaNombres", grafica[0]);
        request.getSession().setAttribute("graficaCantidad", grafica[1]);
        return mapping.findForward(PAGE);
    }

    public ActionForward aPagina(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (!request.isRequestedSessionIdValid()) {
            return mapping.findForward(PAGE);
        }

        BusquedaActividad ba = (BusquedaActividad) form;

        String[] grafica = ba.getGrafica();

        ArrayList<Actividad> acts = ba.obtenerPagina();
        ba.setBotonesPaginas();
        System.out.println("Actividades para mostrar (nros):");
        /*for (int i = 1; i <= acts.size(); i++) {
         System.out.println(i + ".- " + acts.get(i).getNombreTipoActividad());
         }*/

        request.getSession().setAttribute("actividades", acts);
        request.getSession().setAttribute("graficaNombres", grafica[0]);
        request.getSession().setAttribute("graficaCantidad", grafica[1]);


        return mapping.findForward(PAGE);
    }
}
