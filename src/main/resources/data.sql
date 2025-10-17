

INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel) VALUES(null, 'admin@example.com', '1234', 'ADMIN', true, 1);
INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 1);
INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 2);
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Bellerina-cappuccina', '/img/versus/pjpngs/bellerina-cappuccina.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Bombardino-crocodilo', '/img/versus/pjpngs/bombardino-crocodilo.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Brrbrrr-patapi', '/img/versus/pjpngs/brrbrrr-patapi.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Capiballero-cocosini', '/img/versus/pjpngs/capiballero-cocosini.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Capuccino-assasno', '/img/versus/pjpngs/capuccino-assasno.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Chimpancini-bananini', '/img/versus/pjpngs/chimpancini-bananini.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Los-tralaleritos', '/img/versus/pjpngs/los-tralaleritos.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Saturno-saturnita', '/img/versus/pjpngs/saturno-saturnita.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Tralalero', '/img/versus/pjpngs/tralalero.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png');

-- INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png', 'crear');
-- INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png', 'crear');
-- INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png', 'crear');
-- INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png', 'adivinar');