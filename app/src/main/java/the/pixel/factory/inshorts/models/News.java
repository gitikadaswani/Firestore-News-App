package the.pixel.factory.inshorts.models;

/**
 * Created by dhruvesh on 1/1/18.
 */

public class News {
    private String title;
    private String description;
    private String url;
    private String image;
    private int likes;
    private String id;

    public String getTitle(){
        return title;
    }

    public  void setTitle(String title){
        this.title=title;
    }

    public String getDescription(){
        return description;
    }

    public  void setDescription(String description){
        this.description=description;
    }

    public String getLink(){
        return url;
    }

    public  void setLink(String link){
        this.url=link;
    }

    public String getImage(){
        return image;
    }

    public  void setImage(String image){
        this.image=image;
    }

    public int getLikes(){
        return likes;
    }

    public  void setLikes(int likes){
        this.likes=likes;
    }

    public String getId(){
        return id;
    }

    public  void setId(String id){
        this.id=id;
    }
}
