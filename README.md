# topjava_graduation
TopJava graduation project

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

Desicion:

Only authorized users can do requests!

Commands:

1.Authorization
url: http://localhost:8085/login
method: post
dataParams: {
username: [String]
password: [String]
}

Admin has username "admin" and password "admin" by default

2.Registration
url: http://localhost:8085/registration
method: post
dataParams: {
name: [String]
password: [String]
}

3.Get list of restaurants with today lunches
url: http://localhost:8085/ajax/restaurants/
method: get

4.Get list of restaurants with lunch history and voting history, this operation only access for Admins
url: http://localhost:8085/ajax/restaurants/allWithContents
method: get

5.Add or update restaurant, this operation only access for Admins
url: http://localhost:8085/ajax/restaurants/
method: post
dataParams: {
name: [String]
address: [String]
id: [Long] -optional, if exists, if will update restaurant with this id, else create a new restaurant
}

6.Get restaurant by id
url: http://localhost:8085/ajax/restaurants/{id}
method: get 
{id} is path varaible

7. Delete restaurant, this operation only access for Admins
url: http://localhost:8085/ajax/restaurants/{id}
method: delete
{id} is path varaible

8. Show all users, this operation only access for Admins
url: http://localhost:8085/ajax/users/
method: get

9. delete user, this operation only access for Admins
url: http://localhost:8085/ajax/users/{id}
method: delete
{id} is path varaible

10.Show all dishes, this operation only access for Admins
url: http://localhost:8085/ajax/dishes/
method: get

11.Show dish by id
url: http://localhost:8085/ajax/dishes/{id}
method: get
{id} is path varaible

12.Show dishes, which are the incoming components of specific lunch
url: http://localhost:8085/ajax/dishes/getByLunch
method: get
url params: lunchId=Long

13. add or update dish, this operation only access for Admins
url: http://localhost:8085/ajax/dishes/
method: post
dataParams: {
name: [String]
price: [Bigdecimal]
id: [Long] -optional, if exists, if will update restaurant with this id, else create a new restaurant
}

14. delete dish, this operation only access for Admins
url: http://localhost:8085/ajax/dishes/{id}
method: delete
{id} is path varaible

Operations with lunches  only access for Admins:

15.Get all lunches
url: http://localhost:8085/ajax/lunches/
method: get

16. Get lunch by id
url: http://localhost:8085/ajax/lunches/{id}
method: get
{id} is path varaible

17. Add lunch
url: http://localhost:8085/ajax/lunches/
method: post
dataParams: {
name: [String]
dishesIds: [List<Long>]
restaurantId: [Long]
}
  
18.Delete lunch
url: http://localhost:8085/ajax/lunches/{id}
method: delete
{id} is path varaible
  
And at least voting:

19. Show all votes, this operation only access for Admins
url: http://localhost:8085/ajax/votes/
method: get

20. Show votes of specific restaurant, this operation only access for Admins
url: http://localhost:8085/ajax/votes/getByRestaurantId
method: get
url params: restaurantId=Long

21. Show votes of logged user
url: http://localhost:8085/ajax/votes/getMyVotes
method: get

22. Voting!
url: http://localhost:8085/ajax/votes/
method: post
dataParams: {
restaurantId: [Long]
}

//todo
- update users
- impl front
- upgrade exception handling
