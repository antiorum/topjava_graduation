new Vue({
    el: '#voting',
    data: function () {
        return {
            restaurants: null,
        }
    },
    mounted() {
        axios
            .get('http://localhost:8080/ajax/restaurants')
            .then(response => (this.restaurants = response.data))
    },
    methods: {
        vote: function (event) {
            let bodyFormData = new FormData();
            bodyFormData.set('restaurantId', event.target.parentElement.parentElement.firstChild.textContent);
            axios({
                method: 'post',
                url: 'http://localhost:8080/ajax/votes',
                data: bodyFormData
            })
                .then(function (response) {
                    $('.modal-title').html('Успешно!');
                    $('.modal-body').html('Ваш голос учтен!');
                    $('#modalWindow').modal('show');

                })
                .catch(function (error) {
                    $('.modal-title').html('Неудача!');
                    $('.modal-body').html('Сегодня вы уже не можете изменить своё решение, пожалуйста проголосуйте завтра!');
                    $('#modalWindow').modal('show');
                });
        }
    }
});

let getAllDishes = function () {
    axios
        .get('http://localhost:8080/ajax/dishes')
        .then(function (resp) {
            adminPanelVue.dishes = resp.data;
        });
};

let getRestaurantsWithTodayLunch = function () {
    axios
        .get('http://localhost:8080/ajax/restaurants')
        .then(function (resp) {
            adminPanelVue.restaurants = resp.data;
        })
};

let adminPanelVue = new Vue({
    el: '#admin_panel',
    data: function () {
        return {
            users: null,
            dishes: null,
            restaurants: null,
            dishesForCreateLunch: []
        }
    },
    created() {
        axios
            .get('http://localhost:8080/ajax/users')
            .then(response => (this.users = response.data));
        getAllDishes();
        getRestaurantsWithTodayLunch();
    },
    methods: {
        deleteUser: function (event) {
            const userId = event.target.parentElement.parentElement.firstChild.textContent;
            axios({
                method: 'delete',
                url: 'http://localhost:8080/ajax/users/' + userId
            });
            for (let i = 0; i < this.users.length; i++) {
                if (this.users[i].id === parseInt(userId)) {
                    this.users.splice(i, 1)
                }
            }
        },
        deleteDish: function (event) {
            const dishId = event.target.parentElement.parentElement.firstChild.textContent;
            axios({
                method: 'delete',
                url: 'http://localhost:8080/ajax/dishes/' + dishId
            });
            for (let i = 0; i < this.dishes.length; i++) {
                if (this.dishes[i].id === parseInt(dishId)) {
                    this.dishes.splice(i, 1)
                }
            }
        },
        createDish: function () {
            let newDishFormData = new FormData();
            newDishFormData.set('name', $('#newDishName').val());
            newDishFormData.set('price', $('#newDishPrice').val());
            axios({
                method: 'post',
                url: 'http://localhost:8080/ajax/dishes',
                data: newDishFormData
            }).then(function (response) {
                getAllDishes();
                $('#dishCreateModal').modal('hide');
            });
        },
        updateDish: function () {
            let updateDishFormData = new FormData();
            updateDishFormData.set('id', $('#updateDishId').val());
            updateDishFormData.set('name', $('#updateDishName').val());
            updateDishFormData.set('price', $('#updateDishPrice').val());
            axios({
                method: 'post',
                url: 'http://localhost:8080/ajax/dishes',
                data: updateDishFormData
            }).then(function (response) {
                getAllDishes();
                $('#dishUpdateModal').modal('hide');
            });
        },
        openDishUpdate: function (event) {
            const dishId = event.target.parentElement.parentElement.firstChild.textContent;
            const dishName = event.target.parentElement.parentElement.childNodes[2].textContent;
            const dishPrice = parseFloat(event.target.parentElement.parentElement.childNodes[4].textContent);
            $('#updateDishId').val(dishId);
            $('#updateDishName').val(dishName);
            $('#updateDishPrice').val(dishPrice);
            $('#dishUpdateModal').modal('show')
        },
        deleteLunch: function (event) {
            const lunchId = event.target.parentElement.parentElement.childNodes[6].childNodes[1].textContent;
            axios({
                method: 'delete',
                url: 'http://localhost:8080/ajax/lunches/' + lunchId,
            }).then(function (resp) {
                getRestaurantsWithTodayLunch();
            });
        },
        openLunchCreate: function (event) {
            const restaurantId = event.target.parentElement.parentElement.firstChild.textContent;
            const restaurantName = event.target.parentElement.parentElement.childNodes[2].textContent;
            const restaurantAddress = event.target.parentElement.parentElement.childNodes[4].textContent;
            $('#createLunchRestaurantId').text(restaurantId);
            $('#createLunchRestaurantName').text(restaurantName);
            $('#createLunchRestaurantAddress').text(restaurantAddress);
            $('#lunchCreateModal').modal('show')
        },
        clearDishesForCreateLunch: function () {
            adminPanelVue.dishesForCreateLunch = []
        },
        deleteFromDishesToLunch: function (event) {
            const dishId = event.target.parentElement.parentElement.firstChild.textContent;
            for (let i = 0; i < adminPanelVue.dishesForCreateLunch.length; i++) {
                if (adminPanelVue.dishesForCreateLunch[i].id === parseInt(dishId)) {
                    adminPanelVue.dishesForCreateLunch.splice(i, 1)
                }
            }
        },
        addDishToLunch: function (event) {
            const dishId = event.target.parentElement.parentElement.firstChild.textContent;
            for (let i = 0; i < adminPanelVue.dishes.length; i++) {
                if (adminPanelVue.dishes[i].id === parseInt(dishId)) {
                    let containigId = 0;
                    for (let i = 0; i < adminPanelVue.dishesForCreateLunch.length; i++) {
                        if (adminPanelVue.dishesForCreateLunch[i].id === parseInt(dishId)) {
                            containigId = 1;
                        }
                    }
                    if (containigId === 0) adminPanelVue.dishesForCreateLunch.push(adminPanelVue.dishes[i])
                }
            }
        },
        createLunch: function (event) {
            const lunchName = $('#newLunchName').val();
            const dishesIds = [];
            const restaurantId = $('#createLunchRestaurantId').text();

            for (let i=0; i< this.dishesForCreateLunch.length;i++){
                dishesIds.push(this.dishesForCreateLunch[i].id)
            }

            let newLunchFormData = new FormData();
            newLunchFormData.set('name', lunchName);
            newLunchFormData.set('dishesIds', dishesIds);
            newLunchFormData.set('restaurantId', restaurantId);

            axios({
                method: 'post',
                url: 'http://localhost:8080/ajax/lunches',
                data: newLunchFormData
            }).then(function (response) {
                getRestaurantsWithTodayLunch();
                adminPanelVue.dishesForCreateLunch = [];
                $('#newLunchName').val('');
                $('#lunchCreateModal').modal('hide')
            });
        }
    }
});



