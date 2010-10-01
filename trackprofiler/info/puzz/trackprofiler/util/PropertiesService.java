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
 * Created on Mar 12, 2005
 */
package info.puzz.trackprofiler.util;

import info.puzz.trackprofiler.TrackProfilerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Sluzi za citanje iz raznih properties datoteka. Osim citanja, ovaj singleton
 * se koristi i kao cache tih datoteka (tako da ih nije potrebno vise puta
 * citati).
 * 
 * Ova datoteka po defaultu koristi svoj nacin ucitavanja .properties s
 * pretpostavkom da je encoding UTF8. Ukoliko se zeli drukcije potrebno je prije
 * koristenja postaviti <code>setLoadPropertiesAsUTF8( true );</code>
 * 
 * @author Tomo Krajina
 */
public class PropertiesService {
    
    /**
	 * Zeli li se da se properties datoteka ucitava kao UTF8 ili defaultno po
	 * javi.
	 */
	private boolean loadPropertiesAsUTF8 = true;
	
    // -------------------------------------------------------------------
    // singleton:
    // -------------------------------------------------------------------

    private static PropertiesService _instance = null;

    private PropertiesService() {}

    public static PropertiesService getInstance() {
        if ( _instance == null )
            _instance = new PropertiesService();
        return _instance;
    }

    // ------------------------------------------------------------------------

    private Map/*<String, Map>*/ allProperties = new HashMap/*<String, Map>*/();

    // ------------------------------------------------------------------------

    /**
     * Vraca properties koje se nalaze u <home>/propertiesName.properties (nije
     * potrebno stavljati ekstenziju kod poziva funkcije!)
     */
    public Properties getProperties( String propertiesName ) throws TrackProfilerException {
    	if( propertiesName == null ) {
            // Samo za LOG
    		throw new TrackProfilerException( "Nepostojeća .properties datoteka: <null>!" ); //$NON-NLS-1$
    	}
    	String fileName = null;
//    	if( propertiesName.endsWith( ".properties" ) ) {
    		fileName = propertiesName;
//    	}
//    	else {
//    		fileName = propertiesName + ".properties";
//    	}
    	fileName = fileName.trim();
    	if( ! fileName.startsWith( "/" ) ) { //$NON-NLS-1$
    		fileName = "/" + fileName; //$NON-NLS-1$
    	}
        if ( allProperties.containsKey( propertiesName ) ) {
            return (Properties) allProperties.get( propertiesName );
        }
        try {
        	Properties newProperties = null;
        	InputStream in = getClass().getResourceAsStream( fileName );
        	if( in == null ) {
        		throw new TrackProfilerException( "Nepostojeća .properties datoteka:" + fileName ); //$NON-NLS-1$
        	}
        	if( loadPropertiesAsUTF8 ) {
	        	newProperties = loadUTF8Properties( in );
        	}
        	else {
        		newProperties = new Properties();
				newProperties.load( in );
        	}
            allProperties.put( propertiesName, newProperties );
            return newProperties;
        }
        catch ( IOException e ) {
            throw new TrackProfilerException( e.getMessage() );
        }
    }

    public String getProperty( String propertiesFile, String propertyName ) throws TrackProfilerException {
        return getProperties( propertiesFile ).getProperty( propertyName );
    }

    public String getPropertyAsTrimmedString( String propertiesFile, String propertyName ) throws TrackProfilerException {
        return this.getProperty( propertiesFile, propertyName ).trim();
    }

    public int getPropertyAsInteger( String propertiesFile, String propertyName )
            throws TrackProfilerException {
        try {
            return Integer
                    .parseInt( this.getPropertyAsTrimmedString( propertiesFile, propertyName ) );
        }
        catch ( Exception e ) {
            throw new TrackProfilerException( e.getMessage() );
        }
    }
    
    /**
     * Ucitava .properties datoteku ali s UTF8 encodingom.
     * TODO: Jos malo srediti i istestirati.
     */
    public static Properties loadUTF8Properties( InputStream in ) throws IOException, TrackProfilerException {
    	if( in == null ) {
    		throw new TrackProfilerException( "..." ); //$NON-NLS-1$
    	}
    	BufferedReader reader = new BufferedReader( new InputStreamReader( in, "UTF8" ) ); //$NON-NLS-1$
    	Properties result = new Properties();
    	while( true ) {
    		String line = reader.readLine();
    		if( line == null ) {
    			break;
    		}
    		if( line.trim().length() == 0 ) {
    			continue;
    		}
    		String propertyName = null;
    		String propertyValue = null;
    		if( line.trim().startsWith( "#" ) || line.trim().startsWith( "!" ) ) { //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-2$
    			continue;
    		}
            
            line = line.trim();
            int eqPosition = line.indexOf( '=' );
            if( eqPosition < 0 ) {
                throw new TrackProfilerException( "Greška!" ); //$NON-NLS-1$
            }

            propertyName = line.substring( 0, eqPosition ).trim();
    		propertyValue = line.substring( eqPosition + 1 ).trim();
    		if( propertyValue.endsWith( "\\" ) ) { //$NON-NLS-1$
    			while( true ) {
    				String newLine = reader.readLine();
    				if( newLine == null ) {
    					break;
    				}
    				propertyValue += newLine; // TODO: maknuti "\" na kraju
    				if( ! newLine.endsWith( "\\" ) ) { //$NON-NLS-1$
    					break;
    				}
    			}
    		}
    		result.put( propertyName, propertyValue );
    	}
    	return result;
    }

	public boolean isLoadPropertiesAsUTF8() {
		return loadPropertiesAsUTF8;
	}

	public void setLoadPropertiesAsUTF8(boolean loadPropertiesAsUTF8) {
		this.loadPropertiesAsUTF8 = loadPropertiesAsUTF8;
	}

    // ------------------------------------------------------------------------

    public static void main( String[] args ) throws Exception {
    }

}

