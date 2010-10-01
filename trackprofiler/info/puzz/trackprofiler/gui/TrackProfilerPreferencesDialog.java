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
import info.puzz.trackprofiler.Messages;
import info.puzz.trackprofiler.TrackProfilerAppContext;
import info.puzz.trackprofiler.util.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class TrackProfilerPreferencesDialog extends javax.swing.JDialog {
    
    private JPanel waypointsPanel;
    private JTextField waypointMinDistanceField;
    private ButtonGroup waypointsLabelButtonGroup;
    private JButton cancelButton;
    private JButton saveButton;
    private JCheckBox filledTrackCheckBox;
    private JTextField smoothNumberField;
    private JLabel smoothNoLabel;
    private JPanel tracksPanel;
    private JRadioButton waypointFromDescription;
    private JRadioButton waypointFromTitle;
    private JLabel waypointMinDistanceLabel;
    
    private JFrame owner = null;

    private TrackProfilerAppContext tempProperties = TrackProfilerAppContext.getTempInstance();

    /**
    * Auto-generated main method to display this JDialog
    */
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Iterator i = properties.keySet().iterator();
        while( i.hasNext() ) {
            String key = (String) i.next();
            System.out.println( key + "->" + properties.getProperty( key ) ); //$NON-NLS-1$
        }
//        JFrame frame = new JFrame();
//        TrackProfilerPreferencesDialog inst = new TrackProfilerPreferencesDialog(frame);
//        inst.setVisible(true);
    }
    
    public TrackProfilerPreferencesDialog(JFrame frame) {
        super(frame);
        initGUI();

        this.owner = frame;
        this.tempProperties = TrackProfilerAppContext.getTempInstance();
        this.loadValues();
    }

    private void initGUI() {
        try {
            {
                this.setTitle( new Message( Messages.PREFERENCES ).toString() );
                this.setResizable(false);
                this.getContentPane().setLayout(null);
                this.setLocation(new java.awt.Point(200, 200));
                this.getContentPane().add(getWaypointsPanel());
                this.getContentPane().add(getTracksPanel());
                this.getContentPane().add(getSaveButton());
                this.getContentPane().add(getCancelButton());
            }
            this.setSize(328, 284);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel getWaypointsPanel() {
        if (waypointsPanel == null) {
            waypointsPanel = new JPanel();
            waypointsPanel.setBounds(10, 10, 300, 110);
            waypointsPanel.setBorder(BorderFactory.createTitledBorder( new Message( Messages.WAYPOINTS ).toString() ));
            waypointsPanel.setLayout(null);
            waypointsPanel.add(getWaypointMinDistanceLabel());
            waypointsPanel.add(getWaypointMinDistanceField());
            waypointsPanel.add(getWaypointFromTitle());
            waypointsPanel.add(getWaypointFromDescription());
            
            getWaypointsLabelButtonGroup().add(getWaypointFromTitle());
            getWaypointsLabelButtonGroup().add(getWaypointFromDescription());
        }
        return waypointsPanel;
    }
    
    private JLabel getWaypointMinDistanceLabel() {
        if (waypointMinDistanceLabel == null) {
            waypointMinDistanceLabel = new JLabel();
            waypointMinDistanceLabel.setText( new Message( Messages.MIN_DISTANCE_FOR_WAYPOINT ).toString() );
            waypointMinDistanceLabel.setFont( GUIConstants.DIALOG_12 );
            waypointMinDistanceLabel.setBounds(10, 20, 190, 20);
        }
        return waypointMinDistanceLabel;
    }
    
    private JTextField getWaypointMinDistanceField() {
        if (waypointMinDistanceField == null) {
            waypointMinDistanceField = new JTextField();
            waypointMinDistanceField.setBounds(220, 20, 60, 20);
            waypointMinDistanceField.addFocusListener(new FocusAdapter() {
                public void focusLost( FocusEvent evt ) {
                    TrackProfilerPreferencesDialog.this.saveValues();
                }
            });
        }
        return waypointMinDistanceField;
    }
    
    private JRadioButton getWaypointFromTitle() {
        if (waypointFromTitle == null) {
            waypointFromTitle = new JRadioButton();
            waypointFromTitle.setText( new Message( Messages.LABEL_FROM_TITLE ).toString() );
            waypointFromTitle.setBounds(10, 50, 280, 30);
            waypointFromTitle.setFont( GUIConstants.DIALOG_12 );
            waypointFromTitle.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    saveValues();
                }
            });
        }
        return waypointFromTitle;
    }
    
    private JRadioButton getWaypointFromDescription() {
        if (waypointFromDescription == null) {
            waypointFromDescription = new JRadioButton();
            waypointFromDescription.setText( new Message( Messages.LABEL_FROM_DESCRIPTION ).toString() );
            waypointFromDescription.setBounds(10, 70, 280, 30);
            waypointFromDescription.setFont( GUIConstants.DIALOG_12 );
            waypointFromDescription.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    TrackProfilerPreferencesDialog.this.saveValues();
                }
            });
        }
        return waypointFromDescription;
    }
    
    private ButtonGroup getWaypointsLabelButtonGroup() {
        if (waypointsLabelButtonGroup == null) {
            waypointsLabelButtonGroup = new ButtonGroup();
        }
        return waypointsLabelButtonGroup;
    }
    
    private JPanel getTracksPanel() {
        if (tracksPanel == null) {
            tracksPanel = new JPanel();
            tracksPanel.setBounds(10, 120, 300, 90);
            tracksPanel.setBorder(BorderFactory.createTitledBorder( new Message( Messages.TRACKS ).toString() ));
            tracksPanel.setLayout(null);
            tracksPanel.add(getSmoothNoLabel());
            tracksPanel.add(getSmoothNumberField());
            tracksPanel.add(getFilledTrackCheckBox());
        }
        return tracksPanel;
    }
    
    private JLabel getSmoothNoLabel() {
        if (smoothNoLabel == null) {
            smoothNoLabel = new JLabel();
            smoothNoLabel.setText( new Message( Messages.SMOOTH_NO_FROM_COMPUTING ).toString() );
            smoothNoLabel.setBounds(10, 20, 220, 20);
            smoothNoLabel.setFont( GUIConstants.DIALOG_12 );
        }
        return smoothNoLabel;
    }
    
    private JTextField getSmoothNumberField() {
        if (smoothNumberField == null) {
            smoothNumberField = new JTextField();
            smoothNumberField.setBounds(230, 20, 60, 20);
        }
        return smoothNumberField;
    }
    
    private JCheckBox getFilledTrackCheckBox() {
        if (filledTrackCheckBox == null) {
            filledTrackCheckBox = new JCheckBox();
            filledTrackCheckBox.setText( new Message( Messages.FILLED_CHART ).toString() );
            filledTrackCheckBox.setBounds(10, 50, 280, 30);
            filledTrackCheckBox.setFont(GUIConstants.DIALOG_12);
            filledTrackCheckBox.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    TrackProfilerPreferencesDialog.this.saveValues();
                }
            });
        }
        return filledTrackCheckBox;
    }
    
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText( new Message( Messages.SAVE ).toString() );
            saveButton.setBounds(110, 220, 90, 20);
            saveButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/save_edit.gif"))); //$NON-NLS-1$
            saveButton.setFont(GUIConstants.DIALOG_12);
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    saveButtonActionPerformed(evt);
                }
            });
        }
        return saveButton;
    }
    
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText( new Message( Messages.CANCEL ).toString() );
            cancelButton.setBounds(210, 220, 100, 20);
            cancelButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/delete_edit.gif"))); //$NON-NLS-1$
            cancelButton.setFont(GUIConstants.DIALOG_12);
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });
        }
        return cancelButton;
    }
    
    private void loadValues() {
        this.getWaypointMinDistanceField().setText( "" + this.tempProperties.getWaypointMinDistance() ); //$NON-NLS-1$

        this.getWaypointFromTitle().setSelected( this.tempProperties.isWaypointLabelFromTitle() );
        this.getWaypointFromDescription().setSelected( !this.tempProperties.isWaypointLabelFromTitle() );

        this.getSmoothNumberField().setText( "" + this.tempProperties.getComputingSmoothNumber() ); //$NON-NLS-1$
        this.getFilledTrackCheckBox().setSelected( this.tempProperties.isFilledGraph() );
    }
    
    private void saveValues() {
        try {
            this.tempProperties.setWaypointMinDistance( Integer.parseInt( this.getWaypointMinDistanceField()
                    .getText() ) );
            if( getWaypointFromTitle().isSelected() ) {
                this.tempProperties.setWaypointLabelFromTitle( true );
            }
            else if( getWaypointFromDescription().isSelected() ) {
                this.tempProperties.setWaypointLabelFromTitle( false );
            }
            this.tempProperties.setComputingSmoothNumber( Integer.parseInt( this.getSmoothNumberField()
                    .getText() ) );
            this.tempProperties.setFilledGraph( this.getFilledTrackCheckBox().isSelected() );
        }
        catch ( Exception e ) {
            JOptionPane.showMessageDialog( this, new Message( Messages.VALUE_ERROR ).toString(), new Message( Messages.ERROR ).toString(),
                    JOptionPane.WARNING_MESSAGE );
            this.loadValues();
        }
    }
    
    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.setVisible( false );
    }
    
    private void saveButtonActionPerformed(ActionEvent evt) {
        // Snima se u datoteku i sprema u kontekst:

        TrackProfilerAppContext.getInstance().setProperties( this.tempProperties.getProperties() );

        try {
            this.tempProperties.getProperties().store(
                    new FileOutputStream( TrackProfilerAppContext.PROPERTIES_FILE_NAME ), "TrackerProfiler" ); //$NON-NLS-1$
        }
        catch ( Exception e ) {
            JOptionPane.showMessageDialog( this, new Message( Messages.PREFERENCES_SAVE_ERROR ).toString(), new Message( Messages.ERROR ).toString(),
                    JOptionPane.WARNING_MESSAGE );
        }
        
        this.setVisible( false );
        
        if ( this.owner != null && this.owner instanceof TrackProfilerFrame ) {
            try {
                ( (TrackProfilerFrame) this.owner ).drawChart();
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

}
