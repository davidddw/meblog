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

package org.cloud.meblog.controller;

import org.cloud.meblog.model.Category;
import org.cloud.meblog.model.Page;
import org.cloud.meblog.model.Tag;
import org.cloud.meblog.model.User;
import org.cloud.meblog.service.*;
import org.cloud.meblog.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.cloud.meblog.constant.GlobalContants.SETTINGS;

/**
 * Created by d05660ddw on 2017/4/8.
 */
public abstract class BaseController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IPageService pageService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IUserService userService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getAll();
    }

    @ModelAttribute("pages")
    public List<Page> getPages() {
        return pageService.getNameAndSlug();
    }

    @ModelAttribute("tags")
    public List<Tag> getTags() {
        return tagService.getTop10();
    }

    @ModelAttribute("users")
    public List<User> getUsers() {
        return userService.getAll();
    }

    protected abstract ModelAndView defaultModelAndView(ModelAndView modelAndView, HttpServletRequest request);

    @ModelAttribute("basePath")
    public String getBasePath() {
        return PropertiesUtil.propertiesToMap(SETTINGS).get("urlPath");
    }

    @ModelAttribute("uploadPath")
    public String getUploadRoot() {
        return PropertiesUtil.propertiesToMap(SETTINGS).get("uploadPath");
    }

    @ModelAttribute("options")
    public Map<String, String> getOptions() {
        return PropertiesUtil.propertiesToMap(SETTINGS);
    }
}
