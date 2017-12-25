package efsframe.cn.declare;

/**
 * ����ͨ�ó���
 */
public class Common 
{
  /// ϵͳ����
  public static final String SYSTEM_NAME                    = "efs";
  
  /// ���ݿ����Ͷ���
  public static final String C_ORACLE                       = "ORACLE";
  public static final String C_MSSQL                        = "MSSQL";
  public static final String C_MYSQL                        = "MYSQL";
  public static final String C_UNSUPPORT                    = "UNSUPPORT";
  
  /// SQL��������ַ����ָ���
  public static final String STR_DATE_SYS                   = "$DATE_SYS_";     /// ϵͳʱ��(��ȷ������)
  public static final String STR_DATE                       = "$DATE_";         /// ¼��ʱ��(��ȷ����)
  public static final String STR_DATE_DB                    = "$DATE_DB";       /// ���ݿ�ʱ��(��ȷ������)
  public static final String STR_COMPAR                     = "$";              /// ���ڷָ�
  public static final String STR_SEP                        = "$SEP$";          /// ���ָ�
  public static final String MACRO_BLOB                     = "$BLOB_";         /// ������� BLOB ����
  public static final String MACRO_AT                       = "@";              /// �ֶ����ķָ���
  public static final String MACRO_AT_                      = "@_";
  public static final String MACRO_DOLLAR                   = "$";
  
  public static final String SPACE                          = " ";
  public static final String COMMA                          = ",";
  public static final String SEMICOLON                      = ";";
  public static final String DOT                            = ".";
  public static final String SEP                            = "��";
  public static final String BAR                            = "/";
  public static final String BAR2                           = "//";
  public static final String MARK                           = "��";
  public static final String QUOTE                          = "'";
  public static final String RETURN                         = "\n";

  public static final String ON                             = "ON";
  public static final String AS                             = "AS";
  public static final String ALL                            = "*";

  public static final String EQUAL                          = "=";
  public static final String N_EQUAL                        = "!=";
  public static final String BIG                            = ">";
  public static final String BIG_EQUAL                      = ">=";
  public static final String LESS                           = "<";
  public static final String LESS_EQUAL                     = "<=";
  public static final String IS                             = "IS";
  public static final String NOT                            = "NOT";
  public static final String NULL                           = "NULL";
  public static final String LIKE                           = SPACE + "LIKE" + SPACE;
  public static final String N_LIKE                         = SPACE + NOT    + LIKE;
  public static final String IN                             = SPACE + "IN"   + SPACE;
  public static final String NOT_IN                         = SPACE + NOT    + IN;
  public static final String IS_NULL                        = SPACE + IS     + SPACE + NULL;
  public static final String IS_NOT_NULL                    = SPACE + IS     + SPACE + NOT + SPACE + NULL;

  public static final String LEFT_JOIN                      = "LEFT JOIN";
  public static final String LEFT_OUTER_JOIN                = "LEFT OUTER JOIN";
  public static final String RIGHT_JOIN                     = "RIGHT JOIN";
  public static final String RIGHT_OUTER_JOIN               = "RIGHT OUTER JOIN";
  public static final String JOIN                           = "JOIN";
  public static final String INNER_JOIN                     = "INNER JOIN";

  public static final String INSERT                         = "INSERT"      + SPACE +
                                                              "INTO"        + SPACE;
  public static final String UPDATE                         = "UPDATE"      + SPACE;
  public static final String DELETE                         = "DELETE"      + SPACE +
                                                              "FROM"        + SPACE;
  public static final String VALUES                         = "VALUES"      + SPACE;
  public static final String SELECT                         = "SELECT"      + SPACE;
  public static final String FROM                           = "FROM"        + SPACE;
  public static final String WHERE                          = "WHERE"       + SPACE;
  public static final String AND                            = "AND"         + SPACE;
  public static final String GROUP                          = "GROUP BY"    + SPACE;
  public static final String ORDER                          = "ORDER BY"    + SPACE;
  public static final String HAVING                         = "HAVING"      + SPACE;
  public static final String SET                            = "SET"         + SPACE;
  public static final String OJOIN                          = "(+)";
  public static final String DISTINCT                       = "DISTINCT"    + SPACE;
  public static final String RETURNING                      = "RETURNING"   + SPACE;
  public static final String DECLARE                        = "DECLARE";
  public static final String BEGIN                          = "BEGIN";
  public static final String END                            = "END";
  public static final String BLOB                           = "BLOB";
  public static final String EMPTY_BLOB                     = "EMPTY_BLOB()";
  public static final String INTO                           = "INTO"        + SPACE;

  public static final String DBFUNC_SYSDATE                 = "SYSDATE";
  public static final String DBFUNC_HEXTORAW                = "HEXTORAW";
  public static final String DBFUNC_DBMS_LOB_WRITE          = "DBMS_LOB.WRITE";

  public static final String S_FROM                         = SPACE   + FROM;
  public static final String S_VALUES                       = SPACE   + VALUES;
  public static final String S_SET                          = SPACE   + SET;
  public static final String S_WHERE                        = SPACE   + WHERE;
  public static final String S_AND                          = SPACE   + AND;
  public static final String S_GROUP                        = SPACE   + GROUP;
  public static final String S_ORDER                        = SPACE   + ORDER;
  public static final String S_HAVING                       = SPACE   + HAVING;
  public static final String S_DESC                         = SPACE   + "DESC";
  public static final String S_RETURNING                    = SPACE   + RETURNING;
  public static final String S_INTO                         = SPACE   + INTO;
  
  public static final String DECLARE_R                      = DECLARE + RETURN;
  public static final String BEGIN_R                        = BEGIN   + RETURN;

  public static final String FLG_TRUE                       = "1";
  public static final String FLG_FALSE                      = "0";
  
  public static final String USERTYPE_SP                    = "AA";

  /// ����ʶ��ĺ�������õ�ƴ����Ϣ
  public static final String NOT_FOUND_SPELL                = "?";

  /// ������������
  public static final String ERR_UNKOWN                     = "δ֪����";
  public static final String ERR_QUERYNOTHING               = "���ؽ��Ϊ��";
  public static final String ERR_QUERYXMLFAILED             = "���ؽ������ʧ��";
  public static final String ERR_WITHOUTDATASEP             = "ȱ�����ڷָ��־��";
  public static final String ERR_INVALIDDATE                = "���ڸ�ʽ���Ϸ�";
  public static final String ERR_NOTSUPPORTDBMS             = "��֧�ֵ����ݿ�ϵͳ";
  public static final String ERR_NOTFOUNDCONFIGFILE         = "�Ҳ���ϵͳ�����ļ�:";

  /// ������������
  public static final String OP_INSERT                      = "0";
  public static final String OP_UPDATE                      = "1";
  public static final String OP_DELETE                      = "2";

  public static final int IOP_INSERT                        = 0;
  public static final int IOP_UPDATE                        = 1;
  public static final int IOP_DELETE                        = 2;
  
  /// ������������
  public static final String OP_READ                        = "0";
  public static final String OP_WRITE                       = "1";

  public static final int IOP_READ                          = 0;
  public static final int IOP_WRITE                         = 1;
  
  /// �ֶ�״̬����
  public static final String ST_NORMAL                      = "0";
  public static final String ST_QUERY                       = "5";
  public static final String ST_DONOTHING                   = "9";
  
  public static final int IST_NORMAL                        = 0;
  public static final int IST_QUERY                         = 5;
  public static final int IST_DONOTHING                     = 9;

  /// �ֶ����Ͷ���
  public static final String DT_STRING                      = "0";
  public static final String DT_NUMBER                      = "1";
  public static final String DT_SYSDATETIME                 = "2";
  public static final String DT_DATE                        = "3";
  public static final String DT_DATETIME                    = "4";
  public static final String DT_BINARY                      = "5";
  public static final String DT_SQLSCRIPT                   = "5";
  
  public static final int IDT_STRING                        = 0;
  public static final int IDT_NUMBER                        = 1;
  public static final int IDT_SYSDATETIME                   = 2;
  public static final int IDT_DATE                          = 3;
  public static final int IDT_DATETIME                      = 4;
  public static final int IDT_BINARY                        = 5;

  /// ��������
  public static final int    RT_SUCCESS                     = 0;                /// �ɹ�
  public static final int    RT_FUNCERROR                   = 1;                /// ʧ��
  public static final int    RT_LOGICERROR                  = 2;                /// �߼�����
  
  public static final String SRT_SUCCESS                    = "0";              /// �ɹ�
  public static final String SRT_FUNCERROR                  = "1";              /// ʧ��
  public static final String SRT_LOGICERROR                 = "2";              /// �߼�����
  
  public static final String RT_QUERY_SUCCESS               = "00";             /// ��ѯ�����м�¼
  public static final String RT_QUERY_NOTHING               = "01";             /// ��ѯ�����޼�¼

  /// ����Ŷ���
  public static final String MAX_ROLEID                     = "000001";         /// ��ɫ���
  public static final String MAX_USERID                     = "000002";         /// �û����
  public static final String MAX_ERROR_LOGID                = "000003";         /// ������־���
  public static final String MAX_SYSTEM_LOGID               = "000004";         /// ϵͳ��־���
  public static final String MAX_WORKFLOWID                 = "000010";         /// ���������
  public static final String MAX_OBJID                      = "000011";         /// �����ʶ
  public static final String MAX_TASKID                     = "000012";         /// ������
  
  /// XML �ĵ��ڵ㶨��
  public static final String XML_HEADINFO                   = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  public static final String XML_NODE_ELEMENT               = "Element";
  public static final String XML_NODE_ATTRIBUTE             = "Attribute";

  public static final String XDOC_ROOT                      = "EFSFRAME";
  public static final String XDOC_LOGIC_ROOT                = "LOGIC";
  public static final String XDOC_DATAINFO                  = "DATAINFO";
  public static final String XDOC_USERINFO                  = "USERINFO";
  public static final String XDOC_ERRORINFO                 = "ERRORINFO";
  public static final String XDOC_QUERYINFO                 = "QUERYINFO";
  public static final String XDOC_DATA                      = "DATA";
  public static final String XDOC_SQLSCRIPT                 = "SQLSCRIPT"; 

  public static final String XDOC_ERRORRESULT               = "ERRORRESULT";
  public static final String XDOC_FUNCERROR                 = "FUNCERROR";
  public static final String XDOC_ROW                       = "ROW";

  public static final String XDOC_PREDICATE                 = "PREDICATE";
  public static final String XDOC_CONDITIONS                = "CONDITIONS";
  public static final String XDOC_CONDITION                 = "CONDITION";
  public static final String XDOC_TYPE                      = "TYPE";
  public static final String XDOC_ALIAS                     = "ALIAS";
  public static final String XDOC_FIELDNAME                 = "FIELDNAME";
  public static final String XDOC_OPERATION                 = "OPERATION";
  public static final String XDOC_VALUE                     = "VALUE";
  public static final String XDOC_LOGININFO                 = "LOGININFO";

  public static final String XDOC_QUERYCONDITION            = "QUERYCONDITION";
  public static final String XDOC_AFFAIREVENT               = "AFFAIREVENT";
  public static final String XML_AGERANGE                   = "AGERANGE";
  
  public static final String XML_PROP_SOURCE                = "efsframae";
  public static final String XML_PROP_VERSION               = "version";
  public static final String XML_PROP_ROW                   = "row";
  public static final String XML_PROP_ALIAS                 = "alias";
  public static final String XML_PROP_OPERATION             = "operation";
  public static final String XML_PROP_WRITEEVENT            = "writeevent";
  public static final String XML_PROP_CHECKNAME             = "checkname";
  public static final String XML_PROP_STATE                 = "state";
  public static final String XML_PROP_DATATYPE              = "datatype";
  public static final String XML_PROP_PARENTNODE            = "parentnode";
  public static final String XML_PROP_PARENTDATAINDEX       = "parentdataindex";
  public static final String XML_PROP_SV                    = "sv";
  public static final String XML_PROP_AFFAIRTYPEID          = "affairtypeid";
  public static final String XML_PROP_AFFAIRTYPENAME        = "affairtypename";
  public static final String XML_PROP_NAME                  = "name";
  public static final String XML_PROP_EVENTTYPEID           = "eventtypeid";
  public static final String XML_PROP_EVENTTYPENAME         = "eventtypename";
  public static final String XML_PROP_OPURL                 = "opurl";
  public static final String XML_PROP_TEXT                  = "text";
  
  public static final String XML_PROP_RECORDSPERPAGE        = "recordsperpage";
  public static final String XML_PROP_CURRENTPAGENUM        = "currentpagenum";
  public static final String XML_PROP_TOTALPAGES            = "totalpages";
  public static final String XML_PROP_RECORDS               = "records";
  public static final String XML_FILE_SUFFIX                = "xml";

  public static final String PVAL_SOURCE                    = "urn=www-efsframe-cn";
  public static final String PVAL_VERSION                   = "1.0";
  
}
