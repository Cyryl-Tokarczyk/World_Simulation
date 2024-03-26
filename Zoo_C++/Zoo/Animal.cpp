#include "Animal.h"

void Animal::takeAction()
{
	move();
}

void Animal::collision( Organism* attacker )
{
	if (typeid(*this) == typeid(*attacker))
	{
		reproduce();

		return;
	}

	if (attacker->getStrength() > strength)
	{
		attacker->changePosition( x, y );
		world.logAttack( attacker, this );
		world.deleteOrganism( this );
	}
}

void Animal::reproduce() const
{
	int childX, childY, timer = 0;

	do
	{
		if (timer > maxChildPositionRandomisingTries)
		{
			return;
		}
		childX = x;
		childY = y;
		getRandomMovePosition( childX, childY );
		timer++;
	} while (world.getOrganism( childX, childY ));

	world.addOrganism( getChild( childX, childY ) );
}

void Animal::move()
{
	int newX = x, newY = y;

	getRandomMovePosition( newX, newY );

	Organism* organism = world.getOrganism( newX, newY );

	if (organism)
	{
		organism->collision( this );
	}
	else
	{
		changePosition( newX, newY );
	}
}
