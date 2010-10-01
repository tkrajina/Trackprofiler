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
 * Created on 2006.01.30
 */
package info.puzz.trackprofiler.appobjects;

import java.util.Vector;

/**
 * @author Tomo Krajina
 */
public class Waypoints extends Vector/*<Waypoint>*/ {

    public Waypoints( int initialCapacity, int capacityIncrement ) {
        super( initialCapacity, capacityIncrement );
    }

    public Waypoints( int initialCapacity ) {
        super( initialCapacity );
    }

    public Waypoints() {
        super();
    }

    public void add( Waypoints wpts ) {
        if( wpts == null || wpts.size() == 0 ) {
        	return;
        }
        for( int i = 0; i < wpts.size(); i++ ) {
        	this.add( wpts.get( i ) );
        }
    }

    public void add( Waypoint wpt ) {
        super.add( wpt );
    }
    
    public Waypoint getWaypoint( int i ) {
        return (Waypoint) super.get( i );
    }
    
}