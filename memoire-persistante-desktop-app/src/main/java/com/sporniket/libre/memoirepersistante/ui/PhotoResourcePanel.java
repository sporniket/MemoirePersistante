/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

import com.sporniket.libre.memoirepersistante.types.PhotoResource;
import com.sporniket.libre.memoirepersistante.types.ThumbnailProvider;
import com.sporniket.libre.lang.message.BehaviourOnMissingMessage;
import com.sporniket.libre.lang.message.MessageProviderInterface;
import com.sporniket.libre.lang.message.ResourceBundleMessageProvider;
import com.sporniket.libre.lang.string.StringTools;
import com.sporniket.libre.lang.url.ClassLoaderUrlProvider;
import com.sporniket.libre.lang.url.UrlProvider;
import com.sporniket.libre.ui.action.AdaptedAction;
import com.sporniket.libre.ui.icon.IconProvider;
import com.sporniket.libre.ui.icon.IconProviderFromUrl;
import com.sporniket.libre.ui.swing.ComponentFactory;

/**
 * Panel displaying a thumbnail of a PhotoResource.
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
public class PhotoResourcePanel extends JPanel implements TableCellRenderer
{

	private static final int LENGTH__TRONCATED_LABEL = 30;

	private static final int LENGTH__MAX_LABEL_LENGTH = 35;

	private static final IconProvider<URL> ICON_PROVIDER = new IconProviderFromUrl();

	/**
	 * Serial uid.
	 */
	private static final long serialVersionUID = -4889626105660602116L;

	private static final int PADDING = com.sporniket.libre.memoirepersistante.ui.ComponentFactory.PADDING;

	private static final MessageProviderInterface MESSAGE_PROVIDER = new ResourceBundleMessageProvider(
			PhotoResourcePanel.class.getName(), new BehaviourOnMissingMessage.ReturnNull());

	private static final UrlProvider URL_PROVIDER__ICONS = new ClassLoaderUrlProvider(PhotoResourcePanel.class);

	private JComponent myViewAnnotationPanel;

	private JComponent myEditAnnotationPanel;

	private String myTags;

	private String myAudience;

	private JLabel myDescriptionLabel = new JLabel();

	private JLabel myTagsLabel = new JLabel();

	private JLabel myAudienceLabel = new JLabel();

	private final PhotoResource myPhotoResource;

	/**
	 * Get photoResource.
	 * 
	 * @return the photoResource
	 * @since 15.07.00-SNAPSHOT
	 */
	public PhotoResource getPhotoResource()
	{
		return myPhotoResource;
	}

	/**
	 * Changed on one toggle to know where to apply the edit.
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private JLabel myTargetLabel;

	private EditImageDescriptionUi myEditImageDescriptionUi;

	/**
	 * Get editImageDescriptionUi.
	 * 
	 * @return the editImageDescriptionUi
	 * @since 15.07.00-SNAPSHOT
	 */
	private EditImageDescriptionUi getEditImageDescriptionUi()
	{
		return myEditImageDescriptionUi;
	}

	/**
	 * Get selected.
	 * 
	 * @return the selected
	 * @since 15.07.00-SNAPSHOT
	 */
	public boolean isSelected()
	{
		return getPhotoResource().getSelected();
	}

	/**
	 * Change selected.
	 * 
	 * @param selected
	 *            the new value of selected.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setSelected(boolean selected)
	{
		getPhotoResource().setSelected(selected);
	}

	public PhotoResourcePanel(PhotoResource data, ThumbnailProvider thumbnailPool, UiTheme uiTheme) throws IOException
	{
		super();
		myPhotoResource = data;
		// settings
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// sub components
		JPanel _filenameLine = ComponentFactory.createPanelWithHorizontalLayout();
		_filenameLine.add(Box.createHorizontalGlue());
		_filenameLine.add(new JLabel(data.getSourceFile().getName()));
		_filenameLine.add(Box.createHorizontalGlue());
		add(_filenameLine);
		add(Box.createVerticalStrut(PADDING));

		JComponent _toolbar = createToolbar();
		add(_toolbar);

		PhotoResourcePreview _preview = createPreview(data, thumbnailPool, uiTheme);
		add(_preview);

		myViewAnnotationPanel = createAnnotationPanel();
		// add(new JTable(new PhotoResourceInformationDataSheet(data)));
		add(myViewAnnotationPanel);

		formatDescription();
		formatTags();
		formatAudience();

		myEditAnnotationPanel = createEditionPanel();
	}

	private void formatAudience()
	{
		if (StringTools.isEmptyString(myAudience))
		{
			myAudienceLabel.setText("Edit audience...");
		}
		else if (myAudience.length() > LENGTH__MAX_LABEL_LENGTH)
		{
			myAudienceLabel.setText(myAudience.substring(0, LENGTH__TRONCATED_LABEL) + "...");
		}
		else
		{
			myAudienceLabel.setText(myAudience);
		}
		invalidate();
	}

	private void formatTags()
	{
		if (StringTools.isEmptyString(myTags))
		{
			myTagsLabel.setText("Edit tags...");
		}
		else if (myTags.length() > LENGTH__MAX_LABEL_LENGTH)
		{
			myTagsLabel.setText(myTags.substring(0, LENGTH__TRONCATED_LABEL) + "...");
		}
		else
		{
			myTagsLabel.setText(myTags);
		}
		invalidate();
	}

	private void formatDescription()
	{
		if (StringTools.isEmptyString(getPhotoResource().getDescription()))
		{
			myDescriptionLabel.setText("Edit Description...");
		}
		else if (getPhotoResource().getDescription().length() > LENGTH__MAX_LABEL_LENGTH)
		{
			myDescriptionLabel.setText(getPhotoResource().getDescription().substring(0, LENGTH__TRONCATED_LABEL) + "...");
		}
		else
		{
			myDescriptionLabel.setText(getPhotoResource().getDescription());
		}
		invalidate();
	}

	private JComponent createAnnotationPanel()
	{
		JPanel _panel = ComponentFactory.createPanelWithVerticalLayout();

		_panel.add(createAnnotationLine("editDescription", myDescriptionLabel));
		_panel.add(createAnnotationLine("editTags", myTagsLabel));
		_panel.add(createAnnotationLine("editAudience", myAudienceLabel));

		return _panel;
	}

	private JComponent createEditionPanel()
	{
		myEditImageDescriptionUi = new EditImageDescriptionUi(myViewAnnotationPanel.getMinimumSize());
		// JPanel _panel = ComponentFactory.createPanelWithVerticalLayout();
		// _panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
		// BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)));
		//
		// JPanel _titleLine = ComponentFactory.createPanelWithHorizontalLayout();
		// _titleLine.add(new JLabel("Edit xxx"));
		// _titleLine.add(Box.createHorizontalGlue());
		// JButton _button = new JButton("Ok");
		// _button.setContentAreaFilled(false);
		// _button.addActionListener(
		getEditImageDescriptionUi().addOkActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				commitEdit();
			}
		});

		return getEditImageDescriptionUi().getPanel();
	}

	/**
	 * @param actionName
	 * @param descriptionLabel
	 * @return the panel.
	 * @since 15.07.00-SNAPSHOT
	 */
	private JPanel createAnnotationLine(String actionName, JLabel descriptionLabel)
	{
		JPanel _lineDesc = ComponentFactory.createPanelWithHorizontalLayout();
		_lineDesc.add(createPanelButton(actionName));
		_lineDesc.add(Box.createHorizontalStrut(PADDING));
		_lineDesc.add(descriptionLabel);
		_lineDesc.add(Box.createHorizontalGlue());
		return _lineDesc;
	}

	/**
	 * @param data
	 * @param thumbnailPool
	 * @param uiTheme
	 * @return the preview.
	 * @throws IOException
	 * @since 15.07.00-SNAPSHOT
	 */
	private PhotoResourcePreview createPreview(PhotoResource data, ThumbnailProvider thumbnailPool, UiTheme uiTheme)
			throws IOException
	{
		PhotoResourcePreview _preview = new PhotoResourcePreview(data, thumbnailPool, uiTheme);
		return _preview;
	}
	/**
	 * @return the toolbar.
	 * @since 15.07.00-SNAPSHOT
	 */
	private JComponent createToolbar()
	{
		JPanel _toolbar = new JPanel();
		_toolbar.setLayout(new BoxLayout(_toolbar, BoxLayout.X_AXIS));
		JCheckBox _selected = new JCheckBox(new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				setSelected(((JCheckBox) e.getSource()).isSelected());
			}
		});
		_selected.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		_toolbar.add(_selected);
		_toolbar.add(Box.createHorizontalStrut(16));
		_toolbar.add(createPanelButton("rotateCcw"));
		_toolbar.add(Box.createHorizontalStrut(PADDING));
		_toolbar.add(createPanelButton("rotateCw"));
		_toolbar.add(Box.createHorizontalStrut(16));
		_toolbar.add(createPanelButton("crop"));
		_toolbar.add(Box.createHorizontalGlue());
		_toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, PADDING, 0, getBackground()));
		return _toolbar;
	}

	/**
	 * @param actionName
	 * @return the button.
	 * @since 15.07.00-SNAPSHOT
	 */
	private JButton createPanelButton(String actionName)
	{
		AdaptedAction<URL> _action = (AdaptedAction<URL>) com.sporniket.libre.ui.action.Utils
				.retrieveActionDefinitionFromMessageProvider(new AdaptedAction<URL>(null, MESSAGE_PROVIDER, ICON_PROVIDER),
						MESSAGE_PROVIDER, actionName, getLocale(), this, URL_PROVIDER__ICONS);
		JButton _button = new JButton(_action);
		_button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		_button.setContentAreaFilled(false);
		_button.setRolloverEnabled(true);
		return _button;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int column)
	{
		return this;
	}

	public void rotatePictureClockwise()
	{
		// TODO
		System.out.println("rotatePictureClockwise");
	}

	public void rotatePictureCounterClockwise()
	{
		// TODO
		System.out.println("rotatePictureCounterClockwise");
	}

	public void toggleCrop()
	{
		// TODO
		System.out.println("toggleCrop");
	}

	public void editDescription()
	{
		// TODO
		System.out.println("editDescription");
		myEditImageDescriptionUi.setDescription(getPhotoResource().getDescription());
		myTargetLabel = myDescriptionLabel;
		switchToAnnotationEdition();
	}

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private void switchToAnnotationEdition()
	{
		// if (null != getRootPane())
		{
			if (myEditAnnotationPanel.getParent() != getRootPane().getLayeredPane())
			{
				getRootPane().getLayeredPane().add(myEditAnnotationPanel, JLayeredPane.POPUP_LAYER);
			}
			Point _positionInPanel = computePositionInReferenceComponent(myViewAnnotationPanel, this);

			JViewport _viewport = findViewport(this);
			if (null == _viewport)
			{
				int _xBound = _positionInPanel.x;
				int _yBound = _positionInPanel.y;
				System.out.println("   --> position : " + _xBound + " , " + _yBound);

				moveEditAnnotationPanelTo(_xBound, _yBound);
			}
			else
			{
				Point _offset = _viewport.getViewPosition();
				Point _rootOffset = computePositionInReferenceComponent(_viewport, getRootPane().getContentPane());
				int _xBound = _positionInPanel.x - _offset.x + _rootOffset.x;
				int _yBound = _positionInPanel.y - _offset.y + _rootOffset.y;
				System.out.println("   --> position : " + _xBound + " , " + _yBound);

				moveEditAnnotationPanelTo(_xBound, _yBound);
			}
			myEditAnnotationPanel.setVisible(true);
			myEditAnnotationPanel.revalidate();
			getRootPane().repaint();

		}
	}

	/**
	 * Move the edit annotation panel, and adjust its size.
	 * 
	 * @param x new x position.
	 * @param y new y position.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void moveEditAnnotationPanelTo(int x, int y)
	{
		Rectangle _viewAnnotationBounds = myViewAnnotationPanel.getBounds();
		Dimension _preferredSize = myEditAnnotationPanel.getPreferredSize();
		myEditAnnotationPanel.setBounds(x, y, _viewAnnotationBounds.width, _preferredSize.height);
	}

	private Point computePositionInReferenceComponent(Component component, Container referenceComponent)
	{
		// TODO Auto-generated method stub
		Point _local = component.getLocation();
		if (component == referenceComponent)
		{
			return _local;
		}
		else if (null != component.getParent())
		{
			Point _parent = computePositionInReferenceComponent(component.getParent(), referenceComponent);
			return new Point(_local.x + _parent.x, _local.y + _parent.y);
		}
		else
		{
			return _local;
		}
	}

	private JViewport findViewport(Component component)
	{
		// TODO Auto-generated method stub
		if (component instanceof JViewport)
		{
			return (JViewport) component;
		}
		else if (null != component)
		{
			return findViewport(component.getParent());
		}
		else
		{
			return null;
		}
	}

	public void editTags()
	{
		// TODO
		System.out.println("editTags");
		// myEditionArea.setText(myTags);
		myTargetLabel = myTagsLabel;
		switchToAnnotationEdition();
	}

	public void editAudience()
	{
		// TODO
		System.out.println("editAudience");
		// myEditionArea.setText(myAudience);
		myTargetLabel = myAudienceLabel;
		switchToAnnotationEdition();
	}

	public void commitEdit()
	{
		if (myTargetLabel == myDescriptionLabel)
		{
			getPhotoResource().setDescription(myEditImageDescriptionUi.getDescription());
			formatDescription();
		}
		else if (myTargetLabel == myTagsLabel)
		{
			// myTags = myEditionArea.getText();
			formatTags();
		}
		else if (myTargetLabel == myAudienceLabel)
		{
			// myAudience = myEditionArea.getText();
			formatAudience();
		}
		switchToAnnotationView();
	}

	/**
	 * 
	 * @since 15.07.00-SNAPSHOT
	 */
	private void switchToAnnotationView()
	{
		// if (getRootPane()!= null)
		{
			getRootPane().getLayeredPane().remove(myEditAnnotationPanel);
			getRootPane().repaint();
		}
	}

}
