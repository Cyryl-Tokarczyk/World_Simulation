from random import randint, random
from animals import Antelope, CyberSheep, Fox, Sheep, Turtle, Wolf
from human import Action, Human
from organism import Organism
from plants import Belladona, Dandelion, Grass, Guarana, Sosnowsky


class World:

    SPAWN_CHANCE = 0.05
    NUM_OF_ORG_TO_CHOOSE_FROM = 11

    __x = None
    __y = None
    __player = None
    __board = None
    __organisms = []

    def addOrganism(self, organism):
        if self.__board[organism.y][organism.x] != None:
            return

        # children add organism
        self.__board[organism.y][organism.x] = organism
        self.__organisms.insert(0, organism)

    def __init__(self, x, y, empty):
        self.__x = x
        self.__y = y
        self.__board = [None] * y
        for i in range(0, y):
            self.__board[i] = [None] * x

        if empty == False:
            self.randomizeBoard()

            self.__player = Human(self, x // 2, y // 2)
            self.addOrganism(self.__player)
            # addAll children
            # clear children

    def makeTurn(self):
        getInitiative = Organism.getInitiative
        sorted(self.__organisms, key=getInitiative)

        for organism in self.__organisms:
            organism.takeAction()
        # manage children
        for organism in self.__organisms:
            organism.advanceAge()

    def getX(self):
        return self.__x

    def getY(self):
        return self.__y

    def getPlayer(self):
        return self.__player

    def getOrganism(self, x, y):
        if 0 > x or x >= self.__x or 0 > y or y >= self.__y:
            return None

        return self.__board[y][x]

    def swap(self, x1, y1, x2, y2):
        temp = self.__board[y1][x1]
        self.__board[y1][x1] = self.__board[y2][x2]
        self.__board[y2][x2] = temp

    def deleteOrganism(self, organism):
        for i in range(0, self.__y):
            for j in range(0, self.__x):
                if self.__board[i][j] == organism:
                    self.__board[i][j] = None

        self.__organisms.remove(organism)

    def randomizeBoard(self):
        for i in range(0, self.__y):
            for j in range(0, self.__x):
                if random() <= self.SPAWN_CHANCE:
                    orgNum = randint(0, self.NUM_OF_ORG_TO_CHOOSE_FROM - 1)
                    if orgNum == 0:
                        self.addOrganism(Grass(self, j, i))
                    elif orgNum == 1:
                        self.addOrganism(Wolf(self, j, i))
                    elif orgNum == 2:
                        self.addOrganism(Sheep(self, j, i))
                    elif orgNum == 3:
                        self.addOrganism(Dandelion(self, j, i))
                    elif orgNum == 4:
                        self.addOrganism(Guarana(self, j, i))
                    elif orgNum == 5:
                        self.addOrganism(Belladona(self, j, i))
                    elif orgNum == 6:
                        self.addOrganism(Sosnowsky(self, j, i))
                    elif orgNum == 7:
                        self.addOrganism(Fox(self, j, i))
                    elif orgNum == 8:
                        self.addOrganism(Turtle(self, j, i))
                    elif orgNum == 9:
                        self.addOrganism(Antelope(self, j, i))
                    elif orgNum == 10:
                        self.addOrganism(CyberSheep(self, j, i))