
package com.example.newsapp.model.data;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopHeadlines {

    @SerializedName("totalArticles")
    @Expose
    private Integer totalArticles;
    @SerializedName("articles")
    @Expose
    private List<Article> articles;

    public Integer getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(Integer totalArticles) {
        this.totalArticles = totalArticles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
