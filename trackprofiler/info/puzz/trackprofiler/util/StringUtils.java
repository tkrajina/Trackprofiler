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
 * Created on Aug 2, 2005
 */
package info.puzz.trackprofiler.util;

import java.util.Vector;

/**
 * Par korisnih funkcija koje se vec nalaze u apache.lang utils, ali da se ne
 * vuce cijeli jar zbog njih => koristi se ova klasa.
 * 
 * @author Tomo Krajina
 */
public class StringUtils {
	
    public static final String LETTERS_AND_DIGITS = "qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM1234567890"; //$NON-NLS-1$
    public static final String LETTERS = "qwertzuiopasdfghjklyxcvbnmQWERTZUIOPASDFGHJKLYXCVBNM"; //$NON-NLS-1$
    public static final String DIGITS = "1234567890"; //$NON-NLS-1$

	/**
     * The length used in randomString.
     * 
     * @see #randomString()
     */
    public static final int RANDOM_STRING_LENGTH = 2;

    /**
     * Podijeli string u array po dijelovima koji su ograniceni s delimiterima.
     * U biti se to moze zamijeniti i s String.split(), ali pozeljno je
     * koristiti ovu metodu zbog kompatibilnosti s javom 1.3 !
     */
    public static String[] split( String delimiter, StringBuffer buffer ) {
        if ( buffer == null ) {
            return null;
        }
        Vector ret = new Vector();
        String string = new String( buffer );
        if ( string.length() == 0 ) {
            String[] arr = {};
            return arr;
        }
        else if ( string.indexOf( delimiter ) == -1 ) {
            String[] arr = new String[ 1 ];
            arr[0] = string;
            return arr;
        }
        else {
            // int n = 0;
            String temp;
            while ( string.indexOf( delimiter ) != -1 ) {
                temp = string.substring( 0, string.indexOf( delimiter ) );
                if ( temp.length() > 0 ) {
                    ret.add( temp );
                }
                string = string.substring( string.indexOf( delimiter ) + delimiter.length() );
//                if ( n++ > 10 ) {
//                    break;
//                }
            }
            if ( string.length() > 0 ) {
                ret.add( string );
            }
            String[] arr = new String[ ret.size() ];
            for ( int i = 0; i < ret.size(); i++ ) {
                arr[i] = (String) ret.get( i );
            }
            return arr;
        }
    }

    public static String[] split( String delimiter, String str ) {
        if ( str == null ) {
            return null;
        }
        return split( delimiter, new StringBuffer( str ) );
    }

    /**
     * Zalijepi array stringova u jedan string tako da dijelovi budu ograniceni
     * s delimiterima
     */
    public static String implode( String delimiter, Object[] arr ) {
        StringBuffer ret = new StringBuffer();
        if ( arr.length == 0 ) {
            return ret.toString();
        }
        if ( arr.length == 1 ) {
            return arr[0].toString();
        }
        ret.append( arr[0].toString() );
        for ( int i = 0; i < arr.length - 1; i++ ) {
            ret.append( delimiter + arr[i + 1].toString() );
        }
        return ret.toString();
    }

    /** Spaja niz Stringova s odredjenim delimiterom. */
    public static String implode( String delimiter, Vector v ) {
        return implode( delimiter, v.toArray() );
    }

    /** Provjerava sastoji li se string samo od slova i brojeva ili ne. */
//    public static boolean isAlphanumeric( String string ) {
//        if ( string == null ) {
//            return false;
//        }
//        return string.matches( "[a-zA-Z0-9]*" ); //$NON-NLS-1$
//    }

    /**
     * Return a random string of letters and/or digits. The length of this
     * String is RANDOM_STRING_LENGTH
     */
//    public static String randomString() {
//        return randomString( RANDOM_STRING_LENGTH );
//    }

//    public static String randomString( int length ) {
//        StringBuffer ret = new StringBuffer( length );
//        int s = LETTERS_AND_DIGITS.length();
//        for ( int i = 0; i < length; i++ ) {
//            ret.append( LETTERS_AND_DIGITS.charAt( Util.getSecureRandomInt( s ) ) );
//        }
//        return ret.toString();
//    }

    /** U Stringu ostavlja samo znamenke. */
    public static String removeNonDigits( String string ) {
         return leaveCharacters(string, "1234567890"); //$NON-NLS-1$
    }

    /**
      * Vraca string koji se dobije kad se iz prvog stringa maknu svi znakovi
      * koji se nalaze u drugom.
      */
    public static String removeCharacters( String string, String toRemove ) {
         if( string == null ) {
              return null;
         }
         if (toRemove == null || toRemove.length() == 0) {
               return string;
          }
         StringBuffer result = new StringBuffer();
         for( int i = 0; i < string.length(); i++ ) {
              char ch = string.charAt( i );
              if( toRemove.indexOf( ch ) < 0 ) {
                   result.append( ch );
              }
         }
         return result.toString();
    }
    
    /** Suprotno od removeCharacters */
    public static String leaveCharacters( String string, String leaveCharacters ) {
         if( string == null ) {
              return null;
         }
         if (leaveCharacters == null || leaveCharacters.length() == 0) {
               return ""; //$NON-NLS-1$
          }
         StringBuffer result = new StringBuffer();
         for( int i = 0; i < string.length(); i++ ) {
              char ch = string.charAt( i );
              if( leaveCharacters.indexOf( ch ) >= 0 ) {
                   result.append( ch );
              }
         }
         return result.toString();
    }

}
