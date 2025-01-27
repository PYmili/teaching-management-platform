package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.ResourceMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourceRequestPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private ResourceMapper resourceMapper;

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
        logger.info(resourcePojo.toString());
        // 使用mapper上传
        boolean result = resourceMapper.upload(resourcePojo);
        if (!result) {
            logger.warn("上传资源： {} 失败！", requestPojo.getFileName());
            return new ResponseEntity<>(
                    ApiResponse.not_found("上传资源失败！"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.ok(resourcePojo.getId()), HttpStatus.NOT_FOUND);
    }

    /**
     * 通过`Pojo`对象模型查询资源
     * @param resourcePojo {@link ResourcePojo}
     * @return {@link List}
     */
    public List<ResourcePojo> findByPojo(ResourcePojo resourcePojo) {
        logger.info(resourcePojo.toString());
        return resourceMapper.findByPojo(resourcePojo);
    }
}
