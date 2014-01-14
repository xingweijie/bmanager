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
<title>Canvas Admin - Interface Elements</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="stylesheets/all.css" type="text/css" />
<style>
.btn {
	margin-right: .65em;
	margin-bottom: .5em;
}

#loaders .widget-content {
	text-align: center;
}

#loaders img {
	display: inline-block;
	margin: 1em .5em;
	vertical-align: middle;
}

.blockMessage {
	margin-top: 2em;
}

#pagination .widget-content {
	text-align: center;
}

.pagination {
	margin-bottom: 0;
}

#gravity {
	width: 100%;
	margin: 5px 0;
	border-spacing: 2px;
}

#gravity td {
	text-align: center;
	vertical-align: middle;
	padding: 5px 0;
	background-color: #EEE;
	width: 33%;
}

#gravity a {
	
}

#gravity a:hover {
	color: #505050;
	background: none;
}
</style>
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function changeIframe(id, link) {
		$iframe = $("#" + id);
		$iframe.attr("src", link);
		$iframe.attr("width", $("#content").width());
	}
</script>
</head>
<body>
	<div id="header">
		<h1>
			<a href="<%=path%>">Canvas Admin</a>
		</h1>
		<a href="javascript:;" id="reveal-nav"> <span class="reveal-bar"></span>
			<span class="reveal-bar"></span> <span class="reveal-bar"></span> </a>
	</div>
	<!-- #header -->
	<div id="sidebar">
		<ul id="mainNav">
			<li id="navDashboard" class="nav" style="cursor:pointer;"
				onclick='changeIframe("frame", "<%=path%>/superuserLogin.html");'><span
				class="icon-home"></span> <a>超级用户登录</a>
			</li>
			<li id="navDashboard" class="nav" style="cursor:pointer;"
				onclick='changeIframe("frame", "<%=path%>/managerLogin.html");'><span
				class="icon-home"></span> <a>管理员登录</a>
			</li>
		</ul>
	</div>
	<div id="content">
		<iframe id="frame" src="" height="790px"
			width="900px"></iframe>
	</div>
</body>
</html>