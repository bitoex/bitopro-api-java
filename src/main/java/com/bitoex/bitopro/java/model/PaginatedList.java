package com.bitoex.bitopro.java.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaginatedList<T> implements Iterable<T> {

    private final List<T> data;
    private final int page;
    private final int totalPages;

    @JsonCreator
    public PaginatedList(List<T> data, int page, int totalPages) {
        this.data = data;
        this.page = page;
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Iterator<T> iterator() {
        if(data != null) {
            return data.iterator();
        } 
        else {
            return Collections.<T>emptyList().iterator();
        }
    }
}
