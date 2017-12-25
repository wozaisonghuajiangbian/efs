package efsframe.cn.cache;

import java.sql.ResultSet;
import efsframe.cn.declare.Field;

public class UserInfo implements java.io.Serializable
{
  private static final long serialVersionUID = 1093701881849726945L;

  private String m_str_UserID;
  private String m_str_UserTitle;
  private String m_str_UserName;
  private String m_str_UserPassword;
  private String m_str_UnitID;
  private String m_str_Duty;
  private String m_str_Nation;
  private String m_str_IDCard;
  private String m_str_NativePlace;
  private String m_str_Education;
  private String m_str_Address;
  private String m_str_TempAddress;
  private String m_str_Contact;
  private String m_str_SMSTel;
  private String m_str_UserDes;
  private String m_str_Birthday;
  private String m_str_Disabled;
  private String m_str_CanEditPassword;
  private String m_str_UserType;
  private String m_str_Sex;
  
  public String getAddress()
  {
    return m_str_Address;
  }

  public void setAddress(String strAddress)
  {
    m_str_Address = strAddress;
  }

  public String getBirthday()
  {
    return m_str_Birthday;
  }

  public void setBirthday(String strBirthday)
  {
    m_str_Birthday = strBirthday;
  }

  public String getCanEditPassword()
  {
    return m_str_CanEditPassword;
  }

  public void setCanEditPassword(String strCanEditPassword)
  {
    m_str_CanEditPassword = strCanEditPassword;
  }

  public String getContact()
  {
    return m_str_Contact;
  }

  public void setContact(String strContact)
  {
    m_str_Contact = strContact;
  }

  public String getDisabled()
  {
    return m_str_Disabled;
  }

  public void setDisabled(String strDisabled)
  {
    m_str_Disabled = strDisabled;
  }

  public String getDuty()
  {
    return m_str_Duty;
  }

  public void setDuty(String strDuty)
  {
    m_str_Duty = strDuty;
  }

  public String getEducation()
  {
    return m_str_Education;
  }

  public void setEducation(String strEducation)
  {
    m_str_Education = strEducation;
  }


  public String getIDCard()
  {
    return m_str_IDCard;
  }

  public void setIDCard(String strIDCard)
  {
    m_str_IDCard = strIDCard;
  }


  public String getNation()
  {
    return m_str_Nation;
  }

  public void setNation(String strNation)
  {
    m_str_Nation = strNation;
  }

  public String getNativePlace()
  {
    return m_str_NativePlace;
  }

  public void setNativePlace(String strNativePlace)
  {
    m_str_NativePlace = strNativePlace;
  }


  public String getSex()
  {
    return m_str_Sex;
  }

  public void setSex(String strSex)
  {
    m_str_Sex = strSex;
  }

  public String getSMSTel()
  {
    return m_str_SMSTel;
  }

  public void setSMSTel(String strSMSTel)
  {
    m_str_SMSTel = strSMSTel;
  }

  public String getTempAddress()
  {
    return m_str_TempAddress;
  }

  public void setTempAddress(String strTempAddress)
  {
    m_str_TempAddress = strTempAddress;
  }

  public String getUnitID()
  {
    return m_str_UnitID;
  }

  public void setUnitID(String strUnitID)
  {
    m_str_UnitID = strUnitID;
  }

  public String getUserDes()
  {
    return m_str_UserDes;
  }

  public void setUserDes(String strUserDes)
  {
    m_str_UserDes = strUserDes;
  }

  public String getUserID()
  {
    return m_str_UserID;
  }

  public void setUserID(String strUserID)
  {
    m_str_UserID = strUserID;
  }

  public String getUserName()
  {
    return m_str_UserName;
  }

  public void setUserName(String strUserName)
  {
    m_str_UserName = strUserName;
  }

  public String getUserPassword()
  {
    return m_str_UserPassword;
  }

  public void setUserPassword(String strUserPassword)
  {
    m_str_UserPassword = strUserPassword;
  }

  public String getUserTitle()
  {
    return m_str_UserTitle;
  }

  public void setUserTitle(String strUserTitle)
  {
    m_str_UserTitle = strUserTitle;
  }

  public String getUserType()
  {
    return m_str_UserType;
  }

  public void setUserType(String strUserType)
  {
    m_str_UserType = strUserType;
  }
  
  public void setValueByKey(String strKey,String strColumnValue)
  {
    if(strKey.equalsIgnoreCase(Field.USERID))
    {
      setUserID(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.USERTITLE))
    {
      setUserTitle(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.USERNAME))
    {
      setUserName(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.USERPASSWORD))
    {
      setUserPassword(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.UNITID))
    {
      setUnitID(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.DUTY))
    {
      setDuty(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.NATION))
    {
      setNation(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.IDCARD))
    {
      setIDCard(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.NATIVEPLACE))
    {
      setNativePlace(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.EDUCATION))
    {
      setEducation(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.ADDRESS))
    {
      setAddress(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.TEMPADDRESS))
    {
      setTempAddress(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.CONTACT))
    {
      setContact(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.SMSTEL))
    {
      setSMSTel(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.USERDES))
    {
      setUserDes(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.BIRTHDAY))
    {
      setBirthday(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.DISABLED))
    {
      setDisabled(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.CANEDITPASSWORD))
    {
      setCanEditPassword(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.USERTYPE))
    {
      setUserType(strColumnValue);
      return ;
    }
    if(strKey.equalsIgnoreCase(Field.SEX))
    {
      setSex(strColumnValue);
      return ;
    }
  }
  
  public void setValueByResult(ResultSet rst)
  {
    try
    {
      String str_ColumnName;
      String str_ColumnValue;
      for(int i=1; i<=rst.getMetaData().getColumnCount(); i++)
      {
        str_ColumnName  = rst.getMetaData().getColumnName(i).toLowerCase(); 
        str_ColumnValue = rst.getString(i)==null ? "" : rst.getString(i).toString();
        setValueByKey(str_ColumnName, str_ColumnValue);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
