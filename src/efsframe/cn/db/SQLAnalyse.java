package efsframe.cn.db;

/**
 * ����׼XML�������ݷ��������T-SQL
 * @author enjsky
 */
import java.util.*;
import java.io.*;
import org.dom4j.*;
import efsframe.cn.declare.*;
import efsframe.cn.func.General;

public class SQLAnalyse 
{
  /**
   * ͨ������� XML �ṹ����֯���� SQL ���
   * @param eleSQL              ���� SQL �ṹ�� XML �ڵ�
   * @return String              ������ɵ� SQL ���
   * @throws Exception
   */
  public static String analyseXMLSQL(Element eleSQL) throws Exception
  {
    /// ��� SQL ������
    int int_Operation = Integer.parseInt(eleSQL.attributeValue(Common.XML_PROP_OPERATION));

    LinkedList<String> lst_SQL = new LinkedList<String>();

    String str_SQL = "";

    switch (int_Operation)
    {
      case Common.IOP_INSERT:
        str_SQL = parseInsert(eleSQL, lst_SQL);
        str_SQL = parseBLOB(str_SQL, lst_SQL);
        break;
      case Common.IOP_UPDATE:
        str_SQL = parseUpdate(eleSQL, lst_SQL);
        str_SQL = parseBLOB(str_SQL, lst_SQL);
        break;
      case Common.IOP_DELETE:
        str_SQL = parseDelete(eleSQL, lst_SQL);
        break;
    }

    if(General.empty(str_SQL)) return null;

    return str_SQL;
  }

  /**
   * ͨ������� XML �ṹ����֯���� SQL ���
   * @param docSQL              ���� SQL �ṹ�� XML �ĵ�����
   * @return String              ������ɵ� SQL ���
   */
  public static String analyseXMLSQL(Document docSQL) throws Exception
  {
    return analyseXMLSQL(docSQL.getRootElement());
  }

  /**
   * ͨ������� XML �ṹ����֯���� SQL ���
   * @param strXML              ���� SQL �ṹ�� XML �ַ���
   * @return String              ������ɵ� SQL ���
   */
  public static String analyseXMLSQL(String strXML) throws Exception
  {
    Document docSQL = DocumentHelper.parseText(strXML);
    return analyseXMLSQL(docSQL);
  }

  /**
   * ͨ������� XML �ṹ����֯���� INSERT ���͵� SQL ���
   * @param eleSQL              ���� SQL �ṹ�� XML �ڵ�
   * @param lstBLOB             ���ڴ洢 BLOB �������ݵ� List
   * @return String              ������ɵ� SQL ���
   */
  private static String parseInsert(Element eleSQL, List<String> lstBLOB) throws Exception
  {
    Iterator it_Temp = eleSQL.elementIterator();

    String str_FieldList = "";                  /// �ֶ��б�
    String str_ValueList = "";                  /// �ֶ�ֵ�б�
    String str_TableName = eleSQL.getName();    /// ����

    while (it_Temp.hasNext())
    {
      Element ele_Field     = (Element)it_Temp.next();
      String str_State      = ele_Field.attributeValue(Common.XML_PROP_STATE);
      String str_FieldName  = ele_Field.getName();
      String str_FieldValue = ele_Field.getText();
     
      if (str_State==null) continue;

      if(str_State.equals(Common.DT_STRING))
      {
        str_FieldList += str_FieldName + Common.COMMA;
        
        String str_DataType = ele_Field.attributeValue(Common.XML_PROP_DATATYPE);
        
        if (str_DataType==null) continue;

        int int_DataType = General.empty(str_DataType)?0:Integer.parseInt(str_DataType);

        str_ValueList += General.converType(int_DataType, str_FieldValue, str_FieldName, lstBLOB) + Common.COMMA;
      } /// if(str_State.equals(Common.DT_STRING))
    } /// while (it_Temp.hasNext())

    if (General.empty(str_FieldList)) return null;

    return Common.INSERT + str_TableName + Common.SPACE +
              General.addBracket(str_FieldList.substring(0, str_FieldList.length() - 1)) +
           Common.S_VALUES + 
              General.addBracket(str_ValueList.subSequence(0, str_ValueList.length() - 1));
  }

  /**
   * ͨ������� XML �ṹ����֯���� UPDATE ���͵� SQL ���
   * @param eleSQL              ���� SQL �ṹ�� XML �ڵ�
   * @param lstBLOB             ���ڴ洢 BLOB �������ݵ� List
   * @return String              ������ɵ� SQL ���
   */
  private static String parseUpdate(Element element,List<String> lstBLOB) throws Exception
  {
    Iterator it_Temp = element.elementIterator();

    String str_EditList = "";                   /// �ֶ�ֵ�б�
    String str_WhereList = "";                  /// �����б�
    String str_TableName = element.getName();   // ����

    while (it_Temp.hasNext())
    {
      Element ele_Field     = (Element)it_Temp.next();
      String str_State      = ele_Field.attributeValue(Common.XML_PROP_STATE);
      String str_FieldName  = ele_Field.getName();
      String str_FieldValue = ele_Field.getText();

      if (str_State==null) continue;

      int int_State = Integer.parseInt(str_State);
      String str_DataType;
      int int_DataType;

      switch (int_State)
      {
        case Common.IST_NORMAL:
          str_DataType = ele_Field.attributeValue(Common.XML_PROP_DATATYPE);
          int_DataType = General.empty(str_DataType) ? Common.IDT_STRING : Integer.parseInt(str_DataType);

          str_EditList += str_FieldName + Common.EQUAL + General.converType(int_DataType, str_FieldValue, str_FieldName, lstBLOB) + Common.COMMA;

          break;

        case Common.IST_QUERY:
          str_DataType = ele_Field.attributeValue(Common.XML_PROP_DATATYPE);
          int_DataType = General.empty(str_DataType) ? Common.IDT_STRING : Integer.parseInt(str_DataType);

          if(int_DataType==Common.IDT_BINARY)
          {
            throw new Exception("��ѯ�־��в����Զ��������ֶ�������Ϊ��ѯ�־������!");
          }

          str_WhereList += str_FieldName + Common.EQUAL + General.converType(int_DataType, str_FieldValue, str_FieldName, lstBLOB) + Common.S_AND;
      } /// switch (int_State)
    } /// while (it_Temp.hasNext())

    if (General.empty(str_EditList)) return null;
    
    if (!General.empty(str_WhereList))
      str_WhereList = Common.S_WHERE + str_WhereList.substring(0, str_WhereList.length() - 5);

    return Common.UPDATE + str_TableName +
           Common.S_SET  + str_EditList.substring(0, str_EditList.length() - 1) +
           str_WhereList;
  }

  /**
   * ͨ������� XML �ṹ����֯���� DELETE ���͵� SQL ���
   * @param eleSQL              ���� SQL �ṹ�� XML �ڵ�
   * @param lstBLOB             ���ڴ洢 BLOB �������ݵ� List
   * @return String              ������ɵ� SQL ���
   */
  private static String parseDelete(Element element, List<String> lstBLOB) throws Exception
  {
    Iterator it_Temp = element.elementIterator();

    String str_WhereList = "";                  /// �����б�
    String str_TableName = element.getName();   /// ����

    while (it_Temp.hasNext())
    {
      Element ele_Field     = (Element)it_Temp.next();
      String str_State      = ele_Field.attributeValue(Common.XML_PROP_STATE);
      String str_FieldName  = ele_Field.getName();
      String str_FieldValue = ele_Field.getText();

      if (str_State==null) continue;

      int int_State = Integer.parseInt(str_State);

      String str_DataType;
      int int_DataType;

      switch (int_State)
      {
        case Common.IST_QUERY:
          str_DataType = ele_Field.attributeValue(Common.XML_PROP_DATATYPE);
          int_DataType = General.empty(str_DataType) ? Common.IDT_STRING : Integer.parseInt(str_DataType);

          if (int_DataType==Common.IDT_BINARY)
          {
            throw new Exception("��ѯ�־��в����Զ��������ֶ�������Ϊ��ѯ�־������!");
          } /// if (int_DataType==Common.IDT_BINARY)

          str_WhereList += str_FieldName + Common.EQUAL + General.converType(int_DataType, str_FieldValue, str_FieldName, lstBLOB) + Common.S_AND;
      } /// switch (int_State)
    } /// while (it_Temp.hasNext())

    if (!General.empty(str_WhereList))
      str_WhereList = Common.S_WHERE + str_WhereList.substring(0, str_WhereList.length() - 5);

    return Common.DELETE + str_TableName + str_WhereList;
  }

  /**
   * �滻 һ��SQL ����е� $BLOB_@FIELDNAME@_##$(Oracle�汾)
   * @param strLine             ���е� SQL ���
   * @param lstBLOB             ���ڴ洢 BLOB �������ݵ� List
   * @return String              ������ɵ� SQL ���
   */
  private static String parseBLOB(String strLine, List<String> lstBLOB) throws Exception
  {
    int int_Pos = strLine.indexOf(Common.MACRO_BLOB);

    if (int_Pos<0) return strLine + Common.RETURN;
    
    String declare = Common.DECLARE_R + "varblob" + Common.SPACE + Common.BLOB + Common.SEMICOLON + Common.RETURN;

    String str_BLOBSQL = declare + Common.BEGIN_R;

    int int_PosB = strLine.indexOf(Common.MACRO_DOLLAR, int_Pos + 1);

    /// ȡ�� ��ʶǰ�� SQL ���;
    str_BLOBSQL += strLine.substring(0, int_Pos);

    /// ȡ��$BLOB_@FIELDNAME@_##$ �е����� 
    /// BLOB_@FIELDNAME@_##
    String str_FieldMemo = strLine.substring(int_Pos + 1, int_PosB);

    // ȡ���ֶ���
    String str_FieldName = str_FieldMemo.substring(str_FieldMemo.indexOf(Common.MACRO_AT) + 1,
                                                   str_FieldMemo.indexOf(Common.MACRO_AT_));

    /// ȡ�ö�������List �е�λ��
    int int_Index = Integer.parseInt(str_FieldMemo.substring(str_FieldMemo.indexOf(Common.MACRO_AT_) + 2));
    
    /// �õ�����������
    String str_Data = lstBLOB.get(int_Index);
    
    ///  ������ݴ�����ǧ
    ///  �ͽ�����������ת����
    ///  DBMS_LOB.WRITE(varblob, 0, 2000, HEXTORAW('10FABD3242349'))
    ///  ���� ֱ�ӽ����ݷ��� SQL �����
    String str_Write = "";
    String str_Return = "";
    String str_Into = "" ;

    if (str_Data.length()>4000)
    {
      int int_Size = str_Data.length();
      int int_Line = int_Size%4000==0 ? int_Size/4000 : (int_Size/4000+1);

      str_BLOBSQL += Common.EMPTY_BLOB;

      str_Return = Common.S_RETURNING + str_FieldName;
      str_Into = Common.S_INTO + "varblob" + Common.SPACE;

      for (int i=0; i<int_Line; i++)
      {
        String str_TempData;

        if (i==int_Line-1)
          str_TempData = str_Data.substring(i * 4000, int_Size);
        else
          str_TempData = str_Data.substring(i * 4000, (i + 1) * 4000);
               
        str_Write += Common.DBFUNC_DBMS_LOB_WRITE +
                         General.addBracket("varblob"                 + Common.COMMA +
                                            str_TempData.length() / 2 + Common.COMMA +
                                            (i * 2000 + 1)            + Common.COMMA +
                                            Common.DBFUNC_HEXTORAW    + General.addBracket(General.addQuotes(str_TempData))) +
                     Common.SEMICOLON + Common.RETURN;
      } /// for (int i=0; i<int_Line; i++)
    } /// if (str_Data.length()>4000)
    else
    {
      str_BLOBSQL += Common.DBFUNC_HEXTORAW + General.addBracket(General.addQuotes(str_Data));
    } /// if (str_Data.length()>4000)

    str_BLOBSQL += strLine.substring(int_PosB + 1);
    str_BLOBSQL += str_Return + str_Into   + Common.SEMICOLON + Common.RETURN +
                   str_Write  + Common.END + Common.SEMICOLON + Common.RETURN;

    return str_BLOBSQL;
  } 


  public static void main(String[] args)
  {
    ///String str_XML = "<PUNISHORG operation='0' writeevent='1'><ORGNAME state='0' datatype='5'>��λ����</ORGNAME><TELNO state='0' datatype='0'>��λ��ϵ�绰</TELNO><ORGADDRESS state='0' datatype='0'>��λ��ַ</ORGADDRESS><NAME state='0' datatype='0'>��������</NAME><SEX state='0' datatype='1'>1</SEX><IDCARDNO state='0' datatype='0'>4323424</IDCARDNO><BIRTHDAY state='0' datatype='3'>19780201</BIRTHDAY><CRIMINALRECORD state='0' datatype='0'>ǰ�������������¼���</CRIMINALRECORD><NAMESPELL state='0' datatype='0'>spenll</NAMESPELL><NAMESPELLALL state='0' datatype='0'>spenll</NAMESPELLALL><ORGNAMESPELL state='0' datatype='0'>spenl</ORGNAMESPELL><ORGNAMESPELLALL state='0' datatype='0'>spell</ORGNAMESPELLALL><EVENTTYPEID state='0' datatype='0'>0001</EVENTTYPEID><OBJID state='0' datatype='0'></OBJID><ORGID state='0' datatype='0'></ORGID><HASCRIMINALRECORD state='0' datatype='1'></HASCRIMINALRECORD><AFFAIRTYPECLASS state='0' datatype='1'></AFFAIRTYPECLASS><ORGSTATUS state='0' datatype='1'></ORGSTATUS></PUNISHORG>";
    String str_XML="<TESTPHOTO  operation='0'><PHOTOID state='0' datatype='0'>001</PHOTOID><PHOTO state='0' datatype='5'>bb</PHOTO></TESTPHOTO>";
    try{
      DataInputStream in =
        new DataInputStream(
          new BufferedInputStream(
            new FileInputStream("D:\\1.jpg")));
      int int_Size = in.available();

      //char ch[] = new char[int_Size];

      byte bt[] = new byte[int_Size];
      in.read(bt);
      
      String ret = new sun.misc.BASE64Encoder().encode(bt);
      //decoder.decodeBuffer()
      Document doc = DocumentHelper.parseText(str_XML);
      //LinkedList lstBLOB = new LinkedList();
      // Node node = doc.selectSingleNode("TESTPHOTOPHOTO");
      
      DataOutputStream out =
        new DataOutputStream(
          new BufferedOutputStream(
            new FileOutputStream("D:\\Test001.text")));
      
      List lstBLOB = doc.selectNodes("//PHOTO");
      Iterator it_Temp = lstBLOB.iterator();
      while(it_Temp.hasNext())
      {
        Element element = (Element)it_Temp.next();
        //System.out.println("the name is" + element.getName());
        element.setText(ret);
      }
       
      String str_SQL = SQLAnalyse.analyseXMLSQL(doc);
      out.writeChars(str_SQL);
      System.out.println(str_SQL);
      DataStorage obj_DS = new DataStorage();
      obj_DS.addSQL(str_SQL);
      obj_DS.runSQL();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
}
