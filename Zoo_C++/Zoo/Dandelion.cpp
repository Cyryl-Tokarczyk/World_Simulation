#include "Dandelion.h"

void Dandelion::takeAction()
{
	for (int i = 0; i < 3; i++)
		spread( new Dandelion( world, x, y ) );
}

void Dandelion::print( std::ostream& os ) const
{
	os << '*';
}

std::string Dandelion::name() const
{
	return organismName;
}
