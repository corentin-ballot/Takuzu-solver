import java.util.Arrays;
import java.util.Random;

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
	 * Constructeur par default
	 * Genere le Takuzu donne dans le sujet du projet.
	 */
	public Takuzu() {
		int[][] grille = {{-1, 1, -1, 0}, {-1, -1, 0, -1}, {-1, 0, -1, -1}, {1, 1, -1, 0}};
		this.grille = grille;
	}

	/**
	 * Constructeur
	 * Genere le Takuzu passe en parametre. 
	 * 
	 * @param grille La grille du Takuzu
	 */
	public Takuzu(int[][] grille) {
		this.grille = grille;
	}
	
	/**
	 * Constructeur
	 * Genere le Takuzu vide. 
	 * 
	 * @param taileGrille La taille de la grille du Takuzu
	 */
	public Takuzu(int taileGrille) {
		int[][] grille = new int[taileGrille][];
		
		for (int i = 0; i < taileGrille; i++) {
			int[] ligne = new int[taileGrille];
			for (int j = 0; j < ligne.length; j++) {
				ligne[j] = -1;				
			}
			grille[i] = ligne;
		}
		
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
		String s = "";
		for (int i = 0; i < grille.length; i++) {
			s += Arrays.toString(grille[i]);
			if(i != grille.length -1 ) s += "\n";
		}
		return s.replace(",", "").replace("-1", "_");
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
	 * Methode d'initialisation de la resolution recursive du Takuzu utilisant le principe de backtracking
	 * 
	 * @return Le Takuzu resolu en cas de succes, null sinon
	 */
	public Takuzu resolution(){
		return resolution(this.clone(), 0, 0);
	}
	
	/**
	 * Methode de resolution recursive du Takuzu utilisant le backtracking
	 * 
	 * @param tak Takuzu devant etre resolu
	 * @param ligne Ligne a laquelle en est la resulution
	 * @param colone Colone a laquelle en est la resulution
	 * @return Le Takuzu resolu en cas de succes, null sinon
	 */
	private Takuzu resolution(Takuzu tak, int ligne, int colone){	
		
		for (int i = 0; i < 2; i++) {
			if(this.grille[ligne][colone] == -1){ 											// Si la case original contient -1 (case vide)
				tak.grille[ligne][colone] ++;												// On l'incremente dans notre copie local	
			}
			
/*			System.out.println(tak 
					+ "\t Travail sur L"+ ligne + " valide? " + tak.ligneValide(ligne) 
					+ "\t C"+ colone + " valide? " + tak.coloneValide(colone));*/
			
			if(colone < this.grille[ligne].length -1){										// Si l'on ne se trouve pas en fin de ligne, on passe a la colone suivante
				if(tak.ligneValide(ligne) && tak.coloneValide(colone)){
					Takuzu tmp = resolution(tak.clone(), ligne, colone +1);					// On passe a la resolution de la case suivante
					if(tmp != null && tmp.estValide()) return tmp;
				}
			} else if(ligne < this.grille.length -1){										// Sinon, si l'on se trouve en fin de ligne
				if(tak.ligneValide(ligne) && tak.coloneValide(colone) && tak.ligneEstUnique(ligne)){
					Takuzu tmp = resolution(tak.clone(), ligne + 1, 0);						// On passe a la resolution de la ligne suivante
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
	 * Verifie la validite du Takuzu
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
	 * Verifie la validite de la ligne <b>i</b> du Takuzu<br/>
	 * La validite de la ligne est verifie si elle ne presente pas trois cases de suite non vide contenant le meme chiffre, 
	 * puis si elle possede autant de 0 que de 1 dans le cas ou la ligne est completement remplis (ne contient pas de cases vides).
	 * 
	 * @param i Numero de la ligne a evaluer
	 * @return <b>true</b> si la ligne est valide, <b>false</b> sinon 
	 */
	private boolean ligneValide(int i){
		boolean coloneIncomplete = false; 	// Variable indiquant si la ligne est completement remplis, ou non
		int nb_de_1 = 0;					// Nombre de 1 de la ligne
		int nb_de_0 = 0;					// Nombre de 0 de la ligne
		
		if(grille[i][0] == 0) 
			nb_de_0 ++;
		else if(grille[i][0] == 1) 
			nb_de_1 ++;
		else 
			coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		
		if(grille[i][1] == 0) 
			nb_de_0 ++;
		else if(grille[i][1] == 1) 
			nb_de_1 ++;
		else 
			coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		
		// Pour chaque colone
		for (int j = 2; j < grille[i].length; j++) {
			// Si trois cases consecutives sont identique (non vide)
 			if(grille[i][j-2] == grille[i][j-1] && grille[i][j-1] == grille[i][j] && grille[i][j] != -1)
				return false; // Le Takuzu est invalide
 			
 			if(grille[i][j] == 0) 
 				nb_de_0 ++;
 			else if(grille[i][j] == 1) 
 				nb_de_1 ++;
 			else 
 				coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		}
		
		return (nb_de_1 == (grille.length / 2) && nb_de_0 == (grille.length / 2)) || (coloneIncomplete && nb_de_0 <= (grille.length / 2) && nb_de_1 <= (grille.length / 2));
	}
	
	/**
	 * Verifie la validite de la colone <b>i</b> du Takuzu<br/>
	 * La validite de la colone est verifie si elle ne presente pas trois cases de suite non vide contenant le meme chiffre, 
	 * puis si elle possede autant de 0 que de 1 dans le cas ou la colone est completement remplis (ne contient pas de cases vides).
	 * 
	 * @param i Numero de la colone a evaluer
	 * @return <b>true</b> si la colone est valide, <b>false</b> sinon 
	 */
	private boolean coloneValide(int i){
		boolean coloneIncomplete = false; 	// Variable indiquant si la ligne est completement remplis, ou non
		int nb_de_1 = 0;					// Nombre de 1 de la ligne
		int nb_de_0 = 0;					// Nombre de 0 de la ligne
		
		if(grille[0][i] == 0) 
			nb_de_0 ++;
		else if(grille[0][i] == 1) 
			nb_de_1 ++;
		else 
			coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		
		if(grille[1][i] == 0) 
			nb_de_0 ++;
		else if(grille[1][i] == 1) 
			nb_de_1 ++;
		else 
			coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		
		// Pour chaque lignes
		for (int j = 2; j < grille.length; j++) {
			// Si trois cases consecutives sont identique et remplis
 			if(grille[j-2][i] == grille[j-1][i] && grille[j-1][i] == grille[j][i] && grille[j][i] != -1)
				return false; // Le Takuzu est invalide
 			
 			if(grille[j][i] == 0) 
 				nb_de_0 ++;
 			else if(grille[j][i] == 1) 
 				nb_de_1 ++;
 			else 
 				coloneIncomplete = true; 	// On definit coloneIncomplete a vrai
		}
				
		return nb_de_1 == (grille.length / 2) || (coloneIncomplete && nb_de_1 <= (grille.length / 2));
	}
	
	/**
	 * Verifie qu'une ligne n'a pas de double dans le Takuzu
	 * 
	 * @param j Numero de la ligne a verifier
	 * @return <b>true</b> si la ligne est unique, <b>false</b> sinon
	 */
	private boolean ligneEstUnique(int j){
		// Pour chaque ligne
		for (int i = 0; i < j; i++) {
			// Si deux lignes sont identique
			if(Arrays.equals(this.grille[i], this.grille[j]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Verifie qu'une colone n'a pas de double dans le Takuzu
	 * 
	 * @param j Numero de la colone a verifier
	 * @return <b>true</b> si la ligne est unique, <b>false</b> sinon
	 */
	private boolean coloneEstUnique(int j){
		// Pour chaque ligne
		for (int i = 0; i < j; i++) {
			// Si deux lignes sont identique
			if(Arrays.equals(this.grille[i], this.grille[j]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Genere un Takuzu partiellement pre-remplis
	 * 
	 * @param proportionPreRemplissage Proportion de case pre-remplis que le Takuzu doit avoir
	 * @return Un Takuzu partiellement pre-remplis avec un taux de remplissage de <b>proportionPreRemplissage</b>
	 */
	private Takuzu generate(float proportionPreRemplissage) {
		Random rand = new Random();
		int nb = 0;
		
		// Melange les lignes du Takuzu
		do{
			int line_1 = rand.nextInt(this.grille.length);
			int line_2 = rand.nextInt(this.grille.length);
			
			int[] tmp = grille[line_1];
			grille[line_1] = grille[line_2];
			grille[line_2] = tmp;
			
		}while(++nb < this.grille.length && !this.estValide());
		
		// Parcours des lignes de la grille 
		for (int i = 0; i < grille.length; i++) {
			// Parcours des colones de la grille 
			for (int j = 0; j < grille[i].length; j++) {
				// Si le nombre tiré aléatoirement est plus petit de proportionPreRemplissage
				if(rand.nextFloat() > proportionPreRemplissage)
					this.grille[i][j] = -1; // La case est "vidé"
			}
		}
		
		return this;
	}
	
	/**
	 * Verifie qu'une chaine de caractere est un entier positif
	 * 
	 * @param str chaine de caractere a verifier
	 * @return <b>true</b> si la chaine est un entier positif, <b>false</b> sinon
	 */
	public static boolean isPositiveInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static void main(String[] args) {		
		if(args.length == 0){
			System.out.println("java Takuzu: parametres manquant.");
			System.out.println("Saisissez \" java Takuzu --help \" pour plus d'informations.");
			System.exit(0);
		} else if(args[0].equals("--help")){
			System.out.println("RESOLUTION");
			System.out.println("==========");
			System.out.println("Utilisation : java Takuzu --solve L1 L2 L3 L4 [...]");
			System.out.println("Lancer la resolution de la grille de Takuzu passe en parametre.");
			System.out.println("");
			System.out.println("L1 correspond a la premiere ligne du Takuzu, L2 a la seconde, etc...");
			System.out.println("Les grilles doivent etre carre, et les lignes avoir un nombre pair de cases.");
			System.out.println("Les lignes doivent suivre la syntaxe suivante : \" ?1?0 \". Le caractere representant une case vide est ici \" ? \", mais peut etre tremplace par n'importe quel caractere excepte \" 0 \", et \" 1 \".");
			System.out.println("");
			System.out.println("Exemple d'utilisation avec le Takuzu donne en exemple sur Wikipedia avec le caractere \" 2 \" comme separateur : \" java Takuzu --solve 2120 2202 2022 1120 \" ");
			System.out.println("");
			System.out.println("GENERATION");
			System.out.println("==========");
			System.out.println("Utilisation : java Takuzu --generate TAILLE [OPTION]");
			System.out.println("Lancer la generation d'une grille de Takuzu de TAILLExTAILLE.");
			System.out.println("");
			System.out.println("La proportion de cases pre-remplis est par defaut de 0.25, mais peut-etre passe en argument pour modifier les parametres de genreration de grille.");
			System.out.println("Exemple d'utilisation pour generer un Takuzu de taille 8x8 avec une proposrtion de remplissage de 20% : \" java Takuzu --generate 8 0.2 \"");
			
			System.exit(0);
		} else if(args[0].equals("--solve")){
				if((args.length - 1) % 2 != 0 || args[1].length() != args.length - 1){
					System.out.println("java Takuzu: parametres de grille invalide.");
					System.out.println("Saisissez \" java Takuzu --help \" pour plus d'informations.");
					System.exit(0);
				} else {
					for (int i = 2; i < args.length; i++) {
						if(args[1].length() != args[i].length()){
							System.out.println("java Takuzu: parametres de grille invalide.");
							System.out.println("Saisissez \" java Takuzu --help \" pour plus d'informations.");
							System.exit(0);
						}
					}
				}
			
				int[][] grille = new int[args.length -1][];
				
				for (int i = 1; i < args.length; i++) {
					int[] ligne = new int[args[i].length()];
					for (int j = 0; j < ligne.length; j++) {
						switch (args[i].charAt(j)) {
							case '0': ligne[j] = 0; break;
							case '1': ligne[j] = 1; break;
							default: ligne[j] = -1; break;
						}
						grille[i-1] = ligne;
					}
				}
				
				Takuzu tak = new Takuzu(grille);
				long startTime, endTime;
				
				startTime = System.currentTimeMillis();
				Takuzu sol = tak.resolution();
				endTime = System.currentTimeMillis();
				
				if(sol == null) System.out.println("Pas de solution.");
				else System.out.println("Solution : \n" + sol);
				
				System.out.println("Temps d'execution : " + (endTime - startTime) + " (millis) ");
		} else if(args[0].equals("--generate")){
			if(args.length == 1 || !isPositiveInteger(args[1])){
				System.out.println("java Takuzu: parametres de generation invalide.");
				System.out.println("Saisissez \" java Takuzu --help \" pour plus d'informations.");
				System.exit(0);
			} else {
				Takuzu tak = new Takuzu(Integer.parseInt(args[1])).resolution();
				long startTime, endTime;
				
				float proportionPreRemplissage;
				if(args.length < 3){
					proportionPreRemplissage = 0.25f;
				}else {
					try{
						proportionPreRemplissage = Float.parseFloat(args[2]);
					}catch(NumberFormatException e){
						proportionPreRemplissage = 0.25f;
					}
				}
				
				startTime = System.currentTimeMillis();
				tak.generate(proportionPreRemplissage);
				endTime = System.currentTimeMillis();
				
				System.out.println("Grille : " + tak.toString().replace(" ", "").replace("]", "").replace("[", " ").replace("\n", " "));
				System.out.println(tak);
				System.out.println("Temps d'execution : " + (endTime - startTime) + " (millis) ");
			}
		} else {
			System.out.println("java Takuzu: parametres invalide.");
			System.out.println("Saisissez \" java Takuzu --help \" pour plus d'informations.");
			System.exit(0);
		}
	}
}
