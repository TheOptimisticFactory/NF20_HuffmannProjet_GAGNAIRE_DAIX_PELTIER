package main.file.basics;

public class TreeDictionnary {
	int value;
	TreeDictionnary left;
	TreeDictionnary right;
	TreeDictionnary current;

	/* ========================================= CONSTRUCTEUR ========================================= */

	public TreeDictionnary(){
		this.value = -1;
		this.left = null;
		this.right = null;
		this.current = null;
	}

	/* ========================================= DICTIONNAIRES ========================================= */

	public TreeDictionnary getLeft() {
		return left;
	}

	public TreeDictionnary getRight() {
		return right;
	}

	public void setLeft(TreeDictionnary left) {
		this.left = left;
	}

	public void setRight(TreeDictionnary right) {
		this.right = right;
	}



	/* ========================================= VALEUR ========================================= */

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void addValue(int val, boolean tab[], int length) {
		this.current = this;
		for(int i = 0; i < length; i++) {
			if(tab[i]) {
				if(this.current.getLeft() == null) {
					this.current.setLeft(new TreeDictionnary());
				} 
				this.current = this.current.getLeft();
			} else {
				if(this.current.getRight()==null) {
					this.current.setRight(new TreeDictionnary());
				} 
				this.current = this.current.getRight();
			}
		}
		this.current.setValue(val);
		this.current = null;
	}

	public int getValue(boolean leftWanted) {
		if(this.getRight() ==null || this.getLeft() == null) {
			this.current = null;
			return this.value;
		}
		if(this.current == null) {
			if(leftWanted) {
				this.current = this.getLeft();
			} else {
				this.current = this.getRight();
			}
			int val = this.current.getValue();
			if(val!=-1) {
				this.current = null;
			}
			return val;
		} else {
			int val = this.current.getValue(leftWanted);
			if(val!=-1) {
				this.current = null;
			}
			return val;
		}
	}

	/* ========================================= UTILS ========================================= */

	@Override
	public String toString(){
		if(this.value == -1)
			return "["+this.left+":"+this.right+"]";
		else
			return "("+(char)this.value+")";
	}
}
