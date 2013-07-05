function  Player(startEnergy)
{
    this.energy = startEnergy;
    this.energyGenerationSpeed = 0.1;

    this.bases = [];

    this.addBase = function(base) {
        this.bases.push(base);
    }
    
    this.update = function(dt) {
        // generate energy
        this.energy += this.energyGenerationSpeed;
    }
}