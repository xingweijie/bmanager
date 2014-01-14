<%@ page language="java"
	import="java.util.*, cn.edu.neu.dateUtils.DefaultFormat"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>Edit Project</title>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="stylesheets/sample_pages/people.css"
	type="text/css" />
<style type="text/css">
input {
	width: 100px;
}
</style>
<script type="text/javascript">
		var path = "<%=path%>";
	function editParticipator(projectId){
		//window.open(path + "/editParticipator.html?projectId=" + projectId);
		window.location.href=path + "/editParticipator.html?projectId=" + projectId;
	}
	function updateProject(projectId) {
	var titleStr = $("#title_" + projectId).val();
	var nameStr = $("#name_" + projectId).val();
	var createTimeStr = $("#createTime_" + projectId).val();
	var deadlineStr = $("#deadline_" + projectId).val();
		$.ajax({
			type : "POST",
			url : path + "/updateProject.html",
			dataType : "json",
			data : {
				projectId : projectId,
				title : titleStr,
				name : nameStr,
				createTime : createTimeStr,
				deadline : deadlineStr,
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
	function addProject() {
	var titleStr = $("#title_new").val();
	var nameStr = $("#name_new").val();
	var createTimeStr = $("#createTime_new").val();
	var deadlineStr = $("#deadline_new").val();
		$.ajax({
			type : "POST",
			url : path + "/addProject.html",
			dataType : "json",
			data : {
				title : titleStr,
				name : nameStr,
				createTime : createTimeStr,
				deadline : deadlineStr,
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
	function deleteProject(projectId) {
		$.ajax({
			type : "POST",
			url : path + "/deleteProject.html",
			dataType : "json",
			data : {
				projectId : projectId,
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
				<th>主题</th>
				<th>项目类别</th>
				<th>创建时间</th>
				<th>截止时间</th>
				<th>修改参与者</th>
				<th>保存键</th>
				<th>删除键</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${projects}" var="project" varStatus="status">
				<c:if test="${status.index % 2 == 0 }">
					<tr class="even gradeA">
				</c:if>
				<c:if test="${status.index % 2 == 1 }">
					<tr class="odd gradeA">
				</c:if>
				<td class="center"><input type="text" name="id"
					id='id_${projects[status.index]["id"]}'
					value='${projects[status.index]["id"]}' />
				</td>
				<td class="center"><input type="text" name="title"
					id='title_${projects[status.index]["id"]}'
					value='${projects[status.index]["title"]}' />
				</td>
				<td class="center"><input type="text" name="name"
					id='name_${projects[status.index]["id"]}'
					value='${categories[status.index]["name"]}' />
				</td>
				<td class="center"><input type="text" name="createTime"
					id='createTime_${projects[status.index]["id"]}'
					value='<fmt:formatDate value='${projects[status.index]["createTime"]}' pattern='<%=DefaultFormat.getDefaultDateformat() %>' />' />
				</td>
				<td class="center"><input type="text" name="deadline"
					id='deadline_${projects[status.index]["id"]}'
					value='<fmt:formatDate value='${projects[status.index]["deadline"]}' pattern='<%=DefaultFormat.getDefaultDateformat() %>' />' />
				</td>
				<td class="center"
					onclick='editParticipator(${projects[status.index]["id"]})'><button
						class="btn btn-primary ">
						<span class="icon-key-fill"></span>点击修改参与者
					</button></td>
				<td class="center"><button class="btn btn-primary "
						onclick='updateProject(${projects[status.index]["id"]})'>
						<span class="icon-loop"></span>保存
					</button>
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='deleteProject(${projects[status.index]["id"]})'>
						<span class="icon-loop"></span>删除
					</button></td>
				</tr>
			</c:forEach>
			<tr class="odd gradeA">
				<td class="center"><label id='id_new'>添加一个新的项目</label>
				</td>
				<td class="center"><input type="text" name="title"
					id='title_new'></td>
				<td class="center"><input type="text" name="name" id='name_new' />
				</td>
				<td class="center"><input type="text" name="createTime"
					id='createTime_new' />
				</td>
				<td class="center"><input type="text" name="deadline"
					id='deadline_new' />
				</td>
				<td class="center"><button class="btn btn-gray ">
						<span class="icon-key-fill"></span>点击修改参与者
					</button></td>
				<td class="center"><button class="btn btn-primary "
						onclick="addProject();">
						<span class="icon-loop"></span>添加
					</button></td>
				<td class="center"><button class="btn btn-gray ">
						<span class="icon-loop"></span>删除
					</button></td>
			</tr>
		</tbody>
	</table>
</body>
</html>