#pragma once

#include <iostream>
#include <typeinfo>

#include "World.h"

class World;

enum Directions
{
	Up,
	Down,
	Left,
	Right,
	UpLeft,
	UpRight,
	DownLeft,
	DownRight,
	NumberOfDirections,
};

class Organism
{
protected:
	int strength, initiative, x, y, age = 0;
	World& world;

public:
	const std::string organismName = "Unknown";

	static bool compareOrganismsInitiative( Organism* first, Organism* second );

	Organism( World& world );
	Organism( World& world, int x, int y );
	Organism( World& world, int strength, int initiative, int x, int y, int age );

	friend std::ostream& operator<<( std::ostream& os, const Organism& organism );
	virtual void print( std::ostream& os ) const;

	virtual void takeAction() = 0;
	virtual void collision( Organism* attacker ) = 0;
	virtual void advanceAge();
	virtual std::string name() const;

	int getX() const;
	int getY() const;
	int getStrength() const;
	int getInitiative() const;
	int getAge() const;
	void getRandomMovePosition( int& newX, int& newY ) const;

	void setX( int x );
	void setY( int y );
	void setXAndY( int x, int y );

	void changePosition( int x, int y );
	void buffStrength( int value );
};