<%@ page language="java" import="efsframe.cn.cache.*" pageEncoding="UTF-8"%>
<%@ include file="../inc/head.inc.jsp" %>
<%!
/**
'*******************************
'** 程序名称：   qryPsnList.jsp
'** 实现功能：   查询学生列表
'** 设计人员：   Enjsky
'** 设计日期：   2009-10-14
'*******************************
*/
%>

<%
UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
%>
<HTML>
<head>
<title>查询学生列表</title>
<link rel="stylesheet" type="text/css" href="../css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="../css/efs-all.css" />
<script type="text/javascript" src="../js/loadmask.js"></script>
<script type="text/javascript" src="../js/efs-all.js"></script>

<SCRIPT language="JavaScript">

var g_XML = Efs.Common.getQryXml();

var sPersonID = "";
function doGridClick(data){
  sPersonID = data["PERSONID"];
  
	if(sPersonID != ""){
		try {
      Efs.getExt("cmdEdit").enable();
    }
    catch(e) {}
    try {
      Efs.getExt("cmdDel").enable();
    }
    catch(e) {}
	}
}

// 进入查询
function doQry()
{
  var strXml = Efs.Common.getQryXml(Efs.getExt("frmQry"));
  
  Efs.getDom("psnList").setAttribute("txtXML", strXml);
  
  Efs.getExt("psnGrid").store.reload();
}

// 修改人员档案
function onEditEx() {
  
  if(sPersonID == "")
  {
    alert("没有选择学生");
    return false;
  }
  Efs.getExt("frmData").reset();
  
  Efs.Common.ajax("<%=rootPath%>/ajax?method=getPersonDetail&txtPersonID=" + sPersonID,"",function(succ,response,options){
     if(succ){ // 是否成功
       var xmlReturnDoc = response.responseXML;
       Efs.Common.setEditValue(xmlReturnDoc,Efs.getExt("frmData"), "QUERYINFO");
     }
     else{
       alert("加载数据失败!");
     }
  });

  Efs.getExt("PsnMWin").show();
}

// 提交修改人员信息
function doPsnEdit() {
  Efs.getExt("frmData").submit();
}


// 获取异步提交的返回监听函数
function frmPostSubBack(bln,from,action)
{
  
  if(bln)
  {
    Efs.getExt("PsnMWin").hide();
    doQry();
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

// 删除人员信息
function onDelEx()
{
  Efs.getExt("frmData").submit(Efs.getExt("psnGrid").getDelXml());
}


function redSex(sV,sT,recordD)
{
  if(sV == "1")
  {
    return "<img src='../images/default/dd/user_green.jpg' title='男'/>";
  }
  else
    return "<img src='../images/default/dd/ico_service_faq.gif' title='女'/>";
}

function redDetail(sV,sT,recordD)
{
  return "<a href='psnDetail.jsp?txtPersonID=" + recordD.data["PERSONID"] + "' style='color:red;' target='_blank'>详细信息</a>";
}

function ShowWaitMsg(sMsg)
{
  Ext.MessageBox.show({
       msg: sMsg,
       progressText: '下载中...',
       width:300,
       wait:true,
       waitConfig: {interval:200},
       icon:'ext-mb-download'
   });
}

var xmlhttp;
var intTotalPages = 0;
var pageSize = 4;
var filePath = "c:\\人员信息.xls";
var index = 0;
var iPage = 0;
function toExcel()
{
  var allnum = Efs.getExt("psnGrid").store.getTotalCount();
  if(allnum == 0)
  {
    Ext.MessageBox.alert('导出状态','数据查询为空，请重新查询');
    return false;
  }

  if(allnum % pageSize == 0)
    intTotalPages = allnum/pageSize;
  else
    intTotalPages = parseInt(allnum/pageSize) + 1;
  
  ShowWaitMsg("正在生成Excel数据，请稍等......");
  
  var xmlData = "<ROOT><ROWTITLE><COLTITLE>序号</COLTITLE> <COLTITLE>编码</COLTITLE><COLTITLE>姓名</COLTITLE><COLTITLE>身份证</COLTITLE><COLTITLE>性别</COLTITLE><COLTITLE>籍贯</COLTITLE><COLTITLE>出生日期</COLTITLE><COLTITLE>联系电话</COLTITLE></ROWTITLE></ROOT>";
  var objStream = new ActiveXObject("SchOcx.LocalSystem"); /// 该控件必须先安装在客户端
  var strRet = objStream.XmlToExcel(xmlData,index,filePath);
  objStream = null;
  index = 1;
  iPage = 1;
  
  Efs.getExt("cmdExcel").disable();
  
  window.setTimeout("toExportData();",10);
}

function toExportData()
{
  ShowWaitMsg("正在导出第"+iPage+"页数据，请耐心等待...");
  var sXml = Efs.Common.getQryXml(Efs.getExt("frmQry"));

  xmlDoc = Efs.Common.createDocument();
  xmlDoc.async=false;
  xmlDoc.loadXML(sXml);

  Efs.Common.setAttribValue(xmlDoc, "EFSFRAME/QUERYCONDITION", "currentpagenum", iPage);
  Efs.Common.setAttribValue(xmlDoc, "EFSFRAME/QUERYCONDITION", "recordsperpage", pageSize);
  
  // 向组件递交查询
  xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
  xmlhttp.onreadystatechange = getReady;
  xmlhttp.Open("POST","<%=rootPath%>/ajax?method=getPersonListSv&txtXML=" + xmlDoc.xml,true);
  xmlhttp.Send();
}

// 根据 readyState 状态判断是否已经查询完毕
function getReady(){
  if(xmlhttp.readyState==4){
    if(xmlhttp.status==200)
    {
      xmlReturnDoc=xmlhttp.responseXML;
      if(xmlReturnDoc.selectSingleNode("//ERRORRESULT").text == "00")
      {
        var objStream = new ActiveXObject("SchOcx.LocalSystem");
        var strRet = objStream.XmlToExcel(xmlReturnDoc.selectSingleNode("//QUERYINFO").xml,index,filePath);
        objStream = null;
        iPage++;
        
        if(iPage<=intTotalPages)
        {
          index = (iPage-1)*pageSize+1;
          window.setTimeout("toExportData();",10);
        }
        else
        {
          Ext.MessageBox.hide();
          Ext.MessageBox.alert("导出状态", "导出完成，请打开" + filePath + " 进行查看");
          Efs.getExt("cmdExcel").enable();
        }
      }
      else
      {
        Ext.MessageBox.hide();
        Ext.MessageBox.alert('导出状态', '查询数据错误，请重新查询导出');
        Efs.getExt("cmdExcel").enable();
      }
    }
    else
    {
      Ext.MessageBox.hide();
      Efs.getExt("cmdExcel").enable();
      Ext.MessageBox.alert('导出状态','查询数据失败');
    }
  }
}

</SCRIPT>
</HEAD>
<BODY>
<div iconCls="icon-panel" region="north" height="60" title="查询学生列表" border="false">
 <form id="frmQry"  method="post">
  <TABLE class="formAreaTop" width="100%" height="100%" cellpadding="0" cellspacing="0">
      <tr>
        <td>&nbsp;</td>
        <td width="60">姓名</td>
        <td width="160"><input type="text" class="Edit" kind="text" fieldname="NAME" operation="like" maxlength="30" hint="模糊查询"></td>
        <td width="40">性别</td>
        <td width="160"><input type="text" class="Edit" kind="dic" src="DIC_SEX" fieldname="SEX"></td>
        <td width="40">年龄</td>
        <td width="160"><input type="text" class="Edit" kind="int" fieldname="YEAROLD" operation="&gt;="></td>
        <td width="40">籍贯</td>
        <td width="160"><input type="text" class="Edit" kind="dic" src="DIC_CODE" fieldname="PLACECODE"></td>
        <td><input iconCls="icon-qry" type="button" value="查 询" onEfsClick="doQry()"></td>
        <td>&nbsp;</td>
      </tr>
    </TABLE>
  </form>
</div>

<div id="psnGrid" region="center" xtype="grid" pagingBar="true" pageSize="4" onEfsRowClick="doGridClick()" onEfsRowDblClick="onEditEx()">
   <div xtype="tbar">
   	<span style="font-size:9pt;font-weight:bold;color:#15428B;">学生列表</span>
    <div text="->"></div>
<%
//  查看是否拥有编辑学生的权限，如果有，显示编辑学生按钮
if(UserCache.getUserRightByEvent(userSession.getUserID(),"300103"))
{
%>    
   	<div iconCls="icon-edit" id="cmdEdit" text="编辑学生#E" onEfsClick="onEditEx()" disabled></div>
    <div text="-"></div>
<%
}
%>    
<%
if(UserCache.getUserRightByEvent(userSession.getUserID(),"300104"))
{
%>    
   	<div iconCls="icon-Del" id="cmdDel" text="删除学生#D" onEfsClick="onDelEx()" disabled></div>
    <div text="-"></div>
<%
}
%>    
    <div iconCls="icon-excel" id="cmdExcel" text="导出Excel#E" onEfsClick="toExcel()"></div>
    <div text="-"></div>
   	<div iconCls="icon-back" text="返 回" onEfsClick="top.showTask()"></div>
   </div>
	<div id="psnList" xtype="store" url="<%=rootPath%>/ajax?method=getPersonList" baseParams="{txtXML:g_XML}" autoLoad="true">
		<div xtype="xmlreader" fieldid="PERSONID" record="ROW" tabName="PERSON" totalRecords="QUERYINFO@records">
			<div name="PERSONID" mapping="PERSONID"></div>
			<div name="NAME" mapping="NAME"></div>
			<div name="IDCARD"></div>
			<div name="SEX"></div>
      <div name="PLACECODE"></div>
			<div name="BIRTHDAY"></div>
			<div name="TEL"></div>
		</div>
	</div>
	<div xtype="colmodel">
    <div type="checkbox"></div>
    <div header="编码" width="80" sortable="true" dataIndex="PERSONID"></div>
		<div header="学生姓名" width="80" sortable="true" dataIndex="NAME"></div>
		<div header="身份证号" width="120" sortable="true" dataIndex="IDCARD" align="center"></div>
		<div header="性别" width="40" sortable="true" dataIndex="SEX" renderer="redSex"></div>
    <div header="籍贯" width="120" sortable="true" dataIndex="PLACECODE" kind="dic" src="DIC_CODE" align="center"></div>
    <div header="查看详细信息" width="120" sortable="true" align="center" dataIndex="SEX" renderer="redDetail"></div>
	</div>
</div>

<!-- window开始 -->
<div iconCls="icon-panel" id="PsnMWin" xtype="window" width="560" height="255" title="修改学生" resizable="true" modal="true">
  <div region="center" xtype="panel" title="" border="false" autoScroll="true">
    <div xtype="tbar">
      <div text="->"></div>
      <div iconCls="icon-ok2" id="cmdUser" text="确  定" onEfsClick="doPsnEdit()"></div>
    </div>
    <form id="frmData" class="efs-box" method="post" url="<%=rootPath%>/ajax?method=PsnDeal" onEfsSuccess="frmPostSubBack(true)" onEfsFailure="frmPostSubBack(false)">
      <TABLE class="formArea">
        <TR>
          <TD width="100" labelFor="name">姓  名</TD>
          <TD><INPUT id="name" type="text" kind="zhunicode" must="true" maxlength="50" fieldname="PERSON/NAME" datatype="0" state="0"></TD>
          <TD width="20"></TD>
          <TD width="100">身份证号码</TD>
          <TD><INPUT type="text" kind="idcard" fieldname="PERSON/IDCARD" sex="sex" birthdate="birthday" datatype="0" state="0"></TD>
        </TR>
        <TR>
          <TD width="100" labelFor="sex">性  别</TD>
          <TD><INPUT type="text" kind="dic" src="DIC_SEX" id="sex" fieldname="PERSON/SEX" must="true" datatype="0" state="0"></TD>
          <TD width="20"></TD>
          <TD width="100" labelFor="birthday">出生日期</TD>
          <TD><INPUT type="text" kind="date" id="birthday" fieldname="PERSON/BIRTHDAY" datatype="3" state="0" must="true"></TD>
        </TR>
        <TR>
          <TD width="100">籍  贯</TD>
          <TD><INPUT type="text" kind="dic" src="DIC_CODE" fieldname="PERSON/PLACECODE" datatype="0" state="0"></TD>
          <TD width="20"></TD>
          <TD width="100">年  龄</TD>
          <TD><INPUT type="text" kind="int" range=[0,100] fieldname="PERSON/YEAROLD" datatype="1" state="0"></TD>
        </TR>
        <TR>
          <TD width="100">邮  箱</TD>
          <TD><INPUT type="text" kind="email" fieldname="PERSON/EMAIL" datatype="0" state="0"></TD>
          <TD width="20"></TD>
          <TD width="100">电话号码</TD>
          <TD><INPUT type="text" kind="text" fieldname="PERSON/TEL" datatype="0" state="0"></TD>
        </TR>
        <tr>
          <td>备注</td>
          <td colspan="4"><TEXTAREA class="Edit" kind="text" style="height:60px;width:430px" fieldname="PERSON/BAK" state="0" datatype="0"></TEXTAREA>
          </td>
        </tr>
      </TABLE>
      <INPUT type="hidden" kind="text" fieldname="PERSON/PERSONID" datatype="0" state="5" operation="1" writeevent="0"><!--operation="0"  定义为修改接口-->
    </form>            
  </div>
</div>
<!-- window结束 -->

</BODY>
</HTML>

