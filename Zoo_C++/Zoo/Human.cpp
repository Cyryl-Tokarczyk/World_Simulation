#include "Human.h"

void Human::move( int newX, int newY )
{
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

void Human::special()
{
	if (currentSpecialCooldown <= 0)
	{
		for (int i = x - 1; i <= x + 1; i++)
		{
			for (int j = y - 1; j <= y + 1; j++)
			{
				Organism* org = world.getOrganism( i, j );
				if (!(i == x && j == y) && org)
				{
					world.deleteOrganism( org );
				}
			}
		}

		currentSpecialCooldown = specialCooldown;
	}
}

bool Human::isLegalMove( int x, int y ) const
{
	if (0 <= x && x < world.getX() &&
		0 <= y && y < world.getY())
	{
		return true;
	}

	return false;
}

Human::Human( World& world, int x, int y ) : Organism( world, x, y )
{
	strength = 5;
	initiative = 4;
}

void Human::takeAction()
{
	while (true)
	{
		char key = _getch();
		int newX = x, newY = y;

		if (key == 'w')
		{
			newY--;

			if (!isLegalMove( newX, newY ))
				continue;

			move( newX, newY );
			break;
		}
		if (key == 's')
		{
			newY++;

			if (!isLegalMove( newX, newY ))
				continue;

			move( newX, newY );
			break;
		}
		if (key == 'a')
		{
			newX--;

			if (!isLegalMove( newX, newY ))
				continue;

			move( newX, newY );
			break;
		}
		if (key == 'd')
		{
			newX++;

			if (!isLegalMove( newX, newY ))
				continue;

			move( newX, newY );
			break;
		}
		if (key == ' ')
		{
			special();
		}
	}
}

void Human::collision( Organism* attacker )
{
	if (attacker->getStrength() > strength)
	{
		attacker->changePosition( x, y );
		world.logAttack( attacker, this );
		world.deleteOrganism( this );
	}
}

void Human::advanceAge()
{
	age++;

	if( currentSpecialCooldown > 0 )
		currentSpecialCooldown--;
}

void Human::print( std::ostream& os ) const
{
	os << (char)1;
}

std::string Human::name() const
{
	return organismName;
}
