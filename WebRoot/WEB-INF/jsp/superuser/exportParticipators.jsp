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
<title>add participators by file</title>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<link rel="stylesheet"
	href="<%=path%>/stylesheets/sample_pages/people.css" type="text/css" />
<style type="text/css">
input{
	width:30px;
}
</style>
<script type="text/javascript">
		var path = "<%=path%>";
	function exportParticipator(managerId) {
		$.ajax({
			type : "POST",
			url : path + "/getDownloadKey.html",
			dataType : "json",
			success : function(dataObj) {
				if (true == dataObj["success"]) {
					window.location.href = path + "/downloadfile.html?key="
							+ dataObj["reason"];
				} else {
					alert(dataObj["reason"]);
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="widget">
		<div class="widget-header">
			<span class="icon-article"></span>
			<h3 class="icon aperture">File Download</h3>
		</div>
		<!-- .widget-header -->
		<div class="widget-content">
			<button class="btn btn-primary " onclick="exportParticipator();">
				<span class="icon-loop"></span>下载
			</button>
		</div>
		<!-- .widget-content -->
	</div>

	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>title</th>
				<th>category</th>
				<th>createTime</th>
				<th>deadline</th>
				<th>addButton</th>
				<th>deleteButton</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${participators}" var="project" varStatus="status">
				<c:if test="${status.index % 2 == 0 }">
					<tr class="even gradeA">
				</c:if>
				<c:if test="${status.index % 2 == 1 }">
					<tr class="odd gradeA">
				</c:if>
				<td class="center">${participators[status.index]["id"]}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>