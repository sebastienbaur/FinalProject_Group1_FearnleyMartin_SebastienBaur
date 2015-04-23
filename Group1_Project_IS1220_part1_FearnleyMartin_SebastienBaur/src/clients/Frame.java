package clients;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import treeImplementation.Edge;
import treeImplementation.Node;
import treeImplementation.NotInTreeException;
import treeImplementation.ParentException;
import treeImplementation.Tree;
import dataTypes.NoAvailableSpaceException;
import dataTypes.NotADirectoryException;
import dataTypes.NotAnExistingFileException;
import dataTypes.VirtualDisk;

public class Frame extends JFrame implements TreeSelectionListener, ActionListener, MouseListener, KeyListener{

	//	CLUI.crvfs("vfsGUItest",1000);

	//	VirtualDisk vd = VirtualDisk.loadVirtualDisk("virtual disks/vdlevel1.ser");
	//	VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);



	protected VirtualDisk vd;
	protected JTree tree;
	protected TreePath treepath=null;
	protected Tree tempTree=null;
	protected Node tempNode=null;
	protected int index;
	protected JPanel pane;

	public VirtualDisk getVd() {
		return vd;
	}

	public void setVd(VirtualDisk vd) {
		this.vd = vd;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	private JTextField commandLinePrinting = new JTextField(20);
	private JEditorPane commandLineWriting = new JEditorPane();
	//	protected JEditorPane htmlPane = new JEditorPane();
	private JPanel panLeft = new JPanel();
	//	private JPanel panUpRight = new JPanel();
	private JPanel panDownRight = new JPanel();
	private JTabbedPane tabbedPanUpRight = new JTabbedPane();
	JTextArea htmlView = new JTextArea();

	//text fields
	JTextField renameTextField = new JTextField();
	JTextField copyTextField = new JTextField();
	JTextField createVFSTextField = new JTextField(); // maybe add two others ones for size and location and name
	JTextField importFileStructureTextField = new JTextField(); // maybe open a window of navigation AND maybe others text boxes
	JTextField exportVFSTextField = new JTextField();
	JTextField findTextField = new JTextField();
	JTextField helpTextField = new JTextField();
	JTextField loadTextField = new JTextField();

	private JButton buttonRename = new JButton("Rename");
	private JButton buttonCopy = new JButton("Copy");
	private JButton buttonPaste = new JButton("Paste");
	private JButton buttonRemoveVFS = new JButton("Remove VFS");
	private JButton buttonRemoveFile = new JButton("Remove file");
	private JButton buttonCreateVFS = new JButton("Create VFS");
	private JButton buttonImport = new JButton("Import file structure"); 
	private JButton buttonExport = new JButton("Export VFS");
	private JButton buttonFind = new JButton("Find");
	private JButton buttonHelp = new JButton("Help");
	private JButton buttonCut = new JButton("Cut");
	private JButton buttonLoad = new JButton("Load");
	private JButton buttonFreeSpace = new JButton("Query Free Space");


	public Frame() throws NotInTreeException{
		this.setResizable(false);
		this.setSize(1000, 500);
		JFrame frame = new JFrame();
		this.setTitle("VFS Management");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		htmlView.setEditable(false);
		//		tree = TreeUtil.buildTreeFromVd(vd);
		//		VdcnManagement.getVdList().add(vdcn);
		//		panUpRight.add(tree);

		// ajout des panneaux aux bonnes positions
		this.getContentPane().add(panLeft, BorderLayout.WEST);
		this.getContentPane().add(tabbedPanUpRight, BorderLayout.CENTER);
		this.getContentPane().add(panDownRight, BorderLayout.SOUTH);

		panLeft.setLayout(new GridLayout(13,2));

		panLeft.add(buttonRename);
		panLeft.add(renameTextField);
		panLeft.add(buttonCreateVFS);
		panLeft.add(createVFSTextField);
		panLeft.add(buttonImport);
		panLeft.add(importFileStructureTextField);
		panLeft.add(buttonExport);
		panLeft.add(exportVFSTextField);
		panLeft.add(buttonFind);
		panLeft.add(findTextField);
		panLeft.add(buttonLoad);
		panLeft.add(loadTextField);
		panLeft.add(buttonCopy);
		panLeft.add(new JPanel());
		panLeft.add(buttonCut);
		panLeft.add(new JPanel());
		panLeft.add(buttonPaste);
		panLeft.add(new JPanel());
		panLeft.add(buttonRemoveFile);
		panLeft.add(new JPanel());
		panLeft.add(buttonRemoveVFS);
		panLeft.add(new JPanel());
		panLeft.add(buttonFreeSpace);
		panLeft.add(new JPanel());
		panLeft.add(buttonHelp);
		panLeft.add(helpTextField);

		TextPrompt renametp = new TextPrompt("<new name>",renameTextField);
		TextPrompt createvfstp = new TextPrompt("<vfsname> <size>",createVFSTextField);
		TextPrompt ifstp = new TextPrompt("<host path>",importFileStructureTextField);
		TextPrompt expvfstp = new TextPrompt("<export path>",exportVFSTextField);
		TextPrompt findtp = new TextPrompt("<filename>",findTextField);
		TextPrompt helptp = new TextPrompt("<command>",helpTextField);
		TextPrompt loadtp = new TextPrompt("<host path>",loadTextField);


		//		panUpRight.add(tree);
		//		tabbedPanUpRight.addTab("vfs1",panUpRight);

		Box b = Box.createVerticalBox();
		b.setPreferredSize(new Dimension(900,100));
		Box lineWriting = Box.createHorizontalBox();
		lineWriting.add(new JLabel("Write here : "));
		lineWriting.add(commandLineWriting);
		b.add(lineWriting);
		Box lineReading = Box.createHorizontalBox();
		lineReading.add(new JLabel("Read here : "));
		JScrollPane htmlContainer = new JScrollPane(htmlView);
		htmlContainer.setPreferredSize(new Dimension(700,100));
		htmlContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		lineReading.add(htmlContainer);
		b.add(Box.createVerticalStrut(10));
		b.add(lineReading);
		panDownRight.add(b);

		buttonRename.addMouseListener(new RenameButtonListener());
		buttonCopy.addMouseListener(new CopyButtonListener());
		buttonPaste.addMouseListener(new PasteButtonListener());
		buttonRemoveVFS.addMouseListener(new RemoveVFSButtonListener());
		buttonRemoveFile.addMouseListener(new RemoveFileButtonListener());
		buttonCreateVFS.addMouseListener(new CreateVFSButtonListener());
		buttonImport.addMouseListener(new ImportButtonListener());
		buttonExport.addMouseListener(new ExportButtonListener());
		buttonFind.addMouseListener(new FindButtonListener());
		buttonHelp.addMouseListener(new HelpButtonListener());
		tabbedPanUpRight.addMouseListener(new TabMouseListener());
		buttonCut.addMouseListener(new CutButtonListener());
		buttonLoad.addMouseListener(new LoadButtonListener());
		buttonFreeSpace.addMouseListener(new FreeSpaceButtonListener());

		//		VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
		//		VdcnManagement.getVdList().add(vdcn);
		//		int index = tabbedPanUpRight.getSelectedIndex();
		//		try {
		//			vd = CLUI.getVdACNFromVfsname(tabbedPanUpRight.getTitleAt(index)).getVd();
		//		} catch (VirtualDiskDoesntExistException e1) {
		//			e1.printStackTrace();
		//		}
		//		tree = TreeUtil.buildTreeFromVd(vd);
		//		panUpRight.add(tree);
		//		tabbedPanUpRight.addTab(vd.getName(),panUpRight);
		//		tree.addTreeSelectionListener(new SelectionListener());
		this.setVisible(true);


	}



	@Override
	public void valueChanged(TreeSelectionEvent e) {
		//		treepath = e.getPath();
		//		htmlPane.setText( TreeUtil.treePathToString(treepath));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {


	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub


	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	class FindButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			String filename = findTextField.getText();
			if (VdcnManagement.vdList.isEmpty()){
				htmlView.setText("There is no virtual disk to search in");
			}
			else{
				if (filename.equals("") || filename.equals(null) || filename == null){
					htmlView.setText("Please enter a valid research query");
				}
				else{
					String res = "";
					List<Node> list = null;
					try {
						list = vd.search(filename);
						for (Node n : list){

							try {
								res = res + vd.getPath(n) + "\n" ;
							} catch (NotInTreeException e1) {
								htmlView.setText("Cannot get the path of " +n.getName());
								//						e1.printStackTrace();
							}
						}
						htmlView.setText(res);
					} catch (NotInTreeException e1) {
						htmlView.setText(filename + " cannot be found in "+vd.getName());
						//				e1.printStackTrace();
					} 
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class HelpButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			String str = new String();
			String calledFunction = helpTextField.getText().toLowerCase();
			switch (calledFunction){
			case "create vfs":
				str +="To create a new VFS with name vfsname and maximal dimension dim bytes\n " ;
				str +="Syntax: <vfsname> <capacity>\n " ;
				break;
			case "rename":
				str +="To change the name of a file/directory which is currently selected.\n " ;
				str +="Syntax: <vfsNewName>\n " ;
				str +="It will change the older name of the current vfs by vfsNewName. \n";
				break;
			case "load" :
				str+= "allows you to load a virtual disk the computerpath (absolute or relative) of which is written in the text field next to it \n";
				str +="Syntax : <computerpath> \n";
			case "copy":
				str +="To copy, within the VFS named vfsname, the content of a file/directory which is currently selected.\n " ;
				str +="<targetpath>\n " ;
				str +="It will copy the currently selected file/directory in the directory the path of which is targetpath \n";
				break;
			case "paste" :
				str += "After having copied a file/directory, allows you to paste it into the current selected directory \n";
				str += "Syntax : just copy a first file/directory and then select the place where you want to paste it. Then click on paste.\n";
			case "remove file":
				str +="To remove a file/directory which is currently selected.\n " ;
				str +="Syntax: just select the file/directory to be removed and click on the remove file button \n " ;
				break;
			case "remove vfs":
				str +="To delete a VFS currently selected\n " ;
				str +="Syntax: select the tab of the VFS to be removed and click on the remove VFS button \n " ;
				break;
			case "import file structure":
				str +="To import the content of the directory/file corresponding to absolute name hostpath on the host file system into the position given by the selected VFS and directory.\n " ;
				str +="Syntax: <hostpath> \n " ;
				break;
			case "export vfs":
				str +="To export the selected VFS into the absolute path named hostpath of the host file system\n " ;
				str +="Syntax: <hostpath>\n " ;
				break;
			case "free":
				str += "To display the quantity of free/occupied space for the selected VFS \n " ;
				str += "Syntax: free \n " ;
				break;
			case "find":
				str +="To search if a file named filename is stored in the selected VFS, shall return the absolute path of the sought file if it is present in the VFS, null otherwise\n " ;
				str +="Syntax: <filename>\n " ;
				break;
			case "help":
				str +="To display an \"help message\" (similar to that of unix shell terminal) which gives information about how to use the command named command-name. If help is invoked without <command-name> argument then it should display a generic help message about how to use the CLUI (e.g. general syntax of a CLUI command, list of all CLUI commands name).\n " ;
				str +="<command-name>\n " ;
				str +="if nothing is written, gives you general information about the commands\n";
				break;
				//			case "gen":
				//				str +="Generates a tree of the current file system\n " ;
				//				str +="Syntax gen <vfsname>\n " ;
				//				break;
			case "" : 
				//				str += "Commands take the following general syntax: command <arg1> <arg2> <arg3> \n";
				str += "Write the buttons' labels in the text field next to the Help button and click again on Help to get more information about how to use them \n";
				break;
			case "cut" : 
				str += "";
				break;
			case "query free space" :
				str += "Prints the free space of the selected virtual disk";
			default: 
				str += "";
			}
			htmlView.setText(str);
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ExportButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			String hostpath = exportVFSTextField.getText();
			if (!hostpath.equals("")&& hostpath!=null){
				CLUI.expvfs(vd.getName(), hostpath);
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class ImportButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (treepath != null){
				//				JPanel pane = (JPanel)tabbedPanUpRight.getComponentAt(index);

				pane.removeAll();
				String parent = TreeUtil.treePathToString(treepath);	
				String hostpath = importFileStructureTextField.getText();

				try {
					vd.importFileStructure(hostpath, parent);
				} catch (NoAvailableSpaceException e2) {
					htmlView.setText("there isn't enough space left on your virtual disk");
				} catch (NotInTreeException e2) {
					e2.printStackTrace();
				} catch (NotADirectoryException e2) {
					htmlView.setText("you can't import anything in a Fichier object");
				} catch (ParentException e2) {
					e2.printStackTrace();
				} catch (NotAnExistingFileException e2) {
					htmlView.setText("the entered hostpath isn't a valid file");
				}

				try {
					tree = TreeUtil.buildTreeFromVd(vd);
				} catch (NotInTreeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



				pane.add(tree);

				tree.addTreeSelectionListener(new SelectionListener());
				revalidate();	
				repaint();
			}
			else{
				htmlView.setText("No file/directory selected");
			}

		}

	}

	class CopyButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if (treepath != null){
				String path = TreeUtil.treePathToString(treepath);
				try {
					tempTree = vd.getSubTree(path);
					tempNode = vd.getNodeFromPath(path);
					commandLinePrinting.setText(path + " has been copied");
				} catch (NotInTreeException e1) {
					htmlView.setText("path doesn't exist");
					e1.printStackTrace();
				}
			}
			else{
				htmlView.setText("No file/directory selected");
			}

		}



		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class CutButtonListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (treepath != null){
				pane.removeAll();
				String path = TreeUtil.treePathToString(treepath);
				try {
					tempTree = vd.getSubTree(path);
					//                              tempTree = vd.duplicateTree(vd.getSubTree(path));
					//					System.out.println(tempTree.toString());
					tempNode = vd.getNodeFromPath(path);
					commandLinePrinting.setText(path + " has been cut");
					CLUI.rm(vd.getName(),path);
					//					System.out.println(tempTree.toString());
					try {
						tree = TreeUtil.buildTreeFromVd(vd);
					} catch (NotInTreeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pane.add(tree);
					tree.addTreeSelectionListener(new SelectionListener());
					revalidate();
					repaint();
				} catch (NotInTreeException e1) {
					htmlView.setText("path doesn't exist");
					e1.printStackTrace();
				}
			}
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class PasteButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (treepath != null){
				String parent = TreeUtil.treePathToString(treepath);
				if (tempTree!=null && tempNode!=null){
					Tree subTreeCopy = null;
					try {
						subTreeCopy = vd.duplicateTree(tempTree);
					} catch (NotInTreeException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					if (subTreeCopy!=null){
						if (vd.getTotalFileSize(subTreeCopy) < vd.queryFreeSpace()){
							//				        	System.out.println(tempNode.toString());
							//				        	System.out.println(parent.toString());

							pane.removeAll();    

							for (Node n : subTreeCopy.getNodeList()){
								vd.getTree().addNode(n);
								//								System.out.println("add: " + n.toString());

							}
							for(Edge e : subTreeCopy.getEdgeList()){
								try {

									vd.getTree().addEdge(e);
									//									System.out.println("add edge: " + e.toString());

								} catch (ParentException e1) {
									e1.printStackTrace();
								} catch (NotInTreeException e1) {
									e1.printStackTrace();
								}
							}

							try {
								Edge edgeToAdd = new Edge(vd.getNodeFromPath(parent),subTreeCopy.getRoot());
								vd.getTree().addEdge(edgeToAdd);
								//								System.out.println("add edge (parent) " + edgeToAdd.toString());
							} catch (ParentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (NotInTreeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								tree = TreeUtil.buildTreeFromVd(vd);
								//								System.out.println("ok");
							} catch (NotInTreeException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//							CLUI.ls(vd.getName(), "", "");
							//							System.out.println(vd.getTree().getNodeList().toString());
							//							System.out.println(vd.getTree().getEdgeList().toString());
							pane.add(tree);
							tree.addTreeSelectionListener(new SelectionListener());
							revalidate();
							repaint();   
						}
						else{commandLinePrinting.setText("error while copying");}
					}
					else {commandLinePrinting.setText("Not enough available space in virtual disk");}
				}
				else{commandLinePrinting.setText("Please copy something first");}
			}
			else{commandLinePrinting.setText("Please select a place to copy to on the tree");}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class TabMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (VdcnManagement.vdList.isEmpty()){
				htmlView.setText("There is still not any opened virtual disk. Please use the create VFS or the Load button to add one");
			}
			else{
				int index = tabbedPanUpRight.getSelectedIndex();
				pane = (JPanel)tabbedPanUpRight.getComponentAt(index);
				String nameVFS = tabbedPanUpRight.getTitleAt(index);
				try {
					vd = CLUI.getVdACNFromVfsname(nameVFS).getVd();
					tree = TreeUtil.buildTreeFromVd(vd);
				} catch (VirtualDiskDoesntExistException e1) {
					e1.printStackTrace();
				} catch (NotInTreeException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}


		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class CreateVFSButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		// PEUT ETRE CHANGER LA FACON D'OBTENIR LE DERNIER INDICE EN UTILISANT getVdACNFromVfsname au cas où multithread
		@Override
		public void mouseReleased(MouseEvent e) {
			// ordre des arguments : name capacity
			String enteredText = createVFSTextField.getText();
			List<String> splitEnteredText = CLUI.preTreatment(enteredText);
			if (splitEnteredText.size() != 2){
				htmlView.setText("You must enter first the name and second the capacity of the Vitual Disk !");
			}
			else{
				try{
					CLUI.crvfs(splitEnteredText.get(0), Integer.valueOf(splitEnteredText.get(1)));
					vd = CLUI.getVdACNFromVfsname(splitEnteredText.get(0)).getVd();
					JPanel vdContent = new JPanel();
					vdContent.setName(splitEnteredText.get(0));

					try {
						tabbedPanUpRight.addTab(vd.getName(), vdContent);
						index = tabbedPanUpRight.indexOfTab(vdContent.getName());
						pane = (JPanel)tabbedPanUpRight.getComponentAt(index);
						tabbedPanUpRight.setSelectedIndex(index);
						tree = TreeUtil.buildTreeFromVd(vd);
						vdContent.add(tree);
						tree.addTreeSelectionListener(new SelectionListener());
					} catch (NotInTreeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				catch(NumberFormatException i){
					htmlView.setText("Error : The second argument must be the capacity of the Virtual Disk, of type int");
				} catch (VirtualDiskDoesntExistException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}


			//				VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
			//				VdcnManagement.getVdList().add(vdcn);
			//				int index = tabbedPanUpRight.getSelectedIndex();
			//				try {
			//					vd = CLUI.getVdACNFromVfsname(tabbedPanUpRight.getTitleAt(index)).getVd();
			//				} catch (VirtualDiskDoesntExistException e1) {
			//					e1.printStackTrace();
			//				}
			//				try {
			//					tree = TreeUtil.buildTreeFromVd(vd);
			//				} catch (NotInTreeException e1) {
			//					e1.printStackTrace();
			//				}
			//				panUpRight.add(tree);
			//				tabbedPanUpRight.addTab(vd.getName(),panUpRight);
			//				tree.addTreeSelectionListener(new SelectionListener());

		}

	}

	class RemoveVFSButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (VdcnManagement.vdList.isEmpty()){
				htmlView.setText("there isn't any virtual disk to be removed");
			}
			else{
				Component component = tabbedPanUpRight.getSelectedComponent();
				String nameVFS = tabbedPanUpRight.getTitleAt(index);
				CLUI.rmvfs(nameVFS);
				//			VirtualDisk searchedVD = new VirtualDisk();
				//			for (VdAndCurrentNode vdACN : VdcnManagement.vdList)
				//			{
				//				VirtualDisk vd = vdACN.getVd();
				//				if (vd.getName().equals(name)){
				//					searchedVD = vd;
				//					break;
				//				}
				//			}
				//			VdcnManagement.vdList.remove(searchedVD);
				tabbedPanUpRight.remove(component);
			}
		}


	}

	class RemoveFileButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (treepath != null){
				pane.removeAll();
				String oldPath = TreeUtil.treePathToString(treepath);	

				CLUI.rm(vd.getName(), oldPath);
				try {
					tree = TreeUtil.buildTreeFromVd(vd);
				} catch (NotInTreeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				pane.add(tree);
				tree.addTreeSelectionListener(new SelectionListener());
				revalidate();
				repaint();
			}
			else{
				htmlView.setText("No file/directory selected");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class MoveButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class RenameButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (treepath != null){
				if ((!renameTextField.getText().equals("")) && (renameTextField.getText()!=null)){
					pane.removeAll();
					String oldPath = TreeUtil.treePathToString(treepath);	
					String newPath = renameTextField.getText();
					CLUI.mv(vd.getName(), oldPath, newPath);
					try {
						tree = TreeUtil.buildTreeFromVd(vd);
					} catch (NotInTreeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pane.add(tree);
					tree.addTreeSelectionListener(new SelectionListener());
					revalidate();
					repaint();
				}
				else {htmlView.setText("Please enter a new name for the file/directory");}
			}
			else{
				htmlView.setText("No file/directory selected");
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	class SelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent arg0) {
			treepath = arg0.getPath();
			htmlView.setText( TreeUtil.treePathToString(treepath));
		}

	}

	public class LoadButtonListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			String enteredPath = loadTextField.getText();
			if (enteredPath.equals(null) || enteredPath.equals(""))
				htmlView.setText("please enter a valid path");
			else{
				vd = VirtualDisk.loadVirtualDisk(enteredPath);
				VdAndCurrentNode vdcn = new VdAndCurrentNode(vd); // the virtual disk vd should also be added to the list of virtual disks already opened
				VdcnManagement.vdList.add(vdcn);
				JPanel vdContent = new JPanel(); // creation of a pane that will contain the loaded virtual disk vd
				try{
					tabbedPanUpRight.addTab(vd.getName(), vdContent); // add a tab containing the JTree representing vd
					index = tabbedPanUpRight.indexOfTab(vd.getName()); // updating of index
					pane = (JPanel) tabbedPanUpRight.getComponentAt(index); // updating of pane
					tabbedPanUpRight.setSelectedIndex(index); // selection of the tab that has just been added
					tree = TreeUtil.buildTreeFromVd(vd); // creation of the tree from vd, which has just been loaded
					vdContent.add(tree); // adding of the newly created tree to vdContent, which is contained in the new tab of tabbedPanUpRight
					tree.addTreeSelectionListener(new SelectionListener()); // adding of a mouse listener, such as clicking on the tree causes an action of the program
				}
				catch(NotInTreeException e1){
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class FreeSpaceButtonListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				htmlView.setText(String.valueOf(vd.queryFreeSpace()));
			} catch (NullPointerException e2){}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
