/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that will display the header and footer.
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
 * <i>Memoire Persistante &#8211; app</i> is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with <i>The Sporniket Image Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
 * 
 * <hr>
 * 
 * @author David SPORN
 * 
 * @version 15.07.00-SNAPSHOT
 * @since 15.07.00-SNAPSHOT
 */
public class PageDecorator
{
	private static final String DEFAULT_TITLE = "PhotoBook";

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
	 * <i>Memoire Persistante &#8211; app</i> is free software: you can redistribute it and/or modify it under the terms of the GNU
	 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
	 * option) any later version.
	 * 
	 * <p>
	 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
	 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
	 * License for more details.
	 * 
	 * <p>
	 * You should have received a copy of the GNU General Public License along with <i>The Sporniket Image Library &#8211;
	 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
	 * 
	 * <hr>
	 * 
	 * @author David SPORN
	 * 
	 * @version 15.07.00-SNAPSHOT
	 * @since 15.07.00-SNAPSHOT
	 */
	public static class DefaultRenderer implements Renderer
	{

		private final Stroke myRuleStroke = new BasicStroke(1);

		public void renderFooter(PageDecorator data, int page, Graphics2D target, PageFormat pageFormat)
		{
			target.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			PhotoBookFormat _bookFormat = data.getBookFormat();
			Font _font = _bookFormat.getFooterFont();
			FontMetrics _metrics = target.getFontMetrics(_font);
			String _footer = "" + page + " / " + data.getPageCount();

			int _footerHeight = _bookFormat.getFooterHeight(target);
			double _textWidth = _font.getStringBounds(_footer, target.getFontRenderContext()).getWidth();
			int _baseX = (int) (pageFormat.getImageableX());
			int _textX = (int) (pageFormat.getImageableX() + (pageFormat.getImageableWidth() - 2*_bookFormat.getFooterPadding() - _textWidth)/2.0);
			int _baseY = (int) (pageFormat.getImageableY() + pageFormat.getImageableHeight() - _footerHeight);

			// clipping
			Shape _origClip = target.getClip();
			Rectangle2D _rectClip = new Rectangle2D.Double();
			_rectClip.setFrame(_baseX, _baseY, pageFormat.getImageableWidth(), _footerHeight);
			target.setClip(_rectClip);

			// draw the text
			// TODO CENTER THE TEXT
			target.setColor(Color.BLACK);
			target.setFont(_font);
			target.drawString(_footer, _textX,
					_bookFormat.getFooterPadding() + _metrics.getAscent() + _baseY);

			// draw rule
			Stroke _origStroke = target.getStroke();

			int _ruleY = _baseY;
			Line2D _rule = new Line2D.Double(_baseX, _ruleY, _baseX + pageFormat.getImageableWidth(), _ruleY);
			target.setStroke(myRuleStroke);
			target.draw(_rule);

			target.setStroke(_origStroke);

			// restore
			target.setClip(_origClip);
		}

		public void renderHeader(PageDecorator data, int page, Graphics2D target, PageFormat pageFormat)
		{
			target.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			PhotoBookFormat _bookFormat = data.getBookFormat();
			Font _font = _bookFormat.getHeaderFont();
			FontMetrics _metrics = target.getFontMetrics(_font);
			int _headerHeight = _bookFormat.getHeaderHeight(target);
			double _textWidth = _font.getStringBounds(data.getTitle(), target.getFontRenderContext()).getWidth();
			int _baseX = (int) (pageFormat.getImageableX());
			int _textX = (int) (pageFormat.getImageableX() + (pageFormat.getImageableWidth() - 2*_bookFormat.getHeaderPadding() - _textWidth)/2.0);
			int _baseY = (int) pageFormat.getImageableY();

			// clipping
			Shape _origClip = target.getClip();
			Rectangle2D _rectClip = new Rectangle2D.Double();
			_rectClip.setFrame(_baseX, _baseY, pageFormat.getImageableWidth(), _headerHeight);
			target.setClip(_rectClip);

			// draw the text
			target.setColor(Color.BLACK);
			target.setFont(_font);
			target.drawString(data.getTitle(), _textX,
					_bookFormat.getHeaderPadding() + _metrics.getAscent() + _baseY);

			// draw rule
			Stroke _origStroke = target.getStroke();

			int _ruleY = _headerHeight + _baseY;
			Line2D _rule = new Line2D.Double(_baseX, _ruleY, _baseX + pageFormat.getImageableWidth(), _ruleY);
			target.setStroke(myRuleStroke);
			target.draw(_rule);

			target.setStroke(_origStroke);

			// restore
			target.setClip(_origClip);
		}

	}

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
	 * <i>Memoire Persistante &#8211; app</i> is free software: you can redistribute it and/or modify it under the terms of the GNU
	 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
	 * option) any later version.
	 * 
	 * <p>
	 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
	 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
	 * License for more details.
	 * 
	 * <p>
	 * You should have received a copy of the GNU General Public License along with <i>The Sporniket Image Library &#8211;
	 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
	 * 
	 * <hr>
	 * 
	 * @author David SPORN
	 * 
	 * @version 15.07.00-SNAPSHOT
	 * @since 15.07.00-SNAPSHOT
	 */
	public static interface Renderer
	{
		void renderFooter(PageDecorator data, int page, Graphics2D target, PageFormat pageFormat);

		void renderHeader(PageDecorator data, int page, Graphics2D target, PageFormat pageFormat);
	}

	private static final PhotoBookFormat DEFAULT_PHOTOBOOK_FORMAT = new PhotoBookFormat();

	private static final Renderer DEFAULT_RENDERER = new DefaultRenderer();

	private PhotoBookFormat myBookFormat = DEFAULT_PHOTOBOOK_FORMAT;

	/**
	 * Number of pages of the photobook.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private int myPageCount;

	private Renderer myRenderer = DEFAULT_RENDERER;

	/**
	 * Title of the photobook.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private String myTitle = DEFAULT_TITLE;

	/**
	 * Map that will contain text attributes to allow text computation for rendering.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private final Map<AttributedCharacterIterator.Attribute, Object> myTextFormatForHeader = new HashMap<AttributedCharacterIterator.Attribute, Object>();

	/**
	 * Map that will contain text attributes to allow text computation for rendering.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private final Map<AttributedCharacterIterator.Attribute, Object> myTextFormatForFooter = new HashMap<AttributedCharacterIterator.Attribute, Object>();


	/**
	 * Get bookFormat.
	 * 
	 * @return the bookFormat
	 * @since 15.07.00-SNAPSHOT
	 */
	public PhotoBookFormat getBookFormat()
	{
		return myBookFormat;
	}

	/**
	 * Get pageCount.
	 * 
	 * @return the pageCount
	 * @since 15.07.00-SNAPSHOT
	 */
	public int getPageCount()
	{
		return myPageCount;
	}

	/**
	 * Get renderer.
	 * 
	 * @return the renderer
	 * @since 15.07.00-SNAPSHOT
	 */
	private Renderer getRenderer()
	{
		return myRenderer;
	}

	/**
	 * Get titre.
	 * 
	 * @return the titre
	 * @since 15.07.00-SNAPSHOT
	 */
	public String getTitle()
	{
		return myTitle;
	}

	public void paint(Graphics2D g, PageFormat pageFormat, int page)
	{
		updateTextFormats(); // to be sure
		int _page = page + 1;// page is 0-based
		getRenderer().renderHeader(this, _page, g, pageFormat);
		getRenderer().renderFooter(this, _page, g, pageFormat);
	}

	/**
	 * Change bookFormat.
	 * 
	 * @param bookFormat
	 *            the new value of bookFormat.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setBookFormat(PhotoBookFormat bookFormat)
	{
		myBookFormat = bookFormat;
	}

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public void updateTextFormats()
	{
		myTextFormatForHeader.put(TextAttribute.FONT, getBookFormat().getHeaderFont());
		myTextFormatForFooter.put(TextAttribute.FONT, getBookFormat().getFooterFont());
	}
	
	/**
	 * Change pageCount.
	 * 
	 * @param pageCount
	 *            the new value of pageCount.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setPageCount(int pageCount)
	{
		myPageCount = pageCount;
	}

	/**
	 * Change renderer.
	 * 
	 * @param renderer
	 *            the new value of renderer.
	 * @since 15.07.00-SNAPSHOT
	 */
	private void setRenderer(Renderer renderer)
	{
		myRenderer = renderer;
	}

	/**
	 * Change titre.
	 * 
	 * @param title
	 *            the new value of titre.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setTitle(String title)
	{
		myTitle = title;
	}
}
