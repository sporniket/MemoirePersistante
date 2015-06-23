/**
 * 
 */
package com.sporniket.libre.memoirepersistante;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
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
public class MemoirePersistanteApplication
{
	/**
	 * Class for storing preferences.
	 * 
	 * @author David SPORN
	 *
	 * @version 15.07.00-SNAPSHOT
	 * @since 15.07.00-SNAPSHOT
	 */
	private static class Preferences
	{
		/**
		 * Location of the last opened directory.
		 * 
		 * @since 15.07.00-SNAPSHOT
		 */
		private String myLastOpenedDir;

		/**
		 * List of directories that constitute the user phototheque.
		 * 
		 * @since 15.07.00-SNAPSHOT
		 */
		private List<String> myPhotoRepositories = new ArrayList<String>();

		/**
		 * List of the locations of the repositories backups.
		 * 
		 * @since 15.07.00-SNAPSHOT
		 */
		private List<String> myRepositoriesBackups = new ArrayList<String>();

		/**
		 * Path to the temp directory where the thumbnails will be stored.
		 * 
		 * @since 15.07.00-SNAPSHOT
		 */
		private String myTempDirLocation;

		/**
		 * Get lastOpenedDir.
		 * 
		 * @return the lastOpenedDir
		 * @since 15.07.00-SNAPSHOT
		 */
		public String getLastOpenedDir()
		{
			return myLastOpenedDir;
		}

		/**
		 * Change lastOpenedDir.
		 * 
		 * @param lastOpenedDir
		 *            the lastOpenedDir to set
		 * @since 15.07.00-SNAPSHOT
		 */
		public void setLastOpenedDir(String lastOpenedDir)
		{
			myLastOpenedDir = lastOpenedDir;
		}

		/**
		 * Get tempDirLocation.
		 * 
		 * @return the tempDirLocation
		 * @since 15.07.00-SNAPSHOT
		 */
		public String getTempDirLocation()
		{
			return myTempDirLocation;
		}

		/**
		 * Change tempDirLocation.
		 * 
		 * @param tempDirLocation
		 *            the tempDirLocation to set
		 * @since 15.07.00-SNAPSHOT
		 */
		public void setTempDirLocation(String tempDirLocation)
		{
			myTempDirLocation = tempDirLocation;
		}

		/**
		 * Get photoRepositories.
		 * 
		 * @return the photoRepositories
		 * @since 15.07.00-SNAPSHOT
		 */
		public List<String> getPhotoRepositories()
		{
			return myPhotoRepositories;
		}

		/**
		 * Get repositoriesBackups.
		 * 
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
	 * 
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
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private void readSetup()
	{
		// FIXME read a preference files
		String _tempDirLocation = SystemProperties.Private.getUserHome() + File.separator + ".MemoirePersistante" + File.separator
				+ "thumbnails";
		System.out.println("_tempDirLocation = " + _tempDirLocation);
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

	private File myCurrentDirectory = null;

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
		_toolbar.add(new AbstractAction("Save album")
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveAlbum();

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
		_toolbar.add(new AbstractAction("Save selection...")
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveSelection();

			}
		});

		return _toolbar;
	}

	private File selectSourceDir()
	{
		int _userChoice = getChooser().showDialog(getInstance(), "Select Source Directory");
		if (JFileChooser.APPROVE_OPTION == _userChoice)
		{
			final File _selectedFile = getChooser().getSelectedFile();
			setCurrentDirectory(_selectedFile);
			myTitle.setText(_selectedFile.getAbsolutePath());
			List<PhotoResource> _photos = getInstance().openSourceDirectory(_selectedFile);
			myPhotos = (null != _photos) ? new ArrayList<PhotoResource>(_photos) : new ArrayList<PhotoResource>(0);
		}
		return null;
	}

	private void printSelection()
	{
		if (myPhotos.isEmpty()) return;
		List<PhotoResource> _selection = getSelection();
		if (_selection.isEmpty()) return;
		myPrintSetup.setup(_selection, myTitle.getText());
	}

	/**
	 * @return
	 */
	private List<PhotoResource> getSelection()
	{
		List<PhotoResource> _selection = new ArrayList<PhotoResource>(myPhotos.size());
		for (PhotoResource _photo : myPhotos)
		{
			if (_photo.getSelected()) _selection.add(_photo);
		}
		return _selection;
	}

	private void saveAlbum()
	{
		saveAlbum(new File(myCurrentDirectory, "0-index.rdf"), myTitle.getText(), myPhotos);
	}

	private void saveSelection()
	{
		List<PhotoResource> _selection = getSelection();
		saveAlbum(new File(myCurrentDirectory, "9-photobook.rdf"), myTitle.getText(), _selection);
	}

	private void saveAlbum(File target, String title, List<PhotoResource> photos)
	{
		// 1-Create RDF model
		Model _albumModel = saveAlbum__createRdfModel(title, photos);

		// 2-Export to XML file
		saveAlbum__writeRdfFile(target, _albumModel);
	}

	/**
	 * @param target
	 *            the file to write (will be overwritten).
	 * @param albumRdfModel
	 *            the rdf model of the album.
	 */
	private void saveAlbum__writeRdfFile(File target, Model albumRdfModel)
	{
		FileOutputStream _out = null;
		try
		{
			_out = new FileOutputStream(target);
			albumRdfModel.write(_out);
		}
		catch (FileNotFoundException _exception)
		{
			// TODO Auto-generated catch block
			_exception.printStackTrace();
		}
		finally
		{
			if (null != _out)
			{
				try
				{
					_out.close();
				}
				catch (IOException _exception)
				{
					// TODO Auto-generated catch block
					_exception.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param title
	 *            the title of the album.
	 * @param photos
	 *            the list of photos.
	 * @return
	 */
	private Model saveAlbum__createRdfModel(String title, List<PhotoResource> photos)
	{
		Model _albumModel = ModelFactory.createDefaultModel();
		
		//create property set
		//FIXME usual metadata would be better using Dublin Core
		String nodeUri = "http://shema.sporniket.com/node/";
		String metadataUri = "http://shema.sporniket.com/metadata/";
		String metadataDcUri = "http://purl.org/dc/terms/";
		_albumModel.setNsPrefix("dc", metadataDcUri);
		_albumModel.setNsPrefix("spn-met", metadataUri);
		Property propertyTitle = _albumModel.createProperty(metadataDcUri, "title");
		Property propertyPhotos = _albumModel.createProperty(metadataUri, "photos");
		Property propertyPhoto = _albumModel.createProperty(metadataUri, "photo");
		Property propertyDescription = _albumModel.createProperty(metadataDcUri, "description");
		
		//_albumModel.

		// the root node
		Resource _albumResource = _albumModel.createResource(nodeUri+".");
		Literal _title = _albumModel.createLiteral(title);
		_albumResource.addProperty(propertyTitle, _title);
		Bag _photos = _albumModel.createBag(propertyPhotos.getURI());
		_albumResource.addProperty(propertyPhotos, _photos);

		// the photos nodes
		for (PhotoResource _photo : photos)
		{
			Resource _photoResource = _albumModel.createResource(nodeUri+_photo.getSourceFile().getName());
			_photos.addProperty(propertyPhoto, _photoResource);
			if(null != _photo.getDescription())
			{
				Literal _description = _albumModel.createLiteral(_photo.getDescription());
				_photoResource.addProperty(propertyDescription, _description);
			}
		}
		return _albumModel;
	}

	/**
	 * @return the currentDirectory
	 */
	protected File getCurrentDirectory()
	{
		return myCurrentDirectory;
	}

	/**
	 * @param currentDirectory
	 *            the currentDirectory to set
	 */
	protected void setCurrentDirectory(File currentDirectory)
	{
		myCurrentDirectory = currentDirectory;
	}

}
