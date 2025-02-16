package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.ResourceMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourceRequestPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 资源 Service
 * @author PYmili
 */
@Slf4j
@Service
public class ResourceService {

    // 操作资源的mapper
    private final ResourceMapper resourceMapper;

    public ResourceService(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    /**
     * 上传资源
     * @param requestPojo {@link ResourcePojo}
     * @return {@link ResponseEntity}
     * @throws IOException
     */
    public ResponseEntity<ApiResponse<Object>> upload(ResourceRequestPojo requestPojo) throws IOException {
        ResourcePojo resourcePojo = new ResourcePojo();
        resourcePojo.setFileName(requestPojo.getFileName());
        resourcePojo.setContentType(requestPojo.getContentType());
        resourcePojo.setFileSize(requestPojo.getFileSize());
        resourcePojo.setData(requestPojo.getMultipartFile().getBytes());
        log.info(resourcePojo.toString());
        // 使用mapper上传
        boolean result = resourceMapper.upload(resourcePojo);
        if (!result) {
            log.warn("上传资源： {} 失败！", requestPojo.getFileName());
            return ApiResponse.not_found("上传资源失败！");
        }
        return ApiResponse.ok(resourcePojo.getId());
    }

    /**
     * 通过{@link ResourcePojo}查询资源
     * @param resourcePojo {@link ResourcePojo}
     * @return {@link List}
     */
    public List<ResourcePojo> findByPojo(ResourcePojo resourcePojo) {
        log.info(resourcePojo.toString());
        return resourceMapper.findByPojo(resourcePojo);
    }
}
