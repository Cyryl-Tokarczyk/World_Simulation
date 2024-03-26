import math
from random import random
from animal import Animal
from plants import Sosnowsky


class Antelope(Animal):
    ANTELOPE_DODGE_CHANCE = 0.5

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 4
        self._initiative = 4

    def takeAction(self):
        newPos = self.getRandomMovePosition(self.x, self.y, 2)
        organism = self._world.getOrganism(newPos[0], newPos[1])

        if organism != None:
            organism.collision(self)
        else:
            self.changePosition(newPos[0], newPos[1])

    def collision(self, attacker):
        if type(self) == type(attacker):
            self.reproduce()
            return

        if random() <= self.ANTELOPE_DODGE_CHANCE:
            timer = 0
            newPos = self.getRandomMovePosition(self.x, self.y)
            organism = self._world.getOrganism(newPos[0], newPos[1])

            while organism != None:
                if timer > self.MAX_CHILD_POS_RAND_TRIES:
                    return

                newPos[0] = self.x
                newPos[1] = self.y
                newPos = self.getRandomMovePosition(newPos[0], newPos[1])
                organism = self._world.getOrganism(newPos[0], newPos[1])
                timer += 1

            if organism == None:
                self.changePosition(newPos[0], newPos[1])
                return

        super().collision(attacker)

    def getColor(self):
        return "#A0522D"


class CyberSheep(Animal):

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 11
        self._initiative = 4

    def lookForNearestSosnowsky(self):
        pos = [None] * 2
        currentMinDistance = 10000
        isThereAny = False

        for i in range(0, self._world.getY()):
            for j in range(0, self._world.getX()):
                if isinstance(self._world.getOrganism(j, i), Sosnowsky) and math.dist([self.x, self.y], [i, j]) < currentMinDistance:
                    pos[0] = j
                    pos[1] = i
                    isThereAny = True

        if not isThereAny:
            return False
        
        return pos

    def moveTo(self, target):
        newPos = [None] * 2
        xDiff = target[0] - self.x
        yDiff = target[1] - self.y

        if abs(xDiff) > abs(yDiff):
            if xDiff < 0:
                newPos[0] = self.x - 1
            else:
                newPos[0] = self.x + 1
            newPos[1] = self.y
        else:
            if yDiff < 0:
                newPos[1] = self.y - 1
            else:
                newPos[1] = self.y + 1
            newPos[0] = self.x

        organism = self._world.getOrganism(newPos[0], newPos[1])

        if organism != None:
            organism.collision(self)
        else:
            self.changePosition(newPos[0], newPos[1])

    def takeAction(self):
        target = self.lookForNearestSosnowsky()
        if target == False:
            super().takeAction()
        else:
            self.moveTo(target)

    def getColor(self):
        return "#D8C8C8"


class Fox(Animal):

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 3
        self._initiative = 7

    def takeAction(self):
        timer = 0
        newPos = self.getRandomMovePosition(self.x, self.y)
        organism = self._world.getOrganism(newPos[0], newPos[1])

        while organism != None and organism.getStrength() > self._strength:
            if timer > self.MAX_CHILD_POS_RAND_TRIES:
                return

            newPos[0] = self.x
            newPos[1] = self.y
            newPos = self.getRandomMovePosition(newPos[0], newPos[1])
            organism = self._world.getOrganism(newPos[0], newPos[1])
            timer += 1

        if organism != None:
            organism.collision(self)
        else:
            self.changePosition(newPos[0], newPos[1])

    def getColor(self):
        return "#FF8413"


class Sheep(Animal):

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 4
        self._initiative = 4

    def getColor(self):
        return "#C8C8C8"


class Turtle(Animal):

    TURTLE_MOVE_CHANCE = 0.25

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 2
        self._initiative = 1

    def takeAction(self):
        if random() <= self.TURTLE_MOVE_CHANCE:
            self.move()

    def collision(self, attacker):
        if type(self) == type(attacker):
            self.reproduce()
            return

        if attacker.getStrength() < 5:
            return

        if attacker.getStrength() > self._strength:
            attacker.changePosition(self.x, self.y)
            # log attack
            self._world.deleteOrganism(self)

    def getColor(self):
        return "#8A9A5B"


class Wolf(Animal):

    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 9
        self._initiative = 5

    def getColor(self):
        return "#808080"