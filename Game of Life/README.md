# Conway's Game of Life

## Description

Conway's Game of Life is a cellular automaton devised by mathematician John Conway in 1970. This game is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input from human players. The game consists of a grid of cells, each of which can be either alive or dead. The state of the cells evolves over time according to a set of simple rules based on the number of live neighbors each cell has.

## Features

- **Interactive Grid**: Users can click on cells to toggle their state (alive or dead).
- **Game Controls**: Start, pause, and reset the simulation with easy-to-use buttons.
- **Speed Control**: Adjust the speed of the simulation to observe different patterns and behaviors.

## Rules

The next generation of cells is created by applying the following four rules to the current generation (a cell’s neighbors are the eight cells adjacent to it):

1. **Survival**: Any live cell with two or three live neighbors survives.
2. **Death**: Any live cell with fewer than two live neighbors dies (underpopulation) or with more than three live neighbors dies (overpopulation).
3. **Birth**: Any dead cell with exactly three live neighbors becomes a live cell (reproduction).
4. **Stasis**: All other live cells die in the next generation, and all other dead cells remain dead.

## Getting Started

To run the project locally, clone the repository.

