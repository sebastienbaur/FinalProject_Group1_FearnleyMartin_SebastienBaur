package clients;

import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTree;

import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;
import dataTypes.Directory;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;

public class test extends JFrame {

	public static void main(String[] args)  throws NotInTreeException, VirtualDiskDoesntExistException, NoAvailableSpaceException, NotADirectoryException, ParentException, NotAnExistingFileException {
//		// TODO Auto-generated method stub
////		CLUI.crvfs("pastetest",1000);
////		CLUI.impvfs("eval/pasteTest", "pastetest", "Home");
////		JTree tree= TreeUtil.buildTreeFromVd(CLUI.getVdACNFromVfsname("pastetest").getVd());
////		JFrame f = new JFrame();
////		f.setSize(700, 500);
////	
////		
////		
////		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		f.add(tree);
//////		f.setVisible(true);
////		VirtualDisk vd456 = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
////		System.out.println(vd456.getTree());
//		
//		File f = new File("eval/Home");
////		File f0 = new File("eval/vd1.ser");
////		System.out.println(f.exists());
////		f.delete();
////		System.out.println(f.exists());
//		String[] fils = f.list();
//		System.out.println(fils[0]);
//		File f0 = new File(f.getPath() + "/" + fils[0]);
//		System.out.println(f0.exists());
//		while (fils != null){
//			if(fils.length == 1){
//				f = new File(f.getPath() +"/"+fils[0]) ;
//				System.out.println(fils[0]);
//				fils = f.list();
//				System.out.println(fils);
//			}
//			else{
//				
//			}
//		}
//		while (f0.exists()){
//			f.delete();
//			f = new File(f.getParent());
//		}
//	
//		VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
//		System.out.println(Node.uniqueId);
//		System.out.println(vd.getTree());
//		System.out.println(vd.getTree().nodeCount());
//		vd.importFileStructure("eval/Host", "Home/level 1");
//		System.out.println(vd.getTree());
//		System.out.println(vd.getTree().nodeCount());
////		vd.exportDirectory("eval/test 3", "Home")
//		for (int k=0; k<vd.getTree().nodeCount(); k++){
//			System.out.println("---------------------------------------------------");
//			System.out.println("node " + k + ", son id est : " + vd.getTree().getNodeList().get(k).id );
//		}
//		System.out.println(Node.uniqueId);

		
		// CE CODE CREE UNE STRUCTURE INFINIE EMBOITEE DE DOSSIERS
		VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
		System.out.println(vd.getTree());
		vd.importFileStructure("eval/Host", "Home/level 1");
		System.out.println(vd.getTree());
		vd.exportDirectory("eval/test", "Home");
		
		
//		 //Ce code teste getNodeFromPath dans le cas où plusieurs nodes ont le même nom
//		VirtualDisk vd1 = VirtualDisk.createVirtualDisk("jean", "eval", 1000000);
//		Directory d1 = new Directory("Home");
//		System.out.println("l'id du second Home est : " + d1.id);
//		Directory d2 = new Directory("Home");
//		System.out.println("l'id du troisième Home est : " + d2.id);
//		System.out.println("--------------------------------------------------------");
//		vd1.addDirectory("Home", d1);
//		vd1.addDirectory("Home/Home", d2);
//		System.out.println("l'id du directory retourné par getNodeFromPath est : " + vd1.getNodeFromPath("Home/Home") + " et il devrait être : " + d1.id);
		
		
//		public void mouseClicked(MouseEvent e) {
//			if (treepath != null){
//				String path = TreeUtil.treePathToString(treepath);
//				try {
//					tempTree = vd.getSubTree(path);
//					tempNode = vd.getNodeFromPath(path);
//					htmlView.setText(path + " has been copied");
//				} catch (NotInTreeException e1) {
//					htmlView.setText("path doesn't exist");
//					e1.printStackTrace();
//				}
//			}
//			else{
//				htmlView.setText("No file/directory selected");
//			}
//
//		}
		
		
		
		
	}
		
		

}
