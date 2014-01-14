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
<title>Edit Participator</title>
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
	function updateParticipator(participatorId) {
	var name = $("#name_" + participatorId).val();
	var sex = $("#sex_" + participatorId).val();
	var phone = $("#phone_" + participatorId).val();
	var email = $("#email_" + participatorId).val();
	var province = $("#province_" + participatorId).val();
	var city = $("#city_" + participatorId).val();
	var area = $("#area_" + participatorId).val();
	var address = $("#address_" + participatorId).val();
	var qq = $("#qq_" + participatorId).val();
	var time = $("#time_" + participatorId).val();
		$.ajax({
			type : "POST",
			url : path + "/updateParticipator.html",
			dataType : "json",
			data : {
				participatorId : participatorId,
				name : name,
				sex : sex,
				phone : phone,
				email : email,
				province : province,
				city : city,
				area : area,
				address : address,
				qq : qq,
				time : time,
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
	function addParticipator() {
	var projectId = '${project["id"]}';
	var name = $("#name_new").val();
	var sex = $("#sex_new").val();
	var phone = $("#phone_new").val();
	var email = $("#email_new").val();
	var province = $("#province_new").val();
	var city = $("#city_new").val();
	var area = $("#area_new").val();
	var address = $("#address_new").val();
	var qq = $("#qq_new").val();
	var time = $("#time_new").val();
		$.ajax({
			type : "POST",
			url : path + "/addParticipator.html",
			dataType : "json",
			data : {
				projectId : projectId,
				name : name,
				sex : sex,
				phone : phone,
				email : email,
				province : province,
				city : city,
				area : area,
				address : address,
				qq : qq,
				time : time,
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
	function deleteParticipator(participatorId) {
		$.ajax({
			type : "POST",
			url : path + "/deleteParticipator.html",
			dataType : "json",
			data : {
				participatorId : participatorId,
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
	<div class="user-card">
		<div class="avatar">
			<img src="./images/stream/defaultavatar_small.png" title="User"
				alt="">
		</div>
		<div class="details">
			<p>
				<strong>title: ${project["title"]}</strong><br /> id:
				${project["id"]}<br /> category: ${category["name"]}<br />
				createTime:
				<fmt:formatDate value='${project["createTime"]}'
					pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				<br /> deadline:
				<fmt:formatDate value='${project["deadline"]}'
					pattern='<%=DefaultFormat.getDefaultDateformat() %>' />
				<br />
			</p>
		</div>
		<!-- .user-card-content -->
	</div>
	<!-- .user-card -->
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>sex</th>
				<th>phone</th>
				<th>email</th>
				<th>province</th>
				<th>city</th>
				<th>area</th>
				<th>address</th>
				<th>qq</th>
				<th>time</th>
				<th>save Button</th>
				<th>delete Button</th>
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
				<td class="center"><input type="text" name="id"
					id='id_${participators[status.index]["id"]}'
					value='${participators[status.index]["id"]}' />
				</td>
				<td class="center"><input type="text" name="name"
					id='name_${participators[status.index]["id"]}'
					value='${participators[status.index]["name"]}' />
				</td>
				<td class="center"><input type="text" name="sex"
					id='sex_${participators[status.index]["id"]}'
					value='${participators[status.index]["sex"]?"男":"女"}' />
				</td>
				<td class="center"><input type="text" name="phone"
					id='phone_${participators[status.index]["id"]}'
					value='${participators[status.index]["phone"]}' /></td>
				<td class="center"><input type="text" name="email"
					id='email_${participators[status.index]["id"]}'
					value='${participators[status.index]["email"]}' /></td>
				<td class="center"><input type="text" name="province"
					id='province_${participators[status.index]["id"]}'
					value='${participators[status.index]["province"]}' /></td>
				<td class="center"><input type="text" name="city"
					id='city_${participators[status.index]["id"]}'
					value='${participators[status.index]["city"]}' /></td>
				<td class="center"><input type="text" name="area"
					id='area_${participators[status.index]["id"]}'
					value='${participators[status.index]["area"]}' /></td>
				<td class="center"><input type="text" name="address"
					id='address_${participators[status.index]["id"]}'
					value='${participators[status.index]["address"]}' /></td>
				<td class="center"><input type="text" name="qq"
					id='qq_${participators[status.index]["id"]}'
					value='${participators[status.index]["qq"]}' /></td>
				<td class="center"><input type="text" name="time"
					id='time_${participators[status.index]["id"]}'
					value='<fmt:formatDate value='${participators[status.index]["time"]}' pattern='<%=DefaultFormat.getDefaultDateformat() %>' />' />
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='updateParticipator(${participators[status.index]["id"]})'>
						<span class="icon-loop"></span>保存
					</button>
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick='deleteParticipator(${participators[status.index]["id"]})'>
						<span class="icon-loop"></span>删除
					</button></td>
				</tr>
			</c:forEach>
			<tr class="odd gradeA">
				<td class="center"><label id='id_new'>添加一个新的Participator</label>
				</td>
				<td class="center"><input type="text" name="name" id='name_new' />
				</td>
				<td class="center"><input type="text" name="sex" id='sex_new' />
				</td>
				<td class="center"><input type="text" name="phone"
					id='phone_new' /></td>
				<td class="center"><input type="text" name="email"
					id='email_new' /></td>
				<td class="center"><input type="text" name="province"
					id='province_new' /></td>
				<td class="center"><input type="text" name="city" id='city_new' />
				</td>
				<td class="center"><input type="text" name="area" id='area_new' />
				</td>
				<td class="center"><input type="text" name="address"
					id='address_new' /></td>
				<td class="center"><input type="text" name="qq" id='qq_new' />
				</td>
				<td class="center"><input type="text" name="time" id='time_new' />
				</td>
				<td class="center"><button class="btn btn-primary "
						onclick="addParticipator();">
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