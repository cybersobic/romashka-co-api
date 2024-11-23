# Сборка проекта в .jar
build-jar:
	mvn clean package

# Запуск контейнеров со сборкой образов и файла .jar
build: build-jar
	docker-compose up --build

# Запуск контейнеров
on:
	docker-compose up -d

# Остановка контейнеров
off:
	docker-compose down

# Проверка статуса контейнеров
status:
	docker-compose ps

# Перезапуск контейнеров
restart: off build on