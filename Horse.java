/**
 * 
 * @author Mateus Franco e Cunha
 * @studentID 220530325
 * @version V2.0
 */
public class Horse
{
    //Fields of class Horse
    private String name; // horse name
    private char code; // horse unicode
    private int distance; // distance travelled by the horse
    private boolean fallen; // A boolean indicating whether or not the horse has fallen.
    private double confidence; // confidence rating of the horse, represented as a decimal number between 0 and 1
    
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.name = horseName;
        this.code = horseSymbol;
        this.confidence = Math.round(horseConfidence * 100.0) / 100.0;
        this.distance = 0;
        this.fallen = false;
    }
    
    //Other methods of class Horse
    // --------------------------------------------------
    // Get
    public String getName()
    {
        return this.name;
    }

    public char getSymbol()
    {
        return this.code;
    }

    public int getDistanceTravelled()
    {
        return this.distance;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public boolean hasFallen()
    {
        return this.fallen;
    }
    
    // --------------------------------------------------
    // Set
    public void goBackToStart()
    {
        this.distance = 0;
        return;
    }
    
    public void fall()
    {
        this.fallen = true;
        return;
    }
    
    public void moveForward()
    {
        this.distance = this.distance + 1;
        return;
    }

    public void setConfidence(double newConfidence)
    {
        // Format the newConfidence to two decimal places
        this.confidence = Math.round(newConfidence * 100.0) / 100.0;
        return;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.code = newSymbol;
        return;
    }
    
}
