package efsframe.cn.classes;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import efsframe.cn.base.CommonQuery;
import efsframe.cn.base.NumAssign;
import efsframe.cn.base.QueryDoc;
import efsframe.cn.db.DataStorage;
import efsframe.cn.db.SQLAnalyse;
import efsframe.cn.declare.Common;
import efsframe.cn.declare.Field;
import efsframe.cn.declare.Table;
import efsframe.cn.func.General;

public class ClassesBO {
	/**
	 * ��ȡ��ڵ�
	 * 
	 * @param strXml
	 * @return ��ڵ�Ԫ��
	 * @throws DocumentException
	 */
	private Element getTabNoe(String strXml) throws DocumentException {
		Document doc = DocumentHelper.parseText(strXml);

		Element ele = (Element) doc.selectSingleNode("//DATAINFO/" + Table.CLASSES);

		return ele;
	}

	/**
	 * �����༶
	 * 
	 * @param strXml
	 *            DATAINFOΪ���ڵ��xml����
	 * @return null���ߴ�����Ϣ
	 */
	public String add(String strXml) {
		String strRet = null;
		try {
			// ��ȡ��ڵ�
			Element ele = getTabNoe(strXml);
			// ���ñ��
			String strId = NumAssign.assignID_A("100000");
			ele.element("CID").setText(strId);

			// ����ִ�ж���
			DataStorage storage = new DataStorage();
			storage.addSQL(SQLAnalyse.analyseXMLSQL(ele));

			strRet = storage.runSQL();
      
      General.createDicFileSQL("SELECT CID,CNAME FROM CLASSES ORDER BY CID", "DIC_CLASSES");

		}
    catch (Exception e) {
			strRet = "" + e.getMessage();
		}

		return strRet;
	}

	public String list(String strXml) throws Exception {
		// �����׼��ѯXML�ӿڷ��������
		QueryDoc obj_Query = new QueryDoc(strXml);
		int int_PageSize = obj_Query.getIntPageSize();

		int int_CurrentPage = obj_Query.getIntCurrentPage();

		// ��ѯ�ֵ�
		String str_Select = "s.CID CID,s.CNAME CNAME,s.CMEMO CMEMO";
		// ��ѯ��
		String str_From = Table.CLASSES + Common.SPACE + Table.S;
		// ������׼�Ĳ�ѯ����
		String str_Where = obj_Query.getConditions();
		str_Where = General.empty(str_Where) ? str_Where : Common.WHERE
				+ str_Where;

		// ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
		return CommonQuery.basicListQuery(str_Select, str_From, str_Where,"CID", null, int_PageSize, int_CurrentPage);
	}

	/**
	 * ���ݰ༶��Ų�ѯ�༶��ϸ��Ϣ
	 * @param cid �༶���
	 * @return ��ϸ��Ϣ
	 * @throws Exception
	 */
	public String detail(String cid) throws Exception {
		String str_Select = "s.CID CID,s.CNAME CNAME,s.CMEMO CMEMO";
		// ��ѯ��
		String str_From = Table.CLASSES + Common.SPACE + Table.S;
		// ������׼�Ĳ�ѯ����
		String str_Where = Common.WHERE + "cid = '" + cid + "'";
		
		return CommonQuery.basicListQuery(str_Select, 
                str_From, 
                str_Where,
                "CID",
                null, 
                null, null, 1, 1, 1, 1, 0, true,
                Table.CLASSES);
	}

	/**
	 * ���༶��ŵ�ѧ���б�
	 * @param strXml
	 * @return
	 * @throws Exception
	 */
	public String personList(String strXml) throws Exception {
	      // �����׼��ѯXML�ӿڷ��������
	      QueryDoc obj_Query = new QueryDoc(strXml);
	      int int_PageSize = obj_Query.getIntPageSize();

	      int int_CurrentPage = obj_Query.getIntCurrentPage();

	      // ��ѯ�ֵ�
	      String str_Select = "s.PERSONID PERSONID,s.NAME NAME,s.IDCARD IDCARD,s.SEX SEX,s.PLACECODE PLACECODE,s.BIRTHDAY BIRTHDAY,s.TEL TEL,r.cid cid";
	      // ��ѯ��
	      String str_From   = Table.PERSON + Common.SPACE + Table.S + ",classesref r";
	      // ������׼�Ĳ�ѯ����
	      String str_Where = obj_Query.getConditions();
	      
	      str_Where ="where s.personid=r.personid(+) and (r.cid is null or " + str_Where + ")";
	      
	      // �������ֶ��б�
	      String[] str_DateList  = {Field.BIRTHDAY};
	      
	      // ��׼�ġ�ͳһ�ķ�ҳ��ѯ�ӿ�
	      return CommonQuery.basicListQuery(str_Select,
	                                        str_From,
	                                        str_Where,
	                                        "s." + Field.PERSONID,
	                                        str_DateList,
	                                        int_PageSize,
	                                        int_CurrentPage);
	}

}
