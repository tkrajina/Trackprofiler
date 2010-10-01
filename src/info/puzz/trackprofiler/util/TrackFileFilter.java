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
package info.puzz.trackprofiler.util;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.filechooser.FileFilter;

/**
 * A convenience implementation of TrackFileFilter that filters out all files except
 * for those type extensions that it knows about.
 * 
 * Extensions are of the type ".foo", which is typically found on Windows and
 * Unix boxes, but not on Macinthosh. Case is ignored.
 * 
 * Example - create a new filter that filerts out all files but gif and jpg
 * image files:
 * 
 * JFileChooser chooser = new JFileChooser(); TrackFileFilter filter = new
 * TrackFileFilter( new String{"gif", "jpg"}, "JPEG & GIF Images")
 * chooser.addChoosableFileFilter(filter); chooser.showOpenDialog(this);
 * 
 * @version 1.8 07/26/04
 * @author Jeff Dinkins
 */
public class TrackFileFilter extends FileFilter {

    private static String TYPE_UNKNOWN = "Type Unknown"; //$NON-NLS-1$

    private static String HIDDEN_FILE = "Hidden File"; //$NON-NLS-1$

    private Hashtable/*<String, FileFilter>*/ filters = null;

    private String description = null;

    private String fullDescription = null;

    private boolean useExtensionsInDescription = true;

    /**
     * Creates a file filter. If no filters are added, then all files are
     * accepted.
     * 
     * @see #addExtension
     */
    public TrackFileFilter() {
        this.filters = new Hashtable/*<String, FileFilter>*/();
    }

    public boolean accept( File f ) {
        if ( f != null ) {
            if ( f.isDirectory() ) {
                return true;
            }
            String extension = getExtension( f );
            if ( extension != null && filters.get( getExtension( f ) ) != null ) {
                return true;
            }
        }
        return false;
    }

    public String getExtension( File f ) {
        if ( f != null ) {
            String filename = f.getName();
            int i = filename.lastIndexOf( '.' );
            if ( i > 0 && i < filename.length() - 1 ) {
                return filename.substring( i + 1 ).toLowerCase();
            }
        }
        return null;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     * 
     * For example: the following code will create a filter that filters out all
     * files except those that end in ".jpg" and ".tif":
     * 
     * TrackFileFilter filter = new TrackFileFilter();
     * filter.addExtension("jpg"); filter.addExtension("tif");
     * 
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension( String extension ) {
        if ( filters == null ) {
            filters = new Hashtable/*<String, FileFilter>*/( 5 );
        }
        filters.put( extension.toLowerCase(), this );
        fullDescription = null;
    }

    /**
     * Returns the human readable description of this filter. For example: "JPEG
     * and GIF Image Files (*.jpg, *.gif)"
     * 
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see TrackFileFilter#getDescription
     */
    public String getDescription() {
        if ( fullDescription == null ) {
            if ( description == null || isExtensionListInDescription() ) {
                fullDescription = description == null ? "(" : description + " ("; //$NON-NLS-1$ //$NON-NLS-2$
                // build the description from the extension list
                Enumeration extensions = filters.keys();
                if ( extensions != null ) {
                    fullDescription += "." + (String) extensions.nextElement(); //$NON-NLS-1$
                    while ( extensions.hasMoreElements() ) {
                        fullDescription += ", ." + (String) extensions.nextElement(); //$NON-NLS-1$
                    }
                }
                fullDescription += ")"; //$NON-NLS-1$
            }
            else {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    /**
     * Sets the human readable description of this filter. For example:
     * filter.setDescription("Gif and JPG Images");
     * 
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     */
    public void setDescription( String description ) {
        this.description = description;
        fullDescription = null;
    }

    /**
     * Determines whether the extension list (.jpg, .gif, etc) should show up in
     * the human readable description.
     * 
     * Only relevent if a description was provided in the constructor or using
     * setDescription();
     * 
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
     */
    public void setExtensionListInDescription( boolean b ) {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription() {
        return useExtensionsInDescription;
    }
}
