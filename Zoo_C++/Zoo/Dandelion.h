#pragma once

#include "Plant.h"

class Dandelion : public Plant
{
public:
	using Plant::Plant;

	const std::string organismName = "Dandelion";

	void takeAction() override;
	void print( std::ostream& os ) const override;
	std::string name() const override;
};