package icu.pymiliblog.teachingmanagementplatform.pojo.resource;

import org.springframework.web.multipart.MultipartFile;

public class ResourceRequestPojo {
    private String fileName;
    private String contentType;
    private Long fileSize;
    private MultipartFile multipartFile;

    public ResourceRequestPojo() {}

    public ResourceRequestPojo(String fileName, String contentType, Long fileSize, MultipartFile data) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.multipartFile = data;
    }

    // get/set

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

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
