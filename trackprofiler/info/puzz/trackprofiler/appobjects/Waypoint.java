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
 * Created on 2006.01.24
 */
package info.puzz.trackprofiler.appobjects;

import java.util.Vector;

public class Waypoint extends AbstractPoint {

    private String title;
    private String description;
    private int arrowLength = 20;

    private Vector positionsOnTrack = new Vector();

    private boolean visible = true;

    public Waypoint() {
    }

    /** Kopija vec postojeceg Waypoint-a. */
    public Waypoint( Waypoint wpt ) {
        super( wpt );
        if( wpt != null ) {
            this.setTitle( wpt.getTitle() );
            this.setDescription( wpt.getDescription() );
            this.setArrowLength( wpt.getArrowLength() );
        }
    }

//    public Waypoint( BigDecimal latitude, BigDecimal longitude, double elevation ) {
//        super( latitude, latitude, elevation );
//    }

    public Waypoint( double latitude, double longitude, double elevation ) {
        super( latitude, longitude, elevation );
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }


    public boolean isVisible() {
        return visible;
    }


    public void setVisible( boolean visible ) {
        this.visible = visible;
    }


    public Vector getPositionsOnTrack() {
        return positionsOnTrack;
    }

    public double getFirstPositionOnTrack() {
    	if( positionsOnTrack != null && positionsOnTrack.size() > 0 ) {
    		return ( ( Double ) positionsOnTrack.get( 0 ) ).doubleValue();
    	}
    	return 10000000000000000D;
    }

    /**
     * @return Na kojoj udaljenosti od pocetka se nalazi waypoint ili -1 ako
     *         nije nadjen na trasi.
     */
    public void setPositionsOnTrack( Vector positionsOnTrack ) {
        this.positionsOnTrack = positionsOnTrack;
    }

    public void addPositionOnTrack( double position ) {
    	Double dPos = new Double( position );
    	if( ! getPositionsOnTrack().contains( dPos ) ) {
        	getPositionsOnTrack().add( dPos );
    	}
    }

    public int getArrowLength() {
        return arrowLength;
    }

    /** Duljina strelice s kojom se prikaze waypoint na grafu. */
    public void setArrowLength( int arrowLength ) {
        this.arrowLength = arrowLength;
    }

}