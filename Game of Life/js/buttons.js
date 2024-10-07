// get buttons from DOM
const popButton = document.querySelector('.pop');
const velocityButton = document.querySelector('.velocity');
const nextButton = document.querySelector('.next');
const resetButton = document.querySelector('.reset');
const startStopButton = document.querySelector('.start-stop');

// Add event listener for the click event
popButton.addEventListener('click', executePopButton);
velocityButton.addEventListener('click', executeVelocityButton);
nextButton.addEventListener('click', executeNextButton);
resetButton.addEventListener('click', executeveResetButton);
startStopButton.addEventListener('click', executeveStartStopButton);

// ----------------- Random Population -----------------
function executePopButton(e) {
    // Prevent default
    e.preventDefault();

    // Access the universeGrid object
    if (typeof universeGrid !== 'undefined') 
    {
        window.universeGrid.randomGrid(); // Call method from universeGrid
        window.universeGrid.populateGrid(); // Call method from universeGrid
    } 
    else 
    {
        console.error("universeGrid is not defined");
    }

    let genElement = document.querySelector('.gen h2');
    // generation
    genElement.textContent = window.universeGrid.getGeneration();

    return;
}

// ----------------- Increase Speed -----------------
function executeVelocityButton(e) {
    // Prevent default
    e.preventDefault();

    let speed = velocityButton.getAttribute('state');
    let h1Element = velocityButton.querySelector('h1');

    // increase speeds
    if(speed === '1')
    {
        velocityButton.setAttribute('state', '2');
        h1Element.className = 'speed-arrow-2';
        velocityButton.querySelector('h1').textContent = ">>"
    }
    else if(speed === '2')
    {
        velocityButton.setAttribute('state', '3');
        h1Element.className = 'speed-arrow-3';
        velocityButton.querySelector('h1').textContent = ">>>"
    }
    else if(speed === '3')
    {
        velocityButton.setAttribute('state', '4');
        h1Element.className = 'speed-arrow-4';
        velocityButton.querySelector('h1').textContent = ">>>>"
    }   
    else if(speed === '4')
    {
        velocityButton.setAttribute('state', '5');
        h1Element.className = 'speed-arrow-5';
        velocityButton.querySelector('h1').textContent = ">>>>>"
    }   
    else if(speed === '5')
    {
        velocityButton.setAttribute('state', '6');
        h1Element.className = 'speed-arrow-6';
        velocityButton.querySelector('h1').textContent = ">>>>>>"
    }   
    else // 6
    {
        velocityButton.setAttribute('state', '1');
        h1Element.className = 'speed-arrow-1';
        velocityButton.querySelector('h1').textContent = ">"
    }

    return;
}

// ----------------- Next Generation -----------------
function executeNextButton(e) {
    //console.log("Next generation");

    // Prevent default
    e.preventDefault();

    let genElement = document.querySelector('.gen h2');
    genElement.textContent = window.universeGrid.getGeneration();

    window.universeGrid.play();

    genElement.textContent = window.universeGrid.getGeneration();

    return;
}

// ----------------- Reset -----------------
function executeveResetButton(e) {
    //console.log("Reset Grid");

    // Prevent default
    e.preventDefault();

    let genElement = document.querySelector('.gen h2');

    // Access the universeGrid object
    if (typeof universeGrid !== 'undefined') {
        window.universeGrid.resetGrid(); // Call method from universeGrid
        window.universeGrid.populateGrid(); // Call method from universeGrid

        // generation
        genElement.textContent = window.universeGrid.getGeneration();

        // reset start button
        let currentState = startStopButton.getAttribute('state');
        startStopButton.setAttribute('state', 'to-play');
        startStopButton.querySelector('img').src = "../icons/play.svg";
    } else {
        console.error("universeGrid is not defined");
    }
    return;
}

// ----------------- Play / Stop -----------------
function executeveStartStopButton(e) {
    //console.log("Start-Stop Button");

    e.preventDefault();

    let currentState = startStopButton.getAttribute('state');

    if (currentState === 'to-play') {
        //console.log("To play");
        startStopButton.setAttribute('state', 'to-stop');
        startStopButton.querySelector('img').src = "../icons/stop.svg";

        if (typeof universeGrid !== 'undefined') {
            animationFrameId = requestAnimationFrame(playing);
        } else {
            console.error("universeGrid is not defined");
        }
    } else {
        //console.log("To stop");
        startStopButton.setAttribute('state', 'to-play');
        startStopButton.querySelector('img').src = "../icons/play.svg";
    }
}

let animationFrameId;

function playing() {
    let currentState = startStopButton.getAttribute('state');

    let universeSpeed = velocity();

    let genElement = document.querySelector('.gen h2');

    if (currentState === 'to-stop') 
    {
        window.universeGrid.play();
        // Schedule the next iteration after a 1-second delay
        setTimeout(function() {
            animationFrameId = requestAnimationFrame(playing);
        }, universeSpeed); // 1000 milliseconds = 1 second

        // generation
        genElement.textContent = window.universeGrid.getGeneration();

    } else {
        //console.log("Stopped playing");
        startStopButton.setAttribute('state', 'to-play');
        startStopButton.querySelector('img').src = "../icons/play.svg";
        cancelAnimationFrame(animationFrameId);
    }
}

function velocity() {
    // get velocity button
    let velocity = velocityButton.getAttribute('state');
    let velocityInt = parseInt(velocity);

    // calculating the time in milliseconds
    let timeSpeed = 1000 / (2 ** (velocityInt - 1));
    
    //console.log("speed", timeSpeed);
    return timeSpeed;
}

// ----------------- Pop-up Message - ? Question Mark -----------------
const questionPage = document.querySelector('.question');
questionPage.addEventListener('click', executeveQuestionPage);

const closeQuestionPage = document.querySelector('.closePopup');
closeQuestionPage.addEventListener('click', executeveCloseQuestionPage);

function executeveQuestionPage(e) // OPEN
{
    const divQuestion = document.getElementById('popup');
    divQuestion.style.display = 'block'; 
}

function executeveCloseQuestionPage(e) // CLOSE
{
    const divQuestion = document.getElementById('popup');
    divQuestion.style.display = 'none'; 
}

