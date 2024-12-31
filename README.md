# Snake Game

This is a classic Snake game implemented in Java using Swing for the graphical user interface.

## How to Play

Use the arrow keys to control the snake's direction. The goal is to eat the red food blocks and grow longer. The game ends if the snake collides with itself or the game boundaries.

## Features

*   Classic Snake gameplay.
*   Score tracking.
*   High score tracking (in memory, resets on restart).
*   Sound effects for eating food and game over.
*   Retry option after game over.

## How to Run

1.  **Make sure you have Java installed** (JDK 8 or higher).
2.  **Compile the Java files.** Navigate to the `src` directory in your terminal and compile the Java files using the command:
    ```bash
    javac *.java
    ```
    or if you have package structure
      ```bash
    javac -d . *.java
    javac -d . Constant/*.java
    ```
    (Note: you need to compile from the src directory to create class files with correct folder structure)
3.  **Run the game:** Use the following command:
    ```bash
    java App
    ```
    or
    ```bash
    java -cp . App
    ```
    (Note: -cp . will make sure all class files in the current directory are available to the JRE)
4. If you have a jar file, then execute it as follows.
```bash
java -jar <jar-file-name>.jar
