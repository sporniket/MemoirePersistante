/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.memoirepersistante.types.ThumbnailProvider;

/**
 * Preview panel to add to the UI.
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
public class PhotoResourcePreview extends JPanel
{

	/**
	 * Serial version id.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static final long serialVersionUID = 4549772059261179664L;

	public PhotoResourcePreview(PhotoResource data, ThumbnailProvider thumbnailPool, UiTheme uiTheme) throws IOException
	{
		// TODO Auto-generated constructor stub
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(new PhotoResourcePreviewBody(data, thumbnailPool, uiTheme), BorderLayout.CENTER);
	}

}
