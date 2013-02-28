package es.home.recetario.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Menu {

    public String goToBuscadorIngrediente() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().put("buscadorReceta", null);
        return "busqIngredientes";
    }

    public String goToBuscadorReceta() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().put("buscadorIng", null);
        return "busqRecetas";
    }

    public String goToCrearIngrediente() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().put("buscadorReceta", null);
        facesContext.getExternalContext().getSessionMap().put("buscadorIng", null);
        return "detalleIngrediente";
    }

    public String goToCrearReceta() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getSessionMap().put("buscadorIng", null);
        facesContext.getExternalContext().getSessionMap().put("buscadorReceta", null);
        return "detalleReceta";
    }

}
