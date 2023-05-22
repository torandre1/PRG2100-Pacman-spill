import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
 
public class Ghost extends Figur{
        static public int ms = 250, avrunder = 4;
        
	public Ghost(int radius) {
		super(radius);
	}
         // gir hvert spøkelse en tilfeldig retning basert på math random 0-3
	public void flytt(Pacman P, ArrayList<Ghost> Ghosts) {
            Timer timer = new Timer();
	    TimerTask timerTask = new TimerTask() {
	    
            public void run() {
                int random = (int) (Math.random() * avrunder);   
	        if(random == 1 || random == 3)
		for(Ghost g: Ghosts) {
                    if (g.getX() < P.getX()) {
                            g.setX((int) (g.getX()+16));
		    } else
                            g.setX((int) (g.getX()-16));
		    if (g.getY() < P.getY()) {
                            g.setY((int) (g.getY()+16));
		    } else
                            g.setY((int) (g.getY()-16));
		    if(g.kollisjon(P)) {
		        P.minusLiv();
		            	}
		            } 
	        	else
		            for(Ghost g : Ghosts) {
		                int retning = (int) (Math.random() * 4); 
		                switch(retning) {
		                    case 0: // flyttes opp
		                        g.setY((int) (g.getY() - 16));
		                        break;
		                    case 1: // flyttes ned
		                        g.setY((int) (g.getY() + 16));
		                        break;
		                    case 2: // flyttes ventre
		                        g.setX((int) (g.getX() - 16));
		                        break;
		                    case 3: // flyttes høyre
		                        g.setX((int) (g.getX() + 16));
		                        break;
		                }
		            }
                        }
                    };
                timer.schedule(timerTask, 5, ms); // oppdateringshastighet på ghosts
	    
	}
}
