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
 * Created on 2006.02.08
 */
package info.puzz.trackprofiler;

import info.puzz.trackprofiler.util.PropertiesService;

import java.util.Iterator;
import java.util.Properties;

/**
 * Sve poruke u messages_{hr|en} datoteci. Kreira se automatski pomocu
 * {@link #createConstantsFromProperties(String)} metode (samo copy-paste).
 * 
 * @author Tomo Krajina
 */
public class Messages {

    public static final String ERROR_WHEN_LOADING = "error_when_loading"; //$NON-NLS-1$
    public static final String HIGHEST_POINT = "highest_point"; //$NON-NLS-1$
    public static final String FILE_NOT_FOUND = "file_not_found"; //$NON-NLS-1$
    public static final String SELECTED_ANGLE_LABEL = "selected_angle_label"; //$NON-NLS-1$
    public static final String EXIT = "exit"; //$NON-NLS-1$
    public static final String CANCEL = "cancel"; //$NON-NLS-1$
    public static final String ELEVATION = "elevation"; //$NON-NLS-1$
    public static final String _3D_LENGTH = "3d_length"; //$NON-NLS-1$
    public static final String ERROR_OPENING_BROWSER = "error_opening_browser"; //$NON-NLS-1$
    public static final String GENERIC_ERROR = "generic_error"; //$NON-NLS-1$
    public static final String PLT_AND_WPT_FILES = "plt_and_wpt_files"; //$NON-NLS-1$
    public static final String SMOOTH = "smooth"; //$NON-NLS-1$
    public static final String DESCRIPTION = "description"; //$NON-NLS-1$
    public static final String WAYPOINTS = "waypoints"; //$NON-NLS-1$
    public static final String WAYPOINTS_LIST = "waypoints_list"; //$NON-NLS-1$
    public static final String LOWEST_POINT = "lowest_point"; //$NON-NLS-1$
    public static final String VISIBILITY = "visibility"; //$NON-NLS-1$
    public static final String LENGTH = "length"; //$NON-NLS-1$
    public static final String FILLED_CHART = "filled_chart"; //$NON-NLS-1$
    public static final String SELECTED_DISTANCE_LABEL = "selected_distance_label"; //$NON-NLS-1$
    public static final String COPYRIGHT = "copyright"; //$NON-NLS-1$
    public static final String WAYPOINT_ARROW_LENGTH = "waypoint_arrow_length"; //$NON-NLS-1$
    public static final String SAVE = "save"; //$NON-NLS-1$
    public static final String LABEL_FROM_TITLE = "label_from_title"; //$NON-NLS-1$
    public static final String TRACK_HEADER_NOT_VALID = "track_header_not_valid"; //$NON-NLS-1$
    public static final String ABOUT = "about"; //$NON-NLS-1$
    public static final String SHOW_ALL_WAYPOINTS = "show_all_waypoints"; //$NON-NLS-1$
    public static final String ERROR_IN_FILE = "error_in_file"; //$NON-NLS-1$
    public static final String TRACK_DETAILS = "track_details"; //$NON-NLS-1$
    public static final String TRACK_NOT_LOADED = "track_not_loaded"; //$NON-NLS-1$
    public static final String ERROR = "error"; //$NON-NLS-1$
    public static final String MINIMUM_LABEL = "minimum_label"; //$NON-NLS-1$
    public static final String PREFERENCES = "preferences"; //$NON-NLS-1$
    public static final String LABEL_FROM_DESCRIPTION = "label_from_description"; //$NON-NLS-1$
    public static final String EXTREMES = "extremes"; //$NON-NLS-1$
    public static final String MAXIMUM_LABEL = "maximum_label"; //$NON-NLS-1$
    public static final String TITLE = "title"; //$NON-NLS-1$
    public static final String VALUE_ERROR = "value_error"; //$NON-NLS-1$
    public static final String _2D_LENGTH = "2d_length"; //$NON-NLS-1$
    public static final String POSITION = "position"; //$NON-NLS-1$
    public static final String PREFERENCES_SAVE_ERROR = "preferences_save_error"; //$NON-NLS-1$
    public static final String RESET = "reset"; //$NON-NLS-1$
    public static final String LOAD = "load"; //$NON-NLS-1$
    public static final String CLOSE = "close"; //$NON-NLS-1$
    public static final String MIN_DISTANCE_FOR_WAYPOINT = "min_distance_for_waypoint"; //$NON-NLS-1$
    public static final String FILE_TYPE_ERROR = "file_type_error"; //$NON-NLS-1$
    public static final String TRACK_PROFILE = "track_profile"; //$NON-NLS-1$
    public static final String DOWNHILL_SUM = "downhill_sum"; //$NON-NLS-1$
    public static final String HIDE_ALL_WAYPOINTS = "hide_all_waypoints"; //$NON-NLS-1$
    public static final String TRACKS = "tracks"; //$NON-NLS-1$
    public static final String SMOOTH_NO_FROM_COMPUTING = "smooth_no_from_computing"; //$NON-NLS-1$
    public static final String UPHILL_SUM = "uphill_sum"; //$NON-NLS-1$
    public static final String TRACK_POINTS = "track_points"; //$NON-NLS-1$
    
    private Messages() {}

    protected void createConstantsFromProperties( String propertyFileName ) throws Exception {
        Properties properties = PropertiesService.getInstance().getProperties( propertyFileName );
        Iterator i = properties.keySet().iterator();
        while( i.hasNext() ) {
            String key = (String) i.next();
            // String value = (String) properties.get( key );
            String _key = key.toUpperCase().replace( ' ', '_' ); //$NON-NLS-1$ //$NON-NLS-2$
            if( "0123456789".indexOf(key.charAt(0))>0) { //$NON-NLS-1$
                _key = "_" + _key; //$NON-NLS-1$
            }
            System.out.println( "public static final String " + _key + " = \"" + key //$NON-NLS-1$ //$NON-NLS-2$
                    + "\"; //$NON-NLS-1$" ); //$NON-NLS-1$
        }
    }

    public static void main( String[] args ) throws Exception {
        new Messages().createConstantsFromProperties( "/messages_en" ); //$NON-NLS-1$
    }

}
