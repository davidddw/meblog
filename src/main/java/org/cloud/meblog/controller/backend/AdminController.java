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

package org.cloud.meblog.controller.backend;

import org.cloud.meblog.constant.UrlConstants;
import org.cloud.meblog.model.Article;
import org.cloud.meblog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by d05660ddw on 2017/3/25.
 */
@Controller("adminController")
@RequestMapping(UrlConstants.ADMIN)
public class AdminController extends AdminBaseController {

    @Autowired
    private IArticleService articleService;

    @GetMapping
    public ModelAndView homePageHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMIN_FTL), request);
    }

    @GetMapping(UrlConstants.WRITEARTICLE)
    public ModelAndView newArticleHandler(HttpServletRequest request,
                                          @RequestParam(value = "id", required = false) String id) {
        ModelAndView result = defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.WRITEARTICLE), request);
        if (null != id) {
            Article article = articleService.getById(Long.parseLong(id));
            result.addObject("article", article);
        }
        return result;
    }

    @GetMapping(UrlConstants.LISTARTICLE)
    public ModelAndView listArticleHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTARTICLE), request);
    }

    @GetMapping(UrlConstants.LISTCATEGORY)
    public ModelAndView listCategoryHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTCATEGORY), request);
    }

    @GetMapping(UrlConstants.LISTTAG)
    public ModelAndView listTagHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTTAG), request);
    }

    @GetMapping(UrlConstants.LISTPAGE)
    public ModelAndView listPageHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTPAGE), request);
    }

    @GetMapping(UrlConstants.LISTGALLERY)
    public ModelAndView listGalleryHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTGALLERY), request);
    }

    @GetMapping(UrlConstants.LISTNOVEL)
    public ModelAndView listNovelHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTNOVEL), request);
    }

    @GetMapping(UrlConstants.LISTNOVELCHAPTER)
    public ModelAndView listNovelChapterHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTNOVELCHAPTER), request);
    }

    @GetMapping(UrlConstants.LISTCOMMENT)
    public ModelAndView listCommentHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.DEVELOPING), request);
    }

    @GetMapping(UrlConstants.LISTUSER)
    public ModelAndView listUserHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTUSER), request);
    }

    @GetMapping(UrlConstants.LISTSETTINGS)
    public ModelAndView listSettingsHandler(HttpServletRequest request) {
        return defaultModelAndView(new ModelAndView(UrlConstants.ADMINTHEME + UrlConstants.LISTSETTINGS), request);
    }
}
