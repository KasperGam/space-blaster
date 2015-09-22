import spaceblaster.gui.GameFrame;
import spaceblaster.model.Game;


public class SpaceBlaster {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	GameFrame frame = new GameFrame(1000, 700);
        		@SuppressWarnings("unused")
				Game g = new Game(frame);
            }
        });

		

	}

}
