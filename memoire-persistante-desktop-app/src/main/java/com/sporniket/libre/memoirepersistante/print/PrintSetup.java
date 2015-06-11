/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.PrintException;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;

/**
 * Class that manage the setting up of the printing
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
public class PrintSetup
{
	private static class PhotoBookPageSample implements Printable
	{
		private static final int HEIGHT_TEXT_AREA = 24;
		private final PrintSetup mySetup;

		/**
		 * @param setup
		 * @since 15.07.00-SNAPSHOT
		 */
		public PhotoBookPageSample(PrintSetup setup)
		{
			super();
			mySetup = setup;
		}

		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
		{
			// TODO Auto-generated method stub
			// first image
			PhotoResource _photo = mySetup.getPhotos().get(pageIndex);
//			if (pageIndex > 0)
//			{
//				return NO_SUCH_PAGE;
//			}
			Graphics2D _g2d = (Graphics2D) graphics;
			_g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			double _maxWidth = pageFormat.getImageableWidth();
			double _maxHeight = pageFormat.getImageableHeight();

			// load the image
			try
			{
				System.out.println("print " + _photo.getSourceFile().getName() + "...");
				BufferedImage _picture = null;
				_picture = ImageIO.read(_photo.getSourceFile());
				double _factor = _maxWidth / _picture.getWidth();

				double _imageWidth = _factor * _picture.getWidth();
				double _imageHeight = _factor * _picture.getHeight();

				_g2d.drawImage(_picture, 0, 0, (int) _imageWidth, (int) _imageHeight, 0, 0, _picture.getWidth(),
						_picture.getHeight(), null);
				
				//test writing text over photo, with semi-transparent white fill
				int _textStartY = (int) _imageHeight - HEIGHT_TEXT_AREA ;
				Composite _origComposite = _g2d.getComposite();
				_g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				_g2d.setColor(Color.WHITE);
				_g2d.fillRect(0, _textStartY, (int)_imageWidth, HEIGHT_TEXT_AREA);
				
				_g2d.setComposite(_origComposite);
				_g2d.setColor(Color.BLACK);
				Font _font = new Font("Serif", Font.PLAIN, 12);
				_g2d.setFont(_font);
				FontMetrics _fontMetrics = _g2d.getFontMetrics();
				_g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				_g2d.drawString(_photo.getSourceFile().getName(), 6, _textStartY+6+_fontMetrics.getAscent());

			}
			catch (IOException _exception)
			{
				_exception.printStackTrace();
				throw new PrinterException(_exception.getMessage());
			}

			return PAGE_EXISTS;
		}

	}
	
	private static class PhotoBook implements Pageable {
		private final PrintSetup mySetup;
		private final PageFormat myPageFormat;
		private final PhotoBookPageSample myPage;

		/**
		 * @param setup
		 * @param pageFormat
		 * @since 15.07.00-SNAPSHOT
		 */
		private PhotoBook(PrintSetup setup, PageFormat pageFormat)
		{
			super();
			mySetup = setup;
			myPageFormat = pageFormat;
			myPage = new PhotoBookPageSample(mySetup);
		}

		/**
		 * Get setup.
		 * @return the setup
		 * @since 15.07.00-SNAPSHOT
		 */
		public PrintSetup getSetup()
		{
			return mySetup;
		}

		public int getNumberOfPages()
		{
			return getSetup().getPhotos().size();
		}

		public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException
		{
			// TODO Auto-generated method stub
			return myPageFormat;
		}

		public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException
		{
			// TODO Auto-generated method stub
			return myPage;
		}
		
	}

	private static final PageFormatMarginSpecification DEFAULT_MARGINS = PageFormatMarginSpecification.createSymetricSpecification(
			36, 18);

	private List<PhotoResource> myPhotos;

	private PhotoBookPageSample myPrintJob = new PhotoBookPageSample(this);
	
	private PageDecorator myPageDecorator = new PageDecorator();
	
	private PhotoBookFormat myBookFormat = new PhotoBookFormat();
	
	private PhotoBookPageComposer myPageComposer = new PhotoBookPageComposer(myBookFormat, myPageDecorator);

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

	/**
	 * @param photos photos
	 * @param title the title
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setup(List<PhotoResource> photos, String title)
	{
		myPhotos = photos;
		myPageDecorator.setTitle(title);
		doSetup();
	}

	private void doSetup()
	{
		// TODO Auto-generated method stub
		PrinterJob _job = PrinterJob.getPrinterJob();
//		_job.setPrintable(myPrintJob);
		try
		{
			PageFormat _defaultPageFormat = PageFormatFactory.createStandardFormat(new Paper(),
					JavaPrintOrientation.PORTRAIT, DEFAULT_MARGINS);
			PageFormat _selectedPageFormat = _job.pageDialog(_defaultPageFormat);
			myPageComposer.setPageFormat(_selectedPageFormat);
			myPageComposer.compose(getPhotos());
			_job.setPageable(myPageComposer);//new PhotoBook(this, _selectedPageFormat));
			boolean _confirmPrint = _job.printDialog();
			if (_confirmPrint)
			{
				_job.print();
			}
		}
		catch (PrinterException e)
		{
			e.printStackTrace();
		}
		catch (PrintException _exception)
		{
			_exception.printStackTrace();
		}
	}

}
