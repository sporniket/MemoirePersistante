/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Specification for styling a photobook.
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
public class PhotoBookFormat
{
	private static final FontProvider DEFAULT_FONT_PROVIDER = FontProvider.create("Serif");

	private FontProvider myFontProvider = DEFAULT_FONT_PROVIDER;

	public Font getAnnotationFont()
	{
		return getFontProvider().getRegularFont();
	}

	public int getAnnotationHeight(Graphics g, int lineCount)
	{
		FontMetrics _metrics = g.getFontMetrics(getAnnotationFont());
		return _metrics.getHeight() * (lineCount + 1);
	}

	public int getAnnotationPadding()
	{
		return getAnnotationFont().getSize() / 2;
	}

	/**
	 * Get fontProvider.
	 * 
	 * @return the fontProvider
	 * @since 15.07.00-SNAPSHOT
	 */
	public FontProvider getFontProvider()
	{
		return myFontProvider;
	}

	public Font getFooterFont()
	{
		return getFontProvider().getSmallFont();
	}

	public int getFooterHeight(Graphics g)
	{
		FontMetrics _metrics = g.getFontMetrics(getFooterFont());
		return _metrics.getHeight() * 2;
	}

	public int getFooterPadding()
	{
		return getFooterFont().getSize() / 2;
	}

	public Font getHeaderFont()
	{
		return getFontProvider().getSmallFont();
	}

	public int getHeaderHeight(Graphics g)
	{
		FontMetrics _metrics = g.getFontMetrics(getHeaderFont());
		return _metrics.getHeight() * 2;
	}

	public int getHeaderPadding()
	{
		return getHeaderFont().getSize() / 2;
	}

	/**
	 * Change fontProvider.
	 * 
	 * @param fontProvider
	 *            the new value of fontProvider.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setFontProvider(FontProvider fontProvider)
	{
		myFontProvider = fontProvider;
	}

}
