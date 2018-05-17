//点击关闭按钮刷新页面
$(document).on("click", ".closeBtn", function () {
    window.location.reload();
});
//点击新增按钮弹出模态框
function addEmp() {
    //重置模态框
    clearForm();
    //发送ajax请求获取部门信息，并显示在下拉列表中
    getDepts("#empAddModal select");
    //弹出模态框
    $("#empAddModal").modal({
        backdrop:"static"
    });
}

//查询部门信息，并显示在下拉列表中
function getDepts(element) {
    $(element).empty();
    //var option = $("<option></option>").append("---请选择---");
    //option.appendTo($(element));
    $.ajax({
        url:path+"/depts",
        type:"GET",
        success:function (result) {
            // console.log(result);
            /**
             * {code: 100, msg: "处理成功！", extend: {…}}
             * code:100
             * extend:depts:Array(5)
             * 0:{deptId: 1, deptName: "开发部"}
             * 1:{deptId: 2, deptName: "测试部"}
             * 2:{deptId: 3, deptName: "产品部"}
             * 3:{deptId: 4, deptName: "销售部"}
             * 4:{deptId: 5, deptName: "市场部"}
             * length:5
             */
            $.each(result.extend.depts, function () {
                var optionTag = $("<option></option>").append(this.deptName).attr("value", this.deptId);
                optionTag.appendTo($(element));
            });
        }
    });
}

//添加员工时，点击保存按钮
function addEmployee() {
    //校验表单数据
    if (!validateAddForm()) {
        return false;
    }
    //判断ajax员工姓名是否校验成功
    if($("#addEmpBtn").attr("ajaxValidate") == "error"){
        return false;
    }
    //发送ajax请求添加员工
    $.ajax({
        url:path+"/addEmployee",
        type:"POST",
        data:$("#empAddModal form").serialize(),
        success:function (result) {
            if(result.code == 100){
                alert(result.msg);
                //关闭添加员工模态框
                $("#empAddModal").modal('hide');
                //刷新页面
                window.location.reload();
            }else {  //显示错误信息
                if (undefined != result.extend.errorFields.email) {
                    showValidateMsg($("#addEmpEmail"), "error", result.extend.errorFields.email);
                }
                if (undefined != result.extend.errorFields.empName) {
                    showValidateMsg($("#addEmpName"), "error", result.extend.errorFields.empName);
                }
            }
        }
    });
}

//校验添加员工的表单数据
function validateAddForm() {
    //验证邮箱
    var email = $("#addEmpEmail").val();
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if(!regEmail.test(email)){
        showValidateMsg( $("#addEmpEmail"), "error", "邮箱格式不正确");
        return false;
    }else {
        showValidateMsg( $("#addEmpEmail"), "success", "邮箱合法");
    }
    //如果用户名已存在，则直接返回false，无需验证用户名格式
    if($("#addEmpName").attr("exist") == "yes"){
        return false;
    }
    //验证用户名格式
    var empName = $("#addEmpName").val();
    var regName = /(^[a-zA-Z0-9_-]{4,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
    if(!regName.test(empName)){
        //alert("员工姓名：2-5位中文或4-16位英文数字组合")
        showValidateMsg($("#addEmpName"), "error", "员工姓名：2-5位中文或4-16位英文数字组合");
        return false;
    } else {
        showValidateMsg($("#addEmpName"), "success", "用户名合法");
    }
    return true;
}

//校验结果的显示
function showValidateMsg(element, status, msg) {
    //清楚当前元素以前的校验状态
    element.parent().removeClass("has-success has-error");
    element.next("span").text("");
    if("success" == status){//校验成功
        element.parent().addClass("has-success");
    }else if ("error" == status){//校验失败
        element.parent().addClass("has-error");
    }
    element.next("span").text(msg);
}

//ajax请求验证数据库中是否已存在该员工姓名
function empNameChange(element) {
    var empName = $("#"+element).val();
    $.ajax({
        url:path+"/checkEmpName",
        data:"empName="+empName,
        type:"POST",
        success:function (result) {
            $("#addEmpBtn").removeAttr("ajaxValidate");
            $("#addEmpName").removeAttr("exist");
            if(result.code == 100){   //员工姓名可用
                $("#addEmpBtn").attr("ajaxValidate", "success");
                $("#addEmpName").attr("exist", "no");
                showValidateMsg($("#"+element), "success",result.extend.nameMsg);
            }else {  //员工姓名不可用
                $("#addEmpBtn").attr("ajaxValidate", "error");
                $("#addEmpName").attr("exist", "yes");
                showValidateMsg($("#"+element), "error",result.extend.nameMsg);
            }
        }
    });
}

//再次点击新增按钮，弹出模态框时，先清空之前模态框的样式及表单数据
function clearForm() {
    $("#empAddModal form")[0].reset();
    $("#empAddModal form").find("*").removeClass("has-success has-error");
    //span标签的类选择器
    $(".help-block").text("");
    
}

//===============================================================================================

//点击编辑按钮
$(document).on("click", ".edit-btn", function () {
    //获取id
    var empid = $(this).parents("tr").find("td:eq(1)").text();
    //将员工id传给更新按钮，为更新按钮添加一个属性，属性值是员工id
    $("#updateEmpBtn").attr("updateEmpId", empid);
    //1、查出部门信息并添加到下拉列表中
    getDepts("#empUpdateModal select");
    //2、查出员工信息
    getEmployee(empid);
    //3、弹出模态框
    $("#empUpdateModal").modal({
        backdrop:"static"
    });
});

//根据id查询员工信息，并显示在员工更新模态框中
function getEmployee(id) {
    $.ajax({
        url:path+"/getEmployee?id="+id,
        type:"GET",
        success:function (result) {
            var employee = result.extend.emp;
            $("#updateEmpName").text(employee.empName);
            $("#updateEmpEmail").val(employee.email);
            $("#empUpdateModal input[name=gender]").val([employee.gender]);
            $("#empUpdateModal select").val([employee.dId]);
            console.log("did="+employee.dId);
        }
    });
}
    
//修改员工信息时，点击更新按钮
$(document).on("click", "#updateEmpBtn", function () {
    var id = $("#updateEmpBtn").attr("updateEmpId");
    //进行邮箱验证
    var email = $("#updateEmpEmail").val();
    var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    if(!regEmail.test(email)){
        showValidateMsg( $("#updateEmpEmail"), "error", "邮箱格式不正确");
        return false;
    }else {
        showValidateMsg( $("#updateEmpEmail"), "success", "邮箱合法");
    }
    //发送ajax请求，保存修改后的员工信息
    $.ajax({
        url:path+"/updateEmployee",
        type:"POST",
        data:$("#empUpdateModal form").serialize()+"&empId="+id,
        success:function (result) {
            alert(result.msg);
            $("#empUpdateModal").modal('hide');
            //刷新页面
            window.location.reload();
        }
    });
});

//===================================================================================================

//删除一条员工信息，点击表格中的删除按钮
$(document).on("click", ".delete-btn", function () {
    var empId = $(this).parents("tr").find("td:eq(1)").text();
    var empName = $(this).parents("tr").find("td:eq(2)").text();
    if (confirm("确认删除【"+empName+"】吗？")) {
        //发送ajax请求，删除该员工信息
        $.ajax({
            url:path+"/deleteEmployee",
            type:"POST",
            data:"id="+empId,
            success:function (result) {
                alert(result.msg);
                //刷新页面
                window.location.reload();
            }
        });
    }
});

//===================================================================================================

//批量删除时，当前页的全选与全不选功能
$(document).on("click" , "#checkAll", function () {
    // 点击全选时使表格中的复选框与全选复选框状态一致
    $(".checkOne").prop("checked", $(this).prop("checked"));
});

//为表格中的复选框绑定点击事件
$(document).on("click", ".checkOne", function () {
    //判断当前是否为全选
    var flag = $(".checkOne:checked").length == $(".checkOne").length;
    $("#checkAll").prop("checked",flag);
});

//点击批量删除按钮
$(document).on("click", "#deleteAllBtn", function () {
    var empNames = "";
    var empIds = "";
    $.each($(".checkOne:checked"), function () {
        empIds += $(this).parents("tr").find("td:eq(1)").text()+"-";
        empNames += $(this).parents("tr").find("td:eq(2)").text()+"，";
    });
    //去除empIds中多余的"-"
    empIds = empIds.substring(0, empIds.length-1);
    //去除empNames中多余的逗号"，"
    empNames = empNames.substring(0, empNames.length-1);
    if (confirm("确认删除【"+empNames+"】吗？")) {
        //发送ajax请求
        $.ajax({
            url:path+"/batchDeleteEmployee",
            type:"POST",
            data:"empIdString="+empIds,
            success:function (result) {
                alert(result.msg);
                //刷新页面
                window.location.reload();
            }
        });
    }
});




