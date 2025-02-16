package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源 mapper
 * @author PYmili
 */
@Mapper
public interface ResourceMapper {
    /**
     * 上传资源
     * @param resourcePojo {@link ResourcePojo}
     * @return boolean
     */
    boolean upload(ResourcePojo resourcePojo);

    /**
     * 通过{@link ResourcePojo}查找
     * @param resourcePojo {@link  ResourcePojo}
     * @return {@link List}
     */
    List<ResourcePojo> findByPojo(ResourcePojo resourcePojo);
}
