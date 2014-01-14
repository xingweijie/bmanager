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
<title>add participators by file</title>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/js/jquery.uploadify.min.js"></script>
<link rel="stylesheet" href="<%=path%>/stylesheets/uploadify.css"
	type="text/css" media="screen" />
<link rel="stylesheet"
	href="<%=path%>/stylesheets/sample_pages/people.css" type="text/css" />
<style type="text/css">
input {
	width: 100px;
}
</style>
<script type="text/javascript">
		var path = "<%=path%>";
	$(function() {
		$('#participatorFile').uploadify({
			'swf' : 'flash/uploadify.swf', //上传文件的进度条
			'uploader' : path + '/uploadfile.html', //上传文件的后台处理页面
			'auto' : false,
			'fileObjName' : 'participatorFile',
			'buttonText' : '请选择*.csv',
			'fileTypeDesc' : '包含Participator信息的csv文件',
			'fileTypeExts' : '*.csv',
			'onUploadStart' : function(file){
				$("#participatorFile").uploadify("settings", 'formData', {'projectId' : $("#selectedProjectId").val()});
			}
		});
	});
	function selectProject(projectId){
		$("#selectedProjectId").val(projectId);
	}
</script>
</head>
<body>
	<div class="widget">
		<div class="widget-header">
			<span class="icon-article"></span>
			<h3 class="icon aperture">File Input</h3>
		</div>
		<!-- .widget-header -->
		<div class="widget-content">
			<div class="field">
				<input type="file" name="participatorFile" id="participatorFile" />
			</div>
			<div>
				<label style="color:#fff; font-size:large;">项目编号</label><input
					id="selectedProjectId" type="text" readonly />
			</div>
			<div>
				<button class="btn btn-primary "
					onclick="$('#participatorFile').uploadify('upload');">
					<span class="icon-loop"></span>上传
				</button>
				<button class="btn btn-primary "
					onclick="$('#participatorFile').uplaodify('cancel','*');">
					<span class="icon-loop"></span>取消
				</button>
			</div>
		</div>
		<!-- .widget-content -->
	</div>

	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>ID</th>
				<th>主题</th>
				<th>项目类别</th>
				<th>创建时间</th>
				<th>截止时间</th>
				<th>添加键</th>
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
					onclick='selectProject(${projects[status.index]["id"]})'><button
						class="btn btn-primary ">
						<span class="icon-key-fill"></span>选择该项目
					</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>