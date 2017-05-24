package org.cloud.meblog.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.meblog.model.Article;
import org.cloud.meblog.model.ArticleTagLink;
import org.cloud.meblog.utils.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectArticlesByTag(@Param("tagValue") String tagName);

    List<Article> selectArticlesByCategory(@Param("enName") String categoryName);

    List<Article> selectArticlesByCategoryId(@Param("id") Long categoryId);

    List<Article> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);

    List<Article> selectArticlesByReadCount();

    Article selectByWid(@Param("wid") Long wid);

    List<Article> selectPrevArticles(@Param("now") Date currentTime);

    List<Article> selectNextArticles(@Param("now") Date currentTime);

    List<Article> selectRelativeArticlesByTag(@Param("myid") Long myId);

    List<Article> selectRelativeArticlesByCategory(@Param("myid") Long myId);

    Long selectArticleCountByCategoryId(@Param("categoryId") Long categoryId);

    void updateReadCountById(Article article);

    void updateLiveCountById(Article article);

    List<Article> selectByTitleOrContent(String value);

    List<Article> selectArticlesByUser(@Param("id") Long id);
}
