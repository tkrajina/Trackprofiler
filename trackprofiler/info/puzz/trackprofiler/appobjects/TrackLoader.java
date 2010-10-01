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

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.TrackProfilerException;
import info.puzz.trackprofiler.util.Message;
import info.puzz.trackprofiler.util.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Vector;

public class TrackLoader {

    public static Track loadTrack( InputStream in ) throws TrackProfilerException {
        Vector/*<String>*/ lines = getLines( in );
        
        Track resultTrack = parseHeader( lines );
        
        Vector trackPoints = new Vector();

        // TODO: skuziti znacenje prvih 6 linija!
        if ( lines.size() < 6 ) {
            throw new TrackProfilerException( "track_header_not_valid" ); //$NON-NLS-1$
        }

        for ( int i = 6; i < lines.size(); i++ ) {
            TrackPoint dot = loadTrackDot( (String )lines.get( i ) );
            trackPoints.add( dot );
        }
        
        resultTrack.setTrackPoints( trackPoints );

        return resultTrack;
    }

    private static Track parseHeader( Vector/*<String>*/ lines ) {
        Track result = new Track();
//        String thirdLine = (String) lines.get( 2 );
        // TODO:
//        if ( thirdLine.toLowerCase().trim().endsWith( "feet" ) ) { //$NON-NLS-1$
//            result.setUnit( TrackProfilerAppContext.FEET );
//        }
//        else {
//            result.setUnit( TrackProfilerAppContext.METER );
//        }
        return result;
    }

    private static TrackPoint loadTrackDot( String line ) throws TrackProfilerException {
        try {
            String[] temp = StringUtils.split(",", line ); // line.split( "," ); //$NON-NLS-1$
            double lat = Double.parseDouble( temp[0].trim() );
            double lon = Double.parseDouble( temp[1].trim() );
            double ele = Double.parseDouble( temp[3].trim() ) * TrackPoint.FEET_METER;
            TrackPoint result = new TrackPoint( lat, lon, ele );
            // System.out.println( "Učitana točka: " + result ); //$NON-NLS-1$
            return result;
        }
        catch ( Exception e ) {
            throw new TrackProfilerException( new Message( Messages.ERROR_IN_FILE ).toString() );
        }
    }

    public static Vector/*<String>*/ getLines( InputStream in ) throws TrackProfilerException {
        if ( in == null ) {
            throw new TrackProfilerException("generic_error" ); //$NON-NLS-1$
        }
        try {
            byte[] bytes = new byte[ in.available() ];
            in.read( bytes );
            String content = new String( bytes );
            System.out.println(content.length());
            System.out.println(in.available());
            String[] lines = StringUtils.split( "\n", content ); // content.split( "\n" ); //$NON-NLS-1$
            Vector/*<String>*/ result = new Vector/*<String>*/();
            for ( int i = 0; i < lines.length; i++ ) {
                result.add( lines[i].trim() );
            }
            return result;
        }
        catch ( Exception e ) {
            throw new TrackProfilerException( "generic_error" ); //$NON-NLS-1$
        }
    }
    
    public static Waypoints loadWaypoints( InputStream in ) throws TrackProfilerException {
        Vector/*<String>*/ lines = getLines( in );
        Waypoints result = new Waypoints();

        // TODO: Provjera velicine array-a
        for ( int i = 4; i < lines.size(); i++ ) {
            result.add( getWaypoint( (String)lines.get( i ) ) );
        }
        
        return result;
    }

    private static Waypoint getWaypoint( String line ) {
        String[] temp = StringUtils.split( ",", line ); // line.split( "," ); //$NON-NLS-1$
        Waypoint result = new Waypoint();

        result.setTitle( temp[1].trim() );
        result.setLatitude( Double.parseDouble( temp[2].trim() ) );
        result.setLongitude( Double.parseDouble( temp[3].trim() ) );

        result.setDescription( temp[10].trim() );
        
        // System.out.println( "Učitan:" + result ); //$NON-NLS-1$

        return result;
    }
    
    public static void main( String[] args ) throws Exception {
        FileInputStream in = new FileInputStream( "test/waypoints.wpt" ); //$NON-NLS-1$

        Vector/*<Waypoint>*/waypoints = loadWaypoints( in );
        in = new FileInputStream( "test/track.plt" ); //$NON-NLS-1$

        // @SuppressWarnings("unused") //$NON-NLS-1$
        Track track = loadTrack( in );
        System.out.println(track);

        // @SuppressWarnings("unused") //$NON-NLS-1$
        Waypoint w1 = (Waypoint) waypoints.get( 5 );
        System.out.println(w1);
    }
    
}