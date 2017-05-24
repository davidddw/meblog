/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.meblog.controller.front;

import com.github.pagehelper.PageInfo;
import org.cloud.meblog.constant.CookieConstants;
import org.cloud.meblog.constant.UrlConstants;
import org.cloud.meblog.model.*;
import org.cloud.meblog.service.*;
import org.cloud.meblog.utils.CookieUtil;
import org.cloud.meblog.utils.PropertiesUtil;
import org.cloud.meblog.utils.QRCodeUtil;
import org.cloud.meblog.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/3/20.
 */
@Controller
public class NodeController extends FrontBaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IPageService pageService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private IGalleryService galleryService;

    @Autowired
    private INovelInfoService novelInfoService;

    @Autowired
    private INovelChapterService novelChapterService;

    /**
     * 主页列表显示文章，按照创建时间降序排列
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.HOMEPAGE)
    public ModelAndView homePageHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum) {
        ModelAndView result;
        if (1 == pageNum) {
            result = defaultFirstPage(new ModelAndView(UrlConstants.HOME_FTL), request);
        } else {
            result = defaultModelAndView(new ModelAndView(UrlConstants.HOME_FTL), request);
        }
        long count = articleService.getCount(new Article());
        result.addObject("count", count);
        return result;
    }

    /**
     * 查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETARTICLE)
    public @ResponseBody
    Map<String, Object> articleGetMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                         @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getAllByDateDesc(pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 按照Category列表显示文章
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.CATEGORIES)
    public ModelAndView articleListByCategoriesHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request) {
        List<Article> articleList = new ArrayList<>();
        List<Category> categories = categoryService.getAll();
        ModelAndView result;
        if (categories.isEmpty()) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            result = defaultModelAndView(new ModelAndView(UrlConstants.CATEGORIES_FTL), request);
            result.addObject("categories", categories);
            for (Category sub : categories) {
                articleList.addAll(articleService.getArticlesByCategory(sub));
            }
            result.addObject("count", articleList.size());
        }
        return result;
    }

    /**
     * 按照Category二级列表显示文章
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.CATEGORY)
    public ModelAndView articleListByCategory(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                              @PathVariable String categoryName,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        ModelAndView result;
        Category category = categoryService.getCategoryByName(categoryName);
        if (null == category) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            List<Article> articleList = articleService.getArticlesByCategory(new Category(categoryName, null), pageNum, pageSize);
            result = defaultModelAndView(new ModelAndView(UrlConstants.CATEGORY_FTL), request);
            result.addObject("count", new PageInfo<>(articleList).getTotal());
            result.addObject("category", category);
        }
        return result;
    }

    /**
     * 查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETCATEGORY)
    public @ResponseBody
    Map<String, Object> articleGetByCategoryMethod(@PathVariable String categoryName,
                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getArticlesByCategory(new Category(categoryName, null), pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 显示page页面
     *
     * @param options
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.PAGE)

    public ModelAndView page(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                             @PathVariable String pageTitle) {
        Page page = pageService.getBySlug(pageTitle);
        ModelAndView result;
        if (page == null) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            result = defaultModelAndView(new ModelAndView(UrlConstants.PAGE_FTL), request);
            result.addObject("page", page);
        }
        return result;
    }

    /**
     * 显示标签页面
     *
     * @param options
     * @return
     */
    @GetMapping(value = UrlConstants.BIAO)
    public ModelAndView biao(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request) {
        List<Tag> tagList = tagService.getAll();
        ModelAndView result = defaultModelAndView(new ModelAndView(UrlConstants.BIAO_FTL), request);
        result.addObject("tags", tagList);
        return result;
    }

    /**
     * 按照Tag列表显示文章
     *
     * @param options
     * @param request
     * @param tagName
     * @return
     */
    @GetMapping(UrlConstants.TAG)
    public ModelAndView articleListByTagHandler(@ModelAttribute("options") HashMap<String, String> options, HttpServletRequest request,
                                                @PathVariable String tagName,
                                                @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        List<Article> articleList = articleService.getArticlesByTag(new Tag(tagName), pageNum, pageSize);
        ModelAndView result;
        if (articleList.isEmpty()) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            result = defaultModelAndView(new ModelAndView(UrlConstants.TAG_FTL), request);
            result.addObject("count", new PageInfo<>(articleList).getTotal());
            result.addObject("tag", new Tag(tagName));
        }
        return result;
    }

    /**
     * 查询article
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETTAG)
    public @ResponseBody
    Map<String, Object> articleGetByTagMethod(@PathVariable String tagName,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Article> articleList = articleService.getArticlesByTag(new Tag(tagName), pageNum, pageSize);
        items.put("items", new PageInfo<>(articleList));
        return items;
    }

    /**
     * 显示详情页面
     *
     * @param options
     * @param articleId
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.ARTICLE)
    public ModelAndView articles(@ModelAttribute("options") HashMap<String, String> options,
                                 @PathVariable String articleId, HttpServletRequest request) {
        Long myArticleId = StringHelper.parseLongWithDefault(articleId, 0);
        Article article = articleService.getArticlesByWid(myArticleId);
        ModelAndView result;
        if (article == null) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            article.setReadCount(article.getReadCount() + 1);
            articleService.updateArticleReadCount(article);
            result = defaultModelAndView(new ModelAndView(UrlConstants.ARTICLE_FTL), request);
            result.addObject("article", article);
            result.addObject("relative_article", articleService.getRelativeArticlesByCategory(article));
        }
        return result;
    }

    /**
     * 点赞操作
     *
     * @param request
     * @param response
     * @param um_id
     * @return
     */
    @PostMapping(value = UrlConstants.PATH_LOVE)
    public
    @ResponseBody
    String love(HttpServletRequest request, HttpServletResponse response, @RequestParam String um_id) {
        Cookie cookie = CookieUtil.getCookieByName(request, CookieConstants.ARTICLEID + um_id);
        Article article = articleService.getArticlesByWid(Long.valueOf(um_id));
        // 判断cookie是否为空
        if (cookie == null) {
            article.setLikeCount(article.getLikeCount() + 1);
            // 数据库操作，点赞个数加
            articleService.love(article);
            CookieUtil.addCookie(response, CookieConstants.ARTICLEID + um_id, "", CookieConstants.MAXAGE);
        }
        return String.valueOf(article.getLikeCount());
    }

    @GetMapping(value = "/qrcode")
    public void qrcode(String content, @RequestParam(defaultValue = "256", required = false) int width,
                       @RequestParam(defaultValue = "256", required = false) int height, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/png");
        try {
            outputStream = response.getOutputStream();
            QRCodeUtil.writeToStream(content, outputStream, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示404页面
     *
     * @return
     * @throws Exception
     */
    @GetMapping(UrlConstants.ERROR404)
    public ModelAndView handleRequest() throws Exception {
        return new ModelAndView(UrlConstants.NOTFOUND_FTL);
    }

    /**
     * 显示画廊
     *
     * @param request
     * @param albumId
     * @return
     */
    @GetMapping(UrlConstants.ALBUM)
    public ModelAndView gallery(HttpServletRequest request, @PathVariable String albumId) {
        Album album = albumService.getById(Long.parseLong(albumId));
        ModelAndView result;
        if (album == null) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            List<Gallery> galleries = galleryService.getGalleryByAlbum(album);
            result = defaultModelAndView(new ModelAndView(UrlConstants.ALBUM_FTL), request);
            result.addObject("galleries", galleries);
            result.addObject("album", album);
        }
        return result;
    }

    /**
     * 显示Albumset画廊
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.ALBUMS)
    public ModelAndView galleriesListHandler(HttpServletRequest request,
                                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                             @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<Album> albums = albumService.getAllAlbum(pageNum, pageSize);
        ModelAndView result;
        if (albums.isEmpty()) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            result = defaultModelAndView(new ModelAndView(UrlConstants.THEME + UrlConstants.ALBUMS), request);
            result.addObject("count", new PageInfo<>(albums).getTotal());

        }
        return result;
    }

    /**
     * 查询album
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETALBUMS)
    public @ResponseBody
    Map<String, Object> albumGetByTagMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                            @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<Album> albums = albumService.getAllAlbum(pageNum, pageSize);
        for (Album album : albums) {
            List<Gallery> galleries = galleryService.getGalleryByAlbum(album);
            if (galleries.size() > 0) {
                album.setCoverpic(galleries.get(0).getThumbRelativePath());
            }
        }
        items.put("items", new PageInfo<>(albums));
        return items;
    }

    /**
     * 显示appdownload
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.APPDOWEN)
    public ModelAndView appDownload(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.APPDOWEN_FTL), request);
    }

    /**
     * 显示novels
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVEL)
    public ModelAndView novelListHandler(HttpServletRequest request,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                         @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        List<NovelInfo> novelInfoList = novelInfoService.getAllNovel(pageNum, pageSize);
        if (novelInfoList.isEmpty()) {
            return defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            ModelAndView result = defaultModelAndView(new ModelAndView(UrlConstants.NOVEL_FTL), request);
            result.addObject("count", new PageInfo<>(novelInfoList).getTotal());
            return result;
        }
    }

    /**
     * 查询album
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETNOVEL)
    public @ResponseBody
    Map<String, Object> novelGetByTagMethod(@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                            @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> items = new HashMap<>();
        List<NovelInfo> novelInfoList = novelInfoService.getAllNovel(pageNum, pageSize);
        items.put("items", new PageInfo<>(novelInfoList));
        return items;
    }

    /**
     * 显示novelchanpter
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVELCHAPTER)
    public ModelAndView novelChanpterlistHandler(HttpServletRequest request, @PathVariable String novelId) {
        ModelAndView result;
        List<NovelChapter> lastNovelChapter = novelChapterService.getLastChaptersByNovelId(Long.parseLong(novelId));
        if (lastNovelChapter.isEmpty()) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            NovelInfo novelInfo = novelInfoService.getById(Long.parseLong(novelId));
            long count = novelChapterService.getCountByNovelId(Long.parseLong(novelId));
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOVEL_CHPTER_FTL), request);
            result.addObject("novelId", novelId);
            result.addObject("novelInfo", novelInfo);
            result.addObject("novelCount", count);
            result.addObject("lastnovelchapters", lastNovelChapter);
        }
        return result;
    }

    /**
     * 查询
     *
     * @return json
     */
    @GetMapping(UrlConstants.GETNOVELCHAPTER)
    public @ResponseBody
    Map<String, Object> novelChapterGetMethod(@PathVariable String novelId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
                                              @RequestParam(value = "size", required = false, defaultValue = "50") int pageSize) {
        Map<String, Object> novelChapters = new HashMap<>();
        List<NovelChapter> novelChapter = novelChapterService.getChaptersByNovelId(Long.parseLong(novelId), pageNum, pageSize);
        novelChapters.put("items", new PageInfo<>(novelChapter));
        return novelChapters;
    }

    /**
     * 显示noveldetails
     *
     * @param request
     * @return
     */
    @GetMapping(UrlConstants.NOVELDETAIL)
    public ModelAndView novelDetailHandler(HttpServletRequest request, @PathVariable String novelId, @PathVariable String chapterId) {
        NovelChapter novelDetails = novelChapterService.getDetailsByNovelAndChapter(Long.parseLong(novelId), Long.parseLong(chapterId));
        ModelAndView result;
        if (novelDetails == null) {
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOTFOUND_FTL), request);
        } else {
            String content = StringHelper.getContentFromTxt(PropertiesUtil.getConfigBykey("uploadPath") + "/" + novelDetails.getLocation());
            novelDetails.setLocation(content != null ? content : "");
            result = defaultModelAndView(new ModelAndView(UrlConstants.NOVELDETAIL_FTL), request);
            result.addObject("noveldetails", novelDetails);
            Map<String, NovelChapter> previousNext = novelChapterService.getPrevAndNextNovel(Long.parseLong(novelId), Long.parseLong(chapterId));
            String prevUrl, nextUrl;
            if (previousNext.get("prev_novel") == null) {
                prevUrl = novelDetails.getNovel().getId().toString();
            } else {
                prevUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("prev_novel").getWid());
            }
            result.addObject("prevurl", prevUrl);
            if (previousNext.get("next_novel") == null) {
                nextUrl = novelDetails.getNovel().getId().toString();
            } else {
                nextUrl = String.format("%s/%s", novelDetails.getNovel().getId(), previousNext.get("next_novel").getWid());
            }
            result.addObject("nexturl", nextUrl);
        }
        return result;
    }
}
