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
<title>Edit Authority</title>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="stylesheets/sample_pages/people.css"
	type="text/css" />
<style type="text/css">
input {
	width: 30px;
}
</style>
<script type="text/javascript">
		var path = "<%=path%>";
	function deleteAuthority(managerId, projectId) {
		$.ajax({
			type : "POST",
			url : path + "/deleteAuthority.html",
			dataType : "json",
			data : {
				managerId : managerId,
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
	function addAuthority(managerId, projectId) {
		$.ajax({
			type : "POST",
			url : path + "/addAuthority.html",
			dataType : "json",
			data : {
				managerId : managerId,
				projectId : projectId,
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
				<th>添加键</th>
				<th>删除键</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${authorizedProjects}" var="project"
				varStatus="status">
				<c:if test="${status.index % 2 == 0 }">
					<tr class="even gradeA">
				</c:if>
				<c:if test="${status.index % 2 == 1 }">
					<tr class="odd gradeA">
				</c:if>
				<td class="center">${authorizedProjects[status.index]["id"]}</td>
				<td class="center">${authorizedProjects[status.index]["title"]}</td>
				<td class="center">${authorizedCategories[status.index]["name"]}</td>
				<td class="center"><fmt:formatDate
						value='${authorizedProjects[status.index]["createTime"]}'
						pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				</td>
				<td class="center"><fmt:formatDate
						value='${authorizedProjects[status.index]["deadline"]}'
						pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				</td>
				<td class="center"><button class="btn btn-gray ">
						<span class="icon-loop"></span>添加
					</button>
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='deleteAuthority(${manager["id"]}, ${authorizedProjects[status.index]["id"]})'>
						<span class="icon-loop"></span>删除
					</button>
				</td>
				</tr>
			</c:forEach>
			<c:forEach items="${notAuthorizedProjects}" var="project"
				varStatus="status">
				<c:if test="${status.index % 2 == 0 }">
					<tr class="even gradeA">
				</c:if>
				<c:if test="${status.index % 2 == 1 }">
					<tr class="odd gradeA">
				</c:if>
				<td class="center">${notAuthorizedProjects[status.index]["id"]}</td>
				<td class="center">${notAuthorizedProjects[status.index]["title"]}</td>
				<td class="center">${notAuthorizedCategories[status.index]["name"]}</td>
				<td class="center"><fmt:formatDate
						value='${notAuthorizedProjects[status.index]["createTime"]}'
						pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				</td>
				<td class="center"><fmt:formatDate
						value='${notAuthorizedProjects[status.index]["deadline"]}'
						pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='addAuthority(${manager["id"]}, ${notAuthorizedProjects[status.index]["id"]});'>
						<span class="icon-loop"></span>添加
					</button>
				</td>
				<td class="center"><button class="btn btn-gray ">
						<span class="icon-loop"></span>删除
					</button>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>