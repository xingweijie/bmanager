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

<title>Canvas Admin - 404 Page Not Found</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="author" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<link rel="stylesheet" href="stylesheets/all.css" type="text/css" />

<!--[if gte IE 9]>
	<link rel="stylesheet" href="stylesheets/ie9.css" type="text/css" />
	<![endif]-->

<!--[if gte IE 8]>
	<link rel="stylesheet" href="stylesheets/ie8.css" type="text/css" />
	<![endif]-->

</head>

<body class="error-page">

	<div id="error-header">
		<a href="./dashboard.html">Canvas Admin</a>
	</div>

	<div id="error-wrapper">

		<h1>Oops!</h1>

		<div id="error-code">404 Not Found</div>

		<div id="error-message">Sorry, an error has occured. Requested
			page not found!</div>

		<div id="error-actions">
			<a href="./dashboard.html" class="btn btn- btn-primary">Back to
				Dashboard</a>
		</div>

	</div>
	<!-- #error-wrapper -->

	<script src="javascripts/all.js"></script>

</body>
</html>