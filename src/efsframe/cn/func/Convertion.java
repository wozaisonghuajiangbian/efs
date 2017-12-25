package efsframe.cn.func;


public class Convertion {

  /**
   * ��ǰ�Ĵ洢λ��
   */
  private int bitWidth;

  /**
   * ���캯�������õ�ǰϵͳ��λ��
   * @param bitWidth int λ��
   */
  public Convertion(int bitWidth) {
    this.bitWidth = bitWidth * 8;
  }
  public Convertion() {
    this.bitWidth = 1 * 8;
  }
  
  /**
   * ʮ�����Ƶ�ʮ���Ƶ�ת����ע�⣺
   * 

ʮ������û��С����ֻ��Ϊ������
   * 
ת��������ֵΪLong.MAX_VALUE���������ֵ���κ�������ת�����ֵ��
   * 
���ֵ����Double.MAX_VALUEʱ�������׳��쳣��
   * @param strHex String ��Ҫת����ʮ���������ַ���
   * @return String  ת�����ʮ�������ַ���
   * @throws Exception
   */
  public String hexToDecimal(String strHex) throws Exception{
    //����ת�����
    String strDec = ""; 

    //����Ϊ�գ����ؿ��ַ���
    if(strHex.length() == 0)
      return "";

    //ת��
    String strBin = hexToBinary(strHex);
    strDec = binaryToDecimal(strBin);

    //���ؽ��
    return strDec;
  }

public String hexToString(String strHex) throws Exception
{
//����ת�����
  String strDec = ""; 

  //����Ϊ�գ����ؿ��ַ���
  if(strHex.length() == 0)
    return "";

 //ת��Ϊ�ַ�����
  char[] arrHex = strHex.toLowerCase().toCharArray();
  String strBin = "";
  //�õ��������ַ���
  for(int i = 0; i < arrHex.length; i++) {
    strBin += getBinstrFromHexchar(arrHex[i]);
  }
  int len = strBin.length();
  for(int i=0;i<len;i=i+8)
  {
    String strTemp1 = strBin.substring(i,i+8);
    String strTemp2 = "";
    int i2 = 0;
    for(int j=0;j<8;j++)
    {
      strTemp2 = strTemp1.substring(j,j+1);
      i2 += Integer.parseInt(strTemp2) * (int)Math.pow(2,8-(j+1));
    }
    strDec += "" + (char)i2;
  }
  
  //System.out.println(strBin);
  
  return strDec;
}
  
  /**
   * ��ʮ������ת��Ϊ�����ơ�
   * @param strHex String ʮ���������ַ���
   * @return String ���������ַ���
   * @throws Exception
   */
  public String hexToBinary(String strHex) throws Exception {
    //����ת�����
    String strBin = "";

    //����Ϊ�գ����ؿ��ַ���
    if(strHex.length() <= 0) {
      return "";
    }

    //ת��Ϊ�ַ�����
    char[] arrHex = strHex.toLowerCase().toCharArray();
    //�õ��������ַ���
    for(int i = 0; i < arrHex.length; i++) {
      strBin += getBinstrFromHexchar(arrHex[i]);
    }

    //ɾ����ͷ��0
    while(strBin.charAt(0) == '0') {
      strBin = strBin.substring(1);
    }

    return strBin;
  }

  /**
   * ��ʮ�������ַ���ת��Ϊ��Ӧ���������ַ�����
   * 


   * 
ֻ�ܶ���Long.MAX_VALUE����ֵ����ת��
   * 
ֻ�ܶ���������ת��������С��ʱ���ͻ�ֻȡС����ǰ������֡�
   * 
���ڸ�����ת��������λ��ʾ����λ��1��ʾ����0��ʾ���������̣��������ֵ�Ķ����ƣ�
   * ��λ�󷴣����һλ��1�����ɵõ������Ķ������������磺-5 ��λ��Ϊ8λ 5�Ķ����ƣ�101���󷴣�010����1��
   * 011�����Ϊ11111011��
   * 

   * @param strDec String ʮ�������ַ���
   * @return String ���������ַ���
   * @throws Exception ����������Ϸ�
   */
  public String decimalToBinary(String strDec) throws Exception {
    //����ת�����
    String strBin = "";
    //�Ƿ�Ϊ����
    boolean isNegative = false; 


    //����Ϊ�գ����ؿ��ַ���
    if(strDec.length() <= 0) {
      return "";
    }

    //��ȡС������ǰ�Ĳ���
    if(strDec.indexOf(".") != -1)
      strDec = strDec.substring(0, strDec.indexOf("."));

    //����Ǹ�������ȥ������
    if(strDec.indexOf("-") != -1 && strDec.indexOf("-") == 0) {
      strDec = strDec.substring(1);
      isNegative = true;
    }
      

    //�õ���������
    try {
      long num = Long.parseLong(strDec);
      long mod = 0;
      while (num != 0) {
        mod = num % 2;
        num /= 2;
        strBin = mod + strBin;
      }
    } catch(Exception e) {
      System.err.print(e.getMessage());
      throw new Exception("");
    }

    //�ж��Ƿ�Ϊ����
    if(isNegative) {
      //���ַ���ǰ��0����,ʹ�䳤�ȴﵽλ���������λΪ1--��ʾ����
      int difference = bitWidth - strBin.length();
      while(difference > 1) {
        strBin = "0" + strBin;
        difference--;
      }
      strBin = difference == 1 ? ("1" + strBin) : strBin;

      StringBuffer sbBinTemp = new StringBuffer(strBin);
      //�����λ���λ��
      int index  = 1;
      while(index < sbBinTemp.length()) {
        if(sbBinTemp.charAt(index) == '1') {
          sbBinTemp.setCharAt(index, '0');
        } else {
          sbBinTemp.setCharAt(index, '1');
        }
        index++;
      }

      //�󲹣��������һλ��1
      index--;
      while(index > 0) {
        if(sbBinTemp.charAt(index) == '1') {
          sbBinTemp.setCharAt(index, '0');
        } else {
          sbBinTemp.setCharAt(index, '1');
          break;
        }
        index--;
      }

      strBin = sbBinTemp.toString();
    }

    return strBin;
  }

  /**
   * ����������ת��Ϊʮ����������
   * @param strBin String �������ַ���
   * @return String ʮ�������ַ���
   * @throws Exception
   */
  public String binaryToHex(String strBin) throws Exception {
    //����ת�����
    String strHex = "";

    //����Ϊ�գ����ؿ��ַ���
    if(strBin.length() <= 0) {
      return "";
    }

    //���ַ���ǰ�油0��ʹ���ȳ�Ϊ4��������
    int lengthOfBin = strBin.length();
    for(int i = 0; i < (4 - lengthOfBin % 4) && (lengthOfBin % 4) != 0; i++) {
      strBin = "0" + strBin;
    }

    //ת��Ϊʮ��������--��������������˹λ���֣�����λת��Ϊʮ��������
    //�ٵõ�ʮ��������Ӧ��ʮ�������ַ�
    for(int i = 0; i < strBin.length(); i += 4) {
      String strDec = binaryToDecimal(strBin.substring(i, i + 4));
      strHex += getHexcharFromDecnum(Integer.parseInt(strDec));
    }

    //���ؽ��
    return strHex;

  }

  /**
   * ����������ת��Ϊʮ��������ת��������ܳ���Long.MAX_VALUE��
   * ���������
   * 

��������Ƴ��ȴﵽϵͳλ���������λ1�Ļ������ᱻתΪ���������磺λ����8��
   * �� 11111011 --> -5
   * @param strBin String ���������ַ���
   * @return String ʮ�������ַ���
   * @throws Exception
   */
  public String binaryToDecimal(String strBin) throws Exception {
    //����ת�����
    long dec = 0;
    //����Ƿ�Ϊ����
    boolean isNegative = false; 

    //����Ϊ�գ����ؿ��ַ���
    if(strBin.length() <= 0) {
      return "";
    }

    //�ж�������
    if(strBin.length() == bitWidth && strBin.charAt(0) == '1') {
      //������Ϊȥ��
      strBin = strBin.substring(1);
      isNegative = true;

      StringBuffer sbBinTemp = new StringBuffer(strBin);
      //��1
      int index = strBin.length() - 1;
      while(index >= 0) {
        if (sbBinTemp.charAt(index) == '1') {
          sbBinTemp.setCharAt(index, '0');
          break;
        } else {
          sbBinTemp.setCharAt(index, '1');
          index--;
        }
      }

      //��
      for(index = 0; index < sbBinTemp.length(); index++) {
        if (sbBinTemp.charAt(index) == '1') {
          sbBinTemp.setCharAt(index, '0');
        } else {
          sbBinTemp.setCharAt(index, '1');
        }
      }
      strBin = sbBinTemp.toString();
    }

    //ת��
    
    //java.lang.Math mathCompute = new java.lang.Math();
    if(strBin.indexOf("1") == -1) {
      dec = (long)Math.pow(2, strBin.length());
    } else {
    for(int i = 0; i < strBin.length(); i++) {
      try {
        dec += Long.parseLong(strBin.substring(i, i + 1)) *
          Math.pow(2, strBin.length() - i - 1);
      } catch (Exception e) {
        throw e;
      }
    }
    }

    //���ؽ��
    String strDec = String.valueOf(dec);
    if(isNegative) strDec = "-" + strDec;
    return strDec;
  }

  /**
   * ���ݲ�ͬ��ʮ�������ַ����õ����Ӧ�Ķ������ַ�����
   * @param cHex char ʮ�������ַ�
   * @return String  �������ַ���
   */
  private String getBinstrFromHexchar(char cHex) {
    String strBin = "";

    //��ȡ��ͬ�Ķ������ַ���
    switch (cHex) {
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
   * @param iDec int ʮ������
   * @return char ʮ�������ַ�
   */
  private char getHexcharFromDecnum(int iDec) {
    char cHex = 'x';

    //�õ�ʮ�����ַ�
    switch (iDec) {
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
  
  public  static void main(String[] args) throws Exception
  {
  }
}

