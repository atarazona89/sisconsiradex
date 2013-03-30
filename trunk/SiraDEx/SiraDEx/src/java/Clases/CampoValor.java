/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DBMS.Entity;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author SisCon
 */
public class CampoValor implements Serializable {

    private Campo campo;
    private String valor;
    private FormFile file = null;
    private static String[] ATRIBUTOS = {
        "id_campo", //0
        "id_actividad", //1
        "valor", //2
        "archivo" //3
    };
    private static String[] TABLAS = {
        "VALOR",
        "CAMPO",
        "ACTIVIDAD"
    };

    public CampoValor() {
    }

    public CampoValor(Campo campo) {
        this.campo = campo;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
        this.valor = file.getFileName();
    }

    public void setFile(final byte[] data) {
        try {
            FormFile ff = new FormFile() {
                @Override
                public String getContentType() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void setContentType(String contentType) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public int getFileSize() {
                    return data.length;
                }

                @Override
                public void setFileSize(int fileSize) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public String getFileName() {
                    return valor;
                }

                @Override
                public void setFileName(String fileName) {
                    valor = fileName;
                }

                @Override
                public byte[] getFileData() {
                    return data;
                }

                @Override
                public InputStream getInputStream() throws FileNotFoundException, IOException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void destroy() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };

            BufferedOutputStream bos;
            bos = new BufferedOutputStream(new FileOutputStream(valor));
            bos.write(data);
            bos.flush();

            System.out.println("File Name:" + ff.getFileName());
            System.out.println("File size:" + ff.getFileSize() + "bytes");
            file = ff;

            if (bos != null) {

                bos.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(CampoValor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Root.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = null;
    }

    public boolean agregar(int idAct) {
        Entity eAgregar = new Entity(1, 6);//INSERT VALOR
        boolean resp = true;

        Integer idCampo = new Integer(campo.getIdCampo());
        Integer idActividad = new Integer(idAct);

        if (file != null) {
            if (file.getFileSize() > 2097152) { //2097152 bytes = 2MB
                return false;
            }
            Object[] tupla = {idCampo, idActividad, valor, file};
            resp = resp && eAgregar.insertar(tupla);
        } else {
            Object[] tupla = {idCampo, idActividad, valor};
            resp = resp && eAgregar.insertar(tupla);
        }

        return resp;
    }

    /*Crea una lista de CampoValor con los campos del tipo de actividad cuyo id 
     es pasado por parametro*/
    public static ArrayList<CampoValor> listarCampos(int idTipoActividad) {
        ArrayList<CampoValor> listaValor = new ArrayList<>(0);
        Entity eCampo = new Entity(0, 3);//SELECT CAMPO
        String[] columnas = {"id_tipo_actividad"};
        Integer[] condiciones = {idTipoActividad};

        ResultSet rs = eCampo.seleccionar(columnas, condiciones);

        if (rs != null) {
            try {
                while (rs.next()) {
                    Campo c = new Campo();
                    c.setIdCampo(rs.getInt("id_campo"));
                    c.setIdTipoActividad(rs.getInt("id_tipo_actividad"));
                    c.setNombre(rs.getString("nombre_campo"));
                    c.setTipo(rs.getString("tipo_campo"));
                    c.setLongitud(rs.getInt("longitud"));
                    c.setObligatorio(rs.getBoolean("obligatorio"));
                    c.setCatalogo(rs.getString("catalogo"));
                    CampoValor cv = new CampoValor(c);
                    listaValor.add(cv);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CampoValor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaValor;
    }

    /* Crea una lista de CampoValor con los campos y valores de la actividad cuyo
     * id es pasado por parametro*/
    public static ArrayList<CampoValor> listarCamposValores(int idActividad) {
        try {
            ArrayList<CampoValor> listaValor = new ArrayList<>(0);
            Entity eCampo = new Entity(0, 2);//SELECT CAMPO
            String[] ATRIBUTO = {
                "id_campo",
                "id_tipo_actividad",
                "nombre_campo",
                "tipo_campo",
                "longitud",
                "obligatorio",
                "valor",
                "catalogo",
                "archivo"
            };
            String[] tabABuscar = {
                TABLAS[0],
                TABLAS[1],
                TABLAS[2]
            };
            String[] colCondicion = {"id_actividad"};
            Object[] colValor = {idActividad};
            try (ResultSet rs = eCampo.naturalJoin(ATRIBUTO, tabABuscar, colCondicion, colValor)) {
                if (rs != null) {
                    while (rs.next()) {
                        CampoValor cv = new CampoValor();
                        cv.setValor(rs.getString(ATRIBUTO[6]));



                        String tipoCampo = rs.getString(ATRIBUTO[3]);
                        if (!cv.getValor().equals("")
                                && ((tipoCampo.equals(ATRIBUTO[8])
                                || tipoCampo.equals("producto")))) {
                            byte[] data = rs.getBytes(ATRIBUTO[8]);
                            cv.setFile(data);

                        }
                        Campo c = new Campo();
                        c.setIdCampo(rs.getInt(ATRIBUTO[0]));
                        c.setIdTipoActividad(rs.getInt(ATRIBUTO[1]));
                        c.setNombre(rs.getString(ATRIBUTO[2]));
                        c.setTipo(rs.getString(ATRIBUTO[3]));
                        c.setLongitud(rs.getInt(ATRIBUTO[4]));
                        c.setObligatorio(rs.getBoolean(ATRIBUTO[5]));
                        c.setCatalogo(rs.getString(ATRIBUTO[7]));
                        cv.setCampo(c);

                        listaValor.add(cv);
                    }

                }
                rs.close();
            }

            return listaValor;
        } catch (SQLException ex) {
            Logger.getLogger(CampoValor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean modificar(CampoValor campoNM, int idAct) {
        boolean resp = true;
        Entity e = new Entity(2, 6);//Update valor

        String tipo = campoNM.getCampo().getTipo();

        if (!tipo.equals("archivo") && !tipo.equals("producto")) {
            String[] condColumnas = {
                ATRIBUTOS[0], //id_campo
                ATRIBUTOS[1], //id_actividad
                ATRIBUTOS[2] //valor
            };
            Object[] valores = {
                campoNM.getCampo().getIdCampo(),
                idAct,
                campoNM.getValor()
            };
            String[] colModificar = {ATRIBUTOS[2]}; //valor
            String[] modificaciones = {valor};

            resp = e.modificar(condColumnas, valores, colModificar, modificaciones);

        } else {
            /* PROBLEMAS CON EL FILE POR LO QUE NO FUNCIONA EL MODIFICAR CON UPDATE
            String[] condColumnas = {
                ATRIBUTOS[0], //id_campo
                ATRIBUTOS[1], //id_actividad
                ATRIBUTOS[2], //valor
                ATRIBUTOS[3]
            };
            Object[] valores = {
                campoNM.getCampo().getIdCampo(),
                idAct,
                campoNM.getValor(),
                campoNM.getFile()
            };
            String[] colModificar = {ATRIBUTOS[2],ATRIBUTOS[3]}; //valor
            Object[] modificaciones = {valor, file};
            
            resp &= e.modificar(condColumnas, valores, colModificar, modificaciones);
            */
            e = new Entity(5, 6);//DELETE VALOR
            String[] campos = {
                ATRIBUTOS[0], //id_campo
                ATRIBUTOS[1] //id_actividad
            };
            Integer[] condicion = {
                campoNM.getCampo().getIdCampo(),
                idAct
            };

            resp &= e.borrar(campos, condicion);

            System.out.println("Borrado " + resp + " en VALOR la tupla "
                    + "con id_campo = " + campoNM.getCampo().getIdCampo()
                    + " id_actividad = " + idAct);

            e = new Entity(1, 6); //INSERT VALOR

            Object[] tuplaInsert = {
                campo.getIdCampo(),
                idAct,
                valor,
                file
            };

            resp &= e.insertar(tuplaInsert);
            System.out.println("Isercion " + resp + " en VALOR");
        }

        return resp;
    }
}
