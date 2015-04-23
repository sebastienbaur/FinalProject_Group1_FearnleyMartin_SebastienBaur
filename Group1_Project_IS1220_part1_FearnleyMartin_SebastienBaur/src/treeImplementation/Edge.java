package treeImplementation;

import java.io.*;

public class Edge implements Serializable {
	public static int uniqueId = 0;
	protected int id;
	private Node startNode;
	private Node endNode;

	public Edge(Node s, Node e){
		this.startNode=s;
		this.endNode=e;
		this.id=uniqueId;
		uniqueId+=1;
	}

	
	public void setId(int i){
		this.id=i;
	}

	public int getId(){
		return id;
	}
	
	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	@Override
	public String toString() {
		return "Edge [" + startNode + "," + endNode + ", id :" + id + "]";
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endNode == null) ? 0 : endNode.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((startNode == null) ? 0 : startNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (endNode == null) {
			if (other.endNode != null)
				return false;
		} else if (!endNode.equals(other.endNode))
			return false;
		if (id != other.id)
			return false;
		if (startNode == null) {
			if (other.startNode != null)
				return false;
		} else if (!startNode.equals(other.startNode))
			return false;
		return true;
	}

	
	


}
