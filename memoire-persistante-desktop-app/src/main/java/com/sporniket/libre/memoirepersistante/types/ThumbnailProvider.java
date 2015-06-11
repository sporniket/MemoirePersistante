/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Manage a limited number of thumbnail (300x300 pictures).
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
public class ThumbnailProvider
{

	private static final String FILENAME__DEFAULT_THUMBNAIL = "camera.png";

	private static final int MAXIMUM_CAPACITY = 10;

	private File myWorkingDirectory;

	/**
	 * @return the workingDirectory
	 */
	public File getWorkingDirectory()
	{
		return myWorkingDirectory;
	}

	public ThumbnailProvider(File workingDirectory)
	{
		super();
		myWorkingDirectory = workingDirectory;
	}

	public BufferedImage getThumbnail(File thumbnailFile) throws IOException
	{
		BufferedImage _result = null;
		_result = ImageIO.read(thumbnailFile);
		return _result;
	}

	// todo : register a new thumbnail
	// todo : set a working directory for thumbnails

}
