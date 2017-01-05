package Projet.Takuzu;

import java.util.Arrays;

/**
 * @author Ballot Corentin
 * 4 janv. 2017
 */
public class Takuzu {
	//= = = = = = = = = = = = = = = = = = = =
	// Fields & Constants
	//= = = = = = = = = = = = = = = = = = = =
	
	int[][] grille;

	//= = = = = = = = = = = = = = = = = = = =
	// Constructors
	//= = = = = = = = = = = = = = = = = = = =
	
	/**
	 * Constructeur par défault
	 * Génère le Takuzu donné dans le sujet du projet.
	 */
	public Takuzu() {
		int[][] grille = {{-1, 1, -1, 0}, {-1, -1, 0, -1}, {-1, 0, -1, -1}, {1, 1, -1, 0}};
		this.grille = grille;
	}

	/**
	 * Constructeur
	 * Génère le Takuzu passé en paramètre. 
	 * 
	 * @param grille La grille du Takuzu
	 */
	public Takuzu(int[][] grille) {
		this.grille = grille;
	}
	
	//= = = = = = = = = = = = = = = = = = = =
	// Getters & setters & override methods
	//= = = = = = = = = = = = = = = = = = = =

	/**
	 * Getter de la grille
	 * @return La grille du Takuzu
	 */
	public int[][] getGrille() {
		return grille;
	}

	/**
	 * Setter de la grille
	 * @param grille La nouvelle grille du Takuzu
	 */
	/**
	 * @param grille
	 */
	public void setGrille(int[][] grille) {
		this.grille = grille;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String s = "{";
		for (int i = 0; i < grille.length; i++) {
			s += Arrays.toString(grille[i]);
			if(i != grille.length -1 ) s += ", ";
		}
		return s + "}";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Takuzu clone() {
		int[][] clone = new int[this.grille.length][];
		for (int i = 0; i < clone.length; i++) {
			clone[i] = this.grille[i].clone();
		}
		
		return new Takuzu(clone);
	};
	
	//= = = = = = = = = = = = = = = = = = = =
	// Methods
	//= = = = = = = = = = = = = = = = = = = =
	
	/**
	 * Méthode d'initialisation de la résolution récursive du Takuzu utilisant le principe de backtracking
	 * 
	 * @return Le Takuzu résolu en cas de succès, null sinon
	 */
	public Takuzu resolution(){
		return resolution(this.clone(), 0, 0);
	}
	
	/**
	 * Méthode de résolution récursive du Takuzu utilisant le backtracking
	 * 
	 * @param tak Takuzu devant être résolu
	 * @param ligne Ligne à laquelle en est la résulution
	 * @param colone Colone à laquelle en est la résulution
	 * @return Le Takuzu résolu en cas de succès, null sinon
	 */
	private Takuzu resolution(Takuzu tak, int ligne, int colone){	
		
		for (int i = 0; i < 2; i++) {
			if(this.grille[ligne][colone] == -1){ 											// Si la case original contient -1 (case vide)
				tak.grille[ligne][colone] ++;												// On l'incrémente dans notre copie local	
			}
			
/*			System.out.println(tak 
					+ "\t Travail sur L"+ ligne + " valide? " + tak.ligneValide(ligne) 
					+ "\t C"+ colone + " valide? " + tak.coloneValide(colone));*/
			
			if(colone < this.grille[ligne].length -1){										// Si l'on ne se trouve pas en fin de ligne, on passe à la colone suivante
				if(tak.ligneValide(ligne) && tak.coloneValide(colone)){
					Takuzu tmp = resolution(tak.clone(), ligne, colone +1);					// On passe à la résolution de la case suivante
					if(tmp != null && tmp.estValide()) return tmp;
				}
			} else if(ligne < this.grille.length -1){										// Sinon, si l'on se trouve en fin de ligne
				if(tak.ligneValide(ligne) && tak.coloneValide(colone) && tak.ligneEstUnique(ligne)){
					Takuzu tmp = resolution(tak.clone(), ligne + 1, 0);						// On passe à la résolution de la ligne suivante
					if(tmp != null && tmp.estValide()) return tmp;
				}
			}else {
				if(tak.estValide()) return tak;
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Verifie la validité du Takuzu
	 * 
	 * @return <b>true</b> si le Takuzu est valide, <b>false</b> sinon
	 */
	public boolean estValide(){
		// Pour chaque lignes
		for (int i = 0; i < grille.length; i++) {
			if(!ligneValide(i) || !ligneEstUnique(i))
				return false;
			// Pour chaque colone dans la ligne i
			for (int j = 0; j < grille[i].length; j++) {
				 if(!coloneValide(j) || !coloneEstUnique(j))
					 return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Verifie la validité de la ligne <b>i</b> du Takuzu<br/>
	 * La validité de la ligne est verifié si elle ne présente pas trois cases de suite non vide contenant le même chiffre, 
	 * puis si elle possède autant de 0 que de 1 dans le cas ou la ligne est completement remplis (ne contient pas de cases vides).
	 * 
	 * @param i Numéro de la ligne à évaluer
	 * @return <b>true</b> si la ligne est valide, <b>false</b> sinon 
	 */
	private boolean ligneValide(int i){
		boolean coloneIncomplete = false;
		int somme = 0;
		if(grille[i][0] >= 0) somme+= grille[i][0];
		else coloneIncomplete = true;
		if(grille[i][1] >= 0) somme+= grille[i][1];
		else coloneIncomplete = true;
		
		// Pour chaque colone
		for (int j = 2; j < grille[i].length; j++) {
			// Si trois cases consécutives sont identique
 			if(grille[i][j-2] == grille[i][j-1] && grille[i][j-1] == grille[i][j] && grille[i][j] != -1)
				return false; // Le Takuzu est invalide
 			
 			if(grille[i][j] >= 0) somme+= grille[i][j];
 			else coloneIncomplete = true;
		}
		
		return somme == (grille.length / 2) || coloneIncomplete;
	}
	
	/**
	 * Verifie la validité de la colone <b>i</b> du Takuzu<br/>
	 * La validité de la colone est verifié si elle ne présente pas trois cases de suite non vide contenant le même chiffre, 
	 * puis si elle possède autant de 0 que de 1 dans le cas ou la colone est completement remplis (ne contient pas de cases vides).
	 * 
	 * @param i Numéro de la colone à évaluer
	 * @return <b>true</b> si la colone est valide, <b>false</b> sinon 
	 */
	private boolean coloneValide(int i){
		boolean coloneIncomplete = false;
		int somme = 0;
		if(grille[0][i] >= 0) somme+= grille[0][i];
		else coloneIncomplete = true;
		if(grille[1][i] >= 0) somme+= grille[1][i];
		else coloneIncomplete = true;
		
		// Pour chaque lignes
		for (int j = 2; j < grille.length; j++) {
			// Si trois cases consécutives sont identique et remplis
 			if(grille[j-2][i] == grille[j-1][i] && grille[j-1][i] == grille[j][i] && grille[j][i] != -1)
				return false; // Le Takuzu est invalide
 			
 			if(grille[j][i] >= 0) somme+= grille[j][i];
 			else coloneIncomplete = true;
		}
				
		return somme == (grille.length / 2) || coloneIncomplete;
	}
	
	/**
	 * Vérifie qu'une ligne n'a pas de double dans le Takuzu
	 * 
	 * @param j Numero de la ligne à verifier
	 * @return <b>true</b> si la ligne est unique, <b>false</b> sinon
	 */
	private boolean ligneEstUnique(int j){
		// Pour chaque ligne
		for (int i = 0; i < j; i++) {
			// Si deux lignes sont identique
			if(grille[i].equals(grille[j]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Vérifie qu'une colone n'a pas de double dans le Takuzu
	 * 
	 * @param j Numero de la colone à verifier
	 * @return <b>true</b> si la ligne est unique, <b>false</b> sinon
	 */
	private boolean coloneEstUnique(int j){
		// Pour chaque ligne
		for (int i = 0; i < j; i++) {
			// Si deux lignes sont identique
			if(grille[i].equals(grille[j]))
				return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		//System.setOut(new PrintStream(new File("afile.txt")));
		
		//int[][] grille = {{-1, 1, -1, 0}, {-1, -1, 0, -1}, {-1, 0, -1, -1}, {1, 1, -1, 0}};
		// Solution : {[0, 1, 1, 0], [1, 0, 0, 1], [0, 0, 1, 1], [1, 1, 0, 0]}
		
		int[][] grille = {{-1, 0, -1, 0, -1, -1, -1, 0, 0, -1},{-1, -1, -1, 0, -1, -1, -1, 0, -1, 0},{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},{-1, 0, -1, -1, -1, -1, -1, -1, -1, 0},{-1, -1, -1, -1, 0, -1, -1, -1, -1, -1},{0, -1, -1, -1, -1, -1, -1, -1, 0, 0},{-1, -1, -1, -1, 0, -1, -1, -1, -1, -1},{-1, -1, 0, -1, 1, -1, -1, -1, 0, 0},{-1, -1, -1, -1, 1, 0, -1, -1, -1, -1},	{-1, 0, -1, -1, -1, 0, -1, -1, 0, 0}};
		// Solution : {[1, 0, 1, 0, 0, 1, 1, 0, 0, 1], [0, 1, 1, 0, 1, 1, 0, 0, 1, 0], [0, 1, 0, 1, 0, 0, 1, 1, 0, 1], [1, 0, 0, 1, 1, 0, 0, 1, 1, 0], [1, 0, 1, 0, 0, 1, 0, 0, 1, 1], [0, 1, 0, 1, 1, 0, 1, 1, 0, 0], [0, 0, 1, 1, 0, 1, 0, 0, 1, 1], [1, 1, 0, 0, 1, 1, 0, 1, 0, 0], [0, 1, 0, 0, 1, 0, 1, 0, 1, 1], [1, 0, 1, 1, 0, 0, 1, 1, 0, 0]}
		
		Takuzu tak = new Takuzu(grille);
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		System.out.println(tak.resolution());
		endTime = System.currentTimeMillis();
		System.out.println("Execution : " + (endTime - startTime) + " (millis) ");
	}
}

