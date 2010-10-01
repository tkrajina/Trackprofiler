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
package info.puzz.trackprofiler.util;

import info.puzz.trackprofiler.appobjects.TrackPoint;
import info.puzz.trackprofiler.appobjects.Track;

public class TrackProfilerMath {

    /** Stupnjeve pretvara u radijane. */
    public static double convertDMS2radians( double d ) {
        return ( d / 180D ) * Math.PI;
    }

    /** Zaokruzuje broj na n decimalnih mjesta. */
    public static double round( double d, int n ) {
        double result = d * Math.pow( 10, n );
        result = Math.ceil( result );
        return result / Math.pow( 10, n );
    }
    
    /**
     * Prosjecna zakrivljenost u toj tocki grafa.
     * 
     * Ova metoda se (za sada) nigdje koristi i nije testirana, ali neka ostane
     * za svaki slucaj :)
     */
    public static double claculateAverageAngle( Track track, int position ) {
        final int INTERVAL = 4;
        int start = position - INTERVAL;
        int end = position + INTERVAL;
        if ( start < 0 ) {
            start = 0;
        }
        if ( end >= track.size() ) {
            end = track.size() - 1;
        }
        if( end - start == 0 ) {
            return 0;
        }
        double avgTanAngle = 0;
        for( int i = 1; i < Math.abs( end - start ); i++ ) {
            TrackPoint d1 = track.getPointAt( i - 1 );
            TrackPoint d2 = track.getPointAt( i );
            double tanAngle = ( d2.getElevation() - d1.getElevation() ) / d2.distance2D( d1 );
            System.out.println(d2.getElevation() - d1.getElevation());
            System.out.println(tanAngle);
            avgTanAngle += tanAngle;
        }
        
        return Math.atan( avgTanAngle / ( end - start ) );
    }
    
    public static void main( String[] args ) {
        
        System.out.println(round(3.14159265359, 3));
        
    }

}
