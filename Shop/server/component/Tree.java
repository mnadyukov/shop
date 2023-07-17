package server.component;

import server.utilities.Structure;
import server.utilities.TextPattern;

//tree{<minWidth><maxWidth><minHeight><maxHeight>{<SUBTREE>...}}
//SUBTREE:=b{<title{SUBTREES}>} | l{<title{<COMMAND>}>}
public class Tree implements Component {

	public static class Branch implements Component{

		private Structure branch;
		
		public Branch() {
			this("");
		}
		
		public Branch(String title) {
			if(title==null || !title.matches(TextPattern.TITLE))title="";
			branch=new Structure("b")
				.addChild(title);
				
		}
		
		public Branch setTitle(String value) {
			if(value!=null && value.matches(TextPattern.TITLE))branch.setValue(value, 0);
			return(this);
		}
		
		public Branch addBranch(Branch obj) {
			if(obj!=null)branch.getChild(0).addChild(obj.getComponent());
			return(this);
		}
		
		public Branch addLeaf(Leaf obj) {
			if(obj!=null)branch.getChild(0).addChild(obj.getComponent());
			return(this);
		}
		
		public Structure getComponent() {
			return(branch);
		}
		
	}
	
	public static class Leaf implements Component{

		private Structure leaf;
		
		public Leaf() {
			this("","");
		}
		
		public Leaf(String title) {
			this(title,"");
		}
		
		public Leaf(String title,String command) {
			this(title,new Structure(command));
		}
		
		public Leaf(String title, Structure command) {
			if(title==null || !title.matches(TextPattern.TITLE))title="";
			if(command==null || !command.getValue().matches(TextPattern.NAME))command=new Structure("");
			leaf=new Structure("l")
				.addChild(
					new Structure(title)
						.addChild(command)
				);
		}
		
		public Leaf setTitle(String value) {
			if(value!=null && value.matches(TextPattern.TITLE))leaf.setValue(value, 0);
			return(this);
		}
		
		public Leaf setCommand(String command) {
			if(command==null)return(this);
			return(setCommand(new Structure(command)));
		}
		
		public Leaf setCommand(Structure command) {
			if(command!=null && command.getValue().matches(TextPattern.NAME))leaf.getChild(0).setChild(command, 0);
			return(this);
		}
		
		public Structure getComponent() {
			return(leaf);
		}
		
	}
	
	private Structure tree;
	
	public Tree() {
		tree=new Structure("tree")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("0")
			.addChild("");
	}
	
	public Tree setMinWidth(int value) {
		if(value>=0 && value<=100)tree.setValue(""+value, 0);
		return(this);
	}
	
	public Tree setMaxWidth(int value) {
		if(value>=0 && value<=100)tree.setValue(""+value, 1);
		return(this);
	}
	
	public Tree setMinHeight(int value) {
		if(value>=0 && value<=100)tree.setValue(""+value,2);
		return(this);
	}
	
	public Tree setMaxHeight(int value) {
		if(value>=0 && value<=100)tree.setValue(""+value, 3);
		return(this);
	}
	
	public Tree addBranch(Branch branch) {
		if(branch!=null)tree.getChild(4).addChild(branch.getComponent());
		return(this);
	}
	
	public Tree addLeaf(Leaf leaf) {
		if(leaf!=null)tree.getChild(4).addChild(leaf.getComponent());
		return(this);
	}
	
	public Structure getComponent() {
		return(tree);
	}

}
