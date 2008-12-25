<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <title>首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/collapser.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/main.css"></link>
	<!-- GC -->
	<style type="text/css">
	html, body {
        margin:0;
        padding:0;
        border:0 none;
        overflow:hidden;
        height:100%;
    }
	</style>
</head>
<body scroll="no" id="docs">
  	<div id="loading-mask" style="width:100%;height:100%;background:#c3daf9;position:absolute;z-index:20000;left:0;top:0;">&#160;</div>
  <div id="loading">
    <div class="loading-indicator"><img src="${ctx}/scripts/ext/resources/images/default/grid/loading.gif" style="width:16px;height:16px;" align="absmiddle">&#160;Loading...</div>
  </div>
    <!-- include everything after the loading indicator -->
    <script type="text/javascript" src="${ctx}/scripts/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/ext/ext-all.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/scripts/ext/resources/css/ext-all.css" />

    <script type="text/javascript" src="${ctx}/scripts/main.js"></script>

  <div id="header">
	  <div style="padding-top:10px;padding-left:10px;"><b>SpringSide--++</b></div>
  </div>

  <div id="west2">
  </div>  
  <div id="classes">
	  <!-- BEGIN TREE -->
<%@ include file="tree.jsp" %>
      <!-- END TREE -->
  </div>

  <iframe id="main" id="main" frameborder="no"></iframe>
  </body>
</html>
