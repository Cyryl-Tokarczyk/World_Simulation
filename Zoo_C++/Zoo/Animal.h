#pragma once

#include "Organism.h"

constexpr int maxChildPositionRandomisingTries = 15;

class Animal : public Organism
{
protected:
	virtual Organism* getChild( int x, int y ) const = 0;

public:
	using Organism::Organism;

	void takeAction() override;
	void collision( Organism* organism ) override;

	void reproduce() const;

	void move();
};

