/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Interface for providing color schemes, font, etc...
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
public interface UiTheme {
	/**
	 * BackgroundColor for most of the component.
	 * @since 15.07.00-SNAPSHOT
	 * @return the background color.
	 */
	Color getBackground() ;
	/**
	 * BackgroundColor for editable fields.
	 * @return the background color of an editable component.
	 * @since 15.07.00-SNAPSHOT
	 */
	Color getBackgroundEditable() ;
	
	/**
	 * BackgroundColor for most of the component when selected.
	 * @return the background color for a selected component.
	 * @since 15.07.00-SNAPSHOT
	 */
	Color getSelectedBackground() ;
	/**
	 * BackgroundColor for editable fields when selected.
	 * @return the background color for a selected editable component.
	 * @since 15.07.00-SNAPSHOT
	 */
	Color getSelectedBackgroundEditable() ;
	
	/**
	 * The thumbnail will have the largest size that fits into the returned dimension.
	 * @return get the dimensions of the bounding box.
	 * @since 15.07.00-SNAPSHOT
	 */
	Dimension getThumbnailBoundingBoxForMainPanel();
}
