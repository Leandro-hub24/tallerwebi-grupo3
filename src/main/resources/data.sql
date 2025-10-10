

INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel) VALUES(null, 'admin@example.com', '1234', 'ADMIN', true, 1);
INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 1);
INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 2);
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Bellerina-cappuccina', '/img/versus/pjpngs/bellerina-cappuccina.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Bombardino-crocodilo', '/img/versus/pjpngs/bombardino-crocodilo.png');
INSERT INTO Rompecabeza(id, nombre, urlImg) VALUES (null, 'Brrbrrr-patapi', '/img/versus/pjpngs/brrbrrr-patapi.png');