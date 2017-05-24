package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.NovelInfo;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface NovelInfoMapper extends BaseMapper<NovelInfo> {
    List<NovelInfo> selectAllOrderBy(@Param("condition") String condition, @Param("column") String orderColumn, @Param("orderDir") String orderDir);

    int deleteRelationshipFromUser(@Param("id") Long id);

    NovelInfo selectRoleByUsername(@Param("name") String username);

    long selectCountByCondition(@Param("condition") String condition, NovelInfo novelInfo);
}
