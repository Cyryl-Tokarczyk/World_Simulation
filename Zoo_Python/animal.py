from abc import abstractmethod
from urllib.parse import MAX_CACHE_SIZE
from organism import Organism


class Animal(Organism):

    MAX_CHILD_POS_RAND_TRIES = 15

    @abstractmethod
    def _getChild():
        pass

    # def __init__(self, world):
    #     super().__init__(world)

    # def __init__(self, world, x, y):
    #     super().__init__(world, x, y)

    # def __init__(self, world, strength, initiative, x, y, age):
    #     super().__init__(world, strength, initiative, x, y, age)

    def takeAction(self):
        self.move()
        
    def collision(self, attacker):
        if type(self) == type(attacker) :
            self.reproduce()
            return

        if attacker.getStrength() > self._strength:
            attacker.changePosition( self.x, self.y )
            # log attack
            self._world.deleteOrganism(self)

    def reproduce(self):
        timer = 0
        childPos = self.getRandomMovePosition(self.x, self.y)

        while self._world.getOrganism(childPos[0], childPos[1]) != None:
            if timer > self.MAX_CHILD_POS_RAND_TRIES:
                return

            childPos[0] = self.x
            childPos[1] = self.y
            childPos = self.getRandomMovePosition(childPos[0], childPos[1])
            timer += 1
        
        self._world.addOrganism(type(self)(self._world, childPos[0], childPos[1]))

    def move(self):
        newPos = self.getRandomMovePosition(self.x, self.y)
        organism = self._world.getOrganism(newPos[0], newPos[1])

        if organism != None:
            organism.collision(self)
        else:
            self.changePosition(newPos[0], newPos[1])