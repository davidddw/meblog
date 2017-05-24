package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.Tag;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface TagMapper extends BaseMapper<Tag> {
    Tag selectByName(String name);

    void updateCountByPrimaryKey(Tag tag);

    List<Tag> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);
}
