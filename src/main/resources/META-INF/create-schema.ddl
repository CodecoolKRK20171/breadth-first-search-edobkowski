create table Friendship (UserNode int8 not null, FriendNode int8 not null, primary key (UserNode, FriendNode))
create table UserNode (id  bigserial not null, firstName varchar(255), lastName varchar(255), primary key (id))
alter table Friendship add constraint FKc5lmnwd99v09lcolljj3y2ube foreign key (FriendNode) references UserNode
alter table Friendship add constraint FK5i9k3ng3s6477p7tsu0p8a2bf foreign key (UserNode) references UserNode
create table Friendship (UserNode int8 not null, FriendNode int8 not null, primary key (UserNode, FriendNode))
create table UserNode (id  bigserial not null, firstName varchar(255), lastName varchar(255), primary key (id))
alter table Friendship add constraint FKc5lmnwd99v09lcolljj3y2ube foreign key (FriendNode) references UserNode
alter table Friendship add constraint FK5i9k3ng3s6477p7tsu0p8a2bf foreign key (UserNode) references UserNode
