package clients;

import java.io.File;
import java.util.*;

import treeImplementation.*;
import dataTypes.*;

public class CLUI {

	//list contents of directory
	public static void ls(String vfsname, String args, String pathname){
		VdAndCurrentNode vdcn;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			Node currentNode = vdcn.getCurrentNode();

			//all files & folders
			if (pathname.equals("")){
				switch(args){
				case "":
					List<Node> succ;
					try {
						succ = vd.getAllSuccessors(vd.getPath(currentNode));
						for (Node n : succ){
							if (n instanceof Fichier){
								System.out.println(n.getName()+"    "+"f");
							}
							else{
								System.out.println(n.getName()+"    "+"d");
							}
						}
						break;
					} catch (NotInTreeException e) {
						System.out.println("The current node: "+ currentNode + " is irretrievable");
						
					}

					//prints size too
				case "-l":
					List<Node> succ2;
					try {
						succ2 = vd.getAllSuccessors(vd.getPath(currentNode));
						for (Node n : succ2){
							if (n instanceof Fichier){
								System.out.println(n.getName()+"    "+((Fichier)n).getSize() + "    "+"f");
							}
							else{
								Tree subTree = vd.getSubTree(vd.getPath(n));
								long size = vd.getTotalFileSize(subTree);
								System.out.println(n.getName()+"    "+ size+"     "+"d");
							}
						}
						break;
					} catch (NotInTreeException e) {
						System.out.println("The current node: "+ currentNode + " is irretrievable");
						
					}


				}
			}
			//files & folders in pathname
			else{
				switch(args){
				case "":
					List<Node> succ;
					try {
						succ = vd.getAllSuccessors(pathname);
						for (Node n : succ){
							if (n instanceof Fichier){
								System.out.println(n.getName()+"    "+"f");
							}
							else{

								System.out.println(n.getName()+"     "+"d");
							}
						}
						break;
					} catch (NotInTreeException e) {
						System.out.println("The current node: "+ currentNode + " is irretrievable");
						
					}

					//prints size too
				case "-l":
					List<Node> succ2;
					try {
						succ2 = vd.getAllSuccessors(pathname);
						for (Node n : succ2){
							//							System.out.println(succ2);
							if (n instanceof Fichier){
								System.out.println(n.getName()+"    "+((Fichier)n).getSize() + "    "+"f");
							}
							else{
								Tree subTree = vd.getSubTree(vd.getPath(n));
								long size = vd.getTotalFileSize(subTree);
								System.out.println(n.getName()+"    "+ size+"     "+"d");
							}
						}
						break;
					} catch (NotInTreeException e) {
						System.out.println("The current node: "+ currentNode + " is irretrievable");
						
					}
				}
			}
		} catch (VirtualDiskDoesntExistException e) {
			System.out.print("No virtual disk called '" + vfsname + "' exists");
			
		}

	}

	//returns vd and its current node object from the name of a vfs
	public static VdAndCurrentNode getVdACNFromVfsname(String vfsname) throws VirtualDiskDoesntExistException{
		for (VdAndCurrentNode vdcn : VdcnManagement.getVdList()){
			if(vdcn.getVd().getName().equals(vfsname)){
				return vdcn;
			}
		}
		throw new VirtualDiskDoesntExistException("No virtual disk called '" + vfsname + "' exists");
	}

	//navigate to directory
	public static void cd(String vfsname, String pathname){
		VdAndCurrentNode vdcn;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			Node nodeSpecified;
			if (pathname.equals("..")){
				Node parentNode = null;
				if (!vdcn.getVd().getTree().getPredecessor(vdcn.getCurrentNode()).isEmpty()){
					parentNode = vdcn.getVd().getTree().getPredecessor(vdcn.getCurrentNode()).get(0);
					vdcn.setCurrentNode(parentNode);
					System.out.println("The current node is " + vdcn.getCurrentNode());
				}
				else{
					System.out.println("There is no parent node");
				}
				
			}
			else{
				try {
					nodeSpecified = vdcn.getVd().getNodeFromPath(pathname);
					vdcn.setCurrentNode(nodeSpecified);
					System.out.println("The current node is " + nodeSpecified);
				} catch (NotInTreeException e) {
					System.out.println(pathname + " not found");
					
				}
			}

		} catch (VirtualDiskDoesntExistException e) {
			System.out.println("No virtual disk called '" + vfsname + "' exists");
			
		}

	}

	//to create a new virtual disk
	public static void crvfs(String vfsname, long dim) {
		Scanner sc = new Scanner(System.in);
		String answer = new String();
		File f = new File("virtual disks/"+vfsname+".ser");
		if (f.exists()){
			System.out.println("There is already a file called virtual disks/"+vfsname+".ser" + ". Are you sure you want to replace it ? (Y/N)");
			answer = sc.nextLine();
			if (answer.equals("Y")){
				VirtualDisk vd = VirtualDisk.createVirtualDisk(vfsname, "virtual disks/"+ vfsname+ ".ser", dim);
				VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
				VdcnManagement.getVdList().add(vdcn);
			}
			else if (answer.equals("N")){
				System.out.println("Nothing's been deleted. You can still create this virtual disk with another name by reusing this command");
			}
			else
				System.out.println("Nothing happened because your answer is neither Y nor N. You can try again the command if you want to create a virtual disk");
		}
		else{
			VirtualDisk vd = VirtualDisk.createVirtualDisk(vfsname, "Virtual Disks/"+ vfsname+ ".ser", dim);
			VdAndCurrentNode vdcn = new VdAndCurrentNode(vd);
			VdcnManagement.getVdList().add(vdcn);
		}
	}

	//renames files/directory
	public static void mv (String vfsname, String oldpath, String newpath) {
		VdAndCurrentNode vdcn;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			String[] str= newpath.split("/");
			String newName = str[str.length-1];		
			try {
				vdcn.getVd().rename(oldpath, newName);
			} catch (NotInTreeException e) {
				System.out.println(oldpath + " not found");
				
			}
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println("No virtual disk called '" + vfsname + "' exists");
			
		}

	}

	//copy
	public static void cp(String vfsname, String sourcepath,String targetpath) throws VirtualDiskDoesntExistException, NotInTreeException, NotADirectoryException, NoAvailableSpaceException, ParentException{
		VdAndCurrentNode vdcn= getVdACNFromVfsname(vfsname);
		VirtualDisk vd = vdcn.getVd();
		//change targetpath into parent path
		String[] str= targetpath.split("/");
		String parentPath = "";
		for (int i=0; i<str.length-2;i++){
			parentPath = parentPath+str[i]+"/";
		}
		parentPath = parentPath+str[str.length-2];
		System.out.println(parentPath);
		vd.copy(sourcepath, parentPath);
	}

	//import file structure into vd
	public static void impvfs(String hostpath,String vfsname, String vfspath){
		VdAndCurrentNode vdcn = null;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			try { 
				vd.importFileStructure(hostpath, vfspath);
				System.out.println(hostpath + " imported into "+ vfsname);
			} catch (NoAvailableSpaceException e) {
				System.out.println("No available space");
				
			} catch (NotInTreeException e) {
				System.out.println(vfspath + " not found");
				
			} catch (NotADirectoryException e) {
				System.out.println(vfspath + " is not a directory");
				
			} catch (ParentException e) {
				System.out.println("parent exception: file system you are importing is corrupted");
				
			} catch (NotAnExistingFileException e) {
				System.out.println("The hostpath you entered isn't the path of an existing file or directory");
			}
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");

		}

	}

	//exports home aswell
	public static void expvfs(String vfsname, String hostpath){
		VdAndCurrentNode vdcn = null;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			try {
				vd.exportDirectory(hostpath, "Home");
			} catch (NotInTreeException e) {
				System.out.println("Home directory has been deleted, cannot export the vfs as vfs is damaged");
				}
			System.out.println(vfsname + " exported into "+ hostpath);
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");
			
		}

	}

	//queries free space on the vd
	public static void free(String vfsname){
		try {
			VdAndCurrentNode vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			System.out.println(vd.queryFreeSpace());
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");
			
		}
	}

	//remove a vd
	public static void rmvfs (String vfsname){
		VdAndCurrentNode vdcn;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			vdcn.getVd().deleteVirtualDisk();
			VdcnManagement.getVdList().remove(vdcn);
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println("No virtual disk called '" + vfsname + "' exists");
			
		}

	}

	//remove file/directory from vd
	public static void rm(String vfsname, String pathname){
		VdAndCurrentNode vdcn;
		try {
			vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			try {
				vd.deleteAll(pathname);
			} catch (NotInTreeException e) {
				System.out.println(pathname + " not found in "+ vfsname);
				
			}
		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");
			
		}


	}

	//unused
	public static String invertedCommaRemover(String str){
		if ((str.startsWith("\""))&& (str.endsWith("\""))){
			System.out.println("hey");
			return str.replace("\"","");
		}
		else{
			return str;
		}
	}

	public static void find(String vfsname, String filename){
		try {
			VdAndCurrentNode vdcn = getVdACNFromVfsname(vfsname);
			VirtualDisk vd = vdcn.getVd();
			List<Node> list = null;
			try {
				list = vd.search(filename);
				for (Node n : list){
					try {
						System.out.println(vd.getPath(n));
					} catch (NotInTreeException e) {
						System.out.println("Cannot get the path of " +n.getName());
						
					}
				}
			} catch (NotInTreeException e) {
				System.out.println(filename + " cannot be found in "+vfsname);
				
			}

		} catch (VirtualDiskDoesntExistException e) {
			System.out.println(vfsname +" doesn't exist");
			
		} 
	}

	public static void help (String str){
		switch (str){
		case "ls":
			System.out.println("To list the information concerning files and directories contained in absolute position corresponding to pathname (if no pathname argument is given then the current position, i.e. the current directory, is listed) in a VFS named vfsname. The command ls should behave differently, depending on the optional argument arg:");
			System.out.println("If args=\"\" (i.e. no args is given): in this case ls simply displays the list of names of files and directories contained in the current directory of the VFS.");
			System.out.println("In this case ls vfs1 -l displays the list of names and dimension of files and directories contained in the current directory of the VFS.");
			System.out.println("Syntax: ls <vfsname> <args> <pathname> ");
			break;
		case "crvfs":
			System.out.println("To create a new VFS with name vfsname and maximal dimension dim bytes");
			System.out.println("Syntax: crvfs <vfsname> <dim>");
			break;
		case "cd":
			System.out.println("Changes the current position in the VFS vfsname to the directory whose absolute name is <pathname>");
			System.out.println("Notice that if pathname=. the current position is unchanged whether if pathname=..the current position becomes the parent directory of the current one. For example \"cd vfs1 /Pictures/London\" will set the current position of VFS called vfs1 to path \"/Pictures/London\", whereas \"cd vfs1 ..\" will set the current position to the parent directory of the current one hence to \"/Pictures\" if \"cd vfs1 ..\" is executed soon after \"cd vfs1 /Pictures/London\"");
			System.out.println("Syntax: cd <vfsname> <pathname>");
			break;
		case "mv":
			System.out.println("To change the name of a file/directory with absolute name oldpath in the new absolute name newpath of the VFS named vfsname.");
			System.out.println("Syntax: mv <vfsname> <oldpath> <newpath>");
			break;
		case "cp":
			System.out.println("To copy, within the VFS named vfsname, the content of a file/directory whose absolute name is source path into a target le/directory whose absolute name is targetpath.");
			System.out.println("cp <vfsname> <sourcepath> <targetpath>");
			break;
		case "rm":
			System.out.println("To remove a file/directory with absolute name pathname from the VFS named vfsname.");
			System.out.println("Syntax: rm <vfsname> <pathname>");
			break;
		case "rmvfs":
			System.out.println("To delete a VFS named vfsname");
			System.out.println("Syntax: rmvfs <vfsname>");
			break;
		case "impvfs":
			System.out.println("To import the content of the directory/file corresponding to absolute name hostpath on the host file system into the position vfspath on an existing VFS named vfsname.");
			System.out.println("Syntax: impvfs <hostpath> <vfsname> <vfspath>");
			break;
		case "expvfs":
			System.out.println("To export an existing VFS named vfsname into the absolute path named hostpath of the host file system");
			System.out.println("Syntax: expvfs <vfsname> <hostpath>");
			break;
		case "free":
			System.out.println("To display the quantity of free/occupied space for VFS named vfsname");
			System.out.println("Syntax: free <vfsname>");
			break;
		case "find":
			System.out.println("To search if a file named filename is stored in the VFS named vfsname, shall return the absolute path of the sought le if it is present in the VFS, null otherwise");
			System.out.println("Syntax: find <vfsname> <filename>");
			break;
		case "help":
			System.out.println("To display an \"help message\" (similar to that of unix shell terminal) which gives information about how to use the command named command-name. If help is invoked without <command-name> argument then it should display a generic help message about how to use the CLUI (e.g. general syntax of a CLUI command, list of all CLUI commands name).");
			System.out.println("help <command-name>");
//		case "gen":
//			System.out.println("Generates a tree of the current file system");
//			System.out.println("Syntax gen <vfsname>");
//			break;
		default: 
			System.out.println("The "+ str + "command doesn't exist");
		}
	}

	public static void help(){
		System.out.println("Commands take the following general syntax: command <arg1> <arg2> <arg3>");
		System.out.println("The different commands are: ls, cd, mv, cp, rm, crvfs, rmvfs, impvfs, expvfs, free, find, help");
		System.out.println("Type help <command> to find out more about a command");
	}

	//cuts up the string argument received from the user input into an array of string arguments
	//if path name includes spaces, then the path name must be put inside inverted commas
	public static List<String> preTreatment(String str){
		List<String> list = new ArrayList<String>();
		int strLen = str.length();
		//compteur to iterate through each character of the string
		int compteur = 0;
		//for remembering where the last space was encountered
		int lastSpace = 0;
		//for remembering where the last inverted comma was encountered
		int lastComma = 0;
		//is true if the compteur is between inverted commas
		boolean inInvertedCommas=false;
		//is true if the inverted commas have just been closed
		boolean precededByInvertedCommas=false;
		while (compteur < strLen){
			//tests for a space followed by an inverted comma, i.e. the beginning of an argument in inverted commas
			if (Character.toString((str.charAt(compteur))).equals(" ")&&Character.toString((str.charAt(compteur+1))).equals("\"")){
				//				System.out.println("space comma");
				if (!inInvertedCommas&&!precededByInvertedCommas){
					//					System.out.println(str.substring(lastSpace, compteur));
					list.add(str.substring(lastSpace, compteur));
					lastSpace = compteur+1;
				}
				lastComma=compteur+2;
				compteur=compteur+2;
				inInvertedCommas=true;
				precededByInvertedCommas=false;
			}
			//tests for the end of a string in inverted commas
			else if (Character.toString((str.charAt(compteur))).equals("\"")){

				//				System.out.println("comma");
				list.add(str.substring(lastComma, compteur));
				//				System.out.println(str.substring(lastComma, compteur));
				lastComma = compteur+1;
				lastSpace = compteur+2;
				compteur++;
				inInvertedCommas=false;
				precededByInvertedCommas=true;
			}
			//tests for a space, i.e. athe start of a new argument, unless in inverted commas
			else if(Character.toString((str.charAt(compteur))).equals(" ")){
				if (!inInvertedCommas&&!precededByInvertedCommas){
					//					System.out.println(str.substring(lastSpace, compteur));
					list.add(str.substring(lastSpace, compteur));
					lastSpace = compteur+1;
					compteur++;
					precededByInvertedCommas=false;
				}
				else{
					compteur ++;
					precededByInvertedCommas=false;
				}
			}
			//if reaches end of string
			else if(compteur == strLen-1){
				//				System.out.println(str.substring(lastSpace, compteur+1));
				list.add(str.substring(lastSpace, compteur+1));

				compteur++;

			}
			//if none of these characters are encountered, we continue to iterate through the string
			else{
				compteur ++;
			}
		}
		return list;
	}

	//interprets the user input and calls the correct function (aftr pre treatment)
	public static void understand(String str) throws NumberFormatException, NotInTreeException, VirtualDiskDoesntExistException, NoAvailableSpaceException, NotADirectoryException, ParentException{
		//pre-treat string
		List<String> strList = preTreatment(str);
		//create an array from list returned
		String[] strs =  new String[strList.size()];
		for (int i = 0; i<strList.size();i++){
			strs[i]=strList.get(i);
		}
		//check for first argument
		switch (strs[0]){
		case "ls":
			if (strs.length==2){
				ls(strs[1],"","");
			}
			else if(strs.length==3){
				if(strs[2].equals("-1")){
					ls(strs[1],"-1","");
				}
				else{ls(strs[1],"",strs[2]);
				}
			}
			else if (strs.length==4){
				ls(strs[1],strs[2],strs[3]);
			}
			else {
				System.out.println("Invalid Command, refer to help to see syntax");
			}
			break;
		case "crvfs":
			if (strs.length==3){
				crvfs(strs[1],Integer.parseInt(strs[2]));
			}
			else {
				System.out.println("Invalid Command, refer to help to see syntax");
			}
			break;
		case "cd":
			if (strs.length==3){
				cd(strs[1],strs[2]);
			}
			else {
				System.out.println("Invalid Command, refer to help to see syntax");
			}
			break;
		case "mv":
			if (strs.length==4){
				mv(strs[1],strs[2],strs[3]);
			}
			else {
				System.out.println("Invalid Command, refer to help to see syntax");
			}
			break;
		case "cp":
			if (strs.length==4){
				cp(strs[1],strs[2],strs[3]);
			}
			else {
				System.out.println("Invalid Command, refer to help to see syntax");
			}
			break;
		case "rmvfs":
			if (strs.length==2){
				rmvfs(strs[1]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "rm":
			if (strs.length==3){
				rm(strs[1],strs[2]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "impvfs":
			if (strs.length==4){
				impvfs(strs[1],strs[2],strs[3]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "expvfs":
			if (strs.length==3){
				expvfs(strs[1],strs[2]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "free":
			if (strs.length==2){
				free(strs[1]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "find":
			if (strs.length==3){
				find(strs[1],strs[2]);
			}
			else {
				System.out.println("Invalid command, refer to help to see syntax");
			}
			break;
		case "help":
			if (strs.length==1){ help();}
			else if (strs.length==2&&(strs[1] instanceof String)){help(strs[1]);}
			else{System.out.println("Invalid command, refer to help to see syntax");}
			break;
//			case "gen":
//				if (strs.length==2){
//					new Frame();
//				}
//				else {
//					System.out.println("Invalid command, refer to help to see syntax");
//				}
//				break;
		default: System.out.println("Not a valid command, please type help for more information");
		}
	}

	public static void main(String[] args) throws NotInTreeException, VirtualDiskDoesntExistException, NoAvailableSpaceException, NotADirectoryException, ParentException{
		//create vfs1
		
		crvfs("vfs1",1000l);
		//create vfs2
		//		crvfs("vfs2",1000);
		//		System.out.println(VdcnManagement.getVdList().get(0).getVd());
		//		System.out.println(VdcnManagement.getVdList().get(0).getCurrentNode());
		//acces vfs1 and vfs2
		//		VdAndCurrentNode vfs1 = getVdACNFromVfsname("vfs1");
		//		VdAndCurrentNode vfs2 = getVdACNFromVfsname("vfs2");
		//		System.out.println(vfs1.toString());
		//import level 1
		impvfs("eval/Host/level 1","vfs1","Home");
		//		GenerateTree gt = new GenerateTree(getVdACNFromVfsname("vfs1").getVd());
		//		System.out.println(vfs1.getVd().getTree().getNodeList());
		//		//navigate to level 2
		//		cd("vfs1","Home/level 1/level 2");
		//		System.out.println(vfs1.getCurrentNode());
		//		ls("vfs1","-1","");
		//rename level 1 to level 1 renamed
		//		mv("vfs1","Home/level 1","Home/level 1 renamed");
		//		gt.UpdateTree();
		//		ls("vfs1","","Home");
		//		//put test text.txt in level 2 bis
		//		cp("vfs1","Home/level 1 renamed/level 2/test text.txt","Home/level 1 renamed/level 2 bis/test text.txt");
		//		//display trees
		//		GenerateTree gt = new GenerateTree(vfs1.getVd());
		//		new GenerateTree(vfs2.getVd());
//		expvfs("vfs1","eval/Host/moving test");
		System.out.println("What would you like to do ? Type help to see the commands");


		Scanner scan = new Scanner(System.in);
		while (true){
			String str = scan.nextLine();

			//		List<String> tab = (preTreatment(str));
			//		for (String s : tab){System.out.println("element: " +s);}
			understand(str);
		}

	}
}

