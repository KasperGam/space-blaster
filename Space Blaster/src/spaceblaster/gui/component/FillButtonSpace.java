package spaceblaster.gui.component;

import java.awt.Dimension;

import javax.swing.JComponent;

public class FillButtonSpace extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	
	public FillButtonSpace(int w, int h) {
		super();
		width = w;
		height = h;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

}
