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
/*
 * Created on 2006.02.15
 */
package info.puzz.trackprofiler.gui;

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.appobjects.Track;
import info.puzz.trackprofiler.util.Message;
import info.puzz.trackprofiler.util.TrackProfilerMath;

import javax.swing.table.AbstractTableModel;

public class TrackTableModel extends AbstractTableModel {
    
    private TrackProfilerFrame trackProfilerFrame = null;

    public TrackTableModel(TrackProfilerFrame trackProfilerFrame) {
        super();
        this.trackProfilerFrame = trackProfilerFrame;
    }

    public int getRowCount() {
        Track track = this.getCurrentTrack();
        return track == null ? 0 : track.size();
    }

    public int getColumnCount() {
        return 3;
    }
    
    public String getColumnName(int column) {
        if ( column == 0 ) {
            return "#"; //$NON-NLS-1$
        }
        else if ( column == 1 ) {
            return Message.get(Messages.POSITION);
        }
        return "%"; //$NON-NLS-1$
    }

    public Object getValueAt( int rowIndex, int columnIndex ) {
        Track track = getCurrentTrack();
        if( columnIndex == 0 ) {
            return new Integer( rowIndex );
        }
        else if( columnIndex == 1 ) {
            return new Double( (int) track.getPointAt( rowIndex ).getPosition() );
        }
        return TrackProfilerMath.round( track.getPointAt( rowIndex ).getAngle() * 100, 1 ) + "%"; //$NON-NLS-1$
    }
    
    public Class/*<?>*/ getColumnClass(int columnIndex) {
        if( columnIndex == 0 ) {
            return Integer.class;
        }
        else if( columnIndex == 1 ) {
            return Double.class;
        }
        else {
            return String.class;
        }
    }
    
    private Track getCurrentTrack() {
        return this.trackProfilerFrame.getTrack();
    }

}
