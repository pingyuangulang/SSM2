package com.jibin.test;

import com.github.pagehelper.PageInfo;
import com.jibin.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 使用spring提供的测试请求功能
 * @author 吉彬
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:springmvc.xml"})
public class MVCTest {
//    传入springmvc的ioc
    @Autowired
    WebApplicationContext context;
//    虚拟MVC请求，获取到处理结果
    MockMvc mockMvc;
//    初始化MockMvc
    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void pageTest()throws Exception{
//        模拟请求拿到返回值
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee").param("pageNumber","5")).andReturn();
//        请求成功后，请求域中会有pageInfo，我们可以取出pageInfo进行验证
        MockHttpServletRequest request = mvcResult.getRequest();
        PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
        System.out.println("当前页码："+pageInfo.getPageNum());
        System.out.println("总页码："+pageInfo.getPages());
        System.out.println("总记录数："+pageInfo.getTotal());
        System.out.print("连续显示的页码：");
        int[] array = pageInfo.getNavigatepageNums();
        for (int i : array) {
            System.out.print(" "+i);
        }
        System.out.println();
//        获取员工信息
        List<Employee> list = pageInfo.getList();
        for (Employee e:list) {
            System.out.println(e.toString());
        }
    }
}
