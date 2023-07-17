package server.utilities;

import java.util.HashMap;

import server.ErrorManager;
import server.component.Tree;
import server.query.getProductCatalog;

/**
 * Формирует каталог продукции.
 * @version 1.0
 * @author Надюков Михаил
 */
public class ProductCatalog {

	/**
	 * Возвращает сформированный каталог продукции.
	 * @return Каталог продукции.
	 * null, если произошла ошибка.
	 */
	public static Tree get() {
		try {
			Structure catalog=new getProductCatalog().execute(null);
			if(catalog==null)throw new Exception("ошибка получения каталога продукции из базы данных");
			HashMap<String,Structure> cat=new HashMap<String,Structure>();
			for(Structure str:catalog.getChildren())cat.put(str.getValue(0), str.addChild(""));
			Structure parent;
			for(String num:cat.keySet()) {
				parent=cat.get(cat.get(num).getValue(1));
				if(parent==null)continue;
				parent.getChild(3).addChild(cat.get(num));
				cat.put(num,null);
			}
			Tree products=new Tree();
			Tree.Branch root=new Tree.Branch().setTitle("Каталог товаров");
			products.addBranch(root);
			Tree.Branch branch;
			Tree.Leaf leaf;
			for(String num:cat.keySet()) {
				if(cat.get(num)==null)continue;
				if(cat.get(num).getChild(3).getChildren().size()==0) {
					leaf=getLeaf(cat.get(num));
					if(leaf==null)throw new Exception("ошибка при построении конечной группы каталога товаров");
					root.addLeaf(leaf);
				}else {
					branch=getBranch(cat.get(num));
					if(branch==null)throw new Exception("ошибка при построении группы каталога");
					root.addBranch(branch);
				}
				
			}
			return(products);
		}catch(Exception e) {
			ErrorManager.register(ProductCatalog.class.getName()+".get(): "+e);
			return(null);
		}
	}
	
	private static Tree.Branch getBranch(Structure str){
		try {
			Tree.Branch branch=
				new Tree.Branch()
				.setTitle(str.getValue(2));
			Tree.Branch b;
			Tree.Leaf l;
			for(Structure s:str.getChild(3).getChildren()) {
				if(s.getChild(3).getChildren().size()==0) {
					l=getLeaf(s);
					if(l==null)throw new Exception("ошибка при построении конечной группы каталога товаров");
					branch.addLeaf(l);
				}else {
					b=getBranch(s);
					if(b==null)throw new Exception("ошибка при построении группы каталога");
					branch.addBranch(b);
				}
			}
			return(branch);
		}catch(Exception e) {
			ErrorManager.register(ProductCatalog.class.getName()+".getBranch(Structure): "+e);
			return(null);
		}
	}
	
	private static Tree.Leaf getLeaf(Structure str){
		try {
			return(new Tree.Leaf().setTitle(str.getValue(2)).setCommand(new Structure("view_catalog_products").addChild(str.getValue(0))));
		}catch(Exception e) {
			ErrorManager.register(ProductCatalog.class.getName()+".getLeaf(Structure): "+e);
			return(null);
		}
	}
	
}
