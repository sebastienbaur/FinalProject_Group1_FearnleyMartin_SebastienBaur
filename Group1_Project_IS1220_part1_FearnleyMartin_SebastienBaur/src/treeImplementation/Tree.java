package treeImplementation;
import java.io.Serializable;
import java.util.*;

import treeImplementation.*;
import dataTypes.*;

//using generic types that refer to the generic types of the nodes and edges
public class Tree implements Serializable{


	private  List<Node> nodeList = new ArrayList<Node>();
	private  List<Edge> edgeList = new ArrayList<Edge>();


	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS, TOSTRING, AND EQUALS METHOD
	//---------------------------------------------------------------------------------

	public List<Node> getNodeList() {
		return nodeList;
	}

	@Override
	public String toString() {
		return "Tree [nodeList=" + nodeList + ", edgeList=" + edgeList + "]";
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList) {
		this.edgeList = edgeList;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((edgeList == null) ? 0 : edgeList.hashCode());
		result = prime * result
				+ ((nodeList == null) ? 0 : nodeList.hashCode());
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
		Tree other = (Tree) obj;
		HashSet<Node> thisNodesSet = new HashSet<Node>(this.getNodeList());
		HashSet<Edge> thisEdgesSet = new HashSet<Edge>(this.getEdgeList()); 
		HashSet<Node> otherNodesSet = new HashSet<Node>(other.getNodeList());
		HashSet<Edge> otherEdgesSet = new HashSet<Edge>(other.getEdgeList());
		if (edgeList == null) {
			if (other.edgeList != null)
				return false;
		} else if (!thisEdgesSet.equals(otherEdgesSet))
			return false;
		if (nodeList == null) {
			if (other.nodeList != null)
				return false;
		} else if (!thisNodesSet.equals(otherNodesSet))
			return false;
		return true;
	}



	// -----------------------------------------------------------------------------
	// NODE/EDGE MANAGEMENT
	// -----------------------------------------------------------------------------

	public void addNode(Node n){
		this.nodeList.add(n);
	}

	public void addEdge(Edge e) throws ParentException, NotInTreeException{
		// first we verify whether the endNode already has a predecessor. If it hasn't then you can add one
		boolean test = true;
//		for (Edge edge : edgeList){
//			if (edge.getEndNode() instanceof Fichier && e.getEndNode() instanceof Fichier){
//				Fichier f = (Fichier) edge.getEndNode();
//				Fichier e2 = (Fichier) e.getEndNode();
//				if (f.equals(e2));
//					test = false;
//			}
//			else if (edge.getEndNode() instanceof Directory && e.getEndNode() instanceof Fichier){
//					test = false;
//			}
//			else if (edge.getEndNode() instanceof Fichier && e.getEndNode() instanceof Directory){
//					test = false;
//			}
//			else if (edge.getEndNode() instanceof Directory && e.getEndNode() instanceof Directory){
//				Directory f = (Directory) edge.getEndNode();
//				Directory e2 = (Directory) e.getEndNode();
//				if (f.equals(e2));
//					test = false;
//			}
//		}
		if (test){
			if (this.contains(e.getStartNode())){
				if (this.contains(e.getEndNode())){
					this.edgeList.add(e);
	//				System.out.println("Entered if");
				}
				else{
					throw new NotInTreeException("end node " + e.getEndNode() + " of your edge isn't part of the nodeList of the tree");
				}
				}
			else{
				throw new NotInTreeException("start node "+e.getStartNode() + " of your edge isn't part of the nodeList of the tree");
			}
		}
			
		else
			{
			throw new ParentException(e.getEndNode() + " already has a predecessor ! You can't add this edge");
			}
		

	}


	
	//	// this method is used by the VirtualDisk method deleteAll(String path)
	//	public void deleteAllAux(Node n) throws NotInTreeException{
	//		if(this.getNodeList().contains(n)){
	//			this.getNodeList().remove(n);
	//			// the edges linking the node to its successors/predecessor have to be removed too
	//			try{
	//				this.getEdgeList().remove(this.getEdgeFromNodes(this.getPredecessor(n), n));}
	//			finally{
	//				List<Node> successors = new ArrayList<Node>();
	//				successors = this.getSuccessors(n);
	//				for (int i = 0; i<successors.size();i++){
	//					this.getEdgeList().remove(this.getEdgeFromNodes(n, successors.get(i)));
	//				}
	//			}
	//		}
	//		else 
	//			throw new NotInTreeException("Node "+n+" not in tree");
	//	}

	// deletes a Node and all its successors
	public void deleteAll(Node n) throws NotInTreeException{
		while (this.getNodeList().contains(n)){
			if (this.getSuccessors(n).size() == 0)
				this.deleteLeaf(n);
			else
				this.deleteAll(this.getSuccessors(n).get(0));
		}
	}

	
	// delete a Node which has no successors
	public void deleteLeaf(Node n) throws NotInTreeException{
		if (this.getSuccessors(n).size() == 0 && this.getPredecessor(n).size() != 0){
			this.getNodeList().remove(n);
			this.getEdgeList().remove(this.getEdgeFromNodes(this.getPredecessor(n).get(0), n));
		}
		else if (this.getSuccessors(n).size() == 0 && this.getPredecessor(n).size() == 0)
			this.getNodeList().remove(n);
		else
			System.out.println("it's not a leaf, nothing's been deleted");
	}
	
	

	public void deleteEdge(Edge e) throws NotInTreeException{
		if (this.getEdgeList().contains(e)){
			this.getEdgeList().remove(e);
		}
		else
			throw new NotInTreeException("Edge " +e+ "is not in the tree");
	}

	public int nodeCount(){
		return nodeList.size();
	}

	public int edgeCount(){
		return getEdgeList().size();
	}


	// -----------------------------------------------------------------------------
	// SUCCESSORS/PREDECESSORS MANAGEMENT
	// -----------------------------------------------------------------------------
	public List<Node> getSuccessors(Node n){	
		List<Node> res = new ArrayList<Node>();
		if (n instanceof Fichier)
			return res;
		else{
			for (Edge e : edgeList){
				if (e.getStartNode().equals(n))
					res.add(e.getEndNode());
			}
		}
		return res;
	}
	



	// it has either one or zero predecessor
	public List<Node> getPredecessor(Node n){
		List<Node> predecessor = new ArrayList<Node>();
		for (Edge e : edgeList){
			if (e.getEndNode().equals(n))
				predecessor.add(e.getStartNode());
		}
		return	predecessor;
	}

	// REFACTOR ?
	//return list of predecessors of a node
	public List<Node> getListOfPredecessors(Node n){
//		System.out.println("n " + n.toString());
		List<Node> res = new ArrayList<Node>();
		if (this.getPredecessor(n).size() != 0){
			Node prec = this.getPredecessor(n).get(0);
//			System.out.println("prec " + prec.toString());
			res.add(prec);
			res.addAll(this.getListOfPredecessors(prec));
		}
		return res;
	}

	public Node getRoot() throws NotInTreeException{
		if (!this.getNodeList().isEmpty()){
			Node n = this.getNodeList().get(0);
//			System.out.println(n.toString());
			if (this.nodeList.size()==1){
				return n;
			}
			else{
				
				List<Node> prec = getListOfPredecessors(n);
//				System.out.println(prec.size());
				if (prec.isEmpty()){
					return n;
				}
				else{
				return prec.get(prec.size()-1);
				}
			}
		}
		else throw new NotInTreeException("empty graph");
	}


	public List<Node> getAllSuccessorsAux(Node n, List<Node> successors){
		if (this.getSuccessors(n).isEmpty())
			return successors;
		else{
			successors.addAll(this.getSuccessors(n));
			for (Node successor : this.getSuccessors(n)){
				successors.addAll(this.getAllSuccessorsAux(successor,new ArrayList<Node>()));
			}
			return successors;
		}
	}

	public List<Node> getAllSuccessors(Node n){
		return this.getAllSuccessorsAux(n, new ArrayList<Node>());}


	// -----------------------------------------------------------------------------
	// CONTAINS
	// -----------------------------------------------------------------------------

	// A CHANGER ?
	// verifies whether the Node n is part of the tree or not
	public boolean contains(Node n) {
//		if (this.nodeCount()==1)
//			return true;
//		else if (this.nodeCount()==0)
//			return false;
//		else 
//			
//			return (this.getListOfPredecessors(n).size() != 0 || this.getSuccessors(n).size() != 0);
		if (this.nodeList.contains(n)){return true;}
		else {return false;}
	}




	// -----------------------------------------------------------------------------
	// TREE REARRANGING (INTERNAL MOVING)
	// -----------------------------------------------------------------------------

	// get edge from nodes
	public Edge getEdgeFromNodes(Node start, Node end) throws NotInTreeException{
		for (Edge e : this.getEdgeList()){
			if ((e.getStartNode().equals(start))&&(e.getEndNode().equals(end))){
				return e;
			}	
		}
		throw new NotInTreeException();
	}


	//check tree contains edge
	public boolean containsEdge(Node start, Node end){
		for (Edge e : this.getEdgeList()){
			if ((e.getStartNode().equals(start)) && (e.getEndNode().equals(end))){
				return true;
			}	
		}
		return false;
	}


	// A METTRE DANS VIRTUAL DISK
	//moving a file hierarchy from one Folder to another
	// remark : the parent shouldn't be a successor of the node
	// ajouter une exception ?
	public void move(Node n, Directory parent ) throws NotInTreeException, ParentException, ImpossibleDisplacementException{
		if (this.getPredecessor(n).size() != 0 && !this.getAllSuccessors(n).contains(parent)){
			Node prec = this.getPredecessor(n).get(0);
			Edge edgeToDelete = getEdgeFromNodes(prec,n);
			this.deleteEdge(edgeToDelete);
			Edge edgeToAdd = new Edge(parent,n);
			this.addEdge(edgeToAdd);
		}
		else if (this.getPredecessor(n).size() == 0 && !this.getAllSuccessors(n).contains(parent))
			this.addEdge(new Edge(parent,n));
		else
			throw new ImpossibleDisplacementException();
	}


}
