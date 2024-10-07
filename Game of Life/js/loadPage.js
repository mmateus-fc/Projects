import { Grid } from './classes/Grid.js'; // Import the Grid class

// Declare universeGrid globally by attaching it to the window object
window.universeGrid;

// create universeGrid
function createUniverseGrid()
{
    // Get the universe div
    const universeDiv = document.querySelector('.universe');

    // Get the height of the universeDiv
    const universeHeight = universeDiv.clientHeight;
    const universeWidth = universeDiv.clientWidth;

    // Create object Grid
    window.universeGrid = new Grid(universeHeight, universeWidth, universeDiv);

    //console.log(`height: ${universeGrid.getHeight()}, width: ${universeGrid.getWidth()}, columns: ${universeGrid.getColumns()}, rows: ${universeGrid.getRows()}`);
    window.universeGrid.plotGrid(universeDiv);
    window.universeGrid.populateGrid();

}

createUniverseGrid();



