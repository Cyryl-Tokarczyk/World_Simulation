#include "Fox.h"

Organism* Fox::getChild( int x, int y ) const
{
	return new Fox( world, x, y );
}

Fox::Fox( World& world, int x, int y ) : Animal( world, x, y )
{
	strength = 3;
	initiative = 7;
}

void Fox::takeAction()
{
	int newX, newY, timer = 0;

	Organism* organism;

	do
	{
		if (timer > maxChildPositionRandomisingTries)
		{
			return;
		}
		newX = x;
		newY = y;
		getRandomMovePosition( newX, newY );
		organism = world.getOrganism( newX, newY );
		timer++;
	} while (organism && organism->getStrength() > strength);

	if (organism)
	{
		organism->collision( this );
	}
	else
	{
		changePosition( newX, newY );
	}
}

void Fox::print( std::ostream& os ) const
{
	os << 'F';
}

std::string Fox::name() const
{
	return organismName;
}
