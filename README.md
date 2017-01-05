## Introduction

Le jeu japonais de Takuzu (parfois aussi appelé Binero) est un jeu de réflexion aux règles simples, se jouant sur un plateau carré usuellement de 10x10 cases partiellement rempli. Chaque case ne peut contenir que les valeurs 0 ou 1. Les règles sont les suivantes :

1. Il n'est pas possible d'aligner plus de 2 chiffres identiques cote à cote, en ligne ou en colonne.
2. Il doit y avoir autant de 0 et de 1 sur chaque ligne et colonne.
3. Les lignes et colonnes doivent être toutes différentes.

Voici un exemple, passant d'une grille partiellement pré-remplie à une grille remplie (source Wikipedia) :

![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Takuzu_unsolved_grid.svg/120px-Takuzu_unsolved_grid.svg.png "grille partiellement pré-remplie")
![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Takuzu_solved_grid.svg/120px-Takuzu_solved_grid.svg.png "une grille remplie")

## Technique utilisée par le solver

Pour réaliser ce solver, j'ai choisi d'utiliser le backtracking, qui consiste à parcourir les différentes possibilités, et de revenir en arrière sur des décisions prises afin de sortir d'un blocage.
Exemple avec la résolution de la première ligne du l'exemple donné plus haut :

1) La première case est vide, on y met 0

| 0 | 1 |   | 0 |
|:-:|:-:|:-:|:-:|
|   |   | 0 |   |
|   | 0 |   |   |
| 1 | 1 |   | 0 |

2) La modification de la case 1 ne déroge pas aux 3 règles, on continue

3) La case 2 est pré-remplis, on continue

4) La case 3 est vide, on y met 0

| 0 | 1 | 0 | 0 |
|:-:|:-:|:-:|:-:|
|   |   | 0 |   |
|   | 0 |   |   |
| 1 | 1 |   | 0 |

5) La modification de la case 3 déroge à la règle 2, retour en arrière

6) La case 3 est modifiable, on y remplace 0 par 1

| 0 | 1 | 1 | 0 |
|:-:|:-:|:-:|:-:|
|   |   | 0 |   |
|   | 0 |   |   |
| 1 | 1 |   | 0 |

7) La modification de la case 3 ne déroge pas aux 3 règles, on continue

8) La case 4 est pré-remplis, on continue

9) La ligne est valide, est n'est présente qu'une seule fois, on passe à la résolution de la ligne suivante

## Utilisation du solver

La commande suivante permet d'obtenir l'usage du programme

    java Takuzu --help



Les commandes permetent de lancer la résolution du Takuzu qui suit

    java Takuzu 2120 2202 2022 1120

    java Takuzu .1?0 !:0A 80PL 11S0


|   | 1 |   | 0 |
|:-:|:-:|:-:|:-:|
|   |   | 0 |   |
|   | 0 |   |   |
| 1 | 1 |   | 0 |
