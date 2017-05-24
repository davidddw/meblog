package org.cloud.meblog.service;

import org.cloud.meblog.model.Album;

import java.util.List;

/**
 * Created by d05660ddw on 2017/4/12.
 */
public interface IAlbumService extends IService<Album> {

    long saveOrUpdateAlbum(Album album);

    List<Album> getAllAlbum(int pageNum, int pageSize);

    List<Album> getAllByOrder(String orderColumn, String orderDir, int startIndex, int pageSize);
}
