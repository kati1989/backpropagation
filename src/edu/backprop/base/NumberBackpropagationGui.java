package edu.backprop.base;

import java.io.File;

import java.awt.event.*;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import edu.backprop.network.MathUtil;
import edu.backprop.network.Pattern;
import edu.backprop.network.PatternGroup;


public class NumberBackpropagationGui extends JFrame {
 
	private static final long serialVersionUID = 7143340791046661355L;

    private final NumberPatternResultPanel numberResultPanel;
    private final PatternModifyPanel patternEditPanel;
    private final GridBagConstraints gridBag;

	public NumberBackpropagationGui(String frame_title, final NumberBackpropagation nbp, final PatternGroup training) {
		super(frame_title);

		gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.CENTER;
		gridBag.weightx = 100;
		gridBag.weighty = 100;

		getContentPane().setLayout(new GridBagLayout());

		patternEditPanel = new PatternModifyPanel(nbp, training);
		PatternSelectPanel patternSelector = new PatternSelectPanel(patternEditPanel);
		numberResultPanel = new NumberPatternResultPanel();
		JButton calculateResult = new JButton("Minta kiertekelese");

		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		gridBag.gridheight = 1;
		getContentPane().add(patternSelector, gridBag);

		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		gridBag.gridheight = 1;
		getContentPane().add(patternEditPanel, gridBag);

		gridBag.gridx = 2;
		gridBag.gridy = 0;
		gridBag.gridwidth = 1;
		gridBag.gridheight = 1;
		getContentPane().add(numberResultPanel, gridBag);

		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.gridwidth = 3;
		gridBag.gridheight = 1;
		gridBag.weighty = 20;
		getContentPane().add(calculateResult, gridBag);

		calculateResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				numberResultPanel.setResult(nbp.runNetwork(patternEditPanel.getPattern()));
			}
		});
	}


	public static void main(String args[]) throws Exception {
	
		NumberBackpropagation numberBack = null;
		PatternGroup training = null;

		training = new PatternGroup();
		training.readPatternGroupFromFile(new File(NumberBackpropagation.LEARN_FILE));

		if (args.length == 0) {
			numberBack = new NumberBackpropagation(new File(NumberBackpropagation.NETWORK_FILE));
		} else {
			numberBack = new NumberBackpropagation(new File(args[0]));
		}

		NumberBackpropagationGui gui = new NumberBackpropagationGui("Szam Felismeres", numberBack, training);

		gui.setBackground(java.awt.Color.white);
		gui.setSize(500, 350);
		gui.setVisible(true);

	}   
	
}

/**
 * Szam kivalasztasa megjelenites celjabol.
 */
class PatternSelectPanel extends JPanel {

	private static final long serialVersionUID = -5698181693665181035L;
	
	public PatternSelectPanel(final PatternModifyPanel patternModifyPanel) {
		setLayout(new GridLayout(5, 2));

		JButton numbers[] = new JButton[NumberBackpropagation.PATTERN_COUNT];
		for (int i = 0; i < NumberBackpropagation.PATTERN_COUNT; i++) {
			numbers[i] = new JButton(Integer.toString(i));
			add(numbers[i]);

			numbers[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					patternModifyPanel.setPattern(Integer.parseInt(event.getActionCommand()));
				}
			});
		}

		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		this.setBorder(new TitledBorder(border, "Minta"));
	}
	
}

/**
 *	Megjeleniti a Backpropagation eredmenyet
 */
class NumberPatternResultPanel extends JPanel {
	
	private static final long serialVersionUID = -4356291377159597583L;

	private JButton[] results;

	public NumberPatternResultPanel() {
		setLayout(new GridLayout(5, 2));
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		this.setBorder(new TitledBorder(border, "Eredmeny"));

		results = new JButton[NumberBackpropagation.PATTERN_COUNT];
		for (int i = 0; i < NumberBackpropagation.PATTERN_COUNT; i++) {
			results[i] = new JButton(Integer.toString(i));
			add(results[i]);
		}

		double temp[] = new double[NumberBackpropagation.PATTERN_COUNT];
		setResult(temp);
	}


	public void setResult(double[] answers) {
		for (int i = 0; i < answers.length; i++) {
			if (answers[i] > 0.75) {
				results[i].setBackground(java.awt.Color.green);
				results[i].setForeground(java.awt.Color.black);
			} else {
				results[i].setBackground(java.awt.Color.white);
				results[i].setForeground(java.awt.Color.black);
			}
		}
	}	
	
}

/**
 * Szerkesteni lehet a betanitott mintat.
 *
 */
class PatternModifyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private PatternGroup patterns;

	private JButton[] patternComponents;
	private double[] pattern;

	public PatternModifyPanel(NumberBackpropagation numberPropagation, PatternGroup patternGroup) {
		BevelBorder bevel_border = new BevelBorder(BevelBorder.RAISED);
		this.setBorder(new TitledBorder(bevel_border, "Minta szerkesztes"));
		
		this.patterns = patternGroup;

		pattern = new double[NumberBackpropagation.PATTERN_HEIGHT*NumberBackpropagation.PATTERN_WIDTH];

		setLayout(new GridLayout(NumberBackpropagation.PATTERN_HEIGHT,NumberBackpropagation.PATTERN_WIDTH));

		patternComponents = new JButton[NumberBackpropagation.PATTERN_HEIGHT*NumberBackpropagation.PATTERN_WIDTH];
		for (int i = 0; i < NumberBackpropagation.PATTERN_HEIGHT*NumberBackpropagation.PATTERN_WIDTH; i++) {
			pattern[i] = 0.0;
			patternComponents[i] = new JButton(Integer.toString(i));
			patternComponents[i].setBackground(java.awt.Color.white);
			add(patternComponents[i]);

			patternComponents[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					int index = Integer.parseInt(event.getActionCommand());

					if (pattern[index] < 0.5) {
						pattern[index] = MathUtil.ONE;
						patternComponents[index].setBackground(java.awt.Color.red);
					} else {
						pattern[index] = MathUtil.ZERO;
						patternComponents[index].setBackground(java.awt.Color.white);
					}
				}
			});
		}


	}

	/**
	 * Jelenitse meg a kijelolt mintat mely tanitasra volt hasznalva
	 * 
	 */
	public void setPattern(int index) {

		Pattern selected = patterns.get(index);

		double[] original = selected.getInput();

		for (int i = 0; i < original.length; i++) {
			pattern[i] = original[i];

			if (original[i] < 0.5) {
				patternComponents[i].setBackground(java.awt.Color.white);
			} else {
				patternComponents[i].setBackground(java.awt.Color.red);
			}
		}
	}

    public double[] getPattern() {
    	return(pattern);
    }	
    


}
