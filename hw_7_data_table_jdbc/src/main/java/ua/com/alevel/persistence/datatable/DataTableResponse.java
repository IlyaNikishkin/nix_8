package ua.com.alevel.persistence.datatable;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableResponse<E extends BaseEntity> {

    private List<E> items;
    private long itemsSize;
    private Map<Object, Object> otherParamMap;
    private int currentPage;
    private int currentSize;
    private String sort;
    private String order;

    public DataTableResponse() {
        items = new ArrayList<>();
        otherParamMap = new HashMap<>();
        itemsSize = 0;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public long getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(long itemsSize) {
        this.itemsSize = itemsSize;
    }

    public Map<Object, Object> getOtherParamMap() {
        return otherParamMap;
    }

    public void setOtherParamMap(Map<Object, Object> otherParamMap) {
        this.otherParamMap = otherParamMap;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
