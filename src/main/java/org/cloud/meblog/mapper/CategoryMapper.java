package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.Category;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface CategoryMapper extends BaseMapper<Category> {
    Category selectByName(String name);

    List<Category> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);
}
