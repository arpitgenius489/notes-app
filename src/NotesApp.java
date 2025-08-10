// Main class for the Notes App
// Will implement CLI and file I/O logic here
public class NotesApp {
    // Path to notes file
    private static final String NOTES_FILE = "src/notes.txt";

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println("\n--- Notes App ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addNote(scanner);
                    break;
                case "2":
                    viewNotes();
                    break;
                case "3":
                    deleteNote(scanner);
                    break;
                case "4":
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Add a note to the file
    private static void addNote(java.util.Scanner scanner) {
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();
        try (java.io.FileWriter fw = new java.io.FileWriter(NOTES_FILE, true)) {
            fw.write(note + System.lineSeparator());
            System.out.println("Note added.");
        } catch (java.io.IOException e) {
            System.out.println("Error writing note: " + e.getMessage());
        }
    }

    // View all notes from the file
    private static void viewNotes() {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(NOTES_FILE))) {
            String line;
            int count = 1;
            System.out.println("\n--- Your Notes ---");
            while ((line = br.readLine()) != null) {
                System.out.println(count + ". " + line);
                count++;
            }
            if (count == 1) {
                System.out.println("No notes found.");
            }
        } catch (java.io.IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }

    // Delete a note by its number
    private static void deleteNote(java.util.Scanner scanner) {
        java.util.List<String> notes = new java.util.ArrayList<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(NOTES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                notes.add(line);
            }
        } catch (java.io.IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
            return;
        }
        if (notes.isEmpty()) {
            System.out.println("No notes to delete.");
            return;
        }
        viewNotes();
        System.out.print("Enter note number to delete: ");
        try {
            int num = Integer.parseInt(scanner.nextLine());
            if (num < 1 || num > notes.size()) {
                System.out.println("Invalid note number.");
                return;
            }
            notes.remove(num - 1);
            try (java.io.FileWriter fw = new java.io.FileWriter(NOTES_FILE, false)) {
                for (String note : notes) {
                    fw.write(note + System.lineSeparator());
                }
            }
            System.out.println("Note deleted.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (java.io.IOException e) {
            System.out.println("Error updating notes: " + e.getMessage());
        }
    }
}
