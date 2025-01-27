package icu.pymiliblog.teachingmanagementplatform.util;

import icu.pymiliblog.teachingmanagementplatform.controller.ResourceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ResourceTempCleanerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private final ConcurrentLinkedQueue<File> tempFiles = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public ResourceTempCleanerUtil() {
        // 每隔60秒运行一次清理任务
        scheduler.scheduleAtFixedRate(this::cleanUp, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * 将文件添加到待清理队列。
     * @param file {@link File}
     */
    public void addFileForDeletion(File file) {
        if (file != null && !tempFiles.contains(file)) {
            tempFiles.add(file);
        }
    }

    /**
     * 定时清理任务，尝试删除所有队列中的文件
     */
    private void cleanUp() {
        File file;
        while ((file = tempFiles.poll()) != null) {
            try {
                if (!file.delete()) {
                    logger.warn("无法删除临时文件: {}", file.getAbsolutePath());
                } else {
                    logger.info("文件已被删除: {}", file.getAbsolutePath());
                }
            } catch (Exception e) {
                logger.error("删除临时文件时出错: {}", file.getAbsolutePath(), e);
            }
        }
    }
}
