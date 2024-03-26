#pragma once

#include <vector>
#include <algorithm>
#include <conio.h>
#include <fstream>

#include "Organism.h"

constexpr int SpawnChance = 10;
constexpr int NumOfOrganismsToChooseFrom = 10;

class Organism;

class World
{
private:
	int x, y;
	Organism*** board;
	std::vector<Organism*> organisms;
	std::vector<std::string> log;

	void randomizeBoard();
	void addToLog( const std::string& line );
	std::string readString( std::fstream& file );
public:
	World( int x, int y );
	World( int x, int y, bool empty );

	void addOrganism( Organism* organism );

	void makeTurn();
	void draw();

	int getX() const;
	int getY() const;
	Organism* getOrganism( int x, int y ) const;

	void swap( int x1, int y1, int x2, int y2 );
	void deleteOrganism( Organism* organism );

	void logAttack( Organism* attacker, Organism* defender );

	void saveState();
	World* loadState();

	~World();
};