package com.sporniket.libre.memoirepersistante.print;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

import javax.print.PrintException;

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
public class PageFormatFactory
{
	/**
	 * After applying margin, the remaining area MUST be at least 1 inch in each dimension.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private static final double MINIMAL_IMAGEABLE_SIZE = 72;

	/**
	 * @param paper
	 *            The reference paper (dimension), naturally oriented as portrait.
	 * @param orientation orientation
	 * @param marginSpecifications marginSpecifications.
	 * @return the page format.		
	 * @throws PrintException
	 *             if the mangin specification is not compatible with the paper.
	 * @since 15.07.00-SNAPSHOT
	 */
	public static PageFormat createStandardFormat(Paper paper, JavaPrintOrientation orientation,
			PageFormatMarginSpecification marginSpecifications) throws PrintException
	{
		PageFormat _result = new PageFormat();
		applyMarginSpecsToPaper(paper, orientation, marginSpecifications);
		_result.setPaper(paper);
		_result.setOrientation(orientation.getJavaPrintValue());

		return _result;
	}

	private static void applyMarginSpecsToPaper(Paper paper, JavaPrintOrientation orientation,
			PageFormatMarginSpecification marginSpecifications) throws PrintException
	{
		switch (orientation)
		{
			case LANDSCAPE:
				applyMarginSpecsToPaper__landscape(paper, marginSpecifications);
				break;
			case REVERSE_LANDSCAPE:
				applyMarginSpecsToPaper__landscapeReverse(paper, marginSpecifications);
				break;
			default:
				applyMarginSpecsToPaper__portrait(paper, marginSpecifications);
		}

	}

	/**
	 * @param paper
	 *            the paper whose imageable area will be adjusted using the margin specifications.
	 * @param marginSpecifications
	 *            margin specification to compute the imageable area of the paper.
	 * @throws PrintException
	 *             if the paper is incompatible with the margin specifications.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static void applyMarginSpecsToPaper__landscapeReverse(Paper paper, PageFormatMarginSpecification marginSpecifications)
			throws PrintException
	{
		double _maxX = paper.getHeight();
		double _maxY = paper.getWidth();
		double _marginTotalHorizontal = marginSpecifications.getLeft() + marginSpecifications.getRight();
		double _marginTotalVertical = marginSpecifications.getTop() + marginSpecifications.getBottom();
		double _sizeX = _maxX - _marginTotalHorizontal;
		double _sizeY = _maxY - _marginTotalVertical;
		StringBuilder message = new StringBuilder();
		if (_sizeX < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable width too small : " + _sizeX + " ; ");
		}
		if (_sizeY < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable height too small : " + _sizeY + " ; ");
		}
		if (message.length() > 0)
		{
			throw new PrintException(message.toString());
		}
		paper.setImageableArea(marginSpecifications.getTop(), marginSpecifications.getLeft(), _sizeY, _sizeX);

	}

	/**
	 * @param paper
	 *            the paper whose imageable area will be adjusted using the margin specifications.
	 * @param marginSpecifications
	 *            margin specification to compute the imageable area of the paper.
	 * @throws PrintException
	 *             if the paper is incompatible with the margin specifications.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static void applyMarginSpecsToPaper__landscape(Paper paper, PageFormatMarginSpecification marginSpecifications)
			throws PrintException
	{
		double _maxX = paper.getHeight();
		double _maxY = paper.getWidth();
		double _marginTotalHorizontal = marginSpecifications.getLeft() + marginSpecifications.getRight();
		double _marginTotalVertical = marginSpecifications.getTop() + marginSpecifications.getBottom();
		double _sizeX = _maxX - _marginTotalHorizontal;
		double _sizeY = _maxY - _marginTotalVertical;
		StringBuilder message = new StringBuilder();
		if (_sizeX < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable width too small : " + _sizeX + " ; ");
		}
		if (_sizeY < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable height too small : " + _sizeY + " ; ");
		}
		if (message.length() > 0)
		{
			throw new PrintException(message.toString());
		}
		paper.setImageableArea(marginSpecifications.getTop(), marginSpecifications.getRight(), _sizeY, _sizeX);

	}

	/**
	 * @param paper
	 *            the paper whose imageable area will be adjusted using the margin specifications.
	 * @param marginSpecifications
	 *            margin specification to compute the imageable area of the paper.
	 * @throws PrintException
	 *             if the paper is incompatible with the margin specifications.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static void applyMarginSpecsToPaper__portrait(Paper paper, PageFormatMarginSpecification marginSpecifications)
			throws PrintException
	{
		double _maxX = paper.getWidth();
		double _maxY = paper.getHeight();
		double _marginTotalHorizontal = marginSpecifications.getLeft() + marginSpecifications.getRight();
		double _marginTotalVertical = marginSpecifications.getTop() + marginSpecifications.getBottom();
		double _sizeX = _maxX - _marginTotalHorizontal;
		double _sizeY = _maxY - _marginTotalVertical;
		StringBuilder message = new StringBuilder();
		if (_sizeX < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable width too small : " + _sizeX + " ; ");
		}
		if (_sizeY < MINIMAL_IMAGEABLE_SIZE)
		{
			message.append("Imageable height too small : " + _sizeY + " ; ");
		}
		if (message.length() > 0)
		{
			throw new PrintException(message.toString());
		}
		paper.setImageableArea(marginSpecifications.getLeft(), marginSpecifications.getTop(), _sizeX, _sizeY);
	}
}
