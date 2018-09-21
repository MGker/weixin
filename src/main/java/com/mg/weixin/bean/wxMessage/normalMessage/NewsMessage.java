package com.mg.weixin.bean.wxMessage.normalMessage;

import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/17 14:18
 * @Description:
 */
public class NewsMessage extends BaseMessage {
    private int ArticleCount;
    private List<Article> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticle() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}

