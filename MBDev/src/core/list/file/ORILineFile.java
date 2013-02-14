package core.list.file;

import java.util.ArrayList;
import java.util.List;

import core.fileLine.ORILine;
import core.list.ByteArrayAsChromosomeList;
import core.list.IntArrayAsIntegerList;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ORILineFile {

	private final ByteArrayAsChromosomeList chromosome;
	private final IntArrayAsIntegerList startPosition;
	private final IntArrayAsIntegerList stopPosition;
	private final List<String> names;


	/**
	 * Constructor of {@link ORILineFile}
	 */
	public ORILineFile () {
		chromosome = new ByteArrayAsChromosomeList();
		startPosition = new IntArrayAsIntegerList();
		stopPosition = new IntArrayAsIntegerList();
		names = new ArrayList<>();
	}


	public void add (ORILine line) {
		chromosome.add(line.getChromosome());
		startPosition.add(line.getStartPosition());
		stopPosition.add(line.getStopPosition());
		names.add(line.getElements()[3]);
	}


	public int getSize () {
		return chromosome.size();
	}


	public ORILine getLine (int index) {
		String[] elements = new String[4];
		elements[0] = chromosome.get(index);
		elements[1] = "" + startPosition.get(index);
		elements[2] = "" + stopPosition.get(index);
		elements[3] = names.get(index);
		ORILine line = new ORILine();
		line.initializeFromArray(elements);
		return line;
	}
}
