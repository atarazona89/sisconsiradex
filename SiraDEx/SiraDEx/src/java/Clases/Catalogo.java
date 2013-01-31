/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DBMS.Entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author SisCon
 */
public class Catalogo extends ActionForm {

    private int idCatalogo;
    private String nombre;
    private int nroCampos;
    private ArrayList<CampoCatalogo> campos;
    private static final String[] ATRIBUTOS = {
        "id_cat",
        "nombre",
        "nro_campos"
    };
    private static final String[] tiposCampos = {
        "texto", //STRING
        "numero", //INT
        "fecha", //DATE
    };

    public static String[] getTiposCampos() {
        return tiposCampos;
    }

    public Catalogo() {
    }

    public static Catalogo leer() throws IOException {
        Catalogo resp;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduzca el nombre del catalogo:");
        String name = br.readLine();
        System.out.println("");
        System.out.println("Introduzca el nro de campos: ");
        int nro = Integer.parseInt(br.readLine());
        System.out.println("");

        resp = new Catalogo(name, nro);
        resp.campos = new ArrayList<>();
        for (int i = 0; i < nro; i++) {
            CampoCatalogo cc = CampoCatalogo.leer();
            resp.campos.add(cc);
        }

        return resp;
    }

    public Catalogo(String nombre, int nroCampos) {
        this.nombre = nombre;
        this.nroCampos = nroCampos;
    }

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo() {
        Entity eId = new Entity(0, 8);
        try {
            String[] proyectar = {ATRIBUTOS[0]};
            String[] columnas = {
                "nombre"
            };
            Object[] valores = {
                this.nombre
            };
            ResultSet rs = eId.proyectar(proyectar, columnas, valores);

            if (rs.next()) {
                try {
                    this.idCatalogo = rs.getInt(ATRIBUTOS[0]);
                } catch (SQLException ex) {
                    Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Catalogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ;
    
    public void setIdCatalogo(int idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNroCampos() {
        return nroCampos;
    }

    public void setNroCampos(int nroCampos) {
        this.nroCampos = nroCampos;
    }

    public ArrayList<CampoCatalogo> getCampos() {
        return campos;
    }

    public void setCampos(ArrayList<CampoCatalogo> campos) {
        this.campos = campos;
    }

    @Override
    public String toString() {
        return "Catalogo{" + "nombre=" + nombre + ", nroCampos=" + nroCampos + '}';
    }

    public boolean agregar() {
        Entity eCatalogo = new Entity(1, 8);
        boolean resp = true;

        String[] columnas = {
            "nombre",
            "nro_campos"
        };
        Integer nCampos = new Integer(this.nroCampos);
        Object[] valores = {
            this.nombre,
            nCampos
        };

        resp &= eCatalogo.insertar2(columnas, valores);
        if (resp) {
            setIdCatalogo();
            Iterator itCampos = this.campos.iterator();

            while (itCampos.hasNext() && resp) {
                CampoCatalogo cC = (CampoCatalogo) itCampos.next();
                resp &= cC.agregarCampo(idCatalogo);
            }
        } else {
            return resp;
        }


        return resp;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduzca la cantidad de catalogos a agregar: ");
        int tam = Integer.parseInt(br.readLine());
        System.out.println("");
        Catalogo[] c = new Catalogo[tam];

        for (int i = 0; i < tam; i++) {
            c[i] = Catalogo.leer();
            System.out.println("agregando: " + c[i].toString());
            c[i].agregar();
        }


    }
}