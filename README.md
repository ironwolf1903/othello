# Othello (Reversi) – JavaFX Implementation

A graphical implementation of the board game **Othello (Reversi)** written in **Java** using **JavaFX**.  
This project was developed as part of a university programming assignment and focuses on clean game logic, extensible player strategies, and a responsive graphical user interface.

---

## 🎮 Features

- Fully playable **Othello (8×8)** board
- Graphical user interface built with **JavaFX**
- Turn-based gameplay with valid-move handling
- Automatic disc flipping according to Othello rules
- Support for different player types:
  - Human player (GUI-controlled)
  - AI players (e.g. Minimax-based strategy)
- Clear separation between **game logic**, **players**, and **GUI**

---

## 🖥️ Demo

A short gameplay demo (GIF) is shown below.

![Othello Gameplay Demo](https://github.com/ironwolf1903/othello/blob/submission/gui/src/main/resources/img/Media1.gif)





---

## 🧠 Project Structure

```
src/main/java/de/lmu/bio/ifi
├── Runner.java              # Aı configuration tester
├── gui
│   └── OthelloApp.java      # Main GUI application
├── game                     # Core game logic (board, rules, moves)
├── players
│   ├── HumanPlayer.java
│   ├── BMOMinimaxPlayer.java
│   └── ...
```

- **Runner**: Launches the JavaFX application  
- **GUI**: Handles rendering and user interaction  
- **Game logic**: Board state, move validation, and rules  
- **Players**: Pluggable player strategies (human or AI)

---

## ⚙️ Requirements

- **Java JDK 21**
- **Maven 3.9+**
- Tested on **Windows**

---

## ▶️ How to Run

The recommended way to run the application is via Maven using the JavaFX plugin:

```bash
mvn clean javafx:run
```

This automatically configures the JavaFX runtime and launches the GUI.

---

## 🧪 Build (optional)

To build the project without running it:

```bash
mvn clean package
```

> Note: The generated JAR is not intended to be executed directly with `java -jar` because JavaFX requires additional runtime configuration.

---

## 🧩 Extending the Project

The project is designed to be easily extensible:

- Add new AI strategies by implementing additional player classes
- Improve the Minimax evaluation function
- Extend the GUI with animations or statistics
- Modify the game logic for alternative board sizes or rules

---

## 📚 Technologies Used

- **Java 21**
- **JavaFX 21**
- **Maven**
- **Git & GitHub**

---

## 👤 Author

**Demir Kurt**  
Bioinformatics / Computer Science Student  

GitHub: https://github.com/ironwolf1903

---

## 📜 License

This project is intended for **educational purposes**.  
You are free to explore, study, and extend the code.
