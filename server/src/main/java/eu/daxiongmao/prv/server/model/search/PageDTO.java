package eu.daxiongmao.prv.server.model.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageDTO<T> {

    private List<T> content;
    private int number;
    private int numberOfElements;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageDTO(final Page<T> page) {
        super();
        this.content = new ArrayList<T>(page.getContent());
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public PageDTO(final Page<?> page, final List<T> content) {
        super();
        this.content = content;
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(final List<T> content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(final int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(final long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }


}
