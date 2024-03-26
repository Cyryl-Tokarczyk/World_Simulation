#pragma once

#include "Plant.h"

class Guarana : public Plant
{
public:
	using Plant::Plant;

	const std::string organismName = "Guarana";

	void collision( Organism* attacker ) override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};