/**
 * 
 */
package com.sporniket.libre.memoirepersistante.print;

import java.awt.print.PageFormat;

/**
 * Specification of the page margin, to adjust a page format.
 * 
 * Each dimension is in 1/72 inches to be consistant with the java {@link PageFormat}.
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
public class PageFormatMarginSpecification
{
	/**
	 * Macro to create a fully specified margin specifications.
	 * 
	 * @param top top
	 * @param bottom bottom
	 * @param left left
	 * @param right right
	 * @return the margin specs.
	 * @since 15.07.00-SNAPSHOT
	 */
	public static PageFormatMarginSpecification createFullSpecification(double top, double bottom, double left, double right)
	{
		PageFormatMarginSpecification _result = new PageFormatMarginSpecification();
		_result.setTop(top);
		_result.setBottom(bottom);
		_result.setLeft(left);
		_result.setRight(right);
		return _result;
	}

	/**
	 * Macro to create a margin specs with the same margin on top and bottom, and another on left and right.
	 * 
	 * @param verticalMargin
	 *            top and bottom margin specification.
	 * @param horizontalMargin
	 *            left and right margin specification.
	 * @return the margin specs.
	 * @since 15.07.00-SNAPSHOT
	 */
	public static PageFormatMarginSpecification createSymetricSpecification(double verticalMargin, double horizontalMargin)
	{
		return createFullSpecification(verticalMargin, verticalMargin, horizontalMargin, horizontalMargin);
	}

	private double myBottom;

	private double myLeft;

	private double myRight;

	private double myTop;

	/**
	 * Get bottom.
	 * 
	 * @return the bottom
	 * @since 15.07.00-SNAPSHOT
	 */
	public double getBottom()
	{
		return myBottom;
	}

	/**
	 * Get left.
	 * 
	 * @return the left
	 * @since 15.07.00-SNAPSHOT
	 */
	public double getLeft()
	{
		return myLeft;
	}

	/**
	 * Get right.
	 * 
	 * @return the right
	 * @since 15.07.00-SNAPSHOT
	 */
	public double getRight()
	{
		return myRight;
	}

	/**
	 * Get top.
	 * 
	 * @return the top
	 * @since 15.07.00-SNAPSHOT
	 */
	public double getTop()
	{
		return myTop;
	}

	/**
	 * Change bottom.
	 * 
	 * @param bottom
	 *            the new value of bottom.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setBottom(double bottom)
	{
		myBottom = bottom;
	}

	/**
	 * Change left.
	 * 
	 * @param left
	 *            the new value of left.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setLeft(double left)
	{
		myLeft = left;
	}

	/**
	 * Change right.
	 * 
	 * @param right
	 *            the new value of right.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setRight(double right)
	{
		myRight = right;
	}

	/**
	 * Change top.
	 * 
	 * @param top
	 *            the new value of top.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setTop(double top)
	{
		myTop = top;
	}
}
