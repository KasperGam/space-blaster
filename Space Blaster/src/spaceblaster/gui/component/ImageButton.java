package spaceblaster.gui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class ImageButton extends JComponent implements MouseListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Image highlighted;
	
	private boolean isHighlighted = false;
	private ActionListener listener;
	
	
	public ImageButton(Image img, Image himg, String name, ActionListener listener) {
		super();
		image = img;
		highlighted = himg;
		this.listener = listener;
		this.setName(name);
		addMouseListener(this);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	public Image getImage(){
		if(isHighlighted){
			return highlighted;
		} else {
			return image;
		}
	}
	
	public void setHighlighed(boolean isHighlight){
		isHighlighted = isHighlight;
	}
	

	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(getImage(), 0, 0, null);

	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		isHighlighted = true;
		this.repaint();
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		isHighlighted = false;
		this.repaint();
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(isHighlighted){
			listener.actionPerformed(new ActionEvent(this, 0, ""));
			this.repaint();
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(null), image.getHeight(null));
    }

}
