#pragma once

#include "Animal.h"

class Fox : public Animal
{
protected:
	Organism* getChild( int x, int y ) const override;

public:
	using Animal::Animal;

	const std::string organismName = "Fox";

	Fox( World& world, int x, int y );

	void takeAction() override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};

