<%--
  Created by IntelliJ IDEA.
  User: 吉彬
  Date: 2018/5/12
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>员工列表</title>
    <%
        pageContext.setAttribute("path",request.getContextPath());
    %>
    <!-- web路径：
        不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
        以/开始的相对路径，找资源，以服务器的路径为标准(http://localhost:8080)；需要加上项目名
		http://localhost:8080/ssm
 -->
    <link href="${path}\static\bootstrap-3.3.7-dist\css\bootstrap.min.css" rel="stylesheet">
    <script src="${path}\static\js\jquery.js" type="text/javascript"></script>
    <script src="${path}\static\bootstrap-3.3.7-dist\js\bootstrap.min.js" type="text/javascript"></script>
    <script src="${path}\static\js\employee.js" type="text/javascript"></script>
    <script type="text/javascript">
        var path = '${path}';
    </script>
</head>
<body>

<!-- ===================================  编辑员工模态框  =================================== -->
<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">员工信息修改</h4>
            </div>
            <div class="modal-body">
                <%-- 表单 --%>
                <form class="form-horizontal">
                    <%-- 员工姓名 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">EmpName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="updateEmpName">email@example.com</p>
                        </div>
                    </div>
                    <%-- 员工邮箱 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input type="text" name="email" class="form-control" id="updateEmpEmail">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <%-- 员工性别 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="updateEmpGender1" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="updateEmpGender2" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <%-- 员工部门名称 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Department</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="dId">
                            
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="updateEmpBtn" class="btn btn-primary updateEmpBtn">更新</button>
                <button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- ===================================  编辑员工模态框  =================================== -->


<!-- =====================================  新增员工模态框  ===================================== -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
            </div>
            <div class="modal-body">
                <%-- 表单 --%>
                <form class="form-horizontal">
                    <%-- 员工姓名 --%>
                    <div class="form-group">
                        <label for="addEmpName" class="col-sm-2 control-label">EmpName</label>
                        <div class="col-sm-10">
                            <input type="text" name="empName" class="form-control" id="addEmpName" placeholder="EmpName" onchange="empNameChange('addEmpName')">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <%-- 员工邮箱 --%>
                        <div class="form-group">
                            <label for="addEmpEmail" class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-10">
                                <input type="text" name="email" class="form-control" id="addEmpEmail" placeholder="Email@jibin.com">
                                <span class="help-block"></span>
                            </div>
                    </div>
                    <%-- 员工性别 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Gender</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="addEmpGender1" value="M" checked="checked"> 男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="gender" id="addEmpGender2" value="F"> 女
                            </label>
                        </div>
                    </div>
                    <%-- 员工部门名称 --%>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Department</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="dId">
                                <option selected="selected">---请选择---</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="addEmpBtn" class="btn btn-primary" onclick="addEmployee()">保存</button>
                <button type="button" class="btn btn-default closeBtn" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- =====================================  新增员工模态框  ===================================== -->



<!-- 搭建显示页面 -->
<div class="container">
    <!-- 标题 -->
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <!-- 按钮 -->
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" onclick="addEmp()">添加员工</button>
            <button class="btn btn-danger" id="deleteAllBtn">批量删除</button>
        </div>
    </div>
    <!-- 显示表格数据 -->
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <tr>
                    <th>
                        全选<input type="checkbox" id="checkAll"/>
                    </th>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>邮箱</th>
                    <th>部门</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${pageInfo.list }" var="emp">
                    <tr>
                        <td>
                            <input type="checkbox" class="checkOne"/>
                        </td>
                        <td>${emp.empId }</td>
                        <td>${emp.empName }</td>
                        <td>${emp.gender=="M"?"男":"女" }</td>
                        <td>${emp.email }</td>
                        <td>${emp.department.deptName }</td>
                        <td>
                            <button class="btn btn-primary btn-sm edit-btn">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm delete-btn">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                删除
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <!-- 显示分页信息 -->
    <div class="row">
        <!--分页文字信息  -->
        <div class="col-md-6">当前 ${pageInfo.pageNum }页,总${pageInfo.pages }
            页,总 ${pageInfo.total } 条记录</div>
        <!-- 分页条信息 -->
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="${path }/employee?pageNumber=1">首页</a></li>
                    <c:if test="${pageInfo.hasPreviousPage }">
                        <li><a href="${path }/employee?pageNumber=${pageInfo.pageNum-1}"
                               aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                        </a></li>
                    </c:if>


                    <c:forEach items="${pageInfo.navigatepageNums }" var="page_Num">
                        <c:if test="${page_Num == pageInfo.pageNum }">
                            <li class="active"><a href="#">${page_Num }</a></li>
                        </c:if>
                        <c:if test="${page_Num != pageInfo.pageNum }">
                            <li><a href="${path }/employee?pageNumber=${page_Num }">${page_Num }</a></li>
                        </c:if>

                    </c:forEach>
                    <c:if test="${pageInfo.hasNextPage }">
                        <li><a href="${path }/employee?pageNumber=${pageInfo.pageNum+1 }"
                               aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    </c:if>
                    <li><a href="${path }/employee?pageNumber=${pageInfo.pages}">末页</a></li>
                </ul>
            </nav>
        </div>
    </div>

</div>
</body>
</html>
