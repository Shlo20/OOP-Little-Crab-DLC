import greenfoot.*;  

/**
 * The CrabWorld is the place where crabs and other creatures live. 
 * It creates the initial population.
 */
public class CrabWorld extends World
{
    private Crab crab;
    private boolean bossFightStarted = false;

    public CrabWorld() 
    {
        super(560, 560, 1);
        prepare();
    }

    private void prepare()
    {
        crab = new Crab();
        addObject(crab, 231, 203);

        // Adding Worms
        for (int i = 0; i < 10; i++) {
            Worm worm = new Worm();
            addObject(worm, Greenfoot.getRandomNumber(560), Greenfoot.getRandomNumber(560));
        }

        // Add Lobsters
        for (int i = 0; i < 3; i++) {
            Lobster lobster = new Lobster();
            addObject(lobster, Greenfoot.getRandomNumber(560), Greenfoot.getRandomNumber(560));
        }
    }

    // Method to spawn KingCrab and Lobsters
    public void spawnKingCrab()
    {
        if (!bossFightStarted) {
            bossFightStarted = true;

            // Clear Worm 
            removeObjects(getObjects(Worm.class));
            
            // Spawn KingCrab
            KingCrab kingCrab = new KingCrab();
            addObject(kingCrab, 280, 280);
            Greenfoot.playSound("boss_spawn.wav");

            // Reposition the Crab safely
            crab.setLocation(100, 100);

            // Spawn Lobsters during the fight 
            for (int i = 0; i < 1; i++) {
                Lobster lobster = new Lobster();
                addObject(lobster, Greenfoot.getRandomNumber(560), Greenfoot.getRandomNumber(560));
            }
        }
    }
}
