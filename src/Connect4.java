import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Logic for Connect 4 Culminating
 * Maxim Beauchemin
 */

public class Connect4 extends JPanel implements KeyListener, ActionListener {
    int[][] game;
    int turn, c, r, display = 1, win, tie;
    
    Button bStart = new Button("Start Game"); //Title screen button to start the game
    Button bReturn = new Button("Return to Title Screen"); //Button to return to the title screen
    Button bHow = new Button("How to Play"); //Title screen button to show rules of Connect 4 and keybinds
    Button bRestart = new Button("Play Again?"); //When a player wins, prompts to restart
    ImageIcon titleIcon = new ImageIcon("assets/Logo.png"); //Connect 4 logo
    ImageIcon instructionsIcon = new ImageIcon("assets/HowToPlay.png"); //Image containing rules of Connect 4
    Color myLightBlue = new Color(224, 240, 255);

    //constructors
    public Connect4() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setLayout(null);
        
        bReturn.setBounds(10, 10, 250, 30);
        bStart.setBounds(90, 500, 300, 150);
        bHow.setBounds(470, 500, 300, 150);
        bRestart.setBounds(305, 450, 250, 100);
        
        bReturn.addActionListener(this);
        bStart.addActionListener(this);
        bHow.addActionListener(this);
        bRestart.addActionListener(this);
        
        bReturn.setBackground(myLightBlue);
        bStart.setBackground(myLightBlue);
        bHow.setBackground(myLightBlue);
        bRestart.setBackground(myLightBlue);
        
        bReturn.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        bStart.setFont(new Font("Sans Serif", Font.PLAIN, 50));
        bHow.setFont(new Font("Sans Serif", Font.PLAIN, 50));
        bRestart.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        
        add(bStart); //Shows both title screen buttons
        add(bHow);
        
        display = 2; //Displays the title screen upon opening
        startGame(); //Initialises all variables
    }
    
    public static void main(String[] args) {
        Connect4 c4 = new Connect4();
        JFrame f = new JFrame("Connect 4");
        f.setSize(860, 890); //Size of frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(c4);
        f.setVisible(true);
    }
    
    public void startGame() { //Resets all game variables
        game = new int[][] {
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1,  0,  0,  0,  0,  0,  0,  0, -1, -1, -1}, 
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 
        };
        //Array represents Connect4 board with 6 rows and 7 columns
        //-1s are for error catching when checking win conditions, 0s represent empty spaces
        
        turn = 1; //Even means it is yellow's turn, odd means it is red's turn
        c = 6; //Setting the column of the top token the middle
        win = 0; //0: play continues, 1: yellow won, 2: red won, 3: tie
        tie = 0; //Counts every top row that is filled
    }
    
    public void actionPerformed(ActionEvent e) {
        Object k = e.getSource();
        if (k == bStart) { 
            display = 1; //Displays the board
            remove(bHow); //Remove title screen buttons
            remove(bStart);
            add(bReturn); //Adds a button to return to title screen
        } else if (k == bReturn) {
            display = 2; //Displays the title screen
            remove(bReturn);
            add(bHow); //Adds the title screen buttons
            add(bStart);
            if (win != 0) {
                remove(bRestart); //Hides the Play Again if shown
                startGame(); //If a player won and returned to the title screen, the game resets
            }
        } else if (k == bHow) {
            display = 3; //Displays the How To Play screen
            remove(bHow); //Removes title screen buttons
            remove(bStart);
            add(bReturn); //Adds the Return to Title Screen Button
        } else if (k == bRestart) {
            remove(bRestart);
            startGame(); //Resets all variables and restarts the game
        }
        repaint(); //Once the display variable is changed, the JFrame must update
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (win == 0 && display == 1) {
            //checks that the correct player used the correct button and that the top row is not filled
            if ((k == KeyEvent.VK_DOWN && turn % 2 + 1 == 1 || k == KeyEvent.VK_S && turn % 2 + 1 == 2) && game[3][c] == 0) {
                logic(); //Finds where the token must be placed
                repaint(); //Updates the JFrame
            } else if ((k == KeyEvent.VK_LEFT && turn % 2 + 1 == 1 || k == KeyEvent.VK_A && turn % 2 + 1 == 2) && c > 3) {
                c--; //Moves the top token to the left
                repaint(); //Updates the JFrame
            } else if ((k == KeyEvent.VK_RIGHT && turn % 2 + 1 == 1 || k == KeyEvent.VK_D && turn % 2 + 1 == 2) && c < 9) {
                c++; //Moves the top token to the right
                repaint(); //Updates the JFrame
            }
        }
    }
    public void keyReleased(KeyEvent e) {

    }
    
    public void logic() {
        //For loop runs through all values of empty spaces in a column in the game array
        for (r = 8; r >= 3; r--) { 
            if (game[r][c] == 0) { //Checks for an empty space in all rows of the column
                game[r][c] = turn % 2 + 1; //The empty spot becomes a 2 if it was red, a 1 if it was yellow
                if (r == 3) {
                    tie++; //Increases the tie value by 1, indicating the top row has been filled
                }
                break; //Breaks from the loop to save the row value
            }
        }
        //Checks all win cases
        if ( 
               //Checks a horizontal line to the right
               game[r][c]==game[r][c+1] && game[r][c]==game[r][c+2] 
                && (game[r][c]==game [r][c+3] || game[r][c] == game[r][c-1])
                //Checks a horizontal line to the left
            || game[r][c]==game[r][c-1] && game[r][c]==game[r][c-2] 
                && (game[r][c]==game [r][c-3] || game[r][c] == game[r][c+1]) 
                //Checks a vertical line from below
            || game[r][c]==game[r+1][c] && game[r][c]==game[r+2][c] && game[r][c]==game [r+3][c]
                //Checks a diagonal going from top right to bottom left
            || game[r][c]==game[r+1][c+1] && game[r][c]==game[r+2][c+2] 
                && (game[r][c]==game[r+3][c+3] || game[r][c]==game[r-1][c-1])
            || game[r][c]==game[r-1][c-1] && game[r][c]==game[r-2][c-2] 
                && (game[r][c]==game[r-3][c-3] || game[r][c]==game[r+1][c+1])
                //Checks a diagonal going top left to bottom right
            || game[r][c]==game[r+1][c-1] && game[r][c]==game[r+2][c-2] 
                && (game[r][c]==game[r+3][c-3] || game[r][c]==game[r-1][c+1])
            || game[r][c]==game[r-1][c+1] && game[r][c]==game[r-2][c+2] 
                && (game[r][c]==game[r-3][c+3] || game[r][c]==game[r+1][c-1]))
        {
            win = game[r][c]; //Sets the win to whoever just played
            add(bRestart); //Prompts the players to play again
        } else if (tie == 7) { //Once all columns are full, the game is a tie
            win = 3; //Sets the game to a tie
            add(bRestart); //Prompts the players to play again
        } else {
            turn++; //Turn goes to the next player
        }
    }

    public void paint(Graphics g) {  
        Color backgroundBlue = new Color(10, 7, 43); //Background color
        Color myRed = new Color(196, 0, 0); //Color of the ted token
        Color myYellow = new Color(255, 237, 0); //Color of the yellow token
        
        super.paint(g); //Erases everything
        
        g.setColor(backgroundBlue);
        g.fillRect(0, 0, 860, 890); //Adds a background
        
        switch (display) {
            case 1: //Displays the board  
                g.setColor(new Color(155, 167, 179));
                g.fillRect(0, 0, 860, 150);
                //Nested for loop checks all empty spaces on the board for which player owns them
                for (int i = 0; i < 6; i++) { 
                    for (int j = 0; j < 7; j++) {
                        if (game[i + 3][j + 3] == 2) { //Red player the owns space
                            g.setColor(myRed);
                        } else if (game[i + 3][j + 3] == 1) { //Yellow player owns the space
                            g.setColor(myYellow);
                        } else { //No player owns the space
                            g.setColor(myLightBlue);
                        }
                        g.fillOval(j * 120 + 30, i * 120 + 180, 80, 80); //Fills a circle
                        g.setColor(Color.black);
                        g.drawOval(j * 120 + 30, i * 120 + 180, 80, 80); //Draws an outline for that circle
                    }   
                }
                //If no one has won, the program creates the top token in the colour of the current player
                if (win == 0) { 
                    if (turn % 2 + 1 == 2) {
                            g.setColor(myRed);
                    } else {
                            g.setColor(myYellow);
                    }
                    g.fillOval(c * 120 - 330, 50, 80, 80); //Creates the top token on top of whatever column is selected
                    g.setColor(Color.black);
                    g.drawOval(c * 120 - 330, 50, 80, 80);
                } 
                //Creates a transluscent box that displays who won and prompts the players to play agin
                else { 
                    g.setColor(new Color(24, 24, 40, 200));   
                    g.fillRect(240, 310, 380, 300);
                    g.setColor(Color.white);
                    g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
                    switch (win) {
                        case 1:
                            g.drawString("Yellow Player Wins!", 250, 400);
                            break;
                        case 2: 
                            g.drawString("Red Player Wins!", 275, 400);
                            break;
                        case 3: 
                            g.drawString("No Player Wins!", 280, 400);
                            break;
                        default:
                            System.out.println("Win ERROR");
                            break;
                    }
                }
                break;
            case 2: //Displays the title screen    
                titleIcon.paintIcon(this, g, 45, 150); //Image of the logo
                break;
            case 3: //Display the How To Play screen
                instructionsIcon.paintIcon(this, g, 5, 20); //Image of rules
                break;
            default: //Shows an error if no cases are true
                System.out.println("Display ERROR");
                break;
        }
    }
}