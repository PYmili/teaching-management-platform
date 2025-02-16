package icu.pymiliblog.teachingmanagementplatform.util;

import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * 资源工具类
 * @author PYmili
 */
@Component
public class ResourceUtil {

    /**
     * 创建文件到系统缓存文件夹
     * @param resourcePojo {@link ResourcePojo}
     * @return {@link File}
     * @throws IOException
     */
    public static File createTempFile(ResourcePojo resourcePojo) throws IOException {
        Path tempFilePath = Files.createTempFile(UUID.randomUUID().toString(),
                "." + getExtension(resourcePojo.getContentType()));
        File tempFile = tempFilePath.toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(resourcePojo.getData());
        } catch (Exception e) {
            return null;
        }
        return tempFile;
    }

    /**
     * 根据内容类型返回文件扩展名
     * @param contentType {@link String}
     * @return {@link String}
     */
    public static String getExtension(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "application/pdf" -> "pdf";
            default -> "dat"; // 默认扩展名
        };
    }
}
