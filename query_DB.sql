INSERT INTO tavolo (id, taken) VALUES
    (1, false),
    (2, false),
    (3, false),
    (4, false),
    (5, false),
    (6, false),
    (7, false),
    (8, false),
    (9, false),
    (10, false),
    (11, false),
    (12, false),
    (13, false),
    (14, false),
    (15, false);
    
INSERT INTO piatto (nome, prezzo, categoria, allergie, ordinabile, description)
VALUES ('Spaghetti alla Carbonara', 12.50, 'Primo piatto', 'Glutine, uova', true, 'Spaghetti conditi con uovo, guanciale e formaggio pecorino romano'),
('Pizza Margherita', 8.00, 'Pizza', 'Glutine, lattosio', true, 'Pizza con pomodoro, mozzarella e basilico'),
('Tagliata di Manzo', 18.00, 'Secondo piatto', '', true, 'Tagliata di manzo con rucola e grana'),
('Tiramisù', 5.50, 'Dessert', 'Glutine, uova', true, 'Dolce al cucchiaio con savoiardi, mascarpone, caffè e cacao'),
('Insalata di Rucola', 7.00, 'Contorno', '', true, 'Insalata di rucola, pomodorini e scaglie di grana'),
('Caffè Espresso', 1.20, 'Bevande', '', true, 'Caffè espresso italiano');

INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (100, 1641063999, 25.0, true, 1);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1002, 1642310422, 42.5, true, 2);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1003, 1643628234, 18.75, true, 3);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1004, 1644802889, 57.2, true, 4);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1005, 1646186962, 32.1, true, 5);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1006, 1647472005, 10.99, true, 6);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1007, 1648671623, 24.75, true, 7);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1008, 1650088038, 50.0, true, 8);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (1009, 1651287662, 29.5, true, 9);
INSERT INTO conto (id, time, total, is_chiuso, numero_tavolo) VALUES (10011, 1652535585, 66, true, 10);

insert into utente values('as', 'as', true, 'as', 'as', 'ADDETTOSALA', null);
insert into utente values('a', 'a', true, 'a', 'a', 'AMMINISTRATORE', null);
insert into utente values('s', 's', true, 's', 's', 'SUPERVISORE', null);
insert into utente values('ac', 'ac', true, 'ac', 'ac', 'ADDETTOCUCINA', null);

INSERT INTO ingrediente (nome, costo, quantita, misura, tolleranza, description) 
VALUES ('sale', 0.5, 500, 'grammi', 0.1, 'sale da tavola comune');
INSERT INTO ingrediente (nome, costo, quantita, misura, tolleranza, description) 
VALUES ('farina', 1.2, 1000, 'grammi', 0.05, 'farina 00');
INSERT INTO ingrediente (nome, costo, quantita, misura, tolleranza, description) 
VALUES ('petto di pollo', 3.5, 200, 'grammi', 0.05, 'petto di pollo senza pelle e senza osso');
INSERT INTO ingrediente (nome, costo, quantita, misura, tolleranza, description) 
VALUES ('pomodoro', 1.0, 300, 'grammi', 0.05, 'pomodoro maturo');
INSERT INTO ingrediente (nome, costo, quantita, misura, tolleranza, description) 
VALUES ('mozzarella', 2.5, 250, 'grammi', 0.05, 'mozzarella di bufala campana');