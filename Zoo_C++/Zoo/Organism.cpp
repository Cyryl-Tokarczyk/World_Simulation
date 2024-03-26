#include "Organism.h"

Organism::Organism( World& world ) : world( world )
{
	strength = 0;
	initiative = 0;
	x = -1;
	y = -1;
}

Organism::Organism( World& world, int x, int y ) : world( world ), x( x ), y( y )
{
	strength = 0;
	initiative = 0;
}

Organism::Organism( World& world, int strength, int initiative, int x, int y, int age ) : world( world ), strength( strength ), initiative( initiative ), x( x ), y( y ), age( age ) {};

std::ostream& operator<<( std::ostream& os, const Organism& organism )
{
	organism.print( os );

	return os;
}

void Organism::print( std::ostream& os ) const
{
	os << ' ';
}

void Organism::advanceAge()
{
	age++;
}

int Organism::getX() const
{
	return x;
}

int Organism::getY() const
{
	return y;
}

int Organism::getStrength() const
{
	return strength;
}

int Organism::getInitiative() const
{
	return initiative;
}

int Organism::getAge() const
{
	return age;
}

bool Organism::compareOrganismsInitiative( Organism* first, Organism* second )
{
	if (first->getInitiative() > second->getInitiative())
		return true;
	if (first->getInitiative() == second->getInitiative()
		&& first->getAge() > second->getAge())
		return true;

	return false;
}

void Organism::getRandomMovePosition( int& x, int& y ) const
{
	switch (rand() % NumberOfDirections)
	{
	case Up:
		x = x;
		y = abs( y - 1 );
		break;
	case Down:
		x = x;
		y = std::min( y + 1, world.getY() - 1 );
		break;
	case Left:
		x = abs( x - 1 );
		y = y;
		break;
	case Right:
		x = std::min( x + 1, world.getX() - 1 );
		y = y;
		break;
	case UpLeft:
		x = abs( x - 1 );
		y = abs( y - 1 );
		break;
	case UpRight:
		x = std::min( x + 1, world.getX() - 1 );
		y = abs( y - 1 );
		break;
	case DownLeft:
		x = abs( x - 1 );
		y = std::min( y + 1, world.getY() - 1 );
		break;
	case DownRight:
		x = std::min( x + 1, world.getX() - 1 );
		y = std::min( y + 1, world.getY() - 1 );
		break;
	default:
		break;
	}
}

void Organism::buffStrength( int value )
{
	strength += value;
}

void Organism::setX( int x )
{
	this->x = x;
}

void Organism::setY( int y )
{
	this->y = y;
}

void Organism::setXAndY( int x, int y )
{
	this->x = x;
	this->y = y;
}

std::string Organism::name() const
{
	return organismName;
}

void Organism::changePosition( int newX, int newY )
{
	world.swap( x, y, newX, newY );
	x = newX;
	y = newY;
}