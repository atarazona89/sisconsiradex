/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalogo;

import Clases.Catalogo;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author alejandro
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
        Catalogo t = (Catalogo) form;

        if (t.eliminar(t.getIdCatalogo())) {
            t.setMensaje("El catlogo ha sido eliminado.");
            ArrayList cat = Clases.Catalogo.listar();
            request.setAttribute("catalogos", cat);
            return mapping.findForward(SUCCESS);
        } else {
            t.setMensaje("Error: El catálogo no pudo ser eliminado.");
            ArrayList cat = Clases.Catalogo.listar();
            request.setAttribute("catalogos", cat);
            return mapping.findForward(FAILURE);
        }
    }
}
