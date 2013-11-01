package patternparser.wm;

import java.util.Vector;


public class UnionPatternSet { 

	private Vector<UnionPattern> unionPatternSet;
	
	// union string set
	public UnionPatternSet() {
		this.unionPatternSet = new Vector<UnionPattern>();
	}

	public void addNewUnionPattrn(UnionPattern up) {
		this.unionPatternSet.add(up);
	}


	public Vector<UnionPattern> getSet() {
		return unionPatternSet;
	}

	public void clear() {
		unionPatternSet.clear();
	}
}
