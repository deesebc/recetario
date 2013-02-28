/**
 * 
 */
package es.home.recetario.bean.generic;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

/**
 * @author daniel
 *
 */
public abstract class DataTableBean extends AbstractBean{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected int totalRows;

    // Paging.
    protected int firstRow;
    protected int rowsPerPage;
    protected int totalPages;
    protected int pageRange;
    protected Integer[] pages;
    protected int currentPage;
    public DataTableBean(){
	super();
	rowsPerPage = 10; // Default rows per page (max amount of rows to be displayed at once).
	pageRange = 10; // Default page range (max amount of page links to be displayed at once).
    }
    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
	return currentPage;
    }

    /**
     * @return the firstRow
     */
    public int getFirstRow() {
	return firstRow;
    }

    /**
     * Obtiene el indice del ultimo registro
     * 
     * @return the lastRow
     */
    public int getLastRow() {
	int retorno = firstRow + rowsPerPage;
	if (retorno > totalRows) {
	    retorno = totalRows;
	}
	return retorno;
    }
    /**
     * @return the pageRange
     */
    public int getPageRange() {
	return pageRange;
    }
    /**
     * @return the pages
     */
    public Integer[] getPages() {
	return pages;
    }
    /**
     * @return the rowsPerPage
     */
    public int getRowsPerPage() {
	return rowsPerPage;
    }
    /**
     * @return the totalPages
     */
    public int getTotalPages() {
	return totalPages;
    }
    /**
     * @return the totalRows
     */
    public int getTotalRows() {
	return totalRows;
    }
    protected abstract void loadDataList();
    public void page(final ActionEvent event) {
	page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }

    public void page(final int firstRow) {
	this.firstRow = firstRow;
	loadDataList(); // Load requested page.
    }

    public void pageFirst() {
	page(0);
    }
    public void pageLast() {
	page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }
    public void pageNext() {
	page(firstRow + rowsPerPage);
    }

    public void pagePrevious() {
	page(firstRow - rowsPerPage);
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(final int currentPage) {
	this.currentPage = currentPage;
    }

    /**
     * @param firstRow the firstRow to set
     */
    public void setFirstRow(final int firstRow) {
	this.firstRow = firstRow;
    }

    /**
     * @param pageRange the pageRange to set
     */
    public void setPageRange(final int pageRange) {
	this.pageRange = pageRange;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(final Integer[] pages) {
	this.pages = pages;
    }

    /**
     * @param rowsPerPage the rowsPerPage to set
     */
    public void setRowsPerPage(final int rowsPerPage) {
	this.rowsPerPage = rowsPerPage;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(final int totalPages) {
	this.totalPages = totalPages;
    }

    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(final int totalRows) {
	this.totalRows = totalRows;
    }
}
