SHELL := /bin/bash
init:
	docker-compose -f docker-compose.init.yaml up -d || exit 1
	echo "Waiting for 20 seconds"
	sleep 20
	echo "executing sql file to mysql"
	docker exec -i mysql /usr/bin/mysql < misc/database/schema/cosmetics.sql && echo "Database structure built"
	echo "Docker bootstrap complete"
	docker-compose -f docker-compose.init.yaml down || exit 1;
	docker-compose down || exit 1
	mvn clean install -T 1C -DskipTests || exit 1
	nohup docker-compose up --build &
	tail -f nohup.out
redeploy:
	docker-compose down || exit 1
	mvn clean install -T 1C -DskipTests || exit 1
	touch nohub.out
	nohup docker-compose up --build &
	tail -f nohup.out
runbase:
	docker-compose down || docker-compose -f docker-compose-base.yaml down || exit 1
	touch nohub.out
	nohup docker-compose -f docker-compose-base.yaml up --build &
	tail -f nohup.out

reload:
	docker-compose down || exit 1
	touch nohup.out
	nohup docker-compose up &
	tail -f nohup.out

reload-smtp:
	docker-compose down || exit 1
	touch nohup.out
	nohup docker-compose -f docker-compose-smtp.yaml up &
	tail -f nohup.out
