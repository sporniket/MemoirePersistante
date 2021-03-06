package com.sporniket.libre.memoirepersistante.types;

import java.io.File;
import java.io.FilenameFilter;

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
public class JpegFilenameFilter implements FilenameFilter {
	
	public static final JpegFilenameFilter INSTANCE = new JpegFilenameFilter();

	private static String EXTENSION__JPG = ".jpg";
	private static String EXTENSION__JPEG = ".jpeg";
	
	public boolean accept(File dir, String name) {
		String _normalizedName = name.toLowerCase();
		// TODO Auto-generated method stub
		return _normalizedName.endsWith(EXTENSION__JPG) || _normalizedName.endsWith(EXTENSION__JPEG);
	}
	
}