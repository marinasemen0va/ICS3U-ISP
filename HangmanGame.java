
import hsa.*;

import javax.swing.JOptionPane;
import java.awt.*;
import java.io.*;

public class HangmanGame {
    // instance variables
    Console c;
    String choice = "";
    String level = "";
    String name = "";
    String[] wordBank; 
    int wordNumber = 0;
    String[] hints;
    String[] scores;
    String[] names;
    String[] levels;
    final String HIGH_SCORE_FILE = "high scores.txt";
    int x; 
    int highScoreNumber = 0; 
    Color background = new Color(147, 253, 253);

    /*
	description: title method
    */
    private void title() {
	c.clear();
	c.print(' ', 34);
	c.setColor(background);
	c.fillRect(0, 0, c.maxx(), c.maxy());
	c.setTextBackgroundColor(background);
	c.println("Hangman Game");
	c.println();
    }


    /*
	description: pauses the program until input
    */
    private void pauseProgram() {
	c.println();
	c.println("Please press any key to continue");
	c.getChar();
    }


    /*
	description: intro screen method
    */
    public void splashScreen() {

	title();
	c.println ("This is a fun program of the game hangman! The theme is computer science things but you can change " +
		"that by importing your own word bank!");
	c.println ();
	c.println ("What is your name?"); // TODO: USER INPUT ALLOWED?
	name = c.readLine ();
	Color fade;
	for (x = 0; x < 255; x++) // fade
	{
	    //draw
	    fade = new Color (255 - x, 255 - x, 255);
	    c.setColor(fade);
	    c.fillOval(-65 + x, 150, 60, 60); // head
	    c.drawLine(-35 + x, 210, -35 + x, 310); // body
	    c.drawLine(-35 + x, 310, -75 + x, 360); // left leg
	    c.drawLine(-35 + x, 310, 5 + x, 360); // right leg
	    c.drawLine(-35 + x, 230, -55 + x, 300); // left arm
	    c.drawLine(-35 + x, 230, -5 + x, 260); // right arm
	    c.drawLine(15 + x, 220, -5 + x, 260); // right arm
	    // delay
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (Exception e)
	    {
	    }

	}
	for (x = 0; x < 255; x++) // erase fade
	{
	    c.setColor (new Color (147, 253, 253));
	    c.fillOval(-65 + x, 150, 60, 60); // head
	    c.drawLine(-35 + x, 210, -35 + x, 310); // body
	    c.drawLine(-35 + x, 310, -75 + x, 360); // left leg
	    c.drawLine(-35 + x, 310, 5 + x, 360); // right leg
	    c.drawLine(-35 + x, 230, -55 + x, 300); // left arm
	    c.drawLine(-35 + x, 230, -5 + x, 260); // right arm
	    c.drawLine(15 + x, 220, -5 + x, 260); // right arm
	    // delay
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (Exception e)
	    {
	    }
	}
	// draw hangman
	c.setColor(Color.BLACK);
	c.drawOval(189, 150, 60, 60); // head
	c.fillOval(209, 170, 10, 10); // left eye
	c.fillOval(229, 170, 10, 10); // right eye
	c.drawArc(209, 170, 30, 30, 180, 180); // mouth
	c.drawLine (209, 185, 239, 185);
	c.drawLine(219, 210, 219, 310); // body
	c.drawLine(219, 310, 179, 360); // left leg
	c.drawLine(219, 310, 259, 360); // right leg
	c.drawLine(219, 230, 199, 300); // left arm
	c.drawLine(219, 230, 249, 260); // right arm
	c.drawLine(269, 220, 249, 260); // right arm
	int[] xPoly = {214, 214, 224, 224};
	int[] yPoly = {225, 220, 225, 220};
	c.setColor (Color.RED);
	c.fillPolygon(xPoly, yPoly, 4);
	for (x = 0; x < 200; x++) // computer
	{
	    // erase
	    c.setColor (background);
	    c.fillRect (601- x , 200, 150, 150);
	    // draw
	    c.setColor(Color.BLACK);
	    c.fillRoundRect(600 - x, 200, 150, 100, 20, 20);
	    c.setColor(new Color(255, 225, 175));
	    c.fillRoundRect(610 - x, 210, 130, 80, 20, 20);
	    c.setFont(new Font("times new roman", Font.BOLD, 12));
	    c.setColor(Color.BLACK);
	    c.drawString("computer science", 630 - x, 245);
	    c.setColor(Color.BLACK);
	    c.drawString("is super cool :)", 630 - x, 265);
	    c.setColor(Color.BLACK);
	    c.fillRect(670 - x, 300, 10, 30);
	    c.setColor(Color.BLACK);
	    c.fillRect(655 - x, 325, 40, 5);
	    // delay
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (Exception e)
	    {
	    }

	}
	for (x = 0; x < 30; x++)
	{
	    // erase
	    c.setColor(background);
	    c.drawLine(268 + x, 219 + x, 249, 260); // right arm
	    // draw
	    c.setColor(Color.BLACK);
	    c.drawLine(269 + x, 220 + x, 249, 260); // right arm
	    // delay
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (Exception e)
	    {
	    }
	}
	try
	{
	    Thread.sleep (500);
	}
	catch (Exception e)
	{
	}
	final String defaultWords = "words file.txt"; // default words file name
	final String defaultHints = "hints file.txt"; // default hints file name
	// IMPORT DEFAULT WORDS
	String line = "";
	File inputFile = new File(defaultWords);
	if (!inputFile.exists()) { // checks for file existence and outputs message & exits program if not
	    JOptionPane.showMessageDialog(null, "ERROR - File does not exist [make sure it is in the same folder as the project]", "Error", JOptionPane.ERROR_MESSAGE);
	    System.exit(-1);
	}
	try // open once for size
	{
	    BufferedReader reader = new BufferedReader(new FileReader(defaultWords));
	    while (line != null) { // loop until end of file
		line = reader.readLine();  // read each line
		wordNumber++;  // add to count variable
	    }
	    reader.close();  // close file
	} catch (IOException e) { // catch any file related errors
	    System.out.println(e);  // print error
	}

	wordBank = new String[wordNumber - 1];
	wordNumber -= 1;
	try {
	    BufferedReader r = new BufferedReader(new FileReader(defaultWords)); // reset the buffer
	    for (x = 0; x < wordNumber; x++) { //loop until end of file
		wordBank[x] = r.readLine(); // read and store in array
	    }
	    r.close(); //close data file
	} catch (IOException e) { //handle file related errors
	    System.out.println(e);
	}
	// IMPORT DEFAULT HINTS
	int numberOfLines = 0; // stores amount of lines
	inputFile = new File(defaultHints);
	line = "";
	if (!inputFile.exists()) { // checks for file existence and outputs message & exits program if not
	    JOptionPane.showMessageDialog(null, "ERROR - File does not exist [make sure it is in the same folder as the project]", "Error", JOptionPane.ERROR_MESSAGE);
	    System.exit(-1);
	}
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(defaultHints));
	    while (line != null) { // loop until end of file
		line = reader.readLine();  // read each line
		numberOfLines++;  // add to count variable
	    }
	    reader.close();  // close file
	} catch (IOException e) { // catch any file related errors
	    System.out.println(e);  // print error
	}
	hints = new String[numberOfLines - 1];
	numberOfLines -= 1;
	try {
	    BufferedReader r = new BufferedReader(new FileReader(defaultHints)); // reset the buffer
	    for (x = 0; x < numberOfLines; x++) { //loop until end of file
		hints[x] = r.readLine(); // read and store in array
	    }
	    r.close(); //close data file
	} catch (IOException e) { //handle file related errors
	    System.out.println(e);
	}
	// IMPORT HIGH SCORES IF FILE NOT EMPTY
	highScoreNumber = 0;
	line = "";
	inputFile = new File(HIGH_SCORE_FILE);
	if (!inputFile.exists()) { // checks for file existence and outputs message & exits program if not
	    JOptionPane.showMessageDialog(null, "ERROR - File does not exist [make sure it is in the same folder as the project]", "Error", JOptionPane.ERROR_MESSAGE);
	    System.exit(-1);
	}
	try // open once for size
	{
	    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	    while (line != null) { // loop until end of file
		line = reader.readLine();  // read each line
		highScoreNumber++;  // add to count variable
	    }
	    reader.close();  // close file
	} catch (IOException e) { // catch any file related errors
	    System.out.println(e);  // print error
	}
	if (highScoreNumber > 1) {
	    names = new String[(highScoreNumber - 1) / 3];
	    scores = new String[(highScoreNumber - 1) / 3];
	    levels = new String[(highScoreNumber - 1) / 3];
	    highScoreNumber = (highScoreNumber - 1) / 3;
	    try {
		BufferedReader r = new BufferedReader(new FileReader(defaultWords)); // reset the buffer
		for (x = 0; x < highScoreNumber; x++) { //loop until end of file
		    names[x] = r.readLine(); // read and store in array
		    scores[x] = r.readLine(); // read and store in array
		    levels[x] = r.readLine(); // read and store in array
		}
		r.close(); //close data file
	    } catch (IOException e) { //handle file related errors
		System.out.println(e);
	    }
	}
    }


    /*
	description: menu method
    */
    public void mainMenu() {
	choice = ""; // reset
	title();
	// DISPLAY MENU
	c.println("                         1)  instructions");
	c.println("                         2)  level select");
	c.println("                         3)  high scores");
	c.println("                         4)  abandon theme - import files");
	c.println("                         5)  exit");
	c.println();
	c.setFont(new Font("times new roman", Font.BOLD, 12));
	c.println("Please make a choice:");
	// INPUT + ERROR TRAP
	while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) // if not a correct choice enter loop
	{
	    c.setCursor(10, 1); // display purposes
	    choice = c.readLine(); // input
	    if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) // if not a correct choice show error message
	    {
		JOptionPane.showMessageDialog(null, "Please enter one of the numbers!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	c.setCursor(10, 1); // display purposes
	c.println(choice);
    }


    /*
	description: high scores method
    */
    public void highScores() {
	title();
	if (names == null) {
	    c.println("There are no high scores yet!");
	} else {
	    c.print("    name");
	    c.print(' ', 29);
	    c.print("score");
	    c.print(' ', 28);
	    c.println("level   ");
	    for (x = 0; x < names.length; x++) {
		c.print("    " + names[x]);
		c.print(' ', 35 - names[x].length());
		c.print(scores[x]);
		c.print(' ', 76 - (35 + names[x].length() + scores[x].length()));
		c.println(levels[x]);
	    }
	}
	pauseProgram();
    }


    /*
	description: imprt files method
    */
    public void importFiles() {
	title();
	String wordsFile = "";
	String hintsFile = "";
	wordNumber = 0;
	File inputWords = new File(wordsFile);
	File inputHints = new File(hintsFile);
	c.println("Please enter the name of the word bank file or enter \"words file\":");
	// ERROR TRAP WORDS FILE
	while (!inputWords.exists() || wordNumber < 101) // checks if file exists
	{
	    wordNumber = 0;
	    c.setCursor(4, 1);
	    wordsFile = c.readLine();
	    wordsFile += ".txt";
	    inputWords = new File(wordsFile);
	    if (!inputWords.exists()) {
		JOptionPane.showMessageDialog(null, "ERROR - File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
		String line = "";
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(wordsFile));
		    while (line != null) { // loop until end of file
			line = reader.readLine(); // read each line
			wordNumber++;  // add to count variable
		    }
		    reader.close(); // close file
		} catch (IOException e) { // catch any file related errors
		    System.out.println(e); // print error
		}
		if (wordNumber < 101) {
		    JOptionPane.showMessageDialog(null, "There must be at least 100 words!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	    c.setCursor(4, 1); // won't scroll screen
	    c.println(wordsFile); // if correct input leave on screen
	}
	c.println();
	// ERROR TRAP HINTS FILE
	int numberOfLines = 0;
	c.println("Please enter the name of the hints file or enter \"hints file\":");
	while (!inputHints.exists() || numberOfLines != wordNumber) // checks if file exists
	{
	    numberOfLines = 0;
	    c.setCursor(7, 1);
	    hintsFile = c.readLine();
	    hintsFile += ".txt";
	    inputHints = new File(hintsFile);
	    if (!inputHints.exists()) {
		JOptionPane.showMessageDialog(null, "ERROR - File does not exist", "Error", JOptionPane.ERROR_MESSAGE);
	    } else {
		String line = "";
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(hintsFile));
		    while (line != null) { // loop until end of file
			line = reader.readLine(); // read each line
			numberOfLines++;  // add to count variable
		    }
		    reader.close(); // close file
		} catch (IOException e) { // catch any file related errors
		    System.out.println(e); // print error
		}
		if (numberOfLines != wordNumber) {
		    JOptionPane.showMessageDialog(null, "There must be " + (wordNumber - 1) + " hints!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	    c.setCursor(7, 1); // won't scroll screen
	    c.println(hintsFile); // if correct input leave on screen
	}
	choice = "";
	// IMPORT WORDS FILE
	wordBank = new String[wordNumber - 1];
	wordNumber -= 1;
	try {
	    BufferedReader r = new BufferedReader(new FileReader(wordsFile)); // reset the buffer
	    for (x = 0; x < wordNumber; x++) { //loop until end of file
		wordBank[x] = r.readLine(); // read and store in array
	    }
	    r.close(); //close data file
	} catch (IOException e) { //handle file related errors
	    System.out.println(e);
	}
	// IMPORT HINTS FILE
	hints = new String[numberOfLines - 1];
	numberOfLines -= 1;
	try {
	    BufferedReader r = new BufferedReader(new FileReader(hintsFile)); // reset the buffer
	    for (x = 0; x < numberOfLines; x++) { //loop until end of file
		hints[x] = r.readLine(); // read and store in array
	    }
	    r.close(); //close data file
	} catch (IOException e) { //handle file related errors
	    System.out.println(e);
	}

    }


    /*
	description: instructions screen method
    */
    public void instructions() {
	title();
	c.println("This program is menu oriented and allows you to choose where you want to go! Youcan choose to view " +
		"instructions, import your own files, view high scores, play  game, or exit. Once you select game you must " +
		"also select a level - 1 comes with a hint and 2 comes with a cheat option. In the game you must guess letters " +
		"that might be in the chosen word. You start with a score of 100 and you are deducted 10 points per wrong guess. " +
		"Once you finish the game you will return to the main menu. Only one user per game. Have fun!\n");
	pauseProgram();
    }


    /*
	description: level select method
    */
    public void levelSelect() {
	title();
	level = "";
	c.println("                        1)  level 1 - hint");
	c.println("                        2)  level 2 - no hint");
	c.println();
	c.println("Please make a choice:");
	while (!level.equals("1") && !level.equals("2")) // if not a correct choice enter loop
	{
	    c.setCursor(7, 1); // display purposes
	    level = c.readLine(); // input
	    if (!level.equals("1") && !level.equals("2")) // if not a correct choice show error message
	    {
		JOptionPane.showMessageDialog(null, "Please enter one of the numbers!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	c.setCursor(7, 1); // display purposes
	c.println(level);
    }


    /*
	description: sort scores method
    */
    private int[] sortHighScores(int s, int hsn) {
	int[] sorted = new int[hsn];
	boolean notAdded = true;
	if (hsn == 10 || (hsn > 1 && Integer.parseInt(scores[scores.length - 1]) < s)) {
	    for (x = 0; x < hsn; x++) {
		if (s > Integer.parseInt(scores[x])) {
		    if (notAdded) // if greater than score for 1st time than add it as index 10
		    {
			sorted[x] = 10;
			notAdded = false;
		    } else   // all other cases add x - 1
		    {
			sorted[x] = x - 1;
		    }
		} else   // if not larger than just add
		{
		    sorted[x] = x;
		}
	    }
	} else {
	    for (x = 0; x < hsn - 1; x++) {
		sorted[x] = x;
	    }
	    sorted[hsn - 1] = 10;
	}
	return sorted;
    }


    /*
	description: check if guess is in the word
    */

    private int[] checkForLetter(String g, String w) {
	int amountOfIndexes = 0;
	for (x = 0; x < w.length(); x++) {
	    if (Character.toString(w.charAt(x)).equalsIgnoreCase(g)) {
		amountOfIndexes += 1;
	    }
	}
	int[] indexes = new int[amountOfIndexes];
	int index = 0;
	for (x = 0; x < w.length(); x++) {
	    if (Character.toString(w.charAt(x)).equalsIgnoreCase(g)) {
		indexes[index] = x;
		index += 1;
	    }
	}
	return indexes;
    }


    /*
	description: display method
    */
    public void display() {
	// SETUP
	String getHint = "";
	int randIndex = (int) (Math.random() * (wordNumber - 1)); // TODO: IS RANDOM WITHIN RANGE?
	int score = 100;
	String guess = "6";
	String word = wordBank[randIndex];
	String hint = hints[randIndex];
	String usedLetters = "";
	boolean containsLetter = false;
	boolean aLetter = false;
	boolean used = false;
	boolean hintGotten = false;
	int lettersGuessed = 0;
	int wrongGuesses = 0;
	final String alphabet = "abcdefghijklmnopqrstuvwxyz1";
	title();
	choice = "";
	wordNumber -= 1;
	c.setColor(Color.BLACK);
	c.drawRect(50, 50, 300, 310);
	c.setColor(new Color(145, 255, 158));
	c.fillRect(375, 50, 250, 200);
	c.setColor(Color.BLACK);
	c.setFont(new Font("arial", Font.BOLD, 30));
	c.drawString("a b c d e f g h i", 395, 110);
	c.drawString("j k l m n o p q r", 395, 160);
	c.drawString("s t u v w x y z", 395, 210);
	c.drawLine(70, 350, 110, 350);
	c.drawLine(90, 350, 90, 70);
	c.drawLine(90, 70, 200, 70);
	c.drawLine(200, 70, 200, 85);
	for (x = 0; x < word.length(); x++) {
	    c.drawLine(25 + (40 * x), 400, 55 + (40 * x), 400);
	}
	c.setCursor(22, 1);
	c.print("hint: ");
	if (level.equals("1")) {
	    c.println(hint);
	} else {
	    c.println();
	    getHint = " (or press 1 to get your hint)";
	}
	while (lettersGuessed < word.length() && wrongGuesses < 10) {
	    guess = "";
	    c.println();
	    containsLetter = false;
	    while (used || !aLetter || guess.length() != 1) {
		c.setCursor(23, 1);
		c.print("Please enter your guess" + getHint + ": ");
		guess = c.readLine();
		for (x = 0; x < alphabet.length(); x++) {
		    if (guess.equalsIgnoreCase(Character.toString(alphabet.charAt(x)))) {
			aLetter = true;
		    }
		}
		for (x = 0; x < usedLetters.length(); x++) {
		    if (guess.equalsIgnoreCase(Character.toString(usedLetters.charAt(x)))) {
			used = true;
			break;
		    }
		}
		if (guess.length() != 1) {
		    JOptionPane.showMessageDialog(null, "Please enter ONE character!", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (!aLetter) {
		    JOptionPane.showMessageDialog(null, "Please enter a letter!", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (used) {
		    JOptionPane.showMessageDialog(null, "Please enter a letter that isn't used!", "Error", JOptionPane.ERROR_MESSAGE);

		}
		if (guess.equals("1") && !hintGotten) {
		    getHint = "";
		    c.setCursor(22, 1);
		    c.println("hint: " + hint);
		    hintGotten = true;
		}
	    }
	    int i;
	    usedLetters += guess;
	    for (x = 0; x < word.length(); x++) {
		if (Character.toString(word.charAt(x)).equalsIgnoreCase(guess)) {
		    int[] indexWhereLetters = checkForLetter(guess, word);
		    c.setColor(Color.BLACK);
		    for (i = 0; i < indexWhereLetters.length; i++) {
			c.drawString(guess, 30 + (40 * indexWhereLetters[i]), 395);
		    }
		    lettersGuessed += indexWhereLetters.length;
		    containsLetter = true;
		    break;
		}
	    }
	    if (!containsLetter && !guess.equals("1")) {
		wrongGuesses += 1;
		score -= 10; // adjust score
		c.setColor(Color.BLACK);
		// DRAW PART OF HANGMAN
		switch (wrongGuesses) {
		    case 10:
			int[] xPoly = {190, 190, 210, 210};
			int[] yPoly = {157, 147, 157, 147};
			c.fillPolygon(xPoly, yPoly, 4);
			break;
		    case 9:
			c.drawLine(200, 180, 150, 150); // right hand
			break;
		    case 8:
			c.drawLine(200, 180, 250, 150); // left hand
			break;
		    case 7:
			c.drawLine(200, 180, 150, 150); // right hand
			break;
		    case 6:
			c.drawLine(200, 180, 250, 150); // left hand
			break;
		    case 5:
			c.drawLine(200, 145, 200, 250); // body
			break;
		    case 4:
			c.fillArc(185, 105, 40, 30, 180, 180); // mouth
			break;
		    case 3:
			c.fillOval(215, 105, 10, 10); // right eye
			break;
		    case 2:
			c.fillOval(185, 105, 10, 10); // left eye
			break;
		    default:
			c.drawOval(170, 85, 60, 60); // head
			break;
		}
	    }
	    // COVER LETTER
	    c.setColor(new Color(145, 255, 158));
	    if (guess.equalsIgnoreCase("a")) {
		c.fillRect(395, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("b")) {
		c.fillRect(420, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("c")) {
		c.fillRect(445, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("d")) {
		c.fillRect(470, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("e")) {
		c.fillRect(495, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("f")) {
		c.fillRect(520, 85, 15, 25);
	    } else if (guess.equalsIgnoreCase("g")) {
		c.fillRect(540, 85, 25, 40);
	    } else if (guess.equalsIgnoreCase("h")) {
		c.fillRect(565, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("i")) {
		c.fillRect(590, 85, 25, 25);
	    } else if (guess.equalsIgnoreCase("j")) {
		c.fillRect(390, 135, 20, 40);
	    } else if (guess.equalsIgnoreCase("k")) {
		c.fillRect(410, 135, 25, 25);
	    } else if (guess.equalsIgnoreCase("l")) {
		c.fillRect(430, 135, 25, 25);
	    } else if (guess.equalsIgnoreCase("m")) {
		c.fillRect(445, 135, 35, 25);
	    } else if (guess.equalsIgnoreCase("n")) {
		c.fillRect(510, 135, 25, 25);
	    } else if (guess.equalsIgnoreCase("o")) {
		c.fillRect(510, 135, 25, 25);
	    } else if (guess.equalsIgnoreCase("p")) {
		c.fillRect(535, 135, 25, 35);
	    } else if (guess.equalsIgnoreCase("q")) {
		c.fillRect(565, 135, 25, 35);
	    } else if (guess.equalsIgnoreCase("r")) {
		c.fillRect(590, 135, 25, 25);
	    } else if (guess.equalsIgnoreCase("s")) {
		c.fillRect(390, 185, 25, 25);
	    } else if (guess.equalsIgnoreCase("t")) {
		c.fillRect(415, 185, 20, 25);
	    } else if (guess.equalsIgnoreCase("u")) {
		c.fillRect(440, 185, 20, 25);
	    } else if (guess.equalsIgnoreCase("v")) {
		c.fillRect(465, 185, 20, 25);
	    } else if (guess.equalsIgnoreCase("w")) {
		c.fillRect(490, 185, 25, 25);
	    } else if (guess.equalsIgnoreCase("x")) {
		c.fillRect(515, 185, 25, 25);
	    } else if (guess.equalsIgnoreCase("y")) {
		c.fillRect(540, 185, 25, 35);
	    } else // z
	    {
		c.fillRect(590, 185, 25, 25);
	    }
	}
	c.setColor(Color.WHITE);
	c.drawRect(0, 0, c.maxx(), c.maxy());
	c.setColor(Color.BLACK);
	c.setFont(new Font("Times New Roman", Font.BOLD, 30));
	if (wrongGuesses < 10) {
	    c.drawString("YOU WON!", 200, 110);
	} else {
	    c.drawString("YOU LOST...", 200, 110);
	}
	c.setCursor(20, 1);
	c.println("score: " + score);
	pauseProgram();
	// REMOVE WORD
	String[] tempWordBank = new String[wordNumber];
	for (x = 0; x < wordNumber; x++) {
	    if (x < randIndex) {
		tempWordBank[x] = wordBank[x];
	    } else {
		tempWordBank[x] = wordBank[x + 1];
	    }
	}
	wordBank = new String[wordNumber];
	wordBank = tempWordBank;
	// SORT HIGH SCORES
	if (scores == null) {
	    scores = new String[1];
	    names = new String[1];
	    levels = new String[1];
	    scores[0] = Integer.toString(score);
	    names[0] = name;
	    levels[0] = level;
	} else if ((highScoreNumber == 10 && score > Integer.parseInt(scores[scores.length - 1])) || (highScoreNumber < 10)) // if its a high score then must alter array
	{
	    if (highScoreNumber < 10) // if not at 10 increase size to adjust for capacity
	    {
		highScoreNumber += 1;
	    }
	    int sortOrder[];
	    sortOrder = sortHighScores(score, highScoreNumber);
	    for (x = 0; x < sortOrder.length; x++) {
		if (sortOrder[x] != 10) {
		    scores[x] = scores[sortOrder[x]];
		    names[x] = names[sortOrder[x]];
		    levels[x] = levels[sortOrder[x]];
		} else {
		    scores[x] = Integer.toString(score);
		    names[x] = name;
		    levels[x] = level;
		}
	    }
	}
    }


    /*
	description: bye method
    */
    public void goodbye() {
	title();
	c.println("Thank you for using this program! It is an educational game made for the TDSB   that incorporated a theme involving computer science terms.");
	c.println();
	c.println("Made by Marina Semenova :)");
	pauseProgram();
	c.close();
    }




    public HangmanGame() {
	c = new Console(25, 85,"Hangman Game");
    }


    /*
	description: main
    */
    public static void main(String[] args) {
	HangmanGame d = new HangmanGame();
	d.splashScreen(); // show once
	while (true) // until quit is chosen
	{
	    while (!d.choice.equals("5") && !d.choice.equals("2")) // quit or level select
	    {
		d.mainMenu();
		if (d.choice.equals("1")) {
		    d.instructions();
		}
		if (d.choice.equals("3")) {
		    d.highScores();
		}
		if (d.choice.equals("4")) {
		    d.importFiles();
		}
	    }
	    if (d.choice.equals("5")) // quit
	    {
		break;
	    }
	    d.levelSelect();
	    d.display();
	}
	d.goodbye();
    }
}
