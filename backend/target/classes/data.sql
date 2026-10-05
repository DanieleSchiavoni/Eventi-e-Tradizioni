

-- 2. Categoria
INSERT INTO categoria (nome, icona) VALUES 
('Storia e Monumenti', 'bi-bank'),
('Religione', 'bi-building'),
('Sagre e Tradizioni', 'bi-cup-hot'),
('Musica e Spettacolo', 'bi-music-note-beamed'),
('Sport', 'bi-trophy');

-- 3. Luogo (Columns: name, address, description, image_url, latitude, longitude, categoria_id)
INSERT INTO luogo (name, address, description, image_url, latitude, longitude, categoria_id) 
VALUES ('Torretto', 'Piazza Vittorio Emanuele', 'Torre medievale simbolo di Avetrana', 'https://example.com/torretto.jpg', 40.3512, 17.7281, 1);

-- 4. Evento (Columns: title, description, image_url, organizer_name, start_date, end_date, categoria_id, luogo_id)
INSERT INTO evento (title, description, image_url, organizer_name, start_date, end_date, categoria_id, luogo_id) 
VALUES ('Festa Patronale San Biagio', 'Solenni festeggiamenti in onore di San Biagio', 'https://example.com/sanbiagio.jpg', 'Comitato Festa', '2026-10-01 18:00:00', '2026-10-03 23:59:00', 2, 1);

-- 5. Notizia (Columns: title, content, published_at, priority)
INSERT INTO notizia (title, content, published_at, priority) 
VALUES ('Chiusura Traffico Centro', 'Strade chiuse per evento locale', CURRENT_TIMESTAMP, 'MEDIA');