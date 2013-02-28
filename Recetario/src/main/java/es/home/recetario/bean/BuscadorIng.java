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
import es.home.recetario.service.IngredienteService;
import es.home.recetario.vo.Ingrediente;

/**
 * @author deesbc
 * 
 */
@ManagedBean
@SessionScoped
public class BuscadorIng extends DataTableBean {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(BuscadorIng.class);
    private List<Ingrediente> lista;
    private Ingrediente selected;
    private String nomIng;
    @ManagedProperty(value = "#{ingredienteServiceImpl}")
    private IngredienteService servIng;

    public String buscar() {
	String retorno = null;
	try {
	    lista = servIng.buscarIngPaginado(nomIng, firstRow, rowsPerPage);
	} catch (Exception except) {
	    retorno = "error";
	    LOGGER.error("Ha ocurrido un error durante la busqueda: ", except);
	}
	return retorno;
    }

    public String editar() {
	return "detalleIngrediente";
    }

    public String eliminar() {
	String retorno = null;
	try {
	    if (selected.getRelRectIngs().size() > 0) {
		generaMensajeError("info.01.borrarRel");
	    } else {
		servIng.borrar(selected);
		loadDataList();
	    }
	} catch (Exception except) {
	    retorno = "error";
	    LOGGER.error("Ha ocurrido un error durante la eliminacion: " + selected.getIdIngrediente(), except);
	}
	return retorno;
    }

    /**
     * @return the lista
     */
    public List<Ingrediente> getLista() {
	if (lista == null) {
	    loadDataList();
	}
	return lista;
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
    public Ingrediente getSelected() {
	return selected;
    }

    /**
     * @return the servIng
     */
    public IngredienteService getServIng() {
	return servIng;
    }

    @Override
    protected void loadDataList() {
	lista = servIng.paginarResultados(firstRow, rowsPerPage);
	BigDecimal temp = new BigDecimal(servIng.contarIngredientes());
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
    public void setLista(final List<Ingrediente> lista) {
	this.lista = lista;
    }

    /**
     * @param nomIng
     *            the nomIng to set
     */
    public void setNomIng(final String nomIng) {
	this.nomIng = nomIng;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(final Ingrediente selected) {
	this.selected = selected;
    }

    /**
     * @param servIng
     *            the servIng to set
     */
    public void setServIng(final IngredienteService servIng) {
	this.servIng = servIng;
    }

}
