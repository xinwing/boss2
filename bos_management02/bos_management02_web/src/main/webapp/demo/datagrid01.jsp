<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 
	1. 添加行.参考80行
	2. 数据是否可编辑.参考38行,需要给列添加editor属性
	3. 数据发生改变后,通知服务器进行后续操作,参考68行.不适用于删除操作
 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>DataGrid编辑行</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
		<script type="text/javascript">
			$(function() {

			    var index;
				$("#id_table").datagrid({
					// 定义表头列,重点属性      	
					columns: [
						[{
							checkbox: true,
							id: 'cbs',
							title: '编号',
							field: 'cbs'
						}, {
							field: 'id',
							title: 'ID',
							id: 'id',
							// 当表格处于编辑状态时,本列是否可以被编辑
							editor: {
								// 输入框的类型
								type: "text",
								// 和输入框相关的设置参数.具体可以参考示例中的cacheeditor.html源代码
								options: {}
							}
						}, {
							field: 'name',
							title: '姓名',
							id: 'name',
							editor: {
								type: 'text'
							}
						}, {
							field: 'age',
							title: '年龄',
							id: 'age',
							editor: {
								type: "numberbox",
								options: {}
							}
						}, ]
					],
					// 定义请求地址,重点属性      	
					url: 'data/datagrid.json',
					// 是否显示行号
					rownumbers: true,
					// 是否单选
					singleSelect: true,
					// 编辑后触发的函数
					onAfterEdit: function(index, row, changes) {
						// index: 发生数据改变的行索引
						// row : 完整的行数据,json格式
						// changes : 发生改变的数据,键值对形式

					},
					// 指定工具栏,重点属性
					toolbar: [{
						iconCls: 'icon-add',
						text: '添加',
						handler: function() {
							// 添加一行
							$('#id_table').datagrid('insertRow', {
								// 在第一行的位置添加一行
								index: 0,
								// 表格中的数据,如果不写说明是空行
								row: {}
							});
							// 编辑数据
							// 后面的0代表编辑第一行数据, 因为我们插入数据时,是插入在第一行的
							$('#id_table').datagrid('beginEdit', 0);

						}
					}, "-", {
						iconCls: 'icon-edit',
						text: '编辑',
						handler: function() {
							// 获取用户选择的行
							var rows = $('#id_table').datagrid('getSelections');
							if(rows.length == 1) {
								// 获取被选中的第一行
								var row = rows[0];
								// 获取索引
							    index = $('#id_table').datagrid('getRowIndex', row);
								$('#id_table').datagrid('beginEdit', index)
							} else {
								$.messager.alert('警告', '请选择一行数据', 'info')
							}
						}
					}, "-", {
						iconCls: 'icon-remove',
						text: '删除',
						// 指定点击菜单时的响应函数
						handler: function() {
							// 获取用户选择的行
							var rows = $('#id_table').datagrid('getSelections');
							if(rows.length == 1) {
								// 获取被选中的第一行
								var row = rows[0];
								// 获取索引
								index = $('#id_table').datagrid('getRowIndex', row);
								$('#id_table').datagrid('deleteRow', index)
							} else {
								$.messager.alert('警告', '请选择一行数据', 'info')
							}
						}
					}, "-", {
						iconCls: 'icon-save',
						text: '保存',
						// 指定点击菜单时的响应函数
						handler: function() {
								$('#id_table').datagrid('endEdit', index)
						}
					}],
					// 是否显示分页工具条,重点属性      	
					pagination: true,
					// 是否显示斑马线
					striped: true,
					fitColumns: true

				})
			})
		</script>
	</head>

	<body>
		<table id="id_table">
		</table>
	</body>

</html>