package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.Album;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/4/4.
 */
public interface AlbumMapper extends BaseMapper<Album> {
    List<Album> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);
}
