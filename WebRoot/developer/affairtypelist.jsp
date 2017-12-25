<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../inc/head.inc.jsp" %>
<%!
/**
'*******************************
'** 程序名称：   affairtypelist.jsp
'** 实现功能：   事务类型列表
'** 设计人员：   Enjsky
'** 设计日期：   2006-03-10
'*******************************
*/
%>
<%
String strSelfPath = rootPath + "/developer";
%>
<HTML>
<head>
<base href="<%=webRoot%>">
<title>事务类型列表</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/efs-all.css" />
<script type="text/javascript" src="js/loadmask.js"></script>
<script type="text/javascript" src="js/efs-all.js"></script>

<SCRIPT language="JavaScript">
var g_XML = Efs.Common.getQryXml();

var sAffairTypeID = "";
function onEditEx()
{
  if(sAffairTypeID != "")
  	location.href = "<%=strSelfPath%>/AffairtypeEdit.jsp?txtAffairTypeID=" + sAffairTypeID;
}
function onAddEx()
{
   location.href = "<%=strSelfPath%>/AffairtypeAdd.jsp";
}
function onDicEx()
{
	location.href = "<%=rootPath%>/identify.do?method=toCreateDicFile&txtXML=AFFAIRTYPE&txtNextUrl=<%=strSelfPath%>/affairtypelist.jsp";
}

function doGridClick(data){
	sAffairTypeID = data["AFFAIRTYPEID"]
	if(sAffairTypeID != ""){
		Efs.getExt("cmdEdit").enable();
    Efs.getExt("cmdDel").enable();
	}
}

// 获取异步提交的返回监听函数
function frmPostSubBack(bln,from,action)
{
  if(bln)
  {
    Efs.getExt("affgrid").store.reload();
  }
  else
  {
    var xml_http = action.response;
    if(xml_http != null)
    {
      var objXML = xml_http.responseXML;
      alert("处理失败：" + Efs.Common.getNodeValue(objXML,"//FUNCERROR",0));
      objXML = null;
    }
    xml_http = null;
  }
}

// 删除事务类型
function onDelEx()
{
  if(sAffairTypeID.substr(0,3) == "000")
  {
    Ext.MessageBox.alert("提示","系统默认管理事务类型，不能删除");
    return false;
  }

  Ext.MessageBox.confirm('确认框', '确定要删除该事务类型吗？<br>删除将不能恢复。', function(bln){
    if(bln=="yes")
    {
      Efs.getDom("frmData").setAttribute("url", "<%=rootPath%>/identify.do?method=affairDel");
      Efs.getExt("frmData").submit(sAffairTypeID);
    }
  });

}
</SCRIPT>
</HEAD>
<BODY>

<div iconCls="icon-panel" title="事务类型列表" id="affgrid" region="center" xtype="grid" pagingBar="true" pageSize="25" onEfsRowClick="doGridClick()" onEfsRowDblClick="onEditEx()">
     <div xtype="tbar">
     	<div text="->"></div>
     	<div iconCls="icon-add" text="增加事务#A" onEfsClick="onAddEx()"></div>
      <div text="->"></div>
     	<div iconCls="icon-edit" id="cmdEdit" text="编辑事务#E" onEfsClick="onEditEx()" disabled></div>
      <div text="->"></div>
      <div iconCls="icon-del" id="cmdDel" text="删除事务#D" onEfsClick="onDelEx()" disabled></div>
      <div text="->"></div>
     	<div iconCls="icon-dic" text="生成字典文件#T" onEfsClick="onDicEx()"></div>
      <div text="->"></div>
     	<div iconCls="icon-back" text="返 回" onEfsClick="top.showTask()"></div>      
     </div>
	<div id="affList" xtype="store" url="<%=rootPath%>/ajax?method=getRsQryAffairTypeList" baseParams="{txtXML:g_XML}" autoLoad="true">
		<div xtype="xmlreader" fieldid="AFFAIRTYPEID" record="ROW" totalRecords="QUERYINFO@records">
			<div name="AFFAIRTYPEID" mapping="AFFAIRTYPEID"></div>
			<div name="AFFAIRTYPENAME" mapping="AFFAIRTYPENAME"></div>
			<div name="AFFAIRTYPEMODE"></div>
			<div name="AFFAIRTYPEDES"></div>
		</div>
	</div>
	<div xtype="colmodel">
		<div header="事务类型编号" width="100" sortable="true" dataIndex="AFFAIRTYPEID" hidden="true"></div>
		<div header="事务类型名称" width="200" sortable="true" dataIndex="AFFAIRTYPENAME"></div>
		<div header="事务类型模式" width="200" sortable="true" dataIndex="AFFAIRTYPEMODE"></div>
		<div header="事务类型描述" width="200" sortable="true" dataIndex="AFFAIRTYPEDES"></div>
	</div>
</div>

<form id="frmData" class="efs-box" method="post" url="" onEfsSuccess="frmPostSubBack(true)" onEfsFailure="frmPostSubBack(false)">
</form>
</BODY>
</HTML>
