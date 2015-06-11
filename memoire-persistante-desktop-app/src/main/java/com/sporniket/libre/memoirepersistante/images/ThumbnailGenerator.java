/**
 * 
 */
package com.sporniket.libre.memoirepersistante.images;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.imageio.ImageIO;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.images.core.ImageFileFormat;
import com.sporniket.libre.images.core.thumbnails.Generator;
import com.sporniket.libre.images.core.thumbnails.NameProvider;
import com.sporniket.libre.images.core.thumbnails.WorkingDirectoryManager;
import com.sporniket.libre.io.FileTools;
import com.sporniket.libre.lang.string.StringTools;

/**
 * Thumbnail generator, a thumbnail is a scaled and unrotated version of the original picture.
 * 
 * <p>
 * The generator does not take into account the orientation (exif metadata) of the image.
 * <p>
 * An instance of the generator create thumbnail that have dimensions that are not larger than a given <em>size</em>. Those
 * thumbnail are saved into a <em>working directory</em>.
 * <p>
 * If the source image is too small, it is not scaled up.
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
public class ThumbnailGenerator
{
	/**
	 * Extension of the thumbnail filename.
	 */
	private static final String FILENAME_EXTENSION = "jpg";

	/**
	 * Provide thumbnail file names.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private NameProvider myNameProvider;

	/**
	 * Manage the working directory organisation.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private WorkingDirectoryManager myWorkingDirectoryManager;

	/**
	 * Generate the thumbnails.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private Generator myGenerator;

	public ThumbnailGenerator(int maxSize, File workingDirectory)
	{
		super();
		myNameProvider = new NameProvider.ByPathAndMaxSize(maxSize, FILENAME_EXTENSION);
		myWorkingDirectoryManager = new WorkingDirectoryManager.SimpleManager(workingDirectory);
		myGenerator = new Generator(maxSize);
	}

	/**
	 * Compute the file descriptor of the thumbnail to create.
	 * 
	 * @param sourceImageFile
	 *            the image source file
	 * @return a file descriptor to use to create the thumbnail image.
	 * @throws GeneratorException
	 *             if their is a problem.
	 */
	private File computeThumbnailFile(File sourceImageFile) throws GeneratorException
	{
		String _thumbnailName = myNameProvider.computeName(sourceImageFile);
		File _thumbnailDir = myWorkingDirectoryManager.getStorageDirectory(_thumbnailName);
		if (!_thumbnailDir.exists() && !_thumbnailDir.mkdirs())
		{
			throw new GeneratorException("Could not create all needed subdirectories : " + _thumbnailDir.getAbsolutePath());
		}
		else if (_thumbnailDir.exists() && !_thumbnailDir.isDirectory())
		{
			throw new GeneratorException("Cannot create a subdirectory because a file with the same name exists : "
					+ _thumbnailDir.getAbsolutePath());
		}
		File _thumbnail = new File(_thumbnailDir, _thumbnailName);
		return _thumbnail;
	}

	/**
	 * Create an unrotated thumbnail from an image.
	 * 
	 * @param resource
	 *            the image to convert.
	 * @param forceGeneration
	 *            if <code>true</code>, create the thumbnail even if it exists and is up to date.
	 * @return the {@link File} pointing to the thumbnail.
	 * @throws GeneratorException when there is a problem.
	 */
	public File getThumbnail(PhotoResource resource, boolean forceGeneration) throws GeneratorException
	{
		try
		{
			File _image = resource.getSourceFile();
			File _thumbnail = computeThumbnailFile(_image);
			boolean _generationNotNeeded = !forceGeneration && _thumbnail.exists()
					&& _thumbnail.lastModified() > _image.lastModified();
			if (!_generationNotNeeded)
			{
				myGenerator.generateThumbnail(_image, _thumbnail);
			}
			return _thumbnail;
		}
		catch (GeneratorException _exception)
		{
			throw _exception;
		}
		catch (Exception _exception)
		{
			throw new GeneratorException("Unexpected error", _exception);
		}
	}
}
