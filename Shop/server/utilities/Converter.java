package server.utilities;

import java.util.Calendar;

import server.ErrorManager;

/**
 * Осуществляет различные преобразования данных из одного вида в другой.
 * @version 1.0
 * @author Надюков Михаил
 */
public class Converter {

	/**
	 * Преобразует отметку времени в строку формата ГГГГ.ММ.ДД чч:мм:сс.
	 * @param timestamp Отметка времени.
	 * @return Строка соответствующего формата, представляющая собой данную отметку времени.
	 * null, если произошла ошибка.
	 */
	public static String TimestampToString(long timestamp) {
		try {
			Calendar C=new Calendar.Builder().setInstant(timestamp).build();
			int Y=C.get(Calendar.YEAR);
			int M=C.get(Calendar.MONTH);
			int D=C.get(Calendar.DAY_OF_MONTH);
			int h=C.get(Calendar.HOUR_OF_DAY);
			int m=C.get(Calendar.MINUTE);
			int s=C.get(Calendar.SECOND);
			return(Y+"."+(M<10?"0"+M:M)+"."+(D<10?"0"+D:D)+" "+(h<10?"0"+h:h)+":"+(m<10?"0"+m:m)+":"+(s<10?"0"+s:s));
		}catch(Exception e) {
			ErrorManager.register(Converter.class.getName()+".TimestampToString: "+e);
			return(null);
		}
	}
	
	/**
	 * Преобразует hex-строку в массив байт.
	 * Каждая пара символов в hex-строке представляет собой беззнаковый байт, записанный в шестнадцатиричной системе.
	 * hex-строка должна удовлетворять шаблону: ^([0-9a-fA-F][0-9a-fA-F])*$
	 * @param hex Преобразуемая hex-строка.
	 * @return Массив байт, соответствующий данной hex-строке.
	 */
	public static byte[] hexToByte(String hex) {
		try {
			if(hex.length()%2!=0)throw new Exception("некорректная hex-строка");
			byte[] b=new byte[hex.length()>>1];
			int bb;
			for(int i=0;i<hex.length();i+=2) {
				bb=Integer.parseInt(hex.substring(i,i+2),16);
				b[i>>1]=bb>127?(byte)(bb-256):(byte)bb;
			}
			return(b);
		}catch(Exception e) {
			ErrorManager.register(Converter.class.getName()+".HexToByte: "+e);
			return(null);
		}
	}
	
	/**
	 * Преобразует массив байт в hex-строку.
	 * Байты в массиве трактуются как беззнаковые.
	 * Полученная hex-строка удовлетворяет шаблону: ^([0-9a-fA-F][0-9a-fA-F])*$
	 * @param b Преобразуемый массив байт.
	 * @return hex-строка, представляющая данный массив байт.
	 */
	public static String byteToHex(byte[] b) {
		try {
			StringBuilder str=new StringBuilder();
			int bb;
			for(int i=0;i<b.length;i++) {
				if(b[i]<0) {
					bb=256+b[i];
				}else {
					bb=b[i];
				}
				str.append((bb<16?"0":"")+Integer.toHexString(bb));
			}
			return(new String(str));
		}catch(Exception e) {
			ErrorManager.register(Converter.class.getName()+".byteToHex: "+e);
			return(null);
		}
	}
	
}
