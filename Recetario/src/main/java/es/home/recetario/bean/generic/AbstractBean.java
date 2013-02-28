/**
 * 
 */
package es.home.recetario.bean.generic;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.home.recetario.util.MessageFactory;

/**
 * @author daniel
 * 
 */
public class AbstractBean implements Serializable {

    /** Contexto Spring de la aplicacion */
    private static final ApplicationContext CTX;

    static {
        CTX = new ClassPathXmlApplicationContext(
                "/es/home/recetario/context/appContextRecetaBD.xml");
    }

    /**
     * 
     */
    private static final long serialVersionUID = 526401289257082969L;

    protected static Object getBean(final String bean) {
        return CTX.getBean(bean);
    }

    private void generaMensaje(final String key, final FacesMessage.Severity severity,
            final Object... args) {
        final String mensaje = MessageFactory.getMessage(key, args);
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final FacesMessage message = new FacesMessage(mensaje);
        message.setSeverity(severity);
        facesContext.addMessage(null, message);
        facesContext.renderResponse();
    }

    protected void generaMensajeError(final String keyError, final Object... args) {
        generaMensaje(keyError, FacesMessage.SEVERITY_ERROR, args);
    }

    protected void generaMensajeInfo(final String keyInfo, final Object... args) {
        generaMensaje(keyInfo, FacesMessage.SEVERITY_INFO, args);
    }

}
