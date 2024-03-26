#include "Antelope.h"

Organism* Antelope::getChild( int x, int y ) const
{
	return new Antelope( world, x, y );
}

Antelope::Antelope( World& world, int x, int y ) : Animal( world, x, y )
{
	strength = 4;
	initiative = 4;
}

void Antelope::takeAction()
{
	int newX = x, newY = y;

	switch (rand() % NumberOfDirections)
	{
	case Up:
		newX = newX;
		newY = abs( newY - 2 );
		break;
	case Down:
		newX = newX;
		newY = std::min( newY + 2, world.getY() - 2 );
		break;
	case Left:
		newX = abs( newX - 2 );
		newY = newY;
		break;
	case Right:
		newX = std::min( newX + 2, world.getX() - 2 );
		newY = newY;
		break;
	case UpLeft:
		newX = abs( newX - 2 );
		newY = abs( newY - 2 );
		break;
	case UpRight:
		newX = std::min( newX + 2, world.getX() - 2 );
		newY = abs( newY - 2 );
		break;
	case DownLeft:
		newX = abs( newX - 2 );
		newY = std::min( newY + 2, world.getY() - 2 );
		break;
	case DownRight:
		newX = std::min( newX + 2, world.getX() - 2 );
		newY = std::min( newY + 2, world.getY() - 2 );
		break;
	default:
		break;
	}

	Organism* organism = world.getOrganism( newX, newY );

	if (organism)
	{
		organism->collision( this );
	}
	else
	{
		changePosition( newX, newY );
	}
}

void Antelope::collision( Organism* attacker )
{
	if (typeid(*this) == typeid(*attacker))
	{
		reproduce();

		return;
	}

	if (rand() % AntelopeDodgeChance == 0)
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
		} while (organism);

		if (!organism)
		{
			changePosition( newX, newY );
			return;
		}
	}

	Animal::collision( attacker );
}

void Antelope::print( std::ostream& os ) const
{
	os << (char)14;
}

std::string Antelope::name() const
{
	return organismName;
}
