import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class PacManMain {

	public static void main(String[] args) {
		JFrame window = new JFrame("PacMan");
	 
		PacManModel model = new PacManModel();
		PacManView view = new PacManView(model);
        
		window.setContentPane(view);
        window.pack();  
        //****this centers the window on the users screen
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation( (screensize.width - window.getWidth())/2,
                (screensize.height - window.getHeight())/2 );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);  
        window.setVisible(true);
	}

}
