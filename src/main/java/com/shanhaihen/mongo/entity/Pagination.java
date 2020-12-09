package com.shanhaihen.mongo.entity;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination extends RowBounds implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 该操作只是为了忽略RowBounds属性
     *
     */
    private transient int offset;
    /**
     * 该操作只是为了忽略RowBounds属性
     *
     */
    private transient int limit;

    /**
     * 总数
     */
    private int total;

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 查询总记录数（默认 true）
     */
    private transient boolean searchCount = true;

    /**
     * 开启排序（默认 true） 只在代码逻辑判断 并不截取sql分析
     *
     **/
    private transient boolean openSort = true;


    /**
     * <p>
     * SQL 排序 ASC 集合
     * </p>
     */
    private transient List<String> ascs;
    /**
     * <p>
     * SQL 排序 DESC 集合
     * </p>
     */
    private transient List<String> descs;

    /**
     * 是否为升序 ASC（ 默认： true ）
     *
     * @see #ascs
     * @see #descs
     */
    private transient boolean isAsc = true;

    /**
     * <p>
     * SQL 排序 ORDER BY 字段，例如： id DESC（根据id倒序查询）
     * </p>
     * <p>
     * DESC 表示按倒序排序(即：从大到小排序)<br>
     * ASC 表示按正序排序(即：从小到大排序)
     *
     * @see #ascs
     * @see #descs
     * </p>
     */
    private transient String orderByField;

    public Pagination() {
        super();
    }

    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Pagination(int current, int size) {
        this(current, size, true);
    }

    public Pagination(int current, int size, boolean searchCount) {
        this(current, size, searchCount, true);
    }

    public Pagination(int current, int size, boolean searchCount, boolean openSort) {
        super(offsetCurrent(current, size), size);
        setOffset(offsetCurrent(current, size));
        setLimit(size);
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.searchCount = searchCount;
        this.openSort = openSort;
    }

    protected static int offsetCurrent(int current, int size) {
        if (current > 0) {
            return (current - 1) * size;
        }
        return 0;
    }

    public int offsetCurrent() {
        return offsetCurrent(this.current, this.size);
    }

    public boolean hasPrevious() {
        return this.current > 1;
    }

    public boolean hasNext() {
        return this.current < this.pages;
    }

    public int getTotal() {
        return total;
    }

    public Pagination setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getSize() {
        return size;
    }

    public Pagination setSize(int size) {
        this.size = size;
        return this;
    }

    public int getPages() {
        if (this.size == 0) {
            return 0;
        }
        this.pages = this.total / this.size;
        if (this.total % this.size != 0) {
            this.pages++;
        }
        return this.pages;
    }

    public int getCurrent() {
        return current;
    }

    public Pagination setCurrent(int current) {
        this.current = current;
        return this;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public Pagination setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
        return this;
    }

    /**
     * @see #ascs
     * @see #descs
     */
    @Deprecated
    public String getOrderByField() {
        return orderByField;
    }

    /**
     * @see #ascs
     * @see #descs
     */
    public Pagination setOrderByField(String orderByField) {
        if (StringUtils.isNotEmpty(orderByField)) {
            this.orderByField = orderByField;
        }
        return this;
    }

    public boolean isOpenSort() {
        return openSort;
    }

    public Pagination setOpenSort(boolean openSort) {
        this.openSort = openSort;
        return this;
    }

    public List<String> getAscs() {
        return orders(isAsc, ascs);
    }

    private List<String> orders(boolean condition, List<String> columns) {
        if (condition && StringUtils.isNotEmpty(orderByField)) {
            if (columns == null) {
                columns = new ArrayList<>();
            }
            if (!columns.contains(orderByField)) {
                columns.add(orderByField);
            }
        }
        return columns;
    }

    public Pagination setAscs(List<String> ascs) {
        this.ascs = ascs;
        return this;
    }

    public List<String> getDescs() {
        return orders(!isAsc, descs);
    }

    public Pagination setDescs(List<String> descs) {
        this.descs = descs;
        return this;
    }

    /**
     * @see #ascs
     * @see #descs
     */
    @Deprecated
    public boolean isAsc() {
        return isAsc;
    }

    /**
     * @see #ascs
     * @see #descs
     */
    public Pagination setAsc(boolean isAsc) {
        this.isAsc = isAsc;
        return this;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public Pagination setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public Pagination setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public String toString() {
        return "Pagination { total=" + total + " ,size=" + size + " ,pages=" + pages + " ,current=" + current + " }";
    }

}