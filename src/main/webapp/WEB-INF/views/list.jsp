<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表</title>
	<% 
		pageContext.setAttribute("PATH",request.getContextPath());
	%>
	<!-- web路径：
		不以/开始的路径，找资源，以当前资源路径为基准，经常容易出问题。
		以/开始的相对路径，找资源，以服务器的路径为标准(http://localhost:3306)；需要加上项目名
			http://localhost:3306/crud -->
	<script type="text/javascript" src="${PATH}/static/js/jquery-1.12.3.min.js"></script>
	<link href="${PATH}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet"/>
	<script src="${PATH}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮  -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 表格  -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>lastName</th>
						<th>gender</th>
						<th>email</th>
						<th>department</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageInfo.list}" var="emp">
						<tr>
							<td>${emp.empId}</td>
							<td>${emp.empName}</td>
							<td>${emp.gender==0?'男':'女'}</td>
							<td>${emp.email}</td>
							<td>${emp.department.deptName}</td>
							<td>
								<button class="btn btn-info btn-sm">
									<span class="glyphicon glyphicon-pencil"></span>
								 	编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash"></span>
									删除
								</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 分页  -->
		<div class="row">
			<!-- 分页信息 -->
			<div class="col-md-6">
				当前${pageInfo.pageNum}页,总共${pageInfo.pages}页,总共${pageInfo.total }条记录
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				  	<li><a href="${PATH}/emps?pn=1">首页</a></li>
				  		<!-- 前一页 -->
					    <c:if test="${pageInfo.hasPreviousPage}">
					    	<li>
						      <a href="${PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
						        <span aria-hidden="true">&laquo;</span>
						      </a>
						    </li>
					    </c:if>
					    <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
					    	<c:if test="${page_Num == pageInfo.pageNum}">
					    		<li class="active"><a href="#">${page_Num}</a></li>
					    	</c:if>
					    	<c:if test="${page_Num != pageInfo.pageNum}">
					    		<li><a href="${PATH}/emps?pn=${page_Num}">${page_Num}</a></li>
					    	</c:if>
					    </c:forEach>
					    <!-- 后一页 -->
					    <c:if test="${pageInfo.hasNextPage}">
						    <li>
						      <a href="${PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
						        <span aria-hidden="true">&raquo;</span>
						      </a>
						    </li>
					    </c:if>
				    <li><a href="${PATH}/emps?pn=${pageInfo.pages}">末页</a></li>
				  </ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>