from abc import abstractmethod
from enum import Enum, auto
import random


class Directions(Enum):
    UP = auto()
    DOWN = auto()
    RIGHT = auto()
    LEFT = auto()


class Organism:

    _strength = None
    _initiative = None
    _x = None
    _y = None
    _age = 0
    _world = None

    # def __init__(self, world):
    #     self._world = world
    #     self._strength = 0
    #     self._initiative = 0
    #     self._x = -1
    #     self._y = -1

    def __init__(self, world, x, y):
        self._world = world
        self._strength = 0
        self._initiative = 0
        self._x = x
        self._y = y

    # def __init__(self, world, strength, initiative, x, y, age):
    #     self._world = world
    #     self._strength = strength
    #     self._initiative = initiative
    #     self._x = x
    #     self._y = y
    #     self._age = age

    @abstractmethod
    def takeAction(self):
        pass

    @abstractmethod
    def collision(self, attacker):
        pass

    def advanceAge(self):
        self._age += 1

    def getX(self):
        return self._x

    def setX(self, x):
        self._x = x

    x = property(getX, setX)

    def getY(self):
        return self._y

    def setY(self, y):
        self._y = y

    y = property(getY, setY)

    def setXAndY(self, x, y):
        self._x = x
        self._y = y

    def getStrength(self):
        return self._strength

    def getInitiative(self):
        return self._initiative

    def getAge(self):
        return self._age

    def getRandomMovePosition(self, x, y, move = 1):
        pos = [None] * 2
        dir = random.choice(list(Directions))

        if dir == Directions.UP:
            pos[0] = x
            pos[1] = abs(y - move)
        elif dir == Directions.DOWN:
            pos[0] = x
            pos[1] = min(y + move, self._world.getY() - move)
        elif dir == Directions.RIGHT:
            pos[0] = min(x + move, self._world.getX() - move)
            pos[1] = y
        elif dir == Directions.LEFT:
            pos[0] = abs(x - move)
            pos[1] = y

        return pos

    def changePosition(self, x, y):
        self._world.swap(self.x, self.y, x, y)
        self._world.getOrganism(x, y).setXAndY(self.x, self.y)
        self.setXAndY(x, y)

    def buffStrength(self, value):
        self._strength += value