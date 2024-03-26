#pragma once
#include "Plant.h"
class Belladona : public Plant
{
public:
	using Plant::Plant;

	const std::string organismName = "Belladona";

	void collision( Organism* attacker ) override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};