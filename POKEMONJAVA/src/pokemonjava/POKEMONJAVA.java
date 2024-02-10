import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

public class POKEMONJAVA {
    public static void main(String[] args) {
        // Pokemon´s names and descriptions
        HashMap<String, List<String>> names = new HashMap<>();
        
        // Upload name´s file
        try (Scanner scanner = new Scanner(new File("pokemonnames.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                String name = parts[0];
                List<String> descriptions = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
                names.put(name, descriptions);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        List<String> namesOptions = new ArrayList<>(names.keySet());

        // Name random
        Random random = new Random();
        String pokemonToGuess = namesOptions.get(random.nextInt(namesOptions.size()));

        // Startup Message
        JOptionPane.showMessageDialog(null, "Welcome trainer! Guess them all.\nYou have 6 attempts to guess the Pokémon.", "Pokemon Guess Game", JOptionPane.INFORMATION_MESSAGE);

        boolean correctGuess = false;
        int attempts = 0;
        List<String> hints = new ArrayList<>();
        do {
            String guess = JOptionPane.showInputDialog(null, "Enter your guess:", "Pokemon Guess Game", JOptionPane.QUESTION_MESSAGE);
            if (guess == null) { 
                break;
            }
            attempts++;

            if (guess.equalsIgnoreCase(pokemonToGuess)) {
                JOptionPane.showMessageDialog(null, "Congratulations!! You did it.\nThe Pokémon was: " + pokemonToGuess, "Pokemon Guess Game", JOptionPane.INFORMATION_MESSAGE);
                correctGuess = true;
                // Mostrar imagen de correcto
                ImageIcon icon = scaleImage("correct.png", 300, 300);
                JLabel imageLabel = new JLabel(icon);
                JOptionPane.showMessageDialog(null, new Object[] {"", imageLabel}, "Correct Guess", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Incorrect
                hints.add(names.get(pokemonToGuess).get(0));
                if (attempts > 1) {
                    hints.add(names.get(pokemonToGuess).get(1));
                }
                StringBuilder message = new StringBuilder("Oops, it's not that.\nHere are the clues:\n");
                Set<String> uniqueHints = new LinkedHashSet<>(hints); // Remove duplicates
                for (String hint : uniqueHints) {
                    message.append("- ").append(hint).append("\n");
                }
                
                // Mostrar imagen de intento fallido
                ImageIcon icon = scaleImage("guess.png", 300, 300);
                JLabel imageLabel = new JLabel(icon);
                JOptionPane.showMessageDialog(null, new Object[] {message.toString(), imageLabel}, "Incorrect Guess", JOptionPane.INFORMATION_MESSAGE);
                
                if (attempts >= 6) {
                    JOptionPane.showMessageDialog(null, "You've run out of attempts. The Pokémon was: " + pokemonToGuess, "Pokemon Guess Game", JOptionPane.INFORMATION_MESSAGE);
                    // Mostrar imagen de intentos agotados
                    ImageIcon outOfAttemptsIcon = scaleImage("fail.png", 300, 300);
                    JLabel outOfAttemptsImageLabel = new JLabel(outOfAttemptsIcon);
                    JOptionPane.showMessageDialog(null, new Object[] {"", outOfAttemptsImageLabel}, "Out of Attempts", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } while (!correctGuess && attempts < 6);

        System.exit(0); // Ensure the program exits after closing the last dialog
    }

    // Método para escalar una imagen al tamaño especificado
    private static ImageIcon scaleImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}






