package es.home.recetario.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Clase que maneja los errores de la aplicacion
 * 
 * @author dsblanco
 * 
 */
@ManagedBean
@RequestScoped
public class ErrorBean {

    /** Variable de serializacion */
    private static final long serialVersionUID = 1L;
    /** Variable que indica si es generico un error */
    private boolean generico;

    /**
     * Constructor publico
     */
    public ErrorBean() {
	super();
	final FacesContext context = FacesContext.getCurrentInstance();
	final Map<String, Object> map = context.getExternalContext().getRequestMap();
	final Throwable throwable = (Throwable) map.get("javax.servlet.error.exception");
	if (throwable == null) {
	    // El error se esta enviando concientemente
	    generico = false;
	} else {
	    generico = true;
	}
    }

    /**
     * @return the generico
     */
    public boolean isGenerico() {
	return generico;
    }

    /**
     * @param generico
     *            the generico to set
     */
    public void setGenerico(final boolean generico) {
	this.generico = generico;
    }

}
