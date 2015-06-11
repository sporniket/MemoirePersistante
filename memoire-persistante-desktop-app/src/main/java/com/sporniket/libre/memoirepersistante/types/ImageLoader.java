/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.metadata.IIOMetadata;

import com.sporniket.libre.memoirepersistante.images.GeneratorException;
import com.sporniket.libre.memoirepersistante.images.ThumbnailCreator;
import com.sporniket.libre.memoirepersistante.images.ThumbnailGenerator;
import com.sporniket.libre.images.core.ImageFileUtils.ReaderNotFoundException;
import com.sporniket.libre.images.core.metadata.ImageMetaData;
import com.sporniket.libre.images.core.metadata.ImageMetaDataException;
import com.sporniket.libre.images.core.metadata.MetadataUtils;
import com.sporniket.libre.images.core.metadata.Orientation;
import com.sporniket.libre.images.core.metadata.exif.BasicExifImageMetaDataExtractor;

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
public class ImageLoader
{

	private static final String PADDING_ZERO = "0";

	private static final String MD5_NAME = "MD5";

	private static final Charset CHARSET_UTF8 = Charset.forName("UTF8");

	private final MessageDigest myFilenameHasher;

	private MessageDigest getFilenameHasher()
	{
		return myFilenameHasher;
	}

	public ImageLoader()
	{
		try
		{
			myFilenameHasher = MessageDigest.getInstance(MD5_NAME);
		}
		catch (NoSuchAlgorithmException _exception)
		{
			throw new RuntimeException(_exception);
		}
	}

	public List<PhotoResource> loadPhotos(File[] photos, File workingDirectory)
	{
		List<PhotoResource> _result = new ArrayList<PhotoResource>();
		ThumbnailGenerator _thumbnailCreator = new ThumbnailGenerator(300, workingDirectory);

		for (File _photoFile : photos)
		{
			PhotoResource _descriptor = new PhotoResource();
			_descriptor.setSourceFile(_photoFile);
			getFilenameHasher().reset();
			String _fullPath = _photoFile.getAbsolutePath();
			String _hash = hash(_fullPath);
			Orientation _orientation = Orientation.ROT_000;
			Dimension _realDimensions = new Dimension();
			try
			{
				IIOMetadata _imageIoMetadata = MetadataUtils.extractMetadata(_photoFile);
				ImageMetaData imageMetaData = BasicExifImageMetaDataExtractor.getInstance().extractFromResource(_imageIoMetadata);
				_orientation = imageMetaData.getImageOrientation();
				_realDimensions = imageMetaData.getUnrotatedDimension();
			}
			catch (IOException _exception)
			{
				_exception.printStackTrace();
			}
			catch (ImageMetaDataException _exception)
			{
				_exception.printStackTrace();
			}
			catch (ReaderNotFoundException _exception)
			{
				_exception.printStackTrace();
			}
			catch(Exception _exception)
			{
				_exception.printStackTrace();
			}
			try
			{
				_descriptor.setRealDimensions(_realDimensions);
			}
			catch (PropertyVetoException _exception)
			{
				_exception.printStackTrace();
			}
			_descriptor.setOrientation(_orientation);

			// create the thumbnail
			try
			{
				_descriptor.setThumbnail(_thumbnailCreator.getThumbnail(_descriptor, false));
				_result.add(_descriptor);
			}
			catch (GeneratorException _exception)
			{
				_exception.printStackTrace();
			}
		}

		return _result;
	}

	/**
	 * Compute the hash of the given value.
	 * 
	 * @param value
	 * @return
	 */
	private String hash(String value)
	{
		byte[] _sourceBytes = value.getBytes(CHARSET_UTF8);
		getFilenameHasher().update(_sourceBytes);
		byte[] _hashBytes = getFilenameHasher().digest();
		StringBuilder _hashBuffer = new StringBuilder();
		for (byte _value : _hashBytes)
		{
			int _positiveValue = (_value < 0) ? _value + 256 : _value;
			if (_value < 16)
			{
				_hashBuffer.append(PADDING_ZERO).append(Integer.toHexString(_positiveValue));
			}
			else
			{
				_hashBuffer.append(Integer.toHexString(_positiveValue));
			}
		}
		String _hash = _hashBuffer.toString();
		return _hash;
	}

}
