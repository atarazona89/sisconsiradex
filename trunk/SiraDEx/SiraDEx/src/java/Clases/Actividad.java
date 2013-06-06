/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DBMS.Entity;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author SisCon
 */
public class Actividad extends Root {

    private int idActividad;
    private int idTipoActividad;
    private String nombreTipoActividad;
    private String validacion;
    private String creador; //usbid
    private String fechaCreacion;
    private String modificador;
    private String fechaModif;
    private String descripcion;
    private String validador;
    private ArrayList<String> participantes = new ArrayList<>(0);
    private ArrayList<CampoValor> camposValores;
    private ArrayList<Archivo> archivos = new ArrayList<>(0);
    private int idArchivo;
    private static String[] ATRIBUTOS = {
        "id_actividad", //0
        "id_tipo_actividad", //1
        "nombre_tipo_actividad", //2
        "validacion", //3
        "creador", //4
        "fecha_creacion",//5
        "modificador", //6
        "fecha_modif", //7
        "descripcion", //8
        "validador" //9
    };

    public Actividad() {
    }

    public Actividad(int idActividad, String validacion,
            String evaluacion) {
        this.idActividad = idActividad;
        this.validacion = validacion;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getNombreTipoActividad() {
        return nombreTipoActividad;
    }

    public void setNombreTipoActividad(String nombreTipoActividad) {
        this.nombreTipoActividad = nombreTipoActividad;
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public ArrayList<CampoValor> getCamposValores() {
        return camposValores;
    }

    public void setCamposValores(ArrayList<CampoValor> camposValores) {
        this.camposValores = camposValores;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fecha_creacion) {
        this.fechaCreacion = fecha_creacion;
    }

    public String getModificador() {
        return modificador;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public String getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(String fecha_modif) {
        this.fechaModif = fecha_modif;
    }

    public ArrayList getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList participantes) {
        this.participantes = participantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }

    public int getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }

    public static class Archivo implements Serializable {

        private FormFile file;
        private String nombre;
        private String tipo;

        public Archivo(FormFile file, String nombre, String tipo) {
            this.file = file;
            this.nombre = nombre;
            this.tipo = tipo;
        }

        public FormFile getFile() {
            return file;
        }

        public void setFile(FormFile file) {
            this.file = file;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
    }

    @Override
    public String toString() {
        return "Actividad\n\t{" + "idTipoActividad=" + idTipoActividad
                + "\n\t idActividad=" + idActividad + "\n\t usbid=" + creador + "}";
    }

    public boolean equals(Actividad a) {
        return idActividad == a.getIdActividad();
    }

    public boolean contenidoEn(ArrayList<Actividad> lista) {
        Iterator it = lista.iterator();
        boolean conseguido = false;

        while (it.hasNext() && !conseguido) {
            Actividad act = (Actividad) it.next();
            conseguido = equals(act);
        }
        return conseguido;
    }

    public void setParticipantes(int idAct) {


        Entity eBuscar = new Entity(5); //PARTICIPA
        String[] tablas = {
            "ACTIVIDAD",
            "USUARIO"
        };
        String cols = "p.usbid, nombres, apellidos";
        String[] joins = {"p.id_act=a.id_actividad", "p.usbid=u.usbid"};
        String cond = "id_actividad=" + idAct;
        ResultSet rs = eBuscar.seleccionarSinRepeticion(tablas, cols, "LEFT OUTER JOIN", joins, cond);
        if (rs != null) {
            try {
                while (rs.next()) {
                    String participante;

                    if (rs.getString("nombres") != null) {
                        participante = rs.getString("apellidos") + ", " + rs.getString("nombres");
                    } else {
                        participante = rs.getString("usbid").substring(1);
                    }
                    participantes.add(participante);

                }
            } catch (SQLException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String participantesToString() {
        String p = "";
        Iterator it = participantes.iterator();
        while (it.hasNext()) {
            p += (String) it.next() + "; ";
        }
        p = p.substring(0, p.length() - 2) + ".";
        return p;
    }

    public String camposValoresToString() {
        String s = "";
        Iterator it = camposValores.iterator();
        while (it.hasNext()) {
            CampoValor cv = (CampoValor) it.next();
            String tipo = cv.getCampo().getTipo();
            String valor = cv.getValor();
            if (Clases.Verificaciones.esVacio(valor)) {
                continue;
            }
            switch (tipo) {
                case "textol":
                case "producto":
                    continue;
                case "participante":
                    s += cv.getCampo().getNombre() + ": ";
                    if (!cv.getValorAux().isEmpty()) {
                        s += cv.getValor().substring(1) + ", ";
                    } else {
                        s += cv.getValor() + ", ";
                    }
                    break;
                case "checkbox":
                    if (!valor.equals("false")) {
                        s += cv.getCampo().getNombre() + ", ";
                    }
                    break;
                default:
                    s += cv.getCampo().getNombre() + ": "
                            + cv.getValor() + ", ";
                    break;
            }
        }
        int tam = s.length();
        if (tam > 1) {
            s = s.substring(0, s.length() - 2) + ".";
        }
        return s;
    }

    public String getApellidoNombreCreador() {

        Entity e = new Entity(0);//USUARIO

        String[] col = {"usbid"};
        Object[] condicion = {creador};

        ResultSet rs = e.seleccionar(col, condicion);
        if (rs != null) {
            try {
                rs.next();
                String resp = rs.getString("apellidos");
                resp += ", " + rs.getString("nombres");
                rs.close();
                return resp;
            } catch (SQLException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //teniendo el idActividad hace un set del resto de atributos de la Actividad
    public void setActividad() {
        try {
            Entity eActividad = new Entity(2);//ACTIVIDAD

            String[] tabABuscar = {
                "ACTIVIDAD",
                "TIPO_ACTIVIDAD"
            };
            String[] atrib = {"id_actividad"};
            Integer[] valor = {idActividad};

            try (ResultSet rs = eActividad.naturalJoin(ATRIBUTOS, tabABuscar, atrib, valor)) {
                if (rs != null) {
                    rs.next();
                    idTipoActividad = rs.getInt(ATRIBUTOS[1]);
                    nombreTipoActividad = rs.getString(ATRIBUTOS[2]);
                    validacion = rs.getString(ATRIBUTOS[3]);
                    creador = rs.getString(ATRIBUTOS[4]);
                    fechaCreacion = rs.getString(ATRIBUTOS[5]);
                    modificador = rs.getString(ATRIBUTOS[6]);
                    fechaModif = rs.getString(ATRIBUTOS[7]);
                    descripcion = rs.getString(ATRIBUTOS[8]);
                    camposValores = CampoValor.listarCamposValores(idActividad);

                    Iterator iter = camposValores.iterator();

                    while (iter.hasNext()) {
                        CampoValor cv = (CampoValor) iter.next();
                        String tipoCampo = cv.getCampo().getTipo();
                        String nombre = cv.getValor();
                        if (!nombre.equals("")
                                && ((tipoCampo.equals("archivo")
                                || tipoCampo.equals("producto")))) {
                            Archivo arch = new Archivo(cv.getFile(), nombre,
                                    cv.getCampo().getNombre());
                            archivos.add(arch);
                        }
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean esActividadUsuario() {
        try {
            Entity e = new Entity(2);//ACTIVIDAD

            String[] col = {ATRIBUTOS[0], ATRIBUTOS[4]};
            Object[] condicion = {idActividad, creador};

            ResultSet rs = e.seleccionar(col, condicion);
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getInt(ATRIBUTOS[0]) == idActividad
                            && rs.getString(ATRIBUTOS[4]).equals(creador)) {
                        rs.close();
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean agregar(String ip, String user) {

        if (!Verificaciones.verificar(this)) {
            return false;
        }

        Entity e = new Entity(2);//ACTIVIDAD
        e.setIp(ip);
        e.setUser(user);
        String[] columnas = {
            "id_tipo_actividad",
            "creador"
        };

        Object[] actividad = {
            idTipoActividad,
            creador
        };
        boolean resp;
        if (resp = e.insertar2(columnas, actividad)) {
            e.log();
            idActividad = e.seleccionarMaxId(ATRIBUTOS[0]);

            Iterator itValores = camposValores.iterator();

            while ((itValores.hasNext()) && resp) {
                CampoValor cv = (CampoValor) itValores.next();
                resp &= cv.agregar(idActividad);
                if (!resp) {
                    mensajeError = "Error: La Actividad '" + nombreTipoActividad
                            + "' no pudo ser resgistrada.";
                    if (!eliminarActividad("Error al agregar", "SISTEMA")){
                         mensajeError = " Error: La Actividad '" + nombreTipoActividad
                            + "' no pudo ser resgistrada satisfactoriamente, debe eliminarla"
                                 + " mediante el sistema.";
                    }
                }
            }
        }
        return resp;
    }

    public boolean eliminarActividad(String ip, String user) {
        Entity e = new Entity(2);//ACTIVIDAD
        e.setIp(ip);
        e.setUser(user);
        if (e.borrar(ATRIBUTOS[0], idActividad) && e.log()) {

            mensaje = "La Actividad '" + nombreTipoActividad + "' ha sido eliminada con éxito.";
            return true;
        }
        mensajeError = "La Actividad '" + nombreTipoActividad + "' no pudo ser eliminada.";
        return false;
    }

    public boolean modificar(ArrayList camposNM, String ip, String usuario) {

        if (!Verificaciones.verificar(this)) {
            return false;
        }
        boolean resp = true;
        Iterator it = camposNM.iterator();

        for (int i = 0; it.hasNext() && resp; i++) {
            CampoValor campoNM = (CampoValor) it.next();
            System.out.println("antes modif " + campoNM.getCampo().getNombre() + " " + resp);
            resp &= camposValores.get(i).modificar(campoNM, idActividad, ip, usuario);
            System.out.println("luego modif " + campoNM.getCampo().getNombre() + " " + resp);
        }

        Entity eActividad = new Entity(2);//ACTIVIDAD
        fechaModif = Clases.Log.getFechaHora();
        String[] condColumn = {
            ATRIBUTOS[0]
        };
        Object[] condValores = {
            idActividad
        };
        String[] colModif = {
            ATRIBUTOS[3],
            ATRIBUTOS[6],
            ATRIBUTOS[7]
        };
        Object[] modValor = {
            "En espera",
            modificador,
            fechaModif
        };
        eActividad.setIp(ip);
        eActividad.setUser(usuario);
        eActividad.log();
        resp &= eActividad.modificar(condColumn, condValores, colModif, modValor);

        if (!resp) {
            mensajeError = "Error: No se pudo modificar la Actividad.";
        }
        return resp;
    }

    private String obtenerCorreos() {

        String correos = "";

        Entity eBuscar = new Entity(5); //PARTICIPA
        String[] tablas = {
            "ACTIVIDAD",
            "USUARIO"
        };
        String cols = "p.usbid, email";
        String[] joins = {"p.id_act=a.id_actividad", "p.usbid=u.usbid"};
        String cond = "id_actividad=" + idActividad;
        ResultSet rs = eBuscar.seleccionarSinRepeticion(tablas, cols, "LEFT OUTER JOIN", joins, cond);
        if (rs != null) {
            try {
                while (rs.next()) {
                    String usbid = rs.getString("usbid");

                    if (!usbid.startsWith("$")) {
                        correos += usbid + "@usb.ve ";
                        String email = rs.getString("email");
                        if (email != null || !email.equals("")) {
                            correos += email + " ";
                        }
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return correos;

    }

    public boolean enviarCorreo(int accion) {

        String titulo = "Notificación del SiraDEx (Sistema de Registro de Actividades de"
                + " Extensión de la Universidad Simón Bolívar)";
        String contenido = "";
        String correos = obtenerCorreos();
        if (accion == 0) { //actividad registrada o en espera
            contenido = "Ha sido registrada la Actividad '" + nombreTipoActividad + "' "
                    + "en la cual usted fue agregado como partipante, esta pasará a ser validada "
                    + "por el Decanato de Extensión.\nSerá notificado del resultado de la "
                    + "validación por esta misma vía.";
        }
        if (accion == 1) { //actividad modificada
            contenido = "Ha sido modificada la Actividad '" + nombreTipoActividad + "' "
                    + "de la cual usted es participante, esta pasará a ser validada por el"
                    + " Decanato de Extensión.\nSerá notificado del resultado de la validación"
                    + " por esta misma vía.";
        }
        if (accion == 2) { //actividad rechazada
            String motivo = descripcion.replace("\"","'").replace("\r", "");
            contenido = "Ha sido rechazada la validación de la Actividad '" + nombreTipoActividad 
                    + "' de la cual usted es participante, por el motivo siguiente:\n" + motivo
                    + "\nModifique la Actividad para corregir el problema y esperar "
                    + "nuevamente por la validación.";
        }
        if (accion == 3) { //actividad validada
            contenido = "Ha sido validada la Actividad '" + nombreTipoActividad + "' de la cual "
                    + "usted es participante.";
        }
        String cmd = "echo \"" + contenido + "\" | mail -s " + " \"" + titulo + "\" " + correos;

        String[] comando = {
            "/bin/sh",
            "-c",
            cmd
        };

        System.out.println(cmd);
        try {
            Process p;
            p = Runtime.getRuntime().exec(comando);
            try {
                if (p.waitFor() != 0) {
                    mensajeError = " Error: No se pudo enviar la notificación por correo. ";
                    return false;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                mensajeError = " Error: No se pudo enviar la notificación por correo. " + ex;
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            mensajeError = " Error: No se pudo enviar la notificación por correo. " + ex;
            return false;
        }
        mensaje += " Se le ha enviado una notificación por correo a los usuarios participantes de la Actividad.";
        return true;

    }

    public boolean validar(boolean valida, String ip, String user) {

        Entity eValidar = new Entity(2); //ACTIVIDAD
        String[] condColumn = {
            ATRIBUTOS[0]
        };
        Object[] condValores = {
            idActividad
        };
        String[] colModificar = {
            ATRIBUTOS[3]
        };
        String val;
        if (valida) {
            val = "Validada";
        } else {
            val = "Rechazada. Motivo: " + descripcion.replace("\"","'");
        }
        Object[] modificaciones = {
            val
        };

        boolean b = eValidar.modificar(condColumn, condValores, colModificar, modificaciones);
        eValidar.setIp(ip);
        eValidar.setUser(user);
        eValidar.log();
        return b;

    }

    /**
     * Funcion que toma los valores de un ResultSet y crea una lista de
     * Actividades a partir de ella.
     *
     * @param rs Resultado de una busqueda en la Base de Datos y que contenga la
     * informacion de una o varias Actividades.
     * @return Lista de las Actividades encontradas por la busqueda.
     * @throws SQLException
     */
    public static ArrayList<Actividad> listar(ResultSet rs) {
        try {
            ArrayList<Actividad> acts = new ArrayList<>(0);
            if (rs != null) {

                while (rs.next()) {
                    Actividad a = new Actividad();
                    a.setIdActividad(rs.getInt(ATRIBUTOS[0]));
                    a.setIdTipoActividad(rs.getInt(ATRIBUTOS[1]));
                    a.setNombreTipoActividad(rs.getString(ATRIBUTOS[2]));
                    a.setValidacion(rs.getString(ATRIBUTOS[3]));
                    a.setCreador(rs.getString(ATRIBUTOS[4]));
                    a.setFechaCreacion(rs.getString(ATRIBUTOS[5]));
                    a.setModificador(rs.getString(ATRIBUTOS[6]));
                    a.setFechaModif(rs.getString(ATRIBUTOS[7]));
                    a.setDescripcion(rs.getString(ATRIBUTOS[8]));
                    a.setValidador(rs.getString(ATRIBUTOS[9]));
                    a.setCamposValores(CampoValor.listarCamposValores(a.idActividad));
                    a.setParticipantes(a.idActividad);

                    Iterator iter = a.getCamposValores().iterator();
                    ArrayList<Archivo> archs = new ArrayList<>(0);
                    while (iter.hasNext()) {
                        CampoValor cv = (CampoValor) iter.next();
                        String tipoCampo = cv.getCampo().getTipo();
                        String nombre = cv.getValor();
                        if (!nombre.equals("")
                                && ((tipoCampo.equals("archivo")
                                || tipoCampo.equals("producto")))) {
                            Archivo arch = new Archivo(cv.getFile(), nombre,
                                    cv.getCampo().getNombre());
                            archs.add(arch);
                        }
                    }
                    a.setArchivos(archs);

                    acts.add(a);
                }
            } else {
                acts = null;
            }
            return acts;






        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ArrayList<Actividad> listarActividades() {

        Entity eActividad = new Entity(21);//TIPO_ACT__ACT
        ResultSet rs = eActividad.listar();
        return listar(rs);
    }

    /**
     * Lista todas las actividades de un tipo especificado en el atributo de la
     * actividad que lo llama.
     *
     * @return Lista con todas las actividades del mismo tipo que la actividad.
     */
    public ArrayList<Actividad> listarActividadesDeTipo() {

        Entity eActividad = new Entity(21);//TIPO_ACT__ACT
        String[] columna = {ATRIBUTOS[1]};
        Integer[] condicion = {idTipoActividad};

        ResultSet rs = eActividad.seleccionar(columna, condicion);
        return listar(rs);
    }

    /**
     * Lista las actividades que tiene el usuario.
     *
     * @return Lista con las actividades realizadas por un usuario. Tambien en
     * las que participa.
     */
    public static ArrayList<Actividad> listarActividadesDeUsuario(String usbid) {

        Entity eBuscar = new Entity(5); //PARTICIPA
        String[] tablas = {
            "ACTIVIDAD",
            "TIPO_ACTIVIDAD"
        };
        String cols = "*";
        String[] joins = {"p.id_act=a.id_actividad", "t.id_tipo_actividad=a.id_tipo_actividad"};
        String conds = "p.usbid=" + "'" + usbid + "'";
        ResultSet rs = eBuscar.seleccionarSinRepeticion(tablas, cols, "JOIN", joins, conds);
        return listar(rs);
    }

    /**
     * Lista las actividades según su validador.
     *
     * @param validador Nombre de la dependencia que desea listarCampos las
     * actividades a por validar.
     * @return Lista con las actividades realacionadas con el validador,
     */
    public static ArrayList<Actividad> listarActividadesDeValidador(String validador) {
        Entity eActividad = new Entity(21);//TIPO_ACT__ACT
        ResultSet rs;
        if (validador.equals("WM")) {
            String[] columna = {ATRIBUTOS[3]}; //validacion, validador
            String[] condicion = {"En espera"};
            rs = eActividad.seleccionar(columna, condicion);
        } else {
            String[] columna = {ATRIBUTOS[3], ATRIBUTOS[9]}; //validacion, validador
            String[] condicion = {"En espera", validador};
            rs = eActividad.seleccionar(columna, condicion);
        }

        return listar(rs);
    }

    /**
     * Lista las actividades según su clasificación para el DEx.
     *
     * @param tipo P o R. En caso de ser distinto arrojará null.
     * @return Lista con las Actividades que sean del tipo indicado.
     */
    public static ArrayList<Actividad> listarActividadesPR(String tipo) {
        Entity eBuscar;
        ResultSet rs;
        switch (tipo) {
            case "P":
            case "p":
                eBuscar = new Entity(19);//TIPO_P
                break;
            case "R":
            case "r":
                eBuscar = new Entity(20);//TIPO_R
                break;
            default:
                return null;
        }
        rs = eBuscar.listar();

        return listar(rs);
    }

    /**
     * Lista las actividades de un programa dado.
     *
     * @param programa
     * @return Lista con las actividades que pertenezcan al programa dado.
     */
    public static ArrayList<Actividad> listarActividadesPrograma(String programa) {

        Entity eListar = new Entity(21);//TIPO_ACT__ACT
        String[] columnas = {
            "programa"
        };
        Object[] condiciones = {
            programa
        };

        ResultSet rs = eListar.seleccionar(columnas, condiciones);

        return listar(rs);
    }

    /**
     * Lista las actividades que ha creado un usuario.
     *
     * @param creador El usuario a buscar.
     * @return Lista con todas las actividades que ha creado el usaurio dado.
     */
    public static ArrayList<Actividad> listarActividadesDeCreador(String creador) {

        Entity eSeleccionar = new Entity(2);//ACTIVIDAD
        ResultSet rs;
        String[] columnas = {
            ATRIBUTOS[4]
        };

        Object[] condicion = {
            creador
        };
        rs = eSeleccionar.seleccionar(columnas, condicion);

        return listar(rs);
    }

    public static void main(String args[]) {
        /*
         public static void imprimirLista(ArrayList<String> lista) {
         Iterator it = lista.iterator();
         while (it.hasNext()) {
         System.out.println(it.next());

         }
         }
         
         Campo c = new Campo("Blah", "Entero", true);
         String prueba = "1989";
         String prueba2 = "Adios1987425";


         Actividad a = new Actividad();
         a.setCreador("alejandro");
         a.setIdTipoActividad(66);
         ArrayList<Actividad> lista = null;

         lista = listarActividades();


         System.out.println("\n\n\nListando todas las actividades");
         imprimirLista(lista);
         lista = a.listarActividadesDeTipo();



         System.out.println("\n\n\nListando todas las actividades del tipo");
         imprimirLista(lista);
         lista = a.listarActividadesDeUsuario();

         System.out.println("\n\n\nListando todas las actividades del usuario");
         imprimirLista(lista);*/

        Actividad a = new Actividad();
        System.out.println("fecha hora " + Clases.Log.getFechaHora());
    }
}
