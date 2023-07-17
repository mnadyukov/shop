package server.utilities;

import java.util.ArrayList;

import server.ErrorManager;

/**
 * Реализует тип данных Структура.
 * Структура фактически является деревом.
 * Каждый элемент структуры имеет текстовое значение и может иметь упорядоченные подэлементы.
 * Каждый элемент структуры имеет один элемент-родитель.
 * Элемент верхнего уровня не имеет родителя.
 * @version 1.0
 * @author Надюков Михаил
 */
public class Structure {

	private Structure parent;
	private ArrayList<Structure> children;
	private String value;
	
	/**
	 * Создает пустую структуру (элемент), не содержащую подструктуры, родителя.
	 * Текстовое значение созданной структуры есть пустая строка.
	 */
	public Structure() {
		value="";
		parent=null;
		children=new ArrayList<Structure>();
	}
	
	/**
	 * Создает структуру с указанным текстовым значением.
	 * @param value Текстовое значение создаваемой структуры.
	 */
	public Structure(String value) {
		this.value=value;
		parent=null;
		children=new ArrayList<Structure>();
	}
	
	/**
	 * Возвращает текстовое значение структуры.
	 * @return Текстовое значение структуры.
	 */
	public String getValue() {
		return(value);
	}
	
	/**
	 * Возвращает текстовоезначение указанной подструктуры данной структуры.
	 * @param index Индекс подструктуры (начиная с 0), текстовое значение которой возвращается.
	 * @return Текстовое значение указанной подструктуры.
	 * null, если указанной подструктуры не существует или если произошла ошибка.
	 */
	public String getValue(int index) {
		try {
			if(index<0)throw new Exception("индекс меньше 0");
			if(index>=children.size())throw new Exception("индекс превышает допустимое значение");
			return(children.get(index).getValue());
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".getValue(int): "+e);
			return(null);
		}
	}
	
	/**
	 * Устанавливает текстовое значение структуры.
	 * @param value Новое значение структуры.
	 * @return Текущая структура.
	 */
	public Structure setValue(String value) {
		this.value=value;
		return(this);
	}
	
	/**
	 * Устанавливает текстовое значение указанной подструктуры.
	 * @param value Новое текстовое значение подструктуры.
	 * @param index Индекс (начиная с 0) подструктуры, текстовое значение которой устанавливается.
	 * Если подструктуры с указанным индексом не существует, то присваивание не происходит.
	 * @return Текущая структура.
	 */
	public Structure setValue(String value, int index) {
		try {
			if(index<0)throw new Exception("индекс меньше 0");
			if(index>=children.size())throw new Exception("индекс превышает допустимое значение");
			children.get(index).setValue(value);
			return(this);
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".setValue(String,int): "+e);
			return(this);
		}
	}
	
	/**
	 * Добавляет указанную структуру в конец списка подструктур текущей структуры.
	 * @param child Добавляемая структура.
	 * @return Текущая структура.
	 */
	public Structure addChild(Structure child) {
		children.add(child);
		child.parent=this;
		return(this);
	}
	
	/**
	 * Создает структуру с указанным текстовым значением и добавляет ее в конец списка подструктур текущей структуры.
	 * @param value Текстовое значение.
	 * @return Текущая структура.
	 */
	public Structure addChild(String value) {
		Structure child=new Structure(value);
		return(this.addChild(child));
	}
	
	/**
	 * Возвращает указанную подструктуру текущей структуры.
	 * @param index Индекс (начиная с 0) возвращаемой подструктуры.
	 * @return Указанная подструктура текущей структуры.
	 * null, если указанной подструктуры не существует или произошла ошибка.
	 */
	public Structure getChild(int index) {
		try {
			if(index<0)throw new Exception("индекс меньше 0");
			if(index>=children.size())throw new Exception("индекс превышает допустимое значение");
			return(children.get(index));
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".getChild(int): "+e);
			return(null);
		}
	}
	
	/**
	 * Заменяет указанной структурой указанную подструктуру текущей структуры.
	 * @param child Новая подструктура. Значение null будет проигнорировано.
	 * @param index Индекс (начиная с 0) заменяемой подструктуры.
	 * @return Текущая структура.
	 * null, если заменяемой подструктуры не существует или произошла ошибка.
	 */
	public Structure setChild(Structure child, int index) {
		try {
			if(child==null)return(this);
			if(index<0)throw new Exception("индекс меньше 0");
			if(index>=children.size())throw new Exception("индекс превышает допустимое значение");
			children.set(index,child);
			return(this);
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".setChild(Structure,int): "+e);
			return(null);
		}
	}
	
	/**
	 * Возвращает массив непосредственных подструктур текущей структуры.
	 * @return Массив непосредственных подструктур.
	 */
	public ArrayList<Structure> getChildren(){
		return(children);
	}
	
	/**
	 * Возвращает массив подструктур текущей структуры, текстовое значение которых равно указанному значению.
	 * Поиск производится только среди непосредственных подструктур.
	 * @param value Текстовое значение возвращаемых подструктур.
	 * @return Массив подструктур текущей структуры.
	 */
	public ArrayList<Structure> getChildren(String value){
		ArrayList<Structure> res=new ArrayList<Structure>();
		for(Structure str:children)if(str.getValue().equals(value))res.add(str);
		return(res);
	}
	
	/**
	 * Удаляет указанную подструктуру из списка подструктур текущей структуры.
	 * @param child Удаляемая подструктура.
	 * Значение null игнорируется.
	 * @return Текущая структура.
	 */
	public Structure removeChild(Structure child) {
		if(child==null)return(this);
		children.remove(child);
		child.parent=null;
		return(this);
	}
	
	/**
	 * Удаляет указанную подструктуру из списка подструктур текущей структуры.
	 * @param index Индекс удаляемой подструктуры.
	 * @return Текущая структура.
	 */
	public Structure removeChild(int index) {
		try {
			if(index<0)throw new Exception("индекс меньше 0");
			if(index>=children.size())throw new Exception("индекс превышает допустимое значение");
			children.remove(index);
			return(this);
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".removeChild(int): "+e);
			return(this);
		}
	}
	
	/**
	 * Удаляет все непосредственные подструктуры текущей структуры, текстовое значение которых равно указанному.
	 * @param value Текстовое значение удаляемых подструктур.
	 * @return Текущая структура.
	 */
	public Structure removeChild(String value) {
		for(Structure str:children)if(str.getValue().equals(value))children.remove(str);
		return(this);
	}
	
	/**
	 * Добавляет указанную структуру в конец списка подструктур текущей структуры, 
	 * обеспечивая ее уникальность (в смысле метода equals) среди непосредственных 
	 * подструктур текущей структуры.
	 * Подструктуры, эквивалентные добавляемой, удаляются.
	 * Значение null игнорируется.
	 * @param child Добавляемая подструктура.
	 * @return Текущая структура.
	 */
	public Structure addUniqueChild(Structure child) {
		if(child==null)return(this);
		removeChild(child);
		addChild(child);
		return(this);
	}
	
	/**
	 * Создает новую структуру с указанным текстовым значением и добавляет ее 
	 * в конец списка подструктур текущей структуры, обеспечивая ее уникальность 
	 * среди непосредственных подструктур текущей структуры.
	 * Уникальность понимается как уникальность текстовых значений.
	 * Подструктуры с текстовым значением, эквивалентными текстовому значению добавляемой, удаляются.
	 * @param value Текстовое значение добавляемой подструктуры.
	 * Значение null игнорируется.
	 * @return Текущая структура.
	 */
	public Structure addUniqueChild(String value) {
		removeChild(value);
		addChild(value);
		return(this);
	}
	
	/**
	 * Удаляет все подструктуры текущей структуры.
	 * @return
	 */
	public Structure clear() {
		while(children.size()>0)removeChild(children.get(0));
		return(this);
	}
	
	/**
	 * Формирует структуру из текстового представления.
	 * Формат текстового представления см. {@link #toString() здесь}.
	 * @param str Текстовое представление структуры.
	 * @return Сформированная структура.
	 * null, если текстовое представление некорректно или произошла ошибка.
	 */
	public static Structure fromString(String str) {
		try {
			StringBuffer word=new StringBuffer();
			Structure struct=null,node;
			for(int i=0;i<str.length();i++) {
				if(str.charAt(i)=='\u0001') {
					if(struct==null) {
						struct=new Structure(word.substring(0));
					}else {
						node=new Structure(word.substring(0));
						struct.addChild(node);
						struct=node;
					}
					word=new StringBuffer();
				}else if(str.charAt(i)=='\u0002') {
					if(struct.parent==null)break;
					struct=struct.parent;
				}else {
					word.append(str.charAt(i));
				}
				
			}
			return(struct);
		}catch(Exception e) {
			ErrorManager.register(Structure.class.getName()+".fromString(String): "+e);
			return(null);
		}
	}
	
	/**
	 * Возвращает текстовое представление текущей структуры.
	 * Формат текстового представления: value01SUBSTRUCTURES02, где 
	 * value - текстовое значение текущей структуры, 
	 * SUBSTRUCTURES - упорядоченные текстовые представления подструктур текущей структуры, 
	 * 01 - символ с кодом u0001, 
	 * 02 - символ с кодом u0002.
	 * @return Текстовое представление структуры.
	 */
	public String toString() {
		StringBuffer res=new StringBuffer();
		res.append(value+"\u0001");
		for(Structure str:children)res.append(str.toString());
		res.append("\u0002");
		return(res.substring(0));
	}
	
}
