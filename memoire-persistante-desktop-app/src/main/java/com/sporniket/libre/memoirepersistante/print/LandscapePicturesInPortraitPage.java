/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;

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
public class LandscapePicturesInPortraitPage extends PhotoBookPage
{

	/**
	 * @param photoBookFormat the format.
	 * @param pageDecorator the decorator.
	 * @since 15.07.00-SNAPSHOT
	 */
	public LandscapePicturesInPortraitPage(PhotoBookFormat photoBookFormat, PageDecorator pageDecorator)
	{
		super(photoBookFormat, pageDecorator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sporniket.libre.memoirepersistante.print.PhotoBookPage#paint(java.awt.Graphics2D, java.awt.print.PageFormat, int)
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	@Override
	protected void paint(Graphics2D graphics, PageFormat pageFormat, Specifications specifications)
	{
		if (specifications.getPhotos().isEmpty())
		{
			return;
		}

		int _headerHeight = getPhotoBookFormat().getHeaderHeight(graphics);
		int _footerHeight = getPhotoBookFormat().getFooterHeight(graphics);
		int _baseX = (int) pageFormat.getImageableX();
		int _baseY = (int) pageFormat.getImageableY() + _headerHeight;
		int _baseW = (int) pageFormat.getImageableWidth();
		int _baseH = (int) pageFormat.getImageableHeight() - _headerHeight - _footerHeight;

		int _localImage1Y = PhotoBookPage.MARGIN_PHOTO;
		int _boundingHeight = (_baseH - _localImage1Y) / 2;
		int _localImage2Y = _localImage1Y + _boundingHeight;

		int _image1Y = _baseY + _localImage1Y;
		int _image2Y = _baseY + _localImage2Y;
		int _imageMaxWidth = _baseW;
		int _imageMaxHeight = _boundingHeight - PhotoBookPage.MARGIN_PHOTO;

		PhotoResource _photo = specifications.getPhotos().get(0);
		drawPhoto(_photo, _baseX, _imageMaxWidth, _image1Y, _imageMaxHeight, graphics);

		if (specifications.getPhotos().size() == 1)
		{
			return;
		}
		_photo = specifications.getPhotos().get(1);
		drawPhoto(_photo, _baseX, _imageMaxWidth, _image2Y, _imageMaxHeight, graphics);

	}

	/**
	 * @param photo
	 * @param x
	 * @param maxWidth
	 * @param y
	 * @param maxHeight
	 * @param graphics
	 * @since 15.07.00-SNAPSHOT
	 */
	private void drawPhoto(PhotoResource photo, int x, int maxWidth, int y, int maxHeight, Graphics2D graphics)
	{
		try
		{
			File _sourceFile = photo.getSourceFile();
			System.out.println("drawPhoto " + _sourceFile.getName() + " inside [" + x + "," + y + "," + maxWidth + "," + maxHeight
					+ "]");
			BufferedImage _picture = null;
			_picture = ImageIO.read(_sourceFile);
			double _factor = computeScaleFactor(_picture, maxWidth, maxHeight);
			double _imageWidth = _factor * _picture.getWidth();
			double _imageHeight = _factor * _picture.getHeight();
			System.out.println("    --> factor = " + _factor);
			System.out.println("    --> Image size = " + _imageWidth + " x " + _imageHeight);

			double _dx1 = x + (maxWidth - _imageWidth) / 2;
			double _dy1 = y;
			double _dx2 = _dx1 + _imageWidth;
			double _dy2 = _dy1 + _imageHeight;
			System.out.println("    --> [" + _dx1 + "," + _dy1 + "," + _dx2 + "," + _dy2 + "]");

			graphics.drawImage(_picture, (int) _dx1, (int) _dy1, (int) _dx2, (int) _dy2, 0, 0, _picture.getWidth(),
					_picture.getHeight(), null);

			// Annotation drawing
			drawTextAtTheBottomOfTheArea(photo.getDescription(), _dy1, _dx1, _imageWidth, _imageHeight, graphics);

		}
		catch (IOException _exception)
		{
			// TODO Auto-generated catch block
			_exception.printStackTrace();
		}
	}
}
