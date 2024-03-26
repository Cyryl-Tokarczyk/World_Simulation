#include "Plant.h"

void Plant::takeAction()
{
}

void Plant::collision( Organism* attacker )
{
	if (attacker->getStrength() > strength)
	{
		attacker->changePosition( x, y );
		world.logAttack( attacker, this );
		world.deleteOrganism( this );
	}
}

void Plant::spread( Organism* organism ) const
{
	if (rand() % DefaultSpreadChance == 0)
	{
		int newX = x, newY = y;

		getRandomMovePosition( newX, newY );

		organism->setXAndY( newX, newY );

		world.addOrganism( organism ); // Already handles a case where there is another organism there
	}
	else
	{
		delete organism;
	}	
}
