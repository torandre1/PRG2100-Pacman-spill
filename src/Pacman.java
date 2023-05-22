
public class Pacman extends Figur {
     
	private int antallLiv = 3;
	private int poeng = 0;
        
	public Pacman(int radius) {
		super(radius);
	}
        
        public void setLiv(int i) {
		antallLiv = i;	
	}
        
        public int getLiv() {
		return this.antallLiv;
	}

        public void setPoeng(int i) {
		this.poeng = i;	
	}
        
	public int getPoeng() {
		return this.poeng;
	}
               
        public void minusLiv() {
		this.antallLiv--;
	}
        
	public void giPoeng() {
		this.poeng++;
	}
        
}
