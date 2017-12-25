package efsframe.cn.base;

import efsframe.cn.declare.*;
import efsframe.cn.db.*;
import efsframe.cn.func.General;

public class ErrorLog
{
  /**
   * ��¼������־
   * @return String              ������־���
   * @param arrError            ������Ϣ����
   *          ����˵��:
   *            arrError[0]       �����ʶ
   *            arrError[1]       �¼����ͱ��
   *            arrError[2]       �û����
   *            arrError[3]       �û�����
   *            arrError[4]       �û���λ���
   *            arrError[5]       �û���λ����
   *            arrError[6]       �û���¼���
   *            arrError[7]       ��������1
   *            arrError[8]       ��������2
   */
  public static String addErrorLog(String[] arrError) throws Exception
  {
    /// �����������־���
    String str_ErrorID = NumAssign.assignID_A(Common.MAX_ERROR_LOGID);

    String str_FieldList = General.addBracket(Field.ERRID         + Common.COMMA + 
                                              Field.OBJID         + Common.COMMA +
                                              Field.EVENTTYPEID   + Common.COMMA + 
                                              Field.USERID        + Common.COMMA + 
                                              Field.USERNAME      + Common.COMMA + 
                                              Field.UNITID        + Common.COMMA + 
                                              Field.UNITNAME      + Common.COMMA + 
                                              Field.LOGID         + Common.COMMA + 
                                              Field.ERRDES1       + Common.COMMA + 
                                              Field.ERRDES2       + Common.COMMA + 
                                              Field.OPTIME);

    String str_ValueList = General.addBracket(General.addQuotes(str_ErrorID)                            + Common.COMMA +
                                              General.addQuotes(arrError[0])                            + Common.COMMA +
                                              General.addQuotes(arrError[1])                            + Common.COMMA +
                                              General.addQuotes(arrError[2])                            + Common.COMMA +
                                              General.addQuotes(General.checkSingleQuotes(arrError[3])) + Common.COMMA +
                                              General.addQuotes(arrError[4])                            + Common.COMMA +
                                              General.addQuotes(General.checkSingleQuotes(arrError[5])) + Common.COMMA +
                                              General.addQuotes(arrError[6])                            + Common.COMMA +
                                              General.addQuotes(General.checkSingleQuotes(arrError[7])) + Common.COMMA +
                                              General.addQuotes(General.checkSingleQuotes(arrError[8])) + Common.COMMA +
                                              General.dbDatetime());
    
    String str_SQL = Common.INSERT   + Table.ERRLOG +
                     Common.SPACE    + str_FieldList +
                     Common.S_VALUES + str_ValueList;
    
    DataStorage obj_Storage = new DataStorage();
    obj_Storage.addSQL(str_SQL);
    obj_Storage.runSQL();
    
    return str_ErrorID;
  }
}
