/**
 * 
 */
package com.sporniket.libre.memoirepersistante.images;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;

import javax.imageio.ImageIO;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.lang.string.StringTools;

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
public class ThumbnailCreator {
	/**
	 * Type of file to generate.
	 */
	private static final String NAME__FILE_FORMAT = "JPG";

	/**
	 * Format to compute the thumbnail filename.
	 */
	private static final String FORMAT__FILENAME = "{0}.jpg";

	/**
	 * The generated thumbnail will have dimension that fit inside
	 * {@link #myMaxSize}x{@link #myMaxSize}
	 */
	private int myMaxSize;

	private MessageFormat myFormatterForFilename = new MessageFormat(
			FORMAT__FILENAME);

	/**
	 * This instance will create thumbnails into this directory.
	 */
	private File myWorkingDirectory;

	public ThumbnailCreator(int maxSize, File workingDirectory) {
		super();
		myMaxSize = maxSize;
		myWorkingDirectory = workingDirectory;
		if (!myWorkingDirectory.exists() || !myWorkingDirectory.isDirectory()) {
			throw new RuntimeException(
					"The working directory MUST exist and be a directory : "
							+ myWorkingDirectory.getAbsolutePath());
		}
	}

	/**
	 * Create an unrotated thumbnail from an image.
	 * 
	 * @param resource
	 *            the image to convert.
	 * @param forceGeneration
	 *            if <code>true</code>, create the thumbnail even if it exists
	 *            and is up to date.
	 * @return the {@link File} pointing to the thumbnail.
	 */
	public File createThumbnail(PhotoResource resource, boolean forceGeneration) {
		File _image = resource.getSourceFile();
		String _hashedName = StringTools.hash(_image.getAbsolutePath());
		File _thumbnail = new File(getWorkingDirectory(),
				formatFilename(_hashedName));
		try {
			if (!forceGeneration && _thumbnail.exists()
					&& _thumbnail.lastModified() > _image.lastModified()) {
				return _thumbnail; // do not generate
			}
			// Image loading
			BufferedImage _picture = null;
			_picture = ImageIO.read(_image);

			int _maxDimension = (_picture.getWidth() > _picture.getHeight()) ? _picture
					.getWidth() : _picture.getHeight();

			// Compute thumbnail dimesions
			double _scaleFactor = 1;
			if (_maxDimension > getMaxSize()) {
				_scaleFactor = (double) getMaxSize() / _maxDimension;
			}

			Dimension _thumbnailDimensions = new Dimension(
					(int) ((double) _picture.getWidth() * _scaleFactor),
					(int) ((double) _picture.getHeight() * _scaleFactor));

			boolean _isOrientationPortrait = isOrientationPortrait(resource);
			if (_isOrientationPortrait) {
				_thumbnailDimensions = new Dimension(
						_thumbnailDimensions.height, _thumbnailDimensions.width);
			}

			// Create the thumbnail
			BufferedImage _thumbnailImage = new BufferedImage(
					_thumbnailDimensions.width, _thumbnailDimensions.height,
					BufferedImage.TYPE_INT_RGB);

			Graphics2D _graphics = (Graphics2D) _thumbnailImage.getGraphics();
			_graphics.scale(resource.getOrientation().getScaleX(), 1);
			if (_isOrientationPortrait)
			{
				int _translateComponent = (_thumbnailDimensions.height - _thumbnailDimensions.width)/2;
				_graphics.translate(-_translateComponent, _translateComponent);
			}
			switch (resource.getOrientation()) {
			case MIRROR_ROT_090_CCW:
			case ROT_090_CCW:
				_graphics.rotate(resource.getOrientation().getRotationAngle(),
						_thumbnailDimensions.height/2, _thumbnailDimensions.width/2	);
				break;
			case MIRROR_ROT_180_CCW:
			case ROT_180_CCW:
				_graphics.rotate(resource.getOrientation().getRotationAngle(),
						_thumbnailDimensions.height/2, _thumbnailDimensions.width/2);
				break;
			case MIRROR_ROT_270_CCW:
			case ROT_270_CCW:
				_graphics.rotate(resource.getOrientation().getRotationAngle(),
						_thumbnailDimensions.height/2, _thumbnailDimensions.width/2);
				break;
			}
			if (_isOrientationPortrait) {
				_graphics.drawImage(_picture, 0, 0,
						_thumbnailImage.getHeight(),
						_thumbnailImage.getWidth(), 0, 0, _picture.getWidth(),
						_picture.getHeight(), null);
			} else {
				_graphics.drawImage(_picture, 0, 0, _thumbnailImage.getWidth(),
						_thumbnailImage.getHeight(), 0, 0, _picture.getWidth(),
						_picture.getHeight(), null);
			}

			// Save and return
			ImageIO.write(_thumbnailImage, NAME__FILE_FORMAT, _thumbnail);
			return _thumbnail;
		} catch (Exception _exception) {
			throw new RuntimeException(_exception);
		}
	}

	/**
	 * Test whether the resource is vertical.
	 * 
	 * @param resource the photo
	 * @return true if the photo is turned of a quarter of turn.
	 */
	private boolean isOrientationPortrait(PhotoResource resource) {
		// TODO Auto-generated method stub
		
		return resource.getOrientation().isQuarterTurnRequired();
	}

	/**
	 * Compute the filename of the thumbnail.
	 * 
	 * @param name
	 *            the hashed name of the original file.
	 * @return the correct filename.
	 */
	private String formatFilename(String name) {
		Object[] _args = { name };
		return myFormatterForFilename.format(_args);
	}

	/**
	 * @return the maxSize
	 */
	private int getMaxSize() {
		return myMaxSize;
	}

	/**
	 * @return the workingDirectory
	 */
	private File getWorkingDirectory() {
		return myWorkingDirectory;
	}
}
