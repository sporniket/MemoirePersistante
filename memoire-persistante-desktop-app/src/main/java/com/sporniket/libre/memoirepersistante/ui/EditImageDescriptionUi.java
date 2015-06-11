/**
 * 
 */
package com.sporniket.libre.memoirepersistante.ui;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import com.sporniket.libre.ui.swing.ComponentFactory;

/**
 * User interface to change an image description
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
public class EditImageDescriptionUi
{
	private static final int PADDING = com.sporniket.libre.memoirepersistante.ui.ComponentFactory.PADDING;

	private static JButton createButton(String string)
	{
		String _label = string ; //FIXME retrieve from resource bundle
		JButton _button = new JButton(_label);
		_button.setContentAreaFilled(false);
		return _button;
	}

	/**
	 * @return the texte area.
	 * @since 15.07.00-SNAPSHOT
	 */
	private static JTextArea createTextArea()
	{
		JTextArea _textArea = new JTextArea();
		_textArea.setRows(6);
		_textArea.setWrapStyleWord(true);
		_textArea.setLineWrap(true);

		return _textArea;
	}
	private final JTextArea myEditionArea = createTextArea();
	private final JButton myOkButton = createButton("ok");

	private final JPanel myPanel ;

	/**
	 * @param minimumSize the minimum size.
	 * @since 15.07.00-SNAPSHOT
	 */
	public EditImageDescriptionUi(Dimension minimumSize)
	{
		myPanel = ComponentFactory.createPanelWithVerticalLayout();
		myPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)));

		JPanel _titleLine = ComponentFactory.createPanelWithHorizontalLayout();
		_titleLine.add(new JLabel("Edit description"));
		_titleLine.add(Box.createHorizontalGlue());
		_titleLine.add(getOkButton());
		myPanel.add(_titleLine);
		myPanel.add(Box.createVerticalStrut(PADDING));

		JScrollPane _textPanel = new JScrollPane(myEditionArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Dimension _minimumSize = minimumSize;
		_textPanel.setMinimumSize(new Dimension(_minimumSize.width, _minimumSize.height - _titleLine.getMinimumSize().height));
		myPanel.add(_textPanel);
		myPanel.setVisible(true);
	}

	/**
	 * Add a listener to the OK button.
	 * @param listener the listener.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void addOkActionListener(ActionListener listener)
	{
		getOkButton().addActionListener(listener);
	}

	/**
	 * Read access to the description.
	 * @return the description.
	 * @since 15.07.00-SNAPSHOT
	 */
	public String getDescription(){
		return getEditionArea().getText();
	}

	/**
	 * Get editionArea.
	 * @return the editionArea.
	 * @since 15.07.00-SNAPSHOT
	 */
	private JTextArea getEditionArea()
	{
		return myEditionArea;
	}

	/**
	 * Get okButton.
	 * @return the okButton
	 * @since 15.07.00-SNAPSHOT
	 */
	private JButton getOkButton()
	{
		return myOkButton;
	}
	
	/**
	 * Get panel.
	 * @return the panel
	 * @since 15.07.00-SNAPSHOT
	 */
	public JPanel getPanel()
	{
		return myPanel;
	}
	
	/**
	 * Write access to the description.
	 * @param description the description.
	 * @since 15.07.00-SNAPSHOT
	 */
	public void setDescription(String description){
		getEditionArea().setText(description);
	}
}
