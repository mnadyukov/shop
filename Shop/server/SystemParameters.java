package server;

/**
 * Содержит параметры приложения.
 * Все параметры предназначены только для чтения.
 * @version 1.0
 * @author Надюков Михаил
 */
public class SystemParameters {

	/**
	 * Рабочий каталог приложения.
	 */
	public static final String SERVER_DIRECTORY="c:/eclipse-workspace/Shop";
	
	/**
	 * Номер порта, предназначенного для соединения с клиентами.
	 */
	public static final int SERVER_PORT=4000;
	
	/**
	 * Имя класса, осуществляющего шифрование-расшифровку данных.
	 * Класс должен находится в пакете server.crypto.
	 */
	public static final String CRYPTOGRAPHER="Gost28147Simple";
	
	/**
	 * Имя класса-драйвера применяемой СУБД.
	 */
	public static final String DATABASE_DRIVER="org.firebird.jdbc.FBDriver";
	
	/**
	 * URL файла базы данных.
	 */
	public static final String DATABASE_CONNECTION="jdbc:firebirdsql:localhost/3050:"+SERVER_DIRECTORY+"/database/SHOP.FDB";
	
	/**
	 * Логин системного администратора для доступа к базе данных.
	 */
	public static final String DATABASE_USER="SYSDBA";
	
	/**
	 * Пароль системного администратора для доступа к базе данных.
	 */
	public static final String DATABASE_PASSWORD="masterkey";
	
}
