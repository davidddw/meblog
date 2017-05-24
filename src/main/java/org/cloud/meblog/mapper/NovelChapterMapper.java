package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.NovelChapter;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface NovelChapterMapper extends BaseMapper<NovelChapter> {
    List<NovelChapter> selectAllOrderBy(@Param("condition") String condition, @Param("column") String orderColumn, @Param("orderDir") String orderDir);

    Long selectCountByNovelId(@Param("id") Long id);

    List<NovelChapter> selectChaptersByNovelId(@Param("id") Long novelId);

    NovelChapter selectDetailsByChaptersAndNovelId(@Param("novelid") Long novelId, @Param("chapterid") Long chapterId);

    List<NovelChapter> selectPrevNovels(@Param("novelid") Long novelId, @Param("chapterid") Long chapterId);

    List<NovelChapter> selectNextNovels(@Param("novelid") Long novelId, @Param("chapterid") Long chapterId);

    Long insertByBatch(List<NovelChapter> novelChapters);

    Long deleteByBatch(List<Long> novelChaptersIds);

    List<NovelChapter> selectAllByNovelIdOrderBy(@Param("id") Long novelId, @Param("column") String orderColumn, @Param("orderDir") String orderDir);

    long selectCountByCondition(@Param("condition") String condition, NovelChapter novelChapter);
}
