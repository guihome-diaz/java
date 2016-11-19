package eu.daxiongmao.prv.server.model.search;

import java.util.List;

public class AbstractSearchRequestDTO {

    private Integer pageNumber;
    private Integer pageSize;
    private List<SortDTO> sorters;

    public List<SortDTO> getSorters() {
        return sorters;
    }

    public void setSorters(final List<SortDTO> sorters) {
        this.sorters = sorters;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }
}
