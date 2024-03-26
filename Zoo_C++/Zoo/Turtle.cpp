#include "Turtle.h"

Organism* Turtle::getChild( int x, int y ) const
{
	return new Turtle( world, x, y );
}

Turtle::Turtle( World& world, int x, int y ) : Animal( world, x, y )
{
	strength = 2;
	initiative = 1;
}

void Turtle::takeAction()
{
	if (rand() % TurtleMoveChance == 0)
	{
		move();
	}
}

void Turtle::collision( Organism* attacker )
{
	if (typeid(*this) == typeid(*attacker))
	{
		reproduce();

		return;
	}

	if (attacker->getStrength() < 5)
		return;

	if (attacker->getStrength() > strength)
	{
		attacker->changePosition( x, y );
		world.logAttack( attacker, this );
		world.deleteOrganism( this );
	}
}

void Turtle::print( std::ostream& os ) const
{
	os << (char)4;
}

std::string Turtle::name() const
{
	return organismName;
}
