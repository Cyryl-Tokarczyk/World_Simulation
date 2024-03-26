#include "Grass.h"

void Grass::takeAction()
{
	spread( new Grass( world, x, y ) );
}

void Grass::print( std::ostream& os ) const
{
	os << '|';
}

std::string Grass::name() const
{
	return organismName;
}
