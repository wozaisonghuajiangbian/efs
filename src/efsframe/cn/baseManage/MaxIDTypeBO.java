package efsframe.cn.baseManage;

import efsframe.cn.base.Operation;

public class MaxIDTypeBO {

  /*********************************************************
   * ��ӡ��޸ġ�ɾ���������
   * @param strXml XML ������Ϣ
   * @return String XML ������Ϣ
  ***********************************************************/
  public static String dealXml(String strXml) throws Exception
  {
    
    String xml = Operation.dealWithXml(strXml);
    return xml;
  }
}
