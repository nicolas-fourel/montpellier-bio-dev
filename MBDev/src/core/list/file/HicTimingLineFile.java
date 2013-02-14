package core.list.file;

import core.fileLine.HicTimingLine;
import core.list.ByteArrayAsChromosomeList;
import core.list.FloatArrayAsTimingList;
import core.list.IntArrayAsIntegerList;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class HicTimingLineFile {

	private final ByteArrayAsChromosomeList firstChromosome;
	private final IntArrayAsIntegerList firstPosition;
	private final FloatArrayAsTimingList firstTiming;
	private final ByteArrayAsChromosomeList secondChromosome;
	private final IntArrayAsIntegerList secondPosition;
	private final FloatArrayAsTimingList secondTiming;


	/**
	 * Constructor of {@link HicTimingLineFile}
	 */
	public HicTimingLineFile () {
		firstChromosome = new ByteArrayAsChromosomeList();
		firstPosition = new IntArrayAsIntegerList();
		firstTiming = new FloatArrayAsTimingList();
		secondChromosome = new ByteArrayAsChromosomeList();
		secondPosition = new IntArrayAsIntegerList();
		secondTiming = new FloatArrayAsTimingList();
	}


	public void add (HicTimingLine line) {
		firstChromosome.add(line.getFirstChromosome());
		firstPosition.add(line.getFirstPosition());
		firstTiming.add(line.getFirstTiming());
		secondChromosome.add(line.getSecondChromosome());
		secondPosition.add(line.getSecondPosition());
		secondTiming.add(line.getSecondTiming());
	}


	public int getSize () {
		return firstChromosome.size();
	}


	public HicTimingLine getLine (int index) {
		String[] elementss = new String[6];
		elementss[0] = firstChromosome.get(index);
		elementss[1] = "" + firstPosition.get(index);
		elementss[2] = "" + firstTiming.get(index);
		elementss[3] = secondChromosome.get(index);
		elementss[4] = "" + secondPosition.get(index);
		elementss[5] = "" + secondTiming.get(index);
		HicTimingLine line = new HicTimingLine();
		line.initializeFromArray(elementss);
		return line;
	}
}
