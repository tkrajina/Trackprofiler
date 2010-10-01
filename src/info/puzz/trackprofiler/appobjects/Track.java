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
package info.puzz.trackprofiler.appobjects;

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.TrackProfilerAppContext;
import info.puzz.trackprofiler.TrackProfilerException;
import info.puzz.trackprofiler.util.Message;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

/**
 * Sadrzi sve podatke o nekom track-u.
 *
 * @see info.puzz.trackprofiler.appobjects.TrackLoader
 */
public class Track {

    // TODO: Sve ostale podatke iz headera!

    private String title = ""; //$NON-NLS-1$

    private Waypoints waypoints;

    private Vector/*<TrackPoint>*/ trackPoints = new Vector/*<TrackPoint>*/();

    public static void main( String[] args ) {
    }

    public Track() {
        super();
    }

    public Vector/*<TrackPoint>*/ getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints( Vector/*<TrackPoints>*/ trackPoints ) {
        if( trackPoints == null ) {
            return;
        }
        this.trackPoints = trackPoints;
        this.refreshData();
    }

    public int size() {
        return trackPoints.size();
    }

    public TrackPoint getPointAt( int i ) {
        return (TrackPoint) trackPoints.get( i );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * "Izravnava" track, tj. smanjuje visinske razmake izmedju susjednih
     * tocaka.
     */
    public void smoothTrack() {
        if ( this.size() < 3 ) {
            return;
        }
        double[] elevations = getSmoothedElevations();

        for ( int i = 0; i < this.size(); i++ ) {
            this.getPointAt( i ).setElevation( elevations[i] );
        }

        this.refreshData();
        // Nakon svega resetiramo ekstreme:
//        this.extreemes = null;
    }

    /** Vraca samo array s "izravnatim" visinama pripadnih tocaka track-a. */
    private double[] getSmoothedElevations() {
        double[] elevations = new double[ this.size() ];
        for ( int i = 0; i < this.size(); i++ ) {
            elevations[i] = this.getPointAt( i ).getElevation();
        }
        return getSmoothedElevations( elevations );
    }

    private double[] getSmoothedElevations( double[] elevations ) {
        double[] result = new double[ elevations.length ];
        for ( int i = 0; i < elevations.length; i++ ) {
            result[i] = elevations[i];
        }

        // Prvog i zadnjeg posebno:
        result[0] = ( 0.3 * result[1] + 0.4 * result[0] ) / 0.7;
        result[elevations.length - 1] = ( 0.3 * result[elevations.length - 2] + 0.4 * result[elevations.length - 1] ) / 0.7;

        for ( int i = 1; i < this.size() - 1; i++ ) {
            double elevation = 0.3 * elevations[ i - 1 ] + 0.4 * elevations[ i ] + 0.3 * elevations[ i + 1 ];

            result[ i ] = elevation;
        }
        return result;
    }

    /**
     * Traži ekstreme nekog track-a.
     * @see info.puzz.trackprofiler.TrackProfilerAppContext#getComputingSmoothNumber()
     */
    public Vector/*<TrackExtreeme>*/ findExtremes() {
        Vector/*<TrackExtreeme>*/ result = new Vector/*<TrackExtreeme>*/();

        if ( this.size() < 3 ) {
            return result;
        }

        // Prvo cemo nekoliko puta izravnati krivulju tako da ne bi dobili puno
        // besmislenih malih minimuma:
        double[] elevations = this.getSmoothedElevations();
        for ( int i = 0; i < Math.abs( TrackProfilerAppContext.getInstance().getComputingSmoothNumber() ); i++ ) {
            elevations = this.getSmoothedElevations( elevations );
        }

        // Ekstrem je tocka za koju vrijedi:
        // Obje njegove susjedne tocke su nize (ili vise) od njega)

        // TODO: Napraviti da gleda dvije susjedne sa svake strane!

        // Prva i zadnja tocka ne mogu biti minimumi.
        for ( int i = 1; i < elevations.length - 1; i++ ) {
            double previous = elevations[ i - 1 ];
            double _this = elevations[ i ];
            double next = elevations[ i + 1 ];

            TrackExtreeme te = null;
            if ( previous < _this && next < _this ) {
                TrackPoint point = this.getPointAt( i );
                te = new TrackExtreeme( point.getLatitude(), point.getLongitude(), point
                        .getElevation() );
                te.setMaximum();
                result.add( te );
            }
            else if ( previous > _this && next > _this ) {
                TrackPoint point = this.getPointAt( i );
                te = new TrackExtreeme( point.getLatitude(), point.getLongitude(), point
                        .getElevation() );
                te.setMinimum();
                result.add( te );
            }

            if( te != null ) {
            	Vector positions = this.getWaypointPositions( te );
            	// Ekstrem moze imati samo jednu poziciju:
            	if( positions.size() > 0 ) {
                    te.addPositionOnTrack( ( (Double) positions.get( 0 ) ).doubleValue() );
            	}
            }
        }

        return result;
    }

    /**
     * Vraca array s dva elementa; prvi je ukupno uspona, a drugi je ukupno
     * silazaka.
     */
    public double[] getClimbingAndDownhillSum() throws TrackProfilerException {
        double up = 0;
        double down = 0;

        if ( this.size() < 3 ) {
            return new double[] { 0, 0 };
        }

        Vector/*<TrackPoint>*/ points = new Vector/*<TrackPoint>*/();
        points.add( this.getPointAt( 0 ) );
        points.addAll( this.findExtremes() );
        points.add( this.getPointAt( this.size() - 1 ) );

        for ( int i = 1; i < points.size(); i++ ) {
            AbstractPoint d1 = (AbstractPoint) points.get( i - 1 );
            AbstractPoint d2 = (AbstractPoint) points.get( i );

            if ( d1.getElevation() < d2.getElevation() ) {
                up += d2.getElevation() - d1.getElevation();
            }
            else {
                down += d1.getElevation() - d2.getElevation();
            }
        }

        return new double[] { up,  down };
    }

    /** @see #getTrack3DLength() */
    public double getTrack2DLength() {
        if( this.size() < 2 ) {
            return 0;
        }
        double result = 0;

        for( int i = 1; i < this.size(); i++ ) {
            TrackPoint previous = this.getPointAt( i - 1 );
            TrackPoint point = this.getPointAt( i );
            result += point.distance2D( previous );
        }

        return result;
    }

    /**
     * Duljina puta, ali mjeri se i pomak u visinu (dakle, ne samo duljina
     * tlocrta na karti).
     *
     * @see #getTrack2DLength()
     */
    public double getTrack3DLength() {
        if( this.size() < 2 ) {
            return 0;
        }
        double result = 0;

        for( int i = 1; i < this.size(); i++ ) {
            TrackPoint previous = this.getPointAt( i - 1 );
            TrackPoint point = this.getPointAt( i );
            double distance = point.distance2D( previous );
            double elevation = point.getElevation() - previous.getElevation();

            result += Math.sqrt( distance * distance + elevation * elevation );
        }

        return result;
    }

    /** Trazi najvisu tocku. */
    public TrackPoint getHighestPoint() throws TrackProfilerException {
        if( this.size() == 0 ) {
            // TODO
           throw new TrackProfilerException( new Message( Messages.TRACK_NOT_LOADED ).toString() );
        }
        TrackPoint point = this.getPointAt( 0 );

        for( int i = 0; i < this.size(); i++ ) {
            TrackPoint current = this.getPointAt( i );
            if( current.getElevation() > point.getElevation() ) {
                point = current;
            }
        }

        return point;
    }

    /** Trazi najnizu tocku. */
    public TrackPoint getLowestPoint() throws TrackProfilerException {
        if( this.size() == 0 ) {
            // TODO
            throw new TrackProfilerException( new Message( Messages.TRACK_NOT_LOADED ).toString() );
        }
        TrackPoint point = this.getPointAt( 0 );

        for( int i = 0; i < this.size(); i++ ) {
            TrackPoint current = this.getPointAt( i );
            if( current.getElevation() < point.getElevation() ) {
                point = current;
            }
        }

        return point;
    }

    /**
     * Nalazi na koliko metara od pocetka track-a se nalazi waypoint. Za
     * udaljenost ce vratiti -1 ako je waypoint predaleko od bilo koje tocke
     * track-a!
     *
     * Rezultat je vektor kojemu je svaki clan array od polozaja na tracku
     * (udaljenost od pocetka) i visine.
     */
    public Vector getWaypointPositionsAndElevations( Waypoint waypoint ) {
    	Vector result = new Vector();

        // Sluzi za cuvanje kandidata za najblizu tocku track-a
        double tempDistanceFromPoint = 100000000;
        double tempTrackLength = -1;
        double tempElevation = -1;

        double trackLength = 0;

        // Petlja:
        for ( int i = 0; i < this.size(); i++ ) {
            TrackPoint tempPoint = this.getPointAt( i );
            if ( i == 0 ) {
                tempDistanceFromPoint = tempPoint.distance2D( waypoint );
                tempElevation = tempPoint.getElevation();
            }
            else {
                trackLength += this.getPointAt( i - 1 ).distance2D( this.getPointAt( i ) );
            }

            double distance = tempPoint.distance2D( waypoint );
            if ( distance <= tempDistanceFromPoint && distance <= TrackProfilerAppContext.getInstance().getWaypointMinDistance() ) {
                tempDistanceFromPoint = distance;
                tempTrackLength = trackLength;
                tempElevation = tempPoint.getElevation();
            }

            // Ako smo izasli od minimalne udaljenosti:
            if( distance > TrackProfilerAppContext.getInstance().getWaypointMinDistance() && tempTrackLength > 0 ) {
            	result.add( new double[] { tempTrackLength, tempElevation } );

            	System.out.println( "Dodan " + waypoint.getTitle() + " na " + tempTrackLength );

            	// I resetiramo privremeno varijable:
                tempDistanceFromPoint = 100000000;
                tempTrackLength = -1;
                tempElevation = -1;
            }
        }

        return result;
    }

    public Vector getWaypointPositions( Waypoint waypoint ) {
    	Vector positions = getWaypointPositionsAndElevations( waypoint );
    	Vector result = new Vector();
    	for( int i = 0; i < positions.size(); i++ ) {
    		double[] p = (double[]) positions.get( i );
    		result.add( new Double( p[ 0 ] ) );
    	}
    	return result;
    }

    public Waypoints getWaypoints() {
        if( this.waypoints == null ) {
            this.waypoints = new Waypoints();
        }
        return waypoints;
    }

    public void addWaypoints( Waypoints wpts ) {
        Waypoints _waypoints = this.getWaypoints();
        _waypoints.add( wpts );
        this.setWaypoints( _waypoints );
    }

    public void setWaypoints( Waypoints waypoints ) {
        if( waypoints == null ) {
            return;
        }
        this.waypoints = waypoints;

        refreshData();
    }

    /**
     * Izracunava visinu waypointa ovisno o tome gdje se on nalazi na track-u.
     * Treba pozvati svaki put nakon izravnavanja grafa.
     */
    private void refreshData() {
        if ( this.waypoints != null ) {
//            Waypoints _waypoints = new Waypoints();
            for ( int i = 0; i < this.waypoints.size(); i++ ) {
                Waypoint wpt = (Waypoint) this.waypoints.get( i );
                Vector positionsElevations = this.getWaypointPositionsAndElevations( wpt );
                for( int j = 0; j < positionsElevations.size(); j++ ) {
                	double[] pe = (double[]) positionsElevations.get( j );
                    if ( pe[0] > 0 ) {
                        wpt.addPositionOnTrack( pe[0] );
                        wpt.setElevation( pe[1] );
                    }
                }
            }
//            this.waypoints = _waypoints;
        }

        this.calculatePositionsAndAngles();
    }

    // @SuppressWarnings("unchecked") //$NON-NLS-1$
    public void sortWaypointsByDistance() {
        Waypoints waypoints = this.getWaypoints();
        Collections.sort( waypoints, new Comparator() {

            public int compare( Object o1, Object o2 ) {
                Waypoint w1 = (Waypoint) o1;
                Waypoint w2 = (Waypoint) o2;
                if( w1.getFirstPositionOnTrack() == w2.getFirstPositionOnTrack() ) {
                    return 0;
                }
                else if( w1.getFirstPositionOnTrack() > w2.getFirstPositionOnTrack() ) {
                    return 1;
                }
                return -1;
            }
        } );
    }

    private void calculatePositionsAndAngles() {
        double position = 0;
        for( int i = 0; i < this.trackPoints.size(); i++ ) {
            if( i > 0 ) {
                TrackPoint previous = (TrackPoint) this.trackPoints.get( i - 1);
                TrackPoint current = (TrackPoint) this.trackPoints.get( i );
                position = position + previous.distance2D( current );
                current.setPosition( position );
            }
            if( i > 0 && i < this.trackPoints.size() - 2 ) {
                TrackPoint previous = (TrackPoint) this.trackPoints.get( i - 1);
                TrackPoint current = (TrackPoint) this.trackPoints.get( i );
                TrackPoint next = (TrackPoint) this.trackPoints.get( i + 1 );
                current.setAngle( previous.getAngle( next ) );
            }
        }
    }

}