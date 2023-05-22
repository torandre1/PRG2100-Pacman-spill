import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

 
/**
 * @author Tor André Myhre
 * Obligatorisk oppgave i OBJ2100 V23: Pac-man! Forsøk Nr 3
 * 
 * Denne gangen er det implementert litt flere funksjoner!
 * Som tidligere blir bane lest av en fil. 0 = vegg, 1 = sti, 2 = start pacman
 * 3 = start ghost, 4 = spiselig dot
 * 
 * Nytt i denne versjonen er spøkelser som jager deg, kommer noen av spøkelsene borti deg 3 ganger 
 * så tapes spillet, samles alle dottene så vinnes spillet. 
 * 
 * Det er lagt inn en score teller.
 * 
 * Når spillet er slutt får man et oppsummering av om man vant eller ei og oppnådd poeng.
 * 
 * 
  */


public class pacmanMain extends Application {
    
    // path må stemme overens med din maskin for å at programmet skal fungere
    String path = "C:\\pacman\\images\\";
    
    private static final int BREDDE = 34;       // Antall fliser i bredden
    private static final int HOYDE = 30;        // Antall fliser i høyde
    private static final int FLIS_STR = 16;     // Størrelse per flis
    private static final int DOTS = 100;         // Antall dots som må spises for å vinne/blir synlig på bane
    private static final int GHOST = 5;         // Antall spøkelser
    private int[][] level;                      // 2 dim tabell for level1.txt
  
    Text txt =  new Text();                     // tekst spill status og poengscore
    Group root = new Group();
    Pacman pacman = new Pacman(8);            // pacman
    ArrayList<Figur> dots = new ArrayList<Figur>();  // dots
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();    // ghosts

    /**
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {

 // canvas, group, scene
        Canvas canvas = new Canvas(BREDDE * FLIS_STR, HOYDE * FLIS_STR);        
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, BREDDE * FLIS_STR, HOYDE * FLIS_STR, Color.BLACK);
        
// pacman
        pacman.NyttBilde(path + "pacman.png", root);
        
        // DOTS
        for(int i = 0; i < DOTS; i++) {
        	Figur e = new Figur(8);
        	dots.add(e);
        	dots.get(i).NyttBilde(path+"smallDot.png", root);
        }
        // GHOST
        for(int i = 0; i < GHOST; i++) {
        	Ghost G = new Ghost(8);
        	ghosts.add(G);
        	ghosts.get(i).NyttBilde(path+"ghost.png", root);
        }
        
        // ghosts flytt
	ghosts.get(0).flytt(pacman, ghosts);
        
     /**
      * Events
      * 
      * Flytter pacmanfiguren ved hjelp av piltastene,
      * innholder logikk for å sjekke om man prøver å gå mot vegg og kollisjon med dots og ghots
      * ved tastetrykk eller kolisjon med dots blir gammelt bilde slettet og nytt genert på ny posisjon
      */
        scene.setOnKeyPressed(event -> {	
        for(int i = 0; i < dots.size(); i++) {
        	if(pacman.kollisjon(dots.get(i))) {
        		dots.get(i).setX(111*111);
        		dots.remove(dots.get(i));
            	pacman.giPoeng();  
        		break;
        	}
        }
        for(Ghost G: ghosts) {
        	if(pacman.kollisjon(G)) {
        		pacman.minusLiv();
        		break;
        	}
        }

        int pacmanX = (int)pacman.getX();
        int pacmanY = (int)pacman.getY();

        if(pacman.getLiv() >= 0 && pacman.getPoeng() < DOTS ) {
	        if (event.getCode() == KeyCode.LEFT) {
	        	pacman.NyttBilde(path+"pacmanLeft.png", root);
                        int nyX = pacmanX - FLIS_STR;
	            if (level[pacmanY/FLIS_STR][nyX/FLIS_STR] != 0) {   
	                pacmanX = nyX;
	                pacman.setX(pacmanX);
	            }
	        } else if (event.getCode() == KeyCode.RIGHT) {  
	        	pacman.NyttBilde(path+"pacmanRight.png", root);
	            int nyX = pacmanX + FLIS_STR;
	            if (level[pacmanY/FLIS_STR][nyX/FLIS_STR] != 0) {
	                pacmanX = nyX;
	                pacman.setX(pacmanX);
	            }
	        } else if (event.getCode() == KeyCode.UP) {
	        	pacman.NyttBilde(path+"pacmanUp.png", root);
	            int nyY = pacmanY - FLIS_STR;
	            if (level[nyY/FLIS_STR][pacmanX/FLIS_STR] != 0) {
	                pacmanY = nyY; // - TILESIZE/2;
	                pacman.setY(pacmanY);
	            }
	        } else if (event.getCode() == KeyCode.DOWN) { 
	        	pacman.NyttBilde(path+"pacmanDown.png", root);
	            int nyY = pacmanY + FLIS_STR;
	            if (level[nyY/FLIS_STR][pacmanX/FLIS_STR] != 0) {
	            pacmanY = nyY; // + TILESIZE/2;
	            pacman.setY(pacmanY);
	            }
	        }else if(event.getCode()== KeyCode.Q) {
	        	System.exit(1);
	        }
	    root.getChildren().remove(txt);	       
	    txt.setText("liv : "+(pacman.getLiv())+ "  Poeng : "+ pacman.getPoeng());
	    txt.setX(10);
            txt.setY((HOYDE*FLIS_STR-10));
            txt.setFill(Color.WHITE);
            root.getChildren().add(txt);
            
        }else {  // når liv blir 0
        	if(event.getCode()== KeyCode.Q) {
	        	System.exit(1);
	        }
        	for(Ghost G : ghosts)  
        		G.setX(111*111);
        	ghosts.clear();
        	for(Figur h: dots)
        		h.setX(111*111);
        	dots.clear();
        	pacman.setY(111*111);
        	root.getChildren().remove(txt);	       
	        txt.setText("Liv : "+(0)+ "  Poeng : "+ pacman.getPoeng());
	        txt.setX(10);
            txt.setY((HOYDE*FLIS_STR-20));
            txt.setFill(Color.WHITE);
            root.getChildren().add(txt);
        	txt = new Text();
        	if(pacman.getLiv() <= 0 && pacman.getPoeng() < DOTS)
        		txt.setText("Spillet er over");         // taper tekst
        	if(pacman.getPoeng() >= DOTS && pacman.getLiv() > 0)
        		txt.setText("Du vant!");                // vinner teskt
            txt.setX(((BREDDE/2)*FLIS_STR )/2);
            txt.setY((HOYDE*FLIS_STR)/2);
            
            txt.setFont(Font.font("Arial", 44));
            txt.setFill(Color.RED);
            root.getChildren().add(txt);
            
        }
	    }); 
        
        lesBane("level1.txt");   
        tegnBane(canvas.getGraphicsContext2D());
        stage.setTitle("Pacman");
        stage.setScene(scene);
        stage.show();
    } 
     
    private void lesBane(String filnavn) {
        level = new int[HOYDE][BREDDE];
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(filnavn));
            String linje;
            int rad = 0;
            int dotSum = 0;
            int ghostSum = 0;
            while ((linje = buffReader.readLine()) != null) {
                for (int koll = 0; koll < linje.length(); koll++) {
                    char c = linje.charAt(koll);
                    if (c == '0') {
                        level[rad][koll] = 0; // Vegg
                    } else if (c == '1') {
                        level[rad][koll] = 1; // Tom sti                       
                    }else if (c == '2') {
                        level[rad][koll] = 2; // Pacman start posisjon
                        pacman.setX(koll*FLIS_STR + FLIS_STR/2);
                        pacman.setY(rad*FLIS_STR + FLIS_STR/2);
                    } else if (c == '3') {
                        level[rad][koll] = 1; // ghost start posisjon
                        if(ghostSum < ghosts.size()) {
                        	ghosts.get(ghostSum).setX(koll*FLIS_STR + FLIS_STR/2);
 	                        ghosts.get(ghostSum).setY(rad*FLIS_STR + FLIS_STR/2);
 	                        ghostSum++;
                        }
                    } else if(c == '4') { // Dotter
                    	level[rad][koll] = 1;
                    	if(dotSum < dots.size()) {
	                        dots.get(dotSum).setX(koll*FLIS_STR + FLIS_STR/2);
	                        dots.get(dotSum).setY(rad*FLIS_STR + FLIS_STR/2);
	                        dotSum++;
                        }
                    } 
                }
                rad++;
            }
            buffReader.close();
        } catch (IOException e) {
            System.err.println("Error: leserfeil: " + e.getMessage());
        }
    }
/**
 * tegnBane: lager grafikk
 * @param grafikk 
 */
    private void tegnBane(GraphicsContext grafikk) {
        for (int row = 0; row < HOYDE; row++) {
            for (int col = 0; col < BREDDE; col++) {
                int tile = level[row][col];
                if (tile == 0) {
                    grafikk.setFill(Color.BLUE); // wall
                } else if (tile == 1) {
                    grafikk.setFill(Color.BLACK); // path
                } else if (tile == 2) {
                    grafikk.setFill(Color.BLACK); // Pacman start posisjon (ikke i bruk enda)
                } 
                grafikk.fillRect(col * FLIS_STR, row * FLIS_STR, FLIS_STR, FLIS_STR);
            }
        }  
    }
    /**
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
        
    }
}