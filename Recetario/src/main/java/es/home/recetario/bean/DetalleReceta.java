package es.home.recetario.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import es.home.recetario.bean.generic.DataTableBean;
import es.home.recetario.service.IngredienteService;
import es.home.recetario.service.RecetaService;
import es.home.recetario.vo.Ingrediente;
import es.home.recetario.vo.Receta;
import es.home.recetario.vo.RelRectIng;

@ManagedBean
@ViewScoped
public class DetalleReceta extends DataTableBean {

	private static final long serialVersionUID = -7647929779133437125L;
	private static final Logger LOGGER = Logger.getLogger(DetalleReceta.class);
	@ManagedProperty(value = "#{buscadorReceta.selected}")
	private Receta receta;
	private List<Ingrediente> lista;
	@ManagedProperty(value = "#{ingredienteServiceImpl}")
	private transient IngredienteService servIng;
	@ManagedProperty(value = "#{recetaServiceImpl}")
	private transient RecetaService servRec;
	private Map<Ingrediente, Boolean> selecteds;
	private boolean selectAll;
	private boolean editable;

	public DetalleReceta() {
		super();
		lista = new ArrayList<Ingrediente>();
		receta = new Receta();
		selecteds = new HashMap<Ingrediente, Boolean>();
		editable = false;
	}

	private void asociarIngredientes() {
		receta.getRelRectIngs().clear();
		for (Entry<Ingrediente, Boolean> entry : selecteds.entrySet()) {
			if (entry.getValue()) {
				RelRectIng rel = new RelRectIng();
				rel.setIngrediente(entry.getKey());
				rel.setReceta(receta);
				receta.getRelRectIngs().add(rel);
			}
		}
	}

	/**
	 * @return the lista
	 */
	public List<Ingrediente> getLista() {
		loadDataList();
		return lista;
	}

	/**
	 * @return the receta
	 */
	public Receta getReceta() {
		return receta;
	}

	/**
	 * @return the selecteds
	 */
	public Map<Ingrediente, Boolean> getSelecteds() {
		return selecteds;
	}

	/**
	 * @return the servIng
	 */
	public IngredienteService getServIng() {
		if (servIng == null) {
			servIng = (IngredienteService) getBean("ingredienteServiceImpl");
		}
		return servIng;
	}

	/**
	 * @return the servRec
	 */
	public RecetaService getServRec() {
		if (servRec == null) {
			servRec = (RecetaService) getBean("recetaServiceImpl");
		}
		return servRec;
	}

	public String guardar() {
		String retorno = "busqRecetas";
		try {
			asociarIngredientes();
			getServRec().actualizarReceta(receta);
			final FacesContext fContext = FacesContext.getCurrentInstance();
			final BuscadorReceta busqRec = (BuscadorReceta) fContext
					.getExternalContext().getSessionMap().get("buscadorReceta");
			busqRec.page(busqRec.getFirstRow());
		} catch (Exception except) {
			retorno = "error";
			LOGGER.error("Error al guardar la receta", except);
		}
		return retorno;
	}

	@PostConstruct
	private void init() {
		if (receta == null) {
			editable = true;
			receta = new Receta();
		}
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @return the selectAll
	 */
	public boolean isSelectAll() {
		return selectAll;
	}

	@Override
	protected void loadDataList() {
		if (receta == null || editable) {
			// es una receta nueva
			lista = getServIng().paginarResultados(firstRow, rowsPerPage);
			totalRows = getServIng().contarIngredientes();
		} else {
			lista.clear();
			// estamos editando una receta
			for (RelRectIng relacion : receta.getRelRectIngs()) {
				lista.add(relacion.getIngrediente());
				selecteds.put(relacion.getIngrediente(), true);
			}
			selectAll = true;
			totalRows = lista.size();
		}

		// Set currentPage, totalPages and pages.
		currentPage = (totalRows / rowsPerPage)
				- ((totalRows - firstRow) / rowsPerPage) + 1;
		totalPages = (totalRows / rowsPerPage)
				+ ((totalRows % rowsPerPage != 0) ? 1 : 0);
		int pagesLength = Math.min(pageRange, totalPages);
		pages = new Integer[pagesLength];

		// firstPage must be greater than 0 and lesser than
		// totalPages-pageLength.
		int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)),
				totalPages - pagesLength);

		// Create pages (page numbers for page links).
		for (int i = 0; i < pagesLength; i++) {
			pages[i] = ++firstPage;
		}

	}

	public void marcarOpciones(final AjaxBehaviorEvent event) {
		if (selectAll) {
			for (Ingrediente ing : lista) {
				selecteds.put(ing, true);
			}
		} else {
			selecteds.clear();
		}
	}

	public String modificar() {
		editable = true;
		loadDataList();
		for (RelRectIng obj : receta.getRelRectIngs()) {
			selecteds.put(obj.getIngrediente(), true);
		}
		if (selecteds.size() == lista.size()) {
			selectAll = true;
		} else {
			selectAll = false;
		}
		return null;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/**
	 * @param lista
	 *            the lista to set
	 */
	public void setLista(final List<Ingrediente> lista) {
		this.lista = lista;
	}

	/**
	 * @param receta
	 *            the receta to set
	 */
	public void setReceta(final Receta receta) {
		this.receta = receta;
	}

	/**
	 * @param selectAll
	 *            the selectAll to set
	 */
	public void setSelectAll(final boolean selectAll) {
		this.selectAll = selectAll;
	}

	/**
	 * @param selecteds
	 *            the selecteds to set
	 */
	public void setSelecteds(final Map<Ingrediente, Boolean> selecteds) {
		this.selecteds = selecteds;
	}

	/**
	 * @param servIng
	 *            the servIng to set
	 */
	public void setServIng(final IngredienteService servIng) {
		this.servIng = servIng;
	}

	/**
	 * @param servRec
	 *            the servRec to set
	 */
	public void setServRec(final RecetaService servRec) {
		this.servRec = servRec;
	}

}
