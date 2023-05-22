import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
 
public class Figur { 
	
	public Circle r;  // r for radius
	public Image image;
	public ImageView imageView;
	
        /**
         * 
         * @param radius 
         */
        public Figur(int radius) { 
		r = new Circle(radius); 
	}
        /**
         * 
         * @param d 
         */
	public void setX(int d) {
		this.r.setCenterX(d);
	}
        /**
         * 
         * @param y 
         */
	public void setY(int y) {
		this.r.setCenterY(y);
	}
	/**
         * 
         * @return 
         */
	public double getX() {
		return this.r.getCenterX();
	}
        /**
         * 
         * @return 
         */
	public double getY() {
		return this.r.getCenterY();
	} 
        /**
         * 
         * @param imagePath
         * @param root 
         */
	public void NyttBilde(String imagePath, Group root) {
	    root.getChildren().remove(this.r);  // bilde slettes
	    image = new Image(imagePath);   
	    imageView = new ImageView(image);
	    imageView.setFitWidth(r.getRadius() * 2);
	    imageView.setFitHeight(r.getRadius() * 2);
	    this.r.setFill(new ImagePattern(image));
	    root.getChildren().add(this.r);     // nytt opprettes på ny posisjon
	}
        /**
         * 
         * @param E
         * @return 
         */
	public boolean kollisjon(Figur E) {
		if(this.getX() == E.getX() && this.getY() == E.getY()) {		
			return true;
		}
		return false;
	}
        
   /*     
	protected void test() {
            System.out.println("test test");
	} */ 
}
