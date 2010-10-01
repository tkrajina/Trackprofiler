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
 * Created on 2006.02.01
 */
package info.puzz.trackprofiler.util;

import info.puzz.trackprofiler.TrackProfilerAppContext;
import info.puzz.trackprofiler.TrackProfilerException;

/**
 * Pomocna klasa za eksternalizaciju stringova. Poruka treba biti zapisana u
 * messages_xy (gdje je xy oznaka jezika) datoteci.
 * 
 * @see info.puzz.trackprofiler.Messages
 * @author Tomo Krajina
 */
public class Message {

    private String message;

    public Message( String message ) {
        this.setMessage( message );
    }

    public String getMessage() {
        if( this.message == null ) {
            this.message = ""; //$NON-NLS-1$
        }
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getLocalizedMessage() {
        try {
            return PropertiesService.getInstance().getPropertyAsTrimmedString(
                    TrackProfilerAppContext.getInstance().getMessagesFile(), this.getMessage() );
        }
        catch ( TrackProfilerException e ) {
            e.printStackTrace();
            return message;
        }
    }

    public String toString() {
        return this.getLocalizedMessage();
    }

    public static String get( String message ) {
        return new Message( message ).getLocalizedMessage();
    }

    /** Kad je u eksternaliziranom strignu nesto za zamijeniti s %%. */
    public static String get( String message, String replacement ) {
        String result = new Message( message ).getLocalizedMessage();
        return ReplaceUtils.replace( result, replacement );
    }

    /** @see #get(String, String) */
    public static String get( String message, String replacement1, String replacement2 ) {
        String result = new Message( message ).getLocalizedMessage();
        return ReplaceUtils.replace( result, replacement1, replacement2 );
    }
    
    public static void main( String[] args ) {
    }

}
