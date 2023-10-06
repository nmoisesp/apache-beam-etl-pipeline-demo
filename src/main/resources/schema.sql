create table movies_raw_data (
    id int auto_increment,
    year_ int NOT NULL,
    title varchar(255) NOT NULL,
    studio varchar(100) NOT NULL,
    producers varchar (255) NOT NULL,
    winner varchar(5)
);

create table winner (
    id int auto_increment,
    producer varchar(100) NOT NULL,
    previous_win int NOT NULL,
    following_win int NOT NULL,
    intervals int NOT NULL
);