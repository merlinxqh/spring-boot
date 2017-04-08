package com.spring.service.impl;

import com.spring.base.dao.CrudMapper;
import com.spring.base.service.impl.BaseCrudServiceImpl;
import com.spring.dao.mapper.ResourceMapper;
import com.spring.service.ResourceService;
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