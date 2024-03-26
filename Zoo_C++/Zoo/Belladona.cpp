#include "Belladona.h"

void Belladona::collision( Organism* attacker )
{
	world.logAttack( attacker, this );
	world.deleteOrganism( attacker );
	world.deleteOrganism( this );
}

void Belladona::print( std::ostream& os ) const
{
	os << "#";
}

std::string Belladona::name() const
{
	return organismName;
}
