function  Base(health, unitSpawnPosition)
{
    this.health = health;
    this.healthMax = health;
    this.healthRegenerationSpeed = 0.1;

    this.unitSpawnPosition = unitSpawnPosition;
    
    this.update = function(dt) {
        // regenearate health
        if (this.health < this.healthMax) this.health += this.energyGenerationSpeed;
    }
}