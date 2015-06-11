/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.Font;

/**
 * Class providing the raw font to use for the photo book.
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
public class FontProvider
{
	/**
	 * Macro to create a font provider using the specified font.
	 * @param fontFamily font family to use.
	 * @return the font provider.
	 * @since 15.07.00-SNAPSHOT
	 */
	public static FontProvider create(String fontFamily)
	{
		FontProvider _result = new FontProvider();
		_result.setFontFamily(fontFamily);
		return _result ;
	}
	private static final int DEFAULT_FONT_SIZE__SMALL = 8;

	private static final int DEFAULT_FONT_SIZE__REGULAR = 12;

	private boolean myChanged;

	/**
	 * Font family to use
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private String myFontFamily;
	
	private Font myRegularFont;

	/**
	 * Size in points of the regular text.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private int myRegularSize = DEFAULT_FONT_SIZE__REGULAR ;

	private Font mySmallFont;

	/**
	 * Size in points of the small text.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private int mySmallSize = DEFAULT_FONT_SIZE__SMALL;

	/**
	 * Get fontFamily.
	 * 
	 * @return the fontFamily
	 * @since 15.07.00-SNAPSHOT
	 */
	public String getFontFamily()
	{
		return myFontFamily;
	}

	/**
	 * Get regularFont.
	 * 
	 * @return the regularFont
	 * @since 15.07.00-SNAPSHOT
	 */
	public Font getRegularFont()
	{
		return myRegularFont;
	}

	/**
	 * Get regularSize.
	 * 
	 * @return the regularSize
	 * @since 15.07.00-SNAPSHOT
	 */
	public int getRegularSize()
	{
		return myRegularSize;
	}

	/**
	 * Get smallFont.
	 * 
	 * @return the smallFont
	 * @since 15.07.00-SNAPSHOT
	 */
	public Font getSmallFont()
	{
		if(null == mySmallFont || isChanged())
		{
			update();
			setChanged(false);
		}
		return mySmallFont;
	}

	/**
	 * Get smallSize.
	 * 
	 * @return the smallSize
	 * @since 15.07.00-SNAPSHOT
	 */
	public int getSmallSize()
	{
		return mySmallSize;
	}

	/**
	 * Get changed.
	 * @return the changed
	 * @since 15.07.00-SNAPSHOT
	 */
	private boolean isChanged()
	{
		return myChanged;
	}

	/**
	 * Change changed.
	 * @param changed the new value of changed.
	 * @since 15.07.00-SNAPSHOT
	 */
	private void setChanged(boolean changed)
	{
		myChanged = changed;
	}

	/**
	 * Change fontFamily.
	 * 
	 * @param fontFamily
	 *            the new value of fontFamily.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setFontFamily(String fontFamily)
	{
		myFontFamily = fontFamily;
		setChanged(true);
	}

	/**
	 * Change regularFont.
	 * 
	 * @param regularFont
	 *            the new value of regularFont.
	 * @since 15.07.00-SNAPSHOT
	 */
	private void setRegularFont(Font regularFont)
	{
		myRegularFont = regularFont;
	}

	/**
	 * Change regularSize.
	 * 
	 * @param regularSize
	 *            the new value of regularSize.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setRegularSize(int regularSize)
	{
		myRegularSize = regularSize;
		setChanged(true);
	}

	/**
	 * Change smallFont.
	 * 
	 * @param smallFont
	 *            the new value of smallFont.
	 * @since 15.07.00-SNAPSHOT
	 */
	private void setSmallFont(Font smallFont)
	{
		mySmallFont = smallFont;
	}

	/**
	 * Change smallSize.
	 * 
	 * @param smallSize
	 *            the new value of smallSize.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setSmallSize(int smallSize)
	{
		mySmallSize = smallSize;
		setChanged(true);
	}

	private void update()
	{
		setSmallFont(new Font(getFontFamily(), Font.PLAIN, getSmallSize()));
		setRegularFont(new Font(getFontFamily(), Font.PLAIN, getRegularSize()));
	}
}
