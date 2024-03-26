#include "Wolf.h"

Organism* Wolf::getChild( int x, int y ) const
{
	return new Wolf( world, x, y );
}

Wolf::Wolf( World& world, int x, int y ) : Animal( world, x, y )
{
	strength = 9;
	initiative = 5;
}

void Wolf::print( std::ostream& os ) const
{
	os << 'W';
}

std::string Wolf::name() const
{
	return organismName;
}
