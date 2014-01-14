<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->
<head>

<title>Superuser - Login</title>

<meta charset="utf-8" />
<meta name="description" content="" />
<meta name="author" content="" />
<meta name="viewport" content="width=device-width,initial-scale=1" />

<link rel="stylesheet" href="<%=path%>/stylesheets/reset.css"
	type="text/css" media="screen" title="no title" />
<link rel="stylesheet" href="<%=path%>/stylesheets/text.css"
	type="text/css" media="screen" title="no title" />
<link rel="stylesheet" href="<%=path%>/stylesheets/buttons.css"
	type="text/css" media="screen" title="no title" />
<link rel="stylesheet" href="<%=path%>/stylesheets/theme-default.css"
	type="text/css" media="screen" title="no title" />
<link rel="stylesheet" href="<%=path%>/stylesheets/login.css"
	type="text/css" media="screen" title="no title" />
<script src="<%=path%>/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
		var path = "<%=path%>";
	function loginIdentify() {
		var usernameStr = $("#username").val();
		var passwdStr = $("#passwd").val();
		$.ajax({
			type : "POST",
			url : path + "/superuserLoginIdentify.html",
			dataType : "json",
			data : {
				username : usernameStr,
				passwd : passwdStr,
			},
			success : function(dataObj) {
				if (true == dataObj["success"]) {
					parent.location.href = path + "/superuserMenu.html";
				} else {
					alert(dataObj["reason"]);
				}
			}
		});
	}
</script>
</head>

<body>

	<div id="login">
		<h1>Dashboard</h1>
		<div id="login_panel">
			<div class="login_fields">
				<div class="field">
					<label for="username">超级用户</label> <input type="text"
						name="username" value="" id="username" tabindex="1"
						 />
				</div>

				<div class="field">
					<label for="passwd">密码</label> <input type="password"
						name="passwd" value="" id="passwd" tabindex="2"
						 />
				</div>
			</div>
			<!-- .login_fields -->

			<div class="login_actions">
				<button onclick="loginIdentify();" type="submit"
					class="btn btn-primary" tabindex="3">登录</button>
			</div>
		</div>
		<!-- #login_panel -->
	</div>
	<!-- #login -->



</body>
</html>