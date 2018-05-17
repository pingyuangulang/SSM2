package com.jibin.dao;

import com.jibin.bean.Employee;
import com.jibin.bean.EmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    
    /**
     * 获取总记录数
     * @param example
     * @return
     */
    long countByExample(EmployeeExample example);
    
    /**
     * 删除员工信息
     * @param example
     * @return
     */
    int deleteByExample(EmployeeExample example);
    
    /**
     * 通过id删除员工信息
     * @param empId
     * @return
     */
    int deleteByPrimaryKey(Integer empId);
    
    /**
     * 添加员工信息
     * @param record
     * @return
     */
    int insert(Employee record);
    
    /**
     * 有选择的添加员工的某些信息
     * @param record
     * @return
     */
    int insertSelective(Employee record);
    
    /**
     * 查询员工信息
     * @param example
     * @return
     */
    List<Employee> selectByExample(EmployeeExample example);
    
    /**
     * 根据id查询员工信息
     * @param empId
     * @return
     */
    Employee selectByPrimaryKey(Integer empId);
    
    /**
     * 根据id查询员工信息同时附带部门信息
     * @param empId
     * @return
     */
    Employee selectByPrimaryKeyWithDept(Integer empId);
    
    /**
     * 查询员工信息同时附带部门信息
     * @param example
     * @return
     */
    List<Employee> selectByExampleWithDept(EmployeeExample example);
    
    /**
     * 有选择的修改员工的某些信息
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);
    
    /**
     * 修改员工信息
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);
    
    /**
     * 通过id有选择的修改员工的某些信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employee record);
    
    /**
     * 通过id修改员工信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employee record);
}