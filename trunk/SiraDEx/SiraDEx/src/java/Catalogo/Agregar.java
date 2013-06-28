/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Catalogo;

import Clases.CampoCatalogo;
import Clases.Catalogo;
import Clases.Root;
import Clases.Usuario;
import Clases.Verificaciones;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author SisCon
 */
public class Agregar extends DispatchAction {

    /*
     * forward name="success" path=""
     */
    private static final String SUCCESS = "success";
    private static final String SUCCESSFULL = "successfull";
    private static final String PAGE = "page";

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
    public ActionForward page(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Usuario u = (Usuario) request.getSession().getAttribute("user");
        if (u == null) {
            return mapping.findForward(PAGE);
        }
        Root.deleteSessions(request, "");
        return mapping.findForward(PAGE);
    }

    public ActionForward save(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Usuario u = (Usuario) request.getSession().getAttribute("user");
        if (u == null) {
            return mapping.findForward(PAGE);
        }

        Catalogo cat = (Catalogo) form;

        if (!Verificaciones.verificarCamposFijos(cat)) {
            return mapping.findForward(PAGE);
        }

        if (String.valueOf(cat.getNroCampos()).equals("0")) {
            cat.setMensaje("Error: El campo 'Número de campos' debe contener al "
                    + "menos 1 como valor.");
            return mapping.findForward(PAGE);
        }

        if (cat.esCatalogo()) {
            cat.setMensaje("Error: Ya existe un Catálogo con el Nombre '"
                    + cat.getNombre() + "'. Por favor intente con otro nombre.");
            return mapping.findForward(PAGE);
        }

        int numeroCampos = cat.getNroCampos();
        ArrayList<CampoCatalogo> campos = new ArrayList<>();

        if (cat.isParticipantes()) {
            CampoCatalogo c = new CampoCatalogo();
            c.setNombre("USB-ID");
            c.setTipo("usbid");
            campos.add(c);
        }
        for (int i = 0; i < numeroCampos; i++) {
            CampoCatalogo c = new CampoCatalogo();
            campos.add(c);
        }

        cat.setCampos(campos);

        return mapping.findForward(SUCCESS);

    }

    public ActionForward save2(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Usuario u = (Usuario) request.getSession().getAttribute("user");
        if (u == null) {
            return mapping.findForward(PAGE);
        }

        Catalogo cat = (Catalogo) form;

        String usuario = u.getUsername();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }


        if (!Verificaciones.verificarCamposVariables(cat)) {
            return mapping.findForward(SUCCESS);
        }

        if (cat.agregar(ip, usuario)) {
            request.getSession().setAttribute("mensajeCat", cat.getMensaje());
            return mapping.findForward(SUCCESSFULL);
        }

        return mapping.findForward(SUCCESS);
    }
}
