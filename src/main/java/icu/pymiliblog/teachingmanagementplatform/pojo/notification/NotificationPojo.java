package icu.pymiliblog.teachingmanagementplatform.pojo.notification;

import java.util.Date;

/**
 * 通知 POJO
 * @author PYmili
 */
public class NotificationPojo {
    private Integer id;
    private String title;
    private String imgUrl;
    private String content;
    private Date createdAt;

    // 指定范围
    private Integer start;
    private Integer stop;

    public NotificationPojo() {}
    public NotificationPojo(Integer id, String title, String imgUrl, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "NotificationPojo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
