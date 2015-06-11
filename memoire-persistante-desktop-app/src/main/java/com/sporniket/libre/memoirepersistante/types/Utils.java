/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import com.sporniket.libre.io.FileComparator;
import com.sporniket.libre.io.FileComparator.ByAbsolutePath;

/**
 * Utilities class.
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
public class Utils
{

	/**
	 * Get a filtered list of files of a folder, sorted by name in ascending order.
	 * @param sourceDir source folder.
	 * @param filter the filename filter.
	 * @return the list
	 * @since 15.07.00-SNAPSHOT
	 */
	public static File[] getSortedFiles(File sourceDir, FilenameFilter filter)
	{
		File[] _unsortedList = sourceDir.listFiles(filter);
		Arrays.sort(_unsortedList, FileComparator.ByAbsolutePath.ASCENDING);
		return _unsortedList;
	}

	/**
	 * Get the photos from a folder.
	 * @param sourceDir the source folder.
	 * @param filter the filename filter.
	 * @param tempDirectory the temp folder.
	 * @return a list of {@link PhotoResource}.
	 * @since 15.07.00-SNAPSHOT
	 */
	public static List<PhotoResource> loadPhotoResources(File sourceDir, FilenameFilter filter, File tempDirectory)
	{
		File[] _content = getSortedFiles(sourceDir, filter);

		ImageLoader _loader = new ImageLoader();
		List<PhotoResource> _photoPool = _loader.loadPhotos(_content, tempDirectory);
		return _photoPool;
	}

}
