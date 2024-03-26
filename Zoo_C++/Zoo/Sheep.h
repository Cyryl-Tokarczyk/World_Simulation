#pragma once

#include "Animal.h"

class Sheep : public Animal
{
protected:
	Organism* getChild( int x, int y ) const override;

public:
	using Animal::Animal;

	const std::string organismName = "Sheep";

	Sheep( World& world, int x, int y );

	void print( std::ostream& os ) const override;
	std::string name() const override;
};