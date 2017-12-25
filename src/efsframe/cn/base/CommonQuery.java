package efsframe.cn.base;

import java.util.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import org.dom4j.*;
import oracle.jdbc.OracleTypes;
import efsframe.cn.declare.*;
import efsframe.cn.db.*;
import efsframe.cn.func.General;
import efsframe.cn.cache.*;

/**
 * ͨ�ò�ѯ�����࣬��װ����ͨ��ѯ����ҳ��ѯ�ȹ�������
 * @author enjsky
 */
public class CommonQuery
{  
  /**
   * ͨ�ò�ѯ
   * @param  strSQL                T-SQL��ѯ���
   * @return ResultSet             ��ѯ���ؽ����
   */
  public static ResultSet rsQry(String strSQL) throws Exception
  {
    /// �������
    DBConnection dbc = new DBConnection();

    try
    {
      ResultSet rst = dbc.excuteQuery(strSQL);
      return rst;
    }
    catch(Exception e)
    {
      throw e;
    }
    finally
    {
      dbc.freeConnection();
    }
  }
  /*
   * ���ݼ�¼����ò�ѯXml��������
   * @param �ú������ܴ���ͨ��������������ֵ䷭��Ĺ���
   * @author Enjsky
   * @param rst                 ������Ľ����¼������
   * @param strDicFieldList     �������ֵ��ֶ��б�
   *                            ���磺SEX,NATION
   * @param strDicNameList      �������ֵ������б�
   *                            ���磺DIC_SEX,DIC_NATIVE
   *                            ע�⣺strDicFieldList��strDicNameList����һһ��Ӧ 
   * @param   strDateFieldList    ��������������ֶ��б�
   *                            ���磺BIRTHDAY,COMEDATE
   * @param int_TotalRecords    ��¼����
   *                            ���磺0
   * @param int_TotalPages      ��¼��ҳ��
   *                            ���磺0
   * @param ListName            �����б�����
   *                            ���磺ROW����AFFAIRTYPE��
   * @return String             ��׼�Ĳ�ѯ XML ���ؽṹ���ַ���
   */
  public static String getQueryXml(ResultSet rst,
								  String[] strDicFieldList,
						          String[] strDicNameList,
						          String[] strDateFieldList,
						          int int_TotalRecords,
						          int int_TotalPages,
						          boolean blnDicInSv,
						          String strListName) throws Exception
  {
	  strListName = strListName==null ? Common.XDOC_ROW : strListName;
		
	  ReturnDoc docReturn = new ReturnDoc();
      try
      {
    	  if (docReturn.getQueryInfo(rst, strListName))
	      {            
          Document doc = docReturn.getDocument();
	        List list = doc.selectNodes(Common.BAR2 + strListName);
	        Iterator it = list.iterator();
	
	        while (it.hasNext())
	        {  
	          Element ele = (Element)it.next();
	          
	          Element ele_Temp;
	          String str_Text;
	
	          /// �����ֵ�ķ���
	          DicCache obj_DicCache = DicCache.getInstance();
	
	          if (strDicFieldList!=null)
	          {  
	            for (int i=0; i<strDicFieldList.length; i++)
	            {
	              ele_Temp = (Element)ele.selectSingleNode(strDicFieldList[i]);
	              str_Text = ele_Temp.getText().trim();
	
	              if (General.empty(str_Text)) continue;
	
	              String str_SV = obj_DicCache.getText(strDicNameList[i], str_Text); 
	              		              
	
	              if (!blnDicInSv)
	              {
	                if (str_SV!=null) ele_Temp.setText(str_SV); 
	              }
	              else
	              {
	                if (str_SV!=null) ele_Temp.addAttribute(Common.XML_PROP_SV, str_SV);
	              }
	            }
	          }
	
	          /// �����������͵��ֶ�
	          if (strDateFieldList!=null)
	          {
	            for(int i=0; i<strDateFieldList.length; i++)
	            {
	              ele_Temp = (Element)ele.selectSingleNode(strDateFieldList[i]);
	              str_Text = ele_Temp.getText();
	              
	              if(General.empty(str_Text)) continue;
	
	              String str_SV = General.cDateStr(str_Text);
	              str_Text = General.strToDate(str_SV);
                  ele_Temp.setText(str_Text);
	
 	              // String str_SV = ele_Temp.attributeValue(Common.XML_PROP_SV) ;
	              // str_SV = DateTimeUtil.getDateTime(DateTimeUtil.parse(str_SV, "yyyyMMdd"), "yyyy��MM��dd��") ;
	              if (blnDicInSv)
	              {
	                  
	            	  if (str_SV!=null) ele_Temp.addAttribute(Common.XML_PROP_SV, str_SV);
	              }
	             }
	          }
	        }
	        docReturn.addErrorResult(Common.RT_QUERY_SUCCESS);
	      }
	      else
	      {
	        docReturn.addErrorResult(Common.RT_QUERY_NOTHING);
	      }
	      docReturn.addPropToQueryInfo(Common.XML_PROP_TOTALPAGES, String.valueOf(int_TotalPages));
	      docReturn.addPropToQueryInfo(Common.XML_PROP_RECORDS, String.valueOf(int_TotalRecords));
	      
      }
      catch(Exception e)
      {
    	e.printStackTrace();  
      }
      return docReturn.getXML();
  }
  /**
   * �����ķ�ҳ��ѯ
   * �ú���ͬʱ֧��Oracle��Ms Sql 2005�ķ�ҳ�洢���̲�ѯ
   * @param strFieldList        ��ѯ�ֶ��б�
   *                            ���磺s.WORD WORD,s.SPELL SPELL,s.ASPELL ASPELL
   * @param strTableList        ��ѯ���б�
   *                            ���磺SPELL s
   * @param strOtherClause      ������ѯ���
   *                            ���磺WHERE s.WORD='��'
   * @param strOrdFld           �����ֶ�
   *                            ���磺PERSONID DESC
   * @param strDicFieldList     �������ֵ��ֶ��б�
   *                            ���磺SEX,NATION
   * @param strDicNameList      �������ֵ������б�
   *                            ���磺DIC_SEX,DIC_NATIVE
   *                            ע�⣺strDicFieldList��strDicNameList����һһ��Ӧ 
   * @param strDateFieldList    ��������������ֶ��б�
   *                            ���磺BIRTHDAY,COMEDATE
   * @param intTotalRecords     ��¼����
   *                            ���磺0
   * @param intPageSize         ÿҳ��¼��
   *                            ���磺50
   * @param intCurrentPage      ����ѯ��ҳ��
   *                            ���磺2
   * @param intCountTotal       �Ƿ���Ҫͳ�Ƽ�¼����
   *                            0 - ����ͳ�Ƽ�¼����
   *                                �ڵ�һ�β�ѯ���Ѿ������һ�μ�¼������
   *                                ��ҳ����ͳ�ƺ�Ϳ��Բ����ٴε�ͳ��������
   *                            1 - ��Ҫͳ�Ƽ�¼����
   * @param strListName            �����б�����
   *                            ���磺ROW����AFFAIRTYPE��
   * @return String             ��׼�Ĳ�ѯ XML ���ؽṹ���ַ���
   */
  public static String basicListQuery(String strFieldList,
                                      String strTableList,
                                      String strOtherClause,
                                      String strOrdFld,
                                      String[] strDicFieldList,
                                      String[] strDicNameList,
                                      String[] strDateFieldList,
                                      int intTotalRecords,
                                      int intTotalPages,
                                      int intPageSize,
                                      int intCurrentPage,
                                      int intCountTotal,
                                      boolean blnDicInSv,
                                      String strListName) throws Exception
  {
    /// �������
    DBConnection dbc = new DBConnection();
    String strReturnXml = "";
    try
    {
      String str_FieldList = strFieldList.replaceAll(Common.MARK, Common.QUOTE);
      String str_TableList = strTableList.replaceAll(Common.MARK, Common.QUOTE);
      
      String str_OtherClause = null;
      String str_OrdFld = null;

      if (!General.empty(strOtherClause))
      {
        str_OtherClause = strOtherClause.replaceAll(Common.MARK,Common.QUOTE);
      }

      if (!General.empty(strOrdFld))
      {
    	  str_OrdFld = strOrdFld.replaceAll(Common.MARK,Common.QUOTE);
      }

      int int_TotalRecords = intTotalRecords;
      int int_TotalPages   = intTotalPages; 
      
      Connection conn = dbc.getConnection();
      CallableStatement proc = null;
      
      try
      {
    	  ResultSet rst = null;
    	  
    	  if(DBConnection.JDBCTYPE.equalsIgnoreCase("2")) // Oracle 
	      {
    		  // ��oracle�� �����where������һ��
  	      if (!General.empty(str_OrdFld))
  	      {
  	    	  if(!General.empty(str_OtherClause))
  	    		  str_OtherClause = str_OtherClause + Common.SPACE + Common.ORDER + str_OrdFld;
  	    	  else
  	    		  str_OtherClause = Common.SPACE + Common.ORDER + str_OrdFld;
  	      }
    		  
    		  StringBuffer Statement = new StringBuffer("{CALL QueryPagination(?,?,?,?,?,?,?,?,?,?)}");
		      
		      proc = conn.prepareCall(Statement.toString());
		      
		      proc.registerOutParameter(5, Types.NUMERIC);
		      proc.registerOutParameter(6, Types.NUMERIC);
		      proc.registerOutParameter(10, OracleTypes.CURSOR);
		      
		      proc.setObject(1, "t");
		
		      if (General.empty(str_FieldList))
		        proc.setNull(2, Types.VARCHAR);
		      else
		        proc.setObject(2, str_FieldList);
		
		      if (General.empty(str_TableList))
		        proc.setNull(3, Types.VARCHAR);
		      else
		        proc.setObject(3, str_TableList);
		      
		      if (General.empty(str_OtherClause))
		        proc.setNull(4, Types.VARCHAR);
		      else
		        proc.setObject(4, str_OtherClause);
		     
		      proc.setInt(5, int_TotalRecords);
		      proc.setInt(6, int_TotalPages);
		      proc.setInt(7, intPageSize);
		      proc.setInt(8, intCurrentPage);
		      proc.setInt(9, intCountTotal);
		
		      proc.execute();
			      
		      rst = (ResultSet)proc.getObject(10);

		      int_TotalRecords = proc.getInt(5);
		      int_TotalPages   = proc.getInt(6);
	      }
    	  else if(DBConnection.JDBCTYPE.equalsIgnoreCase("3")) // Ms Sql Server 2005
    	  {
	    	  StringBuffer Statement = new StringBuffer("{CALL dbo.QueryPagination(?,?,?,?,?,?,?,?,?,?)}");
		      
		      proc = conn.prepareCall(Statement.toString());
		      

		      proc.setObject(1, "t");
		      if (General.empty(str_FieldList))
			        proc.setNull(2, Types.VARCHAR);
		      else
		        proc.setObject(2, str_FieldList);
		
		      if (General.empty(str_TableList))
		        proc.setNull(3, Types.VARCHAR);
		      else
		        proc.setObject(3, str_TableList);
		      
		      if (General.empty(str_OtherClause))
		        proc.setNull(4, Types.VARCHAR);
		      else
		        proc.setObject(4, str_OtherClause);
		     
		      if (General.empty(str_OrdFld))
			        proc.setNull(5, Types.VARCHAR);
			      else
			        proc.setObject(5, str_OrdFld);

		      proc.setInt(6, intCurrentPage);
		      proc.setInt(7, intPageSize);
		      proc.setInt(8, intCountTotal);

		      proc.setInt(9, int_TotalRecords);
		      proc.setInt(10, int_TotalPages);
		      proc.registerOutParameter(9, Types.NUMERIC);
		      proc.registerOutParameter(10, Types.NUMERIC);
		      proc.execute();
		      /*
		       * java �ڶ�ȡsql server �Ĵ洢���̷��ؼ�¼����ʱ��
		       * ��out�����Ķ�ȡһ��Ҫ�� proc.executeQuery() ֮ǰ��ɣ�
		       * ���� �ᱨ rst Ϊ�յĴ���
		       */
		      int_TotalRecords = proc.getInt(9);
		      int_TotalPages   = proc.getInt(10);
		      
		      rst = proc.executeQuery();
    	  }
        else if(DBConnection.JDBCTYPE.equalsIgnoreCase("4")) // MySql
        {
          if (!General.empty(str_OrdFld))
          {
            if(!General.empty(str_OtherClause))
              str_OtherClause = str_OtherClause + Common.SPACE + Common.ORDER + str_OrdFld;
            else
              str_OtherClause = Common.ORDER + str_OrdFld;
          }
          
          // ��ѯ�ܼ�¼��
          String strSQL = "SELECT COUNT(1) FROM " + strTableList +  Common.SPACE + str_OtherClause;
          rst = dbc.excuteQuery(strSQL);
          while(rst.next())
          {  
            int_TotalRecords = Integer.parseInt(rst.getString(1));
          }
          rst.close();
          
          // ������ҳ��
          if(int_TotalRecords % intPageSize == 0)
          {
            int_TotalPages = int_TotalRecords / intPageSize;
          }
          else
          {
            int_TotalPages = int_TotalRecords / intPageSize + 1;
          }
          
          strSQL = "SELECT " + strFieldList + " FROM " + strTableList +  Common.SPACE + str_OtherClause + " LIMIT " + (intCurrentPage - 1) * intPageSize + "," + intPageSize;
          rst = dbc.excuteQuery(strSQL);
        }
	      
    	  strReturnXml = getQueryXml(rst,
            									      strDicFieldList,
            							          strDicNameList,
            							          strDateFieldList,
            							          int_TotalRecords,
            							          int_TotalPages,
            							          blnDicInSv,
            							          strListName);
    	  rst.close();
      }
      finally
      {
    	  if(proc != null)
    	    proc.close();
      }
      return strReturnXml;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      dbc.freeConnection();
    }
  }
  /**
   * �����ķ�ҳ��ѯ
   * �ú������ܴ���ͨ��������������ֵ䷭��Ĺ���
   * @param strFieldList        ��ѯ�ֶ��б�
   *                            ���磺s.WORD WORD,s.SPELL SPELL,s.ASPELL ASPELL
   * @param strTableList        ��ѯ���б�
   *                            ���磺SPELL s
   * @param strOtherClause      ������ѯ���
   *                            ���磺WHERE s.WORD='��'
   * @param strOrdFld           �����ֶ�
   *                            ���磺PERSONID DESC
   * @param strDicFieldList     �������ֵ��ֶ��б�
   *                            ���磺SEX,NATION
   * @param strDicNameList      �������ֵ������б�
   *                            ���磺DIC_SEX,DIC_NATIVE
   *                            ע�⣺strDicFieldList��strDicNameList����һһ��Ӧ 
   * @param strDateFieldList    ��������������ֶ��б�
   *                            ���磺BIRTHDAY,COMEDATE
   * @param intTotalRecords     ��¼����
   *                            ���磺0
   * @param intPageSize         ÿҳ��¼��
   *                            ���磺50
   * @param intCurrentPage      ����ѯ��ҳ��
   *                            ���磺2
   * @param intCountTotal       �Ƿ���Ҫͳ�Ƽ�¼����
   *                            0 - ����ͳ�Ƽ�¼����
   *                                �ڵ�һ�β�ѯ���Ѿ������һ�μ�¼������
   *                                ��ҳ����ͳ�ƺ�Ϳ��Բ����ٴε�ͳ��������
   *                            1 - ��Ҫͳ�Ƽ�¼����
   * @param strListName            �����б�����
   *                            ���磺ROW����AFFAIRTYPE��
   * @return String              ��׼�Ĳ�ѯ XML ���ؽṹ���ַ���
   */
  public static String basicListQuery(String strFieldList,
                                      String strTableList,
                                      String strOtherClause,
                                      String strOrdFld,
                                      String[] strDicFieldList,
                                      String[] strDicNameList,
                                      String[] strDateFieldList,
                                      String[] strDateTimeFieldList,
                                      int intTotalRecords,
                                      int intTotalPages,
                                      int intPageSize,
                                      int intCurrentPage,
                                      int intCountTotal,
                                      boolean blnDicInSv,
                                      String strListName) throws Exception
  {
    strListName = strListName==null ? Common.XDOC_ROW : strListName;

    /// �������
    DBConnection dbc = new DBConnection();

    ReturnDoc docReturn = new ReturnDoc();
    try
    {
      String str_FieldList = strFieldList.replaceAll(Common.MARK, Common.QUOTE);
      String str_TableList = strTableList.replaceAll(Common.MARK, Common.QUOTE);
      
      String str_OtherClause = null;

      if (!General.empty(strOtherClause))
      {
        str_OtherClause = strOtherClause.replaceAll(Common.MARK,Common.QUOTE);
      }

      int int_TotalRecords = intTotalRecords;
      int int_TotalPages   = intTotalPages; 
      
      Connection conn = dbc.getConnection();
      CallableStatement proc = null;
      
      try
      {
	      StringBuffer Statement = new StringBuffer("{CALL QueryPagination(?,?,?,?,?,?,?,?,?,?)}");
	      
	      proc = conn.prepareCall(Statement.toString());
	      
	      proc.registerOutParameter(5, Types.NUMERIC);
	      proc.registerOutParameter(6, Types.NUMERIC);
	      proc.registerOutParameter(10, OracleTypes.CURSOR);
	      proc.setObject(1, "t");
	
	      if (General.empty(str_FieldList))
	        proc.setNull(2, Types.VARCHAR);
	      else
	        proc.setObject(2, str_FieldList);
	
	      if (General.empty(str_TableList))
	        proc.setNull(3, Types.VARCHAR);
	      else
	        proc.setObject(3, str_TableList);
	      
	      if (General.empty(str_OtherClause))
	        proc.setNull(4, Types.VARCHAR);
	      else
	        proc.setObject(4, str_OtherClause);
	     
	      proc.setInt(5, int_TotalRecords);
	      proc.setInt(6, int_TotalPages);
	      proc.setInt(7, intPageSize);
	      proc.setInt(8, intCurrentPage);
	      proc.setInt(9, intCountTotal);
	
	      proc.execute();
	
//	      ResultSet rst = ((oracle.jdbc.driver.OracleCallableStatement)proc).getCursor(10);
	      ResultSet rst = (ResultSet)proc.getObject(10);
	      try {
					int_TotalRecords = proc.getInt(5);
					int_TotalPages = proc.getInt(6);

					if (docReturn.getQueryInfo(rst, strListName)) {
						Document doc = docReturn.getDocument();
						List list = doc.selectNodes(Common.BAR2 + strListName);
						Iterator it = list.iterator();

						while (it.hasNext()) {
							Element ele = (Element) it.next();

							Element ele_Temp;
							String str_Text;

							// / �����ֵ�ķ���
							DicCache obj_DicCache = DicCache.getInstance();

							if (strDicFieldList != null) {
								for (int i = 0; i < strDicFieldList.length; i++) {
									ele_Temp = (Element) ele
											.selectSingleNode(strDicFieldList[i]);
									str_Text = ele_Temp.getText().trim();

									if (General.empty(str_Text))
										continue;

									String str_SV = obj_DicCache.getText(
											strDicNameList[i], str_Text);


									if (!blnDicInSv) {
										if (str_SV != null)
											ele_Temp.setText(str_SV);
									} else {
										if (str_SV != null)
											ele_Temp.addAttribute(
													Common.XML_PROP_SV, str_SV);
									}
								}
							}

							// / �����������͵��ֶ�
							if (strDateFieldList != null) {
								for (int i = 0; i < strDateFieldList.length; i++) {
									ele_Temp = (Element) ele
											.selectSingleNode(strDateFieldList[i]);
									str_Text = ele_Temp.getText();

									if (General.empty(str_Text))
										continue;

									str_Text = General.cDateStr(str_Text);

									if (str_Text != null) {
										str_Text = General.strToDate(str_Text);
										ele_Temp.setText(str_Text);
									}
								}
							}
							
							/// ��������ʱ���������͵��ֶ�
							if (strDateTimeFieldList != null) {
								for (int i = 0; i < strDateTimeFieldList.length; i++) {
									String sv="" ;
									ele_Temp = (Element) ele
											.selectSingleNode(strDateTimeFieldList[i]);
									str_Text = ele_Temp.getText();

									if (General.empty(str_Text))
										continue;

									str_Text = General.strToDateTime(str_Text);

									if (str_Text != null) {
										sv = General.cDateTimeStr(str_Text);
										ele_Temp.setText(str_Text);
										XmlFunc.setAttrValue(ele_Temp, "sv", sv) ;
									}
								}
							}
						}						
						docReturn.addErrorResult(Common.RT_QUERY_SUCCESS);
					}
          else {
						docReturn.addErrorResult(Common.RT_QUERY_NOTHING);
					}
					docReturn.addPropToQueryInfo(Common.XML_PROP_TOTALPAGES, String.valueOf(int_TotalPages));
					docReturn.addPropToQueryInfo(Common.XML_PROP_RECORDS,String.valueOf(int_TotalRecords));
				} finally {
					rst.close();
				}
      }
      finally
      {
    	  proc.close();
      }
      return docReturn.getXML();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      dbc.freeConnection();
    }
  }
      
  public static String basicListQuery(String strFieldList,
                                      String strTableList,
                                      String strOtherClause,
                                      String strOrdFld,
                                      String[] strDicFieldList,
                                      String[] strDicNameList,
                                      String[] strDateFieldList,
                                      int intTotalRecords,
                                      int intTotalPages,
                                      int intPageSize,
                                      int intCurrentPage,
                                      int intCountTotal) throws Exception
  {
    return basicListQuery(strFieldList,
                          strTableList,
                          strOtherClause,
                          strOrdFld,
                          strDicFieldList,
                          strDicNameList,
                          strDateFieldList,
                          intTotalRecords,
                          intTotalPages,
                          intPageSize,
                          intCurrentPage,
                          intCountTotal,
                          false,
                          null);
   
  }

  public static String basicListQuery(String strFieldList,
                    										String strTableList, 
                    										String strOtherClause,
                                        String strOrdFld,
                    										String[] strDicFieldList, 
                    										String[] strDicNameList,
                    										String[] strDateFieldList, 
                    										String[] strDateTimeFieldList,
                    										int intTotalRecords, 
                    										int intTotalPages, 
                    										int intPageSize,
                    										int intCurrentPage, 
                    										int intCountTotal) throws Exception {
		return basicListQuery(strFieldList, 
          								strTableList, 
          								strOtherClause,
          								strOrdFld,
          								strDicFieldList, 
          								strDicNameList, 
          								strDateFieldList,
          								strDateTimeFieldList, 
          								intTotalRecords, 
          								intTotalPages,
          								intPageSize, 
          								intCurrentPage, 
          								intCountTotal, 
          								false, 
          								null);

	}
  
  
  public static String basicListQuery(String strFieldList,
      String strTableList,
      String strOtherClause,
      String strOrdFld,
      String[] strDateFieldList,
      int intPageSize,
      int intCurrentPage) throws Exception
  {
  return basicListQuery(strFieldList,
                        strTableList,
                        strOtherClause,
                        strOrdFld,
                        null,
                        null,
                        strDateFieldList,
                        0,
                        0,
                        intPageSize,
                        intCurrentPage,
                        0,
                        false,
                        null);
  
  }
  
  public static void main(String []arg)
  {
    DBConnection dbc = new DBConnection();
    ResultSet rst = null;
    try
    {
      String str_SQL = "SELECT COUNT(1) FROM AFFAIRTYPE s ORDER BY AFFAIRTYPEID";
      
      rst = dbc.excuteQuery(str_SQL);
      while(rst.next())
      {  
        String cc = rst.getString(1);
        System.out.println(cc);
      }
      rst.close();
      
      String strSQL = "SELECT COUNT(1) FROM AFFAIRTYPE s ORDER BY AFFAIRTYPEID";
      rst = rsQry(strSQL);
      while(rst.next())
      {  
        String cc = rst.getString(1);
        System.out.println(cc);
      }
      rst.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

}

