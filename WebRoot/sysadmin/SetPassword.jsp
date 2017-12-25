<%@ page language="java" pageEncoding="UTF-8" import="efsframe.cn.cache.UserLogonInfo"%>
<%@ include file="../inc/head.inc.jsp" %>
<%!
/**
'*******************************
'** 程序名称：   SetPassword.jsp
'** 实现功能：   用户维护 -- 设置口令
'** 设计人员：   Enjsky
'** 设计日期：   2006-03-07
'*******************************
*/
%>
<%
UserLogonInfo userSession = (UserLogonInfo)request.getSession().getAttribute("user");
String strUerID = userSession.getUserID();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML XMLNS:ELEMENT>

<head>
<title>用户维护 -- 设置口令</title>
<link rel="stylesheet" type="text/css" href="../css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="../css/efs-all.css" />
<script type="text/javascript" src="../js/loadmask.js"></script>
<script type="text/javascript" src="../js/efs-all.js"></script>
<script language="JavaScript">
<!--
function doSubmit()
{
  if("<%=strUerID%>" == "0000000001")
  {
    alert("测试系统不让修改超级管理员密码，以免影响其他人试用");
    return false;
  }
  var newpasswd = Efs.getDom("newpasswd");
  var newpasswd2 = Efs.getDom("newpasswd2");
  if (newpasswd.value != newpasswd2.value) {
    newpasswd.value = newpasswd2.value = "";
    newpasswd.focus();
    alert("输入的两个口令不一致，请重新输入！");
    return false;
  }
  Efs.getExt("frmPost").submit();
}

Efs.onReady(function(){
  // alert(Efs.getDom("frmPost").innerHTML);
});

//-->
</script>
</HEAD>
<BODY>
<div xtype="panel" title="设置口令" autoHeight="true" border="false" buttonAlign="center">
  <form id="frmPost" class="efs-box" method="post" action="<%=rootPath%>/identify.do?method=SetPassword">
      <table class="formArea">
        <tr>
          <td class="label">旧的口令</td>
          <td><input type="password" id="oldpasswd" class="Edit" kind="text" fieldname="USERLIST/OLDPASSWORD" must="true" maxlength="12" ignore="true"></td>
        </tr>
        <tr>
          <td class="label">新的口令</td>
          <td><input type="password" id="newpasswd" class="Edit" kind="text" operation="1" writeevent="0" state="0" datatype="0" fieldname="USERLIST/USERPASSWORD" must="true" maxlength="12"></td>
        </tr>
        <tr>
          <td class="label">再次确认新口令</td>
          <td><input type="password" id="newpasswd2" class="Edit" kind="text" fieldname="" must="true" maxlength="12" ignore="true"></td>
        </tr>
        <tr>
          <td></td>
          <td><input type="text" id="userid" kind="text" fieldname="USERLIST/USERID" state="5" datatype="0" value="<%=strUerID%>"></td>
        </tr>
      </table>
      </FORM>
	<div xtype="buttons">
     	<div text="确  定" onEfsClick="doSubmit()"></div>
     	<div text="返  回" onEfsClick="doRet()"></div>
     </div>
</div>    

</BODY>
</HTML>

