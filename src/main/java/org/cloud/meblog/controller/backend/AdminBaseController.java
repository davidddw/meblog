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

import org.cloud.meblog.controller.BaseController;
import org.cloud.meblog.model.Category;
import org.cloud.meblog.model.NovelInfo;
import org.cloud.meblog.model.Tag;
import org.cloud.meblog.model.User;
import org.cloud.meblog.service.*;
import org.cloud.meblog.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by d05660ddw on 2017/4/8.
 */
public class AdminBaseController extends BaseController {

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private INovelInfoService novelInfoService;

    @ModelAttribute("loginName")
    public String getLoginName() {
        return securityService.findLoggedInUsername();
    }

    @Override
    protected ModelAndView defaultModelAndView(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.addObject("contextPath", request.getRequestURL());
        return modelAndView;
    }

    @ModelAttribute("novels")
    public List<NovelInfo> getNovels() {
        return novelInfoService.getAll();
    }

    protected String getUploadPath() {
        return PropertiesUtil.getConfigBykey("uploadPath");
    }

}
