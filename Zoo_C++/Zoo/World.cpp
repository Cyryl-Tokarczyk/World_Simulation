#include "World.h"
#include "Grass.h"
#include "Wolf.h"
#include "Sheep.h"
#include "Dandelion.h"
#include "Guarana.h"
#include "Belladona.h"
#include "Sosnowsky.h"
#include "Fox.h"
#include "Turtle.h"
#include "Antelope.h"
#include "Human.h"

void World::randomizeBoard()
{
	for (int i = 0; i < y; i++)
	{
		for (int j = 0; j < x; j++)
		{
			if (rand() % SpawnChance == 0)
			{
				switch (rand() % NumOfOrganismsToChooseFrom)
				{
				case 0:
					addOrganism( new Grass( *this, j, i ) );
					break;
				case 1:
					addOrganism( new Wolf( *this, j, i ) );
					break;
				case 2:
					addOrganism( new Sheep( *this, j, i ) );
					break;
				case 3:
					addOrganism( new Dandelion( *this, j, i ) );
					break;
				case 4:
					addOrganism( new Guarana( *this, j, i ) );
					break;
				case 5:
					addOrganism( new Belladona( *this, j, i ) );
					break;
				case 6:
					addOrganism( new Sosnowsky( *this, j, i ) );
					break;
				case 7:
					addOrganism( new Fox( *this, j, i ) );
					break;
				case 8:
					addOrganism( new Turtle( *this, j, i ) );
					break;
				case 9:
					addOrganism( new Antelope( *this, j, i ) );
					break;
				default:
					break;
				}
			}
		}
	}
}

World::World( int x, int y ) : x( x ), y( y )
{
	board = new Organism * *[y];
	for (int i = 0; i < y; i++)
	{
		board[i] = new Organism * [x];
		for (int j = 0; j < x; j++)
		{
			board[i][j] = nullptr;
		}
	}

	randomizeBoard();

	if (board[y / 2][x / 2])
	{
		deleteOrganism( board[y / 2][x / 2] );
	}
	addOrganism( new Human( *this, x / 2, y / 2 ) );
}

World::World( int x, int y, bool empty ) : x( x ), y( y )
{
	board = new Organism * *[y];
	for (int i = 0; i < y; i++)
	{
		board[i] = new Organism * [x];
		for (int j = 0; j < x; j++)
		{
			board[i][j] = nullptr;
		}
	}
}

void World::addOrganism( Organism* organism )
{
	// Check if space isn't already occupied

	if (board[organism->getY()][organism->getX()])
	{
		delete organism;
		return;
	}

	// Otherwise proceed

	organisms.push_back( organism );
	board[organism->getY()][organism->getX()] = organisms.back();
}

void World::draw()
{
	system( "cls" );
	std::cout << "Cyryl Tokarczyk s188624 Informatyka Gr.4" << std::endl;

	for (int i = 0; i < x + 2; i++)
	{
		std::cout << 'X';
	}
	std::cout << std::endl;
	for (int i = 0; i < y; i++)
	{
		std::cout << 'X';
		for (int j = 0; j < x; j++)
		{
			if (board[i][j])
				std::cout << *board[i][j];
			else
				std::cout << ' ';
		}
		std::cout << 'X' << std::endl;
	}
	for (int i = 0; i < x + 2; i++)
	{
		std::cout << 'X';
	}
	std::cout << std::endl;
	for (std::string& line : log)
	{
		std::cout << line << std::endl;
	}
	log.clear();
}

void World::makeTurn()
{
	std::sort( organisms.begin(), organisms.end(), &(Organism::compareOrganismsInitiative) );

	for (int i = 0; i < organisms.size(); i++)
	{
		organisms[i]->takeAction();
	}

	for (Organism* organism : organisms)
	{
		organism->advanceAge();
	}
}

int World::getX() const
{
	return x;
}

int World::getY() const
{
	return y;
}

Organism* World::getOrganism( int x, int y ) const
{
	if (0 > x || x >= this->x ||
		0 > y || y >= this->y)
	{
		return nullptr;
	}

	return board[y][x];
}

void World::swap( int x1, int y1, int x2, int y2 )
{
	std::swap( board[y1][x1], board[y2][x2] );
}

void World::deleteOrganism( Organism* organism )
{
	// Delete from board
	for (int i = 0; i < y; i++)
	{
		for (int j = 0; j < x; j++)
		{
			if (board[i][j] == organism)
				board[i][j] = nullptr;
		}
	}

	// Delete from the vector
	for (int i = 0; i < organisms.size(); i++)
	{
		if (organisms[i] == organism)
			organisms.erase( organisms.begin() + i );
	}

	// Actually delete
	delete organism;
}

void World::logAttack( Organism* attacker, Organism* defender )
{
	std::string line;
	if (dynamic_cast<Animal*>(defender))
	{
		line = defender->name() +
			" was killed by " +
			attacker->name() + '!';
	}
	else if (dynamic_cast<Plant*>(defender))
	{
		line = defender->name() +
			" was eaten by " +
			attacker->name() + '!';
	}
	else if (typeid(*defender) == typeid(Human))
	{
		line = "You were killed! :(";
	}
	addToLog( line );
}

void World::saveState()
{
	std::fstream file;
	file.open( "save.txt", std::ios::out );

	// Save board size
	file << x << ',' << y << std::endl;

	// Save organisms
	for (Organism* org : organisms)
	{
		file << org->name() << ','
			<< org->getStrength() << ','
			<< org->getInitiative() << ','
			<< org->getX() << ','
			<< org->getY() << ','
			<< org->getAge() << std::endl;
	}

	file.close();
}

World* World::loadState()
{
	std::fstream file;
	file.open( "save.txt", std::ios::in );

	int x = atoi( readString( file ).c_str() ), y = atoi( readString( file ).c_str() );

	World* world = new World( x, y, true );

	std::string data = readString( file );

	while (data != "")
	{
		std::string name = data;

		int strength = atoi( readString( file ).c_str() ),
			initiative = atoi( readString( file ).c_str() ),
			x = atoi( readString( file ).c_str() ),
			y = atoi( readString( file ).c_str() ),
			age = atoi( readString( file ).c_str() );

		if (name == "Antelope")
		{
			world->addOrganism( new Antelope( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Belladona")
		{
			world->addOrganism( new Belladona( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Dandelion")
		{
			world->addOrganism( new Dandelion( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Fox")
		{
			world->addOrganism( new Fox( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Grass")
		{
			world->addOrganism( new Grass( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Guarana")
		{
			world->addOrganism( new Guarana( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Human")
		{
			world->addOrganism( new Human( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Sheep")
		{
			world->addOrganism( new Sheep( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Sosnowsky")
		{
			world->addOrganism( new Sosnowsky( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Turtle")
		{
			world->addOrganism( new Turtle( *world, strength, initiative, x, y, age ) );
		}
		else if (name == "Wolf")
		{
			world->addOrganism( new Wolf( *world, strength, initiative, x, y, age ) );
		}


		data = readString( file );
	}

	file.close();

	return world;
}

void World::addToLog( const std::string& line )
{
	log.push_back( line );
}

std::string World::readString( std::fstream& file )
{
	char c = file.get();
	std::string result;

	while (c != ',' && c != '\n' && c != -1)
	{
		result += c;
		c = file.get();
	}

	return result;
}

World::~World()
{
	for (int i = 0; i < y; i++)
	{
		delete[] board[i];
	}
	delete[] board;

	for (int i = 0; i < organisms.size(); i++)
	{
		delete organisms[i];
	}
}
