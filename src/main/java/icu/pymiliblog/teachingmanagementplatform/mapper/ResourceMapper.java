package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.resource.ResourcePojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {
    boolean upload(ResourcePojo resourcePojo);
    List<ResourcePojo> findByPojo(ResourcePojo resourcePojo);
}
