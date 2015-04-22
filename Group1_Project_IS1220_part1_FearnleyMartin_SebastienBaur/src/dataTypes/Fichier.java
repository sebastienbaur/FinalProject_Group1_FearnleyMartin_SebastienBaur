package dataTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import treeImplementation.Node;

public class Fichier extends treeImplementation.Node implements Serializable, Visitable{

	protected String type;
	protected long size;
	protected byte[] contenu; 
	

	//---------------------------------------------------------------------------------
	// CONSTRUCTORS
	//---------------------------------------------------------------------------------

	public Fichier(String name, String type, long size) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
		this.id=Node.uniqueId;
		Node.uniqueId+=1;
	}

	public Fichier(String name) {
		super();
		this.name = name;
		this.id=Node.uniqueId;
		Node.uniqueId+=1;
	}
	
	public Fichier(String name, String type, long size, byte[] contenu) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
		this.contenu=contenu;
		this.id=Node.uniqueId;
		Node.uniqueId+=1;
	}

	//---------------------------------------------------------------------------------
	// GETTERS AND SETTERS
	//---------------------------------------------------------------------------------
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public byte[] getContenu() {
		return contenu;
	}

	public void setContenu(byte[] contenu) {
		this.contenu = contenu;
	}

	//---------------------------------------------------------------------------------
	// TOSTRING
	//---------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Fichier [" +name  +", id : " + ((Node)this).id +"]";
	}


	//---------------------------------------------------------------------------------
	// IMPORT ET EXPORT DE FICHIER
	//---------------------------------------------------------------------------------


	//import a file specified by it filepath input and put it into the contenu attribut of a Fichier object
	public void importFile(String input){
		final String INPUT_FILE = input;
		int bytesread = 0;
		byte[] bytes = null;
		try (InputStream inputStream = new FileInputStream(INPUT_FILE)) {
			bytes = new byte[inputStream.available()];
			bytesread = inputStream.read(bytes);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		this.setContenu(bytes);
		this.setSize((long)bytesread);
	}


	// export a file and write it to disk at the specified path output
	public void exportFile(String output){
		final String OUTPUT_FILE = output;
		//the file to be exported is converted into an array of bytes
		byte[] bytes = this.getContenu();

		//the output file
		File file = new File(OUTPUT_FILE);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			// Writes bytes from the specified byte array to this file output stream 
			fos.write(bytes);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		}
		catch (IOException ioe) {
			System.out.println("Exception while writing file " + ioe);
		}
		finally {
			// close the streams using close method
			try {
				if (fos != null) {
					fos.close();
				}
			}
			catch (IOException ioe) {
				System.out.println("Error while closing stream: " + ioe);
			}
		}
	}

	@Override
	public long accept(Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(contenu);
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fichier other = (Fichier) obj;
		if (!Arrays.equals(contenu, other.contenu))
			return false;
		if (size != other.size)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public Fichier duplicateFichier(){
		return new Fichier(this.name,this.type,this.size,this.contenu);
	}


}
