import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.util.ArrayList;

public class RaceGUI extends JFrame {
    private JPanel panelSet;
    private JPanel racePanel;
    
    private JTextField lengthField;
    private int raceLength = 0;

    private JTextField nHorsesField;
    private int nHorses;
    
    private JLabel[] horseLabels;
    private Horse[] Horses = null;

    public JButton btnSetRace;
    public JButton btnStartRace;
    
    Color[] laneColors = {
        new Color(255, 128, 128),   // Red
        new Color(255, 191, 128),   // Orange
        new Color(255, 255, 128),   // Yellow
        new Color(191, 255, 128),   // Light Green
        new Color(128, 255, 128),   // Green
        new Color(128, 255, 191),   // Light Blue
        new Color(128, 255, 255),   // Cyan
        new Color(128, 191, 255),   // Light Purple
        new Color(128, 128, 255),   // Blue
        new Color(191, 128, 255)    // Purple
    };
    
    

    public static void main(String[] args) {
        RaceGUI raceGUI = new RaceGUI();
    }

    public RaceGUI() {
        initializeGUI();
        
        // initializeRace();
        //initializeRace();

        // Add action listener to the Start Race button
        btnSetRace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("EXECUTING SET RACE.");
                
                // check length input
                boolean isValid = false;

                isValid = checkLength();
                isValid = checkHorses();

                if (isValid)
                {        
                    setCustom(); // set attributes of race

                    setRacePanel(); // set lanes GUI
                }   
            }
        });

        // Add action listener to the Start Race button
        btnStartRace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.out.println("EXECUTING START RACE.");               

                if (Horses != null && raceLength > 0)
                {        

                    // Start the race logic on a separate thread
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            startRace();; // Call the method to handle the Start Race button click
                            return null;
                        }
                    }.execute();
                }
            }
        });   
        return;
    }

    // -------------------------------------------------- GUI --------------------------------------------------
    private void initializeGUI() {
        // Main Frame
        setTitle("Horse Race Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Set layout manager to BorderLayout
        //setResizable(false); // Disable resizing
        setSize(1200, 800);
        getContentPane().setBackground(new Color(129, 200, 213)); // Set background color
        setLocationRelativeTo(null); // Center the frame on screen

        // Initialize button panel and add it to the NORTH region
        panelSet = createSetPanel();
        add(panelSet, BorderLayout.NORTH);

        // Panel Lanes
        racePanel = createRacePanel();
        add(racePanel, BorderLayout.CENTER); // Add race panel to the center of the frame
        
        setVisible(true);
    }

    private JPanel createSetPanel() {
        // create button panel
        JPanel panelSet = new JPanel();

        // Set panel color to black
        panelSet.setBackground(new Color(129, 200, 213));   

        // Add empty border to create a gap space
        // public static Border createEmptyBorder(int top, int left, int bottom, int right)
        panelSet.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        // ------------------ LENGTH LABEL ------------------
        // Create label "Length"
        JLabel lengthLabel = new JLabel("Length:");
        lengthLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panelSet.add(lengthLabel);

        // Create text input field
        lengthField = new JTextField(5);
        lengthField.setFont(new Font("Arial", Font.PLAIN, 20));
        panelSet.add(lengthField);

        // ------------------ Number of Horses ------------------
       // Create label "Horses"
       JLabel nHorsesLabel = new JLabel("Number of Horses:");
       nHorsesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
       panelSet.add(nHorsesLabel);

       // Create text input field
       nHorsesField = new JTextField(5);
       nHorsesField.setFont(new Font("Arial", Font.PLAIN, 20));
       panelSet.add(nHorsesField);

        // Set button
        this.btnSetRace = createButton("Set Race Attributes");
        panelSet.add(btnSetRace); // Add "Set Race Attributes" button to the panel

        // Start button
        this.btnStartRace = createButton("Start Race");
        panelSet.add(btnStartRace); // Add "Start Race" button to the panel
        return panelSet;
    }

    private JButton createButton(String title) {
        // Button to start the simulation
        JButton btn = new JButton(title);

        // Button font
        btn.setFont(new Font("Arial", Font.PLAIN, 20));

        // Color of the button
        Color c = new Color(194, 228, 235);
        btn.setBackground(c);

        // This ensures that the button will be sized to fit its label
        btn.setSize(btn.getPreferredSize());

        return btn;
    }

    private JPanel createRacePanel() {
        // Create race panel with custom border
        JPanel racePanel = new JPanel();
        Color c = new Color(129, 200, 213);
        racePanel.setBorder(BorderFactory.createLineBorder(c, 20)); // Outer border

        return racePanel;
    }
    // -------------------------------------------------- END OF GUI --------------------------------------------------

    // Event handling inputs
    private boolean checkLength() 
    {
        System.out.println("Checking race length input...");
        // Get the length input from the text field
        String lengthInput = lengthField.getText();

        try 
        {
            // Parse the input to an integer
            raceLength = Integer.parseInt(lengthInput);

            if (raceLength <= 0) 
            {
                // Length must be a positive integer
                msgbox("Race length must be a positive integer.");
                return false;
            }

            return true; // Input is a valid integer

        } 
        catch (NumberFormatException e) 
        {
            // Handle non-integer input
            msgbox("Invalid input. Please enter an integer number.");
            return false;
        }
    }

    private boolean checkHorses() 
    {
        System.out.println("Checking number of horses...");
        // Get the length input from the text field
        String numberH = nHorsesField.getText();

        try 
        {
            // Parse the input to an integer
            nHorses = Integer.parseInt(numberH);

            if (nHorses <= 0) 
            {
                // Length must be a positive integer
                msgbox("Number of horses must be a positive integer.");
                return false;
            }

            return true; // Input is a valid integer

        } 
        catch (NumberFormatException e) 
        {
            // Handle non-integer input
            msgbox("Invalid input. Please enter an integer number.");
            return false;
        }
    }

    // alert messages
    private void msgbox(String message) {
        // Customize the message dialog's appearance
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 20));
        JOptionPane.showMessageDialog(this, message);
    }

    // ---------------------------- SET RACE CUSTOM ATRIBUTES ----------------------------
    private void setCustom()
    {
        // ----------------- HORSES -----------------
        this.Horses = new Horse[this.nHorses]; // array of horses
        this.horseLabels = new JLabel[this.nHorses + 1]; // array of lanes + 1 (result lane)

        for (int i = 0; i < this.nHorses; i++)
        {
            // Create horses with random confidence values
            this.Horses[i] = new Horse('\u265E', "Horse " + (i + 1), (Math.random() * 0.8 + 0.2));
            System.out.println("Creating: Horse " + (i + 1) + " confidence: " + this.Horses[i].getConfidence());
            // Math.random() generates a random double between 0.0 (inclusive) and 1.0 (exclusive).
            // Multiplying by 0.8 scales the range to [0.0, 0.8].
            // Adding 0.1 shifts the range to [0.1, 0.9].
        }

        return;
    }

    // ---------------------------- Set Lanes GUI ----------------------------
    private void setRacePanel()
    {
        System.out.println("-------------- Setting panel --------------");
        System.out.println("Horses: " + nHorses);

        // Remove existing components
        racePanel.removeAll();
        
        racePanel.setLayout(new GridLayout(nHorses + 1, 1));

        for (int i = 0; i < this.nHorses; i++)
        {
            String laneString = printLane(this.Horses[i]);
            horseLabels[i] = new JLabel(laneString); // Set lane label text
            horseLabels[i].setFont(new Font("Segoe UI Symbol", Font.BOLD, 20)); // Set font style
            horseLabels[i].setHorizontalAlignment(SwingConstants.LEFT); // Center alignment
            horseLabels[i].setOpaque(true); // Make label opaque to show background color
            
            // Use modulo operator to cycle back to the beginning of the array if index exceeds its length
            horseLabels[i].setBackground(laneColors[i % laneColors.length]);
            this.racePanel.add(horseLabels[i]);            
        }

        // Add a label for the finish line
        horseLabels[horseLabels.length - 1] = new JLabel("RESULT"); // Set lane label text
        horseLabels[horseLabels.length - 1].setFont(new Font("Segoe UI Symbol", Font.BOLD, 20)); // Set font style
        horseLabels[horseLabels.length - 1].setHorizontalAlignment(SwingConstants.CENTER);
        horseLabels[horseLabels.length - 1].setOpaque(true); // Make label opaque to show background color
        this.racePanel.add(horseLabels[horseLabels.length - 1]);

        // redo pannel
        racePanel.revalidate();
        racePanel.repaint();
        return;
    }

    // -------------------------------------------------- RACE --------------------------------------------------
    private void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
               
        //reset all the lanes (all horses not fallen and back to 0). 
        for (int i = 0; i < this.nHorses; i++)
        {
            this.Horses[i].goBackToStart();
            //System.out.println("Horse " + (i + 1) + " confidence: " + this.Horses[i].getConfidence() + " | " + this.Horses[i].getDistanceTravelled());
        }

        while (!finished)
        {
            //move each horse
            for (int i = 0; i < this.nHorses; i++)
            {
                moveHorse(this.Horses[i]);
                //System.out.println("Horse " + (i + 1) + " fallen: " + this.Horses[i].hasFallen() + " | " + this.Horses[i].getDistanceTravelled());
            }

            printRace();
            
            // check any won
            for (int i = 0; i < this.nHorses; i++)
            {
                if (raceWonBy(this.Horses[i]))
                {
                    finished = true;
                }
            }

            // check all fallen, therefore at least one not fallen to keep running
            boolean allFallen = true;
            for (int i = 0; i < this.nHorses; i++)
            {
                if (this.Horses[i].hasFallen() == false)
                {
                    allFallen = false;
                }
            }

            if (allFallen)
            {
                finished = true;
            }
            
            //wait for 1 second
            try{ 
                TimeUnit.MILLISECONDS.sleep(250);
                //TimeUnit.SECONDS.sleep(1);
            }catch(Exception e){}
        }
        
        String resultString = endRace();
        
        updateLaneGUIResult(resultString);

        return;
    }
    
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) // if confidence > random, then move
            {
                theHorse.moveForward();

                // increase confidence  
                increaseConfidence(theHorse);
            }
            else
            {
                // decrease confidence
                decreaseConfidence(theHorse);
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            
            // Probability of falling increases exponentially with confidence, capped at max 0.1
            double fallProbability = Math.min(0.1, 0.1 * theHorse.getConfidence() * theHorse.getConfidence());
            
            if (Math.random() < fallProbability) // if fall probability > random, then fall
            {
                theHorse.fall();                
            }
        }
    }
    // Increase the confidence of the horse 
    private void increaseConfidence(Horse theHorse)
    {
        double confidenceIncrease = 0.01;
        double maxConfidence = 1;
        
        // The maximum confidence is 1. Therefore first must check it, before change it.
        double newConfidence = theHorse.getConfidence() + confidenceIncrease;
        //System.out.println("Increase: " + theHorse.getConfidence() + " | " + newConfidence);
        if (newConfidence > maxConfidence)
        {
            theHorse.setConfidence(maxConfidence);
        }
        else
        {
            theHorse.setConfidence(newConfidence);
        }
        
        return;
    }

    // Decrease the confidence of the horse
    private void decreaseConfidence(Horse theHorse) {
        double confidenceDecrease = 0.01;
        double minConfidence = 0.1;
        
        // The maximum confidence is 1. Therefore first must check it before changing it.
        double newConfidence = theHorse.getConfidence() - confidenceDecrease;
        //System.out.println("Decrease: " + theHorse.getConfidence() + " | " + newConfidence);
        
        // Ensure the new confidence doesn't drop below the minimum confidence
        if (newConfidence < minConfidence) {
            theHorse.setConfidence(minConfidence);
        } 
        else 
        {
            theHorse.setConfidence(newConfidence);
        }
    }
    
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {   
        //System.out.print('\u000C');  //clear the terminal window
        
        //System.out.println();
        
        for (int i = 0; i < this.nHorses; i++)
        {
            horseLabels[i].setText(printLane(this.Horses[i]));

            racePanel.revalidate();
            racePanel.repaint();
            //System.out.println();
        }
        
        //System.out.println();    

        return;
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private String printLane(Horse theHorse)
    {
        String laneString = new String();
        
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        //System.out.print('|');
        laneString = laneString + "          |"; // spaces before 
        
        //print the spaces before the horse
        //multiplePrint(' ',spacesBefore);
        //multiplePrint('-',spacesBefore);
        laneString = multiplePrintConcat('-',spacesBefore, laneString);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            //System.out.print('\u2716');
            laneString = laneString + '\u2716';
        }
        else
        {
            //System.out.print(theHorse.getSymbol());
            laneString = laneString + theHorse.getSymbol();
        }
        
        //print the spaces after the horse
        //multiplePrint(' ',spacesAfter);
        //multiplePrint('-',spacesAfter);
        laneString = multiplePrintConcat('-',spacesAfter, laneString);
        
        //print the | for the end of the track
        //System.out.print('|');
        laneString = laneString + "|        "; // spaces after 
        
        // print horse details
        //System.out.print("        " + theHorse.getDistanceTravelled() + " | " + this.raceLength + "        ");
        laneString = laneString + "        " + theHorse.getDistanceTravelled() + " | " + this.raceLength + "        ";

        //printHorse(theHorse);
        laneString = printHorseConcat(theHorse, laneString);
        
        return laneString;
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        //int i = 1;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
        return;
    }

    private String multiplePrintConcat(char aChar, int times, String laneString)
    {
        int i = 0;
        //int i = 1;
        while (i < times)
        {
            laneString = laneString + aChar;
            i = i + 1;
        }
        return laneString;
    }
    
    private void printHorse(Horse theHorse)
    {
        System.out.print(theHorse.getName());
        System.out.print(" (Confidence: " + theHorse.getConfidence() + ")");
        return;
    }

    private String printHorseConcat(Horse theHorse, String laneString)
    {
        laneString = laneString + theHorse.getName();
        laneString = laneString + " (Confidence: " + theHorse.getConfidence() + ")";
        return laneString;
    }

    private void updateLaneGUIResult(String result)
    {
        horseLabels[horseLabels.length - 1].setText(result);

        // redo pannel
        racePanel.revalidate();
        racePanel.repaint();
        return;
    }

    private String endRace() {
        String finalLaneString = "";
    
        // Track the winners
        ArrayList<Horse> winners = new ArrayList<>();
    
        // Iterate over each horse
        for (Horse horse : Horses) {
            if (raceWonBy(horse) && !horse.hasFallen()) {
                winners.add(horse);
            }
        }
    
        int numWinners = winners.size();
    
        if (numWinners == 0) {
            System.out.println("All horses have fallen! There are no winners.");
            finalLaneString = "All horses have fallen! There are no winners.";
        } else if (numWinners == 1) {
            Horse winner = winners.get(0);
            System.out.println("And the winner is " + winner.getName());
            finalLaneString = "And the winner is " + winner.getName();
        } else {
            // Construct the string for multiple winners
            StringBuilder builder = new StringBuilder("It's a tie! The winners are ");
            for (int i = 0; i < numWinners - 1; i++) {
                builder.append(winners.get(i).getName()).append(", ");
            }
            builder.append("and ").append(winners.get(numWinners - 1).getName());
            System.out.println(builder.toString());
            finalLaneString = builder.toString();
        }
    
        return finalLaneString;
    }    

}
