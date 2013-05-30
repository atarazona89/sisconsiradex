/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DBMS.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SisCon
 */
public class BusquedaActividad extends Root {

    private String nombreTipo;
    private String tipoPR;
    private String programa;
    private String validador;
    private String participante; //usbid
    private String fechaInic;
    private String fechaFin;
    private int mostrarPorPagina = 10;
    private int totalPaginas;
    private ArrayList<ArrayList<Actividad>> libro;
    private int pagina;

    public BusquedaActividad() {
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getTipoPR() {
        return tipoPR;
    }

    public void setTipoPR(String tipoPR) {
        this.tipoPR = tipoPR;
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

    public String getParticipante() {
        return participante;
    }

    public void setParticipante(String participante) {
        this.participante = participante;
    }

    public String getFechaInic() {
        return fechaInic;
    }

    public void setFechaInic(String fechaInic) {
        this.fechaInic = fechaInic;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getMostrarPorPagina() {
        return mostrarPorPagina;
    }

    public void setMostrarPorPagina(int mostrarPorPagina) {
        this.mostrarPorPagina = mostrarPorPagina;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public ArrayList<ArrayList<Actividad>> getLibro() {
        return libro;
    }

    public void setLibro(ArrayList<ArrayList<Actividad>> libro) {
        this.libro = libro;
    }

    public int getPagina() {
        return pagina;
    }

    public ArrayList<Actividad> getPagina(int i) {
        return libro.get(i - 1);
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    /**
     * Metodo para buscar por cada uno de los criterios dados.
     *
     * @return Las páginas con las listas de Actividades por página segun la
     * busqueda establecida.
     */
    public void buscar(boolean validada) {
        libro = new ArrayList<>(0);
        totalPaginas = 0;

        boolean hayColumnas = false, hayParticipantes = false, hayRango = false;

        Entity eBuscar = new Entity(0, 21);
        ArrayList<String> auxColumnas = new ArrayList<>(0);
        ArrayList<Object> auxCondiciones = new ArrayList<>(0);

        if (validada) {
            auxColumnas.add("validacion");
            auxCondiciones.add("Validada");
        }

        if (this.nombreTipo != null && !this.nombreTipo.equals("")) {
            auxColumnas.add("nombre_tipo_actividad");
            auxCondiciones.add(nombreTipo);
        }
        if (this.tipoPR != null && !this.tipoPR.equals("")) {
            auxColumnas.add("tipo_p_r");
            auxCondiciones.add(tipoPR);
        }
        if (this.programa != null && !this.programa.equals("")) {
            auxColumnas.add("programa");
            auxCondiciones.add(programa);
        }
        if (this.validador != null && !this.validador.equals("")) {
            auxColumnas.add("validador");
            auxCondiciones.add(validador);
        }

        int tam = auxColumnas.size();
        String[] columnas = new String[tam];
        for (int i = 0; i < tam; i++) {
            columnas[i] = auxColumnas.get(i);
        }
        tam = auxCondiciones.size();
        Object[] condiciones = new Object[tam];

        for (int i = 0; i < tam; i++) {
            condiciones[i] = auxCondiciones.get(i);
        }

        ArrayList<Actividad> cjtoAux = new ArrayList<>(0);     //Resultado de la busqueda cochina y gigante//
        ResultSet rs = null;
        if (columnas.length > 0) {
            rs = eBuscar.seleccionar(columnas, condiciones);
            cjtoAux = Actividad.listar(rs);
            hayColumnas = true;
        }
        
        String[] columna = {
            "tipo_campo"
        };
        Object[] condicion = {
            "fecha"
        };

        Entity eRango = new Entity(0, 24);
        ResultSet rsRango = eRango.seleccionar(columna, condicion);//ESTA TIRANDO UN ERROR EN EL OUTPUT
        ArrayList<Integer> listaIds = new ArrayList<>(0);
        if (this.fechaInic != null && !this.fechaInic.equals("")) {
            if (this.fechaFin != null && !this.fechaFin.equals("")) {
                try {
                    while (rsRango.next()) {
                        String fecha = rsRango.getString("valor");
                        try {
                            if (antesDe(fechaInic, fecha) && despuesDe(fechaFin, fecha)) {
                                listaIds.add(rsRango.getInt("id_actividad"));
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        hayRango = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    while (rsRango.next()) {
                        String fecha = rsRango.getString("valor");
                        try {
                            if (antesDe(fechaInic, fecha)) {
                                listaIds.add(rsRango.getInt("id_actividad"));
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        hayRango = true;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (this.fechaFin != null && !this.fechaFin.equals("")) {
            try {
                while (rsRango.next()) {
                    String fecha = rsRango.getString("valor");
                    try {
                        if (despuesDe(fechaFin, fecha)) {
                            listaIds.add(rsRango.getInt("id_actividad"));
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(BusquedaActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
            hayRango = true;
        } else {
            hayRango = false;
        }
        
        System.out.println("#################################################################################################\n"
                + "###################################################################################################\n"
                + "Actividades obtenidas en la busqueda:");
        for (int i = 0; i < cjtoAux.size(); i++) {
            System.out.println(cjtoAux.get(i).toString());
        }
        System.out.println("#################################################################################################\n"
                + "###################################################################################################");

        ArrayList<Actividad> listaRango = new ArrayList<>(0);
        if (hayRango) {
            Iterator it = listaIds.iterator();
            while (it.hasNext()) {
                Integer id = (Integer) it.next();
                Actividad act = new Actividad();
                act.setIdActividad(id.intValue());
                act.setActividad();
                act.setParticipantes(id.intValue());
                listaRango.add(act);
            }
        } else {
            listaRango.addAll(Actividad.listar(rsRango));
        }

        ArrayList<Actividad> listaParticipantes = new ArrayList<>(0);

        if (this.participante != null && !this.participante.equals("")) {
            hayParticipantes = true;
            listaParticipantes.addAll(Actividad.listarActividadesDeUsuario(participante));    //Resultado de la busqueda de participantes//
        }

        ArrayList<ArrayList<Actividad>> listas = new ArrayList<>(0);
        //De aqui pa'lante, el fume fue tan grande que ni yo mismo lo entiendo. Alejandro
        //Lo que trato de hacer es revisar las distintas formas en que pueden quedar las
        //listas para poder discernir cual(es) lista(s) es(son) vacia(s).
        if (hayParticipantes) {
            listas.add(listaParticipantes);
        }
        if (hayColumnas) {
            listas.add(cjtoAux);
        }
        if (hayRango) {
            listas.add(listaRango);
        }
        ArrayList<Actividad> listaInterceptada = new ArrayList<>(0);

        if (listas.isEmpty()) {
            if (hayParticipantes || hayColumnas || hayRango) {
                libro = paginar(listaInterceptada, mostrarPorPagina);
            } else {
                libro = paginar(cjtoAux, mostrarPorPagina);
            }
        } else {
            listaInterceptada = intersectar(listas);
            libro = paginar(listaInterceptada, mostrarPorPagina);
        }
        totalPaginas = libro.size();
        pagina = 1;
    }

    /**
     * Busca una página específica de la busqueda.
     *
     * @param pagina La página que se desea revisar.
     * @return Lista de Actividades ubicadas en la Pagina solicitada.
     */
    public static ArrayList<Actividad> buscarPagina(BusquedaActividad busqueda,
            int pagina) {
        //ArrayList<Actividad> resp = new ArrayList<>(0);
        if (busqueda.getLibro().size() > 0) {
            return busqueda.getLibro().get(pagina);
        }
        return new ArrayList<>(0);
    }

    /**
     * Vacía la información de todas las actividades en una lista en limpio
     *
     * @return Lista con todas las actividades generadas por la busqueda.
     */
    private ArrayList<Actividad> coleccion() {
        Iterator it = libro.iterator();
        ArrayList<Actividad> resp = new ArrayList<>(0);
        while (it.hasNext()) {
            ArrayList<Actividad> aux0 = (ArrayList<Actividad>) it.next();
            resp.addAll(aux0);
        }

        return resp;
    }

    /**
     * Divide en paginas las actividades conseguidas en la busqueda.
     *
     * @param listaActividades Todas las actividades que se consiguieron en la
     * busqueda.
     * @param cantidadPorPagina El numero de actividades que se pueden mostrar
     * por pagina.
     * @return Una lista que, en cada posicion, contiene una lista de
     * actividades. Representa las paginas que se han de mostrar.
     */
    public ArrayList<ArrayList<Actividad>> paginar(ArrayList<Actividad> listaActividades,
            int cantidadPorPagina) {
        ArrayList<ArrayList<Actividad>> resp = new ArrayList<>(0);
        Iterator it = listaActividades.iterator();

        while (it.hasNext()) {
            ArrayList<Actividad> aux = new ArrayList<>(0);
            for (int i = 0; i < cantidadPorPagina && it.hasNext(); i++) {
                aux.add((Actividad) it.next());
            }
            resp.add(aux);

        }

        return resp;
    }

    /**
     * Procedimineto para reconfigurar la paginacion de la busqueda.
     *
     * @param cantidadPorPagina Cantidad de actividades a mostrar por cada
     * pagina
     */
    public void repaginar(int cantidadPorPagina) {
        ArrayList<Actividad> compilacion = this.coleccion();
        libro = new ArrayList<>(0);
        Iterator it = compilacion.iterator();

        while (it.hasNext()) {
            ArrayList<Actividad> unaPagina = new ArrayList<>(0);
            for (int i = 0; i < cantidadPorPagina && it.hasNext(); i++) {
                unaPagina.add((Actividad) it.next());
            }
            libro.add(unaPagina);
        }
    }

    /**
     * Tomando un arreglo de listas de actividad intersecta las mismas
     *
     * @param listas Las listas que debo intersectar
     * @return La interseccion de las listas.
     */
    private static ArrayList<Actividad> intersectar(ArrayList<ArrayList<Actividad>> listas) {
        ArrayList<Actividad> interseccion = new ArrayList<>(0);
        ArrayList<Actividad> unaLista = listas.get(0);
        System.out.println("#################################################################################################\n"
                + "###################################################################################################");
        System.out.println("El tamaño de la lista de listas es: " + listas.size());
        Iterator it = unaLista.iterator();
        while (it.hasNext()) {
            Actividad unaActividad = (Actividad) it.next();         //Tomo la 1ra actividad de la primera lista (pivote)
            boolean agregar = true;
            Iterator itAux = listas.iterator();
            itAux.next();                                           //Salto la primera lista del iterator, ya fue tomada anteriormente
            while (itAux.hasNext() && agregar) {
                ArrayList<Actividad> aComparar = (ArrayList<Actividad>) itAux.next();   //Tomo a partir de la segunda lista
                if(aComparar.isEmpty()){
                    return new ArrayList<>(0);
                }
                agregar &= unaActividad.contenidoEn(aComparar);                //Reviso existencia
                System.out.println(unaActividad.toString() + "\t" + agregar);
            }
            if (agregar) {
                System.out.println("Agregando una actividad!!!\n\t" + unaActividad.toString());
                interseccion.add(unaActividad);                 //Agrego
            }
        }
        System.out.println("#################################################################################################\n"
                + "###################################################################################################");
        return interseccion;
    }

    /**
     * Funcion que parsea y compara dos fechas.
     *
     * @param fechaIni
     * @param fechaFin
     * @return true en caso de que las fechas esten en orden.
     * @throws ParseException
     */
    private static boolean antesDe(String fechaIni, String fechaFin) throws ParseException {
        boolean resp = false;
        Calendar cIni = Calendar.getInstance();
        Calendar cFin = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        cIni.setTime(sdf.parse(fechaIni));
        cFin.setTime(sdf.parse(fechaFin));

        resp = cIni.before(cFin);

        return resp;

    }

    /**
     * Funcion que parsea y compara dos fechas.
     *
     * @param fechaIni
     * @param fechaFin
     * @return true en caso de que las fechas esten en el orden deseado.
     * @throws ParseException
     */
    private static boolean despuesDe(String fechaIni, String fechaFin) throws ParseException {
        boolean resp = false;
        Calendar cIni = Calendar.getInstance();
        Calendar cFin = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        cIni.setTime(sdf.parse(fechaIni));
        cFin.setTime(sdf.parse(fechaFin));

        resp = cIni.after(cFin);

        return resp;

    }

    public static String[] cantidadActividadesPorTipo() {

        String[] estadistica = new String[2];
        String nombres = "";
        String cantidad = "";
        Entity eSelec = new Entity(0, 2);
        ResultSet rs = eSelec.seleccionarNumActividades();

        try {
            if (rs != null) {
                while (rs.next()) {
                    nombres += rs.getString("nombre_tipo_actividad") + "|";
                    cantidad += rs.getString("cantidad") + ",";
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nombres.length() != 0 && cantidad.length() != 0) {
            nombres = nombres.substring(0, nombres.length() - 1) + "&";
            cantidad = cantidad.substring(0, cantidad.length() - 1) + "&";
        }

        estadistica[0] = nombres;
        estadistica[1] = cantidad;
        return estadistica;
    }
}