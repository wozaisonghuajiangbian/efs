<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../inc/head.inc.jsp" %>
<%!
/**
'*******************************
'** 程序名称：   eventtypelist.jsp
'** 实现功能：   事件类型列表
'** 设计人员：   Enjsky
'** 设计日期：   2006-03-10
'*******************************
*/
%>
<%
String strSelfPath = rootPath + "/developer";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML XMLNS:ELEMENT>
<head>
<base href="<%=webRoot%>">
<title>事件类型列表</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/efs-all.css" />
<script type="text/javascript" src="js/loadmask.js"></script>
<script type="text/javascript" src="js/efs-all.js"></script>

<SCRIPT language="JavaScript">

var g_XML = Efs.Common.getQryXml();

var sEventTypeID = "";
function onEditEx()
{
  if(sEventTypeID != "")
  	location.href = "<%=strSelfPath%>/eventtypeedit.jsp?txtEventTypeID=" + sEventTypeID;
}


function onAddEx()
{
  location.href = "<%=strSelfPath%>/eventtypeadd.jsp";
}

function onDicEx()
{
  location.href = "<%=rootPath%>/identify.do?method=toCreateDicFile&txtXML=EVENTTYPE&txtNextUrl=<%=strSelfPath%>/eventtypelist.jsp";
}

function doGridClick(data){
	sEventTypeID = data["EVENTTYPEID"]
	if(sEventTypeID != ""){
		Efs.getExt("cmdEdit").enable();
    Efs.getExt("cmdDel").enable();
	}
}

// 获取异步提交的返回监听函数
function frmPostSubBack(bln,from,action)
{
  if(bln)
  {
    Efs.getExt("mgGrid").store.reload();
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

// 删除事件类型
function onDelEx()
{
  if(sEventTypeID.substr(0,3) == "000")
  {
    Ext.MessageBox.alert("提示","系统默认管理事件类型，不能删除");
    return false;
  }
  
  Ext.MessageBox.confirm('确认框', '确定要删除该事件类型吗？<br>删除将不能恢复。', function(bln){
    if(bln=="yes")
    {
      Efs.getDom("frmData").setAttribute("url", "<%=rootPath%>/identify.do?method=eventDel");
      Efs.getExt("frmData").submit(sEventTypeID);
    }
  });
}
</SCRIPT>
</HEAD>
<BODY>


<div iconCls="icon-panel" id="mgGrid" region="center" xtype="grid" title="事件类型列表" pagingBar="true" pageSize="25" onEfsRowClick="doGridClick()" onEfsRowDblClick="onEditEx()" buttonAlign="center">
	<div id="mgList" xtype="store" url="<%=rootPath%>/ajax?method=getRsQryEventTypeList" baseParams="{txtXML:g_XML}" autoLoad="true">
		<div xtype="xmlreader" fieldid="EVENTTYPEID" record="ROW" totalRecords="QUERYINFO@records">
			<div name="EVENTTYPEID" mapping="EVENTTYPEID"></div>
			<div name="EVENTTYPENAME" mapping="EVENTTYPENAME"></div>
			<div name="AFFAIRTYPENAME"></div>
			<div name="DISABLED"></div>
			<div name="VISIBLE"></div>
		</div>
	</div>
	<div xtype="colmodel">
		<div header="事件类型编号" width="80" sortable="true" dataIndex="EVENTTYPEID" align="center"></div>
		<div header="事件类型名称" width="120" sortable="true" dataIndex="EVENTTYPENAME" align="center"></div>
		<div header="事务类型名称" width="120" sortable="true" dataIndex="AFFAIRTYPENAME" align="center"></div>
		<div header="是否禁用" width="60" sortable="true" dataIndex="DISABLED" align="center"></div>
		<div header="是否显示" width="60" sortable="true" dataIndex="VISIBLE" align="center"></div>
	</div>

     <div xtype="buttons">
     	<div iconCls="icon-add" text="增加事件类型#A" onEfsClick="onAddEx()"></div>
     	<div iconCls="icon-edit" id="cmdEdit" text="编辑事件类型#E" onEfsClick="onEditEx()" disabled></div>
      <div iconCls="icon-del" id="cmdDel" text="删除事件类型#D" onEfsClick="onDelEx()" disabled></div>
     	<div iconCls="icon-dic" text="生成字典文件#T" onEfsClick="onDicEx()"></div>
     	<div iconCls="icon-back" text="返 回" onEfsClick="top.showTask()"></div>      
     </div>
</div>
<form id="frmData" class="efs-box" method="post" url="" onEfsSuccess="frmPostSubBack(true)" onEfsFailure="frmPostSubBack(false)">
</form>
</BODY>
</HTML>
