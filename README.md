## Introduction

Le jeu japonais de Takuzu (parfois aussi appelé Binero) est un jeu de réflexion aux règles simples, se jouant sur un plateau carré usuellement de 10x10 cases partiellement rempli. Chaque case ne peut contenir que les valeurs 0 ou 1. Les règles sont les suivantes :

1. Il n'est pas possible d'aligner plus de 2 chiffres identiques cote à cote, en ligne ou en colonne.
2. Il doit y avoir autant de 0 et de 1 sur chaque ligne et colonne.
3. Les lignes et colonnes doivent être toutes différentes.

Voici un exemple, passant d'une grille partiellement pré-remplie à une grille remplie (source Wikipedia) :

![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Takuzu_unsolved_grid.svg/120px-Takuzu_unsolved_grid.svg.png "Grille partiellement pré-remplie")
![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/6/68/Takuzu_solved_grid.svg/120px-Takuzu_solved_grid.svg.png "Grille remplie")

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

Les commandes suivante sont équivalente, et permettent de lancer la résolution du Takuzu qui suit

    java Takuzu --solve 2120 2202 2022 1120
    java Takuzu --solve _1_0 __0_ _0__ 11_0
    java Takuzu --solve .1?0 !:0A 80PL 11S0


|   | 1 |   | 0 |
|:-:|:-:|:-:|:-:|
|   |   | 0 |   |
|   | 0 |   |   |
| 1 | 1 |   | 0 |

Le séparateur (correspondant à une case vide) n'est pas définit, seul les caractère "0" et "1" ne peuvent pas être utilisés en tant que tel puique réservé à des cases remplis.

## Technique utilisée par le générateur

Le générateur de Takuzu fait appel au solver avec un grille vide pour obtenir un Takuzu valide, tel que le suivant.

| 0 | 0 | 1 | 1 |
|:-:|:-:|:-:|:-:|
| 0 | 1 | 0 | 1 |
| 1 | 0 | 1 | 0 |
| 1 | 1 | 0 | 0 |

Il permute ensuite *n* fois (ou *n* correspond au nombre de ligne deu Takuzu) des lignes obtenu deux à deux aléatoirement du Takuzu, puis continue les permutation deux à deux aléatoirement jusqu'à obtention d'un Takuzu valide.

| 1 | 0 | 1 | 0 |
|:-:|:-:|:-:|:-:|
| 0 | 1 | 0 | 1 |
| 0 | 0 | 1 | 1 |
| 1 | 1 | 0 | 0 |

*Cette grille est donnée à titre d'exemple, les permutations de lignes reposant sur l'utilisation de la class java.util.Random, le résultat ne peut être prévu sans connaitre la graine utilisé*

Pour finir, il effectue un parcours de la grille, et retire aléatoirement des cases.

|   | 0 |   |   |
|:-:|:-:|:-:|:-:|
|   | 1 |   | 1 |
|   | 0 |   |   |
|   |   |   |   |

*Cette grille est à nouveau donnée à titre d'exemple, le retrait des cases reposant sur l'utilisation de la class java.util.Random, le résultat ne peut être prévu sans connaitre la graine utilisé*

## Utilisation du générateur

La commande suivante permet d'obtenir le Takuzu 4x4 partiellement pré-remplis qui suit :

    java Takuzu --generate 4

|   | 0 |   |   |
|:-:|:-:|:-:|:-:|
|   | 1 |   | 1 |
|   | 0 |   |   |
|   |   |   |   |

*Cette grille est à nouveau donnée à titre d'exemple, la génération reposant sur l'utilisation de la class java.util.Random, le résultat ne peut être prévu sans connaitre la graine utilisé*

La commande suivante permet d'obtenir le Takuzu 10x10 partiellement pré-remplis qui suit :

    java Takuzu --generate 10

| 0 |   |   |   |   | 1 |   |   |   | 1 |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| 0 |   |   |   |   | 1 |   |   |   | 1 |
| 1 | 1 |   |   |   |   |   |   | 1 |   |
|   |   |   |   | 1 |   | 0 | 1 |   |   |
|   |   |   |   |   |   |   | 0 |   |   |
|   | 1 |   |   |   |   |   |   | 0 |   |
|   |   |   |   |   | 0 |   |   |   |   |
|   |   | 1 |   | 1 |   |   |   | 0 | 1 |
|   |   |   |   |   |   |   |   |   |   |
|   |   | 1 | 0 |   |   | 1 |   |   |   |

*Cette grille est à nouveau donnée à titre d'exemple, la génération reposant sur l'utilisation de la class java.util.Random, le résultat ne peut être prévu sans connaitre la graine utilisé*

Le taux de remplissage par défaut est de 0.25 (25% de la grille est pré-remplis). Ce taux peut être modifié comme suit :

    java Takuzu --generate 10 1

| 0 | 0 | 1 | 0 | 1 | 1 | 0 | 1 | 0 | 1 |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| 0 | 0 | 1 | 0 | 0 | 1 | 1 | 0 | 1 | 1 |
| 0 | 0 | 1 | 0 | 1 | 1 | 0 | 0 | 1 | 1 |
| 1 | 1 | 0 | 1 | 1 | 0 | 0 | 1 | 0 | 0 |
| 1 | 1 | 0 | 1 | 0 | 1 | 0 | 0 | 1 | 0 |
| 1 | 1 | 0 | 1 | 0 | 0 | 1 | 0 | 1 | 0 |
| 0 | 0 | 1 | 0 | 1 | 0 | 1 | 0 | 1 | 1 |
| 1 | 1 | 0 | 1 | 0 | 1 | 0 | 1 | 0 | 0 |
| 1 | 1 | 0 | 1 | 0 | 0 | 1 | 1 | 0 | 0 |
| 0 | 0 | 1 | 0 | 1 | 0 | 1 | 1 | 0 | 1 |


*Le taux de remplissage à ici été remplacé par 1 (100% de la grille est pré-remplis)*

*De plus, cette grille est à nouveau donnée à titre d'exemple, la génération reposant sur l'utilisation de la class java.util.Random, le résultat ne peut être prévu sans connaitre la graine utilisé*
