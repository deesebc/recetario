/**
 * 
 */
package es.home.recetario.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import es.home.recetario.bean.generic.DataTableBean;
import es.home.recetario.service.RecetaService;
import es.home.recetario.vo.Receta;

/**
 * @author daniel
 * 
 */
@ManagedBean
@SessionScoped
public class BuscadorReceta extends DataTableBean {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(BuscadorReceta.class);
    private List<Receta> lista;
    private Receta selected;
    private String nombre;
    private String nomIng;

    @ManagedProperty(value = "#{recetaServiceImpl}")
    private RecetaService servReceta;

    public BuscadorReceta() {
	super();
    }

    public String buscar(){
	String retorno = null;
	try{
	    lista = servReceta.buscarRecetaPaginadas(nombre, nomIng, firstRow, rowsPerPage);
	}catch(Exception except){
	    retorno = "error";
	    LOGGER.error("Ha ocurrido un error durante la busqueda: ", except);
	}
	return retorno;
    }

    /**
     * @return the lista
     */
    public List<Receta> getLista() {
	if (lista == null) {
	    loadDataList();
	}
	return lista;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @return the nomIng
     */
    public String getNomIng() {
	return nomIng;
    }

    /**
     * @return the selected
     */
    public Receta getSelected() {
	return selected;
    }

    /**
     * @return the servReceta
     */
    public RecetaService getServReceta() {
	return servReceta;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.home.recetario.bean.generic.DataTableBean#loadDataList()
     */
    @Override
    protected void loadDataList() {
	// Load list and totalCount.
	lista = servReceta.paginarResultados(firstRow, rowsPerPage);
	BigDecimal temp = new BigDecimal(servReceta.contarRecetas());
	totalRows = temp.intValue();

	// Set currentPage, totalPages and pages.
	currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
	totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
	int pagesLength = Math.min(pageRange, totalPages);
	pages = new Integer[pagesLength];

	// firstPage must be greater than 0 and lesser than totalPages-pageLength.
	int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

	// Create pages (page numbers for page links).
	for (int i = 0; i < pagesLength; i++) {
	    pages[i] = ++firstPage;
	}
    }

    /**
     * @param lista
     *            the lista to set
     */
    public void setLista(final List<Receta> lista) {
	this.lista = lista;
    }

    /**
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(final String nombre) {
	this.nombre = nombre;
    }

    /**
     * @param nomIng
     *            the nomIng to set
     */
    public void setNomIng(final String nomIng) {
	this.nomIng = nomIng;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(final Receta selected) {
	this.selected = selected;
    }

    /**
     * @param servReceta
     *            the servReceta to set
     */
    public void setServReceta(final RecetaService servReceta) {
	this.servReceta = servReceta;
    }

    public String ver(){
	return "detalleReceta";
    }

}
