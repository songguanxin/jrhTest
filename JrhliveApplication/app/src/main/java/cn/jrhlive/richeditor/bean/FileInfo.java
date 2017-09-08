package cn.jrhlive.richeditor.bean;

/**
 * desc:
 * Created by jiarh on 17/9/7 16:34.
 */

public class FileInfo {

    String name;
    String path;
    String author;

    public FileInfo(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
