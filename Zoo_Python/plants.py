import animals
from plant import Plant


class Belladona(Plant):

    def takeAction(self):
        self.spread(Belladona(self._world, self.x, self.y))

    def collision(self, attacker):
        # log attack
        self._world.deleteOrganism(attacker)
        self._world.deleteOrganism(self)

    def getColor(self):
        return "#806777"


class Dandelion(Plant):

    def takeAction(self):
        for i in range(0, 3):
            self.spread(Dandelion(self._world, self.x, self.y))

    def getColor(self):
        return "#F0E130"


class Grass(Plant):

    def takeAction(self):
        self.spread(Grass(self._world, self.x, self.y))

    def getColor(self):
        return "#348C31"


class Guarana(Plant):

    BUFF_VALUE = 3

    def takeAction(self):
        self.spread(Guarana(self._world, self.x, self.y))

    def collision(self, attacker):
        if attacker.getStrength() > self._strength:
            attacker.changePosition(self.x, self.y)
            # log attack
            self._world.deleteOrganism(self)
            attacker.buffStrength(self.BUFF_VALUE)

    def getColor(self):
        return "#D73F58"


class Sosnowsky(Plant):

    def takeAction(self):
        for i in range(self.x - 1, self.x + 2):
            for j in range(self.y - 1, self.y + 2):
                org = self._world.getOrganism(i, j)
                if org != None and not isinstance(org, Plant) and not isinstance(org, animals.CyberSheep):
                    self._world.deleteOrganism(org)

        self.spread(Sosnowsky(self._world, self.x, self.y))

    def collision(self, attacker):
        # log attack
        if not isinstance(attacker, animals.CyberSheep):
            self._world.deleteOrganism(attacker)
        self._world.deleteOrganism(self)

    def getColor(self):
        return "#C8FFC8"