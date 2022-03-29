package Boggle;
/**
 * Date: June 16th, 2021
 * Group: Charles, Marco, William
 * Description: ISP Project - Boggle Game with fully functional GUI, Main Menu, and Methods
 */
//imported required Java Functions/API's

import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class BoggleFrame extends JFrame implements ActionListener { //implementing ActionListener
    //attributes
    private JFrame baseFrame; //base JFrame of the GUI

    //MAIN MENU VARIABLES
    private GridBagLayout menuFrameLayout;
    private JPanel menuPanel;

    //panels
    private JPanel buttons;
    private JPanel menuBottomPanel;

    private JLabel welcomeMessage; //main menu welcome message
    private JButton singlePlayerButton; //singleplayer button
    private JButton multiplayerButton; //multiplayer button
    private JButton rulesButton; //rules button
    private JButton settingsButton; //settings button

    //BOGGLE GAME VARIABLES
    private JPanel gamePanel; //main JPanel to store game components
    private BoxLayout frameLayout; //layout for the JPanel

    //create panel components
    private JPanel topPanel; //top panel
    private FlowLayout topLayout;
    private JButton returnMenu;
    private JLabel timerDisplay; //JLabel displaying the timer
    private JLabel playerOneLabel; //JLabel to display P1 label
    private JLabel playerTwoLabel; //JLabel to display P2 label

    private JPanel bottomPanel; //bottom panel
    private BoxLayout bottomLayout;

    private JPanel leftPanel; //left panel
    private BorderLayout leftLayout;
    private JTextField inputMessage; //text field to input word
    private JButton inputButton; //enter button to input word
    private JButton passButton; //pass button to pass turn

    private JTextArea playerOneWords; //left side word bank
    private JLabel playerOneText; //display text
    private JTextArea playerTwoWords; //right side word bank
    private JLabel playerTwoText;

    private JPanel inputPanelP1; //input panel
    private BoxLayout inputLayout;

    private JPanel inputPanelP2; //input panel version 2
    private BoxLayout inputLayoutP2; //input layout version 2

    private JPanel centerPanel; //center panel
    private BoxLayout centerLayout; //center layout
    private JLabel[][] boardArray; //2d array JLabel
    private GridLayout boardLayout; // grid layout

    private JPanel rightPanel; //right panel - cpu panel
    private BoxLayout rightLayout;
    private JLabel cpuText;
    private JTextArea cpuWordBank; //cpu word bank

    private JPanel rightPlayerPanel; //right panel - player2 panel
    private BorderLayout rightPlayerLayout;
    private JTextField inputMessageP2; //player2 text field
    private JButton inputButtonP2; //player2 enter button
    private JButton passButtonP2; //player2 pass button

    private JButton exitButton; // exit


    //LOGIC VARIABLES
    //game variables
    private boolean[][] usedCharsArray; //boolean keeps track of which chars have been used in recursive method
    private char[][] diceArray; //array that stores all possible characters each dice can have
    private ArrayList<String> wordBank; //array list to store dictionary
    private int boardWidth; //width of board
    private int boardLength; //length of board
    private int player1Score; //score of player 1
    private int player2Score; // score of player 2

    //util variables
    private Scanner scanner; //new scanner
    private File file; //new file
    private Timer timer;

    //checker variables
    private int player1PassCounter;//keeps track of passes for player 1
    private int player2PassCounter;//keeps track of passes for player 2
    private ArrayList<String> guessedWords;//keeps track of which words were have been guessed
    private String rules;
    private boolean gameEnd;

    //game settings
    private int minWordLength; //keep track of minimum word length 
    private int timerLength; // keep track of timer length setting
    private int remainingTime; // record remaining time
    private boolean CPUEnabled; // keep track of whether AI is activated
    private int currentTurn;
    private int maxScore; // max score

    private int AIChance; //int variable to store AI chance


    //CUSTOM COLOURS
    public static final Color BASE_LIGHT = new Color(192, 220, 255, 255);
    public static final Color BASE_DARK = new Color(33, 49, 89, 255);
    public static final Color BASE_GREYED = new Color(235, 241, 243, 255);
    public static final Color BASE_SATURATED = new Color(143, 188, 243, 255);
    public static final Color ACCENT_COLOUR = new Color(250, 205, 115, 255);

    /**
     * creates main menu method
     */
    private void constructMainMenu() { //main menu constructor
        baseFrame.setSize(1000, 600); //setting main menu size
        baseFrame.setLocationRelativeTo(null);

        //MAIN MENU GUI
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());  // setting menuPanel layout
        menuPanel.setBorder(new EmptyBorder(50, 10, 10, 10)); // set boarder of menuPanel
        menuPanel.setBackground(BASE_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;//sets current position to north when adding new element

        welcomeMessage = new JLabel("BOGGLE");//create new label to welcome players
        welcomeMessage.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 96)); // set font of welcome message
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setAlignmentX(JLabel.CENTER);  // set allignment for the welcome
        welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
        menuPanel.add(welcomeMessage, gbc);  // add welcome message to the menuPanel

        // specifying constraints of gridbag layout
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        //set up buttons panel
        buttons = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout(); //utilising GridBagLayout
        buttons.setLayout(gridBagLayout);

        // creation of singerPlayerButton
        singlePlayerButton = new JButton("Play Single Player");//create button to allow user to choose single player mode
        singlePlayerButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // set the font of singlePlayerButton
        singlePlayerButton.setBackground(BASE_GREYED);
        singlePlayerButton.setFocusable(false);
        singlePlayerButton.setPreferredSize(new Dimension(400, 50));
        singlePlayerButton.setAlignmentX(JButton.CENTER);
        singlePlayerButton.setHorizontalAlignment(JButton.CENTER);
        singlePlayerButton.addActionListener(this);
        buttons.add(singlePlayerButton, gbc);

        // creation of multiPlayerButton
        multiplayerButton = new JButton("Play Multiplayer"); //setting the name for the multiplayer button
        multiplayerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        multiplayerButton.setBackground(BASE_GREYED);
        multiplayerButton.setFocusable(false);
        multiplayerButton.setPreferredSize(new Dimension(400, 50));
        multiplayerButton.setAlignmentX(JButton.CENTER);
        multiplayerButton.setHorizontalAlignment(JButton.CENTER);
        multiplayerButton.addActionListener(this);
        buttons.add(multiplayerButton, gbc);

        //creation of menuBottomPanel
        menuBottomPanel = new JPanel();
        FlowLayout bottomPanelLayout = new FlowLayout();
        menuBottomPanel.setLayout(bottomPanelLayout); // set the layout for menuBottomPanel

        //creation of rulesButton
        rulesButton = new JButton("Rules"); //setting the name for the rules button
        rulesButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rulesButton.setBackground(BASE_GREYED);
        rulesButton.setFocusable(false);
        rulesButton.setPreferredSize(new Dimension(200, 50));
        rulesButton.setAlignmentX(JButton.CENTER);
        rulesButton.setHorizontalAlignment(JButton.CENTER);
        rulesButton.addActionListener(this);
        menuBottomPanel.add(rulesButton, gbc);

        //creation of settingsButton
        settingsButton = new JButton("Settings");//setting the name for the settingsButton
        settingsButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        settingsButton.setBackground(BASE_GREYED);
        settingsButton.setFocusable(false);
        settingsButton.setPreferredSize(new Dimension(200, 50));
        settingsButton.setAlignmentX(JButton.CENTER);
        settingsButton.setHorizontalAlignment(JButton.CENTER);
        settingsButton.addActionListener(this);
        menuBottomPanel.add(settingsButton, CENTER_ALIGNMENT);
        menuBottomPanel.setOpaque(false);
        buttons.setOpaque(false);
        buttons.add(menuBottomPanel, gbc);

        gbc.weighty = 1; // specifying weights of GridBagLayout

        menuPanel.add(buttons, gbc); // add buttons to menuPanel

        baseFrame.add(menuPanel); // add menuPanel to baseFrame
        menuPanel.setOpaque(true);
        menuPanel.setVisible(true); // set visible to true

        minWordLength = 3; // set the min word length
        maxScore = 61;  // set the winning conditions
        timerLength = 15;  // set the timer settings
    }

    /**
     * creates boggle panel
     *
     * @throws FileNotFoundException - if file not found
     */
    private void constructBoggleGame() throws FileNotFoundException {
        baseFrame.setSize(1000, 600);  // set the frame size
        baseFrame.setLocationRelativeTo(null);
        //BOGGLE GAME PANEL

        //creation of gamePanel
        gamePanel = new JPanel();
        frameLayout = new BoxLayout(gamePanel, BoxLayout.Y_AXIS);
        gamePanel.setLayout(frameLayout);  // apply frame
        gamePanel.setSize(1000, 600);
        //gameFrame.getContentPane();

        //top panel, player one & two labels + timer display
        topLayout = new FlowLayout(FlowLayout.LEFT, 107, 20); // apply flowlayout
        topPanel = new JPanel(topLayout);
        topPanel.setBackground(BASE_DARK);

        returnMenu = new JButton("Back"); //create return to menu button
        returnMenu.setBackground(BASE_LIGHT);
        returnMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        returnMenu.setFocusable(false);
        returnMenu.addActionListener(this);
        timerDisplay = new JLabel("Time: ");  // create timerDisplay label
        timerDisplay.setFont(new Font("Segoe UI", Font.BOLD, 16));  // set the font of timerDisplay label
        timerDisplay.setForeground(Color.WHITE);
        playerOneLabel = new JLabel("<- P1 Score"); // create player one label
        playerOneLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playerOneLabel.setForeground(Color.WHITE);
        playerTwoLabel = new JLabel("P2 Score ->"); // create player two label
        playerTwoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playerTwoLabel.setForeground(Color.WHITE);

        //add player one & two labels + timer display to top panel
        topPanel.add(returnMenu);
        topPanel.add(timerDisplay);
        topPanel.add(playerOneLabel);
        topPanel.add(timerDisplay);
        topPanel.add(playerTwoLabel);

        //add topPanel to gamePanel
        gamePanel.add(topPanel);

        //creation of bottomPanel
        bottomPanel = new JPanel();
        bottomLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        bottomPanel.setLayout(bottomLayout);


        //initialize board array size variables (and used chars array to false)
        boardLength = 5;
        boardWidth = 5;


        // populate the arraylist with words on dictionary
        populateWordBank();

        //populate dice array with all 6 possible characters for each die
        diceArray = new char[25][6];
        populateDiceArray();

        // keep track of words that have been guessed by players
        guessedWords = new ArrayList<String>();


        //left panel, playerOne Area
        leftPanel = new JPanel();
        leftLayout = new BorderLayout();
        leftPanel.setLayout(leftLayout);

        // creation of input panel
        inputPanelP1 = new JPanel();
        inputLayout = new BoxLayout(inputPanelP1, BoxLayout.X_AXIS);
        inputPanelP1.setLayout(inputLayout);
        inputPanelP1.setSize(new Dimension(100, 25));


        // creation of inputMessage and inputButton
        inputMessage = new JTextField();
        inputMessage.setPreferredSize(new Dimension(50, 25));
        inputButton = new JButton("Enter");
        inputButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputButton.setBackground(ACCENT_COLOUR);
        inputButton.setMargin(new Insets(1, 1, 1, 1));
        inputButton.setFocusable(false);
        inputButton.setPreferredSize(new Dimension(50, 25));
        inputButton.addActionListener(this);

        // create pass buttons for user to provide them opportunities to pass their turn
        passButton = new JButton("Pass");
        passButton.setFocusable(false);
        passButton.setPreferredSize(new Dimension(50, 25));
        passButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passButton.setBackground(ACCENT_COLOUR);
        passButton.setMargin(new Insets(1, 1, 1, 1));
        passButton.addActionListener(this);

        // create playerOneText to collect player1 input
        playerOneText = new JLabel("Player One's Word Bank");
        playerOneText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerOneText.setBackground(BASE_SATURATED);
        playerOneText.setOpaque(true);
        playerOneText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playerOneText.setHorizontalAlignment(SwingConstants.CENTER);
        playerOneWords = new JTextArea();
        playerOneWords.setEditable(false);

        // add components to GUI
        leftPanel.add(playerOneText, BorderLayout.PAGE_START);
        inputPanelP1.add(inputMessage);
        inputPanelP1.add(inputButton);
        inputPanelP1.add(passButton);
        leftPanel.add(playerOneWords, BorderLayout.CENTER);
        leftPanel.add(inputPanelP1, BorderLayout.PAGE_END);
        bottomPanel.add(leftPanel);

        //center panel, Boggle Board
        boardLayout = new GridLayout(5, 5, 3, 3);

        centerPanel = new JPanel(boardLayout);
        centerPanel.setPreferredSize(new Dimension(500, 500));
        centerPanel.setBackground(BASE_LIGHT);

        //populate boardArray with a random character from each die's 6 possible characters, as a JLabel
        populateGameBoard();


        bottomPanel.add(centerPanel);

        //right panel, player2 / cpu section
        rightPanel = new JPanel();
        rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
        rightPanel.setLayout(rightLayout);
        cpuText = new JLabel("CPU's Word Bank");
        cpuText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cpuText.setBackground(BASE_SATURATED);
        cpuText.setOpaque(true);
        cpuText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        cpuWordBank = new JTextArea();
        cpuWordBank.setEditable(false);

        // add cpuText and cpuWordBank
        rightPanel.add(cpuText, CENTER_ALIGNMENT);
        rightPanel.add(cpuWordBank);
        rightPanel.setVisible(true);
        bottomPanel.add(rightPanel);


        //right panel for two player mode
        rightPlayerPanel = new JPanel();
        rightPlayerLayout = new BorderLayout();
        rightPlayerPanel.setLayout(rightPlayerLayout);

        // creation of input PanelP2
        inputPanelP2 = new JPanel(); //enter panel for p2
        inputLayoutP2 = new BoxLayout(inputPanelP2, BoxLayout.X_AXIS); //boxlayout
        inputPanelP2.setLayout(inputLayoutP2);
        inputPanelP2.setSize(new Dimension(100, 25));

        // create playerOneText to collect player2 input
        inputMessageP2 = new JTextField();
        inputMessageP2.setPreferredSize(new Dimension(50, 25));
        inputButtonP2 = new JButton("Enter");
        inputButtonP2.setFont(new Font("Rockwell", Font.PLAIN, 16));
        inputButtonP2.setBackground(ACCENT_COLOUR);

        inputButtonP2.setMargin(new Insets(1, 1, 1, 1));
        inputButtonP2.setFocusable(false);
        inputButtonP2.setPreferredSize(new Dimension(50, 25));
        inputButtonP2.addActionListener(this);

        // create passButton2 to give player2 choices to pass their turn
        passButtonP2 = new JButton("Pass");
        passButtonP2.setFocusable(false);
        passButtonP2.setPreferredSize(new Dimension(50, 25));
        passButtonP2.setFont(new Font("Rockwell", Font.PLAIN, 16));
        passButtonP2.setBackground(ACCENT_COLOUR);
        passButtonP2.setMargin(new Insets(1, 1, 1, 1));
        passButtonP2.addActionListener(this);

        // display the inputted correct results of player 2
        playerTwoText = new JLabel("Player Two's Word Bank");
        playerTwoText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerTwoText.setBackground(BASE_SATURATED);
        playerTwoText.setOpaque(true);
        playerTwoText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playerTwoText.setHorizontalAlignment(SwingConstants.CENTER);
        playerTwoWords = new JTextArea();
        playerTwoWords.setEditable(false);

        // add components to GUI
        rightPlayerPanel.add(playerTwoText, BorderLayout.PAGE_START);
        inputPanelP2.add(inputMessageP2);
        inputPanelP2.add(inputButtonP2);
        inputPanelP2.add(passButtonP2);
        rightPlayerPanel.add(playerTwoWords, BorderLayout.CENTER);
        rightPlayerPanel.add(inputPanelP2, BorderLayout.PAGE_END);
        rightPlayerPanel.setVisible(true);
        bottomPanel.add(rightPlayerPanel);

        // add bottomPanel to game Panel
        gamePanel.add(bottomPanel);
        // add gamePanel to baseFrame
        baseFrame.add(gamePanel);
        gamePanel.setVisible(true); // setVisible to true

        shakeTheBoard(); // randomize the board
    }

    // create the constructor
    public BoggleFrame() throws FileNotFoundException {

        // create the baseFrame
        baseFrame = new JFrame();
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseFrame.setResizable(true);
        baseFrame.setTitle("BOGGLE");

        // create the main menu
        constructMainMenu();

        baseFrame.setResizable(false); //disables ability to resize the window
        baseFrame.setVisible(true);
        //setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {


        //MAIN MENU
        if (e.getSource() == singlePlayerButton) {  //if the single player button is pressed
            player1Score = 0;
            player2Score = 0;
            gameEnd = false;
            try { //try catch
                menuPanel.setVisible(false);
                constructBoggleGame();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            bottomPanel.remove(rightPlayerPanel); //removing the right player panel
            bottomPanel.add(rightPanel); //adding the right panel
            CPUEnabled = true; //setting cpu enabled to true
            AIChance = 100;
            currentTurn = 1;
            switchTurns(currentTurn); //invoke switchTurns method
        }
        if (e.getSource() == multiplayerButton) { //if the multiPlayer button is pressed
            player1Score = 0;
            player2Score = 0;
            gameEnd = false;
            try {  //try catch
                menuPanel.setVisible(false);
                constructBoggleGame();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            bottomPanel.remove(rightPanel); //removing the right player panel
            bottomPanel.add(rightPlayerPanel);//adding the right panel
            currentTurn = firstTurnDecider();
            switchTurns(currentTurn);  // switch turns between players
        }

        if (e.getSource() == rulesButton) { // if the rulesButton button is pressed
            displayRules();  // display rules on GUI
        }
        if (e.getSource() == settingsButton) { // if the rulesButton button is pressed
            JOptionPane.showMessageDialog(null,
                    "Please check console.",
                    "Change Settings",
                    JOptionPane.PLAIN_MESSAGE);  // prompt the user to check console

            scanner = new Scanner(System.in);  // collect user input
            int userChoice = -1;
            while (userChoice != 0) {  // create the menu on console
                System.out.println("Boggle Game Settings:" +
                        "\n1. Set Minimum Word Length" +
                        "\n2. Set Round Timer Length" +
                        "\n3. Set Maximum Score" +
                        "\n0. Exit\n");
                System.out.print("Please choose a number: "); // prompt the user to choose
                userChoice = scanner.nextInt();
                if (userChoice == 1) { // user choose to change settings based on their choice
                    System.out.println("\nCurrent minimum word length = " + minWordLength);
                    do {
                        System.out.print("Please enter a new minimum word length: ");
                        minWordLength = scanner.nextInt();  // change the minWordLength
                    } while (minWordLength <= 0);
                } else if (userChoice == 2) {
                    System.out.println("\nCurrent round timer length = " + timerLength);
                    do {
                        System.out.print("Please enter a new round timer length: ");
                        timerLength = scanner.nextInt();   // change timerLength
                    } while (timerLength <= 0);
                } else if (userChoice == 3) {
                    System.out.println("\nCurrent maximum score = " + maxScore);
                    do {
                        System.out.print("Please enter a new maximum score: ");
                        maxScore = scanner.nextInt();   // change maxScore
                    } while (maxScore <= 0);
                } else if (userChoice == 0) {
                    System.out.println("Settings Saved.");
                    break;
                }
                System.out.println();
            }
        }

        //GAMEBOARD
        try {
            if (e.getSource() == (returnMenu)) { //return to menu button
                gamePanel.setVisible(false); //sets gamePanel to false visible
                menuPanel.setVisible(true); //sets menu panel to true
                timer.stop(); //stop the timer
            }
        } catch (Exception exception) {
        }
        try {
            if (e.getSource() == (inputButton)) { //if enter button is pressed

                String userInput = inputMessage.getText().toLowerCase(); //getText from textfield and convert to lowercase
                inputMessage.setText(""); //clear textfield

                if (guessedWords.contains(userInput)) { //if word has already been used before
                    //insert error message
                    JOptionPane.showMessageDialog(null, "Word already has been used", "Oopsie!", JOptionPane.ERROR_MESSAGE); //error pane
                } else if (userInput.length() < minWordLength) {
                    JOptionPane.showMessageDialog(null, "Word is smaller than the minimum word length!", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (wordOnBoard(userInput)) { //if it s a new word
                    if (recursiveBinarySearch(wordBank, 0, wordBank.size(), userInput)) {
                        playerOneWords.append(userInput + "\n"); //add to the player's text area
                        guessedWords.add(userInput); //add to guessed words
                        addScore(userInput, currentTurn); //update score
                        winCheck(); //check for win condition

                    } else { //else if word does not exist at all
                        JOptionPane.showMessageDialog(null, "Word does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else { //else if the word does not exit on the boggle board
                    JOptionPane.showMessageDialog(null, "Word is not on board", "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (player1Score < maxScore || player2Score < maxScore) {
                    CPUDelay();
                    currentTurn = 2;
                    switchTurns(currentTurn);
                }
            }
        } catch (Exception exception) {
        }

        if (e.getSource() == (passButton)) { //if passButton is pressed
            player1PassCounter++;
            doublePassCheck(); //checks if both players have passed twice
            if (player1Score < maxScore || player2Score < maxScore) {
                CPUDelay();

                currentTurn = 2;
                switchTurns(currentTurn);
            } else {
                switchTurns(1);
                timer.stop();
            }

        }

        try {
            if (e.getSource() == (inputButtonP2)) { // if the user clicked inputButtonP2

                String userInput2 = inputMessageP2.getText().toLowerCase();
                inputMessageP2.setText("");
                if (guessedWords.contains(userInput2)) {  // check if the word has been used
                    //insert error message
                    JOptionPane.showMessageDialog(null, "Word already has been used", "Oopsie!", JOptionPane.ERROR_MESSAGE);
                } else if (userInput2.length() < minWordLength) { // check word min length
                    JOptionPane.showMessageDialog(null, "Word is smaller than the minimum word length!", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (wordOnBoard(userInput2)) {  //
                    if (recursiveBinarySearch(wordBank, 0, wordBank.size(), userInput2)) {  // recursively check validity of words
                        playerTwoWords.append(userInput2 + "\n");
                        guessedWords.add(userInput2);
                        addScore(userInput2, currentTurn);
                        winCheck();
                    } else {// print error message
                        JOptionPane.showMessageDialog(null, "Word does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Word is not on board", "Error", JOptionPane.ERROR_MESSAGE);
                }
                currentTurn = 1;
                switchTurns(currentTurn);
            }
        } catch (Exception exception) {
        }

        if (e.getSource() == (passButtonP2)) {
            player2PassCounter++;
            doublePassCheck(); //check if both players have passed twice
            currentTurn = 1;
            switchTurns(currentTurn);

        }


    }

    /**
     * shows user the rules in main menu
     */
    private void displayRules() {
        rules = "";

        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("BoggleRules.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                rules = (rules + strLine + "\n");
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {
        }

        JOptionPane.showMessageDialog(null,
                rules,
                "Boggle Rules",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * populate array with all possible characters for each die
     *
     * @throws FileNotFoundException - if file not found
     */
    private void populateDiceArray() throws FileNotFoundException {

        file = new File("diceCharacters.txt"); //read from diceCharacters.txt

        scanner = new Scanner(file);

        while (scanner.hasNext()) {//runs while file has more text

            for (int i = 0; i < diceArray.length; i++) {// for loop sets i index

                String temp = scanner.nextLine();//chars are taken from this string

                for (int j = 0; j < diceArray[i].length; j++) {//for loop sets j index

                    diceArray[i][j] = temp.charAt(j); //assigns the char at the corresponding index to the dice array variable

                }
            }
        }
    }

    /**
     * assigns a random character from each die's list of characters appropriately
     */
    // populate the game board
    private void populateGameBoard() {
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();

        boardArray = new JLabel[boardWidth][boardLength]; // declare a 2d array

        int diceArrayCounter = 0;

        for (int r = 0; r < boardArray.length; r++) { //for loop for r index

            for (int c = 0; c < boardArray[r].length; c++) { //for loop for c index

                int randomTemp = (int) (Math.random() * 6); //random integer from 0-5
                char temp = diceArray[diceArrayCounter++][randomTemp];
                boardArray[r][c] = new JLabel(String.valueOf(temp)); //assigns the random char to index
                boardArray[r][c].setOpaque(true);
                boardArray[r][c].setHorizontalAlignment(SwingConstants.CENTER);
                boardArray[r][c].setVerticalAlignment(SwingConstants.CENTER);
                boardArray[r][c].setFont(new Font("Segoe UI Semibold", Font.PLAIN, 48));
                boardArray[r][c].setBackground(Color.WHITE);
                centerPanel.add(boardArray[r][c], CENTER_ALIGNMENT);


            }
        }
    }

    /**
     * stores all the words in a file into an array to be sorted
     *
     * @throws FileNotFoundException - incase file is not found
     */
    private void populateWordBank() throws FileNotFoundException {

        File file = new File("dictionary.txt");//create new file

        Scanner read = new Scanner(file);//create new scanner object to read lines in file

        wordBank = new ArrayList<String>();

        //runs as long as file has more lines
        while (read.hasNext()) {
            wordBank.add(read.nextLine());//adds current line into the arrayList
        }
    }

    /**
     * recursive method that checks if the target word is contained within a list
     *
     * @param arr    - list to search from
     * @param min    - first index of the array
     * @param max    - last index of the array
     * @param target - word to search for
     * @return - true if found; false if not
     */
    private boolean recursiveBinarySearch(ArrayList<String> arr, int min, int max, String target) {

        int mid = (min + max) / 2;

        if (min <= max) {//ensures that the array size is greater than 1

            if (arr.get(mid).equals(target)) {//base case; if middle value is equal to target

                return true;

            } else if (arr.get(mid).compareTo(target) < 0) {

                return recursiveBinarySearch(arr, mid + 1, max, target);//recursive call to search the upper half of the array

            } else if (arr.get(mid).compareTo(target) > 0) {

                return recursiveBinarySearch(arr, min, mid - 1, target);//recursive call to search teh bottom half of the array

            }

        }
        return false;
    }

    /**
     * sequential search to find all instances of the fist character of target word
     *
     * @param target - word to search fo
     * @return - returns true if word found; false if word not found
     */
    private boolean wordOnBoard(String target) {

        if (target.length() > 25) return false;//a target length greater than 25, physically cannot fit on the board

        for (int r = 0; r < boardArray.length; r++) {

            for (int c = 0; c < boardArray[r].length; c++) {

                if (boardArray[r][c].getText().toLowerCase().equals(target.substring(0, 1))) { //call recursive method to check adjacent dice

                    usedCharsArray = new boolean[5][5];

                    if (recursiveCharSearch(target, 0, r, c)) { //calls recursive search with loop counters
                        return true;

                    }

                }

            }
        }
        return false;
    }

    /**
     * recursive method to check that each charcter in word is adjacent and exists
     * @param target    - word to check for
     * @param wordIndex - counter for the index for characters
     * @param r         - row index
     * @return - returns true if word found; false if word not found
     */
    private boolean recursiveCharSearch(String target, int wordIndex, int r, int c) {

        if (r < 0 || c < 0 || r >= boardWidth || c >= boardLength)
            return false; //base case prevents checking characters outside of the board

        if (usedCharsArray[r][c]) return false;

        if (boardArray[r][c].getText().toLowerCase().equals(target.substring(target.length() - 1, target.length())) && wordIndex == (target.length() - 1))
            return true;

        //if the current character on board is equal to the character at x index in the target word
        if (boardArray[r][c].getText().toLowerCase().equals(target.substring(wordIndex, (wordIndex + 1))) && target.length() - 1 >= wordIndex) {

            wordIndex++;
            usedCharsArray[r][c] = true; //track which characters on the board have been used
            //recursive calls

            if (recursiveCharSearch(target, wordIndex, r + 1, c)) return true;

            if (recursiveCharSearch(target, wordIndex, r + 1, c + 1)) return true; //bottom right

            if ((recursiveCharSearch(target, wordIndex, r, c + 1))) return true; //middle right

            if ((recursiveCharSearch(target, wordIndex, r - 1, c + 1))) return true; //top right

            if ((recursiveCharSearch(target, wordIndex, r - 1, c))) return true; //top

            if (recursiveCharSearch(target, wordIndex, r - 1, c - 1)) return true; //top left

            if (recursiveCharSearch(target, wordIndex, r, c - 1)) return true; //middle left

            if (recursiveCharSearch(target, wordIndex, r + 1, c - 1)) return true; //bottom left

            usedCharsArray[r][c] = false;

        }

        return false;
    }

    /**
     * switches player turn
     *
     * @param playersTurn - current player turn
     */
    private void switchTurns(int playersTurn) {
        try { // try catch
            timer.stop();
        } catch (Exception ex) {
        }
        if (playersTurn == 1) { // turn off and turn on certain feature for player 1 and 2


            inputMessage.setEnabled(true);
            inputMessageP2.setEnabled((false));
            inputButton.setEnabled(true);
            inputButtonP2.setEnabled(false);
            passButton.setEnabled(true);
            passButtonP2.setEnabled(false);
            //assign new colors
            playerOneWords.setBackground(Color.white);
            playerTwoWords.setBackground(Color.LIGHT_GRAY);
            playerOneWords.setForeground(Color.BLACK);
            playerTwoWords.setForeground(Color.GRAY);
            startTurnTimer();


        } else if (playersTurn == 2) { // turn off and turn on certain feature for player 1 and 2

            inputMessage.setEnabled(false);
            inputMessageP2.setEnabled((true));
            inputButton.setEnabled(false);
            inputButtonP2.setEnabled(true);
            passButton.setEnabled(false);
            passButtonP2.setEnabled(true);
            playerOneWords.setBackground(Color.LIGHT_GRAY);
            playerTwoWords.setBackground(Color.white);
            playerOneWords.setForeground(Color.GRAY);
            playerTwoWords.setForeground(Color.BLACK);
            startTurnTimer();
        }
    }

    /**
     * updates the board timer
     */
    private void updateTimer() {  // update the timer constantly
        timerDisplay.setText("Timer: " + remainingTime);
        remainingTime--;
    }

    /**
     * starts timer using user chosed timer length for each round
     */
    private void startTurnTimer() { // start the timer for game

        if (!gameEnd) {  // if the game is not end

            remainingTime = timerLength;

            ActionListener countdown = new ActionListener() {//action listener used by the timer
                int counter;

                public void actionPerformed(ActionEvent e) {
                    updateTimer();
                    if (counter == timerLength) {

                        JOptionPane.showMessageDialog(null, "Timer ran out!\nPress 'ok' to resume.", "Oopsie!", JOptionPane.ERROR_MESSAGE);
                        if (currentTurn == 1) {
                            ((Timer) e.getSource()).stop();
                            currentTurn = 2;
                            switchTurns(currentTurn);
                            CPUDelay();
                        } else if (currentTurn == 2) {
                            ((Timer) e.getSource()).stop();
                            currentTurn = 1;
                            switchTurns(currentTurn);
                        }
                    }
                    counter++;
                }
            };
            timer = new Timer(1000, countdown);//timer adds a delay between recursive calls
            timer.start();
        }

    }

    /**
     * method checks to see if players have each passed twice, if so, option to shake board is given
     */
    private void doublePassCheck() { // check if each player has passed twice
        if (player1PassCounter >= 2 && player2PassCounter >= 2) {
            int shakeBoardResult = JOptionPane.showConfirmDialog(this, "Each player has passed twice. \nWould you like to 'shake up the board'?", "2 Passes Detected", JOptionPane.YES_NO_OPTION);
            if (shakeBoardResult == JOptionPane.YES_OPTION) {
                shakeTheBoard();  // randomize the board if each plater has passed twice
            }
        }
    }

    /**
     * chooses new random letters per die to fill the board array
     */
    private void shakeTheBoard() { // randomize the board
        player1PassCounter = 0;
        player2PassCounter = 0;
        guessedWords.clear();  // clear the guessed words

        playerOneWords.append("--\n");
        playerTwoWords.append("--\n");

        ActionListener boardAnimation = new ActionListener() {//action listener used by the timer
            int counter;

            public void actionPerformed(ActionEvent e) {
                populateGameBoard();
                if (counter == 15) {
                    ((Timer) e.getSource()).stop();
                }
                counter++;
            }
        };
        Timer timer2 = new Timer(100, boardAnimation);//timer adds a delay between recursive calls
        timer2.start();

    }

    /**
     * based on chance, the cpu will either find a word, or fail to find a word and pass (if CPU fails, console will have a print line)
     */
    private void CPUTurn() {
        int randNumber = (int) (Math.random() * 100);
        int randWord = 0; //index in word bank arrayList

        if (randNumber < AIChance) {

            boolean wordFound = false;
            while (!wordFound) {
                //make sure word is greater than min word count
                randWord = (int) (Math.random() * 109583);
                if (wordOnBoard(wordBank.get(randWord)) && wordBank.get(randWord).length() >= minWordLength && !guessedWords.contains(wordBank.get(randWord)))
                    wordFound = true;
            }
            cpuWordBank.append(wordBank.get(randWord) + "\n");
            guessedWords.add(wordBank.get(randWord)); //adds word guessed by cpu to the list of guessed words
            addScore(wordBank.get(randWord), currentTurn);
            if (AIChance > 15) AIChance -= 2;
            winCheck();
            currentTurn = 1;
            switchTurns(currentTurn);
        } else { //computer has to mess up twice
            player2PassCounter++;
            currentTurn = 1;
            switchTurns(currentTurn);
            System.out.println("CPU Fail, +1 pass");

        }
    }

    /**
     * adds a delay before cpu turn for quality of life
     */
    private void CPUDelay() {  // create a delay CPU method

        if (CPUEnabled) {
            ActionListener CPUTurnWithDelay = new ActionListener() {//action listener used by the timer
                public void actionPerformed(ActionEvent e) {
                    CPUTurn();
                }
            };
            Timer CPUTimer = new Timer(2500, CPUTurnWithDelay);//timer adds a delay between recursive calls
            CPUTimer.setRepeats(false);
            CPUTimer.start();

        }
    }

    /**
     * adds score (word length) to the corresponding player, and updates board text
     * @param input - word to find length from
     * @param player - player to add score to
     */
    private void addScore(String input, int player) {  // add score to users
        int score = input.length();  // add score based on the length of the words
        if (player == 1) {   // if it is player 1
            player1Score += score;  // add score
            playerOneLabel.setText(player1Score + " <- P1 Score");  // display it on GUI
        } else if (player == 2) { // if it is player 2
            player2Score += score;// add score
            playerTwoLabel.setText("P2 Score -> " + player2Score);// display it on GUI
        }

    }

    /**
     * 50/50 chance of who goes first in multiplayer mode
     * @return
     */
    private int firstTurnDecider() {  //deciding who goes first
        int randNumber = (int) (Math.random() * 2 + 1);
        return randNumber;

    }

    /**
     * checks to see if a players score is greater than the max score
     */
    private void winCheck() {// check if winning conditions have met
        timer.stop();
        int userChoice = 2;
        if (player1Score >= maxScore || player2Score >= maxScore) { // if max score has reached
            CPUEnabled = false;
            gameEnd = true;
        }
        if (player1Score >= maxScore) { // if player 1 wins
            userChoice = JOptionPane.showConfirmDialog(null, "Player 1 Wins!\nWould you like to restart?", "Announcement", JOptionPane.YES_NO_OPTION);
        } else if (player2Score >= maxScore) {  // if player2 wins

            if (CPUEnabled) {  // check if it is single player mode
                userChoice = JOptionPane.showConfirmDialog(null, "Looks like the CPU beat you :/\nWould you like to restart?", "Announcement", JOptionPane.YES_NO_OPTION);
            } else {
                userChoice = JOptionPane.showConfirmDialog(null, "Player 2 wins!\nWould you like to restart?", "Announcement", JOptionPane.YES_NO_OPTION);
            }
        }
        if (userChoice == JOptionPane.YES_OPTION) {

            gamePanel.setVisible(false);
            menuPanel.setVisible(true);

        } else if (userChoice == JOptionPane.NO_OPTION) {
            System.exit(1);
        }


    }




}

