package org.cloud.meblog.service;

import org.cloud.meblog.model.NovelInfo;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface INovelInfoService extends IService<NovelInfo> {
    long saveOrUpdateNovelInfo(NovelInfo novelInfo);

    List<NovelInfo> getAllByOrder(String condition, String orderColumn, String orderDir, int startIndex, int pageSize);

    int deleteSafetyById(Long id);

    List<NovelInfo> getAllNovel(int pageNum, int pageSize);

    long getCountByCondition(String condition, NovelInfo novelInfo);
}
