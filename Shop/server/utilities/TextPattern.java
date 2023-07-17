package server.utilities;

/**
 * Содержит регулярные выражения для графических пользовательских компонентов, 
 * различных вводимых пользователем данных.
 * @version 1.0
 * @author Надюков Михаил
 */
public class TextPattern {

	/**
	 * Регулярное выражение для имени компонента, команды.
	 */
	public static final String NAME="^[a-zA-z_][a-zA-Z_0-9]*$";
	
	/**
	 * Регулярное выражение для заголовка компонента.
	 */
	public static final String TITLE="^.*$";
	
	/**
	 * Регулярное выражение для логина пользователя.
	 */
	public static final String LOGIN="^([a-zA-Z_][a-zA-Z_0-9]*){4,}$";
	
	/**
	 * Регулярное выражение для пароля пользователя.
	 */
	public static final String PASSWORD="^[a-zA-Z_0-9]{6,}$";
	
	/**
	 * Регулярное выражение для персонального имени.
	 */
	public static final String FIRST_NAME="^[А-Я][а-я]*$";
	
	/**
	 * Регулярное выражение для персональной фамилии.
	 */
	public static final String SECOND_NAME="^[А-Я][а-я]*(-[А-Я][а-я]*)?$";
	
	/**
	 * Регулярное выражение для персонального отчества.
	 */
	public static final String THIRD_NAME="^[А-Я][а-я]*$";
	
	/**
	 * Регулярное выражение для адреса.
	 */
	public static final String ADDRESS="^.{10,}$";
	
	/**
	 * Регулярное выражение для номера телефона.
	 */
	public static final String PHONE="^\\+?[0-9]{6,}(\\*[0-9]{1,})?$";
	
	/**
	 * Регулярное выражение для адреса электронной почты.
	 */
	public static final String EMAIL="^[a-zA-Z_][a-zA-Z_0-9]*@[a-zA-Z_][a-zA-Z_0-9]*\\.[a-zA-Z]+$";
	
	/**
	 * Регулярное выражение для имени клиентского действия графической пользовательской системы.
	 */
	public static final String ACTION="^#?[a-zA-z_][a-zA-Z_0-9]*$";
	
	/**
	 * Регулярное выражение для описания.
	 */
	public static final String DESCRIPTION="^.+$";
	
	/**
	 * Регулярное выражение для цены.
	 */
	public static final String PRICE="^[0-9]{1,10}(\\.[0-9]{0,2})?$";
	
}
