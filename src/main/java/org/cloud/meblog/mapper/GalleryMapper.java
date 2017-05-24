package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.Gallery;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/4/4.
 */
public interface GalleryMapper extends BaseMapper<Gallery> {
    List<Gallery> selectGalleriesByAlbum(@Param("id") Long id);

    List<Gallery> selectAllOrderBy(@Param("column") String orderColumn, @Param("orderDir") String orderDir);
}
