/*
 * --------------------------------------------------------------
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * --------------------------------------------------------------
 *
 * (c) 2006 by Tomo Krajina, aaa@puzz.info
 *
 */
package info.puzz.trackprofiler.gui;

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.TrackProfilerException;
import info.puzz.trackprofiler.appobjects.Track;
import info.puzz.trackprofiler.appobjects.Waypoint;
import info.puzz.trackprofiler.util.Message;
import info.puzz.trackprofiler.util.TrackProfilerMath;

import javax.swing.table.AbstractTableModel;

public class WaypointsTableModel extends AbstractTableModel {

	public WaypointsTableModel() {
		super();

        if( this.getCurrentTrack() != null ) {
            this.getCurrentTrack().sortWaypointsByDistance();
        }
	}

	public int getRowCount() {
        if ( this.getCurrentTrack() != null ) {
            return this.getCurrentTrack().getWaypoints().size();
        }
        return 0;
    }

	public int getColumnCount() {
		return 6;
	}

    public String getColumnName(int column) {
        if( column == 0 ) {
            return new Message( Messages.VISIBILITY ).toString();
        }
        else if( column == 1 ) {
            return new Message( Messages.TITLE ).toString();
        }
        else if( column == 2 ) {
            return new Message( Messages.DESCRIPTION ).toString();
        }
        else if( column == 3 ) {
            return new Message( Messages.POSITION ).toString();
        }
        else if( column == 4 ) {
            return new Message( Messages.ELEVATION ).toString();
        }
        return new Message( Messages.WAYPOINT_ARROW_LENGTH ).toString();
    }

	public Object getValueAt(int rowIndex, int columnIndex) {
        Waypoint wpt = (Waypoint) this.getCurrentTrack().getWaypoints().get(rowIndex);
		if (columnIndex == 0) {
			return new Boolean( wpt.isVisible() );
		}
		else if (columnIndex == 1) {
			return wpt.getTitle();
		}
        else if (columnIndex == 2) {
            return wpt.getDescription();
        }
        else if (columnIndex == 3) {
            return new Double( TrackProfilerMath.round(wpt.getFirstPositionOnTrack(),2) );
        }
        else if( columnIndex == 4 ) {
            return new Double( TrackProfilerMath.round(wpt.getElevation(),2) );
        }
        return new Double( wpt.getArrowLength() );
	}

    public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex != 3;
	}

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Waypoint wpt = (Waypoint) this.getCurrentTrack().getWaypoints().get(rowIndex);
        if ( columnIndex == 0 ) {
            boolean b = ( (Boolean) aValue ).booleanValue();
            wpt.setVisible( b );
        }
        else if( columnIndex == 1 ) {
            wpt.setTitle( (String) aValue );
        }
        else if( columnIndex == 2 ){
            wpt.setDescription( (String) aValue );
        }
        else if( columnIndex == 3 ){
//            wpt.setPositionOnTrack( ( (Double) aValue ).doubleValue() );
        }
        else if ( columnIndex == 4 ) {
            wpt.setElevation( ( (Double) aValue ).doubleValue() );
        }
        else {
            wpt.setArrowLength( ( (Integer) aValue ).intValue() );
        }

        try {
            TrackProfilerFrame.getInstance().drawChart();
        }
        catch ( TrackProfilerException e ) {
            e.printStackTrace();
        }
        this.fireTableCellUpdated( rowIndex, columnIndex );
    }

    public Class/*<?>*/ getColumnClass(int columnIndex) {
    	if (columnIndex == 0) {
			return Boolean.class;
		}
        if( columnIndex == 1 || columnIndex == 2 ) {
            return String.class;
        }
        if( columnIndex == 4 ) {
            return Double.class;
        }
        return Integer.class;
	}

    private Track getCurrentTrack() {
        return TrackProfilerFrame.getInstance().getTrack();
    }


}