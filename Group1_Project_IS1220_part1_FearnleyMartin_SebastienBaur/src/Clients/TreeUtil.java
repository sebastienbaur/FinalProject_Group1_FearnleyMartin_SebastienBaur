package Clients;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import treeImplementation.Tree;
import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import dataTypes.VirtualDisk;

public class TreeUtil {

	
	protected static void buildTreeFromString(final DefaultTreeModel model, final String str) {
        // Fetch the root node
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        // Split the string around the delimiter
        String [] strings = str.split("/");
        strings = Arrays.copyOfRange(strings, 1, strings.length);
        // Create a node object to use for traversing down the tree as it 
        // is being created
        DefaultMutableTreeNode node = root;

        // Iterate of the string array
        for (String s: strings) {
            // Look for the index of a node at the current level that
            // has a value equal to the current string
            int index = childIndex(node, s);

            // Index less than 0, this is a new node not currently present on the tree
            if (index < 0) {
                // Add the new node
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(s);
                node.insert(newChild, node.getChildCount());
                node = newChild;
            }
            // Else, existing node, skip to the next string
            else {
                node = (DefaultMutableTreeNode) node.getChildAt(index);
            }
        }
	} 
        
    protected static int childIndex(final DefaultMutableTreeNode node, final String childValue) {
        Enumeration<DefaultMutableTreeNode> children = node.children();
        DefaultMutableTreeNode child = null;
        int index = -1;

        while (children.hasMoreElements() && index < 0) {
            child = children.nextElement();

            if (child.getUserObject() != null && childValue.equals(child.getUserObject())) {
                index = node.getIndex(child);
            }
        }

        return index;
    }

    protected static JTree buildTreeFromVd(VirtualDisk vd) throws NotInTreeException{
    	
    	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Home");
        
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree tree = new JTree(model);
        
    	Tree treeVD = vd.getTree();
        List<Node> nodeList = treeVD.getNodeList();
        List<String> pathList = new ArrayList<String>();
        for (Node n : nodeList){
        	pathList.add(vd.getPath(n));
        }
        
        for (String path: pathList){
        	TreeUtil.buildTreeFromString(model,path);
        }
        
        // expands tree
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);}
        
        return tree;
    }

    protected static String treePathToString(TreePath treePath){
    	String path = treePath.toString().replaceAll("\\]||\\[|", "").replaceAll(",", "/");
    	path=path.replace("/ ","/");
    	return path;
    }
}
