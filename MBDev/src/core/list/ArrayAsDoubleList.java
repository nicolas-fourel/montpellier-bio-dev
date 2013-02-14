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



/**
 * Abstract class. Represents a {@link Serializable} and {@link CompressibleList} of {@link Double}.
 * The internal implementation of the data storage is left to the subclasses.
 * @param <T> type of the internal data representing the List of Double
 * @author Julien Lajugie
 * @version 0.1
 */
public abstract class ArrayAsDoubleList<T> extends AbstractList<Double> implements Serializable, List<Double> {

	private static final long serialVersionUID = -4745728013829849L; // generated ID
	private static final int  SAVED_FORMAT_VERSION_NUMBER = 0;		// saved format version
	protected static final int 	RESIZE_MIN = 1000;		// minimum length added every time the array is resized
	protected static final int 	RESIZE_MAX = 10000000;	// maximum length added every time the array is resized
	protected static final int 	RESIZE_FACTOR = 2;		// multiplication factor of the length of the array every time it's resized
	protected T					data;					// byte data array (8 booleans / byte)
	protected int 				size = 0;				// size of the list


	/**
	 * Method used for serialization
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(SAVED_FORMAT_VERSION_NUMBER);
		out.writeObject(data);
		out.writeInt(size);
	}


	/**
	 * Method used for unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.readInt();
		data = (T) in.readObject();
		size = in.readInt();
	}


	/**
	 * Sorts the list
	 */
	public abstract void sort();


	/**
	 * Default constructor. Do nothing.
	 */
	public ArrayAsDoubleList() {
		super();
	}


	/**
	 * Constructor. Sets the size of the list.
	 * @param size size of the array
	 */
	public ArrayAsDoubleList(int size) {
		super();
		this.size = size;
	}


	@Override
	public int size() {
		return size;
	}
}
