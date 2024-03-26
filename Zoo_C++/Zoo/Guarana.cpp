#include "Guarana.h"

void Guarana::collision( Organism* attacker )
{
	if (attacker->getStrength() > strength)
	{
		attacker->changePosition( x, y );
		world.logAttack( attacker, this );
		world.deleteOrganism( this );
		attacker->buffStrength( 3 );
	}
}

void Guarana::print( std::ostream& os ) const
{
	os << "G";
}

std::string Guarana::name() const
{
	return organismName;
}
