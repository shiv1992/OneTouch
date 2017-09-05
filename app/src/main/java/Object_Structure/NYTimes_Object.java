package Object_Structure;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by SHIVVVV on 4/30/2017.
 */

public class NYTimes_Object  {

    private String Title;
    private String Thumbnail_URL;
    private Bitmap Bitmap_Image;
    private Bitmap Bitmap_Background;
    private String Content;
    private String Link;
    private String Author;

    public Bitmap getBitmap_Background() {
        return Bitmap_Background;
    }

    public void setBitmap_Background(Bitmap bitmap_Background) {
        Bitmap_Background = bitmap_Background;
    }

    public Bitmap getBitmap_Image() {
        return Bitmap_Image;
    }

    public void setBitmap_Image(Bitmap bitmap_Image) {
        Bitmap_Image = bitmap_Image;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail_URL() {
        return Thumbnail_URL;
    }

    public String getContent() {
        return Content;
    }

    public String getLink() {
        return Link;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setThumbnail_URL(String thumbnail_URL) {
        Thumbnail_URL = thumbnail_URL;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setLink(String link) {
        Link = link;
    }







}
