/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;

import com.sporniket.libre.memoirepersistante.print.PrintSetup;
import com.sporniket.libre.memoirepersistante.types.BasicUiTheme;
import com.sporniket.libre.memoirepersistante.types.JpegFilenameFilter;
import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.memoirepersistante.types.ThumbnailProvider;
import com.sporniket.libre.memoirepersistante.types.Utils;
import com.sporniket.libre.lang.SystemProperties;

/**
 * The main panel of PhotoBook creator, once a source directory has been choosen.
 * 
 * <p>
 * Can be run standalone.
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
 * <i>Memoire Persistante &#8211; app</i> is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
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
public class PhotoBookPanel extends JScrollPane
{
	private static class StandaloneAppShell
	{
		private final JFileChooser myChooser = new JFileChooser();

		private final PhotoBookPanel myInstance;

		private List<PhotoResource> myPhotos;

		private PrintSetup myPrintSetup = new PrintSetup();

		/**
		 * @param instance the panel.
		 * @since 15.07.00-SNAPSHOT
		 */
		public StandaloneAppShell(PhotoBookPanel instance)
		{
			super();
			myInstance = instance;
			JToolBar _toolbar = createToolbar();

			JFrame _frame = new JFrame(getClass().getSimpleName());
			_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			_frame.getContentPane().add(_toolbar, BorderLayout.NORTH);

			JScrollPane myThumbnailListPanelContainer = getInstance();
			myThumbnailListPanelContainer.getVerticalScrollBar().setUnitIncrement(32);
			_frame.getContentPane().add(myThumbnailListPanelContainer, BorderLayout.CENTER);
			_frame.setSize(750, 550);

			// setup file chooser
			getChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			_frame.setVisible(true);
		}

		/**
		 * @return the toolbar.
		 * @since 15.07.00-SNAPSHOT
		 */
		private JToolBar createToolbar()
		{
			JToolBar _toolbar = new JToolBar();
			_toolbar.setFloatable(false);
			_toolbar.setFocusable(false);
			_toolbar.add(new AbstractAction("Open photo directory...")
			{

				public void actionPerformed(ActionEvent e)
				{
					selectSourceDir();
				}
			});
			_toolbar.add(new AbstractAction("Print selection...")
			{

				public void actionPerformed(ActionEvent e)
				{
					printSelection();
				}
			});
			return _toolbar;
		}

		/**
		 * Get chooser.
		 * 
		 * @return the chooser
		 * @since 15.07.00-SNAPSHOT
		 */
		public JFileChooser getChooser()
		{
			return myChooser;
		}

		/**
		 * Get instance.
		 * 
		 * @return the instance
		 * @since 15.07.00-SNAPSHOT
		 */
		public PhotoBookPanel getInstance()
		{
			return myInstance;
		}

		private void printSelection()
		{
			if (myPhotos.isEmpty()) return;
			List<PhotoResource> _selection = new ArrayList<PhotoResource>(myPhotos.size());
			for (PhotoResource _photo : myPhotos)
			{
				if (_photo.getSelected()) _selection.add(_photo);
			}
			if (_selection.isEmpty()) return;
			myPrintSetup.setup(_selection, "PhotoBook dummy title");
		}

		private File selectSourceDir()
		{
			int _userChoice = getChooser().showDialog(getInstance(), "Select Source Directory");
			if (JFileChooser.APPROVE_OPTION == _userChoice)
			{
				List<PhotoResource> _photos = getInstance().openSourceDirectory(getChooser().getSelectedFile());
				myPhotos = (null != _photos) ? new ArrayList<PhotoResource>(_photos) : new ArrayList<PhotoResource>(0);
			}
			return null;
		}
	}
	
	/**
	 * @author dsporn
	 *
	 * @version 15.07.00-SNAPSHOT
	 * @since 15.07.00-SNAPSHOT
	 */
	private static class PanelSizeUdaterOnViewPortResize implements ComponentListener
	{
		private final JPanel myPanel ;

		/**
		 * @param panel the panel.
		 * @since 15.07.00-SNAPSHOT
		 */
		public PanelSizeUdaterOnViewPortResize(JPanel panel)
		{
			super();
			myPanel = panel;
		}

		/**
		 * Get panel.
		 * @return the panel
		 * @since 15.07.00-SNAPSHOT
		 */
		private JPanel getPanel()
		{
			return myPanel;
		}

		public void componentResized(ComponentEvent e)
		{
			// TODO Auto-generated method stub
			System.out.println("-->componentResized");
			updateThumbnailListPanelLayout(getPanel());
			System.out.println("<--");			
		}

		public void componentMoved(ComponentEvent e)
		{
			// do nothing special
		}

		public void componentShown(ComponentEvent e)
		{
			// do nothing special
		}

		public void componentHidden(ComponentEvent e)
		{
			// do nothing special
		}
	}

	private static final int LAYOUT__HGAP = 20;

	private static final int LAYOUT__VGAP = 35;

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private static final long serialVersionUID = -5969422165853705372L;

	/**
	 * @return the panel.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static JPanel createThumbnailListPanel()
	{
		JPanel _thumbnailListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, LAYOUT__HGAP, LAYOUT__VGAP));
		_thumbnailListPanel.setBorder(null);
		return _thumbnailListPanel;
	}

	public static void main(String[] args)
	{
		String _tempDirectoryPath = SystemProperties.Protected.getTempDirectory();
		System.out.println(_tempDirectoryPath);
		PhotoBookPanel _instance = new PhotoBookPanel(new File(_tempDirectoryPath));
		new StandaloneAppShell(_instance);
	}

	/**
	 * @param container the container.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static void updateThumbnailListPanelLayout(Component container)
	{
		JPanel _panel = (JPanel) container;
		FlowLayout _layout = (FlowLayout) _panel.getLayout();
		int _hgap = _layout.getHgap();
		int _vgap = _layout.getVgap();
		int _max = _panel.getComponentCount();
		Dimension _old = _panel.getPreferredSize();
		if (0 < _max)
		{
			Component _reference = _panel.getComponent(0);
			int _baseVariableWidth = _reference.getWidth() + _hgap;
			int _baseVariableHeight = _reference.getHeight() + _vgap;
			int _minWidth = _hgap + _baseVariableWidth;
			int _minHeight = _vgap + _baseVariableHeight;

			// set minimum size to fit the first component
			Dimension _newMinimumSize = new Dimension(_minWidth, _minHeight);
			_panel.setMinimumSize(_newMinimumSize);
			_panel.setSize(_newMinimumSize);

			// setup preferred size
			int _width = container.getParent().getWidth();
			if (_width < _minWidth)
			{
				_width = _minWidth;
			}
			int colsCount = _width / _baseVariableWidth;
			System.out.println("width = " + _width + " ; colsCount = " + colsCount);
			int _rowCount = (_max + colsCount - 1) / colsCount;
			Dimension _newPreferredSize = new Dimension(_hgap + _baseVariableWidth * colsCount, _vgap + _baseVariableHeight
					* _rowCount);
			if (_newPreferredSize.width != _old.width || _newPreferredSize.height != _old.height)
			{
				_panel.setPreferredSize(_newPreferredSize);
			}
		}
		_panel.revalidate();
	}

	private JPanel myThumbnailListPanel;

	private final ThumbnailProvider myThumbnailProvider;

	/**
	 * temp directory.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private File myWorkingDirectory;

	/**
	 * @param workingDirectory the working directory.
	 * @since 15.07.00-SNAPSHOT
	 */
	public PhotoBookPanel(File workingDirectory)
	{
		super(createThumbnailListPanel());
		if (!workingDirectory.exists() && !workingDirectory.mkdirs())
		{
			throw new RuntimeException("Cannot create working directory :" + workingDirectory.getAbsolutePath());
		}
		if (workingDirectory.exists() && !workingDirectory.isDirectory())
		{
			throw new RuntimeException("Working directory MUST be a directory if it already exists :"
					+ workingDirectory.getAbsolutePath());
		}
		myWorkingDirectory = workingDirectory;
		myThumbnailProvider = new ThumbnailProvider(myWorkingDirectory);
		getVerticalScrollBar().setUnitIncrement(32);

		myThumbnailListPanel = findContentContainer(this.getViewport());
		this.getViewport().addComponentListener(new PanelSizeUdaterOnViewPortResize(myThumbnailListPanel));
		// myCompositingPanel.add(_dataContainer, 0);
		// _dataContainer.setVisible(true);
		updateThumbnailListPanelLayout(myThumbnailListPanel);
	}

	/**
	 * @return the panel.
	 * @since 15.07.00-SNAPSHOT
	 */
	private JPanel findContentContainer(JViewport root)
	{
		JPanel _result = null;
		for (Component _component : root.getComponents())
		{
			System.out.println(_component.getClass().getName());
			if (_component instanceof JPanel)
			{
				_result = (JPanel) _component;
			}
		}
		return _result;
	}

	/**
	 * Get thumbnailProvider.
	 * 
	 * @return the thumbnailProvider
	 * @since 15.07.00-SNAPSHOT
	 */
	private ThumbnailProvider getThumbnailProvider()
	{
		return myThumbnailProvider;
	}
	
	public List<PhotoResource> openSourceDirectory(File sourceDirectory)
	{
		if (!sourceDirectory.exists())
		{
			throw new RuntimeException("Source directory MUST exists :" + sourceDirectory.getAbsolutePath());
		}
		if (sourceDirectory.exists() && !sourceDirectory.isDirectory())
		{
			throw new RuntimeException("Source directory MUST be a directory :" + sourceDirectory.getAbsolutePath());
		}
		List<PhotoResource> _photos = Utils.loadPhotoResources(sourceDirectory, JpegFilenameFilter.INSTANCE, myWorkingDirectory);
		myThumbnailListPanel.removeAll();
		for (Iterator<PhotoResource> _photoIterator = _photos.iterator(); _photoIterator.hasNext();)
		{
			PhotoResource _photo = _photoIterator.next();
			try
			{
				final PhotoResourcePanel _photoPanel = new PhotoResourcePanel(_photo, getThumbnailProvider(), BasicUiTheme.getInstance());
				myThumbnailListPanel.add(_photoPanel);
			}
			catch (IOException _exception)
			{
				System.out.println("Error while reading " + _photo.getSourceFile().getAbsolutePath() + " : "
						+ _exception.getLocalizedMessage());
				_photoIterator.remove();
			}
		}
		getViewport().remove(myThumbnailListPanel);
		getViewport().add(myThumbnailListPanel);
		updateThumbnailListPanelLayout(myThumbnailListPanel);
		return _photos;
	}
	
}
