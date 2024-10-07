import { Universe } from './Universe.js'; // Import Universe class

export class Grid extends Universe {
    constructor(height, width)
    {
        super(height, width);
        /* SUPER: 
        this.height = height;
        this.width = width;
        */

        // grid attributes
        this.cellSize = 10;
        this.nColumns = this.calculateColumns();
        this.nRows = this.calculateRows();

        // grid array
        this.grid = this.createGrid();

        this.canvas = null;
        this.context = null;

        this.gen = 0;
    }

    getColumns()
    {
        return this.nColumns;
    }

    getRows()
    {
        return this.nRows;
    }

    getGeneration()
    {
        return this.gen;
    }
    
    calculateColumns()
    {
        const columns = Math.floor(this.width / this.cellSize); // get only the integer part of the division
        return columns;
    }

    calculateRows()
    {
        const rows = Math.floor(this.height / this.cellSize); // get only the integer part of the division
        return rows;       
    }

    createGrid() // create a grid with all dead (default) cells
    {
        // creating array of size nRows, this array will be an array of arrays (rows, columns)
        //let rowsList = new Array(this.nRows); 

        let rowList = new Array();

        for (let i = 0; i < this.nRows; i++)
        {
            let row = new Array();
            for (let j = 0; j < this.nColumns; j++)
            {
                row.push(0); // creating an array with elements value 0
            }
            rowList.push(row);
        }
        return rowList;
    }

    resetGrid()
    {
        for (let row = 0; row < this.nRows; row++) 
        {
            for (let col = 0; col < this.nColumns; col++) 
            {
                this.grid[row][col] = 0;
            }
        }

        // reset gen
        this.gen = 0;
        return;
    }

    // create a random grid
    randomGrid()
    {
        for (let row = 0; row < this.nRows; row++) 
        {
            for (let col = 0; col < this.nColumns; col++) 
            {
                const random = this.randomNumber();
                this.grid[row][col] = random;
            }
        }

        // reset gen
        this.gen = 0;

        return;
    }

    randomNumber() // random number 0 or 1
    {
        /*
        Math.random() 
            This method generates a random floating-point number between 0 (inclusive) and 1 (exclusive)
        Math.random() * 2 
            Multiply the random number by 2. This scales the range of possible values to between 0 (inclusive) and 2 (exclusive)
        Math.floor(...)
            Math.floor() takes a number and rounds it down to the nearest whole number. For example:
            If the random number generated was 0.3, multiplying by 2 gives 0.6, and Math.floor(0.6) would return 0.
            If the generated number was 1.7, Math.floor(1.7) would return 1.
            Therefore, the result of Math.floor(Math.random() * 2) will either be 0 or 1.
        */
        return Math.floor(Math.random() * 2);
    }

    plotGrid(container)
    {
        // Create and store the canvas and its context as attributes
        this.canvas = document.createElement('canvas');
        this.canvas.width = this.width;
        this.canvas.height = this.height;
        container.appendChild(this.canvas);

        this.context = this.canvas.getContext('2d');

        // Clear the canvas
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height); 

        // Draw grid lines
        this.context.strokeStyle = 'lightgray';
        this.context.lineWidth = 1;

        // Draw vertical lines
        for (let col = 0; col <= this.nColumns; col++) {
            const x = col * this.cellSize;
            this.context.beginPath();
            this.context.moveTo(x, 0);
            this.context.lineTo(x, this.height);
            this.context.stroke();
        }

        // Draw horizontal lines
        for (let row = 0; row <= this.nRows; row++) {
            const y = row * this.cellSize;
            this.context.beginPath();
            this.context.moveTo(0, y);
            this.context.lineTo(this.width, y);
            this.context.stroke();
        }

        // Add mouse listeners for interaction
        this.addMouseListeners();

        return;
    }

    populateGrid() 
    {
        // Loop through the grid array and populate the cells
        for (let row = 0; row < this.nRows; row++) {
            for (let col = 0; col < this.nColumns; col++) {
                const cellValue = this.grid[row][col]; // Get the value of the cell (1 or 0)
                const x = col * this.cellSize;
                const y = row * this.cellSize;

                // Fill the cell based on the value: black for 1, white for 0
                if (cellValue === 1) {
                    this.context.fillStyle = 'black';
                } else {
                    this.context.fillStyle = 'white';
                }

                // Draw the cell
                this.context.fillRect(x, y, this.cellSize, this.cellSize);

                // Draw the grid lines over the cells
                this.context.strokeStyle = 'lightgray';
                this.context.lineWidth = 1;
                this.context.strokeRect(x, y, this.cellSize, this.cellSize);
            }
        }

        return;
    }

    printGrid(grid)
    {
        console.log("PRINT GRID");
        
        // iterate each cell on the universe
        let gridString = "";
        for (let row = 0; row < this.nRows; row++)
        {
            gridString = gridString + grid[row] + "\n";
        }
        console.log(gridString);
        return;
    }

    runUniverse()
    {
        const movements = [
            [-1, -1], // top left
            [-1, 0], // top
            [-1, 1], // top right
            [0, -1], // left
            [0, 1], // right
            [1, -1], // bottom left
            [1, 0], // bottom
            [1, 1] // boom right
        ]

        // create a helper grid array to store to live or die in next generation
        let nextGenGrid = this.copyGrid();
        // this.printGrid(this.grid);
        // this.printGrid(nextGenGrid);

        // iterate each cell on the universe
        for (let row = 0; row < this.nRows; row++)
        {
            for (let col = 0; col < this.nColumns; col++)
            {
                const cellValue = this.grid[row][col];

                let liveCells = 0;

                // iterate over movements
                for(let i = 0; i < movements.length; i++)
                {
                    const movement = movements[i];
                    let movRow = movement[0];
                    let movCol = movement[1];

                    movRow = movRow + row;
                    movCol = movCol + col;

                    const inBounds = this.checkBounds(movRow, movCol);

                    //console.log(`Cell: ${row}, ${col} | mov: ${movRow}, ${movCol} | inBounds ${inBounds}`);
                    if (inBounds)
                    {
                        const neighbour = this.grid[movRow][movCol];
                        
                        if (neighbour === 1)
                        {
                            liveCells++;
                        }
                    }
                }

                // live or die
                //console.log(`Cell ${row}, ${col}: ${cellValue} | Live cells: ${liveCells}`);

                // live or die
                this.liveDie(nextGenGrid, cellValue, liveCells, row, col);
            }
        }
        // this.printGrid(this.grid);
        // this.printGrid(nextGenGrid);
        
        // update grid
        this.grid = nextGenGrid;
        // this.printGrid(this.grid);

        // update gen
        this.gen++;
    }

    copyGrid()
    {
        let rowList = new Array();

        for (let i = 0; i < this.nRows; i++)
        {
            let row = new Array();
            for (let j = 0; j < this.nColumns; j++)
            {
                const cell = this.grid[i][j];
                row.push(cell); 
            }
            rowList.push(row);
        }
        return rowList;
    }
    
    checkBounds(x, y)
    {
        let inBounds = false;

        if (x >= 0 && x <= (this.nRows - 1))
        {
            if (y >= 0 && y <= (this.nColumns - 1))
            {
                inBounds = true;
            }
        }
        return inBounds;
    }

    liveDie(grid, cellValue, nAlive, row, col)
    {
        if (cellValue === 1)
        {
            if (nAlive < 2) // Any live cell with fewer than two live neighbours dies, as if by underpopulation.
            {
                grid[row][col] = 0;
            }
            else if (nAlive === 2 || nAlive === 3) // Any live cell with two or three live neighbours lives on to the next generation.
            {
                grid[row][col] = 1;
            }
            else if (nAlive > 3) // Any live cell with more than three live neighbours dies, as if by overpopulation.
            {
                grid[row][col] = 0;
            }
        }
        else 
        {
            if (nAlive  === 3) // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
            {
                grid[row][col] = 1;
            }
        }

        return;
    }

    play()
    {
        //console.log("play method");
        this.runUniverse();
        this.populateGrid();
    }

    // ------------------- Mouse Movement -------------------
    // Click and drag over the grid to paint cells black
    addMouseListeners() {
        let mouseOn = false; // Track if the mouse is currently down

        // Detect mouse down (start painting)
        this.canvas.addEventListener('mousedown', (event) => {
            mouseOn = true; // Mouse is pressed down
            this.paintCell(event); // Paint the cell on click
        });

        // Detect mouse up (stop painting)
        window.addEventListener('mouseup', () => {
            mouseOn = false; // Mouse is released
        });

        // Detect mouse movement over the canvas
        this.canvas.addEventListener('mousemove', (event) => {
            if (mouseOn) { // Only paint if the mouse is pressed down
                this.paintCell(event); // Paint the cell based on the current mouse position
            }
        });
    }

    // Function to calculate which cell is being hovered over and paint it
    paintCell(event) {
        //console.log("print cell event");
        const rect = this.canvas.getBoundingClientRect(); // Get canvas bounds
        const x = event.clientX - rect.left; // X position relative to the canvas
        const y = event.clientY - rect.top;  // Y position relative to the canvas

        const col = Math.floor(x / this.cellSize);
        const row = Math.floor(y / this.cellSize);

        // Ensure valid cell coordinates
        if (col >= 0 && col < this.nColumns && row >= 0 && row < this.nRows) {
            // Set the cell value to 1 (paint it)
            this.grid[row][col] = 1; 
            this.updateCell(row, col); // Update the visual representation of the cell
        }
    }

    // Update the individual cell (without redrawing the whole grid)
    updateCell(row, col) {
        //console.log("update cell");
        const x = col * this.cellSize;
        const y = row * this.cellSize;

        // Fill the cell based on the value: black for 1, white for 0
        this.context.fillStyle = this.grid[row][col] === 1 ? 'black' : 'white';
        this.context.fillRect(x, y, this.cellSize, this.cellSize);

        // Draw grid lines over the cell
        this.context.strokeStyle = 'lightgray';
        this.context.lineWidth = 1;
        this.context.strokeRect(x, y, this.cellSize, this.cellSize);
    }
}
