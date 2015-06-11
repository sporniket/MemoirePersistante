/**
 * 
 */
package com.sporniket.libre.memoirepersistante.types;

import java.text.MessageFormat;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Simple datasheet table model.
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
public class PhotoResourceInformationDataSheet implements TableModel {

	private static final int COLUMN_COUNT = 2;

	private static final String[] NAMES__ROW = { "FILENAME", "DIMENSIONS",
			"ORIENTATION" };

	private static final int COLUMN_INDEX__DATA_NAME = 0;

	private static final String MESSAGE_FORMAT__DIMENSIONS = "{0} x {1}";
	private PhotoResource myData;
	private MessageFormat myMessageFormatForDimensions = new MessageFormat(
			MESSAGE_FORMAT__DIMENSIONS);
	private String[] myDisplayableData;

	private String[] getDisplayableData() {
		return myDisplayableData;
	}

	private void setDisplayableData(String[] displayableData) {
		myDisplayableData = displayableData;
	}

	private String formatDimensions() {
		Object[] _args = { getData().getRealDimensions().getWidth(),
				getData().getRealDimensions().getHeight() };
		return myMessageFormatForDimensions.format(_args);
	}

	public PhotoResourceInformationDataSheet(PhotoResource data) {
		super();
		setData(data);
	}

	private PhotoResource getData() {
		return myData;
	}

	private void setData(PhotoResource data) {
		myData = data;
		String[] _temp = { myData.getSourceFile().getName(),
				formatDimensions(), myData.getOrientation().toString() };
		setDisplayableData(_temp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return NAMES__ROW.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (COLUMN_INDEX__DATA_NAME == columnIndex) {
			return NAMES__ROW[rowIndex];
		} else {
			return getDisplayableData()[rowIndex];
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#addTableModelListener(javax.swing.event.
	 * TableModelListener)
	 */
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableModel#removeTableModelListener(javax.swing.event
	 * .TableModelListener)
	 */
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
