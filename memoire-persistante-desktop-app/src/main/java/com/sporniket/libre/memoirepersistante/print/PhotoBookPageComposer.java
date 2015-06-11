/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sporniket.libre.memoirepersistante.print.PhotoBookPage.Specifications;
import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.images.core.metadata.Orientation;

/**
 * Class that organise a list of {@link PhotoResource} in a set of {@link PhotoBookPage}.
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
public class PhotoBookPageComposer implements Pageable
{
	private LandscapePicturesInPortraitPage myLandscapePicturesInPortraitPage;

	private PortraitPicturesInPortraitPage myPortraitPicturesInPortraitPage;

	/**
	 * Get portraitPicturesInPortraitPage.
	 * @return the portraitPicturesInPortraitPage
	 * @since 15.07.00-SNAPSHOT
	 */
	private PortraitPicturesInPortraitPage getPortraitPicturesInPortraitPage()
	{
		return myPortraitPicturesInPortraitPage;
	}

	private final Map<Integer, PhotoBookPage> myPageMap = new TreeMap<Integer, PhotoBookPage>();

	/**
	 * Get pageMap.
	 * @return the pageMap
	 * @since 15.07.00-SNAPSHOT
	 */
	private Map<Integer, PhotoBookPage> getPageMap()
	{
		return myPageMap;
	}

	private PageDecorator myPageDecorator;

	private PageFormat myPageFormat;

	private PhotoBookFormat myPhotoBookFormat;

	/**
	 * @param photoBookFormat
	 * @param pageDecorator
	 * @since 15.07.00-SNAPSHOT
	 */
	public PhotoBookPageComposer(PhotoBookFormat photoBookFormat, PageDecorator pageDecorator)
	{
		super();
		myPhotoBookFormat = photoBookFormat;
		myPageDecorator = pageDecorator;
		myLandscapePicturesInPortraitPage = new LandscapePicturesInPortraitPage(photoBookFormat, pageDecorator);
		myPortraitPicturesInPortraitPage = new PortraitPicturesInPortraitPage(photoBookFormat, pageDecorator);
	}

	/**
	 * Get landscapePicturesInPortraitPage.
	 * 
	 * @return the landscapePicturesInPortraitPage
	 * @since 15.07.00-SNAPSHOT
	 */
	private LandscapePicturesInPortraitPage getLandscapePicturesInPortraitPage()
	{
		return myLandscapePicturesInPortraitPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.print.Pageable#getNumberOfPages()
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public int getNumberOfPages()
	{
		return myPageCount;
	}

	/**
	 * Get pageDecorator.
	 * 
	 * @return the pageDecorator
	 * @since 15.07.00-SNAPSHOT
	 */
	private PageDecorator getPageDecorator()
	{
		return myPageDecorator;
	}

	/**
	 * Get pageFormat.
	 * 
	 * @return the pageFormat
	 * @since 15.07.00-SNAPSHOT
	 */
	private PageFormat getPageFormat()
	{
		return myPageFormat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.print.Pageable#getPageFormat(int)
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException
	{
		// FIXME devise a system of pageformat provider to choose between several pageFormat arrangement (simple, asymetric, etc...)
		return getPageFormat();
	}

	/**
	 * Get photoBookFormat.
	 * 
	 * @return the photoBookFormat
	 * @since 15.07.00-SNAPSHOT
	 */
	private PhotoBookFormat getPhotoBookFormat()
	{
		return myPhotoBookFormat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.print.Pageable#getPrintable(int)
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException
	{
		return getPageMap().get(pageIndex);
	}

	/**
	 * Change pageFormat.
	 * 
	 * @param pageFormat
	 *            the new value of pageFormat.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setPageFormat(PageFormat pageFormat)
	{
		myPageFormat = pageFormat;
	}

	private int myPageCount = 0 ;
	public void compose(List<PhotoResource> photos)
	{
		//clear the composer
		clear();
		
		List<PhotoResource> _pool = new ArrayList<PhotoResource>(photos.size()); // clone the list to consume
		_pool.addAll(photos);
		
		int _pageIndex = 0;
		while ( !_pool.isEmpty() )
		{
			List<PhotoResource> _subset = new ArrayList<PhotoResource>(2);
			Specifications _specs = new Specifications(_pageIndex, _subset);
			Orientation _setOrientation = null;
			for (Iterator<PhotoResource> _photoIterator = _pool.iterator(); _photoIterator.hasNext();)
			{
				PhotoResource _photo = _photoIterator.next();
				if (_subset.isEmpty())
				{
					_subset.add(_photo);
					_photoIterator.remove();
					_setOrientation = _photo.getOrientation();
					if (_setOrientation.isQuarterTurnRequired())
					{
						// FIXME create Portrait Page
						getPortraitPicturesInPortraitPage().addPage(_specs);
						getPageMap().put(_pageIndex, getPortraitPicturesInPortraitPage());
						_specs = null ;//not needed anymore, allow detecting remaining specs
						break;
					}
					continue; //time to fill next photo
				}
				if (!_photo.getOrientation().isQuarterTurnRequired())
				{
					_subset.add(_photo);
					_photoIterator.remove();
					getLandscapePicturesInPortraitPage().addPage(_specs);
					getPageMap().put(_pageIndex, getLandscapePicturesInPortraitPage());
					_specs = null ;//not needed anymore, allow detecting remaining specs
					break;
				}
			}
			if (null != _specs)
			{
				//the last specs is partially filled, but there are no more photos.
				if (!_setOrientation.isQuarterTurnRequired())
				{
					getLandscapePicturesInPortraitPage().addPage(_specs);
					getPageMap().put(_pageIndex, getLandscapePicturesInPortraitPage());
				}
				
			}
			_pageIndex++ ;
		}
		myPageCount = _pageIndex;
		getPageDecorator().setPageCount(_pageIndex);

	}

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private void clear()
	{
		getPageMap().clear();
		getLandscapePicturesInPortraitPage().clear();
	}

}
