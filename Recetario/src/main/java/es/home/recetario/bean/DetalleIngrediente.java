package es.home.recetario.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.home.recetario.service.IngredienteService;
import es.home.recetario.util.MessageFactory;
import es.home.recetario.vo.Ingrediente;

@ManagedBean
@ViewScoped
public class DetalleIngrediente implements Serializable {


    private static final long serialVersionUID = 6417603015813227098L;
    private static final Logger LOGGER = Logger.getLogger(DetalleIngrediente.class);
    @ManagedProperty(value = "#{buscadorIng.selected}")
    private Ingrediente ingrediente;
    @ManagedProperty(value = "#{ingredienteServiceImpl}")
    private transient IngredienteService servIng;

    static {
	CTX = new ClassPathXmlApplicationContext("/es/home/recetario/context/appContextRecetaBD.xml");
    }

    /** Contexto Spring de la aplicacion */
    private static final ApplicationContext CTX;

    protected static Object getBean(final String bean) {
	return CTX.getBean(bean);
    }

    public DetalleIngrediente() {
	super();
	ingrediente = new Ingrediente();
    }

    protected void generaMensajeError(final String keyError, final Object... args) {
	String mensaje = MessageFactory.getMessage(keyError, args);
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	final FacesMessage message = new FacesMessage(mensaje);
	message.setSeverity(FacesMessage.SEVERITY_ERROR);
	facesContext.addMessage(null, message);
	facesContext.renderResponse();
    }

    /**
     * @return the ingrediente
     */
    public Ingrediente getIngrediente() {
	return ingrediente;
    }

    /**
     * @return the servIng
     */
    public IngredienteService getServIng() {
	if(servIng == null){
	    servIng = (IngredienteService) getBean("ingredienteServiceImpl");
	}
	return servIng;
    }

    public String guardar(){
	String retorno =  "busqIngredientes";
	try{
	    if(ingrediente.getIdIngrediente() < 1){
		getServIng().crear(ingrediente.getNombre());
	    }else{
		getServIng().actualizar(ingrediente);
	    }
	    final FacesContext fContext = FacesContext.getCurrentInstance();
	    final BuscadorIng busqIng = (BuscadorIng) fContext.getExternalContext().getSessionMap().get("buscadorIng");
	    busqIng.page(busqIng.getFirstRow());
	}catch(Exception except){
	    retorno = "error";
	    generaMensajeError("ing.error.01.save");
	    LOGGER.error("Error al guardar la receta",except);
	}
	return retorno;
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
	if(ingrediente == null){
	    ingrediente = new Ingrediente();
	}
    }

    /**
     * @param ingrediente the ingrediente to set
     */
    public void setIngrediente(final Ingrediente ingrediente) {
	this.ingrediente = ingrediente;
    }

    /**
     * @param servIng the servIng to set
     */
    public void setServIng(final IngredienteService servIng) {
	this.servIng = servIng;
    }

}
