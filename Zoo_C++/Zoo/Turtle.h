#pragma once

#include "Animal.h"

constexpr int TurtleMoveChance = 4;

class Turtle : public Animal
{
protected:
	Organism* getChild( int x, int y ) const override;

public:
	using Animal::Animal;

	const std::string organismName = "Turtle";

	Turtle( World& world, int x, int y );

	void takeAction() override;
	void collision( Organism* attacker ) override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};

