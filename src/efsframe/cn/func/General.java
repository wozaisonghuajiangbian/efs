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
 * 常用函数类，封装了各种常用函数 
 */
public class General
{
  /**
   * 判断字符串是否为空或者null
   * @param str         字符串
   * @return boolean
   */
  public static boolean empty(String str)
  {
    if(str==null) return true;
    if(str.equals("")) return true;
    return false;
  }
  
  /**
   * 获得默认数据库的时间格式字符串
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
		  return dbDatetime(Common.C_MSSQL); // 默认MS SQL
  }

  /**
   * 日期类型处理函数
   * @param strDbms         数据库类型
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
   * 将日期型函数转换为 19990101010101 格式的字符串
   * @param date         待处理的日期
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
 * @param value              待处理的日期字符串
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
 * 处理日期字符串,使之能成为与 Date 类型相兼容的的日期字符串
 *        能够处理的类型有:
 *            2001-5-5    ->      2001-5-5
 *            2001/5/5    ->      2001/5/5
 *            20010505    ->      2001-05-05
 * @param value             待处理的日期字符串
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
   * 处理日期时间字符串,使之能成为与 DateTime 类型相兼容的的日期时间字符串
   *        能够处理的类型有:
   *            2001-5-5 12:55:19    ->      2001-5-5 12:55:19
   *            2001/5/5 12:55:19    ->      2001/5/5 12:55:19
   *            20010505 12:55:19    ->      2001-05-05 12:55:19  返回: String
   * @param value             待处理的日期字符串
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
   * 格式化日期字符串
   * @param sdate       日期字符串
   * @return String
   */
  public static String formatDate(String sdate)
  {
    return formatDate(sdate, "yyyy-mm-dd");
  }
  
  /**
   * 格式化日期时间字符串
   * @param sdate       日期时间字符串
   * @return String
   */
  public static String formatDatetime(String sdate)
  {
    return formatDate(sdate, "yyyy-mm-dd" + Common.SPACE + "hh24:mi:ss");
  }
  
  /**
   * 格式化日期字符串
   * @param   sdate       日期
   * @param   pattern    格式化
   * @return String
   */ 
  public static String formatDate(String sdate, String pattern)
  {
	  if(DBConnection.JDBCTYPE.equalsIgnoreCase("2")) // oracle 格式
		  return "TO_DATE('" + sdate + "','" + pattern + "')";
    else if(DBConnection.JDBCTYPE.equalsIgnoreCase("3")) // Ms Sql 格式
      return "Cast('" + sdate + "' AS DATETIME)";
	  else
		  return "'" + sdate + "'";
  }
  
  /**
   * 将二进制数组转换为16进制字符串
   * @param b         二进制数组
   * @return String
   */
  public static String byte2hex(byte[] b)
  {
    String hs="";
    String stmp="";
    for (int n=0;n<b.length;n++)
    {
      stmp=(Integer.toHexString(b[n] & 0XFF));//toHexString:转16进制
      
      if (stmp.length()==1) hs=hs+"0"+stmp;
      else hs=hs+stmp;
      
      if (n<b.length-1) hs=hs+"";
    }
    return hs.toUpperCase(); // 转大写（16进制的A~F用大写输出）
  }
  
  
  /**
   * 将字符串转二进制数组
   * @param b         二进制数组
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
   * 根据不同的十六进制字符，得到其对应的二进制字符串。
   * @param cHex           十六进制字符
   * @return String        二进制字符串
   */
  public static String getBinstrFromHexchar(char cHex)
  {
    String strBin = "";

    /// 获取不同的二进制字符串
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
   * 根据十进制数得到相对应的十六进制字符。
   * @param iDec         十进制数
   * @return char        十六进制字符
   */
  public static char getHexcharFromDecnum(int iDec)
  {
    char cHex = 'x';

    /// 得到十进制字符
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
   * 根据字段类型转换
   * @param itype     字段类型
   * @param value     字段值
   * @param name      字段名称
   * @param list      包含 blob 的 list
   * @return 返回 解析好的 insert sql 语句
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
        case 0: return "'" + value.replaceAll("'","''") + "'";  // 字符串
        case 1: return General.empty(value)?"''":value;         // 数值
        case 2: return General.dbDatetime();                    // 系统时间
        case 3: return General.empty(value)?"''":General.formatDate(General.strToDate(value));         // 时间
        case 4: return General.empty(value)?"''":General.formatDatetime(General.strToDateTime(value)); // 日期时间型
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
   * 处理日期类型字段中的年龄类型，
   *        将其处理成为可以为数据库接收的合法的 SQL 语句
   *            20              =20
   *            20-             >=20
   *            -20             <=20
   *            20-40           BETWEEN 20 AND 40
   * @param name              年龄的值
   * @param value
   * @param oper
   * @return String              分析后的查询字句
   */
  public static String opYearDate(String name, String value, String oper)
  {
    int ipos = value.indexOf("-");
    if(ipos!=-1)
    {
      /// 分割符在前面
      if(ipos==0)
      {
        return "YEAROLD(" + name + ")" + "<=" + value.substring(1);
      }
      else
      {
        /// 分割符在最后
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
   * 处理日期类型的成为可以为数据库接收的合法的 SQL 语句
   *            20010101            =TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            20010101-           >=TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            -20010101           <=TO_DATE('2001-01-01', 'yyyy-mm-dd')
   *            20010101-20011231   (>=TO_DATE('2001-01-01', 'yyyy-mm-dd') AND <=TO_DATE('2001-12-31', 'yyyy-mm-dd'))
   * @param name               
   * @param value               
   * @param oper            
   * @return String                  分析后的查询字句
   */
  public static String opDate(String name,String value,String oper)
  {
    int ipos = value.indexOf("-");
    
    if(ipos!=-1)
    {
      ///   分割符在前面
      if(ipos==0)
      {
        return formatChar(name,"<=",value.substring(1));
      }
      else
      {
        ///     分割符在后面
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
   * 返回 to_char(date,patter) 形式的语句
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
   * 返回 TO_CHAR(date,patter) 形式的语句，供 Oracle 使用
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
   * 为一个字符串两端加上单引号
   * @param value                      源字符串
   * @return String
   */
  public static String addQuotes(String value)
  {
    return "'" + value + "'";
  }
 
  /**
   * 为一个字符串两端加上圆括号
   * @param value                      源字符串
   * @return String
   */
  public static String addBracket(String value)
  {
    return "(" + value + ")";
  }

  /**
   * 为一个字符串两端加上圆括号
   * @param value                      源字符串
   * @return String
   */
  public static String addBracket(CharSequence value)
  {
    return "(" + value + ")";
  }
  
  /**
   * 将日期型函数转换为 19990101 格式的字符串
   * @param date         待处理的日期
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
   * 检查字符串中的单引号，如果发现，则再追加一个单引号
   *        此函数功能主要是防止存入数据库的字符串内包含单引号会引起的错误，如"Tom's home"
   *        在入库时会写成这样:
   *            Update Tom set home = 'tom's home'  会出错
   *            Update Tom set home = 'tom''s home' 这才是正确的
   * @param strSrc          源字符串
   * @return String
   */
   public static String checkSingleQuotes(String strSrc)
   {
     return strSrc.replaceAll("'", "\'");
   }
  
  /**
   * 创建字典文件
   * @param dicname
   */
  public static void createDicFile(String dicname)
  {
    String strDicPath = DicCache.getDicPath();
    createDicFile(dicname,strDicPath);
  }  
   
  /**
   * 创建字典文件
   * @param strDicName 字典名字
   * @param strDicPath 字典路径
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
   * 通过sql语句直接创建字典
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
   * 将字符串转换成base64编码
   * @param strValue        需要转换的字符串
   * @return String          base64编码
  */
  
  public static String strToBase64(String strValue)
  {
    
    String ret = new sun.misc.BASE64Encoder().encode(strValue.getBytes());
    return ret;
  }
 
  /**
   * 将base64编码转换成字符串
   * @param strValue        需要转换的base64编码
   * @return String          转换后的字符串
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
   * 获得当前日期的2位年份
   * @return String            当前日期的2位年份字符串
  */
  public static String curYear2()
  {
    return curYear2(curYear4());
  }
 
  /**
   * 获得指定日期的2位年份
   * @param strDate     格式为yyyy；或yyyy-mm-dd
   * @return String      字符串
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
   * 获得当前日期的4位年份
   * @return String      当前日期的4位年份字符串
  */
  public static String curYear4()
  {
    return "" + DateTimeUtil.getCurrentYear();
  }  
  
  /**
   * 获得当前日期的月份
   * @return String      当前日期的2位月份字符串，格式为mm
   */
  public static String curMonth()
  {
    String sMonth = "0" + DateTimeUtil.getCurrentMonth();

    if (sMonth.length()>2)
      return sMonth.substring(1);

    return sMonth;
  }
 
  /**
   * 获得当前日期的日
   * @return String      当前日期的2位月份字符串，格式为dd
   */
  public static String curDay()
  {
    String sDay = "0" + DateTimeUtil.getCurrentDay();

    if (sDay.length()>2)
      return sDay.substring(1);

    return sDay;
  }  

  /**
   * 批量生成所有字典文件
   */
  public static void createAllDic()
  {
    DBConnection dbc = new DBConnection();
    ResultSet rst = null;
    try
      {
        /// 首先查询出所有字典的名称
        String str_SQL = Common.SELECT  + Field.DICNAME +
                         Common.S_FROM  + Table.DICLIST;
        
        rst = dbc.excuteQuery(str_SQL);

        /// 将所有字典名称记入到缓存的 HashTable 中
        while(rst.next())
        {  
          createDicFile(rst.getString(Field.DICNAME));
          System.out.println("生成字典文件：" + rst.getString(Field.DICNAME));
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
      
      // 将二进制字符串保存问相应的文件
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
