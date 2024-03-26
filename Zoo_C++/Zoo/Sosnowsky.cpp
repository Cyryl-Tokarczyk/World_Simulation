#include "Sosnowsky.h"

void Sosnowsky::takeAction()
{
	for (int i = x - 1; i <= x + 1; i++)
	{
		for (int j = y - 1; j <= y + 1; j++)
		{
			Organism* org = world.getOrganism( i, j );
			if (org && !dynamic_cast<Plant*>(org))
			{
				world.deleteOrganism( org );
			}
		}
	}

	spread( new Sosnowsky( world, x, y ) );
}

void Sosnowsky::collision( Organism* attacker )
{
	world.logAttack( attacker, this );
	world.deleteOrganism( attacker );
	world.deleteOrganism( this );
}

void Sosnowsky::print( std::ostream& os ) const
{
	os << (char)5;
}

std::string Sosnowsky::name() const
{
	return organismName;
}
