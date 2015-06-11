/**
 * 
 */
package com.sporniket.libre.memoirepersistante;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.sporniket.libre.lang.SystemProperties;
import com.sporniket.libre.memoirepersistante.print.PrintSetup;
import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.memoirepersistante.ui.PhotoBookPanel;

/**
 * Photobook creator.
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
public class MemoirePersistanteApplication
{
	/**
	 * Class for storing preferences.
	 * @author David SPORN
	 *
	 * @version 15.07.00-SNAPSHOT
	 * @since 15.07.00-SNAPSHOT
	 */
	private static class Preferences
	{
		/**
		 * Location of the last opened directory.
		 * @since 15.07.00-SNAPSHOT
		 */
		private String myLastOpenedDir ;
		
		/**
		 * List of directories that constitute the user phototheque.
		 * @since 15.07.00-SNAPSHOT
		 */
		private List<String> myPhotoRepositories = new ArrayList<String>() ;
		
		/**
		 * List of the locations of the repositories backups.
		 * @since 15.07.00-SNAPSHOT
		 */
		private List<String> myRepositoriesBackups = new ArrayList<String>();
		
		/**
		 * Path to the temp directory where the thumbnails will be stored.
		 * @since 15.07.00-SNAPSHOT
		 */
		private String myTempDirLocation ;

		/**
		 * Get lastOpenedDir.
		 * @return the lastOpenedDir
		 * @since 15.07.00-SNAPSHOT
		 */
		public String getLastOpenedDir()
		{
			return myLastOpenedDir;
		}

		/**
		 * Change lastOpenedDir.
		 * @param lastOpenedDir the lastOpenedDir to set
		 * @since 15.07.00-SNAPSHOT
		 */
		public void setLastOpenedDir(String lastOpenedDir)
		{
			myLastOpenedDir = lastOpenedDir;
		}

		/**
		 * Get tempDirLocation.
		 * @return the tempDirLocation
		 * @since 15.07.00-SNAPSHOT
		 */
		public String getTempDirLocation()
		{
			return myTempDirLocation;
		}

		/**
		 * Change tempDirLocation.
		 * @param tempDirLocation the tempDirLocation to set
		 * @since 15.07.00-SNAPSHOT
		 */
		public void setTempDirLocation(String tempDirLocation)
		{
			myTempDirLocation = tempDirLocation;
		}

		/**
		 * Get photoRepositories.
		 * @return the photoRepositories
		 * @since 15.07.00-SNAPSHOT
		 */
		public List<String> getPhotoRepositories()
		{
			return myPhotoRepositories;
		}

		/**
		 * Get repositoriesBackups.
		 * @return the repositoriesBackups
		 * @since 15.07.00-SNAPSHOT
		 */
		public List<String> getRepositoriesBackups()
		{
			return myRepositoriesBackups;
		}
		
	}
	
	private final Preferences myPreferences = new Preferences();
	/**
	 * Get preferences.
	 * @return the preferences
	 * @since 15.07.00-SNAPSHOT
	 */
	private Preferences getPreferences()
	{
		return myPreferences;
	}

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	public MemoirePersistanteApplication()
	{
		super();
		
		readSetup();
		
		myInstance = new PhotoBookPanel(new File(getPreferences().getTempDirLocation()));
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
	 * Open preferences file, and reload saved preferences and data.
	 * @since 15.07.00-SNAPSHOT
	 */
	private void readSetup()
	{
		//FIXME read a preference files
		String _tempDirLocation = SystemProperties.Private.getUserHome()+File.separator+".MemoirePersistante"+File.separator+"thumbnails";
		System.out.println("_tempDirLocation = "+_tempDirLocation);
		getPreferences().setTempDirLocation(_tempDirLocation);
	}

	public static void main(String[] args)
	{
		MemoirePersistanteApplication _instance = new MemoirePersistanteApplication();
	}
	private PhotoBookPanel myInstance;

	private final JFileChooser myChooser = new JFileChooser();

	private List<PhotoResource> myPhotos;

	private PrintSetup myPrintSetup = new PrintSetup();
	
	private JTextField myTitle = new JTextField(40);

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

	/**
	 * @return
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
		_toolbar.add(Box.createHorizontalStrut(24));
		_toolbar.add(new JLabel("Photobook title : "));
		_toolbar.add(myTitle);
		_toolbar.add(Box.createHorizontalStrut(24));
		_toolbar.add(new AbstractAction("Print selection...")
		{

			public void actionPerformed(ActionEvent e)
			{
				printSelection();
			}
		});
		return _toolbar;
	}

	private File selectSourceDir()
	{
		int _userChoice = getChooser().showDialog(getInstance(), "Select Source Directory");
		if (JFileChooser.APPROVE_OPTION == _userChoice)
		{
			myTitle.setText(getChooser().getSelectedFile().getAbsolutePath());
			List<PhotoResource> _photos = getInstance().openSourceDirectory(getChooser().getSelectedFile());
			myPhotos = (null != _photos) ? new ArrayList<PhotoResource>(_photos) : new ArrayList<PhotoResource>(0);
		}
		return null;
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
		myPrintSetup.setup(_selection, myTitle.getText());
	}

}
