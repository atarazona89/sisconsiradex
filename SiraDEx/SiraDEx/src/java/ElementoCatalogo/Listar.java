/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ElementoCatalogo;

import Clases.ElementoCatalogo;
import Clases.Usuario;
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
public class Listar extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

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

        request.getSession().setAttribute("mensajeCat", null);
        Usuario u = (Usuario) request.getSession().getAttribute("user");
        if (u == null) {
            return mapping.findForward(SUCCESS);
        }
        
        Clases.Root.deleteSessions(request, "elementoCatalogoForm","mensajeElem");
        ElementoCatalogo e = (ElementoCatalogo) form;
        e.setMensaje(null);
        int idCat = e.getIdCatalogo();
        e.setNombreCatalogo(Clases.Catalogo.getNombre(idCat));
        ArrayList<ElementoCatalogo> elemsc = Clases.ElementoCatalogo.listarElementosId(idCat);

        if (elemsc.isEmpty()) {
            elemsc = null;
        } else {
            request.setAttribute("campos", elemsc.get(0).getCamposValores());
        }

        request.setAttribute("elementos", elemsc);

        return mapping.findForward(SUCCESS);
    }
}
