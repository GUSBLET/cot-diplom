const blackBack = document.querySelectorAll('.black-back');
const searchButton = document.querySelector('.search-mobile-button img'); // Получаем ссылку на изображение кнопки поиска

document.querySelector('.search-mobile-button').addEventListener('click', function() {
    let burgerMenu = document.getElementById('search_menu');
    if (burgerMenu.style.display === 'flex') {
        burgerMenu.style.display = 'none';
        searchButton.src = 'img/search.png'; // Возвращаем исходную иконку поиска
        blackBack.forEach(back => back.style.display = 'none'); // Скрываем черный фон для всех элементов
        document.body.classList.remove('search-menu-open');
    } else {
        burgerMenu.style.display = 'flex';
        searchButton.src = 'img/x.svg'; // Меняем иконку на крестик
        blackBack.forEach(back => back.style.display = 'block'); // Показываем черный фон для всех элементов
        document.body.classList.add('search-menu-open');
    }
});

document.querySelector('.search-menu').addEventListener('click', function(event) {
    if (event.target === this) {
        this.style.display = 'none';
        searchButton.src = '/img/search.png'; // Возвращаем исходную иконку поиска
        document.body.classList.remove('search-menu-open');
        blackBack.forEach(back => back.style.display = 'none'); // Скрываем черный фон для всех элементов
    }
});


const filterSearchInput = document.getElementById("filterSearchLine");
const brandInput = document.getElementById("brand");
const brandMobileInput = document.getElementById("brand-mobile");
const minPriceInput = document.getElementById("min-price");
const maxPriceInput = document.getElementById("max-price");
const minPriceMobileInput = document.getElementById("min-price-mobile");
const maxPriceMobileInput = document.getElementById("max-price-mobile");
const priceSortInputs = document.getElementsByName('priceSort');
const pageInput = document.getElementById("num_count");
const left_filter_showcase_arrow = document.getElementById("left-showcase-arrow");
const right_filter_showcase_arrow = document.getElementById("right-showcase-arrow");


function updateMobileChecked(pc) {
    document.getElementById(pc).checked = true;
}

function updateMobileInput(mobileId, mobileInput) {
    document.getElementById(mobileId).value = mobileInput.value;
}

function filterShowcase() {
    localStorage.setItem('min-price', minPriceInput.value);
    localStorage.setItem('max-price', maxPriceInput.value);
    localStorage.setItem('brand', brandInput.value);
    localStorage.setItem('search-line', filterSearchInput.value);

    let selectedPriceSortInput;
    for (let i = 0; i < priceSortInputs.length; i++) {
        if (priceSortInputs[i].checked) {
            selectedPriceSortInput = priceSortInputs[i];
            break;
        }
    }
    if (selectedPriceSortInput) {
        localStorage.setItem('price-sort', selectedPriceSortInput.value);
    }
    document.querySelector("#filter-showcase-form").submit();
}

function sendPagination() {
    pageInput.value -= 1;
    document.getElementById('current-page-pagination').value = pageInput.value;
    document.querySelector("#filter-showcase-form").submit();
}

document.addEventListener("DOMContentLoaded", function updateLocalStorage() {
    let queryString = window.location.search;

    if (queryString === "")
        localStorage.clear();
});

minPriceInput.addEventListener('input', () => {
    if (parseFloat(minPriceInput.value) > parseFloat(maxPriceInput.value)) {
        maxPriceInput.value = minPriceInput.value;
    }
});

maxPriceInput.addEventListener('input', () => {
    if (parseFloat(maxPriceInput.value) < parseFloat(minPriceInput.value)) {
        minPriceInput.value = maxPriceInput.value;
    }
});

document.addEventListener("DOMContentLoaded", function filterItems() {
    const priceSortRadioAsc = document.getElementById('price-asc');
    const priceSortRadioDesc = document.getElementById('price-desc');
    const priceSortRadioNone = document.getElementById('price-none');
    const priceSortMobileRadioAsc = document.getElementById('price-asc-mobile');
    const priceSortMobileRadioDesc = document.getElementById('price-desc-mobile');
    const priceSortMobileRadioNone = document.getElementById('price-none-mobile');

    let minPrice = localStorage.getItem("min-price");
    let maxPrice = localStorage.getItem("max-price");
    let priceSort = localStorage.getItem("price-sort");

    let params_filter = '';
    if (minPrice !== null)
        params_filter += '&minPrice=' + minPrice;
    if (maxPrice !== null)
        params_filter += '&maxPrice=' + maxPrice;
    if (priceSort !== null)
        params_filter += '&priceSort=' + priceSort;

    minPriceInput.value = minPrice;
    maxPriceInput.value = maxPrice;
    minPriceMobileInput.value = minPrice;
    maxPriceMobileInput.value = maxPrice;
    brandInput.value = localStorage.getItem("brand")
    brandMobileInput.value = localStorage.getItem("brand")

    if (priceSort === 'DESC') {
        priceSortRadioDesc.checked = true;
        priceSortMobileRadioDesc.checked = true;
    } else if(priceSort === 'ASC') {
        priceSortRadioAsc.checked = true;
        priceSortMobileRadioAsc.checked = true;
    } else {
        priceSortMobileRadioNone.checked = true;
        priceSortRadioNone.checked = true;
    }


    const setPaginationPath = (params_filter) => {
        if (left_filter_showcase_arrow)
            left_filter_showcase_arrow.href += params_filter;
        if (right_filter_showcase_arrow)
            right_filter_showcase_arrow.href += params_filter;

        document.getElementById('last-page').href += params_filter;
        document.getElementById('first-page').href += params_filter;
    }
    setPaginationPath(params_filter)

});






