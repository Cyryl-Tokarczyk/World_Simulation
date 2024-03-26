from abc import abstractmethod
from random import random
from organism import Organism


class Plant(Organism):

    SPREAD_CHANCE = 0.2

    # def __init__(self, world):
    #     super().__init__(world)

    # def __init__(self, world, x, y):
    #     super().__init__(world, x, y)

    # def __init__(self, world, strength, initiative, x, y, age):
    #     super().__init__(world, strength, initiative, x, y, age)

    @abstractmethod
    def takeAction(self):
        pass

    def collision(self, attacker):
        if attacker.getStrength() > self._strength:
            attacker.changePosition( self.x, self.y )
            # log attack
            self._world.deleteOrganism(self)

    def spread( self, organism ):
        if random() <= self.SPREAD_CHANCE:
            newPos = self.getRandomMovePosition(self.x, self.y)
            organism.setXAndY(newPos[0], newPos[1])
            self._world.addOrganism(organism)
            
