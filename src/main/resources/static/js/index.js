document.addEventListener('DOMContentLoaded', function () {
    const dropdownItems = document.querySelector('.dropdown__items');
    const likeButton = document.querySelector('.like-button');

    if(dropdownItems){
        likeButton.addEventListener('click', function () {
            dropdownItems.classList.toggle('open');
        });
    }

    if(likeButton){
        document.addEventListener('click', function(event) {
            const targetElement = event.target;
            if (!dropdownItems.contains(targetElement) && !likeButton.contains(targetElement)) {
                dropdownItems.classList.remove('open');
            }
        });
    }
});

const quantityInputs = document.querySelectorAll('.quantity-count');
const increaseBtns = document.querySelectorAll('.plus');
const decreaseBtns = document.querySelectorAll('.minus');
let minimun_value = 0;

increaseBtns.forEach((btn, index) => {
    btn.addEventListener('click', () => {
        quantityInputs[index].stepUp();
    });
});

decreaseBtns.forEach((btn, index) => {
    btn.addEventListener('click', () => {
        if (quantityInputs[index].value > minimun_value) {
            quantityInputs[index].stepDown();
        }
    });
});


document.querySelector('.mobile-button-filter').addEventListener('click', function() {
    let burgerMenu = document.getElementById('burgerMenu');
    burgerMenu.style.display = 'flex';
    document.body.classList.add('burger-menu-open');
    // Скрытие скролла при открытии меню
    document.body.style.overflow = 'hidden';
});

document.querySelector('.burger-menu').addEventListener('click', function(event) {
    if (event.target === this) {
        this.style.display = 'none';
        document.body.classList.remove('burger-menu-open');
        // Восстановление скролла при закрытии меню
        document.body.style.overflow = 'auto';
    }
});


