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
 * Created on 2006.01.23
 */
package info.puzz.trackprofiler.appobjects;

public class TrackPoint extends AbstractPoint {

    private double position = 0;

    private double angle = 0;

    public TrackPoint( double latitude, double longitude, double elevation ) {
        super( latitude, longitude, elevation );
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle( double angle ) {
        this.angle = angle;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition( double position ) {
        this.position = position;
    }

}