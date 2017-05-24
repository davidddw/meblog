package org.cloud.meblog.service;

import org.cloud.meblog.model.Album;
import org.cloud.meblog.model.Gallery;

import java.util.List;

/**
 * Created by d05660ddw on 2017/4/12.
 */
public interface IGalleryService extends IService<Gallery> {

    long saveOrUpdateGallery(Gallery gallery);

    List<Gallery> getAllByOrder(String orderColumn, String orderDir, int startIndex, int pageSize);

    List<Gallery> getGalleryByAlbum(Album album);
}
