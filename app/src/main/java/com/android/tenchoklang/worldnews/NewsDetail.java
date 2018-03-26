package com.android.tenchoklang.worldnews;

/**
 * Created by tench on 1/25/2018.
 * POJO
 */

class NewsDetail {
    String id;
    String outlet;
    String author;
    String title;
    String description;
    String url;
    String urlToImage;
    String datePublished;

    public NewsDetail(String id, String outlet, String author, String title, String description, String url, String urlToImage, String datePublished) {
        this.id = id;
        this.outlet = outlet;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.datePublished = datePublished;
    }

    public String getId() { return id;  }
    public void setId(String id) { this.id = id;  }

    public String getOutlet() { return outlet;  }
    public void setOutlet(String outlet) { this.outlet = outlet; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getTitle() { return title;   }
    public void setTitle(String title) {   this.title = title;  }

    public String getDescription() { return description;  }
    public void setDescription(String description) {  this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url;}

    public String getUrlToImage() { return urlToImage;}
    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage;}

    public String getDatePublished() { return datePublished;}
    public void setDatePublished(String datePublished) { this.datePublished = datePublished; }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "id='" + id + '\'' +
                ", outlet='" + outlet + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", datePublished='" + datePublished + '\'' +
                '}';
    }
}
