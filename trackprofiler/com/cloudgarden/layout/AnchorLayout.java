/*
 */
package com.cloudgarden.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 * Lays out components, using a combination of their "bounds" property
 * and their AnchorConstraints layout constraint objects. 
 * <P>
 * Sides of the components can be anchored either absolutely (eg, if the 
 * right side is anchored absolutely then it will always be a fixed number 
 * of pixels from the right side of it's parent container) or relatively (ie,
 * if any side is anchored relatively then it will always occur a fixed
 * fraction of the way along it's parent's side). Or they can be not anchored,
 * at all in which case they will occur at places determined by their 
 * component's "bounds" property and the anchoring of the component's
 * other sides.
 * 
 * This class is NOT open source, but according to <a
 * href="http://www.cloudgarden.com/servlet/cloudgarden.EntryServlet?class=Notice&mode=spitFrame&url=http%3A//www.cloudgarden.com/servlet/cloudgarden.EntryServlet%3Fmode%3Ddraw%26class%3DNotice%26NOTICE_ID%3D2587">this
 * link</a> it can be used in non comercial open source programs.

 */
public class AnchorLayout implements LayoutManager2 {

	int preferredWidth, preferredHeight, minHeight, minWidth;
	int numChildren = -1;
	HashMap constraintMap = new HashMap();
	boolean invalid = true;

	public AnchorLayout() {
		super();
	}

	void initialize(Container parent) {
		Component[] children = parent.getComponents();
		if (!invalid)
			return;
		preferredWidth = 10000;
		preferredHeight = 10000;
		minWidth = 0;
		minHeight = 0;
		numChildren = children.length;
		Rectangle pb = parent.getBounds();
		for (int i = 0; i < children.length; i++) {
			Component cont = children[i];
			if (cont != null) {
				Object ld = constraintMap.get(cont);
				Rectangle b = cont.getBounds();
				Dimension pref = cont.getPreferredSize();
				Dimension min = cont.getMaximumSize();
				if (pref == null)
					pref = cont.getSize();
				if (min == null)
					min = cont.getSize();
				int minX = b.x + b.width;
				int minY = b.y + b.height;
				int maxX = b.x + b.width;
				int maxY = b.y + b.height;
				if (ld instanceof AnchorConstraint) {
					AnchorConstraint ac = (AnchorConstraint) ld;
					int acl = ac.left;
					int acr = ac.right;
					int aclt = ac.leftType;
					int acrt = ac.rightType;

					if (aclt == AnchorConstraint.ANCHOR_REL)
						acl = acl * pb.width / 1000;
					if (acrt == AnchorConstraint.ANCHOR_REL)
						acr = pb.width - acr * pb.width / 1000;

					if (aclt != AnchorConstraint.ANCHOR_NONE
						&& acrt != AnchorConstraint.ANCHOR_NONE)
						maxX = acl + pref.width + acr;
					if (aclt == AnchorConstraint.ANCHOR_NONE)
						acl = 0;
					if (acrt == AnchorConstraint.ANCHOR_NONE)
						acr = 0;
					minX = acl + min.width + acr;

					int act = ac.top;
					int acb = ac.bottom;
					int actt = ac.topType;
					int acbt = ac.bottomType;
					if (actt == AnchorConstraint.ANCHOR_REL)
						act = act * pb.height / 1000;
					if (acbt == AnchorConstraint.ANCHOR_REL)
						acb = pb.height - acb * pb.height / 1000;

					if (actt != AnchorConstraint.ANCHOR_NONE
						&& acbt != AnchorConstraint.ANCHOR_NONE)
						maxY = act + pref.height + acb;
					if (actt == AnchorConstraint.ANCHOR_NONE)
						act = 0;
					if (acbt == AnchorConstraint.ANCHOR_NONE)
						acb = 0;
					minY = act + min.height + acb;
				}
				if (minX > minWidth)
					minWidth = minX;
				if (maxX > minWidth)
					preferredWidth = maxX;
				if (minY > minHeight)
					minHeight = minY;
				if (maxY > preferredHeight)
					preferredHeight = maxY;
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
	 */
	public void layoutContainer(Container composite) {
		Component children[] = composite.getComponents();
		initialize(composite);
		Rectangle rect = composite.getBounds();
		int width = rect.width;
		int height = rect.height;
		for (int i = 0; i < children.length; i++) {
			Component cont = children[i];
			if (cont != null) {
				Object ld = constraintMap.get(cont);
				Rectangle b = cont.getBounds();
				Dimension pref = cont.getPreferredSize();
				if (pref == null)
					pref = cont.getSize();
				if (ld instanceof AnchorConstraint) {
					AnchorConstraint ac = (AnchorConstraint) ld;
					int acl = ac.left;
					int acr = ac.right;
					int aclt = ac.leftType;
					int acrt = ac.rightType;
					if (aclt == AnchorConstraint.ANCHOR_REL)
						acl = acl * width / 1000;
					if (acrt == AnchorConstraint.ANCHOR_REL)
						acr = width - acr * width / 1000;

					if (aclt != AnchorConstraint.ANCHOR_NONE) {
						if (acrt != AnchorConstraint.ANCHOR_NONE) {
							b.width = width - acr - acl;
							b.x = acl;
						} else {
							b.width = pref.width;
							if (b.width + acl > width)
								b.width = width - acl;
							b.x = acl;
						}
					} else {
						if (acrt != AnchorConstraint.ANCHOR_NONE) {
							b.x = width - acr - b.width;
						}
						b.width = pref.width;
						if (b.width + b.x > width)
							b.width = width - b.x;
					}

					int act = ac.top;
					int acb = ac.bottom;
					int actt = ac.topType;
					int acbt = ac.bottomType;
					if (actt == AnchorConstraint.ANCHOR_REL)
						act = act * height / 1000;
					if (acbt == AnchorConstraint.ANCHOR_REL)
						acb = height - acb * height / 1000;

					if (actt != AnchorConstraint.ANCHOR_NONE) {
						if (acbt != AnchorConstraint.ANCHOR_NONE) {
							b.height = height - acb - act;
							b.y = act;
						} else {
							b.height = pref.height;
							if (b.height + act > height)
								b.height = height - act;
							b.y = act;
						}
					} else {
						if (acbt != AnchorConstraint.ANCHOR_NONE) {
							b.y = height - acb - b.height;
						}
						b.height = pref.height;
						if (b.height + b.y > height)
							b.height = height - b.y;
					}
					cont.setBounds(b);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	public void addLayoutComponent(String name, Component comp) {}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	public void removeLayoutComponent(Component comp) {
		constraintMap.remove(comp);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	public Dimension preferredLayoutSize(Container parent) {
		initialize(parent);
		return new Dimension(preferredWidth, preferredHeight);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	public Dimension minimumLayoutSize(Container parent) {
		initialize(parent);
		return new Dimension(minWidth, minHeight);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
	 */
	public void addLayoutComponent(Component comp, Object constraints) {
		constraintMap.put(comp, constraints);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	public Dimension maximumLayoutSize(Container target) {
		return preferredLayoutSize(target);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	public void invalidateLayout(Container target) {
		invalid = true;
	}

}
