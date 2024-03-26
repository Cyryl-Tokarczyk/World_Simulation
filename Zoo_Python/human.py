from enum import Enum, auto
from operator import truediv
from re import X
from tkinter import RIGHT, Y

from organism import Organism


class Action(Enum):
    UP = auto()
    DOWN = auto()
    LEFT = auto()
    RIGHT = auto()
    SPECIAL = auto()

class Human(Organism):

    SPECIAL_COOLDOWN = 5

    __currentSpecialCD = 0
    __action = None

    # def __init__(self, world):
    #     super().__init__(world)
    #     self._strength = 5
    #     self._initiative = 4
        
    def __init__(self, world, x, y):
        super().__init__(world, x, y)
        self._strength = 5
        self._initiative = 4

    # def __init__(self, world, strength, initivative, x, y, age, specialCD):
    #     super().__init__(world, strength, initivative, x, y, age)
    #     self.__currentSpecialCD = specialCD
    
    def setAction(self, action):
        self.__action = action

    def getCurrentSpecialCD(self):
        return self.__currentSpecialCD

    def move(self, x, y):
        organism = self._world.getOrganism(x, y)

        if organism != None:
            organism.collision(self)
        else:
            self.changePosition(x, y)

    def special(self):
        if not self.isSpecialReady():
            return

        for i in range(self.x-1, self.x+2):
            for j in range(self.y-1, self.y+2):
                org = self._world.getOrganism(i, j)
                if not (i == self.x and j == self.y) and org != None:
                    self._world.deleteOrganism(org)
        
        self.__currentSpecialCD = self.SPECIAL_COOLDOWN

    def isLegalMove(self, x, y):
        if 0 <= x and x < self._world.getX() and 0 <= y and y < self._world.getY():
            return True

        return False

    def isSpecialReady(self):
        return self.__currentSpecialCD <= 0

    def takeAction(self):
        if self.__action == Action.UP:
            self.move(self.x, self.y - 1)
        elif self.__action == Action.DOWN:
            self.move(self.x, self.y + 1)
        elif self.__action == Action.LEFT:
            self.move(self.x - 1, self.y)
        elif self.__action == Action.RIGHT:
            self.move(self.x + 1, self.y)
        elif self.__action == Action.SPECIAL:
            self.special()

    def collision(self, attacker):
        if attacker.getStrength() > self._strength:
            attacker.changePosition(self.x, self.y)
            # log attack
            self._world.deleteOrganism(self)

    def advanceAge(self):
        self._age += 1

        if self.__currentSpecialCD > 0:
            self.__currentSpecialCD -= 1

    def getColor(self):
        return "black"