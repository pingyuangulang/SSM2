package com.jibin.service;

import com.jibin.bean.Department;
import com.jibin.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 获取所有部门信息
     */
    public List<Department> getAll(){
        return departmentMapper.selectByExample(null);
    }
    
    
}
