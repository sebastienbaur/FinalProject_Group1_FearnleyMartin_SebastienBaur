package dataTypes;

public interface Visitable {

	public long accept(Visitor visitor);

}
