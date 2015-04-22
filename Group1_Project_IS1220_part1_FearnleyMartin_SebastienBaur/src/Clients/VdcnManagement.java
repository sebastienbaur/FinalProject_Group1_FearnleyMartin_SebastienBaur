package Clients;

import java.util.ArrayList;
import java.util.List;

public class VdcnManagement {
	public static List<VdAndCurrentNode> vdList = new ArrayList<VdAndCurrentNode>();

	public static List<VdAndCurrentNode> getVdList() {
		return vdList;
	}

	public static void setVdList(List<VdAndCurrentNode> vdList) {
		VdcnManagement.vdList = vdList;
	}
}
