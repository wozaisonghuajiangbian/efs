package efsframe.cn.base;

import efsframe.cn.declare.*;
import efsframe.cn.db.*;
import efsframe.cn.func.General;

public class SystemLog
{
/**
 * ��¼ϵͳ��־
 * @return String              ϵͳ��¼���
 * @param arrSys            ϵͳ��Ϣ����
 *          ����˵��:
 *            arrSys[0]       �û����
 *            arrSys[1]       �û�����
 *            arrSys[2]       �û�����
 *            arrSys[3]       �û���Ҫ�ر��
 *            arrSys[4]       �û���λ���
 *            arrSys[5]       �û���λ����
 *            arrSys[6]       ��¼�����IP
 *            arrSys[7]       ��¼���������Mac��ַ
 */
  public static String addSysLog(String[] arrSys) throws Exception
  {
    /// ������ϵͳ��־���
    String str_LogID = NumAssign.assignID_A(Common.MAX_SYSTEM_LOGID);

  
    String str_FieldList = General.addBracket(Field.LOGID     + Common.COMMA +
                                              Field.USERID    + Common.COMMA +
                                              Field.USERTITLE + Common.COMMA +
                                              Field.USERNAME  + Common.COMMA +
                                              Field.PERSONID  + Common.COMMA +
                                              Field.UNITID    + Common.COMMA +
                                              Field.UNITNAME  + Common.COMMA +
                                              Field.LOGINIP   + Common.COMMA +
                                              Field.MAC       + Common.COMMA +
                                              Field.ENTERTIME);

    String str_ValueList = General.addBracket(General.addQuotes(str_LogID) + Common.COMMA +
                                              General.addQuotes(arrSys[0]) + Common.COMMA +
                                              General.addQuotes(arrSys[1]) + Common.COMMA +
                                              General.addQuotes(arrSys[2]) + Common.COMMA +
                                              General.addQuotes(arrSys[3]) + Common.COMMA +
                                              General.addQuotes(arrSys[4]) + Common.COMMA +
                                              General.addQuotes(arrSys[5]) + Common.COMMA +
                                              General.addQuotes(arrSys[6]) + Common.COMMA +
                                              General.addQuotes(arrSys[7]) + Common.COMMA +
                                              General.dbDatetime());
    
    String str_SQL = Common.INSERT   + Table.SYSLOG +
                     Common.SPACE    + str_FieldList +
                     Common.S_VALUES + str_ValueList;
    
    DataStorage obj_Storage = new DataStorage();
    obj_Storage.addSQL(str_SQL);
    obj_Storage.runSQL();
    
    return str_LogID;
  }
}
