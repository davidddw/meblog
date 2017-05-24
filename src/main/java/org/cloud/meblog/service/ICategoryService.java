package org.cloud.meblog.service;

import org.cloud.meblog.model.Category;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface ICategoryService extends IService<Category> {
    Category getCategoryByName(String name);

    long saveOrUpdateCategory(Category category);

    List<Category> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);
}
