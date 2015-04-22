package dataTypes;

import java.io.Serializable;

import treeImplementation.Node;

public class Directory extends treeImplementation.Node implements Serializable, Visitable{



	public Directory(String name) {
		super();
		this.name = name;
		this.id=Node.uniqueId;
		Node.uniqueId+=1;
	}





	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}





	@Override
	public String toString() {
		return "Directory [" +name  +", id : " + ((Node)this).id +"]";
	}





	@Override
	public long accept(Visitor visitor) {
		return visitor.visit(this);

	}


	public Directory duplicateDirectory(){
		return new Directory(this.name);
	}



}
