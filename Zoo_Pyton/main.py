from window import Window
from world import World


x = int(input("Input X: "))
y = int(input("Input Y: "))
world = World(x, y, False)

window = Window(x, y, world)