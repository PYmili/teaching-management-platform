package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourceRequestPojo;
import icu.pymiliblog.teachingmanagementplatform.service.ResourceService;
import icu.pymiliblog.teachingmanagementplatform.util.ResourceTempCleanerUtil;
import icu.pymiliblog.teachingmanagementplatform.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceTempCleanerUtil resourceTempCleanerUtil;

    /**
     * 上传资源文件
     * @param requestPojo {@link ResourceRequestPojo}
     * @return {@link ResponseEntity}
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> upload(@ModelAttribute ResourceRequestPojo requestPojo) throws IOException {
        logger.info("url = /upload, requestPojo = {}", requestPojo);
        // 处理上传的资源数据
        MultipartFile multipartFile = requestPojo.getMultipartFile();
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(404, "文件内容为空"),
                    HttpStatus.NOT_FOUND);
        } else if (requestPojo.getFileName() == null ||
                requestPojo.getFileSize() == 0 ||
                requestPojo.getContentType().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(404, "请求参数残缺！"),
                    HttpStatus.NOT_FOUND);
        }
        return resourceService.upload(requestPojo);
    }

    /**
     * 通过`/find`查找指定资源
     * @param id {@link Long} 资源的id（可选）
     * @param name {@link String} 资源的文件名（可选）
     * @return {@link ResponseEntity<Object>}
     * @throws IOException
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<Object> find(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "file_name", required = false) String name) throws IOException {
        logger.info("url = /find, id = {}, file_name = {}", id, name);
        ResourcePojo resourcePojo = new ResourcePojo();
        // 请求数据验证
        if (id == null && (name == null || name.isEmpty())) {
            return new ResponseEntity<>(resourcePojo, HttpStatus.NOT_FOUND);
        }
        if (id != null && id == 0) resourcePojo.setFileName(name);
        if (name != null && !name.isEmpty()) resourcePojo.setId(id);
        // 数据库查询
        List<ResourcePojo> resultList = resourceService.findByPojo(resourcePojo);
        if (resultList.isEmpty()) return new ResponseEntity<>(
                ApiResponse.not_found("未找到此文件！"), HttpStatus.OK);
        // 通过ResourceUtil工具类将资源缓存
        File tempFile = ResourceUtil.createTempFile(resultList.getFirst());
        if (tempFile == null) return new ResponseEntity<>(
                ApiResponse.not_found("服务器错误！"), HttpStatus.NOT_FOUND);
        Resource resource = new FileSystemResource(tempFile);
        // 清理当前缓存资源
        resourceTempCleanerUtil.addFileForDeletion(tempFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resultList.getFirst().getFileName() + "\"")
                .contentType(MediaType.parseMediaType(resultList.getFirst().getContentType()))
                .body(resource);
    }
}
