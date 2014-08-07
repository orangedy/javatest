package patternparser.wm;

import java.util.Vector;


public class SameAtomicPatternSet {
	
	public SameAtomicPatternSet() {
		saps = new Vector<AtomicPattern>();
	}

	private Vector<AtomicPattern> saps;

	public Vector<AtomicPattern> getSaps() {
		return saps;
	}

	public void setSaps(Vector<AtomicPattern> saps) {
		this.saps = saps;
	}
	
	
}