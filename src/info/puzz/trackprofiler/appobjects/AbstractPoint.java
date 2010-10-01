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
 * Created on 2006.02.13
 */
package info.puzz.trackprofiler.appobjects;

public abstract class AbstractPoint {

    /** Jedna stopa je toliki dio metra. */
    public static final double FEET_METER = 1 / 3.28;
    
    /**
     * Koliko jedan stupanj ima metara. Pretpostavka je da je Zemlja sfera i da
     * se mjeri na ekvatoru.
     */
    public static final double ONE_DEGREE = 1000 * 10000.8 / 90;

    private double latitude;
    private double longitude;

    private double elevation;

//    private String unit = Track.METER;
    
    public AbstractPoint() {
    }

    public AbstractPoint( double latitude, double longitude, double elevation ) {
        this.setLatitude( latitude);
        this.setLongitude( longitude );
        this.setElevation( elevation );
    }

    public AbstractPoint( AbstractPoint point ) {
        if( point != null ) {
            this.setLatitude( point.getLatitude() );
            this.setLongitude( point.getLongitude() );
            this.setElevation( point.getElevation() );
        }
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append( " lat:" ).append( this.getLatitude()); //$NON-NLS-1$
        result.append( ", lon:" ).append( this.getLongitude()  ); //$NON-NLS-1$
        result.append( ", ele:" ).append( this.getElevation()  ); //$NON-NLS-1$
        return result.toString();
    }
    
    public double getElevation() {
//        if( Track.FEET.equals( this.unit ) ) {
//            return this.elevation / 3; // TODO
//        }
        return elevation;
    }

    public void setElevation( double elevation ) {
        this.elevation = elevation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude( double latitude ) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }

    /** Udaljenost izmedju projekcija na karti između ove dvije točke. */
    public double distance2D( AbstractPoint point ) {
        if( point == null ) {
            return -1; // TODO: Exception?
        }

        double coef = Math.cos( this.latitude / 180 * Math.PI );
        double x = this.latitude - point.latitude;
        double y = ( this.longitude - point.longitude ) * coef;

        return Math.sqrt( x * x + y * y ) * ONE_DEGREE;
    }
    
    /** Trodimenzionalna udaljenost između dvije točke. */
    public double distance3D( AbstractPoint point ) {
        if( point == null ) {
            return -1; // TODO: Exception?
        }

        double distance = this.distance2D( point );
        double h = point.getElevation() - this.getElevation();

        return Math.sqrt( distance * distance + h * h );
    }

    /**
     * Vraća koliki je kut između ravnine zemlje i pravca koji spaja te dvije
     * točke.
     */
    public double getAngle( AbstractPoint point ) {
        if( point == null ) {
            return 0; // TODO: Exception?
        }

        double distance = this.distance2D( point );
        double h = point.getElevation() - this.getElevation();

        return Math.atan( h / distance );
    }

    public static void main( String[] args ) {
  }


}
