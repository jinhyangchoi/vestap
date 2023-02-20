<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<tiles:insertAttribute name="cmmlib" />

<nav class="mainTopBar navbar fixed-top p-0">
	<tiles:insertAttribute name="header"/>
</nav>
<nav id="sideMenu">
	<tiles:insertAttribute name="sidemenu"/>
</nav>
<div id="main">
	<div id="zoom">
	<tiles:insertAttribute name="content"/>
	</div>
</div>

<tiles:insertAttribute name="cmmjs"/>