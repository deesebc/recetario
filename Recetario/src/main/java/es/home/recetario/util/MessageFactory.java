package es.home.recetario.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Clase que permite obtener los mensajes
 * 
 * @author dsblanco
 * 
 */
public final class MessageFactory {
    /** Fichero con los mensajes */
    private static final ResourceBundle BUNDLE;
    /** Indicador del lenguaje a usar */
    private static final Locale LOCALE;

    /** constructor estatico */
    static {
	final FacesContext context = FacesContext.getCurrentInstance();
	LOCALE = context.getViewRoot().getLocale();
	BUNDLE = context.getApplication().getResourceBundle(context, "msgApp");
    }


    /**
     * Obtiene el mensaje parametrizado del fichero de propiedades
     * 
     * @param key
     *            mensaje a obtener
     * @param nivelMsg
     *            nivel de severidad
     * @param args
     *            parametros a usar en el mensaje
     * @return mensaje fornmateado
     */
    public static String getMessage(final String key, final Object... args) {
	if (args == null || args.length == 0) {
	    return BUNDLE.getString(key);
	}
	MessageFormat fmt = new MessageFormat(BUNDLE.getString(key));
	fmt.setLocale(LOCALE);
	return fmt.format(args).toString();
    }

    /** Constructor privado */
    private MessageFactory() {
	super();
    }
}
