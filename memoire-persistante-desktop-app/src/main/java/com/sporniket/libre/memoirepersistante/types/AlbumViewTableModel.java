/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sporniket.libre.memoirepersistante.ui.PhotoResourcePanel;
import com.sporniket.libre.memoirepersistante.ui.UiTheme;

/**
 * Table model that use the {@link PhotoResourcePanel} as renderer.
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
 * @deprecated
 */
public class AlbumViewTableModel implements TableModel {
	
	private List<PhotoResourcePanel> myDataCells ;
	/**
	 * @return the dataCells
	 */
	public List<PhotoResourcePanel> getDataCells() {
		return myDataCells;
	}

	/**
	 * @param dataCells the dataCells to set
	 */
	private void setDataCells(List<PhotoResourcePanel> dataCells) {
		myDataCells = dataCells;
	}

	public AlbumViewTableModel(List<PhotoResource> data, ThumbnailProvider thumbnailPool, UiTheme uiTheme)
	{
		List<PhotoResourcePanel> _cells = new ArrayList<PhotoResourcePanel>(data.size());
		for(PhotoResource _photo:data)
		{
//			PhotoResourcePanel _cell = new PhotoResourcePanel(_photo, thumbnailPool, uiTheme);
//			_cells.add(_cell);
		}
		setDataCells(_cells);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return getDataCells().size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return PhotoResourcePanel.class;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getDataCells().get(rowIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void addTableModelListener(TableModelListener l) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	public void removeTableModelListener(TableModelListener l) {
	}
	
	public void setupTable(JTable host) {
		TableColumnModel _model = host.getColumnModel();
		int _columnIndex = 0 ;
		int _maxHeight = 0 ;
		int _tableWidth = 0 ;
		for(Enumeration<TableColumn> _columns = _model.getColumns();_columns.hasMoreElements();)
		{
			TableColumn _column = _columns.nextElement();
			_column.setMinWidth(316);
			_column.setMaxWidth(316);
			PhotoResourcePanel _cellRenderer = getDataCells().get(_columnIndex);
			if(_cellRenderer.getPreferredSize().height > _maxHeight)
			{
				_maxHeight = _cellRenderer.getPreferredSize().height;
			}
			_tableWidth+=_cellRenderer.getPreferredSize().width;
			_column.setCellRenderer(_cellRenderer);
			_columnIndex++;
		}
		host.setRowHeight(_maxHeight);
		host.setPreferredSize(new Dimension(_tableWidth, _maxHeight));
		host.setPreferredScrollableViewportSize(new Dimension(getDataCells().get(0).getPreferredSize().width*2,_maxHeight));
		host.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		host.setMinimumSize(new Dimension(getDataCells().get(0).getPreferredSize().width,_maxHeight));
	}


}
