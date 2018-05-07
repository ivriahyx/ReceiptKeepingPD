package c346.rp.edu.sg.receiptkeepingdatabase;

/**
 * Created by 16023018 on 17/12/2017.
 */

public class Comment {
    long id;
    String content;
    String date;
    Double priority;
    String category;
    String image;


    public Comment(long id, String content, Double priority, String date, String category, String image) {
        this.id = id;
        this.content = content;
        this.priority = priority;
        this.date = date;
        this.category = category;
        this.image = image;

    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "" + content + "\nCost: $" + priority + "\nDate: " + date + "\nCategory: " + category  ;
    }
}
