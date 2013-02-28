/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DBMS.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SisCon
 */
public class TipoActividad extends Root {

    private int id;
    private String nombreTipo;
    private String tipoPR;
    private int nroCampos;
    private String descripcion;
    private ArrayList<String> permiso;
    private String programa;
    private String validador;
    private String producto;
    private ArrayList<Campo> campos;
    private static final String[] ATRIBUTOS = {
        "id_tipo_actividad", //0
        "nombre_tipo_actividad", //1
        "tipo_p_r", //2
        "nro_campos", //3
        "descripcion", //4
        "programa", //5
        "validador", //6
        "producto" //7  
    };
    private static final String[] tiposCampos = {
        "texto", //STRING
        "numero", //INT
        "fecha", //DATE
        "checkbox"//CHECKBOX
    };

    public static String[] getTiposCampos() {
        return tiposCampos;
    }

    public TipoActividad() {
    }

    public TipoActividad(String nombreTipo, int nroCampos, String descripcion) {
        this.nombreTipo = nombreTipo;
        this.nroCampos = nroCampos;
        this.descripcion = descripcion;

    }

    public int getId() {
        return id;
    }

    public int getIdTipoActividad() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Campo> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList<Campo> campos) {
        this.campos = campos;
    }

    public Campo getCampo(int indice) {

        return campos.get(indice);
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public int getNroCampos() {
        return nroCampos;
    }

    public void setNroCampos(int nroCampos) {
        this.nroCampos = nroCampos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoPR() {
        return tipoPR;
    }

    public void setTipoPR(String tipoPR) {
        this.tipoPR = tipoPR;
    }

    public ArrayList<String> getPermiso() {
        return permiso;
    }

    public void setPermiso(ArrayList<String> permiso) {
        this.permiso = permiso;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    private boolean checkPermiso(String rol) {
        Iterator it = permiso.iterator();
        while (it.hasNext()) {
            String estePermiso = (String) it.next();
            if (rol.equals(estePermiso)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkValidador(String validador) {
        return this.validador.equals(validador);
    }

    private void setPermisos() {
        Entity ePermisos = new Entity(0, 18);
        int i = 0;
        String[] seleccionar = {
            "permiso.nombre"
        };
        String[] tablas = {
            "tipo_actividad",
            "tipo_permiso",
            "permiso"
        };
        String[] columnas = {
            "tipo_actividad.id_tipo_actividad"
        };
        Object[] valores = {
            id
        };
        ResultSet rs = ePermisos.naturalJoins(seleccionar, tablas, columnas, valores);
        if (rs != null) {
            try {
                permiso = new ArrayList<>(0);
                while (rs.next()) {
                    String estePermiso = (String) rs.getString("nombre");
                    permiso.add(estePermiso);
                    System.out.println("Recuperando el permiso: "+estePermiso);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TipoActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setId() {
        Entity eId = new Entity(0, 1);

        String[] seleccionar = {ATRIBUTOS[0]};

        String[] columnas = {
            ATRIBUTOS[1]
        };

        Object[] valores = {
            nombreTipo
        };

        ResultSet rs = eId.proyectar(seleccionar, columnas, valores);
        if (rs != null) {
            try {
                if (rs.next()) {
                    this.id = rs.getInt(ATRIBUTOS[0]);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TipoActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "TipoActividad{" + "nombreTipo=" + nombreTipo + ", nroCampos=" + nroCampos + ", descripcion=" + descripcion + " }";
    }

    public boolean esTipoActividad() {
        try {
            Entity e = new Entity(0, 1);

            String[] atrib = {ATRIBUTOS[1]};
            String[] valor = {nombreTipo};
            ResultSet rs = e.seleccionar(atrib, valor);
            if (rs != null) {
                while (rs.next()) {
                    if (rs.getString(ATRIBUTOS[1]).equals(nombreTipo)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static TipoActividad getTipoActividad(int id) {

        Entity e = new Entity(0, 1);

        String[] atrib = {"id_tipo_actividad"};
        Integer[] valor = {id};
        ResultSet rs = e.seleccionar(atrib, valor);
        TipoActividad t = new TipoActividad();
        if (rs != null) {
            try {
                rs.next();
                t.setId(id);
                t.setNombreTipo(rs.getString("nombre_tipo_actividad"));
                t.setTipoPR(rs.getString("tipo_p_r"));
                t.setNroCampos(rs.getInt("nro_campos"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setPrograma(rs.getString("programa"));
                t.setValidador(rs.getString("validador"));
                t.setProducto(rs.getString("producto"));
                t.setPermisos();

                return t;
            } catch (SQLException ex) {
                Logger.getLogger(TipoActividad.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public boolean agregarTipoActividad() {

        Entity e = new Entity(1, 1);
        boolean resp;

        Object[] valores = {
            nombreTipo,
            tipoPR,
            nroCampos,
            descripcion,
            programa,
            validador,
            producto
        };
        if (esTipoActividad()) {
            this.mensaje = "No se puede registrar el tipo de actividad."
                    + "Ya existe un tipo de actividad de nombre '"
                    + this.nombreTipo + "'.Por favor elija otro nombre.";
            return false;
        } else {
            System.out.println("No ha sido insertada previamente");
            String[] aInsertar = {
                ATRIBUTOS[1],
                ATRIBUTOS[2],
                ATRIBUTOS[3],
                ATRIBUTOS[4],
                ATRIBUTOS[5],
                ATRIBUTOS[6],
                ATRIBUTOS[7]
            };

            Campo c = new Campo();
            c.setNombre(this.producto);
            c.setIdTipoActividad(id);
            c.setObligatorio(true);
            c.setTipo(Campo.getTIPOS()[3]);
            this.campos.add(c);

            if (resp = e.insertar2(aInsertar, valores)) {
                this.setId();
                System.out.println("Ya inserte el tipo de Actividad con ID " + id);
                Iterator it = campos.iterator();
                System.out.println("Creo el iterador");
                while (it.hasNext() && resp) {
                    System.out.println("Voy iterando");
                    Campo cAgregar = (Campo) it.next();
                    resp &= cAgregar.agregarCampo(id);
                    if (!resp) {
                        System.out.print("No se pudo registrar el campo " + cAgregar.getNombre());
                        this.eliminarTipoActividad();
                    }
                }
                Entity ePermisos = new Entity(1, 18);
                Object[] valoresPermisos = {
                    id,
                    0
                };
                Iterator itPermisos = permiso.iterator();
                while (itPermisos.hasNext()) {
                    String estePermiso = (String) itPermisos.next();
                    switch (estePermiso) {

                        case "estudiante":
                            valoresPermisos[1] = 1;
                            break;
                        case "empleado":
                            valoresPermisos[1] = 2;
                            break;
                        case "obrero":
                            valoresPermisos[1] = 3;
                            break;
                        case "profesor":
                            valoresPermisos[1] = 4;
                            break;
                    }
                    ePermisos.insertar(valoresPermisos);
                }
            } else if (!resp) {
                this.eliminarTipoActividad();
            }
        }

        return resp;
    }

    public boolean eliminarTipoActividad() {
        Entity eTipoActividad = new Entity(5, 1);

        if (eTipoActividad.borrar(ATRIBUTOS[0], this.id)) {

            return true;
        } else {

            return false;
        }

    }

    /**
     *
     * @return
     */
    public static ArrayList<TipoActividad> listarTiposActividad() { //DEBE RECIBIR UN TIPO DE USUARIO
        Entity eListar = new Entity(0, 1);
        ResultSet rs = eListar.listar();
        ArrayList<TipoActividad> tipos = new ArrayList<>(0);

        if (rs != null) {
            try {
                while (rs.next()) {
                    TipoActividad t = new TipoActividad();
                    t.setId(rs.getInt(ATRIBUTOS[0]));
                    t.setNombreTipo(rs.getString(ATRIBUTOS[1]));
                    t.setTipoPR(rs.getString(ATRIBUTOS[2]));
                    t.setNroCampos(rs.getInt(ATRIBUTOS[3]));
                    t.setDescripcion(rs.getString(ATRIBUTOS[4]));
                    t.setPrograma(rs.getString(ATRIBUTOS[5]));
                    t.setValidador(rs.getString(ATRIBUTOS[6]));
                    t.setProducto(rs.getString(ATRIBUTOS[7]));
                    t.setPermisos();
                    tipos.add(t);
                }
            } catch (SQLException ex) {
                Logger.getLogger(TipoActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tipos;
    }

    /**
     * Lista los tipos de actividades que puede realizar el usuario.
     *
     * @param usuario El usuario del cual se quieren listar las posibilidades de
     * tipos de actividad.
     * @return Lista de los tipos de actividad que puede realizar el usuario
     * dado.
     */
    public static ArrayList<TipoActividad> listarTiposActividad(Usuario u) {
        Entity eListar = new Entity(0, 1);
        ArrayList<TipoActividad> tiposAux = listarTiposActividad();
        ArrayList<TipoActividad> tipos = new ArrayList<>(0);
        Iterator it = tiposAux.iterator();

        while (it.hasNext()) {
            TipoActividad t = (TipoActividad) it.next();
            if (t.checkPermiso(u.getRol())) {
                tipos.add(t);
            }
        }
        return tipos;
    }

    public static ArrayList<TipoActividad> listarTiposActividad(String validador) {
        Entity eListar = new Entity(0, 1);
        ArrayList<TipoActividad> tiposAux = listarTiposActividad();
        ArrayList<TipoActividad> tipos = new ArrayList<>(0);
        Iterator it = tiposAux.iterator();

        while (it.hasNext()) {
            TipoActividad t = (TipoActividad) it.next();
            if (t.checkValidador(validador)) {
                tipos.add(t);
            }
        }
        return tipos;
    }

    public boolean modificarTipoActividad(String viejoNombre) {
        boolean respuesta = true;
        Entity e = new Entity(2, 1);

        if (respuesta &= this.esTipoActividad()) {

            String[] columnaCondicion = {TipoActividad.ATRIBUTOS[1]};
            String[] condicion = {viejoNombre};
            Integer numeroDeCampos = new Integer(this.nroCampos);
            Object[] modificaciones = {this.nombreTipo, numeroDeCampos, this.descripcion, this.permiso};

            if (respuesta &= e.modificar(columnaCondicion, condicion, TipoActividad.ATRIBUTOS, modificaciones)) {
                this.setMensaje("Cambio realizado con exito");
                return respuesta;
            } else {
                this.setMensaje("El cambio no se pudo realizar");
                return respuesta;
            }

        } else {
            this.setMensaje("No existe ese tipo de Actividad");
            return respuesta;
        }
    }

    public static void main(String[] args) {
        //TipoActividad t = new TipoActividad("pasantia", 3, "pasantia");
        //t.agregarTipoActividad();

        TipoActividad t = new TipoActividad("prueba1", 2, "pasantia");
        t.setTipoPR("p");
        ArrayList<Campo> cs = new ArrayList<>();

        Campo c0 = new Campo("Nombre", "texto", 16, true);
        Campo c1 = new Campo("Apellido", "texto", 16, false);
        cs.add(c0);
        cs.add(c1);
        t.setCampos(cs);

        if (t.agregarTipoActividad()) {
            System.out.println("\n\n\nExito");
        } else {
            System.out.println("\n\n\nFracaso");
        }
//        t.eliminarTipoActividad();


    }
}
