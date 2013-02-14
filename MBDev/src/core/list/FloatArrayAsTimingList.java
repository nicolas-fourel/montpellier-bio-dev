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
import java.util.Arrays;
import java.util.List;



/**
 * An array of floats encapsulated in order to implement the {@link List} interface with Double parameter
 * It means that the methods get and set work with Double objects
 * @author Julien Lajugie
 * @version 0.1
 */
public final class FloatArrayAsTimingList extends ArrayAsDoubleList<float[]> implements Serializable, List<Double> {

	private static final long serialVersionUID = -5280328695672981245L;	// generated ID
	private static final int  SAVED_FORMAT_VERSION_NUMBER = 0;			// saved format version


	/**
	 * Method used for serialization
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(SAVED_FORMAT_VERSION_NUMBER);
	}


	/**
	 * Method used for unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.readInt();
	}


	/**
	 * Creates an instance of {@link FloatArrayAsTimingList}
	 */
	public FloatArrayAsTimingList() {
		super();
		this.data = new float[0];
	}


	/**
	 * Creates an instance of {@link FloatArrayAsTimingList}
	 * @param size size of the array
	 */
	public FloatArrayAsTimingList(int size) {
		super(size);
		this.data = new float[size];
	}


	@Override
	public void sort() {
		Arrays.sort(data);
	};


	@Override
	public boolean add(Double e) {
		if (e == null) {
			e = -10.0;
		}
		// if the array is to small we resize it before adding the data
		if (size >= data.length) {
			// we multiply the current size by the resize multiplication factor
			int newLength = data.length * RESIZE_FACTOR;
			// we make sure we don't add less than RESIZE_MIN elements
			newLength = Math.max(newLength, data.length + RESIZE_MIN);
			// we make sure we don't add more than RESIZE_MAX elements
			newLength = Math.min(newLength, data.length + RESIZE_MAX);
			float[] newData = new float[newLength];
			for (int i = 0; i < data.length; i++) {
				newData[i] = data[i];
			}
			data = newData;
		}
		data[size] = e.floatValue();
		size++;
		return true;
	}


	public boolean add(String e) {
		Double d = null;
		try {
			d = Double.parseDouble(e);
		} catch (Exception e2) {
		}
		return add(d);
	}


	@Override
	public Double get(int index) {
		if (data[index] == -10.0) {
			return null;
		}
		return (double)data[index];
	}


	/**
	 * @return null in order to accelerate the operation
	 */
	@Override
	public Double set(int index, Double element) {
		data[index] = element.floatValue();
		return null;
	}
}
