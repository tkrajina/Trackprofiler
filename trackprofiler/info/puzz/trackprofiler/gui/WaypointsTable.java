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
import info.puzz.trackprofiler.TrackProfilerException;
import info.puzz.trackprofiler.appobjects.Waypoint;
import info.puzz.trackprofiler.appobjects.Waypoints;
import info.puzz.trackprofiler.util.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
public class WaypointsTable extends javax.swing.JDialog {
	private JButton okButton;
    private JTable waypointsTable;
    private JScrollPane jScrollPane1;
    private JButton showAllButton;
    private JButton hideAllButton;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		WaypointsTable inst = new WaypointsTable();
		inst.setVisible(true);
	}
	
    public WaypointsTable() {
        super(TrackProfilerFrame.getInstance());
        initGUI();
    }

	private void initGUI() {
		try {
            this.getContentPane().setLayout( null );
            this.setTitle(new Message(Messages.WAYPOINTS_LIST).toString());
            this.getContentPane().add( getOkButton() );
            this.getContentPane().add(getJScrollPane1());
            this.getContentPane().add(getShowAllButton());
            this.getContentPane().add(getHideAllButton());
            this.setResizable(false);
            this.setSize(468, 304);
            this.setLocation(new java.awt.Point(300,300));
            this.setModal(true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void okButtonActionPerformed(ActionEvent evt) {
		this.setVisible( false );
	}

    private JButton getOkButton() {
        if( this.okButton == null ) {
            okButton = new JButton();
            okButton.setText(new Message(Messages.CLOSE).toString());
            okButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/delete_edit.gif"))); //$NON-NLS-1$
            okButton.setBounds(340, 240, 110, 20);
            okButton.setFont(GUIConstants.DIALOG_12);
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    okButtonActionPerformed(evt);
                }
            });
        }
        return this.okButton;
    }
    
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setBounds(10, 10, 440, 220);
            jScrollPane1.setViewportView(getWaypointsTable());
        }
        return jScrollPane1;
    }
    
    private JTable getWaypointsTable() {
        if (waypointsTable == null) {
            waypointsTable = new JTable();
            waypointsTable.setModel( new WaypointsTableModel() );
        }
        return waypointsTable;
    }
    
    private JButton getShowAllButton() {
        if (showAllButton == null) {
            showAllButton = new JButton();
            showAllButton.setText(new Message(Messages.SHOW_ALL_WAYPOINTS).toString());
            showAllButton.setBounds(90, 240, 120, 20);
            showAllButton.setFont(GUIConstants.DIALOG_12);
            showAllButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/brkpi_obj.gif"))); //$NON-NLS-1$
            showAllButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    setWaypointsVisibility(true);
                }
            });
        }
        return showAllButton;
    }
    
    private JButton getHideAllButton() {
        if (hideAllButton == null) {
            hideAllButton = new JButton();
            hideAllButton.setText(new Message(Messages.HIDE_ALL_WAYPOINTS).toString());
            hideAllButton.setBounds(220, 240, 110, 20);
            hideAllButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("info/puzz/trackprofiler/icons/brkpd_obj.gif"))); //$NON-NLS-1$
            hideAllButton.setFont(GUIConstants.DIALOG_12);
            hideAllButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    setWaypointsVisibility( false );
                }
            });
        }
        return hideAllButton;
    }
    
    private void setWaypointsVisibility(boolean visibility) {
        Waypoints waypoints = TrackProfilerFrame.getInstance().getTrack().getWaypoints();
        for( int i = 0; i < waypoints.size(); i++ ) {
            Waypoint wpt = (Waypoint) waypoints.get( i );
            wpt.setVisible( visibility );
        }
        this.getWaypointsTable().repaint();
        try {
            TrackProfilerFrame.getInstance().drawChart();
        }
        catch ( TrackProfilerException e ) {
            e.printStackTrace();
        }
    }

}
