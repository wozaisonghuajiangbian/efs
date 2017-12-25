<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../inc/head.inc.jsp" %>
<%!
/**
'*******************************
'** 程序名称：   diclist.jsp
'** 实现功能：   普通字典列表
'** 设计人员：   Enjsky
'** 设计日期：   2006-03-10
'*******************************
*/
%>
<%
String strSelfPath = rootPath + "/sysadmin";
%>
<HTML>
<head>
<base href="<%=webRoot%>">
<title>普通字典列表</title>
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="css/efs-all.css" />
<script type="text/javascript" src="js/loadmask.js"></script>
<script type="text/javascript" src="js/efs-all.js"></script>

<SCRIPT language="JavaScript">
var sDicName = ""
var sDicDes = ""

function onEditDicTm()
{
  if(sDicName == "")
  {
    alert("没有选择字典");
    return false;
  }
  Efs.getExt("DicWin").setTitle("处理字典条目：" + sDicName);
  Efs.getExt("DicWin").show();
  
  Efs.getDom("datastore").setAttribute("txtXML","<?xml version='1.0'?><EFSFRAME efsframe='urn=www-efsframe-cn' version='1.0'><QUERYCONDITION currentpagenum='1' dicname='" + sDicName + "'></QUERYCONDITION></EFSFRAME>");
  Efs.getExt("datagrid").store.load();
}

function onAddDicEx()
{
  location.href = "<%=strSelfPath%>/AddNewDic.jsp";
}

Efs.onReady(
  function(){	
  	Efs.getDom("dicList").setAttribute("txtXML", Efs.Common.getQryXml());
  	Efs.getExt("dicTab").setActiveTab(1);
  	Efs.getExt("dicgrid").store.load();
  }
);

// 进入查询
function doQry()
{
	Efs.getDom("dicList").setAttribute("txtXML", Efs.Common.getQryXml(Efs.getExt("frmQry")));
	Efs.getExt("dicTab").setActiveTab(1);
	Efs.getExt("dicgrid").store.load();
}

function doGridClick(data)
{
	sDicName = data["DICNAME"];
 	sDicDes = data["DICDES"];
  
  Efs.getExt("cmdEditTm").enable();
  Efs.getExt("cmdDic").enable();
  Efs.getExt("cmdDel").enable();
}

// 生成字典文件
function onCreateDic()
{
  with(document.frmPost) {
    setAttribute("url", "<%=rootPath%>/identify.do?method=CreateDicFile");
    txtDicName.value = sDicName;
  }

  Efs.getExt("frmPost").submit();
}

// 删除字典
function onDelDic()
{
  Ext.MessageBox.confirm('确认框', '确定要删除该字典吗？', function(bln){
    if(bln=="yes")
    {
      with(document.frmPost) {
        setAttribute("url", "<%=rootPath%>/identify.do?method=dicDel");
        txtDicName.value = sDicName;
      }
      
      Efs.getExt("frmPost").submit("");
    }
  });
  
}

// 获取异步提交的返回监听函数
function frmPostSubBack(bln,from,action)
{
  if(bln)
  {
    Efs.getExt("dicgrid").store.load();  
  }
  else
  {
    var xml_http = action.response;
    
    if(xml_http != null)
    {
      var strRet = xml_http.responseText;
      if(strRet == "")
      {
        alert("处理完成");
      }
    }
    xml_http = null;
  }
}

// 字典条目的单击事件
function doItemGridClick(data)
{
  with(document.frmDicItem) {
    txtDicName.value = sDicName;
    txtDicCode.value = data["DIC_CODE"];
  }
  Efs.getDom("ItemDicCode").value = data["DIC_CODE"];
  Efs.getDom("ItemDicText").value = data["DIC_TEXT"];
  Efs.getExt("cmdItemEdit").enable();
  Efs.getExt("cmdItemDel").enable();  
}



function onItemDelDic()
{
  Efs.getDom("frmDicItem").setAttribute("url", "<%=rootPath%>/identify.do?method=dicDelItem");
 
  Efs.getExt("frmDicItem").submit();  
}

function onItemAdd()
{
  Efs.getExt("frmItemPost").reset();

  Efs.getDom("ItemDicName").value = sDicName;
  Efs.getDom("ItemDicCode").readOnly = false;

  with(Efs.getExt("DicItemWin"))
  {
    setTitle("添加字典条目");
    show();
  }
}

function onItemEdit()
{
  Efs.getDom("ItemDicName").value = sDicName;
  Efs.getDom("ItemDicCode").readOnly = true;
  with(Efs.getExt("DicItemWin"))
  {
    setTitle("修改字典条目");
    show();
  }
}

// 提交 添加或者修改字典条目
function doItemSubmit()
{
  if(Efs.getExt("frmItemPost").isValid()){
    var strXml = Efs.Common.getOpXml(Efs.getExt("frmItemPost"));
    with(document.frmDicItem) {
      setAttribute("url", "<%=rootPath%>/identify.do?method=dicVindicate");
      txtOpXml.value = strXml;
    }
  
    Efs.getExt("frmDicItem").submit();
  }    
}

// 获取异步提交的返回监听函数
function frmItemSubBack(bln,from,action)
{
  if(bln)
  {
    Efs.getDom("datastore").setAttribute("txtXML", "<?xml version='1.0'?><EFSFRAME efsframe='urn=www-efsframe-cn' version='1.0'><QUERYCONDITION currentpagenum='1' dicname='" + sDicName + "'></QUERYCONDITION></EFSFRAME>");
    Efs.getExt("datagrid").store.load();
    Efs.getExt("DicItemWin").hide();
  }
  else
  {
    var xml_http = action.response;
    
    if(xml_http != null)
    {
      var strRet = xml_http.responseText;
      if(strRet == "")
      {
        alert("处理完成");
      }
    }
    xml_http = null;
  }
}

</SCRIPT>
</HEAD>
<BODY>
<div id="dicTab" region="center" xtype="tabpanel" region="center" border="false" title="字典维护" activeTab="0">
     <div id="tab1" title="字典简单查询"  buttonAlign="center">
     <form id="frmQry" class="efs-box" method="post">
      <TABLE class="formArea">
        <tr>
          <td class="label">字典名称</td>
          <td><input type="text" class="Edit" kind="text" fieldname="DICNAME"></td>
        </tr>
        <tr>
          <td class="label">字典描述</td>
          <td><input type="text" class="Edit" kind="text" fieldname="DICDES"></td>
        </tr>
        <tr>
          <td class="label">字典编码长度</td>
          <td><input type="text" class="Edit" kind="float" fieldname="CODELEN"></td>
        </tr>
        <tr>
          <td class="label">字典修改权限</td>
          <td><input type="text" class="Edit" kind="dic" src="DIC_DICEDITABLE" fieldname="EDITABLE"></td>
        </tr>
      </TABLE>   
      </form>
      <div xtype="buttons">
     	  <div text="查  询" onEfsClick="doQry()"></div>
      </div>       
     </div>
     
     <div id="tab2" title="字典列表">

        <div id="dicgrid" region="center" xtype="grid" title="" border="false" pagingBar="true" pageSize="25" onEfsRowClick="doGridClick()" buttonAlign="center">
        <div xtype="tbar">
          <span style="font-size:9pt;font-weight:bold;color:#15428B;">字典列表</span>
          <div text="-"></div>
          <div iconCls="icon-deal" text="字典操作">
            <div iconCls="icon-add" text="增加字典#A" onEfsClick="onAddDicEx()"></div>
            <div text="-"></div>
            <div iconCls="icon-del" id="cmdDel" text="删除字典#D" onEfsClick="onDelDic()" disabled></div>
          </div>
          <div iconCls="icon-edit" id="cmdEditTm" text="字典条目维护#E" onEfsClick="onEditDicTm()" disabled></div>
          <div text="-"></div>
          <div iconCls="icon-dic" id="cmdDic" text="生成字典文件#C" onEfsClick="onCreateDic()" disabled></div>
          <div text="-"></div>
          <div iconCls="icon-back" text="返 回" onEfsClick="top.showTask()"></div>   
        </div>
        	<div id="dicList" xtype="store" url="<%=rootPath%>/ajax?method=getRsQryDicList" txtXML='' autoLoad="false">
				<div xtype="xmlreader" fieldid="DICNAME" record="ROW" totalRecords="QUERYINFO@records">
					<div name="DICNAME" mapping="DICNAME"></div>
					<div name="DICDES" mapping="DICDES"></div>
					<div name="CODELEN"></div>
					<div name="EDITABLE"></div>
				</div>
			</div>
			<div xtype="colmodel">
				<div header="字典名称" width="105" sortable="true" dataIndex="DICNAME"></div>
				<div header="字典物理名称" width="200" sortable="true" dataIndex="DICDES"></div>
				<div header="编码长度" width="200" sortable="true" dataIndex="CODELEN"></div>
				<div header="字典类型" width="200" sortable="true" dataIndex="EDITABLE"></div>
			</div>		
		</div>
     </div>
</div>        

<div iconCls="icon-panel" id="DicWin" xtype="window" width="540" height="320" title="处理字典条目" resizable="true" modal="true">
  <div id="datagrid" region="center" xtype="grid" title="" pagingBar="true" pageSize="20" onEfsRowclick="doItemGridClick()">
     <div xtype="tbar">
      <div iconCls="icon-add" text="增加条目#A" onEfsClick="onItemAdd()"></div>
      <div text="-"></div>
      <div iconCls="icon-edit" id="cmdItemEdit" text="编辑条目#E" onEfsClick="onItemEdit()" disabled></div>
      <div text="-"></div>
      <div iconCls="icon-del" id="cmdItemDel" text="删除条目#D" onEfsClick="onItemDelDic()" disabled></div>
      <div text="-"></div>
      <div iconCls="icon-dic" id="cmdItemDic" text="生成字典文件#C" onEfsClick="onCreateDic()"></div>
     </div>
    <div id="datastore" xtype="store" url="<%=rootPath%>/ajax?method=getRsQryDicDataList" txtXML='' autoLoad="false">
      <div xtype="xmlreader" fieldid="DIC_CODE" record="ROW" totalRecords="QUERYINFO@records">
        <div name="DIC_CODE" mapping="DIC_CODE"></div>
        <div name="DIC_TEXT" mapping="DIC_TEXT"></div>
        <div name="DIC_VALID"></div>
      </div>
    </div>
    <div xtype="colmodel">
      <div header="字典编码" width="80" sortable="true" dataIndex="DIC_CODE"></div>
      <div header="字典内容" width="260" sortable="true" dataIndex="DIC_TEXT"></div>
      <div header="是否有效" width="80" sortable="true" dataIndex="DIC_VALID"></div>
    </div>  
  </div>
</div>

<div iconCls="icon-panel" id="DicItemWin" xtype="window" width="260" height="150" title="添加字典条目" resizable="true" modal="true">
    <div xtype="tbar">
      <div text="->"></div>
      <div iconCls="icon-ok2" text="确  定" onEfsClick="doItemSubmit()"></div>
     </div>
    <form id="frmItemPost" class="efs-box" method="post">
      <TABLE class="formArea">
        <tr>
          <td class="label">字典编号</td>
          <td><input type="text" kind="text" id="ItemDicCode" fieldname="DICDATA/DIC_CODE" must="true"></td>
        </tr>
        <tr>
          <td class="label">字典内容</td>
          <td><input type="text" kind="text" id="ItemDicText" fieldname="DICDATA/DIC_TEXT" value="" must="true"></td>
        </tr>
        <tr style="display:none">
          <td class="label">是否有效</td>
          <td><input type="text" kind="dic" src="DIC_VALID" id="ItemDicValid" fieldname="DICDATA/DICVALID" value="有效" code="1"></td>
        </tr>
      </TABLE>
      <input type="hidden" kind="text" id="ItemDicName" fieldname="DICDATA/DICNAME" value="">
     </FORM>
</div>


<FORM id="frmPost" name="frmPost" url="" method="post" style="display:none;" onEfsSuccess="frmPostSubBack(true)" onEfsFailure="frmPostSubBack(false)">
  <INPUT type="hidden" name="txtDicName">
  <INPUT type="hidden" name="txtDicDes">
</FORM>

<FORM id="frmDicItem" name="frmDicItem" url="" method="post" style="display:none;" onEfsSuccess="frmItemSubBack(true)" onEfsFailure="frmItemSubBack(false)">
  <INPUT type="hidden" name="txtDicName">
  <INPUT type="hidden" name="txtDicCode">
  <INPUT type="hidden" name="txtOpXml">
</FORM>

</BODY>
</HTML>

