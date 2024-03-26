#include "Sheep.h"

Organism* Sheep::getChild( int x, int y ) const
{
	return new Sheep( world, x, y );
}

Sheep::Sheep( World& world, int x, int y ) : Animal( world, x, y )
{
	strength = 4;
	initiative = 4;
}

void Sheep::print( std::ostream& os ) const
{
	os << 'S';
}

std::string Sheep::name() const
{
	return organismName;
}
