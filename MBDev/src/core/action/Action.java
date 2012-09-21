/*******************************************************************************
 * Montpellier Biology Developpment
 * This project aims to create a tool to process and analyse data of a project in the field of Biology.
 * Copyright (C) 2012, Nicolas Fourel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Authors:
 * 	Nicolas Fourel <nicolas.fourel@live.fr>
 * 	Romain Desprat <rdesprat@gmail.com>
 ******************************************************************************/
package core.action;

import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public abstract class Action extends AbstractAction {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 1258451043908220848L;
	protected String actionName = null;


	@Override
	public final void actionPerformed(ActionEvent arg0) {
		long time = System.currentTimeMillis();
		System.out.println("Operation start: " + actionName);
		doFirst();
		compute();
		doAtTheEnd();
		System.out.println("Operation done: " + actionName);
		time = System.currentTimeMillis() - time;
		System.out.println(getTimeString(time));
	}


	/**
	 * @param ms milli seconds
	 * @return the time in string
	 */
	private String getTimeString (long ms) {
		String time = String.format("%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(ms),
				TimeUnit.MILLISECONDS.toSeconds(ms) -
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))
				);
		String s = "Operation time: " + time;
		return s;
	}


	/**
	 * Process before the action starts
	 */
	protected abstract void doFirst ();


	/**
	 * Compute the action
	 * @return an object
	 */
	protected abstract Object compute ();


	/**
	 * Process at the end of the action
	 */
	protected abstract void doAtTheEnd ();

}
