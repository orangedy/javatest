package patternparser.wm;

import java.util.Vector;


public class UnionPattern {

	private int level;
	
	private Vector<AtomicPattern> apSet;
	
	public UnionPattern() {
		this.apSet = new Vector<AtomicPattern>();
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

	public void addNewAtomicPattrn(AtomicPattern ap) {
		this.apSet.add(ap);
	}

	public Vector<AtomicPattern> getSet() {
		return apSet;
	}

	public boolean isIncludeAllAp(Vector<AtomicPattern> inAps) {
		if (apSet.size() > inAps.size())
			return false;
		for (int i = 0; i < apSet.size(); i++) {
			AtomicPattern ap = apSet.get(i);
			if (isInAps(ap, inAps) == false)
				return false;
		}
		return true;
	}

	private boolean isInAps(AtomicPattern ap, Vector<AtomicPattern> inAps) {
		for (int i = 0; i < inAps.size(); i++) {
			AtomicPattern destAp = inAps.get(i);
			if (ap.getPattern().str.equalsIgnoreCase(destAp.getPattern().str) == true)
				return true;
		}
		return false;
	}


}
