all: run_tests

run_tests:
	robot labOpenCart.robot
	mv -t results/ output.xml log.html report.html

start_opencart:
	docker-compose up -d

delete_opencart:
	docker-compose down
	docker-compose rm -f
