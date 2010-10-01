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
package info.puzz.trackprofiler.gui;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.TrackProfilerAppContext;
import info.puzz.trackprofiler.TrackProfilerException;
import info.puzz.trackprofiler.appobjects.Track;
import info.puzz.trackprofiler.appobjects.TrackExtreeme;
import info.puzz.trackprofiler.appobjects.TrackLoader;
import info.puzz.trackprofiler.appobjects.TrackPoint;
import info.puzz.trackprofiler.appobjects.Waypoint;
import info.puzz.trackprofiler.appobjects.Waypoints;
import info.puzz.trackprofiler.util.Message;
import info.puzz.trackprofiler.util.TrackFileFilter;
import info.puzz.trackprofiler.util.TrackProfilerMath;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by a corporation, company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo. Please visit
 * www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. ************************************* A COMMERCIAL LICENSE
 * HAS NOT BEEN PURCHASED for this machine, so Jigloo or this code cannot be
 * used legally for any corporate or commercial purpose.
 * *************************************
 */
public class TrackProfilerFrame extends javax.swing.JFrame {

    private static final String PLT_EXTENSION = "plt"; //$NON-NLS-1$

    private static final String WPT_EXTENSION = "wpt"; //$NON-NLS-1$

    private JPanel jPanel1;

    private JToolBar topToolBar;
    private JButton aboutButton;
    private JTable trackTable;
    private JScrollPane jScrollPane1;

    private JButton waypointsButton;

    private JButton preferencesButton;

    private JPanel jPanel3;

    private JButton exitButton;

    private JTextField length2DField;

    private JTextField length3DField;

    private JTextField downhillField;

    private JTextField climbingSumField;

    private JLabel length3dLabel;

    private JLabel length2dLabel;

    private JLabel downhillSumLabel;

    private JLabel climbingSumLabel;

    private JTextField lowPointField;

    private JLabel lowPointLabel;

    private JTextField hiPointField;

    private JLabel hiPointLabel;

    private JButton extreemesButton;

    private JButton izravnajButton;

    private JButton resetButton;

    private JButton loadFiles;

    private JPanel jPanel2;

    private ChartPanel chartPanel;

    private Track track;

    /** Ovdje se cuva pocetni track (u slucaju reseta vraca se na njega. */
    private File trackFile;

    /** Waypoints file. */
    private File waypointsFile;

    private boolean showExtreemes = false;

    private static TrackProfilerFrame _instance = null;

    /**
     * Ovdje se cuva direktorij s kojim se je zadnji put pristupilo kod
     * ucitavanja track-a.
     */
    private File folderWithTracks = null;

    /** Redni broj tocke koja je trenutno oznacena. */
    // private int selectedPoint = -1;

    /** Pocetna odabrana tocka u tablici s tockama track-a. */
    private int startSelectedPoints = -1;

    /** Krajnja odabrana tocka. @see #startSelectedPoints */
    private int endSelectedPoints = -1;

    public static void main( String[] args ) {
        TrackProfilerFrame inst = new TrackProfilerFrame();
        inst.setVisible( true );
    }

    private TrackProfilerFrame() {
        super();
        initGUI();
    }

    public static TrackProfilerFrame getInstance() {
        if ( _instance == null ) {
            _instance = new TrackProfilerFrame();
        }
        return _instance;
    }

    private void initGUI() {
        try {
            BorderLayout thisLayout = new BorderLayout();
            this.getContentPane().setLayout( thisLayout );
            setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
            this.setTitle( "Track Profiler " + TrackProfilerAppContext.PROGRAM_VERSION ); //$NON-NLS-1$//$NON-NLS-2$
            this.addWindowListener( new WindowAdapter() {
                public void windowClosing( WindowEvent evt ) {
                    rootWindowClosing( evt );
                }
            } );
            this.getContentPane().add( getJPanel1(), BorderLayout.CENTER );
            this.getContentPane().add( getTopToolBar(), BorderLayout.NORTH );
            this.getContentPane().add( getJPanel2(), BorderLayout.EAST );
            this.setLocation( new java.awt.Point( 100, 100 ) );
            pack();
            this.setSize( 794, 537 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private JPanel getJPanel1() {
        if ( jPanel1 == null ) {
            jPanel1 = new JPanel();
            BorderLayout jPanel1Layout = new BorderLayout();
            jPanel1.setLayout( jPanel1Layout );
            jPanel1.setBorder( BorderFactory.createTitledBorder( "" ) ); //$NON-NLS-1$
            jPanel1.add( getChartPanel() );
        }
        return jPanel1;
    }

    private JPanel getJPanel2() {
        if ( jPanel2 == null ) {
            jPanel2 = new JPanel();
            AnchorLayout jPanel2Layout = new AnchorLayout();
            jPanel2.setLayout(jPanel2Layout);
            jPanel2.setPreferredSize( new java.awt.Dimension( 200, 230 ) );
            jPanel2.add(getJPanel3(), new AnchorConstraint(10,10, 415, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_NONE, AnchorConstraint.ANCHOR_ABS));
            jPanel2.add(getJScrollPane1(), new AnchorConstraint(200,10, 11, 10, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
        }
        return jPanel2;
    }

    public void setTrack( Track track ) throws TrackProfilerException {
        this.track = track;

        this.getTrackTable().setModel( new TrackTableModel( this ) );

        if ( track == null ) {
            this.resetTrackInfo();
        }
        else {
            drawChart();
        }
    }

    protected Track getTrack() {
        return this.track;
    }

    private void computeTrackInfo() throws TrackProfilerException {
        if ( this.track == null ) {
            throw new TrackProfilerException( new Message( Messages.TRACK_NOT_LOADED ).toString() );
        }

        double length2d = this.track.getTrack2DLength();
        double length3d = this.track.getTrack3DLength();
        TrackPoint lowest = this.track.getLowestPoint();
        TrackPoint highest = this.track.getHighestPoint();
        double[] upDown = this.track.getClimbingAndDownhillSum();

        this.getLength2DField().setText( "" + TrackProfilerMath.round( length2d, 1 ) ); //$NON-NLS-1$
        this.getLength3DField().setText( "" + TrackProfilerMath.round( length3d, 1 ) ); //$NON-NLS-1$
        this.getLowPointField()
                .setText( "" + TrackProfilerMath.round( lowest.getElevation(), 1 ) ); //$NON-NLS-1$
        this.getHiPointField().setText(
                "" + TrackProfilerMath.round( highest.getElevation(), 1 ) ); //$NON-NLS-1$
        this.getClimbingSumField().setText( "" + TrackProfilerMath.round( upDown[0], 1 ) ); //$NON-NLS-1$
        this.getDownhillField().setText( "" + TrackProfilerMath.round( upDown[1], 1 ) ); //$NON-NLS-1$
    }

    private void resetTrackInfo() {
        this.getLength2DField().setText( "" ); //$NON-NLS-1$
        this.getLength3DField().setText( "" ); //$NON-NLS-1$
        this.getLowPointField().setText( "" ); //$NON-NLS-1$
        this.getHiPointField().setText( "" ); //$NON-NLS-1$
        this.getClimbingSumField().setText( "" ); //$NON-NLS-1$
        this.getDownhillField().setText( "" ); //$NON-NLS-1$
        this.startSelectedPoints = -1;
        this.endSelectedPoints = -1;
    }

    protected void drawChart() throws TrackProfilerException {
        if ( this.track == null ) {
            throw new TrackProfilerException( "track_not_loaded" ); //$NON-NLS-1$
        }

        this.computeTrackInfo();

        String name = trackFile != null ? trackFile.getName() : "TrackProfiler"; //$NON-NLS-1$

        JFreeChart chart;

        if ( TrackProfilerAppContext.getInstance().isFilledGraph() ) {
            chart = ChartFactory
                    .createXYAreaChart(
                            name,
                            new Message( Messages.LENGTH ).toString(), new Message( Messages.ELEVATION ).toString(), this.toDataset(),
                            PlotOrientation.VERTICAL, true, true, false );
        }
        else {
            chart = ChartFactory
                    .createXYLineChart(
                            name,
                            new Message( Messages.LENGTH ).toString(), new Message( Messages.ELEVATION ).toString(), this.toDataset(),
                            PlotOrientation.VERTICAL, true, true, false );
        }

        chart.setBackgroundPaint( Color.white );

        XYPlot xyplot = (XYPlot) chart.getPlot();
        XYAreaRenderer xyarearenderer = new XYAreaRenderer();
        xyarearenderer.setSeriesPaint(0, new Color(186, 197, 231, 200));

        xyplot.setRenderer(0, xyarearenderer);
        drawSelectedPoints( xyplot );

        if ( this.track.getWaypoints().size() > 0 ) {

            Waypoints markerPoints = new Waypoints();
            markerPoints.addAll( this.track.getWaypoints() );

            for ( int i = 0; i < this.track.getWaypoints().size(); i++ ) {
                Waypoint waypoint = (Waypoint) this.track.getWaypoints().get( i );
                prepareWaypontOnChart( xyplot, waypoint );
            }

            xyplot.setAxisOffset( new RectangleInsets( 5, 5, 5, 5 ) );
            xyplot.getRangeAxis().setUpperMargin( 0.15 );
        }

        if ( showExtreemes ) {
            Vector/* <TrackExtreeme> */extreemes = this.track.findExtremes();
            for ( int i = 0; i < extreemes.size(); i++ ) {
                Waypoint ex = (Waypoint) extreemes.get( i );
                prepareWaypontOnChart( xyplot, ex );
            }
        }

        getChartPanel().setChart( chart );
        getJPanel1().repaint();
    }

    private void drawSelectedPoints( XYPlot xyplot ) {
        if( this.startSelectedPoints >= 0 ) {
            _drawSelectedPoint( xyplot, this.startSelectedPoints );
        }
        if( this.endSelectedPoints >= 0 && this.endSelectedPoints != this.startSelectedPoints ) {
            _drawSelectedPoint( xyplot, this.endSelectedPoints );
        }
    }

    private void _drawSelectedPoint( XYPlot xyplot, int position ) {
        TrackPoint point = this.getTrack().getPointAt( position );

        // Strelicu crtamo samo ako je samo jedna tocka oznacena:
        if ( this.startSelectedPoints < 0 || this.endSelectedPoints < 0 || this.startSelectedPoints == this.endSelectedPoints ) {
            double angle = point.getAngle();
            XYPointerAnnotation xypointerannotation = new XYPointerAnnotation( "", point.getPosition(), //$NON-NLS-1$
                    point.getElevation(), Math.PI - angle );
            xypointerannotation.setTipRadius( 3.0D );
            xypointerannotation.setBaseRadius( 30 );
            xypointerannotation.setTextAnchor( TextAnchor.BASELINE_RIGHT );
            xypointerannotation.setFont( GUIConstants.SANS_SERIF_11 );
            if( angle > 0 ) {
                xypointerannotation.setPaint( Color.red );
            }
            else if( angle < 0 ) {
                xypointerannotation.setPaint( Color.green );
            }
            else {
                xypointerannotation.setPaint( Color.gray );
            }
            xypointerannotation.setText( ( TrackProfilerMath.round( 100 * angle, 1 ) ) + " %" ); //$NON-NLS-1$
            xyplot.addAnnotation( xypointerannotation );
        }

        ValueMarker valuemarker = new ValueMarker( point.getPosition() );
        valuemarker.setLabelOffsetType( LengthAdjustmentType.NO_CHANGE );
        valuemarker.setStroke( new BasicStroke( 1.0F ) );

        if ( this.startSelectedPoints != this.endSelectedPoints && position == this.startSelectedPoints ) {
            valuemarker.setLabelPaint( Color.blue );
            valuemarker.setLabelAnchor( RectangleAnchor.BOTTOM_LEFT );
            valuemarker.setLabelTextAnchor( TextAnchor.BOTTOM_LEFT );

            // Ispisuje udaljenost i kut:
            TrackPoint t1 = this.getTrack().getPointAt(this.startSelectedPoints);
            TrackPoint t2 = this.getTrack().getPointAt(this.endSelectedPoints);
            double distance3D = TrackProfilerMath.round( t1.getPosition() - t2.getPosition(), 1 ) ;
            String angle = Math.abs( TrackProfilerMath.round( t1.getAngle( t2 ) * 100, 1 ) ) + "%"; //$NON-NLS-1$
            String label = Message.get( Messages.SELECTED_DISTANCE_LABEL ) + distance3D + ", " + Message.get( Messages.SELECTED_ANGLE_LABEL ) + angle; //$NON-NLS-1$

            valuemarker.setLabel( "  " + label ); //$NON-NLS-1$
            valuemarker.setLabelFont( GUIConstants.SANS_SERIF_11 );
        }

        xyplot.addDomainMarker( valuemarker );

    }

    private void prepareWaypontOnChart( XYPlot xyplot, Waypoint waypoint ) {
    	Vector positions = waypoint.getPositionsOnTrack();
    	for( int i = 0; i < positions.size(); i++ ) {
            double position = ( (Double) positions.get( i ) ).doubleValue();
            double elevation = waypoint.getElevation();

            if ( position > 0 && waypoint.isVisible() ) {

                String waypointLabel;
                if ( TrackProfilerAppContext.getInstance().isWaypointLabelFromTitle() ) {
                    waypointLabel = waypoint.getTitle();
                }
                else {
                    waypointLabel = waypoint.getDescription();
                }

                double arrowAngle = Math.PI * 1.5;
                if( waypoint.getArrowLength() < 0 ) {
                    arrowAngle += Math.PI;
                }

                XYPointerAnnotation xypointerannotation = new XYPointerAnnotation( waypointLabel, position,
                        elevation, arrowAngle );
                xypointerannotation.setTipRadius( 3.0D );
                xypointerannotation.setBaseRadius( Math.abs( waypoint.getArrowLength() ) );
                xypointerannotation.setFont( GUIConstants.SANS_SERIF_11 );
                xypointerannotation.setPaint( Color.blue );
                xypointerannotation.setTextAnchor( TextAnchor.BASELINE_CENTER );
                xyplot.addAnnotation( xypointerannotation );

                ValueMarker valuemarker = new ValueMarker( position );
                valuemarker.setLabelOffsetType( LengthAdjustmentType.NO_CHANGE );
                valuemarker.setStroke( new BasicStroke( 1.0F ) );

                // TODO: u postavke da li ispisivati ovdje
                if ( false ) {
                    valuemarker.setLabel( waypoint.getTitle() );
                }

                valuemarker.setLabelFont( GUIConstants.SANS_SERIF_11 );

                if ( waypoint instanceof TrackExtreeme ) {
                    valuemarker.setPaint( Color.blue );
                }
                else {
                    valuemarker.setPaint( new Color( 220, 220, 220 ) );
                }
                valuemarker.setLabelPaint( Color.red );
                valuemarker.setLabelAnchor( RectangleAnchor.BOTTOM_LEFT );
                valuemarker.setLabelTextAnchor( TextAnchor.BOTTOM_LEFT );
                // xyplot.addRangeMarker(valuemarker);

                xyplot.addDomainMarker( valuemarker );
            }
    	}
    }

    public void setWaypoints( Waypoints waypoints ) throws TrackProfilerException {
        if ( this.track != null ) {
            this.track.setWaypoints( waypoints );
            this.drawChart();
        }
        else {
            throw new TrackProfilerException( "track_not_loaded" ); //$NON-NLS-1$
        }
    }

    public ChartPanel getChartPanel() {
        if ( this.chartPanel == null ) {
            this.chartPanel = new ChartPanel( null );
            this.chartPanel.setPreferredSize( null );
        }
        return this.chartPanel;
    }

    private JToolBar getTopToolBar() {
        if ( topToolBar == null ) {
            topToolBar = new JToolBar();
            topToolBar.setFloatable( false );
            topToolBar.add( getLoadFiles() );
            topToolBar.add( getResetButton() );
            topToolBar.add( getIzravnajButton() );
            topToolBar.add( getExtreemesButton() );
            topToolBar.add( getWaypointsButton() );
            topToolBar.add( getPreferencesButton() );
            topToolBar.add(getAboutButton());
            topToolBar.add( getExitButton() );
        }
        return topToolBar;
    }

    private JButton getLoadFiles() {
        if ( loadFiles == null ) {
            loadFiles = new JButton();
            loadFiles.setText( new Message( Messages.LOAD ).toString() );
            loadFiles.setFont( GUIConstants.DIALOG_12 );
            loadFiles
                    .setIcon( new ImageIcon( getClass().getClassLoader().getResource( "info/puzz/trackprofiler/icons/fldr_obj.gif" ) ) ); //$NON-NLS-1$
            loadFiles.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    try {
                        loadFilesActionPerformed( evt );
                    }
                    catch ( TrackProfilerException e ) {
                        TrackProfilerFrame.this.showError( new Message( e.getMessage() ) );
                    }
                }
            } );
        }
        return loadFiles;
    }

    private void loadFilesActionPerformed( ActionEvent evt ) throws TrackProfilerException {
        JFileChooser chooser = null;

        if ( this.folderWithTracks != null ) {
            chooser = new JFileChooser( this.folderWithTracks );
        }
        else {
            chooser = new JFileChooser();
        }

        TrackFileFilter filter = new TrackFileFilter();
        filter.addExtension( PLT_EXTENSION );
        filter.addExtension( WPT_EXTENSION );
        filter.setDescription( new Message( Messages.PLT_AND_WPT_FILES ).toString() );
        chooser.setFileFilter( filter );
        chooser.setMultiSelectionEnabled( true );

        int returnVal = chooser.showOpenDialog( this );
        if ( returnVal == JFileChooser.APPROVE_OPTION ) {
            File[] selectedFiles = chooser.getSelectedFiles();

            for ( int i = 0; i < selectedFiles.length; i++ ) {
                File selectedFile = selectedFiles[i];
                this.folderWithTracks = selectedFile.getParentFile();

                if ( selectedFile.getName().toLowerCase().endsWith( "." + PLT_EXTENSION ) ) { //$NON-NLS-1$
                    try {
                        loadTrack( selectedFile );
                    }
                    catch ( FileNotFoundException e ) {
                        throw new TrackProfilerException(
                                new Message( Messages.ERROR_WHEN_LOADING ).toString() + selectedFile.getName() );
                    }
                }
            }

            for ( int i = 0; i < selectedFiles.length; i++ ) {
                File selectedFile = selectedFiles[i];
                this.folderWithTracks = selectedFile.getParentFile();

                if ( selectedFile.getName().toLowerCase().endsWith( "." + WPT_EXTENSION ) ) { //$NON-NLS-1$
                    try {
                        loadWaypoints( selectedFile );
                    }
                    catch ( FileNotFoundException e ) {
                        throw new TrackProfilerException(
                                new Message( Messages.ERROR_WHEN_LOADING ).toString() + selectedFile.getName() );
                    }
                }
            }

        }
    }

    private void loadWaypoints( File selectedFile ) throws FileNotFoundException, TrackProfilerException {
        this.waypointsFile = selectedFile;
        FileInputStream in = new FileInputStream( selectedFile );
        this.setWaypoints( TrackLoader.loadWaypoints( in ) );
    }

    private void loadTrack( File selectedFile ) throws FileNotFoundException, TrackProfilerException {
        this.trackFile = selectedFile;
        FileInputStream in = new FileInputStream( selectedFile );
        this.setTrack( TrackLoader.loadTrack( in ) );
    }

    private JButton getResetButton() {
        if ( resetButton == null ) {
            resetButton = new JButton();
            resetButton.setText( new Message( Messages.RESET ).toString() );
            resetButton.setIcon( new ImageIcon( getClass().getClassLoader().getResource(
                    "info/puzz/trackprofiler/icons/undo_edit.gif" ) ) ); //$NON-NLS-1$
            resetButton.setFont( GUIConstants.DIALOG_12 );
            resetButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    try {
                        resetButtonActionPerformed( evt );
                    }
                    catch ( TrackProfilerException e ) {
                        TrackProfilerFrame.this.showError( new Message( e.getMessage() ) ); //$NON-NLS-1$
                    }
                }
            } );
        }
        return resetButton;
    }

    private void resetButtonActionPerformed( ActionEvent evt ) throws TrackProfilerException {
        try {
            if ( this.trackFile == null ) {
                throw new TrackProfilerException( "track_not_loaded" ); //$NON-NLS-1$
            }
            this.loadTrack( this.trackFile );
            if ( this.waypointsFile != null ) {
                this.loadWaypoints( this.waypointsFile );
            }
        }
        catch ( TrackProfilerException e ) {
            throw e;
        }
        catch ( FileNotFoundException e ) {
            throw new TrackProfilerException( "file_not_found!" ); //$NON-NLS-1$
        }
        this.getChartPanel().repaint();
    }

    private JButton getIzravnajButton() {
        if ( izravnajButton == null ) {
            izravnajButton = new JButton();
            izravnajButton.setText( new Message( Messages.SMOOTH ).toString() );
            izravnajButton.setFont( GUIConstants.DIALOG_12 );
            izravnajButton.setIcon( new ImageIcon( getClass().getClassLoader()
                    .getResource( "info/puzz/trackprofiler/icons/curve.gif" ) ) ); //$NON-NLS-1$
            izravnajButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    try {
                        izravnajButtonActionPerformed( evt );
                    }
                    catch ( TrackProfilerException e ) {
                        TrackProfilerFrame.this.showError( new Message( e.getMessage() ) ); //$NON-NLS-1$
                    }
                }
            } );
        }
        return izravnajButton;
    }

    private void izravnajButtonActionPerformed( ActionEvent evt ) throws TrackProfilerException {
        if ( this.track == null ) {
            throw new TrackProfilerException( "track_not_loaded" ); //$NON-NLS-1$
        }

        // 2x:
        this.track.smoothTrack();
        this.track.smoothTrack();

        this.drawChart();
        this.getTrackTable().setModel( new TrackTableModel( this ) );
    }

    private JButton getExtreemesButton() {
        if ( extreemesButton == null ) {
            extreemesButton = new JButton();
            extreemesButton.setText( new Message( Messages.EXTREMES ).toString() );
            extreemesButton.setFont( GUIConstants.DIALOG_12 );
            extreemesButton.setIcon( new ImageIcon( getClass().getClassLoader().getResource(
                    "info/puzz/trackprofiler/icons/minimum.gif" ) ) ); //$NON-NLS-1$
            extreemesButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    try {
                        extreemesButtonActionPerformed( evt );
                    }
                    catch ( TrackProfilerException e ) {
                        TrackProfilerFrame.this.showError( new Message( e.getMessage() ) );
                    }
                }
            } );
        }
        return extreemesButton;
    }

    private void extreemesButtonActionPerformed( ActionEvent evt ) throws TrackProfilerException {
        if ( this.track == null ) {
            throw new TrackProfilerException( "track_not_loaded" ); //$NON-NLS-1$
        }

        this.showExtreemes = !this.showExtreemes;

        this.drawChart();
    }

    private JLabel getHiPointLabel() {
        if ( hiPointLabel == null ) {
            hiPointLabel = new JLabel();
            hiPointLabel.setText( new Message( Messages.HIGHEST_POINT ).toString() );
            hiPointLabel.setBounds(20, 55, 80, 20);
            hiPointLabel.setFont( GUIConstants.DIALOG_10 );
            hiPointLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return hiPointLabel;
    }

    private JTextField getHiPointField() {
        if ( hiPointField == null ) {
            hiPointField = new JTextField();
            hiPointField.setBounds(100, 55, 70, 20);
            hiPointField.setEditable( false );
        }
        return hiPointField;
    }

    private JLabel getLowPointLabel() {
        if ( lowPointLabel == null ) {
            lowPointLabel = new JLabel();
            lowPointLabel.setText( new Message( Messages.LOWEST_POINT ).toString() ); //$NON-NLS-1$
            lowPointLabel.setFont( GUIConstants.DIALOG_10 );
            lowPointLabel.setBounds( 10, 30, 90, 20 );
            lowPointLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return lowPointLabel;
    }

    private JTextField getLowPointField() {
        if ( lowPointField == null ) {
            lowPointField = new JTextField();
            lowPointField.setBounds( 100, 30, 70, 20 );
            lowPointField.setEditable( false );
        }
        return lowPointField;
    }

    private JLabel getClimbingSumLabel() {
        if ( climbingSumLabel == null ) {
            climbingSumLabel = new JLabel();
            climbingSumLabel.setText( new Message( Messages.UPHILL_SUM ).toString() );
            climbingSumLabel.setFont( GUIConstants.DIALOG_10 );
            climbingSumLabel.setBounds(10, 80, 90, 20);
            climbingSumLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return climbingSumLabel;
    }

    private JLabel getDownhillSumLabel() {
        if ( downhillSumLabel == null ) {
            downhillSumLabel = new JLabel();
            downhillSumLabel.setText( new Message( Messages.DOWNHILL_SUM ).toString() );
            downhillSumLabel.setFont( GUIConstants.DIALOG_10 );
            downhillSumLabel.setBounds(0, 105, 100, 20);
            downhillSumLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return downhillSumLabel;
    }

    private JLabel getLength2dLabel() {
        if ( length2dLabel == null ) {
            length2dLabel = new JLabel();
            length2dLabel.setText( new Message( Messages._2D_LENGTH ).toString() );
            length2dLabel.setFont( GUIConstants.DIALOG_10 );
            length2dLabel.setBounds(30, 130, 70, 20);
            length2dLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return length2dLabel;
    }

    private JLabel getLength3dLabel() {
        if ( length3dLabel == null ) {
            length3dLabel = new JLabel();
            length3dLabel.setText( new Message( Messages._3D_LENGTH ).toString() );
            length3dLabel.setFont( GUIConstants.DIALOG_10 );
            length3dLabel.setBounds(10, 155, 90, 20);
            length3dLabel.setHorizontalAlignment( SwingConstants.RIGHT );
        }
        return length3dLabel;
    }

    private JTextField getClimbingSumField() {
        if ( climbingSumField == null ) {
            climbingSumField = new JTextField();
            climbingSumField.setBounds(100, 80, 70, 20);
            climbingSumField.setEditable( false );
        }
        return climbingSumField;
    }

    private JTextField getDownhillField() {
        if ( downhillField == null ) {
            downhillField = new JTextField();
            downhillField.setBounds(100, 105, 70, 20);
            downhillField.setEditable( false );
        }
        return downhillField;
    }

    private JTextField getLength3DField() {
        if ( length3DField == null ) {
            length3DField = new JTextField();
            length3DField.setBounds(100, 155, 70, 20);
            length3DField.setEditable( false );
        }
        return length3DField;
    }

    private JTextField getLength2DField() {
        if ( length2DField == null ) {
            length2DField = new JTextField();
            length2DField.setBounds(100, 130, 70, 20);
            length2DField.setEditable( false );
        }
        return length2DField;
    }

    private JButton getExitButton() {
        if ( exitButton == null ) {
            exitButton = new JButton();
            exitButton.setText( new Message( Messages.EXIT ).toString() );
            exitButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/delete_edit.gif"))); //$NON-NLS-1$
            exitButton.setFont( GUIConstants.DIALOG_12 );
            exitButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    exitButtonActionPerformed( evt );
                }
            } );
        }
        return exitButton;
    }

    private JPanel getJPanel3() {
        if ( jPanel3 == null ) {
            jPanel3 = new JPanel();
            jPanel3.setBorder( BorderFactory.createTitledBorder( new Message( Messages.TRACK_DETAILS ).toString() ) );
            jPanel3.setLayout( null );
            jPanel3.setPreferredSize(new java.awt.Dimension(180, 185));
            jPanel3.add( getClimbingSumLabel() );
            jPanel3.add( getHiPointField() );
            jPanel3.add( getLowPointLabel() );
            jPanel3.add( getLowPointField() );
            jPanel3.add( getDownhillSumLabel() );
            jPanel3.add( getLength2dLabel() );
            jPanel3.add( getLength3dLabel() );
            jPanel3.add( getClimbingSumField() );
            jPanel3.add( getDownhillField() );
            jPanel3.add( getLength3DField() );
            jPanel3.add( getLength2DField() );
            jPanel3.add( getHiPointLabel() );
        }
        return jPanel3;
    }

    private void exitButtonActionPerformed( ActionEvent evt ) {
        this.close();
    }

    private void rootWindowClosing( WindowEvent evt ) {
        this.close();
    }

    private void close() {
        this.setVisible( false );
        System.exit( 1 );
    }

    private JButton getPreferencesButton() {
        if ( preferencesButton == null ) {
            preferencesButton = new JButton();
            preferencesButton.setText( new Message( Messages.PREFERENCES ).toString() );
            preferencesButton.setIcon( new ImageIcon( getClass().getClassLoader().getResource(
                    "info/puzz/trackprofiler/icons/ok_st_obj.gif" ) ) ); //$NON-NLS-1$
            preferencesButton.setFont( GUIConstants.DIALOG_12 );
            preferencesButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    propertiesButtonActionPerformed( evt );
                }
            } );
        }
        return preferencesButton;
    }

    private void propertiesButtonActionPerformed( ActionEvent evt ) {
        TrackProfilerPreferencesDialog trackProfilerPropertiesDialog = new TrackProfilerPreferencesDialog(
                this );
        trackProfilerPropertiesDialog.setModal( true );
        trackProfilerPropertiesDialog.setVisible( true );
    }

    private JButton getWaypointsButton() {
        if ( waypointsButton == null ) {
            waypointsButton = new JButton();
            waypointsButton.setText( new Message( Messages.WAYPOINTS ).toString() );
            waypointsButton.setFont( GUIConstants.DIALOG_12 );
            waypointsButton.setIcon( new ImageIcon( getClass().getClassLoader().getResource(
                    "info/puzz/trackprofiler/icons/brkpi_obj.gif" ) ) ); //$NON-NLS-1$
            waypointsButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    waypointsButtonActionPerformed( evt );
                }
            } );
        }
        return waypointsButton;
    }

    private void waypointsButtonActionPerformed( ActionEvent evt ) {

        if ( this.track == null ) {
            this.showError( new Message( Messages.TRACK_NOT_LOADED ) );
            return;
        }

        WaypointsTable table = new WaypointsTable();
        table.setVisible( true );
    }

    private void showError( Message message ) {
        JOptionPane.showMessageDialog( this, message.getLocalizedMessage(),
                new Message( Messages.ERROR ).toString(), JOptionPane.WARNING_MESSAGE );
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setBorder( BorderFactory.createTitledBorder( Message.get( Messages.TRACK_POINTS ) ) );
            jScrollPane1.setPreferredSize(new java.awt.Dimension(180, 260));
            jScrollPane1.setViewportView(getTrackTable());
        }
        return jScrollPane1;
    }

    private JTable getTrackTable() {
        if (trackTable == null) {
            trackTable = new JTable();
            trackTable.setModel(new TrackTableModel(this));
            trackTable.addMouseListener(new MouseAdapter() {
                public void mouseReleased( MouseEvent evt ) {
                    TrackProfilerFrame.this.setSelectedPoint();
                }
//                public void mouseClicked(MouseEvent evt) {
//                    TrackProfilerFrame.this.setSelectedPoint();
//                }
            });
            trackTable.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent evt) {
                    TrackProfilerFrame.this.setSelectedPoint();
                }
            });
        }
        return trackTable;
    }

    /** Posebno oznacava tocku na track-u koja je oznacena u tablici. */
    private void setSelectedPoint() {
        int[] _selectedPoints = this.getTrackTable().getSelectedRows();
        if( _selectedPoints == null || _selectedPoints.length == 0 ) {
            this.startSelectedPoints = -1;
            this.endSelectedPoints = -1;
        }
        else {
            // Trazimo prvu i zadnju tocku:
            int _start = _selectedPoints[0];
            int _end = _selectedPoints[_selectedPoints.length - 1];

            if( _start == this.startSelectedPoints && _end == this.endSelectedPoints && _start == _end ) {
                // Ako je odabrana samo jedna tocka - i to ona koja je otprije odabrana => micemo odabir
                this.startSelectedPoints = -1;
                this.endSelectedPoints = -1;
            }
            else {
                this.startSelectedPoints = _start;
                this.endSelectedPoints = _end;
            }
        }

        // Ako su tocke iste kao i stare => necemo prikazati na tablici:

        try {
            this.drawChart();
        }
        catch ( TrackProfilerException e ) {
            this.showError( new Message( e.getMessage() ) );
        }
    }

    /** Vraca Dataset za crtanje grafa od trenutnog track-a. */
    public AbstractXYDataset toDataset() {
        XYSeries xyseries = new XYSeries( new Message( Messages.TRACK_PROFILE ).toString() ); //$NON-NLS-1$

        double ukupno = 0D;
        for ( int i = 1; i < this.getTrack().size(); i++ ) {

            TrackPoint t1 = this.getTrack().getPointAt( i - 1 );
            TrackPoint t2 = this.getTrack().getPointAt( i );
            // System.out.println( "Udaljenost:" + t1.distance( t2 ) );
            ukupno = ukupno + t1.distance2D( t2 );

            // Ne crta tocku ako je izmedju dvije posebno oznacene:
            if ( i > this.startSelectedPoints && i < this.endSelectedPoints ) {
                continue;
            }

            xyseries.add( ukupno, t1.getElevation() );
        }

        return new XYSeriesCollection( xyseries );
    }

    private JButton getAboutButton() {
        if ( aboutButton == null ) {
            aboutButton = new JButton();
            aboutButton.setFont( GUIConstants.DIALOG_12 );
            aboutButton.setIcon( new ImageIcon( getClass().getClassLoader().getResource(
                    "info/puzz/trackprofiler/icons/help_contents.gif" ) ) ); //$NON-NLS-1$
            aboutButton.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent evt ) {
                    aboutButtonActionPerformed( evt );
                }
            } );
            aboutButton.setText( Message.get( Messages.ABOUT ) );
        }
        return aboutButton;
    }

    private void aboutButtonActionPerformed( ActionEvent evt ) {
        JOptionPane.showMessageDialog( this, Message.get( Messages.COPYRIGHT,
                TrackProfilerAppContext.PROGRAM_VERSION ), Message.get( Messages.ABOUT ),
                JOptionPane.INFORMATION_MESSAGE );
    }

}