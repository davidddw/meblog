package org.cloud.meblog.service;

import org.cloud.meblog.dto.ArticleData;
import org.cloud.meblog.model.Article;
import org.cloud.meblog.model.Category;
import org.cloud.meblog.model.Tag;
import org.cloud.meblog.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface IArticleService extends IService<Article> {

    List<Article> getAllByDateDesc(int pageNum, int pageSize);

    List<Article> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);

    List<Article> getAllByTitleOrContent(String value, int pageNum, int pageSize);

    List<Article> getTopByDate();

    List<Article> getTopByReadCount();

    List<Article> getArticlesByCategory(Category category, int pageNum, int pageSize);

    List<Article> getArticlesByCategory(Category category);

    Article getArticlesByWid(long wid);

    List<Article> getArticlesByTag(Tag tag, int pageNum, int pageSize);

    List<Article> getArticlesByTag(Tag tag);

    Map<String, Article> getPrevAndNextArticle(Article article);

    List<Map<String, Object>> getRelativeArticlesByTag(Article article);

    List<Article> getRelativeArticlesByCategory(Article article);

    void updateArticleReadCount(Article article);

    List<Article> getTopByComment();

    void love(Article article);

    long getArticleCountByCategory(Category category);

    long saveOrUpdateArticleFromData(ArticleData articleData);

    int deleteSafetyById(Long id);

    List<Article> getarticlesByUser(User user);
}
