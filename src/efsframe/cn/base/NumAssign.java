package efsframe.cn.base;

import java.sql.*;

import efsframe.cn.db.*;

/**
 * ͳһ�ı�����������
 * �������ݿ�MAXIDTYPE,MAXID�����ش洢���̣�����Ψһ��������
 */
public class NumAssign
{
  /**
   * ����ָ�����͵�һ�������
   * @param strIDType       �������
   * @return string ���һ�����
   */
  public static String assignID_A(String strIDType) throws Exception
  {
    /// �������
    DBConnection dbc = new DBConnection();
    Connection conn = dbc.getConnection();

    try
    {
      conn.setAutoCommit(false);

      String[] para = {"t", strIDType, ""};

      int[] out = {3};

      Pub.executeProcedure(conn, "pMaxID_A", para, out);

      conn.commit();

      return para[2];
    }
    catch(Exception e)
    {
      try
      {
        if(conn!=null)
        {  
          conn.rollback();
        }
      }
      catch(Exception ex)
      {
        throw new Exception(ex.getMessage());
      }
      throw e;
    }
    finally
    {
      dbc.freeConnection();
    }
  }

  /**
   * ����ָ�����͵Ķ��������
   * @param strIDType       �������
   *        strMaxID1       һ�����
   * @return String          ���������
   */
  public static String assignID_B(String strIDType,
                                  String strMaxID1)
  {
    /// �������
    DBConnection dbc = new DBConnection();
    Connection conn = dbc.getConnection();

    try
    {
      conn.setAutoCommit(false);

      String[] para = {"t",strIDType,strMaxID1,""};

      int[] out = {4};

      Pub.executeProcedure(conn,"pMaxID_B",para,out);

      conn.commit();

      return para[3];
    }
    catch(Exception e)
    {
      try
      {
        if(conn!=null)
        {
          conn.rollback();
        }
      }
      catch(Exception ex)
      {
        return ex.getMessage();
      }
      return e.getMessage();
    }
    finally
    {
      dbc.freeConnection();
    }
  }

  /**
   * ����ָ�����͵����������
   * @param strIDType       �������
   *        strMaxID1       һ�����
   *        strMaxID2       �������
   * @return String         ����������
   */
  public static String assignID_C(String strIDType,
                                  String strMaxID1,
                                  String strMaxID2) throws Exception
  {
    /// �������
    DBConnection dbc = new DBConnection();
    Connection conn = dbc.getConnection();

    try
    {
      conn.setAutoCommit(false);

      String[] para = {"t",strIDType,strMaxID1,strMaxID2,""};
      
      int[] out ={5};

      Pub.executeProcedure(conn,"pMaxID_B",para,out);

      conn.commit();

      return para[4];
    }
    catch(Exception e)
    {
      try
      {
        if(conn!=null)
        {  
          conn.rollback();
        }
      }
      catch(Exception ex)
      {
        throw new Exception(ex.getMessage());
      }
      throw e;
    }
    finally
    {
      dbc.freeConnection();
    }
  }
  
  //����������
  public static void main(String []arg)
  {
    try
    {
      System.out.println(assignID_B("100002","201002"));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

}
