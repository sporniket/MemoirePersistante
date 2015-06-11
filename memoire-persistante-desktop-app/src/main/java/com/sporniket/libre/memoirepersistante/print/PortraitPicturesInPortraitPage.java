/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
public class PortraitPicturesInPortraitPage extends PhotoBookPage
{

	/**
	 * @param photoBookFormat format.
	 * @param pageDecorator decorator.
	 * @since 15.07.00-SNAPSHOT
	 */
	public PortraitPicturesInPortraitPage(PhotoBookFormat photoBookFormat, PageDecorator pageDecorator)
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
		// TODO Auto-generated method stub
		int _headerHeight = getPhotoBookFormat().getHeaderHeight(graphics);
		int _footerHeight = getPhotoBookFormat().getFooterHeight(graphics);
		int _baseX = (int) pageFormat.getImageableX();
		int _baseY = (int) pageFormat.getImageableY() + _headerHeight;
		int _baseW = (int) pageFormat.getImageableWidth();
		int _baseH = (int) pageFormat.getImageableHeight() - _headerHeight - _footerHeight;

		int _localImage1Y = PhotoBookPage.MARGIN_PHOTO;
		int _boundingHeight = (_baseH - _localImage1Y) ;

		int _image1Y = _baseY + _localImage1Y;
		int _imageMaxWidth = _baseW;
		int _imageMaxHeight = _boundingHeight - PhotoBookPage.MARGIN_PHOTO;

		PhotoResource _photo = specifications.getPhotos().get(0);
		drawPhoto(_photo, _baseX, _imageMaxWidth, _image1Y, _imageMaxHeight, graphics);

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
			double _factor = computeScaleFactor(_picture, maxHeight, maxWidth); //twist ! switch maxHeight and maxWidth because the picture is unrotated yet.
			double _imageWidth = _factor * _picture.getHeight();
			double _imageHeight = _factor * _picture.getWidth();
			System.out.println("    --> factor = " + _factor);
			System.out.println("    --> Image size = " + _imageWidth + " x " + _imageHeight);

			//so, here is the final area
			double _dx1 = x + (maxWidth - _imageWidth) / 2;
			double _dy1 = y;
			double _dx2 = _dx1 + _imageWidth;
			double _dy2 = _dy1 + _imageHeight;
			System.out.println("    --> [" + _dx1 + "," + _dy1 + "," + _dx2 + "," + _dy2 + "]");
			
			AffineTransform _originalTransform = graphics.getTransform();
			//now the tricky part : draw a rotated picture ; follow the transformations backward...
			
			//translate to final position
			double _centerX = (_dx1 + _dx2)/2;
			double _centerY = (_dy1 + _dy2)/2;
			graphics.translate(_centerX, _centerY);
			
			//scaling
			graphics.scale(_factor, _factor);
			
			//rotate
			graphics.rotate(photo.getOrientation().getRotationAngle(), 0, 0);
			
			//move the image to put center in 0,0
			graphics.translate(-_picture.getWidth()/2.0, -_picture.getHeight()/2.0);

			graphics.drawImage(_picture, 0, 0, null);

			//restore transformation
			graphics.setTransform(_originalTransform);
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
