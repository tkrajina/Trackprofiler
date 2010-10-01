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
 * Created on 2006.01.26
 */
package info.puzz.trackprofiler.appobjects;

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.util.Message;

/** Waypoint koji je istovremeno i visinski ekstrem nekog track-a. */
public class TrackExtreeme extends Waypoint {
    
    private boolean minimum;

    public TrackExtreeme() {
        super();
    }

    public TrackExtreeme( double latitude, double longitude, double elevation ) {
        super( latitude, longitude, elevation );
    }

    public boolean isMinimum() {
        return this.minimum;
    }
    
    public boolean isMaximum() {
        return ! this.minimum;
    }

    public void setMinimum() {
        this.minimum = true;
        this.setTitle( new Message( Messages.MINIMUM_LABEL ).toString() );
        this.setDescription( new Message( Messages.MINIMUM_LABEL ).toString() );
    }

    public void setMaximum() {
        this.minimum = false;
        this.setTitle( new Message( Messages.MAXIMUM_LABEL ).toString() );
        this.setDescription( new Message( Messages.MAXIMUM_LABEL ).toString() );
    }

}
