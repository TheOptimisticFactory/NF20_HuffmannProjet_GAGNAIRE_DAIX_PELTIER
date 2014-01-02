package fileCompressedManager;

public class TreeDictionnary {
	/**
	 * Classe représentant un dictionnaire sous forme d'arbre
	 * Correspond à un arbre de huffman mais en "lecture" est déjà
	 * ecrit dans le fichier.
	 */
	//octet correspondant au noeud :
	int value;
	
	//Noeud gauche (valeur 0) :
	TreeDictionnary left;
	
	//Noeud droite (valeur 1) :
	TreeDictionnary right;
	
	//Noeud actuel (pendant une opération de lecture) :
	TreeDictionnary current;
	
	/**
	 * Création d'un arbre, aucun octet stocké dedans, pas de fils.
	 */
	public TreeDictionnary(){
		this.value = -1;
		this.left = null;
		this.right = null;
		this.current = null;
	}
	
	/**
	 * Renvoie sous arbre gauche.
	 * @return
	 */
	public TreeDictionnary getLeft() {
		return left;
	}

	/**
	 * Renvoie sous arbre droit :
	 * @return
	 */
	public TreeDictionnary getRight() {
		return right;
	}

	/**
	 * Fixe le sous arbre gauche
	 * @param left
	 */
	public void setLeft(TreeDictionnary left) {
		this.left = left;
	}

	/**
	 * Fixe le sous arbre droit
	 * @param right
	 */
	public void setRight(TreeDictionnary right) {
		this.right = right;
	}

	/**
	 * Renvoie la valeur contenus dans le noeud
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Fixe la valeur contenus dans le noeud
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Methode utilisé pour créer l'arbre.
	 * On ajoute une valeur dans l'arbre
	 * @param val : octet à stocké
	 * @param tab : tableau de bit correspondant à l'octet (en compression)
	 * @param length : nombre de bit pour écrire l'octet (en compression)
	 */
	public void addValue(int val, boolean tab[], int length)
	{
		this.current = this;
		//On navigue dans l'arbre en fonction des bits.
		for(int i = 0; i < length; i++)
		{
			//Si le bit actuel est "vrai" (1) on l'ajoute à gauche
			if(tab[i])
			{
				if(this.current.getLeft() == null)
					this.current.setLeft(new TreeDictionnary());
				this.current = this.current.getLeft();
			}else{//Sinon on l'ajoute à droite :
				if(this.current.getRight()==null)
					this.current.setRight(new TreeDictionnary());
				this.current = this.current.getRight();
			}
		}
		this.current.setValue(val);
		this.current = null;
	}
	
	/**
	 * Methode utilisé pour lire le fichier compressé
	 * On lui passe les bits qu'on lit un à un
	 * La méthode va naviguer dans l'arbre au grès des bits
	 * si elle tombe sur une "serie" elle renvoie l'octet qui correspond
	 * sinon elle renvoie -1
	 * @param bit : bit lus.
	 * @return
	 */
	public int getValue(boolean bit){
		if(this.getRight() ==null || this.getLeft() == null){
			this.current = null;
			return this.value;
		}
		if(this.current == null){
		if(bit)
			this.current = this.getLeft();
		else
			this.current = this.getRight();
			int val = this.current.getValue();
			if(val!=-1)
				this.current = null;
			return val;
		}else{
			int val = this.current.getValue(bit);
			if(val!=-1)
				this.current = null;
			return val;
		}
	}
	
	/**
	 * Methode pour afficher l'arbre.
	 */
	public String toString(){
		if(this.value == -1)
			return "["+this.left+":"+this.right+"]";
		else
			return "("+(char)this.value+")";
	}
}
