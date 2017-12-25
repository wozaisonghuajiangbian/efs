package efsframe.cn.cache;

import java.util.*;
import efsframe.cn.declare.*;
import efsframe.cn.func.*;
import efsframe.cn.db.DBConnection;
import efsframe.cn.db.DataStorage;

import java.sql.*;

public class SpellCache implements java.io.Serializable
{
  private static final long serialVersionUID = -8208755962314651055L;
  private static SpellCache obj_SpellCache;

  static ArrayList<Spell> arr_Spell = null;

  /**
   * ʵ����ʼ��
   */
  private SpellCache()
  {
    try
    {
      if(obj_SpellCache==null)
        init();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      obj_SpellCache = null;
    }
  }
  
  /**
   * ���ƴ�������ʵ��
   * @return ƴ�������ʵ��
   */
  public static synchronized SpellCache getInstance()
  {
    if(obj_SpellCache == null)
    {
      obj_SpellCache = new SpellCache();
    }

    return obj_SpellCache;
  }
  
  /**
   * ƴ���������ݵĳ�ʼ��
   */
  private void init() throws Exception
  {
    DBConnection dbc = new DBConnection();
	ResultSet rst = null;
	try
	{
      /// ��ѯ��ƴ������
      String str_SQL = Common.SELECT  + Field.WORD   + Common.COMMA +
                                        Field.SPELL  + Common.COMMA +
                                        Field.ASPELL +
                       Common.S_FROM  + Table.SPELL  +
                       Common.S_ORDER + Field.WORD;

      /// ��ѯ
      rst = dbc.excuteQuery(str_SQL);

      /// ��ʼ�� arr_Spell
      arr_Spell = new ArrayList<Spell>();

      while(rst.next())
      {  
        char chr_Word     = rst.getString(Field.WORD).charAt(0);
        char chr_Spell    = rst.getString(Field.SPELL).charAt(0);
        String str_ASpell = rst.getString(Field.ASPELL);

        arr_Spell.add(new Spell(chr_Word, chr_Spell, str_ASpell));
      }
      
      rst.close();
      
      System.out.println("������������" + arr_Spell.size() + "��ƴ��");
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
    finally
    {
    	rst.close();
    	dbc.freeConnection();
    }
  }

  /**
   * ���ƴ��������
   * @return int               ƴ��������
   */
  public synchronized int getCount()
  {
    return arr_Spell.size();
  }
  
  /**
   * ��õ���ƴ������
   * @param chrWord           �ַ�
   * @return Spell             ����ƴ���������
   */
  public Spell getObject(char chrWord)
  {
    Iterator it = arr_Spell.iterator();

    while(it.hasNext())
    {
      Spell obj_Spell = (Spell)it.next();

      if (obj_Spell.equals(chrWord)) return obj_Spell;
    }
    return null;
  }
  
  /**
   * ����ַ���ƴ��ͷ
   * @param chrWord           �ַ�
   * @return String            ƴ��ͷ
   */
  public String getSpell(char chrWord)
  {
    Spell obj_Spell = getObject(chrWord);

    if(obj_Spell==null) return null;

    return String.valueOf(obj_Spell.getSpell());
  }
  
  /**
   * ����ַ���ȫƴ
   * @param chrWord           �ַ�
   * @return String            ȫƴ
   */
  public String getASpell(char chrWord)
  {
    Spell obj_Spell = getObject(chrWord);

    if(obj_Spell==null) return null;

    return String.valueOf(obj_Spell.getASpell());
  }
  
  /**
   * ����ַ�����ƴ��ͷ
   * @param strWord           �ַ���
   * @return String            ƴ��ͷ
   */
  public String getSpell(String strWord)
  {
    if(strWord==null) return null;
    if(strWord.equals("")) return null;

    char[] chr_Single = strWord.toCharArray();

    String str_Spell = "";

    for(int i=0; i<chr_Single.length; i++)
    {
      str_Spell += getSpell(chr_Single[i]);
    }

    return str_Spell;
  }
  
  /**
   * ����ַ�����ȫƴ
   * @param strWord           �ַ���
   * @return String            ȫƴ
   */
  public String getASpell(String strWord)
  {
    if(strWord==null) return null;
    if(strWord.equals("")) return null;

    char[] chr_Single = strWord.toCharArray();

    String str_ASpell = "";

    for(int i=0; i<chr_Single.length; i++)
    {
      str_ASpell += getASpell(chr_Single[i]);
    }

    return str_ASpell;
  }
  
  /**
   * ����ƴ��
   * @param chrWord              �ַ�
   * @param chrSpell             ƴ��ͷ
   * @param strASpell            ȫƴ
   */
  public synchronized void update(char chrWord,
                                  char chrSpell,
                                  String strASpell) throws Exception
  {
    update(new Spell(chrWord, chrSpell, strASpell));
    return;
  }
 
  /**
   * ����ƴ��
   * @param objSpell              ����ƴ������
   */
  public synchronized void update(Spell objSpell) throws Exception
  {
    try
    {
      Spell obj_Spell = getObject(objSpell.getWord());

      DataStorage obj_DS = new DataStorage();
      
      /// ƴ��������ڣ�����
      if (obj_Spell!=null)
      {
        obj_Spell.setSpell(objSpell.getSpell());
        obj_Spell.setASpell(objSpell.getASpell());

        String str_Spell  = String.valueOf(objSpell.getSpell());
        String str_ASpell = String.valueOf(objSpell.getASpell());
        String str_Word   = String.valueOf(objSpell.getWord());

        String str_SQL = Common.UPDATE  + Table.SPELL  +
                         Common.S_SET   + Field.SPELL  + Common.EQUAL + General.addQuotes(str_Spell)  + Common.COMMA +
                                          Field.ASPELL + Common.EQUAL + General.addQuotes(str_ASpell) +
                         Common.S_WHERE + Field.WORD   + Common.EQUAL + General.addQuotes(str_Word);

        obj_DS.addSQL(str_SQL);
        obj_DS.runSQL();
      }

      /// ƴ�����治���ڣ����
      else
      {
        arr_Spell.add(objSpell);

        String str_SQL = Common.INSERT   + Table.SPELL      +
                           General.addBracket(Field.WORD    + Common.COMMA +
                                              Field.SPELL   + Common.COMMA +
                                              Field.ASPELL) +
                         Common.S_VALUES +
                           General.addBracket(General.addQuotes(String.valueOf(objSpell.getWord()))  + Common.COMMA +
                                              General.addQuotes(String.valueOf(objSpell.getSpell())) + Common.COMMA +
                                              General.addQuotes(objSpell.getASpell()));

        obj_DS.addSQL(str_SQL);
        obj_DS.runSQL();
      }

      return;
    }
    catch(Exception e)
    {
      throw new Exception(e.getMessage());
    }
  }
  
}
class Spell implements java.io.Serializable
{
  private static final long serialVersionUID = 8537233176930218357L;
  private char m_chr_Word;
  private char m_chr_Spell;
  private String m_str_ASpell;
  
  public Spell()
  {
  }
  
  public Spell(char chrWord, char chrSpell, String strASpell)
  {
    m_chr_Word = chrWord;
    m_chr_Spell = chrSpell;
    m_str_ASpell = strASpell;
  }

  public String getASpell()
  {
    return m_str_ASpell;
  }

  public void setASpell(String strASpell)
  {
    m_str_ASpell = strASpell;
  }

  public char getSpell()
  {
    return m_chr_Spell;
  }

  public void setSpell(char chrSpell)
  {
    m_chr_Spell = chrSpell;
  }

  public char getWord()
  {
    return m_chr_Word;
  }

  public void setWord(char chrWord)
  {
    m_chr_Word = chrWord;
  }

  public boolean equals(char chrValue)
  {
    if(m_chr_Word==chrValue) return true;
    return false;
  }
}