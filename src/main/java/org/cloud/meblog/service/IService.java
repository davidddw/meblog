package org.cloud.meblog.service;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IService<T> {

    Long getCount(T entity);

    T getById(Long id);

    List<T> getAll();

    List<T> getAll(int pageNum, int pageSize);

    void save(T entity);

    int deleteById(Long id);
}
