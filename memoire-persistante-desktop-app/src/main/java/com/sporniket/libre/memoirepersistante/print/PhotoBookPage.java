/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.libre.memoirepersistante.types.PhotoResource;

/**
 * Base class for printing a set of photobook pages sharing the same layout.
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
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * 
 * <p>
 * You should have received a copy of the GNU General Public License along with <i>The Sporniket Image Library &#8211; core</i>. If
 * not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.
 * 
 * <hr>
 * 
 * @author David SPORN
 * 
 * @version 15.07.00-SNAPSHOT
 * @since 15.07.00-SNAPSHOT
 */
public abstract class PhotoBookPage implements Printable
{
	public static class Specifications
	{
		private static final String FORMAT__TO_STRING = "Specifications [myPage={0}, myPhotos='{'{1}'}']";

		private final int myPage;

		private final List<PhotoResource> myPhotos;

		/**
		 * @param page
		 *            index of the page.
		 * @param photos
		 *            the photos to display.
		 * @since 15.07.00-SNAPSHOT
		 */
		public Specifications(int page, List<PhotoResource> photos)
		{
			super();
			myPage = page;
			myPhotos = photos;
		}

		/**
		 * Get page.
		 * 
		 * @return the page
		 * @since 15.07.00-SNAPSHOT
		 */
		public int getPage()
		{
			return myPage;
		}

		/**
		 * Get photos.
		 * 
		 * @return the photos
		 * @since 15.07.00-SNAPSHOT
		 */
		public List<PhotoResource> getPhotos()
		{
			return myPhotos;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 * 
		 * @since 15.07.00-SNAPSHOT
		 */
		@Override
		public String toString()
		{
			MessageFormat _format = new MessageFormat(FORMAT__TO_STRING);
			StringBuffer _photoListe = new StringBuffer();
			for (PhotoResource _photo : getPhotos())
			{
				if (_photoListe.length() > 0)
				{
					_photoListe.append(",");
				}
				_photoListe.append(_photo.getSourceFile().getName());
			}
			Object[] _args =
			{
					getPage(), _photoListe.toString()
			};
			return _format.format(_args);
		};

	}

	public static final int MARGIN_PHOTO = 12;// in points

	private final PageDecorator myPageDecorator;

	private final Map<Integer, Specifications> myPagesMap = new TreeMap<Integer, PhotoBookPage.Specifications>();

	private final PhotoBookFormat myPhotoBookFormat;

	/**
	 * Map that will contain text attributes to allow text computation for rendering.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private final Map<AttributedCharacterIterator.Attribute, Object> myTextFormatForPageBody = new HashMap<AttributedCharacterIterator.Attribute, Object>();

	/**
	 * Get textFormatForPageBody.
	 * @return the textFormatForPageBody
	 * @since 15.07.00-SNAPSHOT
	 */
	protected Map<AttributedCharacterIterator.Attribute, Object> getTextFormatForPageBody()
	{
		return myTextFormatForPageBody;
	}

	/**
	 * @param photoBookFormat format.
	 * @param pageDecorator decorator.
	 * @since 15.07.00-SNAPSHOT
	 */
	public PhotoBookPage(PhotoBookFormat photoBookFormat, PageDecorator pageDecorator)
	{
		super();
		myPhotoBookFormat = photoBookFormat;
		myPageDecorator = pageDecorator;
		myTextFormatForPageBody.put(TextAttribute.FONT, getPhotoBookFormat().getAnnotationFont());
	}

	public void addPage(Specifications specifications)
	{
		getPagesMap().put(specifications.getPage(), specifications);
	}

	public void clear()
	{
		getPagesMap().clear();
	}

	/**
	 * Get pageDecorator.
	 * 
	 * @return the pageDecorator
	 * @since 15.07.00-SNAPSHOT
	 */
	private PageDecorator getPageDecorator()
	{
		return myPageDecorator;
	}

	/**
	 * Get pagesMap.
	 * 
	 * @return the pagesMap
	 * @since 15.07.00-SNAPSHOT
	 */
	private Map<Integer, Specifications> getPagesMap()
	{
		return myPagesMap;
	}

	/**
	 * Get photoBookFormat.
	 * 
	 * @return the photoBookFormat
	 * @since 15.07.00-SNAPSHOT
	 */
	protected PhotoBookFormat getPhotoBookFormat()
	{
		return myPhotoBookFormat;
	}

	protected abstract void paint(Graphics2D graphics, PageFormat pageFormat, Specifications specifications);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		System.out.println("--->print page " + pageIndex);
		System.out.println("Imageable area : [" + pageFormat.getImageableX() + "," + pageFormat.getImageableY() + ","
				+ pageFormat.getImageableWidth() + "," + pageFormat.getImageableHeight() + "]");
		if (!getPagesMap().containsKey(pageIndex))
		{
			System.out.println("<--- no such page");
			return NO_SUCH_PAGE;
		}
		Graphics2D _graphics = (Graphics2D) graphics;
		getPageDecorator().paint(_graphics, pageFormat, pageIndex);
		Specifications _specifications = getPagesMap().get(pageIndex);
		System.out.println("specifications for page " + _specifications.toString());
		paint(_graphics, pageFormat, _specifications);
		System.out.println("<--- done");
		return PAGE_EXISTS;
	}

	/**
	 * Compute the scale factor of the image to fit in the constrained dimension.
	 * 
	 * @param picture
	 *            the picture to scale
	 * @param maxWidth
	 *            the maximum value for the scaled image width.
	 * @param maxHeight
	 *            the maximum value for the scaled image height.
	 * @return the scale factor.
	 * @since 15.07.00-SNAPSHOT
	 */
	protected double computeScaleFactor(BufferedImage picture, int maxWidth, int maxHeight)
	{
		double _factorX = (double) maxWidth / (double) picture.getWidth();
		double _factorY = (double) maxHeight / (double) picture.getHeight();
		return (_factorX < _factorY) ? _factorX : _factorY;
	}

	/**
	 * Draw the specified text inside the specified area, the text is vertically aligned on the bottom of the area.
	 * 
	 * @param text
	 *            the text to draw.
	 * @param top
	 *            the top coordinate of the area.
	 * @param left
	 *            the left coordinate of the area.
	 * @param width
	 *            the width of the area.
	 * @param height
	 *            the height of the area.
	 * @param graphics
	 *            the {@link Graphics2D} on which to draw.
	 * @since 15.07.00-SNAPSHOT
	 */
	protected void drawTextAtTheBottomOfTheArea(String text, double top, double left, double width, double height, Graphics2D graphics)
	{
		if (!StringTools.isEmptyString(text))
		{
			List<String> _descriptionLines = cutTextInLines(text, width, getTextFormatForPageBody(),
					graphics.getFontRenderContext());
			Font _font = getPhotoBookFormat().getAnnotationFont();
			int _annotationHeight = getPhotoBookFormat().getAnnotationHeight(graphics, _descriptionLines.size());
	
			int _textStartY = (int) (top + height - _annotationHeight);
			Composite _origComposite = graphics.getComposite();
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			graphics.setColor(Color.WHITE);
			graphics.fillRect((int) left, _textStartY, (int) width + 1, _annotationHeight);
	
			graphics.setComposite(_origComposite);
			graphics.setColor(Color.BLACK);
			graphics.setFont(_font);
			FontMetrics _fontMetrics = graphics.getFontMetrics();
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
			_textStartY += getPhotoBookFormat().getAnnotationPadding();
			int _textStartX = (int) (left + getPhotoBookFormat().getAnnotationPadding());
			for (String _descriptionLine : _descriptionLines)
			{
				graphics.drawString(_descriptionLine, _textStartX, _textStartY + _fontMetrics.getAscent());
				_textStartY += _fontMetrics.getHeight();
			}
	
		}
	}

	protected static List<String> cutTextInLines(String sourceText, double textAreaWidth, Map<AttributedCharacterIterator.Attribute, Object> textFormat, FontRenderContext renderContext)
	{
		String[] _lines = sourceText.split("\\n");
		List<String> _temp = new LinkedList<String>();
		for(String _line:_lines)
		{
			_temp.addAll(cutSingleLineTextInLines(_line.trim(), textAreaWidth, textFormat, renderContext));
		}
		return new ArrayList<String>(_temp);
	}
	
	/**
	 * Compose the text rendering of an initially single line text (no carriage return).
	 * @param sourceText
	 * @param textAreaWidth
	 * @param textFormat
	 * @param renderContext
	 * @return
	 * @since 15.07.00-SNAPSHOT
	 */
	private static List<String> cutSingleLineTextInLines(String sourceText, double textAreaWidth, Map<AttributedCharacterIterator.Attribute, Object> textFormat, FontRenderContext renderContext)
	{
		List<String> _temp = new LinkedList<String>();
		AttributedString _formatedText = new AttributedString(sourceText, textFormat);
		LineBreakMeasurer _lineBreakMeasurer = new LineBreakMeasurer(_formatedText.getIterator(), renderContext);
		int _position = 0 ;
		int _nextPosition ;
		while(_position < sourceText.length()-1)
		{
			_nextPosition = _lineBreakMeasurer.nextOffset((float) textAreaWidth);
			_temp.add(sourceText.substring(_position, _nextPosition));
			_position = _nextPosition;
			_lineBreakMeasurer.setPosition(_position);
		}
		return new ArrayList<String>(_temp);
	}

}
