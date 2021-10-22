package gameComponents;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    
    	private Player gamePlayer;
    	private JLabel name;
    	private JLabel score;

    	public ScorePanel (Player gamePlayer) {
        	super();
        	this.setLayout(null); 										//uses null layout
        	this.gamePlayer = gamePlayer;

        	name = new JLabel(gamePlayer.getPlayerAlliance().toString());                  			//Displays player alliance
        	name.setFont(new Font ("TimesRoman", Font.BOLD, 50));
        	name.setBounds(20,20,250,70);
        	this.add(name);

        	JLabel scoreTag = new JLabel("Score: ");                                  			//Label for score tag
        	scoreTag.setFont(new Font ("TimesRoman", Font.BOLD, 20));
        	scoreTag.setBounds(450,20,250,70);
        	this.add(scoreTag);

        	score = new JLabel("0");                                                 			//Displays player score
        	score.setFont(new Font ("TimesRoman", Font.BOLD, 50));
       		score.setBounds(500,20,300,70);
        	this.add(score);

        	//sets panel features
        	this.setSize(500,100);
        	this.setBackground(new Color(238, 238, 210));
        	this.setVisible(true);
    	}

    	public void setCheck() {
        	name.setForeground(new Color(255, 131, 117));
    	}

    	public void setTurn() {
        	if(gamePlayer.isTurn())
            		name.setForeground(new Color(85, 238, 116));
        	else
            		clearText();
    	}

    	public void clearText() {
        	name.setForeground(Color.BLACK);
    	}

    	public void setScore () {
        	this.score.setText(Integer.toString(gamePlayer.getScore()));
    	}

    	public static void main(String[] args) {
        	JFrame testFrame = new JFrame("Test");

        	ScorePanel sp = new ScorePanel(new Player(Alliance.WHITE, true));
        	testFrame.add(sp);

        	testFrame.setSize(500, 100);
        	testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	testFrame.setVisible(true);
    	}
}