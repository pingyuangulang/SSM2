package com.jibin.service;

import com.jibin.bean.Employee;
import com.jibin.bean.EmployeeExample;
import com.jibin.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    /**
     * 查询所有员工的所有信息
     * @return
     */
    public List<Employee> getAll(){
        return employeeMapper.selectByExampleWithDept(null);
    }
    
    /**
     * 新增员工信息
     * @param employee
     * @return
     */
    public int addEmployee(Employee employee){
        return employeeMapper.insertSelective(employee);
    }
    
    /**
     * 判断员工名是否可用,true代表可用，false代表不可用
     * @param empName
     * @return
     */
    public boolean checkEmpName(String empName){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count==0;
    }
    
    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    public Employee getEmployee(int id){
        return employeeMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 修改员工信息
     * @param employee
     * @return
     */
    public int updateEmployee(Employee employee){
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
    
    /**
     * 根据id删除员工信息
     * @param id
     * @return
     */
    public int deleteEmployee(int id){
        return employeeMapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除员工信息
     * @param ids
     * @return
     */
    public boolean batchDeleteEmployee(List<Integer> ids){
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from XXX where emp_id in (ids)
        criteria.andEmpIdIn(ids);
        int status = employeeMapper.deleteByExample(example);
        return status!=0;
    }
}
