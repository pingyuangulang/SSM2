package com.jibin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jibin.bean.Employee;
import com.jibin.bean.Msg;
import com.jibin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工CRUD请求
 * 前端校验加后端校验提高安全性
 *   1、支持JSR303校验
 *   2、导入Hibernate-Validator的jar包
 * @author 吉彬
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    /**
     * 查询员工数据（分页查询）
     * @param pageNumber
     * @param model
     * @return
     */
    @RequestMapping("/employee")
    public String getEmployees(@RequestParam(value = "pageNumber", defaultValue = "1")Integer pageNumber, Model model){
        PageHelper.startPage(pageNumber, 8);
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps,5);
        model.addAttribute("pageInfo", page);
        return "list";
    }
    
    /**
     * 添加员工
     * @param employee
     * @param result
     * @return
     */
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    @ResponseBody
    public Msg addEmployee(@Valid Employee employee, BindingResult result){
        Msg msg = null;
        if(result.hasErrors()){
            Map<String, String> map = new HashMap<String, String>();
            List<FieldError> list = result.getFieldErrors();
            for (FieldError fieldError : list) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            msg = Msg.fail().add("errorFields", map);
        }else {
            int status = employeeService.addEmployee(employee);
            if(status != 0){
                msg = Msg.success();
            }
            else {
                msg = Msg.fail();
            }
        }
        return msg;
    }
    
    /**
     * 判断员工名是否可用，true代表可用，false代表不可用
     * @param empName
     * @return
     */
    @RequestMapping("/checkEmpName")
    @ResponseBody
    public Msg checkEmpName(@RequestParam("empName") String empName){
        //后端对用户名进行校验
        String regexName = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regexName)){  //用户名校验失败
            return Msg.fail().add("nameMsg", "请输入2-5位中文或4-16位英文数字组合");
        }
        boolean flag = employeeService.checkEmpName(empName);
        if(flag){
            return Msg.success().add("nameMsg", "用户名可用");
        }else {
            return Msg.fail().add("nameMsg", "用户名已存在");
        }
    }
    
    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEmployee")
    public Msg getEmployee(@RequestParam("id") int id){
        Employee employee = employeeService.getEmployee(id);
        return Msg.success().add("emp", employee);
    }
    
    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateEmployee")
    public Msg updateEmployee(Employee employee){
        int status = employeeService.updateEmployee(employee);
        if (status != 0){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
    
    /**
     * 根据id删除员工信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteEmployee")
    @ResponseBody
    public Msg deleteEmployee(int id){
        int status = employeeService.deleteEmployee(id);
        if (status != 0){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
    
    /**
     * 根据id批量删除员工信息
     * @param idString
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchDeleteEmployee")
    public Msg batchDeleteEmployee(@RequestParam("empIdString") String idString){
        List<Integer> ids = new ArrayList<Integer>();
        String[] idArray = idString.split("-");
        //将要批量删除的员工id封装到List<Integer> ids中
        for (String id : idArray) {
            ids.add(Integer.parseInt(id));
        }
        boolean flag = employeeService.batchDeleteEmployee(ids);
        if (flag){
            return Msg.success();
        }else {
            return Msg.fail();
        }
    }
}
