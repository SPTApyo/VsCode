const orb = document.querySelector('.cursor-orb');
const circle = document.querySelector('.cursor-circle');

let orbX = 0, orbY = 0;
let circleX = 0, circleY = 0;
let mouseX = 0, mouseY = 0;
const speedOrb = 0.2;
const speedCircle = 1.9;
let lastUpdate = Date.now();

document.addEventListener('mousemove', updateMousePosition);

function updateMousePosition(e) {
    mouseX = e.pageX;
    mouseY = e.pageY;
}

function updateCursorPositions() {
    const now = Date.now();
    const deltaTime = now - lastUpdate;

    if (deltaTime > 11) {
        orbX += (mouseX - orbX) * speedOrb;
        orbY += (mouseY - orbY) * speedOrb;

        circleX += (orbX - circleX) * speedCircle;
        circleY += (orbY - circleY) * speedCircle;

        orb.style.left = `${orbX - orb.offsetWidth / 2}px`;
        orb.style.top = `${orbY - orb.offsetHeight / 2}px`;
        circle.style.left = `${circleX - circle.offsetWidth / 2}px`;
        circle.style.top = `${circleY - circle.offsetHeight / 2}px`;

        lastUpdate = now;
    }

    requestAnimationFrame(updateCursorPositions);
}

updateCursorPositions();

const buttons = document.querySelectorAll('.interactive');
const parts = document.querySelectorAll('section');

buttons.forEach(button => {
    button.addEventListener('mouseenter', () => {
        orb.style.transform = 'scale(3.5)';
        orb.style.opacity = '0.5';
    });

    button.addEventListener('mouseleave', () => {
        orb.style.transform = 'scale(1)';
        orb.style.opacity = '1';
    });
});

parts.forEach(part => { 
    part.addEventListener('mouseenter', () => {
        orb.style.transform = 'scale(2.5)';
        orb.style.opacity = '0.8';
        orb.style.borderRadius = '0';
        orb.style.clipPath = `polygon(44% 0, 56% 0, 56% 44%, 100% 44%, 100% 56%, 56% 56%, 56% 100%, 44% 100%, 44% 56%, 0 56%, 0 44%, 44% 44%)`;
    });

    part.addEventListener('mouseleave', () => {
        orb.style.transform = 'scale(1)';
        orb.style.opacity = '1';
        orb.style.borderRadius = '50%';
        orb.style.clipPath = 'circle(50% at 50% 50%)';
    });
});
