<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%
      String path = request.getContextPath();
      String basePath = request.getScheme() + "://" + request.getServerName()
          + ":" + request.getServerPort() + path + "/";
      String strUserIP = request.getRemoteAddr();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>Efs Frame ��ҵ����� ��EfsFrame����Ŷ����ľ��� http://www.efsframe.cn/��</title>
    <meta http-equiv="Content-Type" content="text/html;charset=GBK">
    <LINK href="css/style.css" type="text/css" rel="stylesheet">
    <style type="text/css">
<!--

BODY {
  BACKGROUND-POSITION: left top; 
  BACKGROUND-COLOR: #ffffff; images: fixed
}

td {
  font-size:9pt;
}

.TextBorder {
    color:              #333333;
    width:              120px;
    font-size:          9pt;
    background-color:   #ffffff;
    border:             1px solid #0080FF;
}

.button {
  BORDER-RIGHT: #7b9ebd 1px solid;
  PADDING-RIGHT: 2px;
  BORDER-TOP:#7b9ebd 1px solid;
  PADDING-LEFT: 2px;
  FONT-SIZE: 12px; FILTER:progid:DXImageTransform.Microsoft.Gradient(GradientType=0,StartColorStr=#ffffff, EndColorStr=#cecfde);
  BORDER-LEFT: #7b9ebd 1px solid;
  CURSOR: hand; COLOR: black;
  PADDING-TOP: 2px;
  BORDER-BOTTOM: #7b9ebd 1px solid
}
-->
</style>

    <script language="javascript">
function doKeyDown() {
    var keyCode = event.keyCode;
    var src = event.srcElement;
    var edt1 = document.all("txtUserName");
    var edt2 = document.all("txtUserPWD");
    var btn = document.all("Submit");

    switch (keyCode) {
        case 27:    // ESC
            edt1.value = "";
            edt2.value = "";
            return false;
            break;

        case 38:    // UP
            if (src == edt1) {
                btn.focus();
            }
            else if (src == edt2) {
                edt1.focus();
            }
            else if (src == btn) {
                edt2.focus();
            }
            break;

        case 13:    // Enter
        case 40:    // Down
        case 108:   // Enter(С������)
            if (src == edt1) {
                edt2.focus();
            }
            else if (src == edt2) {
                btn.focus();
                if (keyCode != 40) {
                    if (doSubmit()) {
                        frmMain.submit();
                    }
                }
            }
            else if (src == btn) {
                edt1.focus();
            }
            return false;
    }
    return true;
}
//ϵͳ����Ա��¼����
function doSysLog()
{
  with(document.frmMain)
  {
      //����Ĭ���û�������
      txtUserName.value = "system";
//      txtUserPWD.value = "a";
  }
  doSubmit();
}
//��ͨ����Ա��¼����
function doUserLog()
{
  with(document.frmMain)
  {
    txtUserName.value = "enjsky";
//    txtUserPWD.value = "a";
  }
  doSubmit();
}
//�ύ����
function doSubmit()
{
    strUserIp = "<%=strUserIP%>";
    with(document.frmMain)
    {
      strUserTitle = txtUserName.value;
      strUserPWD = txtUserPWD.value;
      strMac = txtMac.value;
    }

    //�����û��������Ϣ��ƴ��xml�ļ�
    strXml = '<?xml version="1.0"?> <EFSFRAME efsframe="urn=www-efsframe-cn" version="1.0"><DATAINFO><LOGININFO><USERTITLE>' + strUserTitle + '</USERTITLE><USERPASSWORD>' + strUserPWD + '</USERPASSWORD><LOGINIP>' + strUserIp + '</LOGINIP><MAC>' + strMac + '</MAC></LOGININFO></DATAINFO></EFSFRAME>';

    with(document.frmMain)
    {
        //��ֵ��XML�ı���
        txtXML.value = strXml;
        //����action��url
        action = "<%=path%>/identify.do?method=login";
        //ִ��action
        submit();
  }
}


window.onload = function() 
{
  document.all("txtMac").value = "";
  document.all("txtUserName").focus();
}

</SCRIPT>
  </head>

  <BODY leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">

    <FORM name="frmMain" action="" method="post"
      onSubmit="return doSubmit()">
      <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%"
        border=0>
        <TBODY>
          <TR>
            <TD align=middle>
              <TABLE height=70 cellSpacing=0 cellPadding=0 width=600 border=0>
                <TBODY>
                  <TR>
                    <TD width=324>
                      <TABLE height=288 cellSpacing=0 cellPadding=0 width=600 border=0>
                        <TBODY>
                          <TR>
                            <TD align=center width=600 background="images/index/famlmain_center.jpg"  height=288>
                              <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
                                <TBODY>
                                  <TR>
                                    <TD width="250px"></TD>
                                    <TD height=180 valign="bottom">
                                      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
                                        <TBODY>
                                          <TR height=24>
                                            <TD width=150 align="right">
                                              <SPAN class=Text>�û�����</SPAN>
                                            </TD>
                                            <TD width=100>
                                              <INPUT name="txtUserName" maxlength="30" class="TextBorder" value="system">
                                            </TD>
                                            <TD width=480 align="center" rowspan="3" valign="top" style="padding:3px;">
                                            <input type="button" name="sysLog" style="width:80px" class="button" value="����Ա��¼" style="margin-bottom:3px;" onclick="doSysLog()">
                                            <input type="button" name="userLog" style="width:80px" class="button" value="һ���û���¼" onclick="doUserLog()">
                                            </TD>
                                          </TR>
                                          <TR height=24>
                                            <TD width=150 align="right">
                                              <SPAN class=Text>��&nbsp;&nbsp;�룺</SPAN>
                                            </TD>
                                            <TD width=100>
                                              <INPUT type="password" name="txtUserPWD" maxlength="30" class="TextBorder" value="a">
                                              <INPUT type="hidden" name="txtMac" value="">
                                              <INPUT type="hidden" name="txtXML">
                                            </TD>
                                          </TR>
                                          <TR>
                                            <TD align="center" colSpan="2" height=30>
                                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                              <input type="Submit" name="Submit" class="button" value="�� ¼" accesskey="E" onKeyDown="doKeyDown()">&nbsp; &nbsp; &nbsp;
                                            </TD>
                                          </TR>
                                        </TBODY>
                                      </TABLE>
                                    </TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                            </TD>
                          </TR>
                        </TBODY>
                      </TABLE>
                      <TABLE height="70" cellSpacing="0" cellPadding="0"
                        width="600" border="0">
                        <TBODY>
                          <TR>
                            <TD width="60" rowspan="2">
                              &nbsp;&nbsp;
                              <img src="images/index/efs.gif" width="32"
                                height="32">
                            </TD>
                            <TD align="left" height="100%">
                              <span style="color:#00309C"> ��Ȩ���У�<a href="http://www.efsframe.cn/" target="_blank">EfsFrame����Ŷ�</a> &nbsp;&nbsp;<B>�汾�� <font color="red">V 2.0&nbsp;&nbsp;&nbsp;&nbsp;ȫ��֧�� IE��FireFox</font>
                              </B>
                              </span>
                              <BR>
                              <span style="color:#9C9A9C">����֧�ֵ绰��027-87176370&nbsp;&nbsp;&nbsp;����֧��QQ��68098375&nbsp;&nbsp;&nbsp;Email��enjsky@163.com&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </TD>
                          </TR>
                        </TBODY>
                      </TABLE>
                    </TD>
                  </TR>
                </TBODY>
              </TABLE>
                </TD>
            </TR>
            </TBODY>
        </TABLE>
    </FORM>
  </body>
</html>
