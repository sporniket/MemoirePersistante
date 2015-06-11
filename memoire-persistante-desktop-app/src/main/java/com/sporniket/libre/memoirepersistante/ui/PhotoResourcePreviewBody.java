/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComponent;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.memoirepersistante.types.ThumbnailProvider;

/**
 * Actual preview of the picture.
 * 
 * The preview will show : the picture itself (always), a cropping zone (optionnal), points of interests (optionnal).
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
class PhotoResourcePreviewBody extends JComponent
{

	/**
	 * serial uid.
	 */
	private static final long serialVersionUID = -6238285071821145149L;

	private PhotoResource myData;

	private UiTheme myUiTheme;

	private int myCenteredX;

	private ThumbnailProvider myThumbnailPool;

	/**
	 * @return the thumbnailPool
	 */
	private ThumbnailProvider getThumbnailPool()
	{
		return myThumbnailPool;
	}

	/**
	 * @param thumbnailPool
	 *            the thumbnailPool to set
	 */
	private void setThumbnailPool(ThumbnailProvider thumbnailPool)
	{
		myThumbnailPool = thumbnailPool;
	}

	/**
	 * @return the centeredX
	 */
	public int getCenteredX()
	{
		return myCenteredX;
	}

	/**
	 * @param centeredX
	 *            the centeredX to set
	 */
	public void setCenteredX(int centeredX)
	{
		myCenteredX = centeredX;
	}

	/**
	 * @return the centeredY
	 */
	public int getCenteredY()
	{
		return myCenteredY;
	}

	/**
	 * @param centeredY
	 *            the centeredY to set
	 */
	public void setCenteredY(int centeredY)
	{
		myCenteredY = centeredY;
	}

	private int myCenteredY;

	private BufferedImage myImage;

	/**
	 * @return the image
	 */
	public BufferedImage getImage()
	{
		return myImage;
	}

	public PhotoResource getData()
	{
		return myData;
	}

	private void setData(PhotoResource data)
	{
		myData = data;
	}

	public UiTheme getUiTheme()
	{
		return myUiTheme;
	}

	public void setUiTheme(UiTheme uiTheme)
	{
		myUiTheme = uiTheme;
	}

	public PhotoResourcePreviewBody(PhotoResource data, ThumbnailProvider thumbnailPool, UiTheme uiTheme) throws IOException
	{
		super();
		setData(data);
		setThumbnailPool(thumbnailPool);
		setUiTheme(uiTheme);
		setSize(new Dimension(uiTheme.getThumbnailBoundingBoxForMainPanel()));
		setPreferredSize(getSize());
		setMinimumSize(getSize());
		setMaximumSize(getSize());
		myImage = getThumbnailPool().getThumbnail(getData().getThumbnail());
		setCenteredX((int) ((getSize().getWidth() - myImage.getWidth()) / 2));
		setCenteredY((int) ((getSize().getHeight() - myImage.getHeight()) / 2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		// boolean _isSelected = false ;//TODO
		Color _background = getBackground();
		g.setColor(_background);
		Graphics2D _g2 = (Graphics2D) g;
		// g2.translate(-getCenteredX(), -getCenteredY());
		// g2.scale(getData().getOrientation().getScaleX(), 1);
		// g2.rotate(getData().getOrientation().getRotationAngle(),THUMBNAIL_CENTER,THUMBNAIL_CENTER);
		g.fillRect(0, 0, (int) getSize().getWidth(), (int) getSize().getWidth());

		boolean _isOrientationPortrait = getData().getOrientation().isQuarterTurnRequired();
		_g2.scale(getData().getOrientation().getScaleX(), 1);

		if (_isOrientationPortrait)
		{
			_g2.translate(getCenteredY(), getCenteredX());
		}
		else
		{
			_g2.translate(getCenteredX(), getCenteredY());
		}

		switch (getData().getOrientation())
		{
			case MIRROR_ROT_090_CCW:
			case ROT_090_CCW:
				_g2.rotate(getData().getOrientation().getRotationAngle(), getImage().getHeight() / 2, getImage().getWidth() / 2);
				break;
			case MIRROR_ROT_180_CCW:
			case ROT_180_CCW:
				_g2.rotate(getData().getOrientation().getRotationAngle(), getImage().getHeight() / 2, getImage().getWidth() / 2);
				break;
			case MIRROR_ROT_270_CCW:
			case ROT_270_CCW:
				_g2.rotate(getData().getOrientation().getRotationAngle(), getImage().getHeight() / 2, getImage().getWidth() / 2);
				break;
		}

		if (_isOrientationPortrait)
		{
			int _translateComponent = (getImage().getWidth() - getImage().getHeight()) / 2;
			_g2.translate(-_translateComponent, _translateComponent);
		}

		g.drawImage(getImage(), 0, 0, null);
		_g2.translate(0, 0);
		_g2.rotate(0);
	}

}
