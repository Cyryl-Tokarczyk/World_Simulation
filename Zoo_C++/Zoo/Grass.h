#pragma once

#include "Plant.h"

class Grass : public Plant
{
public:
	using Plant::Plant;

	const std::string organismName = "Grass";

	void takeAction() override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};

