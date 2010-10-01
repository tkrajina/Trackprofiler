/*
 * Created on 2005.12.06
 */
package info.puzz.trackprofiler.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomo Krajina
 */
public class ReplaceUtils {

    /**
     * Ako je true onda ce isprazniti %%-ove i %key%-ove koji se nisu zamijenili
     * s necim drugim.
     */
    private static final boolean EMPTY_NOT_REPLACED = true;

    private static final char DELIMITER = '%';

    private static final String DELIMITER_AS_STRING = "" + DELIMITER; //$NON-NLS-1$

    // private static final String DELIMITER_1 = "" + DELIMITER_2 + DELIMITER_2; //$NON-NLS-1$

    private static final String DELIMITER_REPLACEMENT = "\\\\" + DELIMITER; //$NON-NLS-1$
   
    /** Ova klasa ima samo static metode. */
    private ReplaceUtils() {}

    /**
     * String koji ima u sebi podstringove tipa %% i array objekata koji ce se
     * redom zamijeniti s %%-ovima.<br>
     * Ako u stringu treba negdje ostati podstring %% onda ga je potrebno
     * zapisati u obliku: \%\% <br>
     */
    public static String replace(String string, Object[] objects) {
        /*
         * Ovo ide s petljom jer se s replaceAll( "%%", replacement ) moze
         * desiti da replacement u sebi ima string %% - tada nastaju problemi.
         */
        if (string == null || objects == null || string.length() == 0) {
            return string;
        }
        StringBuffer result = new StringBuffer();
        int current = 0;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt( i );
            if( c == DELIMITER && i < string.length() - 1 && string.charAt( i + 1 ) == DELIMITER ) {
                ++i;
                if( current < objects.length ) {
                    result.append( objects[ current ] );
                    ++current;
                }
            }
            else if( i < string.length() ) {
                result.append( c );
            }
        }
        String resultStr = result.toString();

        // Da ne bi slucajno ostao jos neki "\%" u stringu:
        if( resultStr.indexOf( DELIMITER ) > 0 ) {
            return _replaceDelimiters( resultStr );
        }

        return resultStr;
    }

    /**
     * String koji ima podstringove oblika %ime1%, %ime2% zamijenjuje sa
     * vrijednostima koje nadje u Map-i s kljucevima "ime1", "ime2", itd.<br>
     */
    public static String replace(String string, Map parameters) {
        if (string == null || parameters == null || string.length() == 0
                || parameters.size() == 0) {
            return string;
        }
       
        StringBuffer result = new StringBuffer();
        StringBuffer currentKey = new StringBuffer();
        boolean key = false;
        boolean keyReplaced = false;
       
        for (int position = 0; position < string.length(); position++) {
            keyReplaced = false;
            char c = string.charAt( position );

            // Ako je "\" ispred DELIMITER-a onda se ignorira:
            if( ( position == 0 && c == DELIMITER ) ||
                    ( position > 0 && string.charAt( position - 1 ) != '\\' && c == DELIMITER ) ) {
                key = ! key;
                keyReplaced = true;
            }
           
            if( key && c != DELIMITER ) {
                currentKey.append( c );
            }
            else if( ! keyReplaced ) {
                result.append( c );
            }
           
            if( ! key && currentKey.length() != 0 ) {
                Object replacement = parameters.get( currentKey.toString() );
                String _replacement = replacement == null ? "" : String.valueOf( replacement ); //$NON-NLS-1$
                result.append( _replacement );
                currentKey = new StringBuffer();
            }
        }

        return result.toString();
    }

    /** Mijenja "\\%" u "%" */
    private static String _replaceDelimiters(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        if (string.indexOf(DELIMITER) > 0) {
            string = string.replaceAll(DELIMITER_REPLACEMENT,
                    DELIMITER_AS_STRING);
        }
        if (EMPTY_NOT_REPLACED) {
            return string.replaceAll("%%", ""); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return string;
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1) {
        return replace(string, new Object[] { o1 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2) {
        return replace(string, new Object[] { o1, o2 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2, Object o3) {
        return replace(string, new Object[] { o1, o2, o3 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4) {
        return replace(string, new Object[] { o1, o2, o3, o4 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8 });
    }

    /** @see #replace(String, Object[]) */
    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10, Object o11) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10, o11 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10, Object o11, Object o12) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10, o11, o12 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10, Object o11, Object o12, Object o13) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10, o11, o12, o13 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10, Object o11, Object o12, Object o13,
            Object o14) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10, o11, o12, o13, o14 });
    }

    public static String replace(String string, Object o1, Object o2,
            Object o3, Object o4, Object o5, Object o6, Object o7, Object o8,
            Object o9, Object o10, Object o11, Object o12, Object o13,
            Object o14, Object o15) {
        return replace(string, new Object[] { o1, o2, o3, o4, o5, o6, o7, o8,
                o9, o10, o11, o12, o13, o14, o15 });
    }

    public static void main(String[] args) {

//        String s = "------%%------%%------"; //$NON-NLS-1$
//        s = ReplaceUtils.replace( s, "___%%___", "xx" ); //$NON-NLS-1$ //$NON-NLS-2$
//        System.out.println(s);
       
//        // Primjer 1. metoda replace() pomocu property-ija iz Map-e
        HashMap params = new HashMap();
        params.put("test", "Ovdje je test");  //$NON-NLS-1$//$NON-NLS-2$
        params.put("drugi", "drugi test"); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println(replace("Ovdje je '%test%', a ovdje '\\%drugi%'... jos jednom: %drugiiii%!!!", //$NON-NLS-1$
                params));

        // Primjer 2. replace() pomocu podstringova %%
//        System.out.println(replace(
//                "update %% set a='%%', b=%%, c='%%' where id='%%' and %%", "tablica", //$NON-NLS-1$ //$NON-NLS-2$
//                "aaaaa13", "13", "asd", "ajdi")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    }

}