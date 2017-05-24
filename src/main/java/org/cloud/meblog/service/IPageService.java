package org.cloud.meblog.service;

import org.cloud.meblog.model.Page;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IPageService extends IService<Page> {

    List<Page> getNameAndSlug();

    long saveOrUpdateCategory(Page page);

    Page getBySlug(String slug);

    List<Page> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);
}
