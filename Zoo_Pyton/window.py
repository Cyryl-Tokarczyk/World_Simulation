from ctypes.wintypes import WORD
from tkinter import Button, Tk

from human import Action


class Window():

    def __init__(self, x, y, world):
        self.__world = world
        self.__window = Tk()
        self.__window.title("Cyryl Tokarczyk s188624 Informatyka Gr.4")
        self.__window.bind("<KeyRelease>", self.keyup)
        self.__buttons = [None] * y
        for i in range(0, y):
            self.__buttons[i] = [None] * x 
            for j in range(0, x):
                org = self.__world.getOrganism(j, i)
                if org == None:
                    button = Button(self.__window, bg="white", height=1, width=2)
                else:
                    color = org.getColor()
                    button = Button(self.__window, bg=color, height=1, width=2)
                self.__buttons[i][j] = button
                button.grid(row=i, column=j)
        self.__window.focus_set()
        self.__window.mainloop()

    def keyup(self, e):
        print ("up", e.char, type(e))
        player = self.__world.getPlayer()
        if e.char == 'w':
            if not player.isLegalMove(player.x, player.y - 1):
                return
            
            player.setAction(Action.UP)
        elif e.char == 's':
            if not player.isLegalMove(player.x, player.y + 1):
                return
            
            player.setAction(Action.DOWN)
        elif e.char == 'a':
            if not player.isLegalMove(player.x - 1, player.y):
                return
            
            player.setAction(Action.LEFT)
        elif e.char == 'd':
            if not player.isLegalMove(player.x + 1, player.y):
                return
            
            player.setAction(Action.RIGHT)
        elif e.char == ' ':
            if not player.isSpecialReady():
                return
            
            player.setAction(Action.SPECIAL)
        else:
            return

        self.__world.makeTurn()
        self.updateTiles()

    def updateTiles(self):
        for i in range(0, self.__world.getY()):
            for j in range(0, self.__world.getX()):
                org = self.__world.getOrganism(j, i)
                if org == None:
                    self.__buttons[i][j]["bg"] = "white"
                else:
                    color = org.getColor()
                    self.__buttons[i][j]["bg"] = color