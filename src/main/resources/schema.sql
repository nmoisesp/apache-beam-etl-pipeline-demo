create table movie_raw (
    id int auto_increment,
    "year" int NOT NULL,
    titles varchar(255) NOT NULL,
    studios varchar(100) NOT NULL,
    producers varchar (255) NOT NULL,
    winner varchar(5)
);

create table winner (
    id int auto_increment,
    producer varchar(100) NOT NULL,
    previous_win int NOT NULL,
    following_win int NOT NULL,
    "interval" int NOT NULL
);