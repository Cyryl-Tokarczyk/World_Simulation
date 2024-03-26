#pragma once

#include "Organism.h"

constexpr int specialCooldown = 5;

class Human : public Organism
{
private:
	int currentSpecialCooldown = 0;

	void move( int x, int y );
	void special();
	bool isLegalMove( int x, int y ) const;

public:
	using Organism::Organism;

	const std::string organismName = "Human";

	Human( World& world, int x, int y );

	void takeAction() override;
	void collision( Organism* attacker ) override;
	void advanceAge() override;

	void print( std::ostream& os ) const override;
	std::string name() const override;
};