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
 * Created on 2006.01.29
 */
package info.puzz.trackprofiler;

import info.puzz.trackprofiler.gui.TrackProfilerFrame;

import java.io.FileInputStream;
import java.util.Properties;

public class TrackProfilerAppStarter {
    
    private static void loadProperties() /*throws TrackProfilerException */ {
        try {
            Properties properties = new Properties();
            properties.load( new FileInputStream( TrackProfilerAppContext.PROPERTIES_FILE_NAME ) );
            TrackProfilerAppContext.getInstance().setProperties( properties );
        }
        catch ( Exception e ) {
            // TODO
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        
        if( args != null && args.length > 0 ) {
            String language = args[ 0 ];
            TrackProfilerAppContext.getInstance().setLanguages( language );
        }
        
        TrackProfilerFrame inst = TrackProfilerFrame.getInstance();
        inst.setVisible(true);
        
        loadProperties();

        // Odmah cemo ucitati jedan graf s waypointima da ne bude prazno:
//        try {
//            InputStream in1 = TrackProfilerFrame.class.getResourceAsStream("/test/track.plt");
//            InputStream in2 = TrackProfilerFrame.class.getResourceAsStream("/test/waypoints.wpt");
//
//            Track track = TrackLoader.loadTrack( in1 );
//            Waypoints waypoints = TrackLoader.loadWaypoints( in2 );
//
//            Track _track = track.smoothTrack(30 );
//
//            inst.setTrack( _track );
//            inst.setWaypoints( waypoints );
//        }
//        catch( Exception e ) {
//            e.printStackTrace();
//        }

    }
    
}
