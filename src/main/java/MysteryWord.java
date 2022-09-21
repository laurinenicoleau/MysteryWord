package main.java;

import java.util.*;

public class MysteryWord {



    /**
     * 1. Lancement du jeu "Welcome to MysteryWord"
     * 2. Description du jeu, appuyez sur Entrée pour jouer.
     * 3. Choisir le niveau de difficulté, Tapez 1 pour Facile, 2 pour Moyen et 3 pour Difficile. Touche Entrée pour valider.
     * 4. Début de la partie
     * Récupération d'un mot selon le niveau choisi : 5 lettres (facile); 6 lettres (moyen); 7 lettres (difficile)
     * 5. On mélange les lettres du mot et on le propose au joueur
     * 6. Trois essais possibles pour retrouver le mot
     * 6.1 Premier essai : - le joueur trouve le mot "Félicitation vous avez gagné "
     * Pour Rejouer tapez 1 (Retour à l'étape 4), Pour Quittez la partie tapez 2
     * - le joueur ne trouve pas le mot "Ce n'est pas la bonne réponse, il vous reste deux essais" et on affiche le nombre de lettres correctes
     * 6.2 Deuxième essai : - le joueur trouve le mot "Félicitation vous avez gagné "
     * Pour Rejouer tapez O (Retour à l'étape 4), Pour Quittez la partie tapez N
     * -le joueur ne trouve pas le mot "Ce n'est pas la bonne réponse, il vous reste 1 essai" et on affiche le nombre de lettres correctes
     * 6.3 Troisième essai : -le joueur trouve le mot "Félicitation vous avez gagné "
     * - le joueur ne trouve pas le mot " Dommage, vous avez perdu ! "
     * Pour Rejouer tapez O (Retour à l'étape 4), pour Quittez la partie tapez N
     */

    Scanner sc = new Scanner(System.in);
    List<String> words;
    int level = 0;

    /**
     * Description des règles et choix du niveau.
     * Récupération de la liste de mots en fonction du niveau choisi.
     */
    public void setup() {
        System.out.println("Bienvenue dans MysteryWord !");
        System.out.println();
        System.out.println("Règles du jeu :");
        System.out.println("Retrouver le mot dont on a mélangé les lettres en 3 essais maximum. Trois niveaux disponibles.");
        System.out.println("Niveau facile : mots de 5 lettres. Moyen : mots de 6 lettres. Difficile : mots de 7 lettres");
        System.out.println("Chaque mot trouvé te fait gagner 10 points.");
        System.out.println();

        do {
            try {
                System.out.println("Choisir le niveau de difficulté [1 à 3]");
                System.out.println("1 - Facile ; 2 - Moyen ; 3 - Difficile. Touche Entrée pour valider.");
                level = sc.nextInt();
                if (level < 1 || level > 3) {
                    System.out.println("Seules les valeurs 1, 2 ou 3 sont attendues");
                } else {
                    if (level == 1) {
                        System.out.println("Niveau facile : mots de 5 lettres");
                    } else if (level == 2) {
                        System.out.println("Niveau moyen : mots de 6 lettres");
                    } else {
                        System.out.println("Niveau difficile : mots de 7 lettres");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Seules les valeurs 1, 2 ou 3 sont attendues");
            } finally {
                sc.nextLine();
            }
        } while (level < 1 || level > 3);

        // On récupère la liste de mots de la longueur souhaitée.
        words = FileUtils.getWordsByLength(getLength(level));
    }

    /**
     * Récupération d'un mot aléatoire et mélange des lettres.
     * Permet au joueur de trouver le mot.
     */
    public void play() {

        int score = 0;
        boolean retry = true;

        while (retry) {
            // on définit un index aléatoire entre 0 et la taille de la liste
            Random random = new Random();
            int index = random.nextInt(words.size() - 1);
            // on récupère le mot
            String word = words.get(index);
            // on mélange le mot
            String shuffledWord = shuffleString(word);

            System.out.println("Début de la partie !");

            int tryNumber = 1;
            boolean found = false;

            while (tryNumber <= 3 && !found) {
                System.out.println("Essai numéro " + tryNumber);
                System.out.println("Quel est ce mot ? " + shuffledWord);
                System.out.println();
                System.out.print("Saisir la réponse : ");

                String answer = sc.nextLine();
                // On vérifie que le nombre de lettres renseigné par le joueur est correct, autant de fois que nécessaire.
                while (answer.length() != getLength(level)) {
                    System.out.println("Le nombre de lettres ne correspond pas, es-tu sûr de ta réponse ?! Mais cherches encore !!!");
                    System.out.print("Saisir la réponse : ");
                    answer = sc.nextLine();
                }

                // On vérifie le nombre de lettre correct entre la réponse du joueur et le mot d'origine.
                // On créé la liste de lettre à partir du mot d'origine.
                List<String> wordSplit = Arrays.asList(word.split(""));
                // On créé la liste de lettre à partir de la réponse du joueur.
                List<String> answerSplit = Arrays.asList(answer.split(""));
                // On vérifie pour chaque lettre que sa place est correcte.
                int correctLetters = 0;
                for (String letter : wordSplit) {
                    int indexLetter = wordSplit.indexOf(letter);
                    if (letter.equalsIgnoreCase(answerSplit.get(indexLetter))) {
                        correctLetters++;
                    }
                }
                if (correctLetters == word.length()) {
                    score += 10;
                    System.out.println("Tu as gagné ! Ton score est de : " + score + " points");
                    found = true;
                } else {
                    System.out.println("Nombre de lettres correctes : " + correctLetters);
                    if (3 - tryNumber > 0) {
                        System.out.println("Essaie encore ! Nombre d'essais restants : " + (3 - tryNumber));
                    } else {
                        System.out.println();
                        System.out.println("Tu n'as pas trouvé ! Le mot mystère était :" + word);
                    }
                    tryNumber++;
                }
            }
            System.out.println();
            System.out.print("Veux-tu rejouer ? (O/N) ");
            String replay = sc.nextLine();
            while (!replay.equalsIgnoreCase("O") && !replay.equalsIgnoreCase("N")) {
                System.out.print("Seules les lettres O ou N sont acceptées ! Veux tu rejouer ? (O/N) ");
                replay = sc.nextLine();
            }
            if (replay.equalsIgnoreCase("N")) {
                retry = false;
            }
        }
    }

    /**
     * Mélange le mot mystère
     *
     * @param mysteryWord mot mystère
     * @return shuffledWord
     */
    private String shuffleString(String mysteryWord) {
        List<String> letters = Arrays.asList(mysteryWord.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }

    /**
     * Récupération de la longueur du mot par rapport au niveau choisi.
     *
     * @param level niveau choisi par le joueur.
     * @return length
     */
    private int getLength(int level) {
        int length;
        if (level == 1) {
            length = 5;
        } else if (level == 2) {
            length = 6;
        } else {
            length = 7;
        }
        return length;
    }

}
