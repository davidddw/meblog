package org.cloud.meblog.mapper;

import org.cloud.meblog.model.ArticleTagLink;
import org.cloud.meblog.model.EntityCount;
import org.cloud.meblog.utils.BaseMapper;

import java.util.List;

/**
 * Created by d05660ddw on 2017/3/6.
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTagLink> {

    Long saveArticleTagRelativity(ArticleTagLink articleTagLink);

    int deleteFromTag(Long id);

    int deleteFromArticle(Long id);

    List<EntityCount> selectArticlesCountByTag();
}
