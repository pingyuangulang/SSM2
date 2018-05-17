package com.jibin.test;

import com.jibin.bean.Department;
import com.jibin.bean.Employee;
import com.jibin.dao.DepartmentMapper;
import com.jibin.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层
 * 推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1、导入SpringTest模块
 * 2、@ContextConfiguration指定Spring配置文件的位置
 * 3、直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    SqlSession sqlSession;
    @Test
    public void employeeMapperTest(){
//        employeeMapper.insertSelective(new Employee(null, "jibin", "M", "jibin_0310@163.com", 1));
//        System.out.println("插入成功！！！");
//        批量插入
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        String sex = "M";
        for(int i = 1;i<1000;i++){
            if(i%2==0) {
                sex = "M";
            }
            else {
                sex = "F";
            }
            String name = UUID.randomUUID().toString().substring(0, 5);
            mapper.insertSelective(new Employee(null, name, sex, name+"@jibin.com", (i%5)+1));
        }
        System.out.println("批量插入已完成！！！");
    }
    @Test
    public void departmentMapperTest(){
        //插入几个部门
//        departmentMapper.insertSelective(new Department(null, "开发部"));
        departmentMapper.insertSelective(new Department(null, "市场部"));
//        departmentMapper.insertSelective(new Department(null, "产品部"));
        System.out.println("插入成功！！！");
    }
}
