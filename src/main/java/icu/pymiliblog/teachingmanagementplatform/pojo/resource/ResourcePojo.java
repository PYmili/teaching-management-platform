package icu.pymiliblog.teachingmanagementplatform.pojo.resource;

import java.util.Arrays;
import java.util.Date;

/**
 * 资源 POJO
 * @author PYmili
 */
public class ResourcePojo {
    private Long id;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private byte[] data;
    private Date updatedAt;
    private Date createdAt;

    public ResourcePojo() {}

    public ResourcePojo(String fileName, String contentType, Long fileSize, byte[] data) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.data = data;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ResourcePojo{" +
                "id=" + id +
                ", name='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", data=" + !Arrays.toString(data).isEmpty() +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}