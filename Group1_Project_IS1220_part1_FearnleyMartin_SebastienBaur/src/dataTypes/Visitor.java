package dataTypes;

public interface Visitor {

	public abstract long visit(Fichier f);
	public abstract long visit(Directory d); 
	
	
}
