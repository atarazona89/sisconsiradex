/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiposActividad;

import Clases.TipoActividad;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SisCon
 */
public class Eliminar extends org.apache.struts.action.Action {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TipoActividad t = (TipoActividad) form;

        if (t.eliminarTipoActividad()) {
            ArrayList ta = Clases.TipoActividad.listarTiposActividad();
            request.setAttribute("tipos", ta);
            t.setMensaje("El tipo de actividad ha sido eliminado.");
            return mapping.findForward(SUCCESS);
        } else {
            ArrayList ta = Clases.TipoActividad.listarTiposActividad();
            request.setAttribute("tipos", ta);
            t.setMensaje("El tipo de actividad que desea eliminar no existe.");
            return mapping.findForward(FAILURE);
        }
    }
}