package efsframe.cn.func;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * ����ʱ�乤����<br>
 * �ṩһЩ���õ�����ʱ��������������з�����Ϊ��̬������ʵ�������༴��ʹ�á�<br><br>
 * ��Ϊ���ڸ�ʽ�ļ�����������ο�java API��java.text.SimpleDateFormat<br>
 * @author Enjsky
 */

public class DateTimeUtil {

  /**
   * ȱʡ��������ʾ��ʽ�� yyyy-MM-dd
   */
  public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

  /**
   * ȱʡ������ʱ����ʾ��ʽ��yyyy-MM-dd HH:mm:ss
   */
  public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  /**
   * ˽�й��췽������ֹ�Ը������ʵ����
   */
  private DateTimeUtil() {}

  /**
   * �õ�ϵͳ��ǰ����ʱ��
   * @return ��ǰ����ʱ��
   */
  public static Date getNow() {
    return Calendar.getInstance().getTime();
  }

  /**
   * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ����
   * @return ��ǰ����
   */
  public static String getDate() {
    return getDateTime(DEFAULT_DATE_FORMAT);
  }

  /**
   * �õ���ȱʡ��ʽ��ʽ���ĵ�ǰ���ڼ�ʱ��
   * @return ��ǰ���ڼ�ʱ��
   */
  public static String getDateTime() {
    return getDateTime(DEFAULT_DATETIME_FORMAT);
  }

  /**
   * �õ�ϵͳ��ǰ���ڼ�ʱ�䣬����ָ���ķ�ʽ��ʽ��
   * @param pattern ��ʾ��ʽ
   * @return ��ǰ���ڼ�ʱ��
   */
  public static String getDateTime(String pattern) {
    Date datetime = Calendar.getInstance().getTime();
    return getDateTime(datetime, pattern);
  }

  /**
   * �õ���ָ����ʽ��ʽ��������
   * @param date ��Ҫ���и�ʽ��������
   * @param pattern ��ʾ��ʽ
   * @return ����ʱ���ַ���
   */
  public static String getDateTime(Date date, String pattern) {
    if (null == pattern || "".equals(pattern)) {
      pattern = DEFAULT_DATETIME_FORMAT;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    return dateFormat.format(date);
  }

  /**
   * �õ���ǰ���
   * @return ��ǰ���
   */
  public static int getCurrentYear() {
    return Calendar.getInstance().get(Calendar.YEAR);
  }

  /**
   * �õ���ǰ�·�
   * @return ��ǰ�·�
   */
  public static int getCurrentMonth() {
    //��get�õ����·�����ʵ�ʵ�С1����Ҫ����
    return Calendar.getInstance().get(Calendar.MONTH) + 1;
  }

  /**
   * �õ���ǰ��
   * @return ��ǰ��
   */
  public static int getCurrentDay() {
    return Calendar.getInstance().get(Calendar.DATE);
  }

  /**
   * ȡ�õ�ǰ�����Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø�����
   * ����Ҫ�õ�������ͬһ������ڣ�������Ϊ-7
   * @param days ���ӵ�������
   * @return �����Ժ������
   */
  public static Date addDays(int days) {
    return add(getNow(), days, Calendar.DATE);
  }

  /**
   * ȡ��ָ�������Ժ�����������ڡ����Ҫ�õ���ǰ�����ڣ������ø�����
   * @param date ��׼����
   * @param days ���ӵ�������
   * @return �����Ժ������
   */
  public static Date addDays(Date date, int days) {
    return add(date, days, Calendar.DATE);
  }

  /**
   * ȡ�õ�ǰ�����Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø�����
   * @param months ���ӵ��·���
   * @return �����Ժ������
   */
  public static Date addMonths(int months) {
    return add(getNow(), months, Calendar.MONTH);
  }

  /**
   * ȡ��ָ�������Ժ�ĳ�µ����ڡ����Ҫ�õ���ǰ�·ݵ����ڣ������ø�����
   * ע�⣬���ܲ���ͬһ���ӣ�����2003-1-31����һ������2003-2-28
   * @param date ��׼����
   * @param months ���ӵ��·���
   * @return �����Ժ������
   */
  public static Date addMonths(Date date, int months) {
    return add(date, months, Calendar.MONTH);
  }

  /**
   * �ڲ�������Ϊָ������������Ӧ������������
   * @param date ��׼����
   * @param amount ���ӵ�����
   * @param field ���ӵĵ�λ���꣬�»�����
   * @return �����Ժ������
   */
  private static Date add(Date date, int amount, int field) {
    Calendar calendar = Calendar.getInstance();

    calendar.setTime(date);
    calendar.add(field, amount);

    return calendar.getTime();
  }

  /**
   * ���������������������
   * �õ�һ�����ڼ�ȥ�ڶ��������ǰһ������С�ں�һ�����ڣ��򷵻ظ���
   * @param one ��һ������������Ϊ��׼
   * @param two �ڶ�������������Ϊ�Ƚ�
   * @return ���������������
   */
  public static long diffDays(Date one, Date two) {
    return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
  }

  /**
   * ����������������·���
   * ���ǰһ������С�ں�һ�����ڣ��򷵻ظ���
   * @param one ��һ������������Ϊ��׼
   * @param two �ڶ�������������Ϊ�Ƚ�
   * @return ������������·���
   */
  public static int diffMonths(Date one, Date two) {

    Calendar calendar = Calendar.getInstance();

    //�õ���һ�����ڵ���ֺ��·���
    calendar.setTime(one);
    int yearOne = calendar.get(Calendar.YEAR);
    int monthOne = calendar.get(Calendar.MONDAY);

    //�õ��ڶ������ڵ���ݺ��·�
    calendar.setTime(two);
    int yearTwo = calendar.get(Calendar.YEAR);
    int monthTwo = calendar.get(Calendar.MONDAY);

    return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
  }

  /**
   * ��һ���ַ����ø����ĸ�ʽת��Ϊ�������͡�<br>
   * ע�⣺�������null�����ʾ����ʧ��
   * @param datestr ��Ҫ�����������ַ���
   * @param pattern �����ַ����ĸ�ʽ��Ĭ��Ϊ��yyyy-MM-dd������ʽ
   * @return �����������
   */
  public static Date parse(String datestr, String pattern) {
    Date date = null;

    if (null == pattern || "".equals(pattern)) {
      pattern = DEFAULT_DATE_FORMAT;
    }

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      date = dateFormat.parse(datestr);
    }
    catch (ParseException e) {
      //
    }

    return date;
  }

  /**
   * ���ر��µ����һ��
   * @return �������һ�������
   */
  public static Date getMonthLastDay() {
    return getMonthLastDay(getNow());
  }

  /**
   * ���ظ��������е��·��е����һ��
   * @param date ��׼����
   * @return �������һ�������
   */
  public static Date getMonthLastDay(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    

    //����������Ϊ��һ�µ�һ��
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

    //��ȥ1�죬�õ��ļ����µ����һ��
    calendar.add(Calendar.DATE, -1);

    return calendar.getTime();
  }
  
  @SuppressWarnings("deprecation")
  public static String getDay()
  {
	  String day ="" ;
	  int d = DateTimeUtil.getNow().getDay() ;
	  if(d==0)
	  {
		  day = "������" ;
	  }
	  else if(d==1)
	  {
		  day = "����һ" ;
	  }
	  else if(d==2)
	  {
		  day = "���ڶ�" ;
	  }
	  else if(d==3)
	  {
		  day = "������" ;
	  }
	  else if(d==4)
	  {
		  day = "������" ;
	  }
	  else if(d==5)
	  {
		  day = "������" ;
	  }
	  else if(d==6)
	  {
		  day = "������" ;
	  }
	  return day ;
  }

  public static void main(String[] args) {

    
  }

}
