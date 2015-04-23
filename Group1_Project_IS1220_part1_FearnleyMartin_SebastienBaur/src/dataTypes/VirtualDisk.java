package dataTypes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import treeImplementation.*;

public class VirtualDisk implements Serializable, Visitor {

	private long capacity;
	private String name;
	private Tree tree = new Tree();
	private String path;

	// -----------------------------------------------------------------------------
	// CONSTRUCTORs UNUSED BY THE USER
	// -----------------------------------------------------------------------------

	//needs to raise error !! but breaks load method
	public VirtualDisk(String name, String path, long capacity) {
		this.name = name;
		if (capacity >= 0){
			this.capacity = capacity;
		}
		else{
			System.out.println("You can't have a negative capacity");}
		this.path = path;
		this.tree = new Tree();
	}

	public VirtualDisk() {
		// TODO Auto-generated constructor stub
	}

	// -----------------------------------------------------------------------------
	// LOADING AND SAVING VIRTUAL DISKS
	// -----------------------------------------------------------------------------

	// saving using serialization
	public void saveVirtualDisk(){
		try{
			FileOutputStream fos = new FileOutputStream(this.path);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
			fos.close();
			System.out.println("Your virtual disk is saved in " + this.path);

		}catch(IOException i){
			i.printStackTrace();
		}
	}

	// loading using deserialization
	public static VirtualDisk loadVirtualDisk(String computerPath) {
		VirtualDisk vd =  new VirtualDisk("name",computerPath,100);
		try{
			FileInputStream fileIn = new FileInputStream(computerPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			vd = (VirtualDisk) in.readObject();
			Node.uniqueId = vd.getTree().nodeCount();
			Edge.uniqueId = vd.getTree().edgeCount();
			in.close();
			fileIn.close();
		}catch(IOException i){
			i.printStackTrace();
		}catch(ClassNotFoundException c){
			System.out.println("VirtualDisk class not found");
			c.printStackTrace();
		}
		return vd;
	}

	// -----------------------------------------------------------------------------
	// FILE STRUCTURE MANAGEMENT
	// -----------------------------------------------------------------------------

	// delete the file lying at the indicated path if it is a file, and the directory as well as all his sons if it's a directory
	public void deleteAll(String path) throws NotInTreeException{
		Node n = this.getNodeFromPath(path);
		this.getTree().deleteAll(n);
	}

	//adds a directory to the tree from a Directory object
	public void addDirectory(String parentLocation, Directory directory) throws NotADirectoryException, ParentException{
		try {
			if (this.getNodeFromPath(parentLocation) instanceof Directory){
				Directory parent = (Directory) this.getNodeFromPath(parentLocation);
				this.getTree().addNode(directory);
				this.getTree().addEdge(new Edge(parent,directory));
			}
			else
				throw new NotADirectoryException();
		} catch (NotInTreeException e) {
			e.printStackTrace();
		} 
	}

	//adds a node to the tree from a Fichier object
	public void addFile(String parentLocation, Fichier fichier) throws NotADirectoryException, NoAvailableSpaceException, ParentException{
		try{
			if (this.getNodeFromPath(parentLocation) instanceof Directory){
				if( fichier.getSize() < this.queryFreeSpace()){
					Directory parent = (Directory) this.getNodeFromPath(parentLocation);
					this.tree.addNode(fichier);
					this.tree.addEdge(new Edge(parent,fichier));
				}
				else
					throw new NoAvailableSpaceException("there is not enough space in the disk");
			}
			else
				throw new NotADirectoryException();
		} catch (NotInTreeException e) {
			e.printStackTrace();
		} 
	}



	public void rename(String filePath, String newName) throws NotInTreeException{
		Node node = this.getNodeFromPath(filePath);
		node.setName(newName);
	}

	// -----------------------------------------------------------------------------
	// VIRTUAL DISK MANAGEMENT
	// -----------------------------------------------------------------------------


	// delete a virtual disk from your computer
	public void deleteVirtualDisk(){
		File f = new File(this.getPath());
		f.delete();
	}

	// the user should use this static method to create a virtual disk. This method creates a base folder called home
	public static VirtualDisk createVirtualDisk(String name, String path, long capacity){
		VirtualDisk vd = new VirtualDisk(name,path,capacity);
		vd.getTree().addNode(new Directory("Home"));
		vd.saveVirtualDisk();
		return vd;
	}



	// -----------------------------------------------------------------------------
	// IMPORT/EXPORT FILE STRUCTURES
	// -----------------------------------------------------------------------------

	//for importing a new file structure into the existing structure form the computer(a file or a directory and all its sons)
	//throws error if no space in vd, or if the parent is not in the tree
	//	public void importFileStructureAux(String computerPath, String parentPath, ArrayList<Node> visited) throws NoAvailableSpaceException, NotInTreeException, NotADirectoryException, ParentException, NotAnExistingFileException {
	//
	//		if (this.getNodeFromPath(parentPath) instanceof Directory){
	//			Directory parent = (Directory) this.getNodeFromPath(parentPath);
	//			File currentFile = new File(computerPath);
	//			if (currentFile.exists()){
	//				//			System.out.println(path);
	//				long availableSpace = this.queryFreeSpace();
	//				if (currentFile.isFile()){ // if it's a file we instantiate a Fichier object
	//					Fichier f = new Fichier(currentFile.getName());
	//					f.importFile(computerPath);
	//					f.setSize(currentFile.length());
	//					if (!visited.contains(f)){
	//						if (f.getSize()<availableSpace){
	//							this.tree.addNode(f);
	//							this.tree.addEdge(new Edge(parent, f));
	//							visited.add(f);
	//						}
	//						else{
	//							throw new NoAvailableSpaceException("There is no space left on disk to add the file: " + f.getName());
	//						}
	//					}
	//				}
	//				if (currentFile.isDirectory()){ // if it's a Directory we instantiate a Directory Object
	//					Directory d = new Directory(currentFile.getName());
	//					if (!visited.contains(d)){
	//						String filename = d.getName();
	//						this.tree.addNode(d);
	//						this.tree.addEdge(new Edge(parent,d));
	//						String[] sonsPaths = currentFile.list();
	//						for (String sonPath : sonsPaths){
	//							//					System.out.println("path: "+ path);
	//							//					System.out.println(sonPath);
	//							//					System.out.println(this.getPath(d));
	//							this.importFileStructureAux(computerPath + "/" + sonPath, this.getPath(d), visited);
	//							//make sure to update path name
	//
	//						}
	//					}
	//				}
	//			}
	//			else
	//				throw new NotAnExistingFileException();
	//		}
	//		else
	//			throw new NotADirectoryException("the indicated path isn't a directory");
	//
	//
	//	}
	//	
	//	public void importFileStructure(String computerPath, String parentPath) throws NoAvailableSpaceException, NotInTreeException, NotADirectoryException, ParentException, NotAnExistingFileException{
	//		this.importFileStructureAux(computerPath, parentPath, new ArrayList<Node>());
	//	}

	public void importFileStructure(String computerPath, String parentPath) throws NoAvailableSpaceException, NotInTreeException, NotADirectoryException, ParentException, NotAnExistingFileException {

		if (this.getNodeFromPath(parentPath) instanceof Directory){
			Directory parent = (Directory) this.getNodeFromPath(parentPath);
			File currentFile = new File(computerPath);
			if (currentFile.exists()){
				//			System.out.println(path);
				long availableSpace = this.queryFreeSpace();
				if (currentFile.isFile()){ // if it's a file we instantiate a Fichier object
					Fichier f = new Fichier(currentFile.getName());
					f.importFile(computerPath);
					f.setSize(currentFile.length());
					if (f.getSize()<availableSpace){
						((Node)f).id = this.getTree().getNodeList().get((this.getTree().getNodeList().size()-1)).id + 1;
						this.tree.addNode(f);
						this.tree.addEdge(new Edge(parent, f));
					}
					else{
						throw new NoAvailableSpaceException("There is no space left on disk to add the file: " + f.getName());
					}
				}
				if (currentFile.isDirectory()){ // if it's a Directory we instantiate a Directory Object
					Directory d = new Directory(currentFile.getName());
					String filename = d.getName();
					((Node)d).id = this.getTree().getNodeList().get((this.getTree().getNodeList().size()-1)).id + 1;
					this.tree.addNode(d);
					this.tree.addEdge(new Edge(parent,d));
					String[] sonsPaths = currentFile.list();
					for (String sonPath : sonsPaths){
						//					System.out.println("path: "+ path);
						//					System.out.println(sonPath);
						//					System.out.println(this.getPath(d));
						this.importFileStructure(computerPath + "/" + sonPath, this.getPath(d));
						//make sure to update path name

					}
				}
			}
			else
				throw new NotAnExistingFileException();
		}
		else
			throw new NotADirectoryException("the indicated path isn't a directory");


	}


	//for exporting a file specified by path from the virtual disk to the computer at a place specified by computerPath
	// computerPath is the place where it'll be exported, name the one where it's currently saved within the vd
	//make sure to specify the filename in the computerPath !!
	public void exportFile(String computerPath, String filename){ 
		Fichier f = new Fichier("fichier");
		try{
			f = (Fichier) this.getNodeFromPath(filename);
			//			System.out.println(f.getName());
			f.exportFile(computerPath);
		}
		catch (NotInTreeException e) {
			e.printStackTrace();
		}
	}


	// exports Directory as specified by path (filename) to folder specified by computerPath
	// NB : it also exports all what's contained in the specified directory
	public void exportDirectory(String computerPath, String filename) throws NotInTreeException{
		Node n = this.getNodeFromPath(filename);
		//get node name (ie name of file/folder)
		String tempFileName= n.getName();

		if (n instanceof Fichier){
			this.exportFile(computerPath+"/"+tempFileName, filename);
		}
		else if (n instanceof Directory){
			(new File(computerPath+"/"+tempFileName)).mkdir();
			List<Node> suc = this.getTree().getSuccessors(n);
			for ( Node node : suc){
				exportDirectory(computerPath+"/"+tempFileName,this.getPath(node));
			}
		}
	}


	// -----------------------------------------------------------------------------
	// MOVING AND COPYING
	// -----------------------------------------------------------------------------

	//Internal moving of hierarchies
	public void move(String nodeMovedPath, String parentPath) throws NotInTreeException, NotADirectoryException, ParentException, ImpossibleDisplacementException{
		Node n = this.getNodeFromPath(nodeMovedPath);
		if (this.getNodeFromPath(parentPath) instanceof Directory){
			Directory parent = (Directory) this.getNodeFromPath(parentPath);
			this.getTree().move(n, parent);
		}
		else
			throw new NotADirectoryException();
	}

	// copying a file hierarchy from one folder to another inside the virtual disk
	//raises exception if there is no available space to recreate extra files
	public void copy(String nodeToBeCopied, String parent) throws NotInTreeException, NotADirectoryException, NoAvailableSpaceException, ParentException{
		Tree subTree = this.getSubTree(nodeToBeCopied);
		Tree subTreeCopy = new Tree();
		subTreeCopy.setNodeList(subTree.getNodeList());
		subTreeCopy.setEdgeList(subTree.getEdgeList());
		if (this.getTotalFileSize(subTreeCopy) < this.queryFreeSpace()){
			for (Node n : subTreeCopy.getNodeList()){
				this.getTree().addNode(n);
			}
			for(Edge e : subTreeCopy.getEdgeList()){
				this.getTree().addEdge(e);
			}
			this.getTree().addEdge(new Edge(this.getNodeFromPath(parent),this.getNodeFromPath(nodeToBeCopied)));
		}
		else
			throw new NoAvailableSpaceException("there is not enough space in the disk");
	}

	public Node duplicateNode(Node n){
		if (n instanceof Fichier){
			Fichier f = (Fichier) n;
			return f.duplicateFichier();
		}
		else{
			Directory d = (Directory)n;
			return d.duplicateDirectory();
		}
	}

	public Tree duplicateTree(Tree tree) throws NotInTreeException{
		Node node = tree.getRoot();
		//    	System.out.print("root is "+ tree.getRoot().toString());
		Successors succ = new Successors();

		Node duplicateNode = this.duplicateNode(node);
		succ.duplicateNodes.add(duplicateNode);
		succ.aux3(node,duplicateNode);
		Tree g = new Tree();
		g.setNodeList(succ.duplicateNodes);
		//		g.getNodeList().remove(0);
		//		System.out.println(g.getNodeList().toString());
		g.setEdgeList(succ.duplicateEdges);
		//		System.out.println(g.getEdgeList().toString());
		return g;
	}


	// -----------------------------------------------------------------------------
	// PATH MANAGEMENT
	// -----------------------------------------------------------------------------

	//returns file path of a node
	public String getPath(Node n) throws NotInTreeException{
		if (this.getTree().contains(n)){
			List<Node> nodes = new ArrayList<Node>();

			nodes = this.getTree().getListOfPredecessors(n);
			String str = new String();
			for (int i = 0; i < nodes.size(); i++)
			{
				str = nodes.get(i).getName() + "/" + str;
			}
			return str + n.getName();
		}
		else
			throw new NotInTreeException("node " + n + " isn't part of the tree");
	}


	//gets the Node object (File or Folder) from path name	
	public Node getNodeFromPath(String path) throws NotInTreeException{
		String[] directories = path.split("/");
		//gets Home node
		Node Home = this.getTree().getNodeList().get(0);
		// Initialises current node with Home, the base of the file hierarchy
		Node currentNode = Home;
		for (int i=0; i< directories.length-1; i++){
			//gets list of successors of the current node
			List<Node> suc = this.getTree().getSuccessors(currentNode);
			//goes through the successors to see if the next directory specified by path is included, throw error if not found
			boolean hasChanged = false;
			for (Node node : suc){
				if (directories[i+1].equals(node.getName())&&(this.getTree().containsEdge(currentNode, node))){
					currentNode=node;
					hasChanged = true;
					break;
				}
			}
			if (!hasChanged){
				throw new NotInTreeException();
			}
		}

		return currentNode;
	}

	//---------------------------------------------------------------------------------
	// SEARCH (INCLUDES subTree AND GETALLSUCCESSORS)
	//---------------------------------------------------------------------------------

	//search function, returns list of files from string name (gives back a list because multiple files may have the same name)
	public List<Node> search (String name) throws NotInTreeException{
		List<Node> res = new ArrayList<Node>();
		for (Node n : this.getTree().getNodeList()){
			if((n.getName().equals(name))&& (n instanceof Fichier)){
				res.add((Fichier)n);
			}
			else if((n.getName().equals(name))&& (n instanceof Directory)){
				res.add((Directory)n);
			}
		}
		if (res.isEmpty()){
			throw new NotInTreeException();
		}
		return res;
	}

	//returns a list of all files and folders in a given directory
	public List<Node> getAllSuccessors(String path) throws NotInTreeException{
		Node node = this.getNodeFromPath(path);
		Successors succ = new Successors();
		succ.nodes.add(node);
		succ.aux(node);
		return succ.nodes;
	}

	//returns the sub tree of a tree starting from the node specified by path
	public Tree getSubTree(String path) throws NotInTreeException{
		Node node = this.getNodeFromPath(path);
		Successors succ = new Successors();
		succ.aux2(node);
		succ.nodes.add(node);
		Tree g = new Tree();
		g.setNodeList(succ.nodes);
		g.setEdgeList(succ.edges);
		return g;
	}

	// nested class used for the getAllSuccessors function, we needed to make aux a recursive function, but have a fixed variable to store the results (res)
	private class Successors{
		List<Node> nodes = new ArrayList<Node>();
		List<Edge> edges = new ArrayList<Edge>();
		List<Node> duplicateNodes = new ArrayList<Node>();
		List<Edge> duplicateEdges = new ArrayList<Edge>();

		public void aux(Node node) throws NotInTreeException{
			//Node node = VirtualDisk.this.getNodeFromPath(path); 
			if(node instanceof Fichier){
				//				this.nodes.add(node);
			}
			else{
				List<Node> succ = VirtualDisk.this.getTree().getSuccessors(node);
				//				this.nodes.add(node);
				for (Node n: succ){
					this.nodes.add(n);
					aux(n);
				}
			}      
		}      
		//for duplicating tree
		public void aux3(Node node, Node duplicateNode) throws NotInTreeException{
			//Node node = VirtualDisk.this.getNodeFromPath(path); 
			Node duplicateNodeSuc;
			if(node instanceof Fichier){
				//				duplicateNode1=duplicateNode(node);
				//				this.duplicateNodes.add(duplicateNode1);
			}
			else{
				List<Node> succ = VirtualDisk.this.getTree().getSuccessors(node);

				for (Node n: succ){
					duplicateNodeSuc=duplicateNode(n);
					this.duplicateNodes.add(duplicateNodeSuc);
					this.duplicateEdges.add(new Edge(duplicateNode, duplicateNodeSuc));
					aux3(n,duplicateNodeSuc);
				}
			}      
		}
		//for getting a subtree
		public void aux2(Node node) throws NotInTreeException{
			//Node node = VirtualDisk.this.getNodeFromPath(path); 
			if(node instanceof Fichier){
				//				this.nodes.add(node);
			}
			else{
				List<Node> succ = VirtualDisk.this.getTree().getSuccessors(node);

				for (Node n: succ){
					this.nodes.add(n);
					this.edges.add(VirtualDisk.this.getTree().getEdgeFromNodes(node, n));
					aux2(n);
				}
			}      
		}
	}

	//for searching in specific directory 
	public List<Node> search (String name, String parent) throws NotInTreeException{
		List<Node> hierarchy = this.getAllSuccessors(parent);
		List<Node> res = new ArrayList<Node>();
		for (Node n : hierarchy){
			if((n.getName().equals(name))&& (n instanceof Fichier)){
				res.add((Fichier)n);
			}
			else if((n.getName().equals(name))&& (n instanceof Directory)){
				res.add((Directory)n);
			}
		}
		if (res.isEmpty()){
			throw new NotInTreeException();
		}
		return res;
	}



	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS AND EQUALS METHOD
	//---------------------------------------------------------------------------------


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (capacity ^ (capacity >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((tree == null) ? 0 : tree.hashCode());
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
		VirtualDisk other = (VirtualDisk) obj;
		if (capacity != other.capacity)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (tree == null) {
			if (other.tree != null)
				return false;
		} else if (!tree.equals(other.tree))
			return false;
		return true;
	}





	public long getCapacity() {
		return capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String newpath){
		this.path = newpath;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}


	//-------------------------------------------------------------------------------------------
	// VISITING
	//-------------------------------------------------------------------------------------------

	@Override
	public long visit(Fichier f) {
		return f.getSize();
	}

	@Override
	public long visit(Directory d) {
		return 0;
	}

	//	// query free space left in virtual disk
	//	public long queryFreeSpace(){
	//		long totalSize = this.tree.getTotalFileSize();
	//		return (this.capacity - totalSize);
	//	}

	//return the total file size of all files contained in tree
	public long getTotalFileSize(Tree t) {
		long totalSize = 0;
		for (Node n : t.getNodeList()){
			if (n instanceof Fichier){
				Fichier f = (Fichier) n;
				totalSize = totalSize + f.getSize();
			}
		}
		return totalSize;
	}

	public long queryFreeSpace(){
		long occupiedSpace = 0;
		for (Node n : this.getTree().getNodeList()){
			if (n instanceof Fichier)
				occupiedSpace += ((Fichier)n).accept(this);
			if (n instanceof Directory)
				occupiedSpace += ((Directory)n).accept(this);
		}
		return (this.capacity - occupiedSpace);
	}

//	@Override
//	public String toString() {
//		return "VirtualDisk [tree=" + tree + "]";
//	}



}