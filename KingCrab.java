import greenfoot.*;  // (World, Actor, GreenfootImage, GreenfootSound, and Greenfoot)

/**
 * KingCrab is the boss of the beach. It is bigger, stronger, and needs multiple hits to defeat.
 * It patrols a specific area and poses a real threat to the player.
 */
public class KingCrab extends Actor
{
    private GreenfootImage image1;
    private GreenfootImage image2;
    private GreenfootImage imageMad;
    private int health;
    private boolean isMad;
    private int cooldown;

    public KingCrab()
    {
        image1 = new GreenfootImage("king_crab.png");
        image2 = new GreenfootImage("king_crab2.png");
        imageMad = new GreenfootImage("king_crab_mad.png");
        
        image1.scale(120, 120);
        image2.scale(120, 120);
        imageMad.scale(120, 120);

        setImage(image1);
        health = 3;  // Needs 3 hits to be defeated
        isMad = false;
        cooldown = 0;
    }

    public void act()
    {
        patrol();
        if (!isMad) {
            switchImage();
        } else {
            cooldown--;
            if (cooldown <= 0) {
                isMad = false;
            }
        }
    }

    /**
     * Movement logic for KingCrab:
     * - Moves in random directions
     * - Occasionally changes direction
     */
    private void patrol()
    {
        move(2);

        // Randomly turn at edges
        if (isAtEdge()) {
            turn(Greenfoot.getRandomNumber(180) - 90);
        }

        // Random movement 
        if (Greenfoot.getRandomNumber(100) < 5) {
            turn(Greenfoot.getRandomNumber(45) - 22); // Small random turn
        }
    }

    /**
     * Animation switch for KingCrab
     */
    private void switchImage()
    {
        if (getImage() == image1) setImage(image2);
        else setImage(image1);
    }

    /**
     * Public method to damage KingCrab.
     * When defeated, it displays "You Win!" and ends the game.
     */
    public void takeDamage()
    {
        health--;
        setImage(imageMad); // Show mad image
        isMad = true;
        cooldown = 30; // Stays mad for a little
        
        if (health <= 0)
        {
            Greenfoot.playSound("king_crab_defeated.wav");

            // Display the "You Win!" message
            CrabWorld world = (CrabWorld) getWorld();
            world.showText("YOU WIN!", world.getWidth() / 2, world.getHeight() / 2);

            // Remove the KingCrab
            getWorld().removeObject(this);

            // Stop the game
            Greenfoot.stop();
        }
    }
}
