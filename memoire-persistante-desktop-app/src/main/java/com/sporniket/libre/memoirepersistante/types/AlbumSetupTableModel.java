/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.awt.Component;
import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Table model for setting up a photo album.
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
 * 
 * @deprecated
 */
public class AlbumSetupTableModel implements TableModel
{
	/**
	 * Renderer for the thumbnail.
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
	 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
	 * option) any later version.
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
	private static class ThumbnailRenderer extends DefaultTableCellRenderer
	{

		public ThumbnailRenderer()
		{
			super();
			// TODO Auto-generated constructor stub
		}

		private Dimension mySize;

		@Override
		protected void setValue(Object value)
		{
			if (value instanceof Icon)
			{
				Icon _icon = (Icon) value;
				setIcon(_icon);
				mySize = new Dimension(_icon.getIconWidth(), _icon.getIconHeight());
			}
		}

		@Override
		public Dimension getSize(Dimension rv)
		{
			return mySize;
		}
	}

	private static class InformationRenderer extends JTextArea implements TableCellRenderer
	{

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
				int column)
		{
			// TODO Auto-generated method stub
			setText((String) value);
			if (isSelected)
			{
				setBackground(table.getSelectionBackground());
			}
			else
			{
				setBackground(table.getBackground());
			}
			return this;
		}

	}

	private static final int COLUMN_INDEX__INFORMATION = 2;

	private static final int COLUMN_INDEX__THUMBNAIL = 1;

	private static final int COLUMN_INDEX__SELECTION = 0;

	private static final String MESSAGE_FORMAT__INFORMATION = "Name : {0}\nOrientation : {1}\nSize : {2}x{3} pixels";

	/**
	 * This model require {@link AlbumSetupTableModel#ROW_SPAN} to display its data.
	 */
	private static final int COL_SPAN = 3;

	private static final String[] NAMES__COLUMNS =
	{
			"", "THUMBNAIL", "INFORMATION"
	};

	private static final Class[] CLASSES__COLUMNS =
	{
			Boolean.class, ImageIcon.class, String.class
	};

	private ThumbnailProvider myThumbnailPool;

	private MessageFormat myMessageFormatInformation = new MessageFormat(MESSAGE_FORMAT__INFORMATION);

	private String formatInformation(PhotoResource photo)
	{
		Object[] _args =
		{
				photo.getSourceFile().getName(),
				photo.getOrientation(),
				photo.getRealDimensions().getWidth(),
				photo.getRealDimensions().getHeight()
		};
		return myMessageFormatInformation.format(_args);
	}

	private ThumbnailProvider getThumbnailPool()
	{
		return myThumbnailPool;
	}

	public void setThumbnailPool(ThumbnailProvider thumbnailPool)
	{
		myThumbnailPool = thumbnailPool;
	}

	/**
	 * List of Photos to display.
	 */
	private List<PhotoResource> myData;

	private List<PhotoResource> getData()
	{
		if (null == myData)
		{
			setData(new ArrayList<PhotoResource>(0));
		}
		return myData;
	}

	public void setData(List<PhotoResource> data)
	{
		myData = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount()
	{

		return getData().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return COL_SPAN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex)
	{
		return NAMES__COLUMNS[columnIndex];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int columnIndex)
	{
		return CLASSES__COLUMNS[columnIndex];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return COLUMN_INDEX__SELECTION == columnIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		// TODO Auto-generated method stub
		PhotoResource _photo = getData().get(rowIndex);
		switch (columnIndex)
		{
			case 0:
				return (Boolean) _photo.getSelected();
			case 1:
//				return new ImageIcon(getThumbnailPool().getThumbnail(_photo.getThumbnail()));
			case 2:
				return formatInformation(_photo);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{
		if (COLUMN_INDEX__SELECTION == columnIndex)
		{
			getData().get(rowIndex).setSelected((Boolean) aValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event. TableModelListener)
	 */
	public void addTableModelListener(TableModelListener l)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event .TableModelListener)
	 */
	public void removeTableModelListener(TableModelListener l)
	{
		// TODO Auto-generated method stub

	}

	public void setupColumnModel(JTable host)
	{
		TableColumnModel _model = host.getColumnModel();
		_model.getColumn(COLUMN_INDEX__SELECTION).setMinWidth(50);
		_model.getColumn(COLUMN_INDEX__SELECTION).setMaxWidth(50);
		_model.getColumn(COLUMN_INDEX__THUMBNAIL).setMinWidth(300);
		_model.getColumn(COLUMN_INDEX__THUMBNAIL).setMaxWidth(300);
		ThumbnailRenderer _thumbnailRenderer = new ThumbnailRenderer();
		// _thumbnailRenderer.setMinimumSize(new Dimension(300,300));
		_model.getColumn(COLUMN_INDEX__THUMBNAIL).setCellRenderer(_thumbnailRenderer);
		_model.getColumn(COLUMN_INDEX__INFORMATION).setMinWidth(300);
		_model.getColumn(COLUMN_INDEX__INFORMATION).setCellRenderer(new InformationRenderer());
	}

}
