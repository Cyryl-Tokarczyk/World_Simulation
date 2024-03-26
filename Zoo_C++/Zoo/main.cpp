#include <iostream>

#include "World.h"

using namespace std;

int main()
{
	/*for (int i = 0; i < 256; i++)
	{
		cout << i << ' ' << (char)i << '\t';
	}
	return 0;*/

	srand( time( NULL ) );

	int x, y;
	char command;
	cout << "Specify board size: ";
	cin >> x >> y;

	World* world = new World( x, y );
	world->draw();

	do
	{
		command = _getch();

		if (command == 'k')
			world->saveState();
		if (command == 'l')
		{
			World* temp = world->loadState();
			delete world;
			world = temp;
			world->draw();
		}

		world->makeTurn();

		world->draw();
	} while (command != 'q');

	delete world;

	return 0;
}