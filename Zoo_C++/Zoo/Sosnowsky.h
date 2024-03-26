#pragma once

#include "Plant.h"

class Sosnowsky : public Plant
{
public:
	using Plant::Plant;

	const std::string organismName = "Sosnowsky";

	void takeAction() override;
	void collision( Organism* attacker ) override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};