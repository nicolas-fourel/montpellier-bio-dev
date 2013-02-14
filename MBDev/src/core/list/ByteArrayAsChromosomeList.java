/*******************************************************************************
 *     GenPlay, Einstein Genome Analyzer
 *     Copyright (C) 2009, 2011 Albert Einstein College of Medicine
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *     Authors:	Julien Lajugie <julien.lajugie@einstein.yu.edu>
 *     			Nicolas Fourel <nicolas.fourel@einstein.yu.edu>
 *     Website: <http://genplay.einstein.yu.edu>
 *******************************************************************************/
package core.list;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

import core.util.Utils;


/**
 * This class implements the List of Boolean interface but internally
 * it contains an array of byte that is dynamically resized in order to
 * be more memory efficient.
 * @author Julien Lajugie
 * @author Nicolas Fourel
 */
public class ByteArrayAsChromosomeList extends AbstractList<String> implements Serializable, List<String> {

	private final static int CHRX = -1;
	private final static int CHRY = -2;
	private final static int CHRM = -3;

	private static final long serialVersionUID = -8787392051503707843L; // generated ID
	private static final int  SAVED_FORMAT_VERSION_NUMBER = 0;			// saved format version
	private static final int 	RESIZE_MIN = 1000;		// minimum length added every time the array is resized
	private static final int 	RESIZE_MAX = 10000000;	// maximum length added every time the array is resized
	private static final int 	RESIZE_FACTOR = 2;		// multiplication factor of the length of the array every time it's resized
	private byte[] 				value;					// int value array
	private int 				size;					// size of the list


	/**
	 * Method used for serialization
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(SAVED_FORMAT_VERSION_NUMBER);
		out.writeObject(value);
		out.writeInt(size);
	}


	/**
	 * Method used for unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.readInt();
		value = (byte[]) in.readObject();
		size = in.readInt();
	}


	/**
	 * Creates an instance of {@link ByteArrayAsChromosomeList}
	 */
	public ByteArrayAsChromosomeList() {
		this.value = new byte[0];
		this.size = 0;
	}


	/**
	 * Creates an instance of {@link ByteArrayAsChromosomeList}
	 * @param size size of the array
	 */
	public ByteArrayAsChromosomeList(int size) {
		this.value = new byte[size];
		this.size = size;
	}


	@Override
	public boolean add(String e) {
		Integer chr;
		if (e.toLowerCase().equals("chrx")) {
			chr = CHRX;
		} else if (e.toLowerCase().equals("chry")) {
			chr = CHRY;
		} else if (e.toLowerCase().equals("chrm")) {
			chr = CHRM;
		} else {
			int intIndex = Utils.getFirstIntegerOffset(e, 0);
			chr = Utils.getFullIntegerPart(e, intIndex);
		}

		// if the array is to small we resize it before adding the data
		if (size >= value.length) {
			// we multiply the current size by the resize multiplication factor
			int newLength = value.length * RESIZE_FACTOR;
			// we make sure we don't add less than RESIZE_MIN elements
			newLength = Math.max(newLength, value.length + RESIZE_MIN);
			// we make sure we don't add more than RESIZE_MAX elements
			newLength = Math.min(newLength, value.length + RESIZE_MAX);
			byte[] newValue = new byte[newLength];
			for (int i = 0; i < value.length; i++) {
				newValue[i] = value[i];
			}
			value = newValue;
		}

		value[size] = chr.byteValue();
		size++;
		return true;
	}


	@Override
	public String get(int index) {
		if (value[index] == CHRX) {
			return "chrX";
		}
		if (value[index] == CHRY) {
			return "chrY";
		}
		if (value[index] == CHRM) {
			return "chrM";
		}
		return "chr" + value[index];
	}


	/**
	 * @return null in order to accelerate the operation
	 */
	@Override
	public String set(int index, String e) {
		Integer chr;
		if (e.toLowerCase().equals("chrx")) {
			chr = CHRX;
		} else if (e.toLowerCase().equals("chry")) {
			chr = CHRY;
		} else if (e.toLowerCase().equals("chrm")) {
			chr = CHRM;
		} else {
			int intIndex = Utils.getFirstIntegerOffset(e, 0);
			chr = Utils.getFullIntegerPart(e, intIndex);
		}
		value[index] = chr.byteValue();
		return null;
	}


	@Override
	public int size() {
		return size;
	}


	/**
	 * Recreates the arrays with the right size in order to optimize the memory usage.
	 */
	public void compact() {
		byte[] valueTmp = new byte[size];
		for (int i = 0; i < size; i++) {
			valueTmp[i] = value[i];
		}
		value = valueTmp;
	}


	/**
	 * Shows the content of this object
	 */
	public void show () {
		String info = "size = " + size + " -> ";
		for (int i = 0; i < value.length; i++) {
			info += (value[i] + 128) + "; ";
		}
		System.out.println(info);
	}
}
