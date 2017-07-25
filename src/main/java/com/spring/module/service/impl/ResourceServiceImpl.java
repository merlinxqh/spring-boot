package com.spring.module.service.impl;

import com.spring.common.base.dao.CrudMapper;
import com.spring.common.base.service.impl.BaseCrudServiceImpl;
import com.spring.module.mapper.ResourceMapper;
import com.spring.module.service.ResourceService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author leo
 * @date 2017-04-08 15:07:43
 * @version 1.0.0
 * @copyright www.leo.com
 */
@Service("resourceService")
public class ResourceServiceImpl extends BaseCrudServiceImpl implements ResourceService {

    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public CrudMapper init() {
        return resourceMapper;
    }
}