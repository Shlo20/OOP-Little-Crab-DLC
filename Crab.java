import greenfoot.*;  

/**
 * This class defines a crab. Crabs live on the beach. They like sand worms 
 * (very yummy, especially the green ones).
 * 
 * Version: 5
 * 
 * In this version, the crab behaves as before, but we add animation of the 
 * image.
 */

public class Crab extends Actor
{
    private GreenfootImage image1;
    private GreenfootImage image2;
    private int wormsEaten;
    private boolean bossSpawned; // New flag to track if the boss has spawned
    private int attackCooldown = 0;

    /**
     * Create a crab and initialize its two images.
     */
    public Crab()
    {
        image1 = new GreenfootImage("crab.png");
        image2 = new GreenfootImage("crab2.png");
        setImage(image1);
        wormsEaten = 0;
        bossSpawned = false; // Boss not spawned at first
    }
        
    /** 
     * Act - do whatever the crab wants to do. This method is called whenever
     *  the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        checkKeypress();//edge();
        move(5);
        edgecorner();
        lookForWorm();
        switchImage();
        checkSpawnBoss(); // New method to check boss spawn

        //Spacebar attack
        if (Greenfoot.isKeyDown("space") && attackCooldown == 0) {
            attack();
            attackCooldown = 30; // Half a second hit cooldown
        }
        
        // Cooldown countdown
        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }
    
    // code to make crab quantum tunnel
    public void edgecorner() {
        int x = getX();
        int y = getY();
    
        // if statement that lets crab teleport opposite side horizontally
        if (x >= getWorld().getWidth() - 1) {
                setLocation(0,y);
            }
    
        // if statement that lets crab teleport to the opposite side if it reaches the left wall
        else if (x <= 0) {
            setLocation(getWorld().getWidth() - 1, y); 
        }
    
        // if statement that teleports crab to the top if it reaches the very bottom side of the map
        if (y >= getWorld().getHeight() - 1) {
                setLocation(x,0);
            }
    
        // if statement that teleports crab to the bottom if it reaches the very top of the map
        else if (y <= 0) 
            {
                setLocation(x, getWorld().getHeight() - 1);
            }
    
    }
        
    /**
     * Alternate the crab's image between image1 and image2.
     */
    public void switchImage()
    {
        if (getImage() == image1) 
        {
            setImage(image2);
        }
        else
        {
            setImage(image1);
        }
    }
            
    /**
     * Makes the crab move based with WASD.
     */
    public void checkKeypress()
    {
        if (Greenfoot.isKeyDown("a")) 
        {
            turn(-4);
        }
        if (Greenfoot.isKeyDown("d")) 
        {
            turn(4);
        }
        if (Greenfoot.isKeyDown("w")) 
        {
            move(5);
        }
        if (Greenfoot.isKeyDown("s")) 
        {
            move(-3);  // reverse
        }
    }
    
    /**
     * Check whether we have stumbled upon a worm.
     * If we have, eat it. If not, do nothing. If we have
     * eaten eight worms, we win.
     */
    public void lookForWorm()
    {
        if ( isTouching(Worm.class) ) 
        {
            removeTouching(Worm.class);
            Greenfoot.playSound("slurp.wav");
            
            wormsEaten = wormsEaten + 1;
            
            if (wormsEaten == 1 && !bossSpawned)  // Changed worm amount
            {
                Greenfoot.playSound("king_crab_defeated.wav");
            }
        }
    }

    /**
     * Check if all worms are collected and spawn KingCrab if not already spawned.
     */
    public void checkSpawnBoss()
    {
        if (wormsEaten == 1 && !bossSpawned) 
        {
            bossSpawned = true;
            CrabWorld world = (CrabWorld) getWorld();
            world.spawnKingCrab(); // Calls the method in CrabWorld to spawn the boss
        }
    }

    /**
     * Crab attack with spacebar slaps KingCrab.
     */
    public void attack()
    {
        KingCrab kingCrab = (KingCrab) getOneIntersectingObject(KingCrab.class);
        if (kingCrab != null)
        {
            Greenfoot.playSound("oof.mp3");
            kingCrab.takeDamage();  // Calls the damage method
        }
    }
}
