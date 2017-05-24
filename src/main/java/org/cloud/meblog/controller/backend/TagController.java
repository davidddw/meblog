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
import org.cloud.meblog.model.Result;
import org.cloud.meblog.model.Tag;
import org.cloud.meblog.service.IArticleService;
import org.cloud.meblog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 * Created by d05660ddw on 2017/4/1.
 */
@RestController("tagController")
@RequestMapping(UrlConstants.ADMIN)
public class TagController extends AdminBaseController {

    @Autowired
    private ITagService tagService;

    @Autowired
    private IArticleService articleService;

    /**
     * 新增
     */
    @PostMapping("/tag")
    public @ResponseBody
    Result addTag(@RequestBody Tag tag) {
        long number = tagService.saveOrUpdateTag(tag);
        return new Result(String.valueOf(number));
    }

    /**
     * 更新
     */
    @PutMapping("/tag/{id}")
    public @ResponseBody
    Result updateTag(@PathVariable String id, @RequestBody Tag tag) {
        tag.setId(Long.parseLong(id));
        long number = tagService.saveOrUpdateTag(tag);
        return new Result(String.valueOf(number));
    }

    /**
     * 查询
     */
    @GetMapping("/tag")
    public @ResponseBody
    Map<String, Object> listTag(@RequestParam String draw,
                                @RequestParam int startIndex,
                                @RequestParam int pageSize,
                                @RequestParam(value = "orderColumn", required = false, defaultValue = "count") String orderColumn,
                                @RequestParam(value = "orderDir", required = false, defaultValue = "desc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        tagService.setTagCount();
        info.put("pageData", tagService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", tagService.getCount(new Tag()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 删除
     */
    @DeleteMapping("/tag/{id}")
    public @ResponseBody
    Result removeTag(@PathVariable String id) {
        Tag tag = tagService.getById(Long.parseLong(id));
        List<Article> articleList = articleService.getArticlesByTag(tag);
        for(Article article : articleList) {
            String finalString = subString(article.getTagStrings(), tag.getName());
            article.setTagStrings(finalString);
            articleService.save(article);
        }
        return new Result(String.valueOf(tagService.deleteSafetyById(tag.getId())));
    }

    private String subString(String tagStrings, String name) {
        String[] str = tagStrings.split(",");
        List<String> list = Arrays.asList(str);
        list.remove(list.indexOf(name));
        return String.join(",", list.toArray(new String[1]));
    }
}
