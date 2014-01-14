<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="stylesheets/all.css" type="text/css" />
<title>Edit Manager</title>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<style type="text/css">
input {
	width: 100px;
}
</style>
<script type="text/javascript">
		var path = "<%=path%>";
	function editAuthority(managerId){
			//window.open(path + "/editAuthority.html?managerId=" + managerId);
			 window.location.href=path + "/editAuthority.html?managerId=" + managerId;
	}
	function updateManager(managerId) {
		var usernameStr = $("#username_" + managerId).val();
		var passwdStr = $("#passwd_" + managerId).val();
		var isActiveStr = $("#isActive_" + managerId).val();
		$.ajax({
			type : "POST",
			url : path + "/updateManager.html",
			dataType : "json",
			data : {
				id : managerId,
				username : usernameStr,
				passwd : passwdStr,
				isActive : isActiveStr,
			},
			success : function(dataObj) {
				if (true == dataObj["success"]) {
					alert("修改成功");
					location.reload();
				} else {
					alert(dataObj["reason"]);
				}
			}
		});
	}
	function addManager() {
		var usernameStr = $("#username_new").val();
		var passwdStr = $("#passwd_new").val();	
		var isActiveStr = $("#isActive_new").val();
		$.ajax({
			type : "POST",
			url : path + "/addManager.html",
			dataType : "json",
			data : {
				username : usernameStr,
				passwd : passwdStr,
				isActive : isActiveStr,
			},
			success : function(dataObj) {
				if (true == dataObj["success"]) {
					alert("添加成功");
					location.reload();
				} else {
					alert(dataObj["reason"]);
				}
			}
		});
	}
	function deleteManager(managerId) {
		$.ajax({
			type : "POST",
			url : path + "/deleteManager.html",
			dataType : "json",
			data : {
				id : managerId,
			},
			success : function(dataObj) {
				if (true == dataObj["success"]) {
					alert("删除成功");
					location.reload();
				} else {
					alert(dataObj["reason"]);
				}
			}
		});
	}
</script>
</head>
<body>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>ID</th>
				<th>用户名</th>
				<th>密码</th>
				<th>是否激活</th>
				<th>权限修改</th>
				<th>保存键</th>
				<th>删除键</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${managers}" var="manager" varStatus="status">
				<c:if test="${status.index % 2 == 0 }">
					<tr class="even gradeA">
				</c:if>
				<c:if test="${status.index % 2 == 1 }">
					<tr class="odd gradeA">
				</c:if>
				<td class="center"><div class="field">
						<input type="text" name="id" id='id_${manager["id"]}'
							value='${manager["id"]}' />
					</div></td>
				<td class="center"><input type="text" name="username"
					id='username_${manager["id"]}' value='${manager["username"]}' />
				</td>
				<td class="center"><input type="text" name="passwd"
					id='passwd_${manager["id"]}' value='${manager["passwd"]}' />
				</td>
				<td class="center"><select id='isActive_${manager["id"]}'>
						<option value='${manager["isActive"]?"激活":"冻结"}'>${manager["isActive"]?"激活":"冻结"}</option>
						<option value='${"true" != manager["isActive"]?"激活":"冻结"}'>${"true"
							!= manager["isActive"]?"激活":"冻结"}</option>
				</select>
				</td>
				<td class="center" onclick='editAuthority(${manager["id"]})'><button
						class="btn btn-primary ">
						<span class="icon-key-fill"></span>点击修改权限
					</button></td>
				<td class="center"><button class="btn btn-primary "
						onclick='updateManager(${manager["id"]})'>
						<span class="icon-loop"></span>保存
					</button>
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='deleteManager(${manager["id"]})'>
						<span class="icon-loop"></span>删除
					</button>
				</td>
				</tr>
			</c:forEach>
			<tr class="even gradeA">
				<td class="center"><label id='id_new'>添加一个新的管理员</label>
				</td>
				<td class="center"><input type="text" name="username"
					id='username_new' />
				</td>
				<td class="center"><input type="text" name="passwd"
					id='passwd_new' />
				</td>
				<td class="center"><select id='isActive_new'>
						<option value='true'>激活</option>
						<option value='false'>冻结</option>
				</select>
				</td>
				<td class="center"><button class="btn btn-gray">
						<span class="icon-key-fill"></span>点击修改权限
					</button></td>
				<td class="center"><button class="btn btn-primary "
						onclick="addManager();">
						<span class="icon-loop"></span>添加
					</button>
				</td>
				<td class="center"><button class="btn btn-gray ">
						<span class="icon-loop"></span>删除
					</button>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>