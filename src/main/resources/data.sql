

INSERT INTO Usuario(id, username, email, password, rol, activo, rompecabezaNivel) VALUES(null,'Juan', 'test@unlam.edu.ar', 'test', 'ADMIN', true, 1);
INSERT INTO Usuario(id, username,  email, password, rol, activo, rompecabezaNivel) VALUES(null,'Leandro', 'admin@example.com', '1234', 'ADMIN', true, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel, versusNivel) VALUES(null, 'ricardosk853@gmail.com', 'asd', 'ADMIN', true, 1, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel, versusNivel) VALUES(null, 'ricardosk8532@gmail.com', 'asd', 'ADMIN', true, 1, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel, versusNivel) VALUES(null, 'ricardosk8533@gmail.com', 'asd', 'ADMIN', true, 1, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel, versusNivel) VALUES(null, 'ricardosk8534@gmail.com', 'asd', 'ADMIN', true, 1, 1);
INSERT INTO Usuario(id, email, password, rol, activo, rompecabezaNivel, versusNivel) VALUES(null, 'ricardosk8535@gmail.com', 'asd', 'ADMIN', true, 1, 1);
/*INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 1);
INSERT INTO NivelJuego(id, nombre, nivel, usuario_id) VALUES (null, 'Rompecabeza', 1, 2);*/
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Bellerina-cappuccina', '/img/versus/pjpngs/bellerina-cappuccina.png', 1);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Bombardino-crocodilo', '/img/versus/pjpngs/bombardino-crocodilo.png', 2);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Brrbrrr-patapi', '/img/versus/pjpngs/brrbrrr-patapi.png', 3);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Capiballero-cocosini', '/img/versus/pjpngs/capiballero-cocosini.png', 4);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Capuccino-assasno', '/img/versus/pjpngs/capuccino-assasno.png', 5);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Chimpancini-bananini', '/img/versus/pjpngs/chimpancini-bananini.png', 6);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Los-tralaleritos', '/img/versus/pjpngs/los-tralaleritos.png', 7);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Saturno-saturnita', '/img/versus/pjpngs/saturno-saturnita.png', 8);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Tralalero', '/img/versus/pjpngs/tralalero.png', 9);
INSERT INTO Rompecabeza(id, nombre, urlImg, nivel) VALUES (null, 'Tuntuntunsahur', '/img/versus/pjpngs/tuntuntunsahur.png', 10);

INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'tralalerobg', 'Tralalero tralala', 'tralalerosound', 'tralaleroshadow', 'tralalerocom', 1);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'tuntunbg', 'Tung tung tung Sahur', 'tuntunsound', 'tuntunshadow', 'tuntuncom', 1);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'chimpancinibg', 'Chimpancini bananini', 'chimpancinisound', 'chimpancinishadow', 'chimpancinicom',1);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'brrbrrbg', 'Brr brr patapi', 'brrbrrsound', 'brrbrrshadow', 'brrbrrcom',1);

INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'ballerinabg', 'Ballerina Capuchina', 'ballerinasound', 'ballerinashadow', 'ballerinacom', 2);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'capibarelobg', 'Capibarelo Cocosini', 'capisound', 'capishadow', 'capicom', 2);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'capuccinobg', 'Capuchino Assasino', 'capuccinosound', 'capuccinoshadow', 'capuccinocom',2);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'lirilibg', 'Lirili Larila', 'lirilisound', 'lirilishadow', 'lirilicom',2);

INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'bobritobg', 'Bobrito Bandido', 'bobritosound', 'bobritoshadow', 'bobritocom', 3);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'bombinibg', 'Bombini Gusini', 'bombinisound', 'bombinishadow', 'bombinicom', 3);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'lirilibg', 'Lirili Larila', 'lirilisound', 'lirilishadow', 'lirilicom',3);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'frigobg', 'Frigo Camelo', 'frigosound', 'frigoshadow', 'frigocom',3);

INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'bonecabg', 'Boneca Ambalabu', 'bonecasound', 'bonecashadow', 'bonecacom', 4);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'mateobg', 'Matteo', 'mateosound', 'mateoshadow', 'mateocom', 4);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'saturnitobg', 'Saturnito Saturnita', 'saturnitosound', 'saturnitoshadow', 'saturnitocom',4);
INSERT INTO Brainrot(id, imagenFondo, imagenPersonaje, audio, imagenSombra, imagenCompleta, nivel) VALUES (null, 'tripibg', 'Trippi Troppi', 'tripisound', 'tripishadow', 'tripicom',4);

INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Taza', '/img/Ingredientes/taza.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Tiburón', '/img/Ingredientes/tiburon.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Gato', '/img/Ingredientes/gato.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Perro', '/img/Ingredientes/perro.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Banana', '/img/Ingredientes/banana.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Pato', '/img/Ingredientes/pato.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Pizza', '/img/Ingredientes/pizza.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Calavera', '/img/Ingredientes/calavera.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Alienígena', '/img/Ingredientes/alienigena.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Robot', '/img/Ingredientes/robot.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Fantasma', '/img/Ingredientes/fantasma.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Ojo', '/img/Ingredientes/ojo.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Hamburguesa', '/img/Ingredientes/hamburguesa.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Carpincho', '/img/Ingredientes/carpincho.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Hongo', '/img/Ingredientes/hongo.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Cohete', '/img/Ingredientes/cohete.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Payaso', '/img/Ingredientes/payaso.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Espada', '/img/Ingredientes/espada.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Dinosaurio', '/img/Ingredientes/dinosaurio.png', 'crear');
INSERT INTO IMAGEN(nombre, url, tipo) VALUES ('Queso', '/img/Ingredientes/queso.png', 'crear');