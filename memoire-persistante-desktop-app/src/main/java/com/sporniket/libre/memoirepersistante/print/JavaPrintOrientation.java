/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.print.PageFormat;

/**
 * Enum listing the java print supported orientation.
 * 
 * <p>
 * &copy; Copyright 2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>Memoire Persistante &#8211; app</i>.
 * 
 * <p>
 * <i>Memoire Persistante &#8211; app</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with <i>The Sporniket Image Library &#8211; core</i>.
 * If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
 * 
 * <hr>
 * 
 * @author David SPORN
 * 
 * @version 15.07.00-SNAPSHOT
 * @since 15.07.00-SNAPSHOT
 */
public enum JavaPrintOrientation
{
	LANDSCAPE(PageFormat.LANDSCAPE),
	PORTRAIT(PageFormat.PORTRAIT),
	REVERSE_LANDSCAPE(PageFormat.REVERSE_LANDSCAPE);
	/**
	 * The java print constant.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	final int myJavaPrintValue;

	/**
	 * @param javaPrintValue
	 * @since 15.07.00-SNAPSHOT
	 */
	private JavaPrintOrientation(int javaPrintValue)
	{
		myJavaPrintValue = javaPrintValue;
	}

	/**
	 * Get javaPrintValue.
	 * 
	 * @return the javaPrintValue
	 * @since 15.07.00-SNAPSHOT
	 */
	public int getJavaPrintValue()
	{
		return myJavaPrintValue;
	}

}
