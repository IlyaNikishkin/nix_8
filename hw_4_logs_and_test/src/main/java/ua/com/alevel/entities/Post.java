package ua.com.alevel.entities;

public class Post extends BaseEntity {

    private String text;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + super.getId() + '\'' +
                ", text=" + text +
                ", user=" + userId +
                '}';
    }
}
