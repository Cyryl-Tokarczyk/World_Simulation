#pragma once

#include "Organism.h"

constexpr int DefaultSpreadChance = 5;

class Plant : public Organism
{
public:
	using Organism::Organism;

	void takeAction() override;
	void collision( Organism* attacker ) override;

	void spread( Organism* organism ) const;
};