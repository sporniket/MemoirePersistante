/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.awt.Color;
import java.awt.Dimension;

import com.sporniket.libre.memoirepersistante.ui.UiTheme;

/**
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
public class BasicUiTheme implements UiTheme
{
	private static final UiTheme INSTANCE = new BasicUiTheme();

	private static final Dimension THUMBNAIL_MAX_DIMENSION = new Dimension(300, 300);

	public static UiTheme getInstance()
	{
		return INSTANCE;
	}

	private Color myBackground = new Color(192, 192, 192);

	private Color myBackgroundEditable = new Color(255, 255, 255);

	private Color mySelectedBackground = new Color(192, 192, 255);

	private Color mySelectedBackgroundEditable = new Color(255, 255, 231);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.memoirepersistante.types.UiTheme#getBackground()
	 */
	public Color getBackground()
	{
		return myBackground;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.memoirepersistante.types.UiTheme#getBackgroundEditable()
	 */
	public Color getBackgroundEditable()
	{
		return myBackgroundEditable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.memoirepersistante.types.UiTheme#getSelectedBackground()
	 */
	public Color getSelectedBackground()
	{
		return mySelectedBackground;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.memoirepersistante.types.UiTheme#getSelectedBackgroundEditable()
	 */
	public Color getSelectedBackgroundEditable()
	{
		return mySelectedBackgroundEditable;
	}

	public Dimension getThumbnailBoundingBoxForMainPanel()
	{
		return THUMBNAIL_MAX_DIMENSION;
	}

}
