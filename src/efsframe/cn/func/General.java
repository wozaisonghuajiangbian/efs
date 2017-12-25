package efsframe.cn.func;

import java.io.*;
import java.sql.ResultSet;
import java.util.*;
import sun.misc.BASE64Decoder;
import java.util.Date;
import org.dom4j.*;
import org.dom4j.io.*;
import efsframe.cn.db.DBConnection;
import efsframe.cn.declare.*;
import efsframe.cn.cache.*;

/**
 * ���ú����࣬��װ�˸��ֳ��ú��� 
 */
public class General
{
  /**
   * �ж��ַ����Ƿ�Ϊ�ջ���null
   * @param str         �ַ���
   * @return boolean
   */
  public static boolean empty(String str)
  {
    if(str==null) return true;
    if(str.equals("")) return true;
    return false;
  }
  
  /**
   * ���Ĭ�����ݿ��ʱ���ʽ�ַ���
   * @return String
   */
  public static String dbDatetime()
  {
	  if(DBConnection.JDBCTYPE.equalsIgnoreCase("2"))
		  return dbDatetime(Common.C_ORACLE);
    else if(DBConnection.JDBCTYPE.equalsIgnoreCase("3"))
      return dbDatetime(Common.C_MSSQL);
    else if(DBConnection.JDBCTYPE.equalsIgnoreCase("4"))
      return dbDatetime(Common.C_MYSQL);
	  else
		  return dbDatetime(Common.C_MSSQL); // Ĭ��MS SQL
  }

  /**
   * �������ʹ�����
   * @param strDbms         ���ݿ�����
   * @return String
   */
  public static String dbDatetime(String strDbms)
  {
    if(strDbms.equals(Common.C_MSSQL))
    {
      return "GETDATE()";
    }
    if(strDbms.equals(Common.C_ORACLE))
    {
      return "SYSDATE";
    }
    if(strDbms.equals(Common.C_MYSQL))
    {
      return "CURRENT_TIMESTAMP";
    }
    return null;
  }
  
  /**
   * �������ͺ���ת��Ϊ 19990101010101 ��ʽ���ַ���
   * @param date         �����������
   * @return String
   */
   public static String cDateTimeStr(String date)
   {
     try
     {
       String temp = strToDateTime(date);
       Date da = DateTimeUtil.parse(temp, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(da);
       
       int year = calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH) + 1;
       int day = calendar.get(Calendar.DATE);
       int am_pm = calendar.get(Calendar.AM_PM) * 12 ;
       int hour = calendar.get(Calendar.HOUR) + am_pm ;
       int minute = calendar.get(Calendar.MINUTE) ;
       int second = calendar.get(Calendar.SECOND) ;
       
       
       String sMonth = month<10 ? "0" + month : String.valueOf(month);
       String sday = day<10 ? "0" + day : String.valueOf(day);
       String sHour = hour<10 ? "0" + hour : String.valueOf(hour);
       String sMinute = minute<10 ? "0" + minute : String.valueOf(minute);
       String sSecond = second<10 ? "0" + second : String.valueOf(second);
       return String.valueOf(year) + sMonth + sday + sHour + sMinute + sSecond ;
     }
     catch(Exception e)
     {
       return null;
     }
   }
  
/**
 * 200101  -> 2001-01
 * @param value              ������������ַ���
 * @return String
 */
  public static String strMonth(String value)
  {
    if(empty(value))
    {
      return value;
    }
    return value.substring(0, 4) + "-" + value.substring(4);
  }
  
/**
 * ���������ַ���,ʹ֮�ܳ�Ϊ�� Date ��������ݵĵ������ַ���
 *        �ܹ������������:
 *            2001-5-5    ->      2001-5-5
 *            2001/5/5    ->      2001/5/5
 *            20010505    ->      2001-05-05
 * @param value             ������������ַ���
 * @return String
 */
  public static String strToDate(String value)
  {
    if(empty(value))
    {
      return null;
    }

    if(value.length()<8)
      return null;

    int iposa = value.indexOf("-");
    int iposb = value.indexOf("/"); 

    if(iposa <0 && iposb<0)
    {
      value = value.substring(0,4) + "-" + value.substring(4,6) + "-" + value.substring(6,8);
    }
    return value;
  }
  
  /**
   * ��������ʱ���ַ���,ʹ֮�ܳ�Ϊ�� DateTime ��������ݵĵ�����ʱ���ַ���
   *        �ܹ������������:
   *            2001-5-5 12:55:19    ->      2001-5-5 12:55:19
   *            2001/5/5 12:55:19    ->      2001/5/5 12:55:19
   *            20010505 12:55:19    ->      2001-05-05 12:55:19  ����: String
   * @param value             ������������ַ���
   */
  public static String strToDateTime(String value)
  {
    if(empty(value))
    {
      return null;
    }
    int ipos = value.indexOf(Common.SPACE);
    if(ipos>0)
    {
      value = strToDate(value.substring(0, ipos)) + value.substring(ipos); 
    }
    else
    {
      value = strToDate(value) + Common.SPACE + "00:00:00";
    }
    if(value.indexOf(".0")!=-1)
    {
    	value = value.substring(0, value.indexOf(".0"));
    }
    return value;
  }

  /**
   * ��ʽ�������ַ���
   * @param sdate       �����ַ���
   * @return String
   */
  public static String formatDate(String sdate)
  {
    return formatDate(sdate, "yyyy-mm-dd");
  }
  
  /**
   * ��ʽ������ʱ���ַ���
   * @param sdate       ����ʱ���ַ���
   * @return String
   */
  public static String formatDatetime(String sdate)
  {
    return formatDate(sdate, "yyyy-mm-dd" + Common.SPACE + "hh24:mi:ss");
  }
  
  /**
   * ��ʽ�������ַ���
   * @param   sdate       ����
   * @param   pattern    ��ʽ��
   * @return String
   */ 
  public static String formatDate(String sdate, String pattern)
  {
	  if(DBConnection.JDBCTYPE.equalsIgnoreCase("2")) // oracle ��ʽ
		  return "TO_DATE('" + sdate + "','" + pattern + "')";
    else if(DBConnection.JDBCTYPE.equalsIgnoreCase("3")) // Ms Sql ��ʽ
      return "Cast('" + sdate + "' AS DATETIME)";
	  else
		  return "'" + sdate + "'";
  }
  
  /**
   * ������������ת��Ϊ16�����ַ���
   * @param b         ����������
   * @return String
   */
  public static String byte2hex(byte[] b)
  {
    String hs="";
    String stmp="";
    for (int n=0;n<b.length;n++)
    {
      stmp=(Integer.toHexString(b[n] & 0XFF));//toHexString:ת16����
      
      if (stmp.length()==1) hs=hs+"0"+stmp;
      else hs=hs+stmp;
      
      if (n<b.length-1) hs=hs+"";
    }
    return hs.toUpperCase(); // ת��д��16���Ƶ�A~F�ô�д�����
  }
  
  
  /**
   * ���ַ���ת����������
   * @param b         ����������
   * @return String
   */  
  public static byte[] hex2byte(String str) {    
    if (str == null){   
     return null;   
    }   
    str = str.trim();   
    int len = str.length();   
       
    if (len == 0 || len % 2 == 1){   
     return null;   
    }   
    byte[] b = new byte[len / 2];   
    try {   
         for (int i = 0; i < str.length(); i += 2) {   
              b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();   
         }   
         return b;   
    } catch (Exception e) {   
     return null;
    }   
  }  

  /**
   * ���ݲ�ͬ��ʮ�������ַ����õ����Ӧ�Ķ������ַ�����
   * @param cHex           ʮ�������ַ�
   * @return String        �������ַ���
   */
  public static String getBinstrFromHexchar(char cHex)
  {
    String strBin = "";

    /// ��ȡ��ͬ�Ķ������ַ���
    switch (cHex)
    {
      case '0':
        strBin = "0000"; 
        break;
      case '1':
        strBin = "0001";
        break;
      case '2':
        strBin = "0010";
        break;
      case '3':
        strBin = "0011";
        break;
      case '4':
        strBin = "0100";
        break;
      case '5':
        strBin = "0101";
        break;
      case '6':
        strBin = "0110";
        break;
      case '7':
        strBin = "0111";
        break;
      case '8':
        strBin = "1000";
        break;
      case '9':
        strBin = "1001";
        break;
      case 'a':
        strBin = "1010";
        break;
      case 'b':
        strBin = "1011";
        break;
      case 'c':
        strBin = "1100";
        break;
      case 'd':
        strBin = "1101";
        break;
      case 'e':
        strBin = "1110";
        break;
      case 'f':
        strBin = "1111";
        break;
    }

    return strBin;
  }

  /**
   * ����ʮ�������õ����Ӧ��ʮ�������ַ���
   * @param iDec         ʮ������
   * @return char        ʮ�������ַ�
   */
  public static char getHexcharFromDecnum(int iDec)
  {
    char cHex = 'x';

    /// �õ�ʮ�����ַ�
    switch (iDec)
    {
      case 0:
        cHex = '0';
        break;
      case 1:
        cHex = '1';
        break;
      case 2:
        cHex = '2';
        break;
      case 3:
        cHex = '3';
        break;
      case 4:
        cHex = '4';
        break;
      case 5:
        cHex = '5';
        break;
      case 6:
        cHex = '6';
        break;
      case 7:
        cHex = '7';
        break;
      case 8:
        cHex = '8';
        break;
      case 9:
        cHex = '9';
        break;
      case 10:
        cHex = 'A';
        break;
      case 11:
        cHex = 'B';
        break;
      case 12:
        cHex = 'C';
        break;
      case 13:
        cHex = 'D';
        break;
      case 14:
        cHex = 'E';
        break;
      case 15:
        cHex = 'F';
        break;
    }

    return cHex;
  }

  /**
   * �����ֶ�����ת��
   * @param itype     �ֶ�����
   * @param value     �ֶ�ֵ
   * @param name      �ֶ�����
   * @param list      ���� blob �� list
   * @return ���� �����õ� insert sql ���
   */
  public static String converType(int itype,
                                  String value,
                                  String name,
                                  List<String> list) throws Exception
  {
    try
    {
      switch(itype)
      {
        case 0: return "'" + value.replaceAll("'","''") + "'";  // �ַ���
        case 1: return General.empty(value)?"''":value;         // ��ֵ
        case 2: return General.dbDatetime();                    // ϵͳʱ��
        case 3: return General.empty(value)?"''":General.formatDate(General.strToDate(value));         // ʱ��
        case 4: return General.empty(value)?"''":General.formatDatetime(General.strToDateTime(value)); // ����ʱ����
        case 5:
          // BASE64Decoder decoder = new BASE64Decoder();          
          // byte bbt[] = decoder.decodeBuffer(value);
          // String str = General.byte2hex(bbt);
          // list.add(str);
          list.add(value);
          
          return General.empty(value)? "''" : "$BLOB_@" + name + "@_" + String.valueOf(list.size()-1) + "$";
      }
    }
    catch(Exception e)
    {
      throw e;
    }
    return "";
  }

  /**
   * �������������ֶ��е��������ͣ�
   *        ���䴦���Ϊ����Ϊ���ݿ���յĺϷ��� SQL ���
   *            20              =20
   *            20-             >=20
   *            -20             <=20
   *            20-40           BETWEEN 20 AND 40
   * @param name              �����ֵ
   * @param value
   * @param oper
   * @return String              ������Ĳ�ѯ�־�
   */
  public static String opYearDate(String name, String value, String oper)
  {
    int ipos = value.indexOf("-");
    if(ipos!=-1)
    {
      /// �ָ����ǰ��
      if(ipos==0)
      {
        return "YEAROLD(" + name + ")" + "<=" + value.substring(1);
      }
      else
      {
        /// �ָ�������
        if(ipos==(value.length()-1))
        {
          return "YEAROLD(" + name + ")" + ">=" + value.substring(0,value.length()-1);
        }
        else
        {
          return "YEAROLD(" + name + ") between " + value.substring(0,ipos) + " and " + value.substring(ipos + 1); 
        }
      }
    }
    else
    {
      String flags = "=,!=,>,<,>=,<=,LIKE,NOT LIKE";
      if(flags.indexOf(oper)>-1)
      {
        return "YEAROLD(" + name + ") " + oper + " " + value;
      }
    }
    return "YEAROLD(" + name + ")" + "=" + value;
  }
 
  /**
   * �����������͵ĳ�Ϊ����Ϊ���ݿ���յĺϷ��� SQL ���
   *            20010101            =TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            20010101-           >=TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            -20010101           <=TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            20010101-20011231   (>=TO_DATE('2001-01-01', 'yyyy-mm-dd') AND <=TO_DATE('2001-12-31', 'yyyy-mm-dd'))
   * @param name               
   * @param value               
   * @param oper            
   * @return String                  ������Ĳ�ѯ�־�
   */
  public static String opDate(String name,String value,String oper)
  {
    int ipos = value.indexOf("-");
    
    if(ipos!=-1)
    {
      ///   �ָ����ǰ��
      if(ipos==0)
      {
        return formatChar(name,"<=",value.substring(1));
      }
      else
      {
        ///     �ָ���ں���
        if(ipos==value.length()-1)
        {  
          return formatChar(name,">=",value.substring(0,value.length()-1));
        }
        else
        {
          return "(" + formatChar(name,">=",value.substring(0,ipos)) + " and " + formatChar(name,"<=",value.substring(ipos+1))+ ")";
        }
      }
    }
    else
    {
      String flags = "=,!=,>,<,>=,<=,LIKE,NOT LIKE";
      if(flags.indexOf(oper)>-1)
      {
        if(oper.indexOf(">")>-1)
        {
          oper = oper.replaceAll(">","<");
        }
        else
        {
          if(oper.indexOf("<")>-1)
          {
            oper = oper.replaceAll("<",">");
          }
        }
        return formatChar(name,oper,value);
      }
    }
    return name + "=" + addQuotes(value);
  }

  /**
   * ���� to_char(date,patter) ��ʽ�����
   * @param name
   * @param oper
   * @param value
   * @param patter
   * @return String
   */
  public static String formatChar(String name,
                                  String oper,
                                  String value,
                                  String patter)
  {
    return "TO_CHAR" + addBracket(name + Common.COMMA + addQuotes(patter)) + oper + addQuotes(value);
  }
 
  /**
   * ���� TO_CHAR(date,patter) ��ʽ����䣬�� Oracle ʹ��
   * @param name
   * @param oper
   * @param value
   * @return String
   */
  public static String formatChar(String name,
                                  String oper,
                                  String value)
  {
    if(value.length()==4)
      return formatChar(name, oper, value, "yyyy");
    
    if(value.length()==6)
      return formatChar(name, oper, strMonth(value), "yyyy-mm");
    
    return formatChar(name, oper, strToDate(value), "yyyy-mm-dd");
  } 
 
  /**
   * Ϊһ���ַ������˼��ϵ�����
   * @param value                      Դ�ַ���
   * @return String
   */
  public static String addQuotes(String value)
  {
    return "'" + value + "'";
  }
 
  /**
   * Ϊһ���ַ������˼���Բ����
   * @param value                      Դ�ַ���
   * @return String
   */
  public static String addBracket(String value)
  {
    return "(" + value + ")";
  }

  /**
   * Ϊһ���ַ������˼���Բ����
   * @param value                      Դ�ַ���
   * @return String
   */
  public static String addBracket(CharSequence value)
  {
    return "(" + value + ")";
  }
  
  /**
   * �������ͺ���ת��Ϊ 19990101 ��ʽ���ַ���
   * @param date         �����������
   * @return String
   */
   public static String cDateStr(String date)
   {
     try
     {
       String temp = strToDateTime(date);
       Date da = DateTimeUtil.parse(temp, DateTimeUtil.DEFAULT_DATE_FORMAT);
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(da);
       
       int year = calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH) + 1;
       int day = calendar.get(Calendar.DATE);
       
       String sMonth = month<10 ? "0" + month : String.valueOf(month);
       String sday = day<10 ? "0" + day : String.valueOf(day);
       return String.valueOf(year) + sMonth + sday;
     }
     catch(Exception e)
     {
       return null;
     }
   }

  /**
   * ����ַ����еĵ����ţ�������֣�����׷��һ��������
   *        �˺���������Ҫ�Ƿ�ֹ�������ݿ���ַ����ڰ��������Ż�����Ĵ�����"Tom's home"
   *        �����ʱ��д������:
   *            Update Tom set home = 'tom's home'  �����
   *            Update Tom set home = 'tom''s home' �������ȷ��
   * @param strSrc          Դ�ַ���
   * @return String
   */
   public static String checkSingleQuotes(String strSrc)
   {
     return strSrc.replaceAll("'", "\'");
   }
  
  /**
   * �����ֵ��ļ�
   * @param dicname
   */
  public static void createDicFile(String dicname)
  {
    String strDicPath = DicCache.getDicPath();
    createDicFile(dicname,strDicPath);
  }  
   
  /**
   * �����ֵ��ļ�
   * @param strDicName �ֵ�����
   * @param strDicPath �ֵ�·��
   */
  public static void createDicFile(String strDicName,
                                   String strDicPath)
  {
    try
    {
      
      String strFileName = strDicPath + Common.BAR + strDicName.toUpperCase() + Common.DOT + Common.XML_FILE_SUFFIX;
      DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(strFileName)));

      Document domresult = DocumentFactory.getInstance().createDocument();
      Element root = domresult.addElement("data");

      

      SpellCache spellcache = SpellCache.getInstance();

      if(strDicName.equals("USERLIST"))
      {
      	  String strSQL = "SELECT USERID,USERNAME USERNAME FROM USERLIST WHERE USERTYPE='10'";
      	  DBConnection dbc = new DBConnection();
      	  ResultSet rst = null;
      	  rst = dbc.excuteQuery(strSQL);
      	  while (rst.next())
          {  
  	        String sCode  = rst.getString("USERID");
  	        String sText  = rst.getString("USERNAME");
  	        
  	        String spell = spellcache.getSpell(sText);
  	        String aspell = spellcache.getASpell(sText);
  	        Element elerow = DocumentHelper.createElement(Common.XML_PROP_ROW);
  	        elerow.addAttribute(Field.DIC_CODE, sCode);
  	        elerow.addAttribute(Field.DIC_TEXT, sText);
  	        elerow.addAttribute(Field.DIC_SPELL, spell);
  	        elerow.addAttribute(Field.DIC_ASPELL, aspell);
  	        root.add(elerow);
    		  
          }
      }
      else
      {
	      ArrayList arr = DicCache.getInstance().getDicByName(strDicName);
	      Iterator it = arr.iterator();
	      while (it.hasNext())
	      {
	        String[] dics = (String[])it.next();
	        String sCode  = dics[0];
	        String sText  = dics[1];
	        String sValid = dics[2];
	        
	        if(sValid.equals("1"))
	        { 
	          String spell = spellcache.getSpell(sText);
	          String aspell = spellcache.getASpell(sText);
	          Element elerow = DocumentHelper.createElement(Common.XML_PROP_ROW);
	          elerow.addAttribute(Field.DIC_CODE, sCode);
	          elerow.addAttribute(Field.DIC_TEXT, sText);
	          elerow.addAttribute(Field.DIC_SPELL, spell);
	          elerow.addAttribute(Field.DIC_ASPELL, aspell);
	          root.add(elerow);
	        }
	      }
      }

      OutputStream os = out;
      OutputFormat of = new OutputFormat();
      of.setEncoding("UTF-8");
      of.setIndent(true);
      of.setNewlines(true);
      XMLWriter writer = new XMLWriter(os, of);

      writer.write(domresult);

      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * ͨ��sql���ֱ�Ӵ����ֵ�
   * @param dicname
   */
  public static void createDicFileSQL(String strSQL,String strDicName)
  {
    try
    {
      String strDicPath = DicCache.getDicPath();
      String strFileName = strDicPath + Common.BAR + strDicName.toUpperCase() + Common.DOT + Common.XML_FILE_SUFFIX;
      DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(strFileName)));

      Document domresult = DocumentFactory.getInstance().createDocument();
      Element root = domresult.addElement("data");

      SpellCache spellcache = SpellCache.getInstance();

      DBConnection dbc = new DBConnection();
      ResultSet rst = null;
      rst = dbc.excuteQuery(strSQL);
      while (rst.next())
      {  
        String sCode  = rst.getString(1);
        String sText  = rst.getString(2);
        
        String spell = spellcache.getSpell(sText);
        String aspell = spellcache.getASpell(sText);
        Element elerow = DocumentHelper.createElement(Common.XML_PROP_ROW);
        elerow.addAttribute(Field.DIC_CODE, sCode);
        elerow.addAttribute(Field.DIC_TEXT, sText);
        elerow.addAttribute(Field.DIC_SPELL, spell);
        elerow.addAttribute(Field.DIC_ASPELL, aspell);
        root.add(elerow);
      
      }
      OutputStream os = out;
      OutputFormat of = new OutputFormat();
      of.setEncoding("UTF-8");
      of.setIndent(true);
      of.setNewlines(true);
      XMLWriter writer = new XMLWriter(os, of);

      writer.write(domresult);

      out.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }  
   
  /**
   * ���ַ���ת����base64����
   * @param strValue        ��Ҫת�����ַ���
   * @return String          base64����
  */
  
  public static String strToBase64(String strValue)
  {
    
    String ret = new sun.misc.BASE64Encoder().encode(strValue.getBytes());
    return ret;
  }
 
  /**
   * ��base64����ת�����ַ���
   * @param strValue        ��Ҫת����base64����
   * @return String          ת������ַ���
  */
  public static String base64ToStr(String strValue) 
  {
    try
    {
      byte[] bt = new BASE64Decoder().decodeBuffer(strValue);
      String ret = "";
     
      for(int i=0;i<bt.length;i++)
      {
        ret += String.valueOf((char)bt[i]);
      }
      return ret;
    }
    catch(Exception e)
    {
      return null;
    }
  }
  
  /**
   * ��õ�ǰ���ڵ�2λ���
   * @return String            ��ǰ���ڵ�2λ����ַ���
  */
  public static String curYear2()
  {
    return curYear2(curYear4());
  }
 
  /**
   * ���ָ�����ڵ�2λ���
   * @param strDate     ��ʽΪyyyy����yyyy-mm-dd
   * @return String      �ַ���
  */
  public static String curYear2(String strDate)
  {
    if(strDate==null)
      return null;

    if(strDate.length()>=4)
      return strDate.substring(2,4);

    return null;
  }
 
  /**
   * ��õ�ǰ���ڵ�4λ���
   * @return String      ��ǰ���ڵ�4λ����ַ���
  */
  public static String curYear4()
  {
    return "" + DateTimeUtil.getCurrentYear();
  }  
  
  /**
   * ��õ�ǰ���ڵ��·�
   * @return String      ��ǰ���ڵ�2λ�·��ַ�������ʽΪmm
   */
  public static String curMonth()
  {
    String sMonth = "0" + DateTimeUtil.getCurrentMonth();

    if (sMonth.length()>2)
      return sMonth.substring(1);

    return sMonth;
  }
 
  /**
   * ��õ�ǰ���ڵ���
   * @return String      ��ǰ���ڵ�2λ�·��ַ�������ʽΪdd
   */
  public static String curDay()
  {
    String sDay = "0" + DateTimeUtil.getCurrentDay();

    if (sDay.length()>2)
      return sDay.substring(1);

    return sDay;
  }  

  /**
   * �������������ֵ��ļ�
   */
  public static void createAllDic()
  {
    DBConnection dbc = new DBConnection();
    ResultSet rst = null;
    try
      {
        /// ���Ȳ�ѯ�������ֵ������
        String str_SQL = Common.SELECT  + Field.DICNAME +
                         Common.S_FROM  + Table.DICLIST;
        
        rst = dbc.excuteQuery(str_SQL);

        /// �������ֵ����Ƽ��뵽����� HashTable ��
        while(rst.next())
        {  
          createDicFile(rst.getString(Field.DICNAME));
          System.out.println("�����ֵ��ļ���" + rst.getString(Field.DICNAME));
        }
        
        rst.close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }  
      finally
      {
        dbc.freeConnection();
      }
  }
  
  public static void main(String[] args)
  {
    try
    {
      System.out.println("A.001");
      
      // ���������ַ�����������Ӧ���ļ�
      byte[] bt = hex2byte("47494638396110001000f70000000000ffffffdee0f1bac6e7ccd7f2dde8f8dce7f7dadee4a5c1e8638fc3aac9efa9c3e4cedff3c3d1e30a64cb0c74e40b58b11e6dc74176af6894c47fafe37da7d484b0df8fb9e88eb8e58eb6e48db5e198bee9b0cff1b2cce9acc2daafc4dbcfddedd0ddecd4e0eec3cad20b74e01175db227ad42a76c23c9cfb2d70b4388adc429efb4098ee4999ea4797e64ea4f84ea3f6468ed54b95df4a91db509eeb4f9be64588ca54a5f45aadff4d94d94f92d5579fe9549be03e6fa15ca1e85290ce5898d763a9ed5a98d84372a15d9bdb5b98d76db7ff65a9ed619fe1558bc35e97d0639ed86fa8e4699fd56b9fd573aae376ace57db6ef75a8dd6995c27eacdc8cbef26f95bd93c1ef92c0ed91bdeaa2d1ff94bde79fcaf584a7cb8cb1d59bc2ea99c0e791b4d8acd4fb8daece97b8d891abc699b1caa3b8cdc5dbf2b0c3d7dbe0e52b8be53da3ff42a3ff49a9ff4caafe4b89c562b5ff4f8fcd60adf467b7ff65adf25d9edd6cbaff72bcff75b6f572ace488c5ff82baf083b8e88dc2f593c9fc8abded9cccf8a4d4ffa9d5ffa8d4fd93b7d9a6c5e2c0e0fdc1d6e9d4e8fad8eafad3e3f2b9c7d4c3ced849aaff4fb0ff59b4ff5fb7ff62b7ff75bfff75c1ff83c6ff8ac8fa9bd1ffbae0ffc5e4ffabc6ddd0e8fccce0f176c2ffe1f2ffeff8ffc1e5ffe8f6ffdef3ffd9f4ffeefcffe6fbfff2fdfff8fefff9fffffcffffd9dad8fcf7f1ffffff00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000021f904010000ac002c00000000100010000008e80059091cc88a820203041362c92260c0854316120a2c0048d4a60c181499f2d2801503821c2c7d3a952a40a84c5ca43039d26120823e9e4e25bae247cca04a943a3511a8e70b22549c7ed8d01144cb1e3a76ca08cc112000a9426758a9211345101d2123044648b0a8549803030d05ba736309185613204019b5e5c3c0105524b9792487d52a277c305d7ab24004080d73def0003285150124a008fd9944c3478d178e5c0ce96186d586074592ccc8130752241c6d5858613490480a090e76e081a1a28511144a1236624565459d1827d6b031d145222b342548c051a549c618df023d5448833c20003b");
      File bfile=new File("d:\\11.gif");           
      FileOutputStream fos = new FileOutputStream(bfile);
      fos.write(bt);   
      fos.close();  
    }
    catch(Exception e)
    {
      
    }
  }
  
}
