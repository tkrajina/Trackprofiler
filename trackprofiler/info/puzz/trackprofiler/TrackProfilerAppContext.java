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
package info.puzz.trackprofiler;

import java.util.Properties;

public class TrackProfilerAppContext {

    public static final String PROGRAM_VERSION = "v0.8"; //$NON-NLS-1$

    public static final String FEET = "ft"; //$NON-NLS-1$
    public static final String METER = "m"; //$NON-NLS-1$

    private static final String HOME_DIR;
    private static final String FILE_SEPARATOR;
    public static final String PROPERTIES_FILE_NAME;
    static {
        FILE_SEPARATOR = System.getProperty( "file.separator" ); //$NON-NLS-1$
        HOME_DIR = System.getProperty( "user.home" ); //$NON-NLS-1$
        PROPERTIES_FILE_NAME = HOME_DIR + FILE_SEPARATOR + ".tracker_profiler"; //$NON-NLS-1$
    }

    private static final String COMPUTING_SMOOTH_NUMBER = "smooth_no"; //$NON-NLS-1$
    private static final int COMPUTING_SMOOTH_NUMBER_DEFAULT = 70;

    private static final String WAYPOINT_MIN_DISTANCE = "waypoint_min_distance"; //$NON-NLS-1$
    private static final int WAYPOINT_MIN_DISTANCE_DEFAULT = 100; //$NON-NLS-1$

    private static final String FILLED_GRAPH = "filled_graph"; //$NON-NLS-1$
    private static final boolean FILLED_GRAPH_DEFAULT = true;

    private static final String WAYPOINT_LABEL_FROM_TITLE = "waypoint_label_from_title"; //$NON-NLS-1$
    private static final boolean WAYPOINT_LABEL_FROM_TITLE_DEFAULT = true;

    private static final String LANGUAGE = "language"; //$NON-NLS-1$
    private static final String LANGUAGE_DEFAULT = "en"; //$NON-NLS-1$

    private String messagesFile;

    private Properties properties = new Properties();

    private static TrackProfilerAppContext _instance;

    private TrackProfilerAppContext() {
        super();
    }

    public static TrackProfilerAppContext getInstance() {
        if ( _instance == null ) {
            _instance = new TrackProfilerAppContext();
        }
        return _instance;
    }

    /** Privremeno; za snimanje postavki. */
    public static TrackProfilerAppContext getTempInstance() {
        TrackProfilerAppContext trackProfilerAppContext = new TrackProfilerAppContext();
        trackProfilerAppContext.setProperties( (Properties) getInstance().getProperties().clone() );
        return trackProfilerAppContext;
    }

    public void setComputingSmoothNumber( int i ) {
        this.properties.setProperty( COMPUTING_SMOOTH_NUMBER, "" + i ); //$NON-NLS-1$
    }

    public int getComputingSmoothNumber() {
        try {
            return Integer.parseInt( this.properties.getProperty( COMPUTING_SMOOTH_NUMBER ) );
        }
        catch ( Exception e ) {
            return COMPUTING_SMOOTH_NUMBER_DEFAULT;
        }
    }

    public void setWaypointMinDistance( int i ) {
        this.properties.setProperty( WAYPOINT_MIN_DISTANCE, "" + i ); //$NON-NLS-1$
    }

    public int getWaypointMinDistance() {
        try {
            return Integer.parseInt( this.properties.getProperty( WAYPOINT_MIN_DISTANCE ) );
        }
        catch ( Exception e ) {
            return WAYPOINT_MIN_DISTANCE_DEFAULT;
        }
    }

    public void setFilledGraph( boolean b ) {
        this.properties.setProperty( FILLED_GRAPH, "" + b ); //$NON-NLS-1$
    }

    public boolean isFilledGraph() {
        try {
            return this.properties.getProperty( FILLED_GRAPH ).equals( "true" ); //$NON-NLS-1$
        }
        catch ( Exception e ) {
            return FILLED_GRAPH_DEFAULT;
        }
    }

    public void setWaypointLabelFromTitle( boolean b ) {
        System.out.println(b);
        this.properties.setProperty( WAYPOINT_LABEL_FROM_TITLE, "" + b ); //$NON-NLS-1$
    }

    public boolean isWaypointLabelFromTitle() {
        try {
            return this.properties.getProperty( WAYPOINT_LABEL_FROM_TITLE ).equals( "true" ); //$NON-NLS-1$
        }
        catch ( Exception e ) {
            return WAYPOINT_LABEL_FROM_TITLE_DEFAULT;
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties( Properties properties ) {
        this.properties = properties;
    }

    /**
     * Ekstenzija s kojom se odredjuje iz koje datoteke ce se citati poruke.
     * Npr. ako je "hr" onda se cita iz meddages_hr.
     */
    public String getLanguage() {
        String result = this.properties.getProperty( LANGUAGE );
        if ( result == null || result.length() == 0 ) {
            return LANGUAGE_DEFAULT;
        }
        return result;
    }

    /** @see #getLanguage() */
    public void setLanguages( String s ) {
        this.properties.setProperty( LANGUAGE, s );
    }

    public String getMessagesFile() {
        if( this.messagesFile == null || this.messagesFile.length() == 0 ) {
            this.messagesFile = "messages_" + this.getLanguage(); //$NON-NLS-1$
        }
        return this.messagesFile;
    }

}