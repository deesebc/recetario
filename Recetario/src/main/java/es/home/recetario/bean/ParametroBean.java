/**
 * 
 */
package es.home.recetario.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author daniel
 *
 */
@ManagedBean
@RequestScoped
public class ParametroBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2612311124913462997L;
    private String uno;
    private String dos;
    /**
     * @return the dos
     */
    public String getDos() {
	return dos;
    }
    /**
     * @return the uno
     */
    public String getUno() {
	return uno;
    }
    /**
     * @param dos the dos to set
     */
    public void setDos(final String dos) {
	this.dos = dos;
    }
    /**
     * @param uno the uno to set
     */
    public void setUno(final String uno) {
	this.uno = uno;
    }
}
